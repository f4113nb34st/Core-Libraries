package ui.component.button;

import render.image.Image;
import ui.Component;
import ui.ListenerType;
import ui.MouseListener;
import ui.UIColor;
import ui.UIDimension;
import ui.component.Panel;
import ui.component.Tooltip;
import util.DefaultInterpolations;
import util.Interpolation;
import util.Util;

/**
 * 
 * A Button is a component that can be pressed.
 * 
 * @author F4113nb34st
 *
 */
public class Button extends Component implements MouseListener
{
	/**
	 * The horizontal border of this Button.
	 */
	public UIDimension horiBorder = new UIDimension();
	
	/**
	 * The vertical border of this Button.
	 */
	public UIDimension vertBorder = new UIDimension();
	
	/**
	 * The panel for the contents of this button.
	 */
	public Panel content;
	
	/**
	 * The background color of this Button.
	 */
	public UIColor backColor = new UIColor();
	
	/**
	 * The hovered foreground color of this Button.
	 */
	public UIColor hoverColor = new UIColor();
	
	/**
	 * The interpolation function for the edges.
	 */
	public Interpolation edgeInterp = DefaultInterpolations.LINEAR;
	
	/**
	 * True if the edges should be rounded, not square.
	 */
	public boolean roundEdges = true;
	
	/**
	 * True to disable the color change on hovering.
	 */
	public boolean disableHoverColor = false;
	
	/**
	 * True if this button is hovered over.
	 */
	public boolean hovered = false;
	
	/**
	 * The tooltip for this button. By default null.
	 */
	public Tooltip tooltip;
	
	/**
	 * True if the borders are clipped to the same size.
	 */
	public boolean sharedBorder = false;
	
	/**
	 * True to use the max border when shared instead of min.
	 */
	public boolean maxBorder = false;
	
	/**
	 * True if the button's size is dependent upon the content.
	 */
	private boolean reactiveSize = false;
	
	public Button()
	{
		hoverable = true;
		
		width = new CustomDimension(true, true);
		height = new CustomDimension(false, true);
		horiBorder.ratio = .05;
		vertBorder.ratio = .05;
		
		backColor.subscribers.add(this);
		hoverColor.subscribers.add(this);
		hoverColor.addParent(backColor, .5);
		hoverColor.addParent(foreColor, .5);
		
		content = new Panel();
		content.x.offset = -.5;
		content.y.offset = -.5;
		content.x.ratio = .5;
		content.y.ratio = .5;
		content.z.offset = .001;
		content.width = new CustomDimension(true, false);
		content.height = new CustomDimension(false, false);
		content.foreColor.addParent(foreColor, 1);
		content.hidden = true;
		content.setParent(this, true);
	}
	
	/**
	 * Sets whether this button uses reactive size.
	 * @param reactive
	 */
	public void setReactiveSize(boolean reactive)
	{
		//only do this stuff after we've been parented
		if(parent != null)
		{
			if(reactive)
			{
				content.width.parentDim = parent.width;
				content.height.parentDim = parent.height;
				horiBorder.parentDim = content.width;
				vertBorder.parentDim = content.height;
				width.parentDim = content.width;
				height.parentDim = content.height;
				width.ratio = 1;
				height.ratio = 1;
			}else
			{
				width.parentDim = parent.width;
				height.parentDim = parent.height;
				horiBorder.parentDim = width;
				vertBorder.parentDim = height;
				content.width.parentDim = width;
				content.height.parentDim = height;
				content.width.ratio = 1;
				content.height.ratio = 1;
			}
		}
		reactiveSize = reactive;
	}
	
	@Override
	public Component getParentFor(Component child)
	{
		return content;
	}
	
	@Override
	public void setParent(Component p, boolean bypass)
	{
		super.setParent(p, bypass);
		//update stuff by reactiveSize
		setReactiveSize(reactiveSize);
	}
	
	@Override
	public void mouseEnter()
	{
		if(!disableHoverColor)
		{
			dirty = true;
			hovered = true;
			content.foreColor.clearParents();
			content.foreColor.addParent(hoverColor, 1);
		}
	}
	
	@Override
	public void mouseLeave()
	{
		if(!disableHoverColor)
		{
			dirty = true;
			hovered = false;
			content.foreColor.clearParents();
			content.foreColor.addParent(foreColor, 1);
		}
	}
	
