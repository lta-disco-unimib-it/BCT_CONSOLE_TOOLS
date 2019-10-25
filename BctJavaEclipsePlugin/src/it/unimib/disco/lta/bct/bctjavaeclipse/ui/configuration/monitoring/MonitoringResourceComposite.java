package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MonitoringResourceComposite extends Composite {

	private ResourcesPage resourcePage;
	private MonitoringConfigurationComposite monitoringConfigurationPage;

	public MonitoringResourceComposite(Composite parent, int style) {
		super(parent, style);

		setLayoutData(new GridData());
		
		CTabFolder folder = new CTabFolder(this, SWT.BORDER);

		
	    CTabItem resource = new CTabItem(folder, SWT.NONE);
	    resource.setText("Resources");
	    resource.setControl(getResourceSelectionTab(folder));

	    CTabItem configuration = new CTabItem(folder, SWT.NONE);
	    configuration.setText("Monitoring Configuration");
	    configuration.setControl(getMonitoringConfigurationTab(folder));
	    
	    
	    
	}
	
	private Control getMonitoringConfigurationTab(CTabFolder folder) {
		Composite composite=new Composite(folder,SWT.NONE);
		composite.setLayoutData(new GridData());

		monitoringConfigurationPage = new MonitoringConfigurationComposite(composite, SWT.NONE);
		
		return composite;
	}

	private Composite getResourceSelectionTab(CTabFolder tab) {
		
		Composite composite=new Composite(tab,SWT.NONE);
		composite.setLayoutData(new GridData());

		resourcePage = new ResourcesPage(composite);
		
		return composite;
	}


	
}
