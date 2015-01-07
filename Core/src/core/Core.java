package core;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import javax.swing.JFrame;
import render.image.Image;
import render.image.IntBackedImage;

public abstract class Core
{
	/**
	 * The tick rate of the game.
	 * (ticks per second)
	 */
	public final double tickRate;
	
	/**
	 * The tick rate in milliseconds per tick.
	 */
	public final double invTickRate;
	
	/**
	 * The currently running instance of the Core.
	 */
	public static Core instance;
	
	/**
	 * True if the game is running.
	 */
	private boolean running;
	
	/**
	 * The JFrame of the game.
	 */
	public GameFrame frame;
	
	/**
	 * The screen we are currently displaying.
	 */
	private IntBackedImage screen;
	
	/**
	 * The screen we are currently drawing on.
	 */
	private IntBackedImage buffer;
	
	/**
	 * This is the painter thread, which handles painting;
	 */
	private Thread painter;
	
	/**
	 * Ensures renders don't happen too fast.
	 */
	protected FrameBarrier barrier;
	
	/**
	 * The title of the program.
	 */
	private String title;
	
	public Core(String name)
	{
		this(name, 60);
	}
	
	public Core(String name, double tick)
	{
		tickRate = tick;
		invTickRate = 1000 / tickRate;
		
		instance = this;
		title = name;
		frame = new GameFrame(name);
		frame.setSize(100, 100);
		
		initFrame();
		
		frame.setFocusTraversalKeysEnabled(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		screen = new IntBackedImage(frame.getTrueWidth(), frame.getTrueHeight());
		buffer = new IntBackedImage(frame.getTrueWidth(), frame.getTrueHeight());
		
		painter = new Thread(new RenderTask(), name + " Core Painter");
		painter.setDaemon(true);
		barrier = new FrameBarrier();
		
		int type = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(1, 1).getType();
		for(Field field : BufferedImage.class.getFields())
		{
			if(field.getType() == int.class && field.getName().startsWith("TYPE"))
			{
				try
				{
					System.out.println(field.getName() + " " + field.getInt(null));
					if(field.getInt(null) == type)
					{
						System.out.println("DING!!!");
					}
				} catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void initFrame()
	{
		Rectangle r = frame.getGraphicsConfiguration().getBounds();
		
		double ratio = .5;
		r.width *= ratio;
		r.height *= ratio;
		frame.setBounds(r);
		frame.setLocationRelativeTo(null);
	}
	
	public void setFullscreen()
	{
		frame.setUndecorated(true);
		GraphicsDevice graphics = frame.getGraphicsConfiguration().getDevice();
		if(graphics.isFullScreenSupported())
		{
			graphics.setFullScreenWindow(frame);
		}else
		{
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}
	
	public abstract void init();
	
	public abstract void tick();
	
	public abstract void paint(Image image);
	
	private int titleUpdateTicker = 0;
	
	/**
	 * The main game loop.
	 */
	public void gameLoop()
	{	
		running = true;
		painter.start();
		
		double prevtime = System.currentTimeMillis();
		double passed;
		boolean sleep;
		long time;
		
		while(running)
		{	
			time = System.currentTimeMillis();
			passed = time - prevtime;
			prevtime = time;
			
			sleep = passed <= invTickRate;//if we are performing faster than we need to, sleep between runs
			
			//ensures ticks happen at uniform tickrate
			while(passed >= invTickRate)
			{
				passed -= invTickRate;
				
				tick();
				
				titleUpdateTicker++;
				if(titleUpdateTicker > tickRate)
				{
					titleUpdateTicker = 0;
					//update framerate in title
					frame.setTitle(title + ": " + (int)FrameRate.getFrameRate());
				}
			}
			
			prevtime -= passed;// revert by the extra amount
			
			//trip the renderer
			barrier.trip();
			
			if(sleep)
			{
				barrier.setFreeReign(true);
				try
				{
					Thread.sleep((long)invTickRate);
				} catch(InterruptedException ex)
				{
					//np, dude, we'll just go along with it
					//ex.printStackTrace();
				}
				barrier.setFreeReign(false);
			}
		}
	}
	
	/**
	 * Paints the screen onto the canvas.
	 */
	protected void corePaint()
	{
		if(buffer.getWidth() != Math.max(frame.getTrueWidth(), 1) || buffer.getHeight() != Math.max(frame.getTrueHeight(), 1))
		{
			buffer.resize(Math.max(frame.getTrueWidth(), 1), Math.max(frame.getTrueHeight(), 1));
		}
		
		//paint
		paint(buffer);
		
		//swap the buffers
		swapBuffers();
		
		//repaint the frame
		frame.repaint();
		//poll the frame rate ticker
		FrameRate.poll();
	}
	
	/**
	 * Just swaps the screen with the back buffer.
	 */
	private synchronized void swapBuffers()
	{
		IntBackedImage temp = screen;
		screen = buffer;
		buffer = temp;
	}
	
	/**
	 * Paints the frame.
	 * @param g The graphics to draw to.
	 */
	protected synchronized void paintFrame(Graphics g)
	{
		if(screen != null)
		{
			Insets ins = frame.getInsets();
			int x = ((frame.getWidth() - ins.left - ins.right) - screen.getWidth()) / 2;
			int y = ((frame.getHeight() - ins.top - ins.bottom) - screen.getHeight()) / 2;
			g.drawImage(screen.getBufferImage(), frame.getInsets().left + x, frame.getInsets().top + y, null);
		}
	}
}