	/**
	 * Updates the sizes of this Component.
	 * @param id The update id.
	 */
	@Override
	public void updateSizes(long id)
	{
		super.updateSizes(id);
		horiBorder.update(id);
		vertBorder.update(id);
		backColor.update(id);
		hoverColor.update(id);
	}

	@Override
	public void render(Image image)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		int horiBorder = this.horiBorder.get();
		int vertBorder = this.vertBorder.get();
		UIColor color = hovered ? hoverColor : foreColor;
		
		horiBorder = Math.min(horiBorder, width / 2);
		vertBorder = Math.min(vertBorder, height / 2);
		
		if(sharedBorder)
		{
			int used;
			if(maxBorder) used = Math.max(horiBorder, vertBorder);
			else used = Math.min(horiBorder, vertBorder);
			horiBorder = vertBorder = used;
		}
		
		if(horiBorder > 0 && vertBorder > 0)
		{
			//for top left corner
			fillCorner(image, false, false, 0, 0, horiBorder, vertBorder, horiBorder, vertBorder, edgeInterp, color);
			
			//for top right corner
			fillCorner(image, true, false, width - horiBorder, 0, width, vertBorder, horiBorder, vertBorder, edgeInterp, color);
			
			//for bottom left corner
			fillCorner(image, false, true, 0, height - horiBorder, horiBorder, height, horiBorder, vertBorder, edgeInterp, color);
			
			//for bottom right corner
			fillCorner(image, true, true, width - horiBorder, height - horiBorder, width, height, horiBorder, vertBorder, edgeInterp, color);
		}
		if(vertBorder > 0)
		{
			//for top side
			fillYSide(image, false, horiBorder, 0, width - horiBorder, vertBorder, vertBorder, edgeInterp, color);
			
			//for bottom side
			fillYSide(image, true, horiBorder, height - vertBorder, width - horiBorder, height, vertBorder, edgeInterp, color);
		}
		if(horiBorder > 0)
		{
			//for left side
			fillXSide(image, false, 0, vertBorder, horiBorder, height - vertBorder, horiBorder, edgeInterp, color);
					
			//for right side
			fillXSide(image, true, width - horiBorder, vertBorder, width, height - vertBorder, horiBorder, edgeInterp, color);
		}
		
