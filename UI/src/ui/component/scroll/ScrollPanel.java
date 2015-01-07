package ui.component.scroll;

import ui.Component;
import ui.UIDimension;
import ui.component.Panel;

/**
 * 
 * A panel that allows the use of scroll bars both vertically and horizontally.
 * 
 * @author F4113nb34st
 *
 */
public class ScrollPanel extends Panel
{
	/**
	 * The panel that represents the visible portion of the content panel.
	 */
	public Panel window;
	
	/**
	 * The vertical scroll bar. May be null if disable in the constructor.
	 */
	public ScrollBar vertBar;
	
	/**
	 * The horizontal scroll bar. May be null if disable in the constructor.
	 */
	public ScrollBar horiBar;
	
	/**
	 * The panel that hold the content of the panel.
	 */
	public ScrollPanelContent content;
	
	/**
	 * Creates a scroll panel with scroll bars.
	 * @param useVertBar True to use the vertical bar.
	 * @param useHoriBar True to use the horizontal bar.
	 */
	public ScrollPanel(boolean useVertBar, boolean useHoriBar)
	{
		//create the window
		window = new Panel();
		window.hidden = true;
		//adjust sizes to accomodate the scroll bars
		window.width = new UIDimension()
		{
			public double filterValue(double val, long id)
			{
				if(vertBar != null)
				{
					val -= vertBar.width.get();
				}
				return val;
			}
		};
		window.height = new UIDimension()
		{
			public double filterValue(double val, long id)
			{
				if(horiBar != null)
				{
					val -= horiBar.width.get();
				}
				return val;
			}
		};
		window.width.ratio = 1;
		window.height.ratio = 1;
		window.setParent(this, true);
		
		if(useVertBar)
		{
			vertBar = new ScrollBar(true);
			vertBar.width.ratio = .1;
			//right justify
			vertBar.x.ratio = 1;
			vertBar.x.offset = -1;
			vertBar.setParent(this, true);
			//add the colored backdrop
			Panel backdrop = new Panel();
			backdrop.z.offset = -.5;
			backdrop.x.ratio = 1;
			backdrop.x.offset = -1;
			backdrop.width.ratio = 1;
			backdrop.height.ratio = 1;
			backdrop.foreColor.addParent(vertBar.backColor, 1);
			backdrop.setParent(vertBar);
			backdrop.x.parentLoc = x;
			backdrop.x.parentDim = width;
			backdrop.y.parentLoc = y;
			backdrop.y.parentDim = window.height;
			backdrop.width.parentDim = vertBar.width;
			backdrop.height.parentDim = window.height;
		}
		if(useHoriBar)
		{
			horiBar = new ScrollBar(false);
			horiBar.height.ratio = .1;
			//bottom justify
			horiBar.y.ratio = 1;
			horiBar.y.offset = -1;
			horiBar.setParent(this, true);
			//add the colored backdrop
			Panel backdrop = new Panel();
			backdrop.z.offset = -.5;
			backdrop.y.ratio = 1;
			backdrop.y.offset = -1;
			backdrop.width.ratio = 1;
			backdrop.height.ratio = 1;
			backdrop.foreColor.addParent(horiBar.backColor, 1);
			backdrop.setParent(horiBar);
			backdrop.x.parentLoc = x;
			backdrop.x.parentDim = window.width;
			backdrop.y.parentLoc = y;
			backdrop.y.parentDim = height;
			backdrop.width.parentDim = window.width;
		}
		
		//create the content panel
		content = new ScrollPanelContent();
		content.hidden = true;
		content.setParent(this, true);
	}
	
	public Component getParentFor(Component child)
	{
		return content;
	}
	
	public boolean hasVertBar()
	{
		return vertBar != null;
	}
	
	public boolean hasHoriBar()
	{
		return horiBar != null;
	}
}
