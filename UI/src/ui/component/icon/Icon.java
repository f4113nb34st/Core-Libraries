package ui.component.icon;

import java.io.File;
import math.vector.old.Vector3D;
import render.image.Image;
import render.image.IntBackedImage;
import ui.Component;

public class Icon extends Component
{
	public Image image;
	protected Image scaledImage;
	protected Vector3D oldColor = new Vector3D();
	
	public Icon(Image img)
	{
		image = img;
		scaledImage = new IntBackedImage(1, 1);
	}
	
	@Override
	public void render(Image canvas)
	{
		//get copies so if it's animated it doesn't flicker
		Image image = this.image;
		Image scaledImage = this.scaledImage;
		
		if(scaledImage.getWidth() != canvas.getWidth() || scaledImage.getHeight() != canvas.getHeight() || !foreColor.get().equals(oldColor))
		{
			scaledImage.resize(canvas.getWidth(), canvas.getHeight());
			oldColor.set(foreColor.get());
			scaledImage.fill(foreColor.getRed(), foreColor.getGreen(), foreColor.getBlue());
			scaledImage.scaleImage(image, 0, 0, canvas.getWidth() / (double)image.getWidth(), canvas.getHeight() / (double)image.getHeight());
			
			if(!new File("C:/Users/F4113nb34st/Desktop/image.png").exists())
			{
				Image.saveImage(new File("C:/Users/F4113nb34st/Desktop/image.png"), scaledImage);
			}
		}
		canvas.drawImage(scaledImage, 0, 0);
	}
}
