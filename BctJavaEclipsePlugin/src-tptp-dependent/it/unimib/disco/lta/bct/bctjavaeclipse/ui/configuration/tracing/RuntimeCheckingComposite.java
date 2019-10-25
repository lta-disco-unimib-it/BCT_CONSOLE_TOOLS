package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.tracing;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.MonitoringConfigurationComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class RuntimeCheckingComposite extends Composite{

	public RuntimeCheckingComposite(Composite parent)
	{
		super(parent, SWT.NONE);
		MonitoringConfigurationComposite m=new MonitoringConfigurationComposite(this,SWT.NONE);
	}
}
