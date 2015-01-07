package ui.component.icon;

import math.vector.old.Vector3D;
import render.image.Image;
import render.image.IntBackedImage;

public class AnimatedIcon extends Icon
{
	public boolean paused = false;
	public boolean slowPause = false;
	
	public int currentFrame = 0;
	private Image[] frames;
	private Image[] scaledFrames;
	private Vector3D[] backgrounds;
	
	public AnimatedIcon(Image img)
	{
		super(img);
		int size = img.getHeight();
		int num = img.getWidth() / img.getHeight();
		frames = new Image[num];
		scaledFrames = new Image[num];
		backgrounds = new Vector3D[num];
		for(int i = 0; i < num; i++)
		{
			frames[i] = new IntBackedImage(size, size);
			scaledFrames[i] = new IntBackedImage(1, 1);
			frames[i].drawImage(img, -i * size, 0);
		}
		image = frames[0];
		scaledImage = scaledFrames[0];
	}
	
	@Override
	public void update()
	{
		super.update();
		if(!paused)
		{
			setCurrentFrame(currentFrame + 1);
			if(slowPause && currentFrame == 0)
			{
				slowPause = false;
				paused = true;
			}
		}
	}
	
	public void setCurrentFrame(int frame)
	{
		backgrounds[currentFrame] = oldColor;
		currentFrame = frame % frames.length;
		image = frames[currentFrame];
		scaledImage = scaledFrames[currentFrame];
		oldColor = backgrounds[currentFrame];
		dirty = true;
	}
}
