package noise;

/**
 * 
 * Class that generates noise values from seeds and coords. 
 * 
 * @author F4113nb34st
 *
 */
public class IntNoiseGenerator
{
	//random instance for rand values generation
	private NoiseGenerator gen = new NoiseGenerator();
	/**
	 * The permutation array.
	 */
	private final int permSize;
	private int[] permutation;
	private int permMask;
	
	/**
	 * Creates a new NoiseGenerator with a permutation table size of 128.
	 */
	public IntNoiseGenerator()
	{
		this(128);
	}
	
	/**
	 * Creates a new NoiseGenerator with the given permutation table size.
	 * @param size The size of the permutation table.
	 */
	public IntNoiseGenerator(int size)
	{
		permSize = size;
		setSeed(0);
	}
	
	/**
	 * Sets the current seed of this NoiseGenerator.
	 * @param seed The seed to set to.
	 */
	public void setSeed(long s)
	{
		gen.setSeed(s);
		
		//generate the permutation table (array of 0-256 shuffled)
		permutation = new int[permSize];
		permMask = permutation.length - 1;
		for(int i = 0; i < permutation.length; i++)
		{
			permutation[i] = i;
		}
		for(int i = 0; i < permutation.length; i++)
		{
			int index = (int)(gen.noise_gen(i) * permutation.length);
			int value = permutation[i];
			
			permutation[i] = permutation[index];
			permutation[index] = value;
		}
	}
	
	/**
	 * Generates a consistent, pseudo-random value between 0 and 1 for the given coords.
	 * @param coords The coords of the noise point.
	 * @return The resulting noise value.
	 */
	public int noise_gen(int... coords)
	{
		int value = 0;
		for(int coord : coords)
		{
			value = permutation[(coord + value) & permMask];
		}
		return value;
	}
}
