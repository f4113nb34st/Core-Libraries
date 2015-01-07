package core;

import java.awt.Graphics;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	public GameFrame(String s)
	{
		super(s);
	}
	
	/*public void repaint()
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					GameFrame.super.repaint();
				}
			});
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}*/
	
	public int getTrueWidth()
	{
		if(isUndecorated())
		{
			return getWidth();
		}
		return getWidth() - getInsets().left - getInsets().right;
	}
	
	public int getTrueHeight()
	{
		if(isUndecorated())
		{
			return getHeight();
		}
		return getHeight() - getInsets().top - getInsets().bottom;
	}
	
	@Override
	public void paint(Graphics g)
	{
		Core.instance.paintFrame(g);
	}
}
