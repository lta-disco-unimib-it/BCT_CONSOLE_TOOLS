package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines.FsaEngineOptionsComposite;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
/**
 * This class represents the KInclusion wizard page
 *
 * @author Terragni Valerio
 *
 */
public class KInclusionWizardPage extends InferenceEngineWizardPage{
	public static final String PAGE_NAME = "KInclusion";
	public FsaEngineOptionsComposite kInclusionComp;



	public KInclusionWizardPage() {
		super("KInclusion");
		setTitle("KInclusion option");
		setDescription("Insert KInclusion option");

	}


	public void createControl(Composite parent) {

		kInclusionComp = new FsaEngineOptionsComposite(parent,SWT.None);
		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();

			kInclusionComp.load(workspaceOptions.getKinclusionEnigineOptions());
			//load options

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		setControl(kInclusionComp);

	}

	public boolean canFlipToNextPage(){

		return false;
	}

	private void initialize() {



		setPageComplete(true);
	}


	@Override
	/**
	 * return the options defined by user
	 * 
	 * @return
	 */
	public Properties getUserDefinedOptions() {
		return kInclusionComp.getUserDefinedOptions();

	}


	@Override
	/**
	 * load properties
	 */
	public void load(Properties options) {
		if ( kInclusionComp != null )
			kInclusionComp.load(options);
	}



}
