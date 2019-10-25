package it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis;

import java.util.HashSet;
import java.util.Set;

import tools.fshellExporter.ModelFilter;
import tools.fshellExporter.ModelInfo;
import tools.violationsAnalyzer.ViolationsUtil;

public class VariableNamesFilter implements ModelFilter {
	HashSet<String> variablesToExclude = new HashSet<String>();
	
	public void addVarToExclude( String varName ){
		variablesToExclude.add(varName);
	}
	
	@Override
	public String processDataProperty(String dataProperty, ModelInfo modelInfo) {
		Set<String> vars = ViolationsUtil.extractVariables(dataProperty);
	
		for ( String var : vars ){
			if ( variablesToExclude.contains(var) ){
				return null;
			}
		}
		//the data property does not contain any variable to exclude
		
		return dataProperty;
	}

}
