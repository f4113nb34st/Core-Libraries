package math;

/**
 * 
 * A math class for dealing with Complex Number operations.
 * 
 * @author F4113nb34st
 *
 */
public class ComplexMath
{
	/**
	 * Returns the absolute value of the given Complex.
	 * @param com The Complex to abs.
	 * @return The absolute value of the Complex.
	 */
	public static final double abs(Complex com)
	{
		return com.length();
	}
	
	/**
	 * Returns the cosine of the given Complex.
	 * @param com The Complex to cos.
	 * @return A new Complex with the resulting value.
	 */
	public static final Complex cos(Complex com)
	{
		double r =  Math.cos(com.real) * Math.cosh(com.imaginary);
		double i = -Math.sin(com.real) * Math.sinh(com.imaginary);
		return new Complex(r, i);
	}
	
	/**
	 * Returns e to the power of the given Complex.
	 * @param com The Complex to exp.
	 * @return A new Complex with the resulting value.
	 */
	public static final Complex exp(Complex com)
	{
		double ea = Math.pow(Math.E, com.real);
		double r = Math.cos(com.imaginary) * ea;
		double i = Math.sin(com.imaginary) * ea;
		return new Complex(r, i);
	}
	
	/**
	 * Returns the natural logarithm of the given Complex.
	 * @param com The Complex to log.
	 * @return A new Complex with the resulting value.
	 */
	public static final Complex log(Complex com)
	{
		double r = Math.log(abs(com));
		double i = Arg(com);
		return new Complex(r, i);
	}
	
	/**
	 * Returns the base 10 logarithm of the given Complex.
	 * @param com The Complex to log10.
	 * @return A new Complex with the resulting value.
	 */
	public static final Complex log10(Complex com)
	{
		Complex numerator = log(com);
		Complex denominator = log(new Complex(10, 0));
		return numerator.divide(denominator);
	}
	
	/**
	 * Returns Arg of the given Complex.
	 * @param com The Complex to Arg.
	 * @return The Arg of the Complex.
	 */
	public static final double Arg(Complex com)
	{
		if(com.real == 0)
		{
			return Double.NaN;
		}else
		{
			if(com.real < 0 && com.imaginary == 0)
			{
				return Math.PI;
			}else
			{
				return 2 * Math.atan2(com.imaginary, com.length() + com.real);
			}
		}
	}
	
	/**
	 * Returns the d power of the given Complex.
	 * @param com The Complex to raise.
	 * @param pow The Complex to raise to.
	 * @return A new Complex with the resulting value.
	 */
	public static final Complex pow(Complex com, Complex pow)
	{
		double tau = com.length();
		double theta = Math.atan2(com.imaginary, com.real);
		double multi = Math.pow(tau, pow.real) * Math.exp(-pow.imaginary * theta);
		double inside = (pow.real * theta) + (pow.imaginary * Math.log(tau));
		double r = multi * Math.cos(inside);
		double i = multi * Math.sin(inside);
		return new Complex(r, i);
	}
	
	/**
	 * Rounds the given Complex.
	 * @param com The Complex to round.
	 * @return The rounded Complex.
	 */
	public static final Complex round(Complex com)
	{
		return new Complex(Math.round(com.real), Math.round(com.imaginary));
	}
	
	/**
	 * Returns the sin of the given Complex.
	 * @param com The Complex to sin.
	 * @return The rounded Complex.
	 */
	public static final Complex sin(Complex com)
	{
		double r = Math.sin(com.real) * Math.cosh(com.imaginary);
		double i = Math.cos(com.real) * Math.sinh(com.imaginary);
		return new Complex(r, i);
	}
	
	/**
	 * Returns the sqrt of the given Complex.
	 * @param com The Complex to sqrt.
	 * @return The rounded Complex.
	 */
	public static final Complex sqrt(Complex com)
	{
		double rad = com.length();
		Complex temp = new Complex(com.real + rad, com.imaginary);
		double length = temp.length();
		double multi = Math.sqrt(rad) / length;
		double r = multi * temp.real;
		double i = multi * temp.imaginary;
		return new Complex(r, i);
	}
	
	/**
	 * Returns the tan of the given Complex.
	 * @param com The Complex to tan.
	 * @return The rounded Complex.
	 */
	public static final Complex tan(Complex com)
	{
		return sin(com).divide(cos(com));
	}
}
