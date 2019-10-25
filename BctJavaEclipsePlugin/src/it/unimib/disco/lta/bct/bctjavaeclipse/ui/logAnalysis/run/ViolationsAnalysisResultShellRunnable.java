package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.ViolationsAnalysisResultRunnable;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.ViolationsAnalysisResultShell;

/**
 * Opens a result shell
 * 
 * @deprecated
 * 
 * @author Fabrizio Pastore
 *
 */
public class ViolationsAnalysisResultShellRunnable extends
		ViolationsAnalysisResultRunnable {

	public void run() {
		ViolationsAnalysisResultShell shell = new ViolationsAnalysisResultShell();
		shell.load(result,null);
		shell.open();
	}

}
