package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class VARTRegressionWizardPage extends CompositeWizardPage<VARTConfigurationComposite, VARTRegressionConfiguration> {

	public VARTRegressionWizardPage(ISelection selection) {
		super(selection, "VART", "This wizard creates a VART configuration to identify regressions in C/C++ projects.");
	}

	@Override
	public VARTRegressionConfiguration getConfiguration() {
		return regressionConfigurationComposite.getConfiguration();

	}

	@Override
	protected VARTConfigurationComposite createMainComposite(Composite parent) {
		return new VARTConfigurationComposite(parent,SWT.NONE);
	}

}
