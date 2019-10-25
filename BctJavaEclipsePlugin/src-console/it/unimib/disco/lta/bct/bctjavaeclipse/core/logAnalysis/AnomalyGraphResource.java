/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
/**
 * 
 */
package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tools.violationsAnalyzer.anomalyGraph.AnomalyGraph;

public class AnomalyGraphResource{
	
	private String resourceName;
	private int connectedComponents;
	private String resourcePath;
	private List<Set<String>> connectedComponentsElements;
	private boolean optimalGraph;
	private String serializedPath;
	
	public AnomalyGraphResource(){
		
	}
	
	public AnomalyGraphResource(String resourceName, String resourcePath, String serializedPath, int connectedComponents, List<Set<String>> connectedComponentsElements, boolean optimal){
		this.resourceName = resourceName;
		this.resourcePath = resourcePath;
		this.serializedPath = serializedPath;
		this.connectedComponents = connectedComponents;
		this.optimalGraph = optimal;
		this.connectedComponentsElements = connectedComponentsElements;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	
	public int getConnectedComponents() {
		return connectedComponents;
	}

	public void setConnectedComponents(int connectedComponents) {
		this.connectedComponents = connectedComponents;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public boolean isOptimalGraph() {
		return optimalGraph;
	}

	public void setOptimalGraph(boolean optimalGraph) {
		this.optimalGraph = optimalGraph;
	}

	public void setConnectedComponentsElements(
			List<Set<String>> connectedComponentsElements) {
		this.connectedComponentsElements = connectedComponentsElements;
	}

	public List<Set<String>> getConnectedComponentsElements() {
		return connectedComponentsElements;
	}
	
	public String getSerializedPath(){
		return serializedPath;
	}
}