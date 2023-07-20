import javax.swing.JButton;

public class Space 
{
	private int number;
	private boolean marked;
	private JButton button; 
	
	public Space(int num)
	{
		number = num;
		marked = false;
		button = new JButton("" + number);
	}
	
	public int getNumber()
	{
		return number;
	}
	public void mark()
	{
		marked = true;
	}
	public void unmark()
	{
		marked = false;
	}
	public boolean ifIsMarked()
	{
		return marked;
	}
	public void setNumber(int num)
	{
		number = num;
	}
	public JButton getButton()
	{
		return button;
	}
}
