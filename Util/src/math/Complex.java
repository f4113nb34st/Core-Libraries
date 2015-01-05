package math;

/**
 * 
 * Represents a complex number.
 * 
 * @author F4113nb34st
 *
 */
public class Complex
{
	/**
	 * The real bit of a Complex.
	 */
	public double real;
	/**
	 * The imaginary bit of a Complex.
	 */
	public double imaginary;
	
	/**
	 * Creates a new Complex Number.
	 * @param r
	 * @param i
	 */
	public Complex(double r, double i)
	{
		real = r;
		imaginary = i;
	}
	
	/**
	 * @return The length of this Complex.
	 */
	public double length()
	{
		return (real * real) + (imaginary * imaginary);
	}
	
	/**
	 * Adds the given Complex to this one.
	 * @param other The Complex to add.
	 * @return A new Complex with the resulting value.
	 */
	public Complex add(Complex other)
	{
		return new Complex(real + other.real, imaginary + other.imaginary);
	}
	
	/**
	 * Subtracts the given Complex from this one.
	 * @param other The Complex to subtract.
	 * @return A new Complex with the resulting value.
	 */
	public Complex subtract(Complex other)
	{
		return new Complex(real - other.real, imaginary - other.imaginary);
	}
	
	/**
	 * Multiplies this Complex by the given one.
	 * @param other The Complex to multiply by.
	 * @return A new Complex with the resulting value.
	 */
	public Complex multiply(Complex other)
	{
		double r = (real * other.real) - (imaginary * other.imaginary);
		double i = (imaginary * other.real) + (real * other.imaginary);
		return new Complex(r, i);
	}
	
	/**
	 * Divides this Complex by the given one.
	 * @param other The Complex to divide by.
	 * @return A new Complex with the resulting value.
	 */
	public Complex divide(Complex other)
	{
		double r = ((real * other.real) + (imaginary * -other.imaginary)) / ((other.real * other.real) + (other.imaginary * other.imaginary));
		double i = ((imaginary * other.real) - (real * -other.imaginary)) / ((other.real * other.real) + (other.imaginary * other.imaginary));
		return new Complex(r, i);
	}
}
