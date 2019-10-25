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
