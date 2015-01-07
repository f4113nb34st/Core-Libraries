package test;

import render.image.Image;
import ui.Component;
import ui.component.Label;
import ui.component.Panel;
import core.UICore;

public class UITest extends UICore
{
	public static void main(String[] args)
	{
		UITest test = new UITest();
		test.init();
		test.gameLoop();
	}
	
	public UITest()
	{
		super("UITest");
	}

	@Override
	public void init()
	{
		/*String[] lines = new String[]{"!\"#$%&'()*", "+,-./01234", "56789:;<=>", "?@ABCDEFGH", "IJKLMNOPQR", "STUVWXYZ[\\", "]^_`abcdef", "ghijklmnop", "qrstuvwxyz{|}~"};
		Component parent = null;
		for(String line : lines)
		{
			Label label = new Label(line);
			if(parent == null)
			{
				label.x.ratio = .5;
				label.x.offset = -.5;
				label.setParent(uiframe);
			}else
			{
				label.y.ratio = 1;
				label.setParent(parent);
			}
			
			label.width.ratio = 1;
			label.height.parentDim = uiframe.height;
			label.height.ratio = 1D / lines.length;
			label.foreColor.base.set(255, 255, 255);
			label.textColor.base.set(0, 0, 0);
			parent = label;
		}*/
		Component panel = new Component()
		{
			public void update()
			{
				super.update();
				dirty = true;
			}
			
			@Override
			public void render(Image image)
			{
				image.fill(0);
				//image.fillRoundedRect(0, 0, image.getWidth(), image.getHeight(), 100, 100, 1, true);
				//image.fillRect(100, 100, 200, 200, .5, false);
				//image.fillRoundedRect(100, 100, 200, 200, 100, 100, 1, true);
				image.fillCircle(image.getWidth() / 2D, image.getHeight() / 2, Math.min(image.getWidth(), image.getHeight()) / 2, 1, true);
			}
		};
		panel.width.ratio = 1;
		panel.height.ratio = 1;
		panel.setParent(uiframe);
	}
}
