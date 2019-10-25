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

