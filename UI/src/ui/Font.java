package ui;

import java.util.HashMap;
import render.image.Image;
import render.image.MaskImage;
import util.DefaultInterpolations;
import math.FastMath;
import math.vector.DoubleVector2D;
import math.vector.Vector2D;
import noise.AdvancedVoronoiNoise;
import noise.voronoi.*;

/**
 * 
 * Represents a true font (constant char width). Cannot be resized, but can be created with any size.
 * 
 * @author F4113nb34st
 *
 */
public class Font
{
	/**
	 * Returns the width for the given height.
	 * @param height The height of the text.
	 * @return The width of the text.
	 */
	public static final int getWidth(int height)
	{
		return height / 2;
	}
	
	/**
	 * The width of the font.
	 */
	private int width;
	
	/**
	 * The height of the font.
	 */
	private int height;
	
	/**
	 * The thickness of the font.
	 */
	private double thickness;
	
	/**
	 * The noise generator.
	 */
	private AdvancedVoronoiNoise noise;
	
	/**
	 * Map of char renders used so far.
	 */
	private HashMap<Character, MaskImage> renders = new HashMap<Character, MaskImage>();
	
	/**
	 * The render for the password character.
	 */
	private MaskImage passwordRender;
	
	/**
	 * Creates a new Font.
	 * @param h The height of the chars.
	 */
	public Font(int h)
	{
		width = h / 2;//width is always .5 height
		height = h;
		thickness = h / 20D;//thickness is .05 height
		thickness = Math.max(thickness, 1);//min 1 thickness
		noise = new AdvancedVoronoiNoise();//create generator
		noise.maxDis = thickness;//set max dis
	}
	
	/**
	 * @return The width of this Font.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return The height of this Font.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * @return The thickness of this Font.
	 */
	public double getThickness()
	{
		return thickness;
	}
	
	/**
	 * Draws the given string on the given image in the given location.
	 * @param s The string to draw.
	 * @param x The x location.
	 * @param y The y location.
	 * @param image The image to draw on.
	 * @param image The image to draw on.
	 */
	public void draw(String s, int x, int y, Image image)
	{
		draw(s, x, y, image, 0, 0, 0);//draw with black
	}
	
	/**
	 * Draws the given string on the given image in the given location with the given color.
	 * @param s The string to draw.
	 * @param x The x location.
	 * @param y The y location.
	 * @param image The image to draw on.
	 * @param image The image to draw on.
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public void draw(String s, int x, int y, Image image, double r, double g, double b)
	{
		for(int i = 0; i < s.length(); i++)
		{
			draw(s.charAt(i), x + (width * i), y, image, r, g, b);
		}
	}
	
	/**
	 * Draws the given char on the given image in the given location with the given color.
	 * @param c The char to draw.
	 * @param x The x location.
	 * @param y The y location.
	 * @param image The image to draw on.
	 */
	public void draw(char c, int x, int y, Image image)
	{
		draw(c, x, y, image, 0, 0, 0);//draw with black
	}
	
	/**
	 * Draws the given char on the given image in the given location with the given color.
	 * @param c The char to draw.
	 * @param x The x location.
	 * @param y The y location.
	 * @param image The image to draw on.
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public void draw(char c, int x, int y, Image image, double r, double g, double b)
	{
		MaskImage render = renders.get(c);
		if(render == null)//if char's never been used
		{
			render = new MaskImage(width, height);//make the render
			drawChar(c, render);
			renders.put(c, render);
		}
		image.blendImage(render, x, y, 1, r, g, b);
	}
	
	/**
	 * Draws the given password char array on the given image in the given location with the given color.
	 * @param cs The password to draw.
	 * @param x The x location.
	 * @param y The y location.
	 * @param image The image to draw on.
	 * @param image The image to draw on.
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public void drawPassword(char[] cs, int x, int y, Image image, double r, double g, double b)
	{
		for(int i = 0; i < cs.length; i++)
		{
			drawPasswordChar(x + (width * i), y, image, r, g, b);
		}
	}
	
	/**
	 * Draws the password char on the given image in the given location with the given color.
	 * @param x The x location.
	 * @param y The y location.
	 * @param image The image to draw on.
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 */
	public void drawPasswordChar(int x, int y, Image image, double r, double g, double b)
	{
		if(passwordRender == null)//if char's never been used
		{
			passwordRender = new MaskImage(width, height);//make the render
			drawPasswordChar(passwordRender);
		}
		image.blendImage(passwordRender, x, y, 1, r, g, b);
	}
	
