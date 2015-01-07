package core;

import render.image.Image;
import ui.Frame;

public abstract class UICore extends Core
{
	public UICore(String name)
	{
		super(name);
	}
	
	public UICore(String name, double tick)
	{
		super(name, tick);
	}
	
	public Frame uiframe = new Frame();

	@Override
	public void tick()
	{
		uiframe.update();
	}

	@Override
	public void paint(Image image)
	{
		uiframe.screenX = frame.getX();
		uiframe.screenY = frame.getY();
		if(!frame.isUndecorated())
		{
			uiframe.screenX += frame.getInsets().left;
			uiframe.screenY += frame.getInsets().top;
		}
		uiframe.frameRender(image);
	}
}
