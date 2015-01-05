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
	public static final double second = 1;
	
	public static final double nanosecond = second * 1E-9;
	public static final double millisecond = second / 1000D;
	public static final double minute = second * 60;
	public static final double hour = minute * 60;
	public static final double day = hour * 24;
	public static final double week = day * 7;
	public static final double year = day * 365;
	
	//time squared
	public static final double secondSq = second * second;
	
	public static final double nanosecondSq = nanosecond * nanosecond;
	public static final double millisecondSq = millisecond * millisecond;
	public static final double minuteSq = minute * minute;
	public static final double hourSq = hour * hour;
	public static final double daySq = day * day;
	public static final double weekSq = week * week;
	public static final double yearSq = year * year;
	
	//distance
	public static final double meter = 1;
	
	public static final double millimeter = meter / 1000D;
	public static final double centimeter = meter / 100D;
	public static final double kilometer = meter * 1000;
	
	public static final double foot = meter * 0.3048;
	
	public static final double inch = foot / 12D;
	public static final double yard = foot * 3;
	public static final double mile = foot * 5280;
	
	//area
	public static final double meterSq = meter * meter;
	
	public static final double millimeterSq = millimeter * millimeter;
	public static final double centimeterSq = centimeter * centimeter;
	public static final double kilometerSq = kilometer * kilometer;
	
	public static final double footSq = foot * foot;
	
	public static final double inchSq = inch * inch;
	public static final double yardSq = yard * yard;
	public static final double mileSq = mile * mile;
	
	//volume
	public static final double meterCu = meter * meter * meter;
	
	public static final double millimeterCu = millimeter * millimeter * millimeter;
	public static final double centimeterCu = centimeter * centimeter * centimeter;
	public static final double kilometerCu = kilometer * kilometer * kilometer;
	
	public static final double footCu = foot * foot * foot;
	
	public static final double inchCu = inch * inch * inch;
	public static final double yardCu = yard * yard * yard;
	public static final double mileCu = mile * mile * mile;
	
	//mass
	public static final double gram = 1;
	
	public static final double kilogram = gram * 1000;
	public static final double slug = 14.5939 * kilogram;
	public static final double pound_Mass = 453.592 * gram;
	
	//force
	public static final double newton = kilogram * meter / secondSq;
	
	public static final double pound_Force = slug * foot / secondSq;
}
