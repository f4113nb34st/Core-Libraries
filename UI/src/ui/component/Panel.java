package ui.component;

import render.image.Image;
import ui.Component;

/**
 * 
 * A Panel is a simple Component with a background color.
 * 
 * @author F4113nb34st
 *
 */
public class Panel extends Component
{
	@Override
	public void render(Image image)
	{
		image.fill(foreColor.getRed(), foreColor.getGreen(), foreColor.getBlue());
	}
}
