package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import java.util.List;

import modelsViolations.BctModelViolation;

public class RegressionAnalysisResult {

	
	private List<BctModelViolation> filteredViolations;

	public RegressionAnalysisResult(){
		
	}
	
	public List<BctModelViolation> getFilteredViolations() {
		return filteredViolations;
	}

	public RegressionAnalysisResult(String fid) {
		// TODO Auto-generated constructor stub
	}

	public void setFilteredViolations(List<BctModelViolation> filteredViolations) {
		this.filteredViolations = filteredViolations;
	}

	@Override
	public boolean equals(Object arg0) {
		if ( ! ( arg0 instanceof RegressionAnalysisResult ) ){
			return false;
		}
		
		RegressionAnalysisResult rhs = (RegressionAnalysisResult) arg0;
		if ( filteredViolations == null ){
			if ( null != rhs.filteredViolations ){
				return false;
			}
		} else {
			if ( ! filteredViolations.equals(rhs.filteredViolations) ){
				return false;
			}
		}
		
		return true;
	}
	

}
