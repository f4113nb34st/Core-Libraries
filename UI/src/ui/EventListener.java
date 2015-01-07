package ui;

import ui.Component.Event;

/**
 * 
 * A listener for all events that processes events.
 * 
 * @author F4113nb34st
 *
 */
public interface EventListener
{
	/**
	 * Called on all EventListeners before an event is processed.
	 */
	public void preEvent(Event e);
	
	/**
	 * Called on all EventListeners after an event is processed.
	 */
	public void postEvent(Event e);
}
