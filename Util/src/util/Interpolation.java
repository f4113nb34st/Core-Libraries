package util;

/**
 * 
 * Represents a method for Interpolating.
 * 
 * @author F4113nb34st
 *
 */
public interface Interpolation
{
	/**
	 * Returns true if this interpolation function needs extra values.
	 * @return True if extended.
	 */
	public boolean extended();
	
	/**
	 * Interpolates between the given values using this type interpolation. (top = 1, bottom = 0)
	 * @param bottom The value at 0.
	 * @param top The value at 1.
	 * @param mu The distance between.
	 * @return The interpolated value.
	 */
	public double interpolate(double bottom, double top, double mu);
	
	/**
	 * Interpolates between the given values using this type interpolation. (top = 1, bottom = 0)
	 * @param past The value at -1.
	 * @param bottom The value at 0.
	 * @param top The value at 1.
	 * @param future The value at 2.
	 * @param mu The distance between.
	 * @return The interpolated value.
	 */
	public double interpolate(double past, double bottom, double top, double future, double mu);
	
	/**
	 * Interpolates between the given values using this type interpolation. (top = 1, bottom = 0)
	 * @param past The value at -1.
	 * @param bottom The value at 0.
	 * @param top The value at 1.
	 * @param future The value at 2.
	 * @param mu The distance between.
	 * @param tension The tension for HERMITE.
	 * @param bias The bias for HERMITE.
	 * @return The interpolated value.
	 */
	public double interpolate(double past, double bottom, double top, double future, double mu, double tension, double bias);
}
