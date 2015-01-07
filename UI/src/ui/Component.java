package ui;

import render.image.Image;
import render.image.IntBackedImage;
import util.collection.LinkedGroup;

/**
 * 
 * A Component is a part of a gui. This can be anything from a button to a panel to a label.
 * 
 * @author F4113nb34st
 *
 */
public abstract class Component
{
	/**
	 * The root frame.
	 */
	public Frame frame;
	
	/**
	 * The parent component.
	 */
	public Component parent;
	
	/**
	 * The child component list.
	 */
	public LinkedGroup<Component> children = new LinkedGroup<Component>();
	
	/**
	 * The x coord of this Component relative to its parent.
	 */
	public UILocation x = new UILocation();
	
	/**
	 * The x coord of this Component relative to its parent.
	 */
	public UILocation y = new UILocation();
	
	/**
	 * The z coord of this Component. Indicates rendering order. By default 1 greater than its parent.
	 */
	public UIZCoord z = new UIZCoord();
	
	/**
	 * The width of this Component.
	 */
	public UIDimension width = new UIDimension();
	
	/**
	 * The height of this Component.
	 */
	public UIDimension height = new UIDimension();
	
	/**
	 * The clipping bounds of the Component.
	 */
	public UIClip clip = new UIClip();
	
	/**
	 * True if this Component is to be rendered.
	 */
	public boolean visible = true;
	
	/**
	 * True if this Component should not be rendered but its children should.
	 */
	public boolean hidden = false;
	
	/**
	 * The "primary" or "foreground" color of this Component. Exact usage may vary by component type.
	 */
	public UIColor foreColor = new UIColor();
	
	/**
	 * If a Component is dirty, it will be redrawn next frame.
	 */
	public boolean dirty = false;
	
	/**
	 * A "snap shot" of this component, re-rendered whenever dirty.
	 */
	public IntBackedImage rendering = new IntBackedImage(1, 1);
	
	/**
	 * True if this Component can be a hover target.
	 */
	public boolean hoverable = false;
	
	public Component()
	{
		foreColor.subscribers.add(this);
	}
	
	/**
	 * Returns the parent that should be used for the given child. Used by components like buttons to hand parentage off to their content panels.
	 * @param child The child component.
	 * @return The parent to use. Frequently this.
	 */
	public Component getParentFor(Component child)
	{
		return this;
	}
	
	/**
	 * Sets the parent of this Component.
	 * @param p The parent component.
	 */
	public void setParent(Component p)
	{
		setParent(p, false);
	}
	
	/**
	 * Sets the parent of this Component. Allows parentage allocation bypassing.
	 * @param p The parent component.
	 * @param bypass True to bypass parentage allocation.
	 */
	public void setParent(Component p, boolean bypass)
	{
		//find our parent
		if(p != null && !bypass)
		{
			p = p.getParentFor(this);
		}
		//remove from old parent if he exists
		if(parent != null)
		{
			parent.children.remove(this);
		}
		parent = p;
		x.dimension = width;
		y.dimension = height;
		//add to new parent if he exists
		if(parent != null)
		{
			setFrame(parent.frame);
			parent.children.add(this);
			
			//set parent dims and locs
			x.parentLoc = parent.x;
			x.parentDim = parent.width;
			y.parentLoc = parent.y;
			y.parentDim = parent.height;
			z.parentZ = parent.z;
			width.parentDim = parent.width;
			height.parentDim = parent.height;
			clip.parentClip = parent.clip;
		}else
		{
			//set parent dims and locs
			x.parentLoc = null;
			x.parentDim = null;
			y.parentLoc = null;
			y.parentDim = null;
			z.parentZ = null;
			width.parentDim = null;
			height.parentDim = null;
			clip.parentClip = null;
		}
	}
	
	public void setFrame(Frame f)
	{
		frame = f;
		for(Component child : children)
		{
			child.setFrame(f);
		}
	}
	
	/**
	 * Called every update.
	 */
	public void update()
	{
		for(Component child : children)
		{
			child.update();
		}
	}
	
	/**
	 * Updates the sizes of this Component.
	 * @param id The update id.
	 */
	public void updateSizes(long id)
	{
		x.update(id);
		y.update(id);
		z.update(id);
		width.update(id);
		height.update(id);
		clip.update(id);
		foreColor.update(id);
	}
	
	/**
	 * Updates the children's sizes of this Component.
	 * @param id The update id.
	 */
	public void updateChildSizes(long id)
	{
		for(Component child : children)
		{
			if(child.visible)
			{
				child.updateSizes(id);
				child.updateChildSizes(id);
			}
		}
	}
	
	/**
	 * Renders this Component.
	 * @param image The image to render to.
	 */
	public abstract void render(Image image);
	
