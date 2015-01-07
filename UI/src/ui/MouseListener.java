package ui;

/**
 * 
 * Interface for Components that listen for mouse events.
 * 
 * @author F4113nb34st
 *
 */
public interface MouseListener
{
	/**
	 * Called when a mouse press event occurs.
	 * @param x The x location of the mouse at the time of the event.
	 * @param y The y location of the mouse at the time of the event.
	 * @param button The button pressed.
	 * @return True if the event should be consumed.
	 */
	public boolean mousePress(int x, int y, int button);
	
	/**
	 * Called when a mouse release event occurs.
	 * @param x The x location of the mouse at the time of the event.
	 * @param y The y location of the mouse at the time of the event.
	 * @param button The button released.
	 * @return True if the event should be consumed.
	 */
	public boolean mouseRelease(int x, int y, int button);
	
	/**
	 * Called when a mouse move event occurs.
	 * @param x The x location of the mouse at the time of the event.
	 * @param y The y location of the mouse at the time of the event.
	 * @param dx The change in x.
	 * @param dy The change in y.
	 * @return True if the event should be consumed.
	 */
	public boolean mouseMove(int x, int y, int dx, int dy);
	
	/**
	 * Called when the mouse enters this Component.
	 */
	public void mouseEnter();
	
	/**
	 * Called when the mouse leaves this Component.
	 */
	public void mouseLeave();
	
	/**
	 * @return The mouse ListenerType.
	 */
	public ListenerType mouseListenerType();
}
