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
 * This class represents the Ktail wizard page
 * 
 * @author Terragni Valerio
 *
 */
public class KTailWizardPage extends InferenceEngineWizardPage{
	public static final String PAGE_NAME = "KTail";
	public FsaEngineOptionsComposite kTailComp;



	public KTailWizardPage() {
		super("KTail");
		setTitle("KTail option");
		setDescription("Insert KTail option");

	}


	public void createControl(Composite parent) {

		kTailComp = new FsaEngineOptionsComposite(parent,SWT.None);
		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();// create composite

			kTailComp.load(workspaceOptions.getKtailEnigineOptions());
			// load options

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setControl(kTailComp);

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
		return kTailComp.getUserDefinedOptions();
	}


	@Override
	/**
	 * load properties
	 */
	public void load(Properties options) {
		if ( kTailComp != null )
			kTailComp.load(options);
	}



}
