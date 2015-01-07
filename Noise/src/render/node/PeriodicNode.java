package render.node;

/**
 * 
 * A PeriodicNode is a Node that is periodic.
 * 
 * @author F4113nb34st
 *
 */
public interface PeriodicNode extends Node
{
	/**
	 * Sets the periods of this node.
	 * @param px The x period.
	 * @param py The y period.
	 */
	public PeriodicNode setPeriods(double px, double py);
	
	/**
	 * Gets the x period of this node.
	 * @return The x period.
	 */
	public double getPeriodX();
	
	/**
	 * Gets the y period of this node.
	 * @return The y period.
	 */
	public double getPeriodY();
}