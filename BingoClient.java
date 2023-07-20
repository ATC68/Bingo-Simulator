import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;

public class BingoClient 
{
	private LinkedList<String> lastFiveNumbers = new LinkedList<String>(); //www.geeksforgeeks.org/data-structures/linked-list/
	private JButton button;
	private BingoCard card;
	private JFrame frame;
	private JFrame numbers;
	private boolean win;
	private Socket s;
	private JButton buttons[] = new JButton[5];//https://docs.oracle.com/javase/7/docs/api/javax/swing/JButton.html
	private boolean bingoIsCalled;
	private boolean bingoIsCorrect;
	
	public BingoClient(BingoCard c) 
	{
		bingoIsCalled = false;
		bingoIsCorrect = false;
		card = c;
		GridLayout g = new GridLayout(5, 0);
		frame = new JFrame("Bingo Check");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 100);
		frame.setVisible(true);
		numbers = new JFrame("Called numbers");
		numbers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		numbers.setSize(300, 300);
		numbers.setVisible(true);
		numbers.setLayout(g);
		for (int i = 0; i < 5; i++)
		{
			buttons[i] = new JButton("");
			numbers.add(buttons[i]);
		}
		button = new JButton("Check For Bingo");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) //http://alvinalexander.com/java/jbutton-listener-pressed-actionlistener/
		    {	
					button.setBackground(Color.YELLOW);
					bingoIsCalled = true;
		    }	
		});
		frame.add(button);
		frame.setLocation(400, 0);
		numbers.setLocation(800, 0);
	}
	
	public void run() throws UnknownHostException, IOException, InterruptedException
	{
			win = false;
			s = new Socket("localhost",6666);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			DataInputStream din = new DataInputStream(s.getInputStream());
			String var;
			while (!win && (var = din.readUTF()) != null) 
			{
				updateList(var); 
				int count = 0;
				while (!bingoIsCalled && count < 7) 
				{
					TimeUnit.SECONDS.sleep(1);//https://www.baeldung.com/java-delay-code-execution
					count++;
				}
				if(bingoIsCalled)
				{
					bingoIsCorrect = checkWin();
					if (bingoIsCorrect) {
						button.setText("WINNER"); //www.geeksforgeeks.org/stringcharacteriterator-settext-method-in-java-with-examples/
						button.setBackground(Color.GREEN);
					} else {
						button.setText("NO BINGO");
						button.setBackground(Color.RED);
						TimeUnit.SECONDS.sleep(5);
						button.setText("Check for Bingo");
						button.setBackground(Color.YELLOW);
					}
					bingoIsCalled = false;
				}
				dout.writeUTF("" + bingoIsCorrect);
				dout.flush();
			}
			dout.close();
			s.close();
	}
	public boolean verify(int[] list)
	{
		int calledValues[] = new int[lastFiveNumbers.size()];
		int count = 0;
		boolean ifTrue = false;
		for (int i = 0; i < lastFiveNumbers.size(); i++)
		{
			calledValues[i] = Integer.parseInt(lastFiveNumbers.get(i).substring(1));
		}
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < calledValues.length; j++)
			{
				if (list[i] == calledValues[j])
				{
					count++;
					if (count == 5)
					{
						ifTrue = true;
					}
				}
			}
		}
		return ifTrue;
	}
	public boolean checkWin()
	{
		return verify(card.checkDiagonal()) || verify(card.checkAcross()) || verify(card.checkDown());
	}
	public void updateList(String s)
	{
		lastFiveNumbers.addFirst(s);
		for (int i = 0; (i < 5 && i < lastFiveNumbers.size()); i++) 
		{
			buttons[i].setText(lastFiveNumbers.get(i)); //https://www.geeksforgeeks.org/stringcharacteriterator-settext-method-in-java-with-examples/
		}
	}
}
