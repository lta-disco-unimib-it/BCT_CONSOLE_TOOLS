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

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ConsoleDisplayMgr;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions.InferModelWizardResult;

import java.util.Properties;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import conf.InvariantGeneratorSettings;

public class InferModelWizard extends Wizard{

	public int c = 0;
	private InferenceEngineWizardPage inferenceEnginePage;
	@Override


	public IWizardPage getPreviousPage(IWizardPage page) {

		return super.getPage("Configurationpage");


	}

	public IWizardPage getNextPage(IWizardPage page) {

		String fsaEngineType = configurationWizardPage.getInvariantGeneratorOptions().getProperty(InvariantGeneratorSettings.Options.fsaEngine);
		// the next page depends the fsa engine choice

		if ( fsaEngineType.equals("KBehavior") ){
			inferenceEnginePage = (InferenceEngineWizardPage) super.getPage("KBehavior");

		} else if( fsaEngineType.equals("KInclusion")){
			inferenceEnginePage = (InferenceEngineWizardPage) super.getPage("KInclusion");

		} else if( fsaEngineType.equals("Reiss")){
			inferenceEnginePage = (InferenceEngineWizardPage) super.getPage("Reiss");

		} else if( fsaEngineType.equals("KTail")){
			inferenceEnginePage = (InferenceEngineWizardPage) super.getPage("KTail");

		}

		if ( loadFsaOptions != null ){
			inferenceEnginePage.load(loadFsaOptions);
			loadFsaOptions = null;
		}

		return inferenceEnginePage;
		//return super.getNextPage(page);
	}


	public String fsaEngine = "";//
	private ConfigurationWizardPage configurationWizardPage;
	private KBehaviorWizardPage kBehaviorWizardPage;
	private KTailWizardPage kTailWizardPage;
	private KInclusionWizardPage kInclusionWizardPage;
	private ReissWizardPage reissWizardPage;
	private InferModelWizardResult result;
	private Properties loadFsaOptions;

	public InferModelWizard(InferModelWizardResult result){
		this.result = result;
		// create and add all possible pages
		configurationWizardPage = new ConfigurationWizardPage();
		addPage(configurationWizardPage);
		configurationWizardPage.setPageComplete(false);


		kBehaviorWizardPage = new  KBehaviorWizardPage();

		addPage(kBehaviorWizardPage);
		kBehaviorWizardPage.setPageComplete(false);

		kTailWizardPage = new KTailWizardPage();
		addPage(kTailWizardPage );
		kTailWizardPage.setPageComplete(false);

		kInclusionWizardPage = new KInclusionWizardPage();
		addPage(kInclusionWizardPage );
		kInclusionWizardPage.setPageComplete(false);

		reissWizardPage = new ReissWizardPage();
		addPage(reissWizardPage );
		reissWizardPage.setPageComplete(false);
	}


	public boolean performFinish() {

		 	
		result.setInferenceEngineOptions(inferenceEnginePage.getUserDefinedOptions());
		result.setInvariantGeneratorOptions(configurationWizardPage.getInvariantGeneratorOptions());
		result.setKeepExistingModels( configurationWizardPage.getKeepExistingModels());
		result.setSkipFailingActions( configurationWizardPage.getSkipFailingActions() );
		result.setSkipFailingTests( configurationWizardPage.getSkipFailingTests() );
		result.setSkipFailingProcesses( configurationWizardPage.getSkipFailingProcesses() );
		return true;
	}


	public boolean performCancel() {
		result.setCanceled(true);
		return true;
	}




	public boolean canFinish(){
//		this "if" are useful for finish button enabled, otherwise when click the previous button, the
//		finish button still enabled.
		if(c==0){
			c++;
			return false;
		}
		else if (c==1){
			c=0;
			return true;
		}
		return false;
	}

	public void loadInvariantGeneratorOptions(Properties invariantGeneratorOptions){
		configurationWizardPage.load(invariantGeneratorOptions);
	}

	public void loadKBehaviorOptions(Properties kbehaviorInferenceEngineOptions) {
		kBehaviorWizardPage.load(kbehaviorInferenceEngineOptions);
	}

	public void loadKTailOptions(Properties ktailEnigineOptions) {
		kTailWizardPage.load(ktailEnigineOptions);
	}

	public void loadReissOptions(Properties reissEnigineOptions) {
		reissWizardPage.load(reissEnigineOptions);
	}

	public void loadKInclusionOptions(Properties kinclusionEnigineOptions) {
		kInclusionWizardPage.load(kinclusionEnigineOptions);
	}

	/**
	 * Set these options when opening the fsa engine page
	 * @param fsaEngineOptions
	 */
	public void loadSelectedOptions(Properties fsaEngineOptions) {
		loadFsaOptions = fsaEngineOptions;
	}




}

