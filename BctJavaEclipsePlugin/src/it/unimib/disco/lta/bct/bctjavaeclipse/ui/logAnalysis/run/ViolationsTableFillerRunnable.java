package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.ViolationsAnalysisResultRunnable;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.ViolationsAnalysisResultComposite;

/**
 * This runner is used to populate a ViolationsAnalysisResultComposite
 *  
 * @deprecated
 * 
 * @author Fabrizio Pastore
 *
 */
public class ViolationsTableFillerRunnable extends
		ViolationsAnalysisResultRunnable {

	private ViolationsAnalysisResultComposite resultComposite;

	public ViolationsTableFillerRunnable(
			ViolationsAnalysisResultComposite resultComposite) {
		this.resultComposite = resultComposite;
	}

	public void run() {
		resultComposite.load(null,result,null);
	}

}
