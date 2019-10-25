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
