package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class keeps the information about the results of an anomaly analysis.
 * It maintains the information about the location of the persisted graphs. 
 * And other information like connected components per graph or whether or not the graph is the optimal one: 
 * this information can be retrieved from the persisted graphs but this permit us to save time.
 * 
 * @author Fabrizio Pastore
 *
 */
public class ViolationsAnalysisResult {
	private ArrayList<AnomalyGraphResource> resources = new ArrayList<AnomalyGraphResource>();
	private String failureId;
	
	/**
	 * Default constructor, required for xml serialization
	 */
	public ViolationsAnalysisResult(){
		
	}
	
	/**
	 * Create a new Violation analysis result object.
	 * 
	 * @param failureId failure which is analyzed
	 */
	public ViolationsAnalysisResult(String failureId){
		this.failureId = failureId;
	}

	/**
	 * Add a new anomaly graph to the collection of graphs generated
	 * 
	 * @param graphId	id of the graph (unique within an analysis)
	 * @param resourcePath path of the persisted graph
	 * @param serializedPath 
	 * @param connectedComponents number of connected components
	 * @param connectedComponentsElements 
	 * @param optimal	whether or not this is an optimal graph
	 */
	public void newElement(String graphId, String resourcePath, String serializedPath, int connectedComponents, List<Set<String>> connectedComponentsElements, boolean optimal ) {
		AnomalyGraphResource resource = new AnomalyGraphResource(graphId,resourcePath,serializedPath,connectedComponents, connectedComponentsElements, optimal );
		resources.add(resource);
	}

	public ArrayList<AnomalyGraphResource> getResources() {
		return resources;
	}

	public void setResources(ArrayList<AnomalyGraphResource> resources) {
		this.resources = resources;
	}

	public String getFailureId() {
		return failureId;
	}

	public void setFailureId(String failureId) {
		this.failureId = failureId;
	}


	
}
