package math;

/**
 * 
 * Stores the values of common measurements in the world.
 * 
 * @author F4113nb34st
 *
 */
public class Measure
{
	//time
	public static final double hour = 3600;
	public static final double minute = 60;
	public static final double second = 1;
	public static final double millisecond = .001;
	
	//time squared
	public static final double hourSq = hour * hour;
	public static final double minuteSq = minute * minute;
	public static final double secondSq = second * second;
	public static final double millisecondSq = millisecond * millisecond;
	
	//distance
	public static final double kilometer = 1000;
	public static final double meter = 1;
	public static final double centimeter = 0.01;
	public static final double millimeter = 0.001;
	
	public static final double mile = 1609.344;
	public static final double yard = 0.9144;
	public static final double foot = 0.3048;
	public static final double inch = 0.0254;
	
	//area
	public static final double kilometerSq = kilometer * kilometer;
	public static final double meterSq = meter * meter;
	public static final double centimeterSq = centimeter * centimeter;
	public static final double millimeterSq = millimeter * millimeter;
	
	public static final double mileSq = mile * mile;
	public static final double yardSq = yard * yard;
	public static final double footSq = foot * foot;
	public static final double inchSq = inch * inch;
	
	//volume
	public static final double kilometerCu = kilometer * kilometer * kilometer;
	public static final double meterCu = meter * meter * meter;
	public static final double centimeterCu = centimeter * centimeter * centimeter;
	public static final double millimeterCu = millimeter * millimeter * millimeter;
	
	public static final double mileCu = mile * mile * mile;
	public static final double yardCu = yard * yard * yard;
	public static final double footCu = foot * foot * foot;
	public static final double inchCu = inch * inch * inch;
	
	//mass
	public static final double kilogram = 1000;
	public static final double gram = 1;
	
	public static final double slug = 14.5939 * kilogram;
	public static final double pound_Mass = 453.592 * gram;
	
	//force
	public static final double newton = kilogram * meter / secondSq;
	
	public static final double pound_Force = slug * foot / secondSq;
}
