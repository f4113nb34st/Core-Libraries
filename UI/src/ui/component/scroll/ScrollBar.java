package ui.component.scroll;

import ui.Component;
import ui.EventListener;
import ui.component.button.Button;
import util.Util;

/**
 * 
 * A scroll bar for a panel with scrolling.
 * 
 * @author F4113nb34st
 *
 */
public class ScrollBar extends Button implements EventListener
{
	//the parent scroll panel
	private ScrollPanel parent;
	//true if the vertical scroll bar
	private final boolean vert;
	//true if currently being dragged
	private boolean dragged = false;
	//the slide translation, similar to the content's origin, but not pixel specific and can go <0 and >1
	private double localOrigin;
	
	public ScrollBar(boolean v)
	{
		vert = v;
		width.ratio = 1;
		height.ratio = 1;
		//don't change colors on hover
		disableHoverColor = true;
		
		horiBorder.ratio = .25;
		vertBorder.ratio = .25;
		//square borders
		sharedBorder = true;
		
		//squared edges
		roundEdges = false;
	}
	
	public void setParent(Component p, boolean bypass)
	{
		super.setParent(p, bypass);
		parent = (ScrollPanel)super.parent;
		//be based on the window not the entire panel in case of a perpendicular scroll bar
		if(vert)
		{
			y.parentDim = parent.window.height;
			height.parentDim = parent.window.height;
		}else
		{
			x.parentDim = parent.window.width;
			width.parentDim = parent.window.width;
		}
	}
	
	/**
	 * Disables the scroll bar.
	 * @param h
	 */
	private void setDisabled(boolean h)
	{
		if(hidden != h)
		{
			hidden = h;
			dirty = true;
		}
	}
	
	/**
	 * Updates the sizes of this Component.
	 * @param id The update id.
	 */
	@Override
	public void updateSizes(long id)
	{
		double ratio;
		if(vert)
		{
			//height is the ratio of the window to the content
			height.ratio = parent.window.height.get() / (double)parent.content.height.get();
			//if content fits in window, disable scroll bar
			if(height.ratio > 1)
			{
				setDisabled(true);
				height.ratio = .25;
			}else
			{
				setDisabled(false);
			}
			//get the ratio of scrolling
			ratio = parent.content.ratioY();
			//smoothly interpolate between (0,min-justified) to (1,max-justified)
			y.ratio = ratio;
			y.offset = -ratio;
		}else//do the horizontal equivalent
		{
			width.ratio = parent.window.width.get() / (double)parent.content.width.get();
			if(width.ratio > 1)
			{
				setDisabled(true);
				width.ratio = .25;
			}else
			{
				setDisabled(false);
			}
			ratio = parent.content.ratioX();
			x.ratio = ratio;
			x.offset = -ratio;
		}
		super.updateSizes(id);
	}
	
	@Override
	public boolean mousePress(int x, int y, int button)
	{
		//if left click and not disabled
		if(button == 0 && !hidden)
		{
			//start dragging
			dragged = true;
			//sync local origin
			if(vert)
			{
				localOrigin = parent.content.ratioY();
			}else
			{
				localOrigin = parent.content.ratioX();
			}
		}
		return true;
	}

	@Override
	public boolean mouseRelease(int x, int y, int button)
	{
		return true;
	}
	
	public void preEvent(Event e)
	{
		//if a move event and we're dragged
		if(dragged && e.mouse && e.button == -1)
		{
			if(vert)
			{
				//move origin by the right amount
				localOrigin += e.dy / (double)(parent.window.height.get() - height.get());
				//update the content
				parent.content.setOriginY(localOrigin);
			}else//do horizontal equivalent
			{
				localOrigin += e.dx / (double)(parent.window.width.get() - width.get());
				parent.content.setOriginX(localOrigin);
			}
		}else//if a release event (we can't use mouseRelease, because we need a global release listener)
		if(dragged && e.mouse && e.button == 0 && !e.down)
		{
			//stop dragging
			dragged = false;
			//clip local origin
			localOrigin = Util.clip(localOrigin, 0, 1);
		}
	}

	@Override public void postEvent(Event e) {}
}
