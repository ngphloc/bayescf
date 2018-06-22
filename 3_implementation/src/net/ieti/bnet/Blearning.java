package net.ieti.bnet;

import net.hudup.core.data.Fetcher;
import net.hudup.core.data.Profile;

/**
 * This interface represents a learning algorithm for Bayesian network.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public interface Blearning {

	
	/**
	 * The main method learns or create Bayesian network from training data.
	 * @param input specified training data.
	 * @param param additional parameter.
	 * @return Bayesian network from specified training data.
	 */
	Bnet learn(Fetcher<Profile> input, Object param);
	
	
}
