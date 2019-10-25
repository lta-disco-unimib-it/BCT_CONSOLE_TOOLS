package it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis;

import tools.fshellExporter.ModelFilter;
import tools.fshellExporter.ModelInfo;

public class TargetOnlyFilter implements ModelFilter {

	@Override
	public String processDataProperty(String dataProperty, ModelInfo modelInfo) {
		if ( modelInfo.getFunctionOriginal().isTargetFunction() ){
			return dataProperty;
		}
		return null;
	}

}
