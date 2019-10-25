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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterRule;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.ComponentsDefinitionComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.BctMonitoringConfigurationEditor;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import util.componentsDeclaration.Component;

public class ComponentsConfigurationComposite extends Composite implements BCTObservable, BCTObserver {

	private ArrayList <CallFilter> callFilterList;
	public static final String ruleTypes[]={"INCLUDE", "EXCLUDE"};
	ArrayList<CallFilterRule> rules= new ArrayList<CallFilterRule>();

	private HashMap<String,FlattenerOptions> flattenerHash = new HashMap<String, FlattenerOptions>();
	
	ArrayList<String>classesToIgnore=new ArrayList<String>();
	
	private Text configurationName;


	
	
	public static class ComponentConfigurationCompositeException extends Exception {

		public ComponentConfigurationCompositeException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ComponentConfigurationCompositeException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	
	private BCTObservaleIncapsulated observer = new BCTObservaleIncapsulated(this);
	
	private CallFilterTable _callFilterTable;
	private ComponentsDefinitionComposite componentsComposite;
	private Button monitorInternalCallsButton;
	private Collection<CallFilter> callFilters = null;
	private CallsFilterShell callsFilterShell;


	
	
	public ComponentsConfigurationComposite(Composite parent, int style) {
		super(parent, style);
		
		
		Label ConfigurationNameLabel;

		GridLayout g = new GridLayout();
		g.numColumns = 2;
		setLayout(g);
		
		
		GridData fillHorizontal = new GridData(GridData.FILL_HORIZONTAL);

		
		ConfigurationNameLabel=new Label(this, SWT.NULL);
	    ConfigurationNameLabel.setText("Configuration name:");	

	    configurationName = new Text(this, SWT.SINGLE | SWT.BORDER | SWT.FILL);
	    configurationName.setLayoutData(fillHorizontal);
	    
	    configurationName.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				observer.notifyBCTObservers(null);
			}
	    	
	    });
	    configurationName.setToolTipText("Component configuration name.");

	    
	    
	    GridData ld = new GridData(GridData.FILL_HORIZONTAL);
	    ld.horizontalSpan = 2;
	    ld.grabExcessHorizontalSpace = true;
	    
	    
	    componentsComposite = new ComponentsDefinitionComposite(this, SWT.NULL);

	    componentsComposite.addBCTObserver(this);
	    
	    
	    
	    componentsComposite.setLayoutData(ld);
	    
	    //FILTER BOX
	    ArrayList<String> componentsNames = new ArrayList<String>();
	    componentsNames.add("*");

	    //Composite monitorComposite = new Composite(this,SWT.NULL);
		
	    //monitorComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
	    
		Label smashAggregationLabel=new Label(this, SWT.NULL);
		smashAggregationLabel.setText("Monitor intra component calls");
		
		monitorInternalCallsButton = new Button(this,SWT.CHECK);
		
		monitorInternalCallsButton.setToolTipText("Select this checkbox if you want to monitor also the internal method invocations of a component." +
				"\nSuppose to define a component C composed by class C.java, and a component D composed by class D.java." +
				"\nSuppose that method C.m() invokes C.n() and D.m() in sequence." +
				"\nWith this checkbox selected BCT will trace both the invocation of method C.m(), and the following invocations of C.n() and D.m()." +
				"\nWithout this checkbox selected BCT will trace the invocation of method C.m(), and the invocation of D.m(), without tracing the invocation of " +
				"C.n(), because made within anothe method of component C.");
		monitorInternalCallsButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observer.notifyBCTObservers(null);
			}
			
		});
		
		
		
		ConfigurationNameLabel=new Label(this, SWT.NULL);
	    ConfigurationNameLabel.setText("Calls to exclude:");	
	    //Label empty = new Label(this, SWT.NULL);
	    
	    Button callFilterButton = new Button(this,SWT.PUSH);
	    callFilterButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				editCallFilters();
			}
		});
	    
//		callFilterTable = new CallFilterTable(this, componentsNames, componentsComposite);
//	    callFilterTable.setLayoutData(ld);
//	    callFilterTable.setToolTipText("This table indicates the environment to exclude from monitoring." +
//	    		"\nAn environment method is a method which is not part of the components to monitorw, which" +
//	    		" is invoked within the monitored components." +
//	    		"\nA list of regular expressions can be defined to exclude environment methods from being monitored." +
//	    		"\nA method that does not match any expression is monitored by default.");
//	    
//	    
//	    
//	    callFilterTable.addBCTObserver(this);
	}

	public void editCallFilters(){
		callsFilterShell = new CallsFilterShell(componentsComposite.getComponents(),callFilters);
		callsFilterShell.addBCTObserver(this);
		callsFilterShell.open();
	}
	
