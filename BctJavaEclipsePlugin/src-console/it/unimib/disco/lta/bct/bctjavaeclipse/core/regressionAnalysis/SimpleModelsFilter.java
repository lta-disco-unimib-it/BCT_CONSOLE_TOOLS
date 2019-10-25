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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import tools.fshellExporter.ModelFilter;
import tools.fshellExporter.ModelInfo;

public class SimpleModelsFilter implements ModelFilter {

	private HashMap<String,HashSet<String>> modelsToExclude = new HashMap<String, HashSet<String>>();
	
	
	
	@Override
	public String processDataProperty(String dataProperty, ModelInfo modelInfo) {
		Set<String> toExclude = getModelsToExclude( modelInfo );
		
		if ( toExclude == null ){
			return dataProperty;
		}
		
		if ( toExclude.contains(dataProperty) ){
			return null;
		}
		
		return dataProperty;
	}

	private Set<String> getModelsToExclude(ModelInfo modelInfo) {
		return modelsToExclude.get(modelInfo.getBctModelName());
	}

	public void excludeIoModel(String programPointName, String dataProperty) {
		HashSet<String> toExclude = modelsToExclude.get(programPointName);
		
		if ( toExclude == null ){
			toExclude = new HashSet<String>();
			modelsToExclude.put(programPointName, toExclude);
		}
		
		toExclude.add(dataProperty);
	}

}
