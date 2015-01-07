package ui.component.button;

import render.image.Image;
import ui.Component;
import ui.component.icon.AnimatedIcon;

public class AnimatedIconButton extends Button
{
	public AnimatedIcon icon;
	
	public AnimatedIconButton(Image image)
	{
		icon = new AnimatedIcon(image);
		icon.paused = true;
		icon.width.ratio = 1;
		icon.height.ratio = 1;
		icon.foreColor.addParent(content.foreColor, 1);
	}
	
	@Override
	public void mouseEnter()
	{	
		super.mouseEnter();
		icon.paused = false;
		icon.slowPause = false;
	}
	
	@Override
	public void mouseLeave()
	{
		super.mouseLeave();
		icon.slowPause = true;
	}
	
	/**
	 * Sets the parent of this Component.
	 * @param p The parent component.
	 */
	@Override
	public void setParent(Component p, boolean bypass)
	{
		super.setParent(p, bypass);
		icon.setParent(content);
	}
}
