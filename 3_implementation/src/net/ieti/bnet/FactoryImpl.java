package net.ieti.bnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;
import org.eclipse.recommenders.jayes.io.xmlbif.XMLBIFReader;
import org.eclipse.recommenders.jayes.io.xmlbif.XMLBIFWriter;

/**
 * This class is the default implementation of a factory.
 * 
 * @author Loc Nguyen
 * @version 1.0
 *
 */
public final class FactoryImpl implements Factory {

	
	@Override
	public Bnet createNetwork() {
		// TODO Auto-generated method stub
		return new BNetworkWrapper();
	}

	
	/**
	 * This class is an implementation of a Bayesian network, which is the wrapper of {@link BayesNet}. 
	 * @author Loc Nguyen
	 * @version 1.0
	 *
	 */
	private static final class BNetworkWrapper implements Bnet {

		/**
		 * Internal Bayesian network.
		 */
		private BayesNet bayesNet = null;
		
		/**
		 * Default constructor.
		 */
		BNetworkWrapper() {
			bayesNet = new BayesNet();
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void addRootNodes(Bnode... rootNodes) {
			// TODO Auto-generated method stub
			for (Bnode node : rootNodes) {
				bayesNet.addNode(((BNodeWrapper)node).bayesNode);
			}
			
		}

		@Override
		public List<Bnode> getRootNodes() {
			// TODO Auto-generated method stub
			List<BayesNode> rootNodes = bayesNet.getNodes();
			return BNodeWrapper.toNodeList(rootNodes);
		}

		@Override
		public void load(InputStream in) throws IOException {
			// TODO Auto-generated method stub
			XMLBIFReader reader = new XMLBIFReader(in);
			bayesNet = reader.read();
			reader.close();
		}

		@Override
		public void save(OutputStream out) throws IOException {
			// TODO Auto-generated method stub
			XMLBIFWriter writer = new XMLBIFWriter(out);
			writer.write(bayesNet);
			writer.close();
		}

		@Override
		public Bnode newNode(String nodeName) {
			// TODO Auto-generated method stub
			return new BNodeWrapper(nodeName);
		}

	}
	
	
	/**
	 * This class is an implementation of a node in Bayesian network, which is the wrapper of {@link BayesNode}. 
	 * @author Loc Nguyen
	 * @version 1.0
	 *
	 */
	private static final class BNodeWrapper implements Bnode {

		/**
		 * Internal node.
		 */
		private BayesNode bayesNode = null;
		
		/**
		 * Constructor with specified node name.
		 * @param nodeName specified node name.
		 */
		@SuppressWarnings("deprecation")
		BNodeWrapper(String nodeName) {
			bayesNode = new BayesNode(nodeName);
		}
		
		/**
		 * Constructor with internal node.
		 * @param bayesNode internal Bayesian node.
		 */
		private BNodeWrapper(BayesNode bayesNode) {
			this.bayesNode = bayesNode;
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return bayesNode.getName();
		}

		@Override
		public void setParents(Bnode... parentNodes) {
			// TODO Auto-generated method stub
			List<BayesNode> nodeList = new ArrayList<>();
			for (Bnode node : parentNodes) {
				nodeList.add(((BNodeWrapper)node).bayesNode);
			}
			bayesNode.setParents(nodeList);
		}

		@Override
		public List<Bnode> getParents() {
			// TODO Auto-generated method stub
			List<BayesNode> parentNodes = bayesNode.getParents();
			return toNodeList(parentNodes);
		}

		@Override
		public List<Bnode> getChildren() {
			// TODO Auto-generated method stub
			List<BayesNode> childNodes = bayesNode.getChildren();
			return toNodeList(childNodes);
		}

		/**
		 * Converting a specified list of {@link BayesNode} (s) to a list of {@link Bnode} (s).
		 * @param bayesNodeList specified list of {@link BayesNode} (s).
		 * @return a list of {@link Bnode} (s).
		 */
		private static List<Bnode> toNodeList(List<BayesNode> bayesNodeList) {
			List<Bnode> nodeList = new ArrayList<>();
			for (BayesNode bayesNode : bayesNodeList) {
				BNodeWrapper wrapper = new BNodeWrapper(bayesNode);
				nodeList.add(wrapper);
			}
			
			return nodeList;
		}
		
		@Override
		public void setProbs(double... cpt) {
			// TODO Auto-generated method stub
			bayesNode.setProbabilities(cpt);
		}

		@Override
		public double[] getProbs() {
			// TODO Auto-generated method stub
			return bayesNode.getProbabilities();
		}
		
	}
	
	
}
