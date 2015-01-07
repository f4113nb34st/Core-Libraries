package ui;

public enum ListenerType
{
	/**
	 * A standard listener type.
	 */
	STANDARD, 
	/**
	 * Collects events even if they are outside of bounds.
	 */
	GLOBAL, 
	/**
	 * Keeps focus after initial event if there is nothing better.
	 */
	FOCUS;
}
