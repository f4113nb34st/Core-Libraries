package render.node;

/**
 * 
 * A SeededNode is a Node that has a seed.
 * 
 * @author F4113nb34st
 *
 */
public interface SeededNode extends Node
{
	/**
	 * Sets the seed of this node.
	 * @param seed The seed.
	 */
	public SeededNode setSeed(long seed);
}