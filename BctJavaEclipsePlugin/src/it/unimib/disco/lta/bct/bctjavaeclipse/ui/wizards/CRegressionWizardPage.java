package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CRegressionWizardPage extends CompositeWizardPage<CRegressionConfigurationComposite, CRegressionConfiguration> {

	private CRegressionConfiguration toLoad;

	public CRegressionWizardPage(ISelection selection) {
		super(selection, "Monitoring Options", "This wizard cconfigure the options required to identify regressions in C/C++ projects.");
	}

	@Override
	public CRegressionConfiguration getConfiguration() {
		return regressionConfigurationComposite.getConfiguration();

	}

	@Override
	protected CRegressionConfigurationComposite createMainComposite(Composite parent) {
		CRegressionConfigurationComposite composite = new CRegressionConfigurationComposite(parent,SWT.NONE);
		composite.load(toLoad, null);
		return composite;
	}

	public void load(CRegressionConfiguration c) {
		if ( regressionConfigurationComposite == null ){
			toLoad = c;
			return;
		}
		regressionConfigurationComposite.load(c, null);
	}


	
	
}
