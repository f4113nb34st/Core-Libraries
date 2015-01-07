package ui.component;

import core.Core;
import render.image.Image;
import ui.Component;
import ui.Font;
import ui.UIColor;

/**
 * 
 * A Label is a Component which contains text.
 * 
 * @author F4113nb34st
 *
 */
public class Label extends Component
{
	public enum Justify
	{
		MIN, MIDDLE, MAX, LEFT, RIGHT, TOP, BOTTOM;
	}
	/**
	 * The text to display.
	 */
	public String text = "";
	
	/**
	 * A char array used if we are a password.
	 */
	public char[] passwordArray = new char[0];
	
	/**
	 * The font to use for this Label.
	 */
	public Font font;
	
	/**
	 * The vertical justification method.
	 */
	public Justify verticalJustify = Justify.MIDDLE;
	
	/**
	 * The horizontal justification method.
	 */
	public Justify horizontalJustify = Justify.MIDDLE;
	
	/**
	 * True if this label should be dotted out.
	 */
	public boolean isPassword = false;
	
	/**
	 * Locks the font so it is not automatically resized.
	 */
	public boolean lockFont = false;
	
	/**
	 * The text color of this Label.
	 */
	public UIColor textColor = new UIColor();
	
	/**
	 * True to use a blinking underscore.
	 */
	public boolean useUnderScore = false;
	
	/**
	 * True if the underscore is currently visible.
	 */
	public boolean underscore = true;
	
	/**
	 * Ticks till a underscore toggle.
	 */
	public double underscoreTicker;
	
	public Label()
	{
		textColor.subscribers.add(this);
	}
	
	public Label(String txt)
	{
		this();
		text = txt;
	}
	
	/**
	 * Called every update.
	 */
	public void update()
	{
		super.update();
		if(useUnderScore && frame.focused == this)
		{
			underscoreTicker--;
			if(underscoreTicker <= 0)
			{
				underscoreTicker += Core.instance.tickRate / 2;
				dirty = true;
				underscore = !underscore;
			}
		}else
		{
			underscoreTicker = Core.instance.tickRate;
			if(underscore)
			{
				dirty = true;
			}
			underscore = false;
		}
	}
	
	/**
	 * Updates the sizes of this Component.
	 * @param id The update id.
	 */
	public void updateSizes(long id)
	{
		super.updateSizes(id);
		textColor.update(id);
	}
	
	@Override
	public void render(Image canvas)
	{
		canvas.fill(foreColor.getRed(), foreColor.getGreen(), foreColor.getBlue());
		
		int length = isPassword ? passwordArray.length : text.length();
		if(useUnderScore)
		{
			length++;
		}
		if(length == 0)
		{
			return;
		}
		int idealHeight = Math.min(canvas.getWidth() * 2 / length, canvas.getHeight());
		idealHeight = Math.max(idealHeight, 18);
		if(!lockFont && (font == null || font.getHeight() != idealHeight))
		{
			font = new Font(idealHeight);
		}
		double textRed = textColor.get().x;
		double textGreen = textColor.get().y;
		double textBlue = textColor.get().z;
		int startX = 0;
		int startY = 0;
		switch(horizontalJustify)
		{
			case MIN:
			case LEFT:
			case TOP:
				startX = 0;
				break;
			case MIDDLE:
				startX = (canvas.getWidth() / 2) - (font.getWidth() * length / 2);
				break;
			case MAX:
			case RIGHT:
			case BOTTOM:
				startX = canvas.getWidth() - (font.getWidth() * length);
				break;
		}
		switch(verticalJustify)
		{
			case MIN:
			case LEFT:
			case TOP:
				startY = 0;
				break;
			case MIDDLE:
				startY = (canvas.getHeight() / 2) - (font.getHeight() / 2);
				break;
			case MAX:
			case RIGHT:
			case BOTTOM:
				startY = canvas.getHeight() - font.getHeight();
				break;
		}
		if(!isPassword)
		{
			if(text.length() > 0)
			{
				font.draw(text, startX, startY, canvas, textRed, textGreen, textBlue);
			}
		}else
		{
			if(passwordArray.length > 0)
			{
				font.drawPassword(passwordArray, startX, startY, canvas, textRed, textGreen, textBlue);
			}
		}
		if(useUnderScore && underscore)
		{
			font.draw('_', startX + (font.getWidth() * (length - 1)), startY, canvas, textRed, textGreen, textBlue);
		}
	}
}
