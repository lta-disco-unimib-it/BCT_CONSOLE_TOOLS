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
