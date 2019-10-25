package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.MonitoringConfigurationComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class FileDataRecorderSettingComposite extends Composite{

	
	public FileDataRecorderSettingComposite(Composite parent)
	{
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		this.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;
		MonitoringConfigurationComposite m=new MonitoringConfigurationComposite(this,SWT.NONE);
		m.pack();
		
	}
}
