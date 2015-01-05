package render.image;

import java.lang.ref.SoftReference;
import util.DefaultInterpolations;
import util.Util;

/**
 * 
 * Performs a large number of tasks for Image manipulation with tedious optimizations.
 * 
 * @author F4113nb34st
 *
 */
public class ImageUtils
{
//	private static final double sqrtHalf = Math.sqrt(.5);
//	
//	//draw methods
//	
//		/**
//		 * Sets the clipping bounds for drawing operations.
//		 * @param x The clip x loc.
//		 * @param y The clip y loc.
//		 * @param w The clip width.
//		 * @param h The clip height.
//		 */
//		public void setClip(double x, double y, double w, double h)
//		{
//			cx1 = Math.max(x, 0);
//			cy1 = Math.max(y, 0);
//			cx2 = Math.min(x + w - 1, width);
//			cy2 = Math.min(y + h - 1, height);
//		}
//		
//		/**
//		 * Clears the clipping bounds for drawing operations.
//		 */
//		public void clearClip()
//		{
//			setClip(0, 0, width, height);
//		}
//		
//		//the clip coords that are used behind the scenes
//		protected double cx1;
//		protected double cy1;
//		protected double cx2;
//		protected double cy2;
//		
//		/**
//		 * Fills a horizontal scan line.
//		 * @param x1 The left edge.
//		 * @param x2 The right edge.
//		 * @param y The y coord.
//		 * @param val The grey comp.
//		 */
//		public void fillXScan(int x1, int x2, int y, double val)
//		{
//			if(x1 > x2)
//			{
//				return;
//			}
//			for(int x = x1; x <= x2; x++)
//			{
//				set(x, y, val);
//			}
//		}
//		
//		/**
//		 * Fills a horizontal scan line.
//		 * @param x1 The left edge.
//		 * @param x2 The right edge.
//		 * @param y The y coord.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 */
//		public void fillXScan(int x1, int x2, int y, int r, int g, int b)
//		{
//			if(x1 > x2)
//			{
//				return;
//			}
//			for(int x = x1; x <= x2; x++)
//			{
//				set(x, y, 255, r, g, b);
//			}
//		}
//		
//		/**
//		 * Fills a horizontal scan line.
//		 * @param x The x coord.
//		 * @param y1 The top edge.
//		 * @param y2 The bottom edge.
//		 * @param val The grey comp.
//		 */
//		public void fillYScan(int x, int y1, int y2, double val)
//		{
//			if(y1 > y2)
//			{
//				return;
//			}
//			for(int y = y1; y <= y2; y++)
//			{
//				set(x, y, val);
//			}
//		}
//		
//		/**
//		 * Fills a horizontal scan line.
//		 * @param x The x coord.
//		 * @param y1 The top edge.
//		 * @param y2 The bottom edge.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 */
//		public void fillYScan(int x, int y1, int y2, int r, int g, int b)
//		{
//			if(y1 > y2)
//			{
//				return;
//			}
//			for(int y = y1; y <= y2; y++)
//			{
//				set(x, y, 255, r, g, b);
//			}
//		}
//		
//		/**
//		 * Fills an anti-aliased horizontal scan line.
//		 * @param x1 The left edge.
//		 * @param x2 The right edge.
//		 * @param y The y coord.
//		 * @param val The grey comp.
//		 */
//		public void fillXScanAA(double x1, double x2, int y, double val)
//		{
//			fillXScan((int)Math.ceil(x1), (int)Math.floor(x2), y, val);
//			if(Math.floor(x1) < Math.ceil(x2))
//			{
//				blend((int)Math.floor(x1), y, Math.ceil(x1) - x1, val);
//				blend((int)Math.ceil(x2), y, x2 - Math.floor(x2), val);
//			}
//		}
//		
//		/**
//		 * Fills an anti-aliased horizontal scan line.
//		 * @param x1 The left edge.
//		 * @param x2 The right edge.
//		 * @param y The y coord.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 */
//		public void fillXScanAA(double x1, double x2, int y, int r, int g, int b)
//		{
//			fillXScan((int)Math.ceil(x1), (int)Math.floor(x2), y, r, g, b);
//			if(Math.floor(x1) < Math.ceil(x2))
//			{
//				blend((int)Math.floor(x1), y, (int)((Math.ceil(x1) - x1) *  255), r, g, b);
//				blend((int)Math.ceil(x2), y, (int)((x2 - Math.floor(x2)) * 255), r, g, b);
//			}
//		}
//		
//		/**
//		 * Fills an anti-aliased horizontal scan line.
//		 * @param x The x coord.
//		 * @param y1 The top edge.
//		 * @param y2 The bottom edge.
//		 * @param val The grey comp.
//		 */
//		public void fillYScanAA(int x, double y1, double y2, double val)
//		{
//			fillYScan(x, (int)Math.ceil(y1), (int)Math.floor(y2), val);
//			if(Math.floor(y1) < Math.ceil(y2))
//			{
//				blend(x, (int)Math.floor(y1), Math.ceil(y1) - y1, val);
//				blend(x, (int)Math.ceil(y2), y2 - Math.floor(y2), val);
//			}
//		}
//		
//		/**
//		 * Fills an anti-aliased horizontal scan line.
//		 * @param x The x coord.
//		 * @param y1 The top edge.
//		 * @param y2 The bottom edge.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 */
//		public void fillYScanAA(int x, double y1, double y2, int r, int g, int b)
//		{
//			fillYScan(x, (int)Math.ceil(y1), (int)Math.floor(y2), r, g, b);
//			if(Math.floor(y1) < Math.ceil(y2))
//			{
//				blend(x, (int)Math.floor(y1), (int)((Math.ceil(y1) - y1) * 255), r, g, b);
//				blend(x, (int)Math.ceil(y2), (int)((y2 - Math.floor(y2)) * 255), r, g, b);
//			}
//		}
//		
//		/**
//		 * Fills this image with the given greyscale color.
//		 * @param val The grey comp.
//		 */
//		public void fill(double val)
//		{
//			fillRect(0, 0, width, height, val, false);
//		}
//		
//		/**
//		 * Fills this image with the given color.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 */
//		public void fill(int r, int g, int b)
//		{
//			fillRect(0, 0, width, height, r, g, b, false);
//		}
//		
//		/**
//		 * Fills the given rectangle with the given greyscale color.
//		 * @param x The rect x loc.
//		 * @param y The rect y loc.
//		 * @param w The rect width.
//		 * @param h The rect height.
//		 * @param val The grey comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillRect(double x, double y, double w, double h, double val, final boolean aa)
//		{
//			//if completely off screen don't do a thing
//			if(x + w - 1 < cx1 || x > cx2 || y + h - 1 < cy1 || y > cy2)
//			{
//				return;
//			}
//			double minJ = Math.max(y, cy1);
//			double maxJ = Math.min(y + h - 1, cy2);
//			for(int j = (int)Math.ceil(minJ); j <= (int)Math.floor(maxJ); j++)
//			{
//				//find edges
//				double x1 = Math.max(x, cx1);
//				double x2 = Math.min(x + w - 1, cx2);
//				
//				//fill scan
//				if(aa)
//				{
//					fillXScanAA(x1, x2, j, val);
//				}else
//				{
//					fillXScan((int)Math.ceil(x1), (int)Math.floor(x2), j, val);
//				}
//			}
//			if(aa)
//			{
//				double minI = Math.max(x, cx1);
//				double maxI = Math.min(x + w - 1, cx2);
//				for(int i = (int)Math.floor(minI); i <= (int)Math.ceil(maxI); i++)
//				{
//					blend(i, (int)Math.floor(minJ), Math.ceil(minJ) - minJ, val);
//					blend(i, (int)Math.ceil(maxJ), maxJ - Math.floor(maxJ), val);
//				}
//			}
//		}
//		
//		/**
//		 * Fills the given rectangle with the given greyscale color.
//		 * @param x The rect x loc.
//		 * @param y The rect y loc.
//		 * @param w The rect width.
//		 * @param h The rect height.
//		 * @param a The alpha comp.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillRect(double x, double y, double w, double h, int r, int g, int b, final boolean aa)
//		{
//			//if completely off screen don't do a thing
//			if(x + w - 1 < cx1 || x > cx2 || y + h - 1 < cy1 || y > cy2)
//			{
//				return;
//			}
//			double minJ = Math.max(y, cy1);
//			double maxJ = Math.min(y + h - 1, cy2);
//			for(int j = (int)Math.ceil(minJ); j <= (int)Math.floor(maxJ); j++)
//			{
//				//find edges
//				double x1 = Math.max(x, cx1);
//				double x2 = Math.min(x + w - 1, cx2);
//				
//				//fill scan
//				if(aa)
//				{
//					fillXScanAA(x1, x2, j, r, g, b);
//				}else
//				{
//					fillXScan((int)Math.ceil(x1), (int)Math.floor(x2), j, r, g, b);
//				}
//			}
//			if(aa)
//			{
//				double minI = Math.max(x, cx1);
//				double maxI = Math.min(x + w - 1, cx2);
//				for(int i = (int)Math.floor(minI); i <= (int)Math.ceil(maxI); i++)
//				{
//					blend(i, (int)Math.floor(minJ), (int)((Math.ceil(minJ) - minJ) * 255), r, g, b);
//					blend(i, (int)Math.ceil(maxJ), (int)((maxJ - Math.floor(maxJ)) * 255), r, g, b);
//				}
//			}
//		}
//
//		/**
//		 * Fills the given rounded rectangle with the given greyscale color.
//		 * @param x The rect x loc.
//		 * @param y The rect y loc.
//		 * @param w The rect width.
//		 * @param h The rect height.
//		 * @param xradius The x rounding radius.
//		 * @param yradius The y rounding radius.
//		 * @param val The grey comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillRoundedRect(double x, double y, double w, double h, double xradius, double yradius, double val, final boolean aa)
//		{
//			//if completely off screen don't do a thing
//			if(x + w - 1 < cx1 || x > cx2 || y + h - 1 < cy1 || y > cy2)
//			{
//				return;
//			}
//			double minJ = Math.max(y, cy1);
//			double maxJ = Math.min(y + h - 1, cy2);
//			for(int j = (int)Math.ceil(minJ); j <= (int)Math.floor(maxJ); j++)
//			{
//				//find edges
//				double x1;
//				double x2;
//				if(j >= y + yradius && j <= y + h - 1 - yradius)
//				{
//					x1 = Math.max(x, cx1);
//					x2 = Math.min(x + w - 1, cx2);
//				}else
//				{
//					double dy = Math.max((y + yradius) - j, j - (y + h - 1 - yradius)) / yradius;
//					double dx = Math.sqrt(1 - dy * dy);
//					x1 = Math.max(x + xradius - (dx * xradius), cx1);
//					x2 = Math.min(x + w - 1 - xradius + (dx * xradius), cx2);
//					
//					if(aa && dy >= dx)
//					{
//						x1 = Math.max(Math.ceil(x + xradius - (dx * xradius)), cx1);
//						x2 = Math.min(Math.floor(x + w - 1 - xradius + (dx * xradius)), cx2);
//					}
//				}
//				
//				//fill scan
//				if(aa)
//				{
//					fillXScanAA(x1, x2, j, val);
//				}else
//				{
//					fillXScan((int)x1, (int)x2, j, val);
//				}
//			}
//			if(aa)
//			{
//				double minI = Math.max(x + xradius * (1 - sqrtHalf), cx1);
//				double maxI = Math.min(x + w - 1 - xradius * (1 - sqrtHalf), cx2);
//				for(int i = (int)Math.floor(minI); i <= (int)Math.ceil(maxI); i++)
//				{
//					double dx = Math.max((x + xradius) - i, i - (x + w - 1 - xradius)) / xradius;
//					double dy = Math.sqrt(1 - dx * dx);
//					double y1 = Math.max(y + yradius - (dy * yradius), cy1);
//					double y2 = Math.min(y + h - 1 - yradius + (dy * yradius), cy2);
//					
//					blend(i, (int)Math.floor(y1), Math.ceil(y1) - y1, val);
//					blend(i, (int)Math.ceil(y2), y2 - Math.floor(y2), val);
//				}
//				for(int i = (int)Math.max(x + xradius, cx1); i <= Math.min(x + w - 1 - xradius, cx2); i++)
//				{
//					blend(i, (int)Math.floor(minJ), Math.ceil(minJ) - minJ, val);
//					blend(i, (int)Math.ceil(maxJ), maxJ - Math.floor(maxJ), val);
//				}
//			}
//		}
//		
//		/**
//		 * Fills the given rounded rectangle with the given greyscale color.
//		 * @param x The rect x loc.
//		 * @param y The rect y loc.
//		 * @param w The rect width.
//		 * @param h The rect height.
//		 * @param xradius The x rounding radius.
//		 * @param yradius The y rounding radius.
//		 * @param a The alpha comp.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillRoundedRect(double x, double y, double w, double h, double xradius, double yradius, int r, int g, int b, final boolean aa)
//		{
//			//if completely off screen don't do a thing
//			if(x + w - 1 < cx1 || x > cx2 || y + h - 1 < cy1 || y > cy2)
//			{
//				return;
//			}
//			double minJ = Math.max(y, cy1);
//			double maxJ = Math.min(y + h - 1, cy2);
//			for(int j = (int)Math.ceil(minJ); j <= (int)Math.floor(maxJ); j++)
//			{
//				//find edges
//				double x1;
//				double x2;
//				if(j >= y + yradius && j <= y + h - 1 - yradius)
//				{
//					x1 = Math.max(x, cx1);
//					x2 = Math.min(x + w - 1, cx2);
//				}else
//				{
//					double dy = Math.max((y + yradius) - j, j - (y + h - 1 - yradius)) / yradius;
//					double dx = Math.sqrt(1 - dy * dy);
//					x1 = Math.max(x + xradius - (dx * xradius), cx1);
//					x2 = Math.min(x + w - 1 - xradius + (dx * xradius), cx2);
//					
//					if(aa && dy >= dx)
//					{
//						x1 = Math.max(Math.ceil(x + xradius - (dx * xradius)), cx1);
//						x2 = Math.min(Math.floor(x + w - 1 - xradius + (dx * xradius)), cx2);
//					}
//				}
//				
//				//fill scan
//				if(aa)
//				{
//					fillXScanAA(x1, x2, j, r, g, b);
//				}else
//				{
//					fillXScan((int)x1, (int)x2, j, r, g, b);
//				}
//			}
//			if(aa)
//			{
//				double minI = Math.max(x + xradius * (1 - sqrtHalf), cx1);
//				double maxI = Math.min(x + w - 1 - xradius * (1 - sqrtHalf), cx2);
//				for(int i = (int)Math.floor(minI); i <= (int)Math.ceil(maxI); i++)
//				{
//					double dx = Math.max((x + xradius) - i, i - (x + w - 1 - xradius)) / xradius;
//					double dy = Math.sqrt(1 - dx * dx);
//					double y1 = Math.max(y + yradius - (dy * yradius), cy1);
//					double y2 = Math.min(y + h - 1 - yradius + (dy * yradius), cy2);
//					
//					blend(i, (int)Math.floor(y1), (int)((Math.ceil(y1) - y1) * 255), r, g, b);
//					blend(i, (int)Math.ceil(y2), (int)((y2 - Math.floor(y2)) * 255), r, g, b);
//				}
//				for(int i = (int)Math.max(x + xradius, cx1); i <= Math.min(x + w - 1 - xradius, cx2); i++)
//				{
//					blend(i, (int)Math.floor(minJ), (int)((Math.ceil(minJ) - minJ) * 255), r, g, b);
//					blend(i, (int)Math.ceil(maxJ), (int)((maxJ - Math.floor(maxJ)) * 255), r, g, b);
//				}
//			}
//		}
//		
//		/**
//		 * Fills the given ellipse with the given greyscale color.
//		 * @param x The oval x loc.
//		 * @param y The oval y loc.
//		 * @param w The oval width.
//		 * @param h The oval height.
//		 * @param val The grey comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillOval(double x, double y, double w, double h, double val, final boolean aa)
//		{
//			//if completely off screen don't do a thing
//			if(x + w - 1 < cx1 || x > cx2 || y + h - 1 < cy1 || y > cy2)
//			{
//				return;
//			}
//			double xradius = w / 2D;
//			double yradius = h / 2D;
//			double minJ = Math.max(y, cy1);
//			double maxJ = Math.min(y + h - 1, cy2);
//			for(int j = (int)Math.ceil(minJ); j <= (int)Math.floor(maxJ); j++)
//			{
//				//find edges
//				double dy = Math.max((y + yradius) - j, j - (y + h - 1 - yradius)) / yradius;
//				double dx = Math.sqrt(1 - dy * dy);
//				double x1 = Math.max(x + xradius - (dx * xradius), cx1);
//				double x2 = Math.min(x + w - 1 - xradius + (dx * xradius), cx2);
//				
//				if(aa && dy >= dx)
//				{
//					x1 = Math.max(Math.ceil(x + xradius - (dx * xradius)), cx1);
//					x2 = Math.min(Math.floor(x + w - 1 - xradius + (dx * xradius)), cx2);
//				}
//				
//				//fill scan
//				if(aa)
//				{
//					fillXScanAA(x1, x2, j, val);
//				}else
//				{
//					fillXScan((int)x1, (int)x2, j, val);
//				}
//			}
//			if(aa)
//			{
//				double minI = Math.max(x + xradius * (1 - sqrtHalf), cx1);
//				double maxI = Math.min(x + w - 1 - xradius * (1 - sqrtHalf), cx2);
//				for(int i = (int)Math.floor(minI); i <= (int)Math.ceil(maxI); i++)
//				{
//					double dx = Math.max((x + xradius) - i, i - (x + w - 1 - xradius)) / xradius;
//					double dy = Math.sqrt(1 - dx * dx);
//					double y1 = Math.max(y + yradius - (dy * yradius), cy1);
//					double y2 = Math.min(y + h - 1 - yradius + (dy * yradius), cy2);
//					
//					blend(i, (int)Math.floor(y1), Math.ceil(y1) - y1, val);
//					blend(i, (int)Math.ceil(y2), y2 - Math.floor(y2), val);
//				}
//			}
//		}
//		
//		/**
//		 * Fills the given ellipse with the given color.
//		 * @param x The oval x loc.
//		 * @param y The oval y loc.
//		 * @param w The oval width.
//		 * @param h The oval height.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillOval(double x, double y, double w, double h, int r, int g, int b, final boolean aa)
//		{
//			//if completely off screen don't do a thing
//			if(x + w - 1 < cx1 || x > cx2 || y + h - 1 < cy1 || y > cy2)
//			{
//				return;
//			}
//			double xradius = w / 2D;
//			double yradius = h / 2D;
//			double minJ = Math.max(y, cy1);
//			double maxJ = Math.min(y + h - 1, cy2);
//			for(int j = (int)Math.ceil(minJ); j <= (int)Math.floor(maxJ); j++)
//			{
//				//find edges
//				double dy = Math.max((y + yradius) - j, j - (y + h - 1 - yradius)) / yradius;
//				double dx = Math.sqrt(1 - dy * dy);
//				double x1 = Math.max(x + xradius - (dx * xradius), cx1);
//				double x2 = Math.min(x + w - 1 - xradius + (dx * xradius), cx2);
//				
//				if(aa && dy >= dx)
//				{
//					x1 = Math.max(Math.ceil(x + xradius - (dx * xradius)), cx1);
//					x2 = Math.min(Math.floor(x + w - 1 - xradius + (dx * xradius)), cx2);
//				}
//				
//				//fill scan
//				if(aa)
//				{
//					fillXScanAA(x1, x2, j, r, g, b);
//				}else
//				{
//					fillXScan((int)x1, (int)x2, j, r, g, b);
//				}
//			}
//			if(aa)
//			{
//				double minI = Math.max(x + xradius * (1 - sqrtHalf), cx1);
//				double maxI = Math.min(x + w - 1 - xradius * (1 - sqrtHalf), cx2);
//				for(int i = (int)Math.floor(minI); i <= (int)Math.ceil(maxI); i++)
//				{
//					double dx = Math.max((x + xradius) - i, i - (x + w - 1 - xradius)) / xradius;
//					double dy = Math.sqrt(1 - dx * dx);
//					double y1 = Math.max(y + yradius - (dy * yradius), cy1);
//					double y2 = Math.min(y + h - 1 - yradius + (dy * yradius), cy2);
//					
//					blend(i, (int)Math.floor(y1), (int)((Math.ceil(y1) - y1) * 255), r, g, b);
//					blend(i, (int)Math.ceil(y2), (int)((y2 - Math.floor(y2)) * 255), r, g, b);
//				}
//			}
//		}
//		
//		/**
//		 * Fills the given circle with the given greyscale color.
//		 * @param x The circle x loc.
//		 * @param y The circle y loc.
//		 * @param radius The circle radius.
//		 * @param val The grey comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillCircle(double x, double y, double radius, double val, final boolean aa)
//		{
//			fillOval(x - radius, y - radius, radius * 2, radius * 2, val, aa);
//		}
//		
//		/**
//		 * Fills the given circle with the given color.
//		 * @param x The circle x loc.
//		 * @param y The circle y loc.
//		 * @param radius The circle radius.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillCircle(double x, double y, double radius, int r, int g, int b, final boolean aa)
//		{
//			fillOval(x - radius, y - radius, radius * 2, radius * 2, r, g, b, aa);
//		}
//		
//		//the polygon scanner
//		private SoftReference<Scanner> scannerRef;
//		
//		protected Scanner getScanner()
//		{
//			Scanner scanner = null;
//			if(scannerRef != null)
//			{
//				scanner = scannerRef.get();
//			}
//			if(scanner == null)
//			{
//				scanner = new Scanner(this);
//				scannerRef = new SoftReference<Scanner>(scanner);
//			}
//			return scanner;
//		}
//		
//		/**
//		 * Fills the given triangle with the given greyscale color.
//		 * @param x1 The first x loc.
//		 * @param y1 The first y loc.
//		 * @param x2 The second x loc.
//		 * @param y2 The second y loc.
//		 * @param x3 The third x loc.
//		 * @param y3 The third y loc.
//		 * @param val The grey comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillTriangle(double x1, double y1, double x2, double y2, double x3, double y3, double val, final boolean aa)
//		{
//			fillPolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, val, aa);
//		}
//		
//		/**
//		 * Fills the given triangle with the given color.
//		 * @param x1 The first x loc.
//		 * @param y1 The first y loc.
//		 * @param x2 The second x loc.
//		 * @param y2 The second y loc.
//		 * @param x3 The third x loc.
//		 * @param y3 The third y loc.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillTriangle(double x1, double y1, double x2, double y2, double x3, double y3, int r, int g, int b, final boolean aa)
//		{
//			fillPolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, r, g, b, aa);
//		}
//		
//		/**
//		 * Fills the given polygon with the given greyscale color.
//		 * @param xs The vert x locs.
//		 * @param ys The vert y locs.
//		 * @param val The grey comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillPolygon(double[] xs, double[] ys, double val, final boolean aa)
//		{
//			Scanner scanner = getScanner();
//			scanner.beginScan();
//			int i2;
//			for(int i = 0; i < xs.length; i++)
//			{
//				i2 = (i + 1) % xs.length;
//				scanner.scanLine(xs[i], ys[i], xs[i2], ys[i2]);
//			}
//			scanner.fillScan(val, aa);
//		}
//		
//		/**
//		 * Fills the given polygon with the given color.
//		 * @param xs The vert x locs.
//		 * @param ys The vert y locs.
//		 * @param r The red comp.
//		 * @param g The green comp.
//		 * @param b The blue comp.
//		 * @param aa True if the scan is anti-aliased.
//		 */
//		public void fillPolygon(double[] xs, double[] ys, int r, int g, int b, final boolean aa)
//		{
//			Scanner scanner = getScanner();
//			scanner.beginScan();
//			int i2;
//			for(int i = 0; i < xs.length; i++)
//			{
//				i2 = (i + 1) % xs.length;
//				scanner.scanLine(xs[i], ys[i], xs[i2], ys[i2]);
//			}
//			scanner.fillScan(r, g, b, aa);
//		}
//		
//		/**
//		 * Draws the given image at the given location.
//		 * @param image The image to draw.
//		 * @param x The x loc.
//		 * @param y The y loc.
//		 */
//		public void drawImage(Image image, int x, int y)
//		{
//			int w = image.getWidth();
//			int h = image.getHeight();
//			//if completely off screen don't do a thing
//			if(x + w < (int)cx1 || x >= (int)cx2 || y + h < (int)cy1 || y >= (int)cy2)
//			{
//				return;
//			}
//			for(int j = 0; j < h; j++)
//			{
//				//skip non-visible bits
//				if(j < (int)cy1) 
//				{
//					j = (int)cy1 - 1;
//					continue;
//				}
//				if(j >= (int)cy2) break;
//				for(int i = 0; i < w; i++)
//				{
//					//skip non-visible bits
//					if(i < (int)cx1) 
//					{
//						i = (int)cx1 - 1;
//						continue;
//					}
//					if(i >= (int)cx2) break;
//					set(x + i, y + j, image.getAlpha(i, j), image.getRed(i, j), image.getGreen(i, j), image.getBlue(i, j));
//				}
//			}
//		}
//		
//		/**
//		 * Blends the given image at the given location. (same as drawing but handles alpha)
//		 * @param image The image to draw.
//		 * @param x The x loc.
//		 * @param y The y loc.
//		 */
//		public void blendImage(Image image, int x, int y)
//		{
//			blendImage(image, x, y, 1, 1, 1, 1);
//		}
//		
//		/**
//		 * Blends the given image at the given location with the given component modifiers. (same as drawing but handles alpha)
//		 * @param image The image to draw.
//		 * @param x The x loc.
//		 * @param y The y loc.
//		 * @param a The alpha multiplier.
//		 * @param r The red multiplier.
//		 * @param g The green multiplier.
//		 * @param b The blue multiplier.
//		 */
//		public void blendImage(Image image, int x, int y, double a, double r, double g, double b)
//		{
//			int w = image.getWidth();
//			int h = image.getHeight();
//			//if completely off screen don't do a thing
//			if(x + w < (int)cx1 || x > (int)cx2 || y + h < (int)cy1 || y > (int)cy2)
//			{
//				return;
//			}
//			for(int j = 0; j < h; j++)
//			{
//				//skip non-visible bits
//				if(y + j < (int)cy1) 
//				{
//					j = (int)cy1 - 1 - y;
//					continue;
//				}
//				if(y + j >= (int)cy2) break;
//				for(int i = 0; i < w; i++)
//				{
//					//skip non-visible bits
//					if(x + i < (int)cx1) 
//					{
//						i = (int)cx1 - 1 - x;
//						continue;
//					}
//					if(x + i >= (int)cx2) break;
//					blend(x + i, y + j, (int)(image.getAlpha(i, j) * a), (int)(image.getRed(i, j) * r), (int)(image.getGreen(i, j) * g), (int)(image.getBlue(i, j) * b));
//				}
//			}
//		}
//		
//		/**
//		 * Draws the given scaled image at the given location.
//		 * @param image The image to draw.
//		 * @param x The x loc.
//		 * @param y The y loc.
//		 * @param scaleX The x scale.
//		 * @param scaleY The y scale.
//		 */
//		public void scaleImage(Image image, int x, int y, double scaleX, double scaleY)
//		{
//			int w = image.getWidth();
//			int h = image.getHeight();
//			int newW = (int)(w * scaleX);
//			int newH = (int)(h * scaleY);
//			//if completely off screen don't do a thing
//			if(x + w < (int)cx1 || x > (int)cx2 || y + h < (int)cy1 || y > (int)cy2)
//			{
//				return;
//			}
//			for(int j = 0; j < newH; j++)
//			{
//				//skip non-visible bits
//				if(j < (int)cy1) 
//				{
//					j = (int)cy1 - 1;
//					continue;
//				}
//				if(j >= (int)cy2) break;
//				//get botY
//				int bottomY = (int)(j / scaleY);
//				//get topY
//				int topY = bottomY + 1;
//				//get blend part for y
//				double blendY = (j / scaleY) - bottomY;
//				for(int i = 0; i < newW; i++)
//				{
//					//skip non-visible bits
//					if(i < (int)cx1) 
//					{
//						i = (int)cx1 - 1;
//						continue;
//					}
//					if(i >= (int)cx2) break;
//					//get botX
//					int bottomX = (int)(i / scaleX);
//					//get topX
//					int topX = bottomX + 1;
//					//get blend part for x
//					double blendX = (i / scaleX) - bottomX;
//					
//					double axBotyBot = image.getAlpha(bottomX, bottomY);
//					double axBotyTop = image.getAlpha(bottomX, topY);
//					double axTopyBot = image.getAlpha(topX, bottomY);
//					double axTopyTop = image.getAlpha(topX, topY);
//					
//					//interp between xbots and xtops
//					double xBotInterpA = DefaultInterpolations.LINEAR.interpolate(axBotyBot, axBotyTop, blendY);
//					double xTopInterpA = DefaultInterpolations.LINEAR.interpolate(axTopyBot, axTopyTop, blendY);
//					double xBotInterpR = DefaultInterpolations.LINEAR.interpolate(image.getRed(bottomX, bottomY) * axBotyBot, image.getRed(bottomX, topY) * axBotyTop, blendY);
//					double xTopInterpR = DefaultInterpolations.LINEAR.interpolate(image.getRed(topX, bottomY) * axTopyBot,    image.getRed(topX, topY) * axTopyTop, blendY);
//					double xBotInterpG = DefaultInterpolations.LINEAR.interpolate(image.getGreen(bottomX, bottomY) * axBotyBot, image.getGreen(bottomX, topY) * axBotyTop, blendY);
//					double xTopInterpG = DefaultInterpolations.LINEAR.interpolate(image.getGreen(topX, bottomY) * axTopyBot,    image.getGreen(topX, topY) * axTopyTop, blendY);
//					double xBotInterpB = DefaultInterpolations.LINEAR.interpolate(image.getBlue(bottomX, bottomY) * axBotyBot, image.getBlue(bottomX, topY) * axBotyTop, blendY);
//					double xTopInterpB = DefaultInterpolations.LINEAR.interpolate(image.getBlue(topX, bottomY) * axTopyBot,    image.getBlue(topX, topY) * axTopyTop, blendY);
//					
//					//interp interps
//					double A = DefaultInterpolations.LINEAR.interpolate(xBotInterpA, xTopInterpA, blendX);
//					double R = DefaultInterpolations.LINEAR.interpolate(xBotInterpR, xTopInterpR, blendX) / A;
//					double G = DefaultInterpolations.LINEAR.interpolate(xBotInterpG, xTopInterpG, blendX) / A;
//					double B = DefaultInterpolations.LINEAR.interpolate(xBotInterpB, xTopInterpB, blendX) / A;
//					
//					A = Util.clip(A, 0, 255);
//					R = Util.clip(R, 0, 255);
//					G = Util.clip(G, 0, 255);
//					B = Util.clip(B, 0, 255);
//					
//					blend(x + i, y + j, (int)A, (int)R, (int)G, (int)B);
//				}
//			}
//		}
}
