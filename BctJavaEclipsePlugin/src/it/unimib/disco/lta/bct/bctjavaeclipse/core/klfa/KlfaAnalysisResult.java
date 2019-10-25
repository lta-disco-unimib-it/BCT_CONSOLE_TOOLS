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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.klfa;

import java.util.List;

import modelsViolations.BctFSAModelViolation;

public class KlfaAnalysisResult {

	private List<BctFSAModelViolation> refinedInteractionViolations;
	private String failureId;
	private List<BctFSAModelViolation> originalInteractionViolations;

	public List<BctFSAModelViolation> getOriginalInteractionViolations() {
		return originalInteractionViolations;
	}

	public void setOriginalInteractionViolations(
			List<BctFSAModelViolation> originalInteractionViolations) {
		this.originalInteractionViolations = originalInteractionViolations;
	}

	public List<BctFSAModelViolation> getRefinedInteractionViolations() {
		return refinedInteractionViolations;
	}

	public String getFailureId() {
		return failureId;
	}

	public KlfaAnalysisResult(String fid) {
		this.failureId = fid;
	}

	public void setRefinedInteractionViolations(
			List<BctFSAModelViolation> refinedInteractionViolations) {
		this.refinedInteractionViolations = refinedInteractionViolations;
	}


}