	/**
	 * Renders this Component and kids. For most cases, use render(Image).
	 * @param image The image to render to.
	 */
	public void baseRender(Image image)
	{
		if(rendering.getWidth() != width.get() || rendering.getHeight() != height.get())
		{
			dirty = true;
			rendering.resize(Math.max(1, width.get()), Math.max(1, height.get()));
		}
		if(dirty)
		{
			dirty = false;
			render(rendering);
		}
		
		image.setClip(clip.getMinX(), clip.getMinY(), clip.getMaxX() - clip.getMinX(), clip.getMaxY() - clip.getMinY());
		image.drawImage(rendering, x.get(), y.get());
		image.clearClip();
	}
	
	/**
	 * Stores data about a mouse or key event
	 */
	protected static class Event
	{
		/**
		 * True if this is a mouse event.
		 */
		public boolean mouse;
		/**
		 * The x coord of the mouse at the time of the event.
		 */
		public int x;
		/**
		 * The y coord of the mouse at the time of the event.
		 */
		public int y;
		/**
		 * The dx of the mouse.
		 */
		public int dx;
		/**
		 * The dy of the mouse.
		 */
		public int dy;
		/**
		 * The mouse button or key pressed.
		 */
		public int button;
		/**
		 * True if this is a 'press' event. False for 'release' events or NA.
		 */
		public boolean down;
		/**
		 * The typed char for key typed events.
		 */
		public char typed;
		/**
		 * True if this is the last call for this event.
		 */
		public boolean focused = false;
	}
	
	protected void basePreEvent(Event e)
	{
		if(this instanceof EventListener)
		{
			((EventListener)this).preEvent(e);
		}
		for(Component child : children)
		{
			child.basePreEvent(e);
		}
	}
	
	protected void basePostEvent(Event e)
	{
		if(this instanceof EventListener)
		{
			((EventListener)this).postEvent(e);
		}
		for(Component child : children)
		{
			child.basePostEvent(e);
		}
	}
	
	protected boolean baseProcess(Event e)
	{
		//true if we are within bounds
		boolean inBounds = e.x >= x.get() && e.y >= y.get() && e.x < x.get() + width.get() && e.y < y.get() + height.get();
		if(e.focused)
		{
			inBounds = true;
		}
		if(e.mouse)
		{
			//true if a global listener
			boolean global = false;
			if(this instanceof MouseListener)
			{
				global = ((MouseListener)this).mouseListenerType() == ListenerType.GLOBAL;
			}
			//if a move event and we should be hovered
			if(hoverable && e.button == -1 && inBounds && frame.hovered != this)
			{
				//call leave on the prev hovered
				if(frame.hovered instanceof MouseListener)
				{
					((MouseListener)frame.hovered).mouseLeave();
				}
				//make us hovered
				frame.hovered = this;
				if(this instanceof MouseListener)
				{
					((MouseListener)this).mouseEnter();
				}
			}
			if(this instanceof MouseListener)
			{
				//if global or in the bounds
				if(global || inBounds)
				{
					return processMouse(e);
				}
			}
		}else
		{
			if(this instanceof KeyListener)
			{
				//if global or in the bounds
				if(((KeyListener)this).keyListenerType() == ListenerType.GLOBAL || inBounds)
				{
					return processKey(e);
				}
			}
		}
		return false;
	}
	
	protected boolean processMouse(Event e)
	{
		MouseListener ml = (MouseListener)this;
		//if a move event
		if(e.button == -1)
		{
			//call move
			if(ml.mouseMove(e.x, e.y, e.dx, e.dy))
			{
				return true;
			}
		}else//else a press or release
		{
			//if a press
			if(e.down)
			{
				//call press
				if(ml.mousePress(e.x, e.y, e.button))
				{
					//clear focused
					frame.focused = null;
					//if focusable
					if(ml.mouseListenerType() == ListenerType.FOCUS || (this instanceof KeyListener && ((KeyListener)this).keyListenerType() == ListenerType.FOCUS))
					{
						//set as focus
						frame.focused = this;
					}
					return true;
				}
			}else//if a release
			{
				//call release
				if(ml.mouseRelease(e.x, e.y, e.button))
				{
					return true;
				}
			}
		}	
		return false;
	}
	
	protected boolean processKey(Event e)
	{
		KeyListener kl = (KeyListener)this;
		//if a typed event
		if(e.button == -1)
		{
			//call press
			if(kl.keyTyped(e.typed))
			{
				//clear focused
				frame.focused = null;
				//if focusable
				if((this instanceof MouseListener && ((MouseListener)this).mouseListenerType() == ListenerType.FOCUS) || kl.keyListenerType() == ListenerType.FOCUS)
				{
					//set as focus
					frame.focused = this;
				}
				return true;
			}
		}else
		{
			//if a press event
			if(e.down)
			{
				//call press
				if(kl.keyPress(e.button))
				{
					return true;
				}
			}else//if a release event
			{
				//call release
				if(kl.keyRelease(e.button))
				{
					return true;
				}
			}
		}
		return false;
	}
}
