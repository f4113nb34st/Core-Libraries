package input;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 
 * A simple keyboard listener class that is not tied to a specific JFrame.
 * Performs a similar function to the Keyboard class of JOGL.
 * 
 * @author F4113nb34st
 *
 */
public final class Keyboard implements AWTEventListener
{
	static
	{
		//register the base event listener
		Toolkit.getDefaultToolkit().addAWTEventListener(new Keyboard(), AWTEvent.KEY_EVENT_MASK);
	}
	//list of KeyBoard listeners
	private static LinkedList<KListener> listeners = new LinkedList<KListener>();
	//set of currently-pressed buttons
	private static final HashSet<Integer> down_buttons = new HashSet<Integer>();
	
	//suppress the default constructor
	private Keyboard(){}
	
	/**
	 * Adds the given key listener.
	 * @param listener The listener.
	 */
	public static void addListener(KListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Removes the given key listener.
	 * @param listener The listener.
	 */
	public static void removeListener(KListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void eventDispatched(AWTEvent e)
	{
		if(!listeners.isEmpty() && e instanceof KeyEvent)
		{
			int button = ((KeyEvent)e).getKeyCode();
			
			if(e.getID() != KeyEvent.KEY_PRESSED && e.getID() != KeyEvent.KEY_RELEASED && e.getID() != KeyEvent.KEY_TYPED)
			{
				return;
			}
			
			switch(e.getID())
			{
				case KeyEvent.KEY_PRESSED:
				{
					setButton(button, true);
					for(KListener lis : listeners)
					{
						lis.keyPress(button);
					}
					break;
				}
				case KeyEvent.KEY_RELEASED:
				{
					setButton(button, false);
					for(KListener lis : listeners)
					{
						lis.keyRelease(button);
					}
					break;
				}
				case KeyEvent.KEY_TYPED:
				{
					for(KListener lis : listeners)
					{
						lis.keyTyped(((KeyEvent)e).getKeyChar());
					}
					break;
				}
			}
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
	 * Represents a Key Listener.
	 */
	public interface KListener
	{
		/**
		 * Called when a key is pressed.
		 * @param button The button pressed. See {@link java.awt.event.KeyEvent KeyEvent} for codes.
		 */
		public void keyPress(int button);
		
		/**
		 * Called when a key is released.
		 * @param button The button released. See {@link java.awt.event.KeyEvent KeyEvent} for codes.
		 */
		public void keyRelease(int button);
		
		/**
		 * Called when a key is typed. 
		 * Differs from KeyPress in that it takes into account key combinations such as "shift + a" -> 'A'
		 * @param c The char typed.
		 */
		public void keyTyped(char c);
	}
	
	/**
	 * Returns the press state of the given button.
	 * @param button The button to check. See {@link java.awt.event.KeyEvent KeyEvent} for codes.
	 * @return True if pressed, else false.
	 */
	public static boolean buttonDown(int button)
	{
		return down_buttons.contains(button);
	}
}
