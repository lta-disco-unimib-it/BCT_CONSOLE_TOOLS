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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourcesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This page shows the options to select the  Java Resources to monitor
 *  
 * @author Fabrizio Pastore
 *
 */
public class JavaResourcesMonitoringPage extends Composite implements BCTObservable, BCTObserver {



	private JavaResourcesTreeComposite tree;

	public JavaResourcesMonitoringPage(Composite _parent, int swt) {
		super(_parent,swt);

		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		
		this.setLayout(gl);
		
		GridData ld = new GridData(GridData.FILL_HORIZONTAL);
		
		ld.horizontalSpan = 2;
		ld.grabExcessHorizontalSpace = true;	
		
		Label smashAggregationLabel=new Label(this, SWT.NONE);
		smashAggregationLabel.setText("Resources to monitor");

		smashAggregationLabel.setLayoutData(ld);
		
		
		
		this.tree = new JavaResourcesTreeComposite(this,SWT.NONE);
		tree.addBCTObserver(this);
		tree.setToolTipText("Select the resources that contain your components classes." +
				"\nThese will be the resources automatically selected for instrumentation, but you will be able to insrument also other resources." +
				"\nThese resources are the ones used to fill the components tree in the project explorer.");
		
		
		
		
		
		//tree.setLayoutData(ld);	
		//tree.pack();
		
	}

	private BCTObservaleIncapsulated observable = new BCTObservaleIncapsulated(this);
	private ResourcesMonitoringOptions loadedOptions;

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}



	public void load(ResourcesMonitoringOptions monitoringOptions) {
		loadedOptions = monitoringOptions;
		
		IJavaProject expandedProject = monitoringOptions.getMonitoredProject();
		List<IJavaProject> projects = new ArrayList<IJavaProject>();
		
		
//		if ( project != null ){
//			projects.add(project);
//		}
		for ( IProject project :  ResourcesPlugin.getWorkspace().getRoot().getProjects() ){
			 IJavaProject jproject = JavaCore.create(project);
			projects.add(jproject);
		}
		
		
		
		tree.populateTree(projects,expandedProject);
		
		tree.selectElements(monitoringOptions.getAllMonitoredFragmentRoots());
		//JavaResourcesUtil.getSourceContainers(project);
	}
	
	public ResourcesMonitoringOptions createMonitoringOptions(){
		ResourcesMonitoringOptions newOpts = new ResourcesMonitoringOptions(loadedOptions.getProjectName());
		for ( IPackageFragmentRoot fragment : tree.getSelectedPackageFragments() ){
			newOpts.addFragmentToMonitor(fragment);
		}
		return newOpts;
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observable.notifyBCTObservers(message);
	}
	

}
