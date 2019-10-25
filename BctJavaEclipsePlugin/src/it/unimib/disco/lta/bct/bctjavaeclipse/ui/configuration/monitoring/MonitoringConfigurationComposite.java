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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.BctComponentConfigurationLoadButton;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener.ObjectFlattenerPage;

import java.util.ArrayList;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;


public class MonitoringConfigurationComposite extends Composite implements MonitoringConfigurationManager {
	//List of the components in the configuration
	private FlattenerOptions flattenerOptions = new FlattenerOptions(true,5,"All",new ArrayList<String>());
	String[] fromValues=new String[100];
	
	private CTabFolder folder;
	private ComponentsConfigurationComposite componentsConfigurationComposite;
	private ObjectFlattenerPage flattenerPage;
	




	
	public MonitoringConfigurationComposite(Composite parent,int options)
	{
		
		super(parent,options);
	
    	setBounds(20, 20, 500, 800);
		setLayoutData(new GridData());

		
//		Group flattenerOptionsGroup=new Group(this, SWT.NONE);
//	    flattenerOptionsGroup.setBounds(5, 5, 400, 110);
//	    flattenerOptionsGroup.setText("Flattener Options");
//	    Label l = new Label(flattenerOptionsGroup, SWT.NONE);
//	    l.setText("AAA");
	    
		folder = new CTabFolder(this, SWT.BORDER );
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		
	    CTabItem configuration = new CTabItem(folder, SWT.NONE);
	    configuration.setText("Configuration");
	    configuration.setControl(getComponentsConfigurationTab(folder));
	    
	    
	    CTabItem flattening = new CTabItem(folder, SWT.NONE);
	    flattening.setText("Object flattening");
	    flattening.setControl(getFlatteningConfigurationTab(folder));
	    
	    folder.setSelection(0);
	    folder.setSize(400, 400);
	    folder.pack();
	}
	
	private Control getFlatteningConfigurationTab(CTabFolder folder) {

		Composite container = new Composite(folder, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;

		flattenerPage = new ObjectFlattenerPage(container,flattenerOptions);

		return container;
	}
	//

	private Composite getComponentsConfigurationTab(CTabFolder folder) {
		
		
		Composite container = new Composite(folder, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;
		
		componentsConfigurationComposite = new ComponentsConfigurationComposite(container, SWT.NONE);
		
		Group buttonGroup =new Group(container,SWT.NONE);
	    buttonGroup.setBounds(5, 385, 440, 50);
		
		Button loadConfiguration = new Button(buttonGroup, SWT.PUSH);
	    loadConfiguration.setText("Load Configuration");
	    loadConfiguration.setBounds(5, 15, 120, 25);
	    BctComponentConfigurationLoadButton b= new BctComponentConfigurationLoadButton(componentsConfigurationComposite);
	    loadConfiguration.addSelectionListener(b);
	    
	    Button saveConfiguration = new Button(buttonGroup, SWT.PUSH);
	    saveConfiguration.setText("Save Configuration");
	    saveConfiguration.setBounds(135, 15, 120, 25);
	    SaveConfigurationButtonListener p= new SaveConfigurationButtonListener(componentsConfigurationComposite);
	    saveConfiguration.addSelectionListener(p);
		
		
		return container;
	}
	
	public String getConfigurationName() {
		return componentsConfigurationComposite.getConfigurationName();
	}
	
	public MonitoringConfiguration createMonitoringConfiguration() {
		ComponentsConfiguration cc = componentsConfigurationComposite.createComponentsConfiguration();
		FlattenerOptions fo = flattenerPage.getDefinedFlattenerOptions();
		
		//MonitoringConfiguration mc = new MonitoringConfiguration(getConfigurationName(),fo,cc);
		
		//return mc;
		throw new RuntimeException("Not implemented");
	}
	
	public void load(MonitoringConfiguration monitoringConfiguration) {
		try {
			componentsConfigurationComposite.load(monitoringConfiguration.getComponentsConfiguration());
		} catch (ComponentManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		flattenerPage.load(monitoringConfiguration.getFlattenerOptions());
	}
	
	
	

	
}
