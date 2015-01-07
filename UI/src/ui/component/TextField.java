package ui.component;

import java.util.Arrays;
import ui.KeyListener;
import ui.ListenerType;
import ui.MouseListener;

public class TextField extends Label implements KeyListener, MouseListener
{
	/**
	 * True if this field can be changed.
	 */
	public boolean editable = true;
	
	@Override
	public boolean keyTyped(char c)
	{
		if(c == '\n')//don't allow new lines retard....
		{
			return true;
		}
		if(c == '\b')//if a backspace delete the end
		{
			if(!isPassword)
			{
				if(text.length() > 0)
				{
					text = text.substring(0, text.length() - 1);
				}
			}else
			{
				if(passwordArray.length > 0)
				{
					char[] newArray = new char[passwordArray.length - 1];
					System.arraycopy(passwordArray, 0, newArray, 0, newArray.length);
					passwordArray = newArray;
					//clear old password array
					Arrays.fill(passwordArray, (char)0);
				}
			}
		}else
		{
			if(!isPassword)
			{
				text += c;
			}else
			{
				char[] newArray = new char[passwordArray.length + 1];
				System.arraycopy(passwordArray, 0, newArray, 0, passwordArray.length);
				newArray[newArray.length - 1] = c;
				passwordArray = newArray;
				//clear old password array
				Arrays.fill(passwordArray, (char)0);
			}
		}
		dirty = true;
		return true;
	}

	@Override
	public boolean keyPress(int button)
	{
		return false;
	}

	@Override
	public boolean keyRelease(int button)
	{
		return false;
	}

	@Override
	public ListenerType keyListenerType()
	{
		return ListenerType.FOCUS;
	}

	@Override
	public boolean mousePress(int x, int y, int button)
	{
		return true;//catch clicks
	}

	@Override
	public boolean mouseRelease(int x, int y, int button)
	{
		return true;//catch clicks
	}
	
	@Override
	public boolean mouseMove(int x, int y, int dx, int dy)
	{
		return false;
	}

	@Override
	public void mouseEnter()
	{
		
	}

	@Override
	public void mouseLeave()
	{
		
	}

	@Override
	public ListenerType mouseListenerType()
	{
		return ListenerType.STANDARD;
	}
}