	/**
	 * Draws a char on the render image.
	 * @param c The char to draw.
	 * @param render The image to render on.
	 */
	private void drawChar(char c, Image render)
	{
		noise.objects.clear();
		double baseLine = height * 2D / 3D;
		double typicalSize = baseLine / 4;
		double lowerSize = baseLine / 5;
		double dotRad = thickness * .5;
		switch(c)
		{
			case '!':
			{
				VoronoiCircle dot = new VoronoiCircle(width / 2, baseLine - dotRad, dotRad, true);
				VoronoiLine line = new VoronoiLine(width / 2, baseLine - (2 * dotRad) - (3 * thickness), width / 2, thickness);
				noise.objects.add(dot);
				noise.objects.add(line);
				break;
			}
			case '\"':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - thickness * 2, typicalSize * 3 / 4 + thickness, width / 2 - thickness * 2, thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + thickness * 2, typicalSize * 3 / 4 + thickness, width / 2 + thickness * 2, thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '#':
			{
				VoronoiLine line1 = new VoronoiLine(width / 3 + width / 6, thickness, width / 3 - width / 6, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width * 2 / 3 + width / 6, thickness, width * 2 / 3 - width / 6, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(thickness, baseLine * 1 / 3, width - thickness, baseLine * 1 / 3);
				VoronoiLine line4 = new VoronoiLine(thickness, baseLine * 2 / 3, width - thickness, baseLine * 2 / 3);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				break;
			}
			case '$':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2, thickness, width / 2, baseLine - thickness);
				double radius = Math.min(width / 2 - thickness * 3, (baseLine - thickness * 6) / 4);
				VoronoiArc arc1 = new VoronoiArc(width / 2, (int)((thickness * 3) + radius), radius, false, Math.PI / 2, Math.PI * 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2, (int)(baseLine - (thickness * 3) - radius), radius, false, Math.PI * 3 / 2, Math.PI * 6 / 2);
				noise.objects.add(line1);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				break;
			}
			case '%':
			{
				VoronoiLine line = new VoronoiLine(width - (thickness * 3), (thickness * 3), (thickness * 3), baseLine - (thickness * 3));
				double radius = Math.min(baseLine / 4, width / 4) - thickness;
				VoronoiCircle circle1 = new VoronoiCircle(width / 4, baseLine / 4, radius, false);
				VoronoiCircle circle2 = new VoronoiCircle(width * 3 / 4, baseLine * 3 / 4, radius, false);
				noise.objects.add(line);
				noise.objects.add(circle1);
				noise.objects.add(circle2);
				break;
			}
			case '&':
			{
				double rad1 = baseLine * 1.5 / 10;
				double rad2 = rad1 * 1.5;
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine / 4, rad1, false);
				VoronoiArc arc1 = new VoronoiArc(thickness + rad2, baseLine - thickness - rad2, rad2, false, 0, 0);
				VoronoiArc arc2 = new VoronoiArc(width / 4, (circle.center.y + arc1.center.y) / 2, 0, false, -Math.PI / 16, 0);
				VoronoiArc arc3 = new VoronoiArc(width - rad1, baseLine - rad1, rad1, false, 0, 0);
				
				//finding tangent angles
				double dis = Vector2D.distance(circle.center, arc1.center);
				double ctoa = Math.atan2(arc1.center.y - circle.center.y, arc1.center.x - circle.center.x);
				double theta11 = ctoa - Math.cos((circle.radius + arc1.radius - thickness * 2) / dis);
				double theta12 = theta11 + Math.PI;
				
				double ctoa2 = Math.atan2(arc3.center.y - circle.center.y, arc3.center.x - circle.center.x);
				double theta2 = ctoa2 + (Math.PI / 2);
				
				arc1.maxTheta = theta12;
				arc1.minTheta = arc1.maxTheta - Math.PI;
				
				arc3.maxTheta = theta2;
				arc3.minTheta = arc3.maxTheta - Math.PI * 2 / 3;
				
				double endPointX = arc1.center.x + (int)(Math.cos(arc1.minTheta) * arc1.radius);
				double endPointY = arc1.center.y + (int)(Math.sin(arc1.minTheta) * arc1.radius);
				double endTheta = FastMath.atan2(endPointY - arc2.center.y, endPointX - arc2.center.x);
				
				arc2.radius = arc2.center.copy().subtract(endPointX, endPointY).length();
				arc2.maxTheta = endTheta;
				
				VoronoiLine line1 = new VoronoiLine(circle.center.x + (Math.cos(theta11) * circle.radius), circle.center.y + (Math.sin(theta11) * circle.radius), 
						                            arc1.center.x + (Math.cos(theta12) * arc1.radius), arc1.center.y + (Math.sin(theta12) * arc1.radius));
				VoronoiLine line2 = new VoronoiLine(circle.center.x + (Math.cos(theta2) * circle.radius), circle.center.y + (Math.sin(theta2) * circle.radius), 
                        						    arc3.center.x + (Math.cos(theta2) * arc3.radius), arc3.center.y + (Math.sin(theta2) * arc3.radius));
				
				noise.objects.add(circle);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(arc3);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '\'':
			{
				VoronoiLine line = new VoronoiLine(width / 2, typicalSize * 3 / 4 + thickness, width / 2, thickness);
				noise.objects.add(line);
				break;
			}
			case '(':
			{
				double radius = height / 2;
				double theta = Math.sin((baseLine / 2 - thickness) / radius);
				double offset = radius - ((1 - Math.cos(theta)) * radius) / 2;
				VoronoiArc arc = new VoronoiArc(width / 2 + offset, baseLine / 2, radius, false, Math.PI - theta, Math.PI + theta);
				noise.objects.add(arc);
				break;
			}
			case ')':
			{
				double radius = height / 2;
				double theta = Math.sin((baseLine / 2 - thickness) / radius);
				double offset = radius - ((1 - Math.cos(theta)) * radius) / 2;
				VoronoiArc arc = new VoronoiArc(width / 2 - offset, baseLine / 2, radius, false, 0 - theta, 0 + theta);
				noise.objects.add(arc);
				break;
			}
			case '*':
			{
				double cx = width / 2;
				double cy = baseLine / 4;
				double radius = baseLine / 5;
				for(double theta = Math.PI / 6; theta <= Math.PI * 5 / 6; theta += Math.PI / 3)
				{
					VoronoiLine line = new VoronoiLine(cx + radius * Math.cos(theta), cy + radius * Math.sin(theta), cx + radius * Math.cos(theta + Math.PI), cy + radius * Math.sin(theta + Math.PI));
					noise.objects.add(line);
				}
				break;
			}
			case '+':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, baseLine / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2, baseLine / 2 - typicalSize, width / 2, baseLine / 2 + typicalSize);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case ',':
			{
				double arcRad = dotRad * 2.5;
				VoronoiCircle dot = new VoronoiCircle(width / 2, baseLine - dotRad, dotRad, true);
				VoronoiArc arc = new VoronoiArc(width / 2 - (dotRad / 2), baseLine, arcRad, false, -Math.PI / 4, Math.PI / 2);
				noise.objects.add(dot);
				noise.objects.add(arc);
				break;
			}
			case '-':
			{
				VoronoiLine line = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, baseLine / 2);
				noise.objects.add(line);
				break;
			}
			case '.':
			{
				VoronoiCircle dot = new VoronoiCircle(width / 2, baseLine - dotRad, dotRad, true);
				noise.objects.add(dot);
				break;
			}
			case '/':
			{
				VoronoiLine line = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, thickness);
				noise.objects.add(line);
				break;
			}
			case '0':
			{
				VoronoiArc arc1 = new VoronoiArc(width / 2, thickness + typicalSize, typicalSize, false, Math.PI, Math.PI * 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2, baseLine - thickness - typicalSize, typicalSize, false, 0, Math.PI);
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness + typicalSize, width / 2 - typicalSize, baseLine - thickness - typicalSize);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness + typicalSize, width / 2 + typicalSize, baseLine - thickness - typicalSize);
				VoronoiLine line3 = new VoronoiLine(width / 2 + typicalSize, thickness + typicalSize, width / 2 - typicalSize, baseLine - thickness - typicalSize);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '1':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2, thickness, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2, thickness, width / 2 - typicalSize, thickness + typicalSize);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '2':
			{
				VoronoiArc arc = new VoronoiArc(width / 2, thickness + typicalSize, typicalSize, false, -Math.PI, 0);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, baseLine - thickness);
				
				DoubleVector2D dif = line2.p1.copy().subtract(arc.center);
				double dis = dif.length();
				double theta = Math.atan2(dif.y, dif.x) - Math.cos(arc.radius / dis);
				DoubleVector2D.pool.release(dif);
				
				VoronoiLine line1 = new VoronoiLine(arc.center.x + (Math.cos(theta) * arc.radius), arc.center.y + (Math.sin(theta) * arc.radius), line2.p1.x, line2.p1.y);
				
				arc.maxTheta = theta;
				
				noise.objects.add(arc);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '3':
			{
				double smallSize = (baseLine - (2 * (thickness + typicalSize))) / 2;
				VoronoiArc arc1 = new VoronoiArc(width / 2, thickness + smallSize, smallSize, false, -Math.PI * 3 / 4, Math.PI / 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2, thickness + (2 * smallSize) + typicalSize, typicalSize, false, -Math.PI / 2, Math.PI * 3 / 4);
				
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				break;
			}
			case '4':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 + (typicalSize / 3), thickness, width / 2 + (typicalSize / 3), baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + (typicalSize / 3), thickness, width / 2 - typicalSize, thickness + (typicalSize * 2.5));
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, thickness + (typicalSize * 2.5), width / 2 + typicalSize, thickness + (typicalSize * 2.5));
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '5':
			{
				double radius = baseLine * 28 / 100;
				double mid = baseLine - (2 * radius) - thickness;
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize * 3 / 5, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, mid, thickness + radius, mid);
				VoronoiLine line2 = new VoronoiLine(line1.p1.x, line1.p1.y, line3.p1.x, line3.p1.y);
				
				VoronoiArc arc = new VoronoiArc(thickness + radius, baseLine - thickness - radius, radius, false, -Math.PI / 2, Math.PI * 3 / 4);
				
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(arc);
				break;
			}
			case '6':
			{
				VoronoiArc arc = new VoronoiArc(width / 2 + typicalSize, typicalSize * 2, typicalSize * 2, false, Math.PI * 3 / 4, Math.PI * 3 / 2);
				double cx = arc.center.x + (Math.cos(Math.PI * 3 / 4) * typicalSize);
				double cy = arc.center.y + (Math.sin(Math.PI * 3 / 4) * typicalSize);
				VoronoiCircle dot = new VoronoiCircle(cx, cy, typicalSize, false);
				
				double dy = baseLine - thickness - typicalSize - dot.center.y;
				dot.center.y += dy;
				arc.center.y += dy;
				
				noise.objects.add(arc);
				noise.objects.add(dot);
				break;
			}
			case '7':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 - typicalSize / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '8':
			{
				VoronoiCircle circle2 = new VoronoiCircle(width / 2, baseLine - thickness - typicalSize, typicalSize, false);
				VoronoiCircle circle1 = new VoronoiCircle(width / 2, 0, (baseLine - typicalSize * 2) / 2 - thickness, false);
				circle1.center.y = circle1.radius + thickness;
				noise.objects.add(circle1);
				noise.objects.add(circle2);
				break;
			}
			case '9':
			{
				VoronoiArc arc = new VoronoiArc(width / 2 + typicalSize, typicalSize * 2, typicalSize * 2, false, Math.PI * 3 / 4, Math.PI * 3 / 2);
				double cx = arc.center.x + (Math.cos(Math.PI * 3 / 4) * typicalSize);
				double cy = arc.center.y + (Math.sin(Math.PI * 3 / 4) * typicalSize);
				VoronoiCircle dot = new VoronoiCircle(cx, cy, typicalSize, false);
				
				double dy = baseLine - thickness - typicalSize - dot.center.y;
				dot.center.y += dy;
				arc.center.y += dy;
				
				//same as 6 but flipped vert and hori
				arc.center.negate().add(width, baseLine);
				dot.center.negate().add(width, baseLine);
				arc.minTheta += Math.PI;
				arc.maxTheta += Math.PI;
				
				noise.objects.add(arc);
				noise.objects.add(dot);
				break;
			}
			case ':':
			{
				VoronoiCircle dot1 = new VoronoiCircle(width / 2, baseLine - dotRad, dotRad, true);
				VoronoiCircle dot2 = new VoronoiCircle(width / 2, baseLine - dotRad - typicalSize * 2, dotRad, true);
				noise.objects.add(dot1);
				noise.objects.add(dot2);
				break;
			}
			case ';':
			{
				double arcRad = dotRad * 2.5;
				VoronoiCircle dot1 = new VoronoiCircle(width / 2, baseLine - dotRad, dotRad, true);
				VoronoiCircle dot2 = new VoronoiCircle(width / 2, baseLine - dotRad - typicalSize * 2, dotRad, true);
				VoronoiArc arc = new VoronoiArc(width / 2 - (dotRad / 2), baseLine, arcRad, false, -Math.PI / 4, Math.PI / 2);
				noise.objects.add(dot1);
				noise.objects.add(dot2);
				noise.objects.add(arc);
				break;
			}
			case '<':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, baseLine / 2 - typicalSize);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, baseLine / 2 + typicalSize);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '=':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - typicalSize / 2, width / 2 + typicalSize, baseLine / 2 - typicalSize / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 + typicalSize / 2, width / 2 + typicalSize, baseLine / 2 + typicalSize / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '>':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - typicalSize, width / 2 + typicalSize, baseLine / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 + typicalSize, width / 2 + typicalSize, baseLine / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '?':
			{
				VoronoiCircle dot = new VoronoiCircle(width / 2, baseLine - dotRad, dotRad, true);
				VoronoiLine line = new VoronoiLine(width / 2, baseLine - (2 * dotRad) - (2 * thickness), width / 2, thickness + typicalSize * 2);
				VoronoiArc arc = new VoronoiArc(width / 2, thickness + typicalSize, typicalSize, false, Math.PI, Math.PI * 2.5);
				noise.objects.add(dot);
				if(line.p1.y >= line.p2.y)
				{
					noise.objects.add(line);
				}
				noise.objects.add(arc);
				break;
			}
			case '@':
			{
				double radius = (width / 2 - thickness) / 2;
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, radius, false);
				VoronoiLine line = new VoronoiLine(width / 2 + radius + thickness, baseLine - thickness, width / 2 + radius, baseLine - thickness - (lowerSize * 2));
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - lowerSize, radius * 2, false, Math.PI / 2, Math.PI * 2.25);
				noise.objects.add(circle);
				noise.objects.add(line);
				noise.objects.add(arc);
				break;
			}
			case 'A':
			{
				VoronoiLine line1 = new VoronoiLine(thickness, baseLine - thickness, width / 2, thickness);
				VoronoiLine line2 = new VoronoiLine(width - thickness, baseLine - thickness, width / 2, thickness);
				DoubleVector2D line3p1 = line1.p2.copy().subtract(line1.p1);
				DoubleVector2D line3p2 = line2.p2.copy().subtract(line2.p1);
				double multi = 2 / 5D;
				line3p1.multiply(multi);
				line3p2.multiply(multi);
				line3p1.add(line1.p1);
				line3p2.add(line2.p1);
				VoronoiLine line3 = new VoronoiLine(line3p1, line3p2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'B':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - thickness, width / 2, baseLine / 2 - thickness);
				VoronoiLine line4 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2, baseLine - thickness);
				VoronoiArc arc1 = new VoronoiArc(width / 2, (line3.p2.y + line2.p2.y) / 2, (line3.p2.y - line2.p2.y) / 2, false, -Math.PI / 2, Math.PI / 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2, (line4.p2.y + line3.p2.y) / 2, (line4.p2.y - line3.p2.y) / 2, false, -Math.PI / 2, Math.PI / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				break;
			}
			case 'C':
			{
				double radius = typicalSize / Math.sqrt(1 / 2D);
				VoronoiArc arc1 = new VoronoiArc(width / 2, baseLine / 2 + radius - typicalSize * 2 + thickness, radius, false, Math.PI * 5 / 4, Math.PI * 7 / 4);
				VoronoiArc arc2 = new VoronoiArc(width / 2, baseLine / 2 - radius + typicalSize * 2 - thickness, radius, false, Math.PI / 4, Math.PI * 3 / 4);
				double theta = Math.asin((-radius + typicalSize * 3 - thickness) / (radius * 2));
				double x = Math.cos(theta) * radius * 2 - Math.sin(Math.PI / 4) * radius;
				VoronoiArc arc3 = new VoronoiArc(width / 2 + x, baseLine / 2, radius * 2, false, Math.PI - theta, Math.PI + theta);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(arc3);
				break;
			}
			case 'D':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize / 2, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 - typicalSize / 2, baseLine - thickness);
				VoronoiArc arc = new VoronoiArc(width / 2 - typicalSize / 2, (line3.p2.y + line2.p2.y) / 2, (line3.p2.y - line2.p2.y) / 2, false, -Math.PI / 2, Math.PI / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(arc);
				break;
			}
			case 'E':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - thickness, width / 2 + typicalSize, baseLine / 2 - thickness);
				VoronoiLine line4 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				break;
			}
			case 'F':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - thickness, width / 2 + typicalSize, baseLine / 2 - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'G':
			{
				double radius = typicalSize / Math.sqrt(1 / 2D);
				VoronoiArc arc1 = new VoronoiArc(width / 2, baseLine / 2 + radius - typicalSize * 2 + thickness, radius, false, Math.PI * 5 / 4, Math.PI * 7 / 4);
				VoronoiArc arc2 = new VoronoiArc(width / 2, baseLine / 2 - radius + typicalSize * 2 - thickness, radius, false, Math.PI / 4, Math.PI * 3 / 4);
				double theta = Math.asin((-radius + typicalSize * 3 - thickness) / (radius * 2));
				double x = Math.cos(theta) * radius * 2 - Math.sin(Math.PI / 4) * radius;
				VoronoiArc arc3 = new VoronoiArc(width / 2 + x, baseLine / 2, radius * 2, false, Math.PI - theta, Math.PI + theta);
				VoronoiLine line1 = new VoronoiLine(width / 2 + typicalSize, baseLine / 2 + radius, width / 2 + typicalSize, baseLine / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, baseLine / 2, width / 2, baseLine / 2);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(arc3);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'H':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 + typicalSize, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, baseLine / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'I':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2, thickness, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize / 2, thickness, width / 2 + typicalSize / 2, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize / 2, baseLine - thickness, width / 2 + typicalSize / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'J':
			{
				VoronoiLine line = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 + typicalSize, baseLine - thickness - typicalSize);
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - typicalSize, typicalSize, false, 0, Math.PI);
				noise.objects.add(line);
				noise.objects.add(arc);
				break;
			}
			case 'K':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2, width / 2 + typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'L':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'M':
			{
				VoronoiLine line1 = new VoronoiLine(thickness, thickness, thickness, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width - thickness, thickness, width - thickness, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(thickness, thickness, width / 2, baseLine - thickness);
				VoronoiLine line4 = new VoronoiLine(width - thickness, thickness, width / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				break;
			}
			case 'N':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 + typicalSize, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'O':
			{
				VoronoiArc arc1 = new VoronoiArc(width / 2, thickness + typicalSize, typicalSize, false, Math.PI, Math.PI * 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2, baseLine - thickness - typicalSize, typicalSize, false, 0, Math.PI);
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness + typicalSize, width / 2 - typicalSize, baseLine - thickness - typicalSize);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness + typicalSize, width / 2 + typicalSize, baseLine - thickness - typicalSize);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'P':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - thickness, width / 2, baseLine / 2 - thickness);
				VoronoiArc arc1 = new VoronoiArc(width / 2, (line3.p2.y + line2.p2.y) / 2, (line3.p2.y - line2.p2.y) / 2, false, -Math.PI / 2, Math.PI / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(arc1);
				break;
			}
			case 'Q':
			{
				VoronoiArc arc1 = new VoronoiArc(width / 2, thickness + typicalSize, typicalSize, false, Math.PI, Math.PI * 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2, baseLine - thickness - typicalSize, typicalSize, false, 0, Math.PI);
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness + typicalSize, width / 2 - typicalSize, baseLine - thickness - typicalSize);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness + typicalSize, width / 2 + typicalSize, baseLine - thickness - typicalSize);
				VoronoiLine line3 = new VoronoiLine(width / 2, baseLine / 2, width - thickness, baseLine - thickness);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'R':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2, thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize, baseLine / 2 - thickness, width / 2, baseLine / 2 - thickness);
				VoronoiLine line4 = new VoronoiLine(width / 2 - typicalSize / 4, baseLine / 2 - thickness, width / 2 + typicalSize, baseLine - thickness);
				VoronoiArc arc1 = new VoronoiArc(width / 2, (line3.p2.y + line2.p2.y) / 2, (line3.p2.y - line2.p2.y) / 2, false, -Math.PI / 2, Math.PI / 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				noise.objects.add(arc1);
				break;
			}
			case 'S':
			{
				VoronoiArc arc2 = new VoronoiArc(width / 2, baseLine - thickness - typicalSize, typicalSize, false, -Math.PI / 2, Math.PI * 3 / 4);
				VoronoiArc arc1 = new VoronoiArc(width / 2, 0, (baseLine - typicalSize * 2) / 2 - thickness, false, Math.PI / 2, Math.PI * 7 / 4);
				arc1.center.y = arc1.radius + thickness;
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				break;
			}
			case 'T':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2, thickness, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'U':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness - typicalSize);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 + typicalSize, baseLine - thickness - typicalSize);
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - typicalSize, typicalSize, false, 0, Math.PI);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(arc);
				break;
			}
			case 'V':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'W':
			{
				VoronoiLine line1 = new VoronoiLine(thickness, thickness, width / 4, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width - thickness, thickness, width * 3 / 4, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 4, baseLine - thickness, width / 2, thickness);
				VoronoiLine line4 = new VoronoiLine(width * 3 / 4, baseLine - thickness, width / 2, thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				break;
			}
			case 'X':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'Y':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2, baseLine / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2, baseLine / 2);
				VoronoiLine line3 = new VoronoiLine(width / 2, baseLine / 2, width / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'Z':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize, thickness, width / 2 + typicalSize, thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 + typicalSize, thickness, width / 2 - typicalSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '[':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize / 2, thickness, width / 2 + typicalSize / 2, thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize / 2, baseLine - thickness, width / 2 + typicalSize / 2, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 - typicalSize / 2, thickness, width / 2 - typicalSize / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '\\':
			{
				VoronoiLine line = new VoronoiLine(width / 2 + typicalSize, baseLine - thickness, width / 2 - typicalSize, thickness);
				noise.objects.add(line);
				break;
			}
			case ']':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize / 2, thickness, width / 2 + typicalSize / 2, thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - typicalSize / 2, baseLine - thickness, width / 2 + typicalSize / 2, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 + typicalSize / 2, thickness, width / 2 + typicalSize / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '^':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - typicalSize * 4 / 5, thickness + typicalSize, width / 2, thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + typicalSize * 4 / 5, thickness + typicalSize, width / 2, thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case '_':
			{
				VoronoiLine line = new VoronoiLine(width / 2 - typicalSize, baseLine - thickness, width / 2 + typicalSize, baseLine - thickness);
				noise.objects.add(line);
				break;
			}
			case '`':
			{
				VoronoiLine line = new VoronoiLine(width / 2 + typicalSize * 2 / 5, typicalSize * 3 / 4 + thickness, width / 2 - typicalSize * 2 / 5, thickness);
				noise.objects.add(line);
				break;
			}
			case 'a':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				VoronoiLine line = new VoronoiLine(width / 2 + lowerSize + thickness, baseLine - thickness, width / 2 + lowerSize, baseLine - thickness - (lowerSize * 2));
				noise.objects.add(circle);
				noise.objects.add(line);
				break;
			}
			case 'b':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				VoronoiLine line = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness, width / 2 - lowerSize, baseLine - thickness - (lowerSize * 4));
				noise.objects.add(circle);
				noise.objects.add(line);
				break;
			}
			case 'c':
			{
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - lowerSize, lowerSize, false, Math.PI / 4, Math.PI * 7 / 4);
				noise.objects.add(arc);
				break;
			}
			case 'd':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				VoronoiLine line = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness, width / 2 + lowerSize, baseLine - thickness - (lowerSize * 4));
				noise.objects.add(circle);
				noise.objects.add(line);
				break;
			}
			case 'e':
			{
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - lowerSize, lowerSize, false, Math.PI / 4, Math.PI * 2);
				VoronoiLine line = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness - lowerSize, width / 2 - lowerSize, baseLine - thickness - lowerSize);
				noise.objects.add(arc);
				noise.objects.add(line);
				break;
			}
			case 'f':
			{
				VoronoiArc arc = new VoronoiArc(width / 2 - thickness + lowerSize - thickness, baseLine / 2 - lowerSize, lowerSize - thickness, false, Math.PI, Math.PI * 2);
				VoronoiLine line1 = new VoronoiLine(width / 2 + lowerSize, baseLine / 2, width / 2 - lowerSize, baseLine / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 - thickness, baseLine - thickness, width / 2 - thickness, baseLine / 2 - lowerSize);
				noise.objects.add(arc);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'g':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				VoronoiLine line = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness + lowerSize, width / 2 + lowerSize, baseLine - thickness - (lowerSize * 2));
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness + lowerSize, lowerSize, false, 0, Math.PI - Math.PI / 8);
				noise.objects.add(circle);
				noise.objects.add(line);
				noise.objects.add(arc);
				break;
			}
			case 'h':
			{
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - lowerSize, lowerSize, false, Math.PI, Math.PI * 2);
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness, width / 2 - lowerSize, baseLine - thickness - (lowerSize * 4));
				VoronoiLine line2 = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness, width / 2 + lowerSize, baseLine - thickness - lowerSize);
				noise.objects.add(arc);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'i':
			{
				VoronoiCircle dot = new VoronoiCircle(width / 2, baseLine - thickness * 2 - lowerSize * 2 - dotRad * 3, dotRad, true);
				VoronoiLine line = new VoronoiLine(width / 2, baseLine - thickness, width / 2, baseLine - thickness - lowerSize * 2);
				noise.objects.add(dot);
				noise.objects.add(line);
				break;
			}
			case 'j':
			{
				VoronoiCircle dot = new VoronoiCircle(width / 2, baseLine - thickness * 2 - lowerSize * 2 - dotRad * 3, dotRad, true);
				VoronoiLine line = new VoronoiLine(width / 2, baseLine - thickness + lowerSize, width / 2, baseLine - thickness - (lowerSize * 2));
				VoronoiArc arc = new VoronoiArc(width / 2 - lowerSize, baseLine - thickness + lowerSize, lowerSize, false, 0, Math.PI / 2);
				noise.objects.add(dot);
				noise.objects.add(line);
				noise.objects.add(arc);
				break;
			}
			case 'k':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 4, width / 2 - lowerSize * 3 / 4, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2 + lowerSize * 3 / 4, baseLine - thickness - lowerSize * 3);
				VoronoiLine line3 = new VoronoiLine(width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2 + lowerSize * 3 / 4, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case 'l':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2, baseLine - thickness - lowerSize * 4, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2, baseLine - thickness, width / 2 + lowerSize / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'm':
			{
				double dis = lowerSize * 5 / 4;
				double radius = dis / 2;
				VoronoiLine line1 = new VoronoiLine(width / 2 - dis, baseLine - thickness - lowerSize * 2, width / 2 - dis, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2, baseLine - thickness - lowerSize * 2 + radius, width / 2, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 + dis, baseLine - thickness - lowerSize * 2 + radius, width / 2 + dis, baseLine - thickness);
				VoronoiArc arc1 = new VoronoiArc(width / 2 - radius, baseLine - thickness - lowerSize * 2 + radius, radius, false, Math.PI, Math.PI * 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2 + radius, baseLine - thickness - lowerSize * 2 + radius, radius, false, Math.PI, Math.PI * 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				break;
			}
			case 'n':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2 - lowerSize * 3 / 4, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + lowerSize * 3 / 4, baseLine - thickness - lowerSize * 5 / 4, width / 2 + lowerSize * 3 / 4, baseLine - thickness);
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - lowerSize * 5 / 4, lowerSize * 3 / 4, false, Math.PI, Math.PI * 2);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(arc);
				break;
			}
			case 'o':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				noise.objects.add(circle);
				break;
			}
			case 'p':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				VoronoiLine line = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness + lowerSize * 1.5, width / 2 - lowerSize, baseLine - thickness - (lowerSize * 2));
				noise.objects.add(circle);
				noise.objects.add(line);
				break;
			}
			case 'q':
			{
				VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine - thickness - lowerSize, lowerSize, false);
				VoronoiLine line = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness + lowerSize * 1.5, width / 2 + lowerSize, baseLine - thickness - (lowerSize * 2));
				noise.objects.add(circle);
				noise.objects.add(line);
				break;
			}
			case 'r':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2, baseLine - thickness - lowerSize * 2, width / 2, baseLine - thickness);
				VoronoiArc arc = new VoronoiArc(width / 2 + lowerSize, baseLine - thickness - lowerSize, lowerSize, false, Math.PI, Math.PI * 13 / 8);
				noise.objects.add(line1);
				noise.objects.add(arc);
				break;
			}
			case 's':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize / 4, baseLine - thickness - lowerSize * 2, width / 2 + lowerSize / 4, baseLine - thickness - lowerSize * 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 - lowerSize / 4, baseLine - thickness - lowerSize, width / 2 + lowerSize / 4, baseLine - thickness - lowerSize);
				VoronoiLine line3 = new VoronoiLine(width / 2 - lowerSize / 4, baseLine - thickness, width / 2 + lowerSize / 4, baseLine - thickness);
				VoronoiArc arc1 = new VoronoiArc(width / 2 + lowerSize / 4, baseLine - thickness - lowerSize * 3 / 2, lowerSize / 2, false, Math.PI * 3 / 2, Math.PI * 15 / 8);
				VoronoiArc arc2 = new VoronoiArc(width / 2 - lowerSize / 4, baseLine - thickness - lowerSize * 3 / 2, lowerSize / 2, false, Math.PI / 2, Math.PI * 3 / 2);
				VoronoiArc arc3 = new VoronoiArc(width / 2 + lowerSize / 4, baseLine - thickness - lowerSize / 2, lowerSize / 2, false, -Math.PI / 2, Math.PI / 2);
				VoronoiArc arc4 = new VoronoiArc(width / 2 - lowerSize / 4, baseLine - thickness - lowerSize / 2, lowerSize / 2, false, Math.PI / 2, Math.PI * 7 / 8);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(arc3);
				noise.objects.add(arc4);
				break;
			}
			case 't':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 + lowerSize * 3 / 4, baseLine / 2, width / 2 - lowerSize * 3 / 4, baseLine / 2);
				VoronoiLine line2 = new VoronoiLine(width / 2, baseLine - thickness, width / 2, baseLine / 2 - lowerSize);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'u':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 3 / 4);
				VoronoiLine line2 = new VoronoiLine(width / 2 + lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2 + lowerSize * 3 / 4, baseLine - thickness);
				VoronoiArc arc = new VoronoiArc(width / 2, baseLine - thickness - lowerSize * 3 / 4, lowerSize * 3 / 4, false, 0, Math.PI);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(arc);
				break;
			}
			case 'v':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + lowerSize * 3 / 4, baseLine - thickness - lowerSize * 2, width / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'w':
			{
				double dis = lowerSize * 5 / 4;
				VoronoiLine line1 = new VoronoiLine(width / 2 - dis, baseLine - thickness - lowerSize * 2, width / 2 - dis / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2, baseLine - thickness - lowerSize * 2, width / 2 - dis / 2, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2, baseLine - thickness - lowerSize * 2, width / 2 + dis / 2, baseLine - thickness);
				VoronoiLine line4 = new VoronoiLine(width / 2 + dis, baseLine - thickness - lowerSize * 2, width / 2 + dis / 2, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				noise.objects.add(line4);
				break;
			}
			case 'x':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness - lowerSize * 2, width / 2 + lowerSize, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness - lowerSize * 2, width / 2 - lowerSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'y':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness - lowerSize * 2, width / 2, baseLine - thickness);
				VoronoiLine line2 = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness - lowerSize * 2, width / 2 - lowerSize * 3 / 4, baseLine - thickness + lowerSize * 7 / 4);
				noise.objects.add(line1);
				noise.objects.add(line2);
				break;
			}
			case 'z':
			{
				VoronoiLine line1 = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness - lowerSize * 2, width / 2 + lowerSize, baseLine - thickness - lowerSize * 2);
				VoronoiLine line2 = new VoronoiLine(width / 2 - lowerSize, baseLine - thickness, width / 2 + lowerSize, baseLine - thickness);
				VoronoiLine line3 = new VoronoiLine(width / 2 + lowerSize, baseLine - thickness - lowerSize * 2, width / 2 - lowerSize, baseLine - thickness);
				noise.objects.add(line1);
				noise.objects.add(line2);
				noise.objects.add(line3);
				break;
			}
			case '{':
			{
				VoronoiArc arc1 = new VoronoiArc(width / 2 + typicalSize, thickness + typicalSize, typicalSize, false, Math.PI, Math.PI * 3 / 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2 - typicalSize, thickness + typicalSize, typicalSize, false, 0, Math.PI / 2);
				VoronoiArc arc3 = new VoronoiArc(width / 2 - typicalSize, thickness + typicalSize * 3, typicalSize, false, Math.PI * 3 / 2, Math.PI * 2);
				VoronoiArc arc4 = new VoronoiArc(width / 2 + typicalSize, thickness + typicalSize * 3, typicalSize, false, Math.PI / 2, Math.PI);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(arc3);
				noise.objects.add(arc4);
				break;
			}
			case '|':
			{
				VoronoiLine line = new VoronoiLine(width / 2, thickness, width / 2, baseLine - thickness);
				noise.objects.add(line);
				break;
			}
			case '}':
			{
				VoronoiArc arc1 = new VoronoiArc(width / 2 + typicalSize, thickness + typicalSize, typicalSize, false, Math.PI / 2, Math.PI);
				VoronoiArc arc2 = new VoronoiArc(width / 2 - typicalSize, thickness + typicalSize, typicalSize, false, Math.PI * 3 / 2, Math.PI * 2);
				VoronoiArc arc3 = new VoronoiArc(width / 2 - typicalSize, thickness + typicalSize * 3, typicalSize, false, 0, Math.PI / 2);
				VoronoiArc arc4 = new VoronoiArc(width / 2 + typicalSize, thickness + typicalSize * 3, typicalSize, false, Math.PI, Math.PI * 3 / 2);
				noise.objects.add(arc1);
				noise.objects.add(arc2);
				noise.objects.add(arc3);
				noise.objects.add(arc4);
				break;
			}
			case '~':
			{
				double radius = typicalSize / 2;
				VoronoiArc arc1 = new VoronoiArc(width / 2 - radius, baseLine / 2 + radius / 2, radius, false, Math.PI, Math.PI * 3 / 2);
				VoronoiLine line = new VoronoiLine(width / 2 - radius, baseLine / 2 - radius / 2, width / 2 + radius, baseLine / 2 + radius / 2);
				VoronoiArc arc2 = new VoronoiArc(width / 2 + radius, baseLine / 2 - radius / 2, radius, false, 0, Math.PI / 2);
				noise.objects.add(arc1);
				noise.objects.add(line);
				noise.objects.add(arc2);
				break;
			}
		}
		
		boolean box = false;
		if(box)
		{
			VoronoiLine line1 = new VoronoiLine(0, 0, width, 0);
			VoronoiLine line2 = new VoronoiLine(0, baseLine, width - 1, baseLine);
			VoronoiLine line3 = new VoronoiLine(0, height - 1, width - 1, height - 1);
			VoronoiLine line4 = new VoronoiLine(0, 0, 0, height - 1);
			VoronoiLine line5 = new VoronoiLine(width - 1, 0, width - 1, height - 1);
			noise.objects.add(line1);
			noise.objects.add(line2);
			noise.objects.add(line3);
			noise.objects.add(line4);
			noise.objects.add(line5);
		}
		
		if(noise.objects.isEmpty())
		{
			render.fill(0);
			return;//don't fill the render with anything
		}
		
		noise.fill(render);
		double edge = Math.max(.5, 1 / thickness);
		edge = Math.min(edge, 1);
		for(int x = 0; x < render.getWidth(); x++)
		{
			for(int y = 0; y < render.getHeight(); y++)
			{
				double value = render.get(x, y) / thickness;
				double alpha = 1;
				if(value > 1 - edge)
				{
					alpha = DefaultInterpolations.COSINE.interpolate(1, 0, (value - (1 - edge)) / edge);
				}
				
				render.set(x, y, alpha);
			}
		}
	}
	
	/**
	 * Draws a password char on the render image.
	 * @param render The image to render on.
	 */
	private void drawPasswordChar(Image render)
	{
		noise.objects.clear();
		
		double baseLine = height * 2D / 3D;
		double lowerSize = baseLine / 5;
		
		VoronoiCircle circle = new VoronoiCircle(width / 2, baseLine / 2, lowerSize, true);
		noise.objects.add(circle);
		
		if(noise.objects.isEmpty())
		{
			render.fill(1);
			return;//don't fill the render with anything
		}
		
		noise.fill(render);
		double edge = Math.max(.5, 1 / thickness);
		edge = Math.min(edge, 1);
		for(int x = 0; x < render.getWidth(); x++)
		{
			for(int y = 0; y < render.getHeight(); y++)
			{
				double value = render.get(x, y) / thickness;
				double alpha = 1;
				if(value > 1 - edge)
				{
					alpha = DefaultInterpolations.COSINE.interpolate(1, 0, (value - (1 - edge)) / edge);
				}
				
				render.set(x, y, alpha);
			}
		}
	}
}
