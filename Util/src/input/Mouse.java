package input;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JFrame;
import math.vector.IntVector2D;

public class Mouse implements AWTEventListener
{
	private static final Cursor invisible_cursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null");
	static
	{
		px = getMouseX();
		py = getMouseY();
		Toolkit.getDefaultToolkit().addAWTEventListener(new Mouse(), AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
		try
		{
			robot = new Robot();
		} catch(AWTException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private static MListener[] listeners = new MListener[0];
	private static final boolean[] buttons = new boolean[3];
	static
	{
		Arrays.fill(buttons, false);
	}
	
	public static void addListener(MListener listener)
	{
		MListener[] newA = new MListener[listeners.length + 1];
		System.arraycopy(listeners, 0, newA, 0, listeners.length);
		newA[newA.length - 1] = listener;
		listeners = newA;
	}
	
	public static void addPriorityListener(MListener listener)
	{
		MListener[] newA = new MListener[listeners.length + 1];
		System.arraycopy(listeners, 0, newA, 1, listeners.length);
		newA[0] = listener;
		listeners = newA;
	}
	
	public static void removeListener(MListener listener)
	{
		int index = -1;
		for(int i = 0; i < listeners.length; i++)
		{
			if(listeners[i] == listener)
			{
				index = i;
				break;
			}
		}
		if(index != -1)
		{
			
			MListener[] newA = new MListener[listeners.length - 1];
			System.arraycopy(listeners, 0, newA, 0, index);
			System.arraycopy(listeners, index + 1, newA, index, newA.length - index);
			listeners = newA;
		}
	}

	private static int px;
	private static int py;
	//mouse locking info
	private static boolean lock = false;
	public static int trueX;
	public static int trueY;
	private static Robot robot;
	private static HashSet<IntVector2D> robotMoves = new HashSet<IntVector2D>();
	
	@Override
	public void eventDispatched(AWTEvent e)
	{
		if(e instanceof MouseEvent)
		{
			int x = ((MouseEvent)e).getXOnScreen();
			int y = ((MouseEvent)e).getYOnScreen();
			int button = ((MouseEvent)e).getButton() - 1;
			int dx = x - px;
			int dy = y - py;
			px = x;
			py = y;
			
			if((e.getID() == MouseEvent.MOUSE_MOVED || e.getID() == MouseEvent.MOUSE_DRAGGED) && robotMoves.remove(new IntVector2D(x, y)))
			{
				return;
			}
			
			switch(e.getID())
			{
				case MouseEvent.MOUSE_PRESSED:
				{
					setButton(button, true);
					for(MListener lis : listeners)
					{
						if(lis.mousePress(x, y, button))
						{
							break;
						}
					}
					break;
				}
				case MouseEvent.MOUSE_RELEASED:
				{
					setButton(button, false);
					for(MListener lis : listeners)
					{
						if(lis.mouseRelease(x, y, button))
						{
							break;
						}
					}
					break;
				}
				case MouseEvent.MOUSE_MOVED:
				case MouseEvent.MOUSE_DRAGGED:
				{
					for(MListener lis : listeners)
					{
						if(lis.mouseMove(x, y, dx, dy))
						{
							break;
						}
					}
					break;
				}
			}
			
			if(lock)
			{
				trueX += dx;
				trueY += dy;
				moveMouse(x - dx, y - dy);
			}
		}
	}
	
	private void setButton(int button, boolean down)
	{
		if(button >= 0 && button < buttons.length)
		{
			buttons[button] = down;
		}
	}
	
	public interface MListener
	{
		public boolean mousePress(int x, int y, int button);
		
		public boolean mouseRelease(int x, int y, int button);
		
		public boolean mouseMove(int x, int y, int dx, int dy);
	}
	
	public static void hideCursor(JFrame frame)
	{
		frame.setCursor(invisible_cursor);
	}
	
	public static void defaultCursor(JFrame frame)
	{
		frame.setCursor(Cursor.getDefaultCursor());
	}
	
	public static void lockMouse(boolean l)
	{
		lock = l;
		if(lock)
		{
			trueX = getMouseX();
			trueY = getMouseY();
		}else
		{
			moveMouse(trueX, trueY);
		}
	}
	
	public static void moveMouse(int x, int y)
	{
		robotMoves.add(new IntVector2D(x, y));
		robot.mouseMove(x, y);
	}
	
	public static int getMouseX()
	{
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	public static int getMouseY()
	{
		return MouseInfo.getPointerInfo().getLocation().y;
	}
	
	public static boolean buttonDown(int button)
	{
		if(button < 0 || button >= buttons.length) return false;
		return buttons[button];
	}
}
