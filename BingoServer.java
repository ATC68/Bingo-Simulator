import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;  
import java.util.Scanner;

public class BingoServer 
{
	private ArrayList<Integer> calledNumbers  = new ArrayList<Integer>();
	private boolean bingoHasBeenCalled;
	private DataOutputStream dout[];
	private DataInputStream din[];
	private Socket sockets[];
	private int gameSize;
	public BingoServer()
	{
		gameSize = -1;
		Scanner sc = new Scanner(System.in);
		while (gameSize <= 0)
		{
			System.out.println("How many cards: ");
			gameSize = sc.nextInt();
			if (gameSize <= 0)
			{
				System.out.println("Invalid answer...try again.");
			}
		}
		bingoHasBeenCalled = false;
		dout = new DataOutputStream[gameSize]; //https://www.javatpoint.com/socket-programming
		din = new DataInputStream[gameSize]; //https://www.javatpoint.com/socket-programming
		sockets = new Socket[gameSize]; //https://www.javatpoint.com/socket-programming
	}
	public void run() throws IOException 
	{
			ServerSocket ss = new ServerSocket(6666);  //https://www.javatpoint.com/socket-programming
			for (int i = 0; i < gameSize; i++)
			{
				sockets[i] = ss.accept();
				din[i] = new DataInputStream(sockets[i].getInputStream());
				dout[i] = new DataOutputStream(sockets[i].getOutputStream());
			}
			while(!bingoHasBeenCalled)
			{
				int calledValue = getRandom();
				while (calledNumbers.contains(calledValue))
				{
					calledValue = getRandom();
				}
				calledNumbers.add(calledValue);
				String s1 = getString(calledValue);
				for (int i = 0; i < gameSize; i++)
				{
					dout[i].writeUTF(s1);
					dout[i].flush();
				} 
				for (int i = 0; i < gameSize; i++)
				{
					if(din[i].readUTF().equals("true"))
					{
						bingoHasBeenCalled = true;
						System.out.println(i + " has won");
						break;
					}
				}
			}
			ss.close();
	}
	public int getRandom()
	{
		return (int) (Math.random() * 74 + 1);
	}
	public String getString(int num)
	{
		String s = null;
		if (num >= 1 && num <= 15)
			s = "B";
		if (num >= 16 && num <= 30)
			s = "I";
		if (num >= 31 && num <= 45)
			s = "N";
		if (num >= 46 && num <= 60)
			s = "G";
		if (num >= 61 && num <= 75)
			s = "O";
		return s + num;
	}
	public static void main(String[] args) throws IOException
	{
		BingoServer b = new BingoServer();
		b.run();
	}
}
