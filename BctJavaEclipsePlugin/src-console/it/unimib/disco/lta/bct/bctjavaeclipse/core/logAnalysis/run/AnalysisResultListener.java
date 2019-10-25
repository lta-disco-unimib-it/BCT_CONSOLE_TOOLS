package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;

/**
 * This listener is notified whenever an analysis of bct violations is conducted
 *   
 * @author Fabrizio Pastore
 *
 */
public interface AnalysisResultListener<T extends Object> {
	
	/**
	 * Invoked when an analysis is started
	 * 
	 */
	public void analysisStartup();
	
	/**
	 * This method is invoked when an analysis has been completed. 
	 * The ViolationsAnalysisResult object passed contains information about the analysis performed.
	 * @param result
	 */
	public void analysisFinished ( T result );

}
