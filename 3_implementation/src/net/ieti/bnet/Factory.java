/**
 * 
 */
package net.ieti.bnet;

/**
 * This interface represents a factory to create Bayesian network and other relevant objects.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface Factory {

	
	/**
	 * Create Bayesian network.
	 * @return Bayesian network.
	 */
	Bnet createNetwork();
	
	
	/**
	 * Create a node in Bayesian network.
	 * @param nodeName specified node name.
	 * @return a node in Bayesian network.
	 */
	Bnode createNode(String nodeName);
	
	
}
