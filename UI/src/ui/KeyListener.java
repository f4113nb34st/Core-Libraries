package ui;

/**
 * 
 * Interface for Components that listen for key events.
 * 
 * @author F4113nb34st
 *
 */
public interface KeyListener
{
	/**
	 * Called when a key press event occurs.
	 * @param button The button pressed. (Use java.awt.KeyEvent for the key codes)
	 * @return True if the event should be consumed.
	 */
	public boolean keyPress(int button);
	
	/**
	 * Called when a key release event occurs.
	 * @param button The button released. (Use java.awt.KeyEvent for the key codes)
	 * @return True if the event should be consumed.
	 */
	public boolean keyRelease(int button);
	
	/**
	 * Called when a key typed event occurs.
	 * @param c The char typed.
	 * @return True if the event should be consumed.
	 */
	public boolean keyTyped(char c);
	
	/**
	 * @return The key ListenerType.
	 */
	public ListenerType keyListenerType();
}
