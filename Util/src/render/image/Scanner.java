package render.image;

import util.Util;

/**
 * 
 * Converts a polygon into a series of scan lines.
 * 
 * @author F4113nb34st
 *
 */
public class Scanner
{
//	/**
//	 * The min y coord.
//	 */
//	private int minY;
//	
//	/**
//	 * The max y coord.
//	 */
//	private int maxY;
//	
//	/**
//	 * The min x coords.
//	 */
//	private double[] minXs;
//	
//	/**
//	 * The max x coords.
//	 */
//	private double[] maxXs;
//	
//	/**
//	 * The parent image of this scanner.
//	 */
//	private Image parent;
//	
//	/**
//	 * Creates a new scanner for the given image.
//	 * @param p The parent of the scanner.
//	 */
//	public Scanner(Image p)
//	{
//		parent = p;
//		int height = parent.getHeight();
//		minXs = new double[height];
//		maxXs = new double[height];
//	}
//	
//	/**
//	 * Begins a new scan.
//	 */
//	public void beginScan()
//	{
//		minY = Integer.MAX_VALUE;
//		maxY = Integer.MIN_VALUE;
//		int height = parent.getHeight();
//		if(minXs.length != height || maxXs.length != height)
//		{
//			minXs = new double[height];
//			maxXs = new double[height];
//		}
//		for(int i = 0; i < height; i++)
//		{
//			minXs[i] = Double.POSITIVE_INFINITY;
//			maxXs[i] = Double.NEGATIVE_INFINITY;
//		}
//	}
//	
//	/**
//	 * Fills the current scan with the given color.
//	 * @param val The grey comp.
//	 * @param aa True if the scan is anti-aliased.
//	 */
//	public void fillScan(double val, final boolean aa)
//	{
//		if(minY > maxY)//skip if invalid scan
//		{
//			return;
//		}
//		for(int y = minY; y <= maxY; y++)
//		{
//			//fill scan
//			if(aa)
//			{
//				parent.fillXScanAA(minXs[y], maxXs[y], y, val);
//			}else
//			{
//				parent.fillXScan((int)minXs[y], (int)maxXs[y], y, val);
//			}
//		}
//	}
//	
//	/**
//	 * Fills the current scan with the given color.
//	 * @param a The alpha comp.
//	 * @param r The red comp.
//	 * @param g The green comp.
//	 * @param b The blue comp.
//	 * @param aa True if the scan is anti-aliased.
//	 */
//	public void fillScan(int r, int g, int b, final boolean aa)
//	{
//		if(minY > maxY)//skip if invalid scan
//		{
//			return;
//		}
//		for(int y = minY; y <= maxY; y++)
//		{
//			//fill scan
//			if(aa)
//			{
//				parent.fillXScanAA(minXs[y], maxXs[y], y, r, g, b);
//			}else
//			{
//				parent.fillXScan((int)minXs[y], (int)maxXs[y], y, r, g, b);
//			}
//		}
//	}
//	
//	/**
//	 * Scans a line.
//	 * @param x1 P1 x.
//	 * @param y1 P1 y.
//	 * @param x2 P2 x.
//	 * @param y2 P2 y.
//	 */
//	public void scanLine(double x1, double y1, double x2, double y2)
//	{
//		if(y1 == y2)//if horizontal line
//		{
//			//ensure x's are sorted
//			if(x1 > x2)
//			{
//				double temp = x2;
//				x2 = x1;
//				x1 = temp;
//			}
//			//clip
//			if(x2 < parent.cx1) return;
//			if(x1 > parent.cx2) return;
//			//just do edges
//			if(y1 < parent.cx1 || y1 > parent.cy2) return;
//			scanPoint(x1, (int)Math.ceil(y1));
//			scanPoint(x2, (int)Math.floor(y2));
//			return;
//		}
//		//ensure p1 is lower point
//		if(y2 < y1)
//		{
//			double ty = y1;
//			y1 = y2;
//			y2 = ty;
//			double tx = x1;
//			x1 = x2;
//			x2 = tx;
//		}
//		double dx = (x2 - x1) / (y2 - y1);
//		double x = x1;
//		int y = (int)Math.ceil(y1);
//		x += (Math.ceil(y1) - y1) * dx;
//		for(; y <= (int)Math.floor(y2); y++, x += dx)
//		{
//			if(y < parent.cy1) continue;
//			if(y >= parent.cy2) break;
//			scanPoint(Util.clip(x, parent.cx1, parent.cx2), y);
//		}
//	}
//	
//	/**
//	 * Adds a single point to the scan.
//	 * @param x The x coord.
//	 * @param y The y coord.
//	 */
//	private void scanPoint(double x, int y)
//	{
//		//put y's
//		minY = Math.min(minY, y);
//		maxY = Math.max(maxY, y);
//		//put x's
//		minXs[y] = Math.min(minXs[y], x);
//		maxXs[y] = Math.max(maxXs[y], x);
//	}
}
