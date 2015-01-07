package test;

import java.awt.Frame;
import render.color.Color;
import render.color.DoubleARGB;
import render.image.Image;
import util.Util;
import core.Core;

public class Tester extends Core
{
	public static void main(String[] args)
	{
		Tester test = new Tester("Tester", 60);
		test.init();
		test.gameLoop();
	}
	
	public Tester(String name, double tick)
	{
		super(name, tick);
	}
	
	@Override
	public void initFrame()
	{
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	@Override
	public void init()
	{
		
	}
	double theta = 0;
	double radius = 100;
	double X = 0;
	double Y = 0;
	boolean increasing = true;
	DoubleARGB color = new DoubleARGB(1, 1, 1, 1);
	
	@Override
	public void tick()
	{
		theta += Math.PI/120;
		theta %= 2*Math.PI;
		double Bradius = 200;
		X = Bradius * Math.cos(theta);
		Y = -Bradius * Math.sin(theta);
		radius = 100 * Math.abs(Math.cos(theta * 2));
		
		double red = Math.abs(Math.cos(theta));
		double green;
		double blue;
		if (theta > 0 && theta < Math.PI/2)
		{
			green = 1;
			blue = Math.cos(theta);
		}else
		if (theta > Math.PI/2 && theta < Math.PI)
		{
			green = Math.sin(theta);
			blue = 0;
		}else
		if (theta > Math.PI && theta < 3*Math.PI/2)
		{
			green = 0;
			blue = -Math.sin(theta);
		}else
		{
			green = Math.cos(theta);
			blue = 1; 
		}
		red = Util.clip(red, 0, 1);
		green = Util.clip(green, 0, 1);
		blue = Util.clip(blue, 0, 1);
		
		
		color.setRed(red);
		color.setGreen(green);
		color.setBlue(blue);
		
		/*if (color.getRed() >= 1 && color.getBlue() >= 1)
		{
			increasing = false;
		}
		if (color.getRed() <= 0 && color.getBlue() <= 0)
		{
			increasing = true;
		}
		
		if (increasing)
		{
			color.setRed(color.getRed() + 1D/120);
			color.setBlue(color.getBlue() + 1D/120);
		}else 
		{
			color.setRed(color.getRed() - 1D/120);
			color.setBlue(color.getBlue() - 1D/120);
		}*/
	}

	@Override
	public void paint(Image image)
	{
		image.fill(Color.BLACK);
		image.fillCircle((int)(X + (image.getWidth() / 2)), (int)(Y + (image.getHeight() / 2)), (int)radius, color);
	}
}
