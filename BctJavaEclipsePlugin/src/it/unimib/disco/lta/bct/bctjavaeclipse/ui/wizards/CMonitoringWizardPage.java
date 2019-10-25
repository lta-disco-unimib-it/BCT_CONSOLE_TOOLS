package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CMonitoringWizardPage extends CompositeWizardPage<CConfigurationComposite, CConfiguration> {

	public CMonitoringWizardPage(ISelection selection) {
		super(selection, "C/C++ Monitoring Configuration", "This wizard creates a BCT configuration for monitoring C/C++ code");
	}

	@Override
	public CConfiguration getConfiguration() {
		return regressionConfigurationComposite.getConfiguration();
	}

	@Override
	protected CConfigurationComposite createMainComposite(Composite container) {
		return new CConfigurationComposite(container, SWT.NONE);
	}

}
