import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;

public class BingoCard 
{
	private Space spaces[][];
	
	public BingoCard()
	{
		int min = 0;
		spaces = new Space[5][5];
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				min = 1 + (15 * i);
				Space newspace = createSpace(min);
				newspace.getButton().addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
				    {	
						if (!newspace.ifIsMarked())
						{
							newspace.getButton().setBackground(Color.RED);
							newspace.mark();
						}
						else
						{
							newspace.getButton().setBackground(new JButton().getBackground());
							newspace.unmark();
						}
				    }	
				});
				spaces[j][i] = newspace; 
			}
		}
		drawCard();
	}
	public Space createSpace(int min)
	{
		boolean isUnique = true;
		int value = 0;
		do 
		{
			isUnique = true;
			value = (int) (Math.random() * 14 + min);
			for (int i = 0; i < 5; i++)
			{
				for (int j = 0; j < 5; j++)
				{
					if(spaces[i][j] != null)
					{
						if (value == spaces[i][j].getNumber())
						{
							isUnique = false;
							break;
						}
					}
				}
			}
		}
		while(!isUnique);
		return new Space(value);
	}
	public void drawCard()
	{
		JFrame frame = new JFrame("Your Bingo Card"); //https://docs.oracle.com/javase/7/docs/api/javax/swing/JFrame.html
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		GridLayout grid = new GridLayout(6, 5, 10, 10); //docs.oracle.com/javase/7/docs/api/java/awt/GridLayout.html
		frame.setLayout(grid);
		JButton b1 = new JButton("B");
		JButton b2 = new JButton("I");
		JButton b3 = new JButton("N");
		JButton b4 = new JButton("G");
		JButton b5 = new JButton("O");
		b1.setBackground(Color.BLUE); //https://alvinalexander.com/java/java-jframe-background-color/
		b2.setBackground(Color.BLUE);
		b3.setBackground(Color.BLUE);
		b4.setBackground(Color.BLUE);
		b5.setBackground(Color.BLUE);
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.add(b5);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				frame.add(spaces[i][j].getButton());
			}
		}
		frame.setVisible(true);
	}
	public int[] checkDiagonal()
	{
		int list[] = new int[5];
		int d1Count = 0, d2Count = 0;
		for (int i = 0; i < 5; i++)
		{
			if (spaces[i][i].ifIsMarked())
				d1Count++;
				if (d1Count == 5)
					for (int k = 0; k < 5; k++)
						{
							list[k] = spaces[k][k].getNumber();
						}
		}
		for (int i = 4; i >= 0; i--)
		{
			for (int j = 0; j < 5; j++)
			{
				if (spaces[i][j].ifIsMarked())
					d2Count++;
					if (d2Count == 5)
						for (int k = 4; k >= 0; k--)
						{
							for (int l = 0; l < 5; l++)
							{
								list[l] = spaces[k][l].getNumber();
							}
						}			
			}
		}
		return list;
	}
	public int[] checkAcross()
	{
		int list[] = new int[5];
		int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
		for (int i = 0; i < 5; i++)
		{
			if (spaces[0][i].ifIsMarked())
			{
				count1++;
				if (count1 == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[0][j].getNumber();
					}
			}
			if (spaces[1][i].ifIsMarked())
			{
				count2++;
				if (count2 == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[1][j].getNumber();
					}
			}
			if (spaces[2][i].ifIsMarked())
			{
				count3++;
				if (count3 == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[2][j].getNumber();
					}
			}
			if (spaces[3][i].ifIsMarked())
			{
				count4++;
				if (count4 == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[3][j].getNumber();
					}
			}
			if (spaces[4][i].ifIsMarked())
			{
				count5++;
				if (count5 == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[4][j].getNumber();
					}
			}
		}
		return list;
	}
	public int[] checkDown()
	{
		int list[] = new int[5];
		int bCount = 0, iCount = 0, nCount = 0, gCount = 0, oCount = 0;
		for (int i = 0; i < 5; i++)
		{
			if (spaces[i][0].ifIsMarked())
			{
				bCount++;
				if (bCount == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[j][0].getNumber();
					}
			}
			if (spaces[i][1].ifIsMarked())
			{
				iCount++;
				if (iCount == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[j][1].getNumber();
					}
			}
			if (spaces[i][2].ifIsMarked())
			{
				nCount++;
				if (nCount == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[j][2].getNumber();
					}
			}
			if (spaces[i][3].ifIsMarked())
			{
				gCount++;
				if (gCount == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[j][3].getNumber();
					}
			}
			if (spaces[i][4].ifIsMarked())
			{
				oCount++;
				if (oCount == 5)
					for (int j = 0; j < 5; j++)
					{
						list[j] = spaces[j][4].getNumber();
					}
			}
		}
		return list;
	}
	public Space[][] getSpaces()
	{
		return spaces;
	}
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException 
	{
		BingoCard card = new BingoCard();
		BingoClient game = new BingoClient(card);
		game.run();
	}
}
