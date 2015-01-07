package ui.component.scroll;

import ui.Component;
import ui.component.Panel;
import util.Util;

/**
 * 
 * The content panel for a scroll panel.
 * 
 * @author F4113nb34st
 *
 */
public class ScrollPanelContent extends Panel
{
	//the scroll panel parent
	private ScrollPanel parent;
	//the origin vars
	public int originX = 0;
	public int originY = 0;
	//the origin bounds
	public int minOriginX;
	public int minOriginY;
	public int maxOriginX;
	public int maxOriginY;
	
	public void setParent(Component p, boolean bypass)
	{
		super.setParent(p, bypass);
		parent = (ScrollPanel)super.parent;
	}
	
	public double ratioX()
	{
		if(maxOriginX == minOriginX)
		{
			return 0;
		}
		return (originX - minOriginX) / (double)(maxOriginX - minOriginX);
	}
	
	public double ratioY()
	{
		if(maxOriginY == minOriginY)
		{
			return 0;
		}
		return (originY - minOriginY) / (double)(maxOriginY - minOriginY);
	}
	
	public void setOriginX(double x)
	{
		x = Util.clip(x, 0, 1);
		originX =  minOriginX + (int)(x * (maxOriginX - minOriginX));
	}
	
	public void setOriginY(double y)
	{
		y = Util.clip(y, 0, 1);
		originY = minOriginY + (int)(y * (maxOriginY - minOriginY));
	}
	
	public void updateSizes(long id)
	{
		super.updateSizes(id);
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		int cx;
		int cy;
		for(Component child : children)
		{
			cx = child.x.get() - x.get();
			cy = child.y.get() - y.get();
			minX = Math.min(minX, cx);
			minY = Math.min(minY, cy);
			maxX = Math.max(maxX, cx + child.width.get());
			maxY = Math.max(maxY, cy + child.height.get());
		}
		
		if(parent.hasHoriBar())
		{
			minOriginX = minX;
			maxOriginX = maxX - parent.window.width.get();
			if(minOriginX > maxOriginX)
			{
				maxOriginX = minOriginX;
			}
			originX = Util.clip(originX, minOriginX, maxOriginX);
			x.pixels = -originX;
		}
		if(parent.hasVertBar())
		{
			minOriginY = minY;
			maxOriginY = maxY - parent.window.height.get();
			if(minOriginY > maxOriginY)
			{
				maxOriginY = minOriginY;
			}
			originY = Util.clip(originY, minOriginY, maxOriginY);
			y.pixels = -originY;
		}
		
		width.pixels = maxX - minX;
		height.pixels = maxY - minY;
		
		clip.minX = parent.window.x.get();
		clip.minY = parent.window.y.get();
		clip.maxX = clip.minX + parent.window.width.get();
		clip.maxY = clip.minY + parent.window.height.get();
	}
}
