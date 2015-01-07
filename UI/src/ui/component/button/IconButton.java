package ui.component.button;

import render.image.Image;
import ui.Component;
import ui.component.icon.Icon;

public class IconButton extends Button
{
	public Icon icon;
	
	public IconButton(Image image)
	{
		icon = new Icon(image);
		icon.width.ratio = 1;
		icon.height.ratio = 1;
		icon.foreColor.addParent(content.foreColor, 1);
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
