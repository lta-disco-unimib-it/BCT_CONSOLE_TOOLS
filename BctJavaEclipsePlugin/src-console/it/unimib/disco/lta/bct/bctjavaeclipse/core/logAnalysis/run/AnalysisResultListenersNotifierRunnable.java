package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;

/**
 * This runnable notify all the registered listeners that a violation-analysis has finished
 * 
 * @author Fabrizio Pastore
 *
 */
public class AnalysisResultListenersNotifierRunnable<T extends Object> implements Runnable {

	private T result;
	private List<AnalysisResultListener<T>> violationAnalysisListener;

	/**
	 * Constructor the runnable.
	 * The result is the result object to sent to all the listeners, the list of listeners is the list of listeners to notify.
	 * 
	 * @param result	result to pass to all notified listeners
	 * @param violationAnalysisListener listeners to notify
	 */
	public AnalysisResultListenersNotifierRunnable( T result, List<AnalysisResultListener<T>> violationAnalysisListener ){
		this.result = result;
		this.violationAnalysisListener = violationAnalysisListener;
	}
	
	public void run() {
		for ( AnalysisResultListener<T> listener : violationAnalysisListener ){
			listener.analysisFinished(result);
		}
	}

}