//	public void editComponentButtonPressed(){
//		
//		try {
//
//			ComponentDefinitionShell box = new ComponentDefinitionShell(this,this.getSelectedComponent());
//			box.setComponentNameLabelText(componentLabelName);
//			box.addBCTObserver(this);
//			box.open();
//			
//		} catch (ComponentManagerException e) {
//			MessageDialog.openError(getShell(), "Error", "Cannot edit component "+e.getMessage());
//		}
//		
//	}
	
	
	public void setConfigurationName(String configurationName) {
		this.configurationName.setText( configurationName );
		
		observer.notifyBCTObservers(null);
	}

	public String getConfigurationName() {
		return configurationName.getText();
	}

	
	public void setConfigurationNameText(String name)
	{
		configurationName.setText(name);
		observer.notifyBCTObservers(null);
	}


	public void setComponents(Collection<Component> comp) {

		observer.notifyBCTObservers(null);
	}
	



	public void setComponentList(List componentList) {
		componentsComposite.setComponentList(componentList);
		observer.notifyBCTObservers(null);
	}


	public void setCallFilter(CallFilter callFilter) {
		
		observer.notifyBCTObservers(null);
	}


	public void addComponent(Component component) throws ComponentManagerException {
		
		observer.notifyBCTObservers(null);
	}

//	public void addCallFilte(CallFilter callFilter) throws CallFilterException {
//		callFilterList =new ArrayList<CallFilter>();
//		callFilterList.add(callFilter);
//		System.out.println("CALL FILTERLIST"+ callFilterList);
//		
//		observer.notifyBCTObservers(null);
//	}





	
	
	public boolean containsFlattener(FlattenerOptions flattenerOptions) {
		return flattenerHash.values().contains(flattenerOptions);
	}

	/**
	 * Return a monitoring configuration object built using the options 
	 * selected by the user in the configuration window.
	 * @return
	 */
	public ComponentsConfiguration createComponentsConfiguration(){
		
		Collection <Component> components= new ArrayList<Component>();
		components.addAll(componentsComposite.getComponents());
		

		
		ComponentsConfiguration mc = new ComponentsConfiguration(getConfigurationName(), callFilters ,components,monitorInternalCallsButton.getSelection());
		System.out.println( "CF "+mc.getCallFilters().size() );
		

		return mc;

	}







	public void removeSelectedComponents() {
		String names[] = componentsComposite.getSelectedComponentsNames();
		
		
		for ( String componentToRemove : names ){
			//callFilterTable.removeComponentName(componentToRemove);
		}
		
		observer.notifyBCTObservers(null);
	}

	public void removeComponent(int index) {
		String cName = componentsComposite.getComponent(index);
		
		//callFilterTable.removeComponentName(cName);
		
		observer.notifyBCTObservers(null);
		
	}
	
	private void removeAllComponents() {
		componentsComposite.removeAllComponents();
		observer.notifyBCTObservers(null);
	}


	public void load(ComponentsConfiguration componentsConfiguration) throws ComponentManagerException {
		removeAllComponents();
		
		
		Collection<Component> components = componentsConfiguration.getComponents();
		componentsComposite.addComponents(components);
		
		String ccName = componentsConfiguration.getConfigurationName();
		if ( ccName == null ){
			ccName = "";
		}
		setConfigurationName(ccName);
		
		ArrayList<String> componentNames = new ArrayList<String>();
		componentNames.add("*");
		for ( Component c : components ){
			componentNames.add(c.getName());
		}
		
		callFilters = componentsConfiguration.getCallFilters();
//		callFilterTable.reset(componentNames);
//		callFilterTable.loadCallFilters(componentsConfiguration.getCallFilters());
		
		monitorInternalCallsButton.setSelection(componentsConfiguration.getMonitorInternalCalls());
		observer.notifyBCTObservers(null);
	}

	public void addBCTObserver(BCTObserver l) {
		observer.addBCTObserver(l);
	}





	public IProject getProject() {
		return componentsComposite.getProject();
	}


	public void bctObservableUpdate(Object modifiedObject, Object message) {
		if ( modifiedObject == callsFilterShell ){
			callFilters = callsFilterShell.getCallFilters();
		}
		observer.notifyBCTObservers(message);
	}



	public boolean contains(Component component) {
		return componentsComposite.contains(component);
	}



	public Component getComponent(String componentName) {
		return componentsComposite.getComponent(componentName);
	}



	public Component getSelectedComponent() throws ComponentManagerException {
		return componentsComposite.getSelectedComponent();
	}






	public void setProject(IProject javaProject) {
		componentsComposite.setProject(javaProject);
	}






	public Collection<? extends Component> getComponents() {
		return componentsComposite.getComponents();
	}






}
