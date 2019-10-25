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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentDefinitionShell;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsClassesTreeComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationManager;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import util.componentsDeclaration.Component;

public class ComponentsDefinitionComposite extends Composite implements ComponentsConfigurationManager, BCTObservable, BCTObserver {
//	private ComponentsClassesTreeComposite componentClasses;
	private List componentList;
	private HashMap<String,Component> components = new HashMap<String, Component>();
	private BCTObservaleIncapsulated observer;
	private String componentsColumnName;
	private String componentLabelName;
	
	public ComponentsDefinitionComposite(Composite parent, int style) {
		this(parent,style, "Components: ", "Component name:" );
	}
	
	public ComponentsDefinitionComposite(Composite parent, int style, String componentsColumnName, String componentLabelName) {	
		super(parent, style);
		observer = new BCTObservaleIncapsulated(this);
		this.componentsColumnName = componentsColumnName;
		this.componentLabelName = componentLabelName;
		GridLayout l = new GridLayout();
		l.numColumns = 2;
		this.setLayout(l);
		
	    Label components =new Label(this, SWT.NULL);
	    components.setText(componentsColumnName);

	    Label label =new Label(this, SWT.NULL);
		
	    componentList = new List(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
	    
	    GridData listLayoutData = new GridData(GridData.FILL_BOTH);
	    listLayoutData.minimumHeight = 150;
	    listLayoutData.grabExcessVerticalSpace=true;
	    componentList.setLayoutData(listLayoutData);
	    
	    componentList.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
			}
	    	
	    });
	    
	    
	    Composite buttons = new Composite(this, SWT.NONE);
	    buttons.setLayout(new FillLayout(SWT.VERTICAL));
	    
	    //Add button
	    Button addButton = new Button(buttons, SWT.PUSH );
	    addButton.setText("Add");

	    addButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				addComponentButtonPressed();
			}
	    	
	    });
	    addButton.setToolTipText("Add a new definition.");
	    
	    //Edit button
	    Button editButton = new Button(buttons, SWT.PUSH);
	    editButton.setText("Edit");

	    editButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				editComponentButtonPressed();
			}
	    	
	    });
	    editButton.setToolTipText("Edit the selected definition.");
	    
	    //Remove button
	    Button removeButton = new Button(buttons, SWT.PUSH);
	    removeButton.setText("Remove");
	    
	    removeButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				removeSelectedComponents();
			}
	
	    });
	    removeButton.setToolTipText("Remove the selected definition.");
	    
//	    componentClasses = new ComponentsClassesTreeComposite(this,SWT.NONE);
//	    componentClasses.setBounds(marginX+240, marginY+20, 310, 100);
	    
		
	}
	
	public void editComponentButtonPressed(){
		
		try {

			ComponentDefinitionShell box = new ComponentDefinitionShell(this,this.getSelectedComponent());
			box.setComponentNameLabelText(componentLabelName);
			box.addBCTObserver(this);
			box.open();
			
		} catch (ComponentManagerException e) {
			MessageDialog.openError(getShell(), "Error", "Cannot edit component "+e.getMessage());
		}
		
	}
	
	
	public void addComponentButtonPressed(){
		ComponentDefinitionShell box = new ComponentDefinitionShell(this);
		box.setComponentNameLabelText(componentLabelName);
		box.addBCTObserver(this);
		box.open();
	}
	
	public void setComponents(Collection<Component> comp) {

		Component[] c= (Component[])comp.toArray(new Component[0]);
		componentList.removeAll();
		for (int i=0;i<c.length;i++)
		{
			
			components.put(c[i].getName(),c[i]);
			componentList.add(c[i].getName());
		}
		
	}
	
	public void setComponentList(List componentList) {
		this.componentList = componentList;
		
	}

	public void addComponent(Component component) throws ComponentManagerException {
		if ( component == null ){
			throw new ComponentManagerException("No component defined");
		}
		if ( components.values().contains(component) )
			throw new ComponentManagerException("Duplicated component name");
		if ( ! components.containsKey(component.getName()) ){
			components.put(component.getName(),component);
			componentList.add(component.getName());
			
		}
		
		observer.notifyBCTObservers(null);
	}
	
	public Component getSelectedComponent() throws ComponentManagerException {
		if ( componentList.getSelectionIndex() == -1 )
			throw new ComponentManagerException("No component selected");
		String name = componentList.getSelection()[0];
		return components.get(name);
	}
	

	public boolean contains(Component component) {
		return components.values().contains(component);
	}

	public Collection<Component> getComponents() {
		return components.values();
	}
	
	public void addComponents(Collection<Component> components) throws ComponentManagerException {
		for( Component component : components ){
			addComponent(component);
		}
		
//		componentClasses.clean();
//		componentClasses.showComponents(this.components.values());
		
		
		observer.notifyBCTObservers(null);
	}

	public void removeSelectedComponents() {
		
		int[] idxs = componentList.getSelectionIndices();
		String names[] = new String[idxs.length];
		
		for ( int i=0; i < idxs.length; ++i ){
			names[i] = componentList.getItem(i); 
		}
		
		componentList.remove( idxs);
		
		for ( String componentToRemove : names ){
			components.remove(componentToRemove);
		}
		
//		componentClasses.showComponents(components.values());
		observer.notifyBCTObservers(null);
	}

	public void removeComponent(int index) {
		String cName = componentList.getItem(index);
		componentList.remove(index);
		components.remove(cName);
//		componentClasses.showComponents(components.values());
		observer.notifyBCTObservers(null);
		
	}
	
	public void removeAllComponents() {
		componentList.removeAll();
		components.clear();
//		componentClasses.clean();
		observer.notifyBCTObservers(null);
	}

	public String getComponent(int index) {
		return componentList.getItem(index);
	}

	public String[] getSelectedComponentsNames() {
		int[] idxs = componentList.getSelectionIndices();
		String names[] = new String[idxs.length];
		
		for ( int i=0; i < idxs.length; ++i ){
			names[i] = componentList.getItem(i); 
		}
		
		return names;
	}


	public Component getComponent(String componentName) {
		return components.get(componentName);
	}
	
	public void setProject(IProject javaProject) {
		this.project = javaProject;
//		componentClasses.setProject(javaProject);
//		componentClasses.showComponents(components.values());
	}
	
	private IProject project;

	public IProject getProject() {
		return project;
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observer.addBCTObserver(bctObserver);
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observer.notifyBCTObservers(message);
	}

	public void load(java.util.List<Component> components) throws ComponentManagerException {
		removeAllComponents();
		for ( Component component : components ){
			addComponent(component);
		}
	}

	
}
