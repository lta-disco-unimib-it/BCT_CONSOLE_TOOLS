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
