package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;

import java.io.Serializable;

import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.ava.engine.AvaClusteredResult;

public class AvaAnalysisResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String failureId;
	private AVAResult avaResult;
	
	public void setFailureId(String failureId) {
		this.failureId = failureId;
	}

	private AvaClusteredResult avaClusteredResult;

	public AvaAnalysisResult(){
		
	}
	
	public AVAResult getAvaResult() {
		return avaResult;
	}
	
	public AvaClusteredResult getAvaClusteredResult() {
		return avaClusteredResult;
	}

	public String getFailureId() {
		return failureId;
	}

	public AvaAnalysisResult(String fid) {
		this.failureId = fid;
	}

	public void setAvaResult(AVAResult avaResult) {
		this.avaResult = avaResult; 
		this.avaClusteredResult = new AvaClusteredResult(avaResult); //this is useful for optimization, the result is created in the background thread
	}

}
