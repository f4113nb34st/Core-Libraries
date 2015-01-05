package input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import math.vector.IntVector2D;

/**
 * 
 * Provides easy to use input automation methods.
 * 
 * @author F4113nb34st
 *
 */
public final class InputAutomation
{
	private static Robot robot;
	static
	{
		//create the robot
		try
		{
			robot = new Robot();
		} catch(AWTException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException("Unsupported Input Automation Exception!");
		}
	}
	
	//suppress the default constructor
	private InputAutomation(){}
	
	/**
	 * Moves the mouse to the given coords.
	 * @param x The x coord.
	 * @param y The y coord.
	 */
	public static final void mouseMove(int x, int y)
	{
		robot.mouseMove(x, y);
	}
	
	/**
	 * Moves the mouse to the given coords.
	 * @param coords The coords.
	 */
	public static final void mouseMove(IntVector2D coords)
	{
		mouseMove(coords.x, coords.y);
	}
	
	/**
	 * Offsets the mouse by the given coords.
	 * @param x The x coord.
	 * @param y The y coord.
	 */
	public static final void mouseMoveDelta(int x, int y)
	{
		robot.mouseMove(Mouse.getMouseX() + x, Mouse.getMouseY() + y);
	}
	
	/**
	 * Offsets the mouse by the given coords.
	 * @param coords The coords.
	 */
	public static final void mouseMoveDelta(IntVector2D delta)
	{
		mouseMoveDelta(delta.x, delta.y);
	}
	
	/**
	 * Presses the given mouse button.
	 * @param button The button to press.
	 */
	public static final void mousePress(int button)
	{
		robot.mousePress(InputEvent.getMaskForButton(button));
	}
	
	/**
	 * Releases the given mouse button.
	 * @param button The button to release.
	 */
	public static final void mouseRelease(int button)
	{
		robot.mouseRelease(InputEvent.getMaskForButton(button));
	}
	
	/**
	 * Moves the mouse wheel.
	 * @param amount The amount to rotate the wheel.
	 */
	public static final void mouseWheel(int amount)
	{
		robot.mouseWheel(amount);
	}
	
	/**
	 * Click the given mouse button.
	 * @param button The button to click.
	 */
	public static final void mouseClick(int button)
	{
		mousePress(button);
		mouseRelease(button);
	}
	
	/**
	 * Click the given mouse button at the given coords.
	 * @param x The x coord.
	 * @param y The y coord.
	 * @param button The button to click.
	 */
	public static final void mouseClick(int x, int y, int button)
	{
		mouseMove(x, y);
		mouseClick(button);
	}
	
	/**
	 * Click the given mouse button at the given coords.
	 * @param coords The coords.
	 * @param button The button to click.
	 */
	public static final void mouseClick(IntVector2D coords, int button)
	{
		mouseMove(coords);
		mouseClick(button);
	}
	
	/**
	 * Presses the given key.
	 * @param key The key to press.
	 */
	public static final void keyPress(int button)
	{
		robot.keyPress(button);
	}
	
	/**
	 * Releases the given key.
	 * @param key The key to release.
	 */
	public static final void keyRelease(int button)
	{
		robot.keyRelease(button);
	}
	
	/**
	 * Types the given key.
	 * @param key The key to type.
	 */
	public static final void keyType(int button)
	{
		keyPress(button);
		keyRelease(button);
	}
}
