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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

/**
 * 
 * This class manage the wizard, this wizard will run on Bct option InferModel.
 * this wizard have only two page, the first is invariant Generator Options, the second depends on 
 * fsa Engine selected.
 * 
 * @author Terragni Valerio
 * 
 */

import org.eclipse.jface.wizard.Wizard;

public class StaticallyIdentifyUsageAnomaliesWizard extends Wizard{

	private InferenceEngineWizardPage inferenceEnginePage;




	public String fsaEngine = "";//
	private StaticallyIdentifyUsageAnomaliesWizardPage configurationWizardPage;
	private StaticallyIdentifyUsageAnomaliesResult result;


	public StaticallyIdentifyUsageAnomaliesWizard(StaticallyIdentifyUsageAnomaliesResult result){
		this.result = result;
		// create and add all possible pages
		configurationWizardPage = new StaticallyIdentifyUsageAnomaliesWizardPage();
		addPage(configurationWizardPage);
	


	}
	
	public boolean canFlipToNextPage(){
		return true;
	}
	
	public boolean canFinish() {
		return true;
	}

	public boolean performFinish() {

		result.setSkipPassingTests( configurationWizardPage.getSkipPassingTests() );
		result.setSkipPassingActions( configurationWizardPage.getSkipPassingActions() );
		result.setSkipPassingProcesses( configurationWizardPage.getSkipPassingProcesses() );
		
		return true;
	}


	public boolean performCancel() {
		
		return true;
	}


}

