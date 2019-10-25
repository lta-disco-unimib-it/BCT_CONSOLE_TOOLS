package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;

/**
 * Abstract class for runners that observe a violation analysis result
 * 
 * @deprecated
 * 
 * @author Fabrizio Pastore
 *
 */
public abstract class ViolationsAnalysisResultRunnable implements Runnable {

	protected ViolationsAnalysisResult result;
	
	public ViolationsAnalysisResult getResult() {
		return result;
	}

	public void setResult(ViolationsAnalysisResult result) {
		this.result = result;
	}

}
