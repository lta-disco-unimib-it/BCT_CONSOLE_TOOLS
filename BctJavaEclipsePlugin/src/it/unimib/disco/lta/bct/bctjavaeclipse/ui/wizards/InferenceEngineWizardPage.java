package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import java.util.Properties;

import org.eclipse.jface.wizard.WizardPage;

public abstract class InferenceEngineWizardPage extends WizardPage {

	protected InferenceEngineWizardPage(String pageName) {
		super(pageName);
	}

	public abstract Properties getUserDefinedOptions();
    
	public abstract void load ( Properties options );
}
