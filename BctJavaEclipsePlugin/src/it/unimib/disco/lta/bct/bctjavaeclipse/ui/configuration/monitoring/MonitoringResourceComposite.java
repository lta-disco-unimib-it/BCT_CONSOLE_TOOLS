/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
