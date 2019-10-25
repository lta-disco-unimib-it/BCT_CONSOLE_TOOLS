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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctResourcesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesFileRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.ComponentsDefinitionComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;

import java.util.ArrayList;
import java.util.List;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import util.componentsDeclaration.Component;

public class TestCasesMonitoringPage extends Composite implements BCTObservable, BCTObserver {


	private ComponentsDefinitionComposite componentsComposite;
	private Button monitorTestCasesButton;
	private Group configurationBox;
	private Button traceTestCasesButton;
	private Button keepStateOnFile;
	private Button keepStateOnMemory;

	public TestCasesMonitoringPage(Composite _parent, int swt) {
		super(_parent,swt);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		this.setLayout(layout);
        
		Label smashAggregationLabel=new Label(this, SWT.NULL);
		smashAggregationLabel.setText("Monitor test cases");
		//smashAggregationLabel.setBounds(10, 20, 200, 20);
		
		monitorTestCasesButton = new Button(this,SWT.CHECK);
		//monitorTestCasesButton.setBounds(220, 20, 20, 20);
		monitorTestCasesButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
				if ( monitorTestCasesButton.getSelection() ){
					configurationBox.setEnabled(true);
					componentsComposite.setEnabled(true);
				} else {
					configurationBox.setEnabled(false);
					componentsComposite.setEnabled(false);
				}
			}
			
		});

		
		
		
		configurationBox = new Group(this, SWT.NONE);
		configurationBox.setText("Test cases monitoring options");
		
		//configurationBox = new Composite(this,SWT.NONE);
		GridLayout cBoxL = new GridLayout();
		cBoxL.numColumns = 2;
		configurationBox.setLayout(cBoxL);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    gd.horizontalSpan = 2;
	    gd.grabExcessHorizontalSpace = true;
		configurationBox.setLayoutData(gd);
	    

		Label label = new Label(configurationBox, SWT.NONE);
		
	    Composite testExecutionConstraints = new Composite(configurationBox, SWT.NONE);
		
	    GridLayout tecLayout = new GridLayout();
	    tecLayout.numColumns = 3;
	    testExecutionConstraints.setLayout(tecLayout);
	    
	    GridData tecLD = new GridData(GridData.FILL_HORIZONTAL);
		tecLD.horizontalSpan = 2;
		tecLD.grabExcessHorizontalSpace = true;
		testExecutionConstraints.setLayoutData(tecLD);
		
		label = new Label(configurationBox, SWT.NONE);
		
		label = new Label(testExecutionConstraints, SWT.NONE);
		label.setText("Test execution constraints:");
		
		GridData ecLD = new GridData(GridData.FILL_HORIZONTAL);
		ecLD.horizontalSpan = 3;
		ecLD.grabExcessHorizontalSpace = true;
		label.setLayoutData(ecLD);
		
		label = new Label(testExecutionConstraints, SWT.NONE);
		
		
		smashAggregationLabel=new Label(testExecutionConstraints, SWT.NULL);
		smashAggregationLabel.setText("Drivers and test cases run on the same JVM");
		//smashAggregationLabel.setBounds(10, 20, 200, 20);

		keepStateOnMemory = new Button(testExecutionConstraints,SWT.CHECK);
		//keepStateOnFile.setBounds(220, 20, 20, 20);
		keepStateOnMemory.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
				if ( keepStateOnFile.getSelection() ){
					
					keepStateOnFile.setSelection(false);
					observable.notifyBCTObservers(null);
				} else {
					keepStateOnFile.setSelection(true);
				}
				
			}
			
		});
		
		
		label = new Label(testExecutionConstraints, SWT.NONE);
		label.setText("    ");
		
		smashAggregationLabel=new Label(testExecutionConstraints, SWT.NULL);
		smashAggregationLabel.setText("Drivers and test cases run on different JVMs");
		//smashAggregationLabel.setBounds(10, 20, 200, 20);
		
		keepStateOnFile = new Button(testExecutionConstraints,SWT.CHECK);
		//keepStateOnFile.setBounds(220, 20, 20, 20);
		keepStateOnFile.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
				if ( keepStateOnFile.getSelection() ){
					keepStateOnMemory.setSelection(false);
					observable.notifyBCTObservers(null);
				} else {
					keepStateOnFile.setSelection(true);
				}
			}
			
		});
		

		
		
		label = new Label(configurationBox, SWT.NONE);
		

		Label traceTestLabel=new Label(configurationBox, SWT.NONE);
		traceTestLabel.setText("Trace test cases during DataRecording");
		//traceTestLabel.setBounds(5, 20, 200, 20);
		
		traceTestCasesButton = new Button(configurationBox,SWT.CHECK);
		//traceTestCasesButton.setBounds(220, 20, 20, 20);

		traceTestCasesButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
			}
			
		});
	    

		label = new Label(configurationBox, SWT.NONE);
		
		componentsComposite = new ComponentsDefinitionComposite(configurationBox, SWT.NONE, "Test Cases:", "Test Case:");
		//componentsComposite.setBounds(5, 50, 570, 200);
		
	    componentsComposite.addBCTObserver(this);
        
	    
	    GridData ld = new GridData(GridData.FILL_HORIZONTAL);
	    ld.horizontalSpan = 2;
	    ld.grabExcessHorizontalSpace = true;
	    componentsComposite.setLayoutData(ld);
	    
	    configurationBox.setEnabled(false);
	}

	private BCTObservaleIncapsulated observable = new BCTObservaleIncapsulated(this);

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}



	public void load(TestCasesMonitoringOptions monitoringOptions) {
		if ( monitoringOptions == null )
			return;

		if ( monitoringOptions.isMonitorTestCases() ){
			monitorTestCasesButton.setSelection(true);
			configurationBox.setEnabled(true);
		}

		if ( monitoringOptions.isTraceTestExecution() ){
			traceTestCasesButton.setSelection(true);
		}
		
		TestCasesRegistryOptions testCasesRegistryOptions = monitoringOptions.getTestCasesRegistryOptions();
		if ( testCasesRegistryOptions instanceof TestCasesMemoryRegistryOptions ){
			keepStateOnMemory.setSelection(true);
		} else if ( testCasesRegistryOptions instanceof TestCasesFileRegistryOptions ){
			keepStateOnFile.setSelection(true);
		}
		
		List<Component> testCases = monitoringOptions.getTestCasesGroupsToMonitor();
		if ( testCases != null ){
			try {
				componentsComposite.load(testCases);
			} catch (ComponentManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public TestCasesMonitoringOptions createMonitoringOptions(){
		TestCasesMonitoringOptions mo = new TestCasesMonitoringOptions();
		
		//add actions/tests
		mo.setMonitorTestCases(monitorTestCasesButton.getSelection());
		mo.setTraceTestExecution(traceTestCasesButton.getSelection());
		
		List<Component> list = new ArrayList<Component>();
		list.addAll(componentsComposite.getComponents());
		mo.setTestCasesGroupsToMonitor(list);
		
		if ( keepStateOnFile.getSelection() ){
			TestCasesFileRegistryOptions opt = new TestCasesFileRegistryOptions();
			
			mo.setTestCasesRegistryOptions(opt);
		} else if ( keepStateOnMemory.getSelection() ){
			mo.setTestCasesRegistryOptions(new TestCasesMemoryRegistryOptions());
		}
		return mo;
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observable.notifyBCTObservers(message);
	}
}