		//for center
		image.fillRect(horiBorder, vertBorder, width - horiBorder * 2, height - vertBorder * 2, color.getRed(), color.getGreen(), color.getBlue(), false);
	}
	
	private void fillXSide(Image image, boolean top, int minX, int minY, int maxX, int maxY, int horiBorder, Interpolation edgeInterp, UIColor color)
	{
		for(int i = minX; i < maxX; i++)
		{
			double mu = (maxX - i) / (double)horiBorder;
			if(top) mu = 1 - mu;
			mu = Util.clip(mu, 0, 1);
			double edgeRed = edgeInterp.interpolate(color.get().x, backColor.get().x, mu);
			double edgeGreen = edgeInterp.interpolate(color.get().y, backColor.get().y, mu);
			double edgeBlue = edgeInterp.interpolate(color.get().z, backColor.get().z, mu);
			double mu2 = Util.clip(Math.abs(.5 - mu) * 2, 0, 1);
			if(!top)
			{
				edgeRed *= 1 + (.05 * mu2);
				edgeGreen *= 1 + (.05 * mu2);
				edgeBlue *= 1 + (.05 * mu2);
			}else
			{
				edgeRed *= 1 - (.05 * mu2);
				edgeGreen *= 1 - (.05 * mu2);
				edgeBlue *= 1 - (.05 * mu2);
			}
			image.fillYScan(i, minY, maxY, (int)(Util.clip(edgeRed, 0, 1) * 0xFF), (int)(Util.clip(edgeGreen, 0, 1) * 0xFF), (int)(Util.clip(edgeBlue, 0, 1) * 0xFF));
		}
	}
	
	private void fillYSide(Image image, boolean top, int minX, int minY, int maxX, int maxY, int vertBorder, Interpolation edgeInterp, UIColor color)
	{
		for(int j = minY; j < maxY; j++)
		{
			double mu = (maxY - j) / (double)vertBorder;
			if(top) mu = 1 - mu;
			mu = Util.clip(mu, 0, 1);
			double edgeRed = edgeInterp.interpolate(color.get().x, backColor.get().x, mu);
			double edgeGreen = edgeInterp.interpolate(color.get().y, backColor.get().y, mu);
			double edgeBlue = edgeInterp.interpolate(color.get().z, backColor.get().z, mu);
			double mu2 = Util.clip(Math.abs(.5 - mu) * 2, 0, 1);
			if(!top)
			{
				edgeRed *= 1 + (.05 * mu2);
				edgeGreen *= 1 + (.05 * mu2);
				edgeBlue *= 1 + (.05 * mu2);
			}else
			{
				edgeRed *= 1 - (.05 * mu2);
				edgeGreen *= 1 - (.05 * mu2);
				edgeBlue *= 1 - (.05 * mu2);
			}
			image.fillXScan(minX, maxX, j, (int)(Util.clip(edgeRed, 0, 1) * 0xFF), (int)(Util.clip(edgeGreen, 0, 1) * 0xFF), (int)(Util.clip(edgeBlue, 0, 1) * 0xFF));
		}
	}
	
	private void fillCorner(Image image, boolean topX, boolean topY, int minX, int minY, int maxX, int maxY, int horiBorder, int vertBorder, Interpolation edgeInterp, UIColor color)
	{
		for(int i = minX; i < maxX; i++)
		{
			for(int j = minY; j < maxY; j++)
			{
				double dx = (maxX - i) / (double)horiBorder;
				if(topX) dx = 1 - dx;
				double dy = (maxY - j) / (double)vertBorder;
				if(topY) dy = 1 - dy;
				double mu;
				if(roundEdges)
				{
					mu = Math.sqrt(dx * dx + dy * dy);
				}else
				{
					mu = Math.max(Math.abs(dx), Math.abs(dy));
				}
				boolean override = mu > 1;
				mu = Util.clip(mu, 0, 1);
				double edgeRed = edgeInterp.interpolate(color.get().x, backColor.get().x, mu);
				double edgeGreen = edgeInterp.interpolate(color.get().y, backColor.get().y, mu);
				double edgeBlue = edgeInterp.interpolate(color.get().z, backColor.get().z, mu);
				if(!override)
				{
					double mu2 = Util.clip(Math.abs(.5 - mu) * 2, 0, 1);
					if(!topX && !topY)
					{
						edgeRed *= 1 + (.05 * mu2);
						edgeGreen *= 1 + (.05 * mu2);
						edgeBlue *= 1 + (.05 * mu2);
					}else
					if(topX && topY)
					{
						edgeRed *= 1 - (.05 * mu2);
						edgeGreen *= 1 - (.05 * mu2);
						edgeBlue *= 1 - (.05 * mu2);
					}
				}
				image.set(i, j, 255, (int)(Util.clip(edgeRed, 0, 1) * 0xFF), (int)(Util.clip(edgeGreen, 0, 1) * 0xFF), (int)(Util.clip(edgeBlue, 0, 1) * 0xFF));
			}
		}
	}
	
	private class CustomDimension extends UIDimension
	{
		public boolean hori;
		public boolean target;
		
		public CustomDimension(boolean h, boolean t)
		{
			hori = h;
			target = t;
		}
		
		@Override
		public double filterValue(double value, long id)
		{
			if(reactiveSize == target)
			{
				int border = maxBorder ? Integer.MIN_VALUE : Integer.MAX_VALUE;
				if((hori || sharedBorder) && horiBorder != null)
				{
					horiBorder.update(id);
					if(maxBorder) border = Math.max(border, horiBorder.get());
					else border = Math.min(border, horiBorder.get());
				}
				if((!hori || sharedBorder) && vertBorder != null)
				{
					vertBorder.update(id);
					if(maxBorder) border = Math.max(border, vertBorder.get());
					else border = Math.min(border, vertBorder.get());
				}
				value += border * 2 * (target ? 1 : -1);
			}
			return value;
		}
	}

	@Override
	public boolean mousePress(int x, int y, int button)
	{
		return false;
	}

	@Override
	public boolean mouseRelease(int x, int y, int button)
	{
		return false;
	}
	
	@Override
	public boolean mouseMove(int x, int y, int dx, int dy)
	{
		return false;
	}

	@Override
	public ListenerType mouseListenerType()
	{
		return ListenerType.STANDARD;
	}
}
