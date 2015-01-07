package filter;

import noise.NoiseGenerator;
import render.image.Image;
import render.image.NoiseImage;
import render.node.*;
import util.concurrent.ArrayTask;
import util.concurrent.ThreadPool;

public class FractalFilter extends Filter
{	
	/**
	 * The NoiseGenerator instance for this FractalFilter.
	 */
	private NoiseGenerator gen = new NoiseGenerator();
	/**
	 * The finest octave.
	 */
	public int fineOctave = 0;
	/**
	 * The broadest octave.
	 */
	public int broadOctave = 8;
	/**
	 * The persistence of the octaves.
	 */
	public double persistence = .5;
	
	public FractalFilter(PeriodicNode in)
	{
		super(in);
	}
	
	@Override
	public SeededNode setSeed(long s)
	{
		gen.setSeed(s);
		return this;
	}
	
	/**
	 * Sets the octaves of this FractalFilter.
	 * @param fine The fine octave.
	 * @param broad The broad octave.
	 */
	public FractalFilter setOctaves(int fine, int broad)
	{
		fineOctave = fine;
		broadOctave = broad;
		return this;
	}
	
	/**
	 * Sets the persistence of this FractalFilter.
	 * @param persis The persistence.
	 */
	public SeededNode setPersistence(double persis)
	{
		persistence = persis;
		return this;
	}

	@Override
	public void fill(final Image array, ThreadPool pool)
	{
		double periodXInit = ((PeriodicNode)inputs[0]).getPeriodX();
		double periodYInit = ((PeriodicNode)inputs[0]).getPeriodY();
		
		//create array of octaves
		final NoiseImage[] octaves = new NoiseImage[broadOctave + 1 - fineOctave];
		
		//starting at top octave and going down
		for(int octave = broadOctave; octave >= fineOctave; octave--)
		{
			//create new array for octave
			octaves[octave - fineOctave] = new NoiseImage(array.getWidth(), array.getHeight());
			//set the periods
			((PeriodicNode)inputs[0]).setPeriods(1 << octave, 1 << octave);
			//set the seed
			if(inputs[0] instanceof SeededNode)
			{
				//generate the octave seed
				long octaveSeed = (long)(Long.MAX_VALUE * gen.noise_gen((double)octave));
				((SeededNode)inputs[0]).setSeed(octaveSeed);
			}
			//fill from noise function with a random seed
			if(inputs[0] instanceof MTNode)
			{
				((MTNode)inputs[0]).fill(octaves[octave - fineOctave], pool);
			}else
			{
				inputs[0].fill(octaves[octave - fineOctave]);
			}
		}
		
		if(pool == null)
		{
			new EvaluateColumnTask(array, octaves).run();
		}else
		{
			pool.addGlobalTask(new EvaluateColumnTask(array, octaves));
			pool.startAndWait();
		}
		
		//reset the periods
		((PeriodicNode)inputs[0]).setPeriods(periodXInit, periodYInit);
	}
	
	private class EvaluateColumnTask extends ArrayTask
	{
		private Image output;
		private Image[] octaves;
		
		public EvaluateColumnTask(Image out, Image[] octs)
		{
			super(0, out.getWidth() - 1);
			output = out;
			octaves = octs;
		}

		@Override
		public void run(int x)
		{
			//for all y's in column
			for(int y = 0; y < output.getHeight(); y++)
			{
				//init value
				double value = 0;
				//initial amplitude
				double currentAmp = 1;
				//amplitude so far
				double totalAmp = 0;
				//stating at top octave and going down
				for(int octave = broadOctave; octave >= fineOctave; octave--)
				{
					//increment value by octave value * currentAmp
					value += octaves[octave - fineOctave].get(x, y) * currentAmp;
					//increase total amp
					totalAmp += currentAmp;
					//modify current amp
					currentAmp *= persistence;
				}
				//normalizes the array
				value /= totalAmp;
				
				//set value
				output.set(x, y, value);
			}
		}
	}
}
