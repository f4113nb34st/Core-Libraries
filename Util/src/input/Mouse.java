package input;

import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedList;
import javax.swing.JFrame;
import math.vector.IntVector2D;

/**
 * 
 * A simple mouse listener class that is not tied to a specific JFrame.
 * Performs a similar function to the Mouse class of JOGL.
 * 
 * @author F4113nb34st
 *
 */
public class Mouse implements AWTEventListener
{
	
	static
	{
		//register the base event listener
		Toolkit.getDefaultToolkit().addAWTEventListener(new Mouse(), AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK);
	}
	//list of Mouse listeners
	private static LinkedList<MListener> listeners = new LinkedList<MListener>();
	//set of currently-pressed buttons
	private static final HashSet<Integer> down_buttons = new HashSet<Integer>();
	
	/**
	 * Adds the given key listener.
	 * @param listener The listener.
	 */
	public static void addListener(MListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes the given key listener.
	 * @param listener The listener.
	 */
	public static void removeListener(MListener listener)
	{
		listeners.remove(listener);
	}

	//the previous mouse location
	private static IntVector2D prevCoords = new IntVector2D();
	//true if the mouse is locked
	private static boolean lock = false;
	//to coords the mouse is locked to
	public static IntVector2D lockCoords;
	
	//the invisible cursor used for "hiding" the cursor
	private static final Cursor invisible_cursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null");
	static
	{
		//init the previous mouse location values
		prevCoords.set(getMouseX(), getMouseY());
	}
	
	@Override
	public void eventDispatched(AWTEvent e)
	{
		if(!listeners.isEmpty() && e instanceof MouseEvent)
		{
			//get current coords
			IntVector2D coords = IntVector2D.pool.get().set(((MouseEvent)e).getXOnScreen(), ((MouseEvent)e).getYOnScreen());
			//get button
			int button = ((MouseEvent)e).getButton();
			//get delta x and y values
			IntVector2D delta = IntVector2D.pool.get().set(coords).subtract(prevCoords);
			
			//if locked
			if(lock)
			{
				//if a robot move, ignore
				if(coords.equals(lockCoords))
				{
					prevCoords.set(coords);
					coords.dispose();
					delta.dispose();
					return;
				}
				//report old coords and move back
				coords.subtract(delta);
				InputAutomation.mouseMove(coords);
			}
			
			switch(e.getID())
			{
				case MouseEvent.MOUSE_PRESSED:
				{
					setButton(button, true);
					for(MListener lis : listeners)
					{
						lis.mousePress(coords, button);
					}
					break;
				}
				case MouseEvent.MOUSE_RELEASED:
				{
					setButton(button, false);
					for(MListener lis : listeners)
					{
						lis.mouseRelease(coords, button);
					}
					break;
				}
				case MouseEvent.MOUSE_MOVED:
				case MouseEvent.MOUSE_DRAGGED:
				{
					for(MListener lis : listeners)
					{
						lis.mouseMove(coords, delta);
					}
					break;
				}
			}
			
			prevCoords.set(coords);
			coords.dispose();
			delta.dispose();
		}
	}
	
	/**
	 * Sets the state of the given button.
	 * @param button The button.
	 * @param down True if pressed, else false.
	 */
	private void setButton(int button, boolean down)
	{
		if(down)
		{
			down_buttons.add(button);
		}else
		{
			down_buttons.remove(button);
		}
	}
	
	/**
	 * Represents a Mouse Listener.
	 */
	public interface MListener
	{
		/**
		 * Called when a mouse button is pressed.
		 * @param button The button pressed.
		 */
		public void mousePress(IntVector2D coords, int button);
		
		/**
		 * Called when a mouse button is released.
		 * @param button The button released.
		 */
		public void mouseRelease(IntVector2D coords, int button);
		
		/**
		 * Called when the mouse is moved.
		 * @param coords The new coords of the mouse.
		 * @param delta The distance moved.
		 */
		public void mouseMove(IntVector2D coords, IntVector2D delta);
	}
	
	/**
	 * Hides the cursor within the given frame.
	 * @param frame The frame.
	 */
	public static void hideCursor(JFrame frame)
	{
		frame.setCursor(invisible_cursor);
	}
	
	/**
	 * Sets the cursor to default within the given frame.
	 * @param frame The frame.
	 */
	public static void defaultCursor(JFrame frame)
	{
		frame.setCursor(Cursor.getDefaultCursor());
	}
	
	/**
	 * Locks the mouse at the current coords while preserving mouse events.
	 * @param l True to lock, false to unlock.
	 */
	public static void lockMouse(boolean l)
	{
		lock = l;
	}
	
	/**
	 * @return The x location of the mouse.
	 */
	public static int getMouseX()
	{
		return MouseInfo.getPointerInfo().getLocation().x;
	}
	
	/**
	 * @return The y location of the mouse.
	 */
	public static int getMouseY()
	{
		return MouseInfo.getPointerInfo().getLocation().y;
	}
	
	/**
	 * Returns the press state of the given button.
	 * @param button The button to check.
	 * @return True if pressed, else false.
	 */
	public static boolean buttonDown(int button)
	{
		return down_buttons.contains(button);
	}
}
