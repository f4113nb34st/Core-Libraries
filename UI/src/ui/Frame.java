package ui;

import input.Keyboard.KListener;
import input.Keyboard;
import input.Mouse;
import input.Mouse.MListener;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.TreeSet;
import render.image.Image;
import render.image.IntBackedImage;
import ui.component.Panel;
import ui.component.TextField;
import ui.component.scroll.ScrollPanelContent;

/**
 * 
 * A Frame is the root component of a UI system.
 * 
 * @author F4113nb34st
 *
 */
public class Frame extends Component implements MListener, KListener
{
	/**
	 * The canvas that stores the last render.
	 */
	public Image canvas;
	
	/**
	 * The x location of this Frame on the screen. Should be set every tick by some kind of manager.
	 */
	public int screenX = 0;
	
	/**
	 * The y location of this Frame on the screen. Should be set every tick by some kind of manager.
	 */
	public int screenY = 0;
	
	/**
	 * The currently focused component.
	 */
	public Component focused;
	
	/**
	 * The currently hovered component.
	 */
	public Component hovered;
	
	public Frame()
	{
		super();
		frame = this;
		//override locs and dims
		x = new FrameLoc();
		y = new FrameLoc();
		z = new FrameZ();
		width = new FrameDim();
		height = new FrameDim();
		//create a miniture canvas (to be resizes as nessessary at renderings)
		canvas = new IntBackedImage(1, 1);
		//add listeners for this frame
		Mouse.addListener(this);
		Keyboard.addListener(this);
	}
	
	public void dispose()
	{
		Mouse.removeListener(this);
		Keyboard.removeListener(this);
	}
	
	public void setScreen(Panel panel)
	{
		children.clear();
		focused = null;
		hovered = null;
		panel.setParent(this);
	}
	
	private long updateID = Long.MIN_VALUE;
	
	public void updateSizes()
	{
		//increment id for call
		updateID++;
		super.updateSizes(updateID);
		super.updateChildSizes(updateID);
	}
	
	private TreeSet<Component> renderList = new TreeSet<Component>(new Comparator<Component>()
	{
		@Override
		public int compare(Component c1, Component c2)
		{
			//concise method, but doesn't handle infinities
			//return (int)Math.signum(c1.z - c2.z);
			if(c1.z.get() > c2.z.get())
			{
				return 1;
			}else
			{
				return -1;
			}
		}
	});

	public void frameRender(Image image)
	{
		//resize the canvas, min of 1 by 1 size
		if(width.get() != image.getWidth() || height.get() != image.getHeight())
		{
			((FrameDim)width).value = image.getWidth();
			((FrameDim)height).value = image.getHeight();
			canvas.resize(width.get(), height.get());
		}
		//update our sizes
		updateSizes();
		//find all components to render
		sortAll(this, renderList);
		//draw the screen
		for(Component comp : renderList)
		{
			comp.baseRender(canvas);
		}
		//clear list for next frame
		renderList.clear();
		//draw on the given image
		image.drawImage(canvas, 0, 0);
	}
	
	private void sortAll(Component comp, TreeSet<Component> set)
	{
		if(comp.visible)
		{
			if(!comp.hidden)
			{
				set.add(comp);
			}
			for(Component child : comp.children)
			{
				sortAll(child, set);
			}
		}
	}
	
	@Override
	public void render(Image image)
	{
		image.fill(foreColor.getRed(), foreColor.getGreen(), foreColor.getBlue());
	}
	
	private TreeSet<Component> eventList = new TreeSet<Component>(new Comparator<Component>()
	{
		@Override
		public int compare(Component c1, Component c2)
		{
			//concise method, but doesn't handle infinities
			//return (int)Math.signum(c1.z - c2.z);
			if(c1.z.get() > c2.z.get())
			{
				return 1;
			}else
			{
				return -1;
			}
		}
	});
	
	public boolean frameEvent(Event e)
	{
		super.basePreEvent(e);
		if(hovered != null)
		{
			//if not in bounds
			if(!(e.x >= hovered.x.get() && e.y >= hovered.y.get() && e.x < hovered.x.get() + hovered.width.get() && e.y < hovered.y.get() + hovered.height.get()))
			{
				//call leave on the hovered
				if(hovered instanceof MouseListener)
				{
					((MouseListener)hovered).mouseLeave();
				}
				hovered = null;
			}
		}
		//find all components to render
		sortAll(this, eventList);
		boolean consumed = false;
		//draw the screen
		for(Component comp : eventList)
		{
			if(comp.baseProcess(e))
			{
				consumed = true;
				break;
			}
		}
		//clear list for next frame
		eventList.clear();
		if(!consumed && focused != null)
		{
			e.focused = true;
			focused.baseProcess(e);
		}
		super.basePostEvent(e);
		return false;
	}

	@Override
	public boolean mousePress(int x, int y, int button)
	{
		//add event
		Event e = new Event();
		e.mouse = true;
		e.x = x - screenX;
		e.y = y - screenY;
		e.button = button;
		e.down = true;
		return frameEvent(e);
	}

	@Override
	public boolean mouseRelease(int x, int y, int button)
	{
		//add event
		Event e = new Event();
		e.mouse = true;
		e.x = x - screenX;
		e.y = y - screenY;
		e.button = button;
		e.down = false;
		return frameEvent(e);
	}

	@Override
	public boolean mouseMove(int x, int y, int dx, int dy)
	{
		//add event
		Event e = new Event();
		e.mouse = true;
		e.x = x - screenX;
		e.y = y - screenY;
		e.dx = dx;
		e.dy = dy;
		e.button = -1;
		return frameEvent(e);
	}
	
	@Override
	public boolean keyPress(int button)
	{
		//add event
		Event e = new Event();
		e.mouse = false;
		e.x = Mouse.getMouseX() - screenX;
		e.y = Mouse.getMouseY() - screenY;
		e.button = button;
		e.down = true;
		return frameEvent(e);
	}
	
	@Override
	public boolean keyRelease(int button)
	{
		//add event
		Event e = new Event();
		e.mouse = false;
		e.x = Mouse.getMouseX() - screenX;
		e.y = Mouse.getMouseY() - screenY;
		e.button = button;
		e.down = false;
		return frameEvent(e);
	}
	
	@Override
	public boolean keyTyped(char c)
	{
		if(c == KeyEvent.CHAR_UNDEFINED)
		{
			return false;
		}
		//add event
		Event e = new Event();
		e.mouse = false;
		e.x = Mouse.getMouseX() - screenX;
		e.y = Mouse.getMouseY() - screenY;
		e.button = -1;
		e.down = true;
		e.typed = c;
		return frameEvent(e);
	}
	
	/**
	 * A editable UILocation for frames.
	 */
	private static class FrameLoc extends UILocation
	{
		public int value = 0;
		
		@Override
		public int get()
		{
			return value;
		}
		
		@Override
		public void update(long id)
		{
			//do nothing
		}
	}
	
	/**
	 * A editable UIZCoord for frames.
	 */
	private static class FrameZ extends UIZCoord
	{
		public double value = 0;
		
		@Override
		public double get()
		{
			return value;
		}
		
		@Override
		public void update(long id)
		{
			//do nothing
		}
	}
	
	/**
	 * A editable UIDimension for frames.
	 */
	private static class FrameDim extends UIDimension
	{
		public int value = 1;
		
		@Override
		public int get()
		{
			return value;
		}
		
		@Override
		public void update(long id)
		{
			//do nothing
		}
	}
}
