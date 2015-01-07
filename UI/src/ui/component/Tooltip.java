package ui.component;

import ui.Component;
import ui.EventListener;
import ui.UIDimension;
import ui.component.Label;

/**
 * 
 * A tooltip is a piece of text that pops up when it's parent is hovered over.
 * 
 * @author F4113nb34st
 *
 */
public class Tooltip extends Label implements EventListener
{
	/**
	 * The size of the text for this tooltip.
	 */
	public int size;
	
	/**
	 * The number of ticks that must pass for the tooltip to be displayed.
	 */
	public int hoverTicks = 30;
	
	/**
	 * The countdown ticker.
	 */
	private int ticker = 0;
	
	public Tooltip()
	{
		this("");
	}
	
	public Tooltip(String txt)
	{
		super(txt);
		visible = false;
		z.offset = Double.POSITIVE_INFINITY;//always display on top
		
		width = new UIDimension()
		{
			@Override
			public synchronized int get()
			{
				return text.length() * Math.max(size, 18) / 2;
			}
		};
		height = new UIDimension()
		{
			@Override
			public synchronized int get()
			{
				return Math.max(size, 18);
			}
		};
	}
	
	@Override
	public void setParent(Component p, boolean bypass)
	{
		super.setParent(p, bypass);
		if(parent != null)
		{
			parent.hoverable = true;
		}
	}
	
	@Override
	public void update()
	{
		super.update();
		if(ticker > 0)//if ticker time left
		{
			ticker--;
			if(ticker == 0)//if done set visible
			{
				visible = true;
			}
		}
	}

	@Override
	public void preEvent(Event e)
	{
		//set to cursor loc + 16
		if(e.x + 16 + width.get() < frame.width.get())
		{
			x.offset = 0;
			x.pixels = e.x - parent.x.get() + 16;
		}else
		{
			x.offset = -1;
			x.pixels = e.x - parent.x.get();
		}
		y.pixels = e.y - parent.y.get() + 16;
		x.update(-1);
		y.update(-1);
	}

	@Override
	public void postEvent(Event e)
	{
		if(frame.hovered == parent)//is parent is hovered
		{
			if(ticker == -1)//and ticker not yet begun
			{
				ticker = hoverTicks;//begin ticker
			}
		}else//if parent not hovered
		{
			ticker = -1;//cancel ticker
			visible = false;
		}
	}
}
