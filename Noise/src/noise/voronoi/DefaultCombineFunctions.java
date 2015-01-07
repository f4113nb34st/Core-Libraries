package noise.voronoi;

/**
 * 
 * A group of commonly used Voronoi CombineFunctions.
 * 
 * @author F4113nb34st
 *
 */
public class DefaultCombineFunctions
{
	/**
	 * Closest.
	 */
	public static final CombineFunction F1 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[0].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 1;
		}
	};
	/**
	 * Second Closest.
	 */
	public static final CombineFunction F2 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[1].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 2;
		}
	};
	/**
	 * Second minus First Closest.
	 */
	public static final CombineFunction F2_F1 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[1].dis - values[0].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 2;
		}
	};
	/**
	 * Third Closest.
	 */
	public static final CombineFunction F3 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[2].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 3;
		}
	};
	/**
	 * Third minus First Closest.
	 */
	public static final CombineFunction F3_F1 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[2].dis - values[0].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 3;
		}
	}; 
	/**Third minus Second Closest.*/
	public static final CombineFunction F3_F2 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[2].dis - values[1].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 3;
		}
	}; 
	/**
	 * Third minus Second plus First Closest.
	 */
	public static final CombineFunction F3_F2_F1 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[2].dis - values[1].dis + values[0].dis;
		}

		@Override
		public int getNumDistances()
		{
			return 3;
		}
		
	};
	/**
	 * The index of the nearest point
	 */
	public static final CombineFunction INDEX = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[0].index;
		}

		@Override
		public int getNumDistances()
		{
			return 1;
		}
	};
	/**The index of the second nearest point*/
	public static final CombineFunction INDEX2 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[1].index;
		}

		@Override
		public int getNumDistances()
		{
			return 2;
		}
	};
	/**
	 * The index of the third nearest point
	 */
	public static final CombineFunction INDEX3 = new CombineFunction()
	{
		@Override
		public double combineFunc(DistanceEntry[] values)
		{
			return values[2].index;
		}

		@Override
		public int getNumDistances()
		{
			return 3;
		}
	};
}
