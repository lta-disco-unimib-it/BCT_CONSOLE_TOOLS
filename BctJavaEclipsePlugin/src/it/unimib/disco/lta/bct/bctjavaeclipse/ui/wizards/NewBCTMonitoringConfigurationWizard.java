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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.BCTMonitoringConfigurationPage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * ".bctmr". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class NewBCTMonitoringConfigurationWizard extends Wizard implements INewWizard, BCTObserver {
	

	private BCTMonitoringConfigurationPage resourcePage;
	private ISelection selection;
	//private ObjectFlattenerWizardPage flattenerPage;
	private BCTComponentsConfigurationWizardPage componentsPage;

	/**
	 * Constructor for BCTMonitoringResource.
	 */
	public NewBCTMonitoringConfigurationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		resourcePage = new BCTMonitoringConfigurationPage(selection);
		
		//TODO: add default location for files
		addPage(resourcePage);
		resourcePage.addBCTObserver(this);
		
		componentsPage = new BCTComponentsConfigurationWizardPage(selection);
		addPage(componentsPage);
		initComponentsPage();
		
		
		
		
//		flattenerPage = new ObjectFlattenerWizardPage(selection);
//		addPage(flattenerPage);
		
		
		if ( selection != null && selection instanceof IStructuredSelection ){
			IStructuredSelection sselection = (IStructuredSelection) selection;
			Object el = sselection.getFirstElement();
			if ( el instanceof IProject ){
				resourcePage.setReferredProject((IProject) el);
			}
			
		}
	}

	
	private void initComponentsPage() {
		ComponentsConfiguration cc = new ComponentsConfiguration();
		List<CallFilter> callFilters = new ArrayList<CallFilter>();
		CallFilterGlobal cf = new CallFilterGlobal();
		cf.addRule( new MatchingRuleInclude("java.lang", "Process", ".*") );
		cf.addRule( new MatchingRuleInclude("java.lang", "Thread", ".*") );
		cf.addRule( new MatchingRuleInclude("java.lang", "Runtime", ".*") );
		cf.addRule( new MatchingRuleExclude("java.lang", ".*", ".*") );
		cf.addRule( new MatchingRuleExclude("java.util", ".*", ".*") );
		//cf.addRule( new MatchingRuleExclude("java.io", ".*", ".*") );
		callFilters.add(cf);
		
		cc.setCallFilters(callFilters);
		
		componentsPage.load(cc);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String resourceName = resourcePage.getResourceName();
		final IPath dest = resourcePage.getDestination();
		final StorageConfiguration storageConfiguration = resourcePage.createStorageConfiguration();
		final ComponentsConfiguration componentsConfiguration = componentsPage.createComponentsConfiguration();
		//final FlattenerOptions opt = flattenerPage.getDefinedFlattenerOptions();
		final String referredProjectName = resourcePage.getReferredProjectName();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					FlattenerOptions opt = new FlattenerOptions(false,3,"all",new ArrayList<String>());
					doFinish(monitor,resourceName,dest,storageConfiguration,componentsConfiguration,opt,referredProjectName);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}

			
		};
		
		
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		
		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 * @param monitor 
	 * @param resourceName 
	 * @param dest 
	 * @param opt 
	 * @param componentsConfiguration 
	 * @param storageConfiguration 
	 * @param referredProjectName 
	 * @param referredProject 
	 */

	private void doFinish(IProgressMonitor monitor, String resourceName, IPath dest, StorageConfiguration storageConfiguration, ComponentsConfiguration componentsConfiguration, FlattenerOptions opt, String referredProjectName)
		throws CoreException {
		
		//calls to monitor cause invalid thread access
		monitor.beginTask("Storing monitoring configuration on file ",2);
		
		//get data
		//is it correct or we have to get this data before, at the caller side? It should happen that the configuration window have bee disposed? 
		
		
		File serFile = ResourcesPlugin.getWorkspace().getRoot().getFile(dest).getRawLocation().toFile();
		ActionsMonitoringOptions mo = new ActionsMonitoringOptions();
		TestCasesMonitoringOptions tcMo = new TestCasesMonitoringOptions();
		tcMo.setTestCasesRegistryOptions(new TestCasesMemoryRegistryOptions());
		MonitoringConfiguration mrc = new MonitoringConfiguration(resourceName,storageConfiguration,opt,componentsConfiguration,mo,tcMo);
		mrc.setReferredProjectName(referredProjectName);
		
		monitor.worked(1);
		try {
			
			MonitoringConfigurationSerializer.serialize(serFile, mrc);
			ConfigurationFilesManager.updateConfigurationFiles(mrc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Status s = new Status(1, DefaultOptionsManager.pluginID, "Cannot save monitoring configuration to "+dest+", an exception occurred.",e);
			throw new CoreException(s);
		} catch (ConfigurationFilesManagerException e) {
			e.printStackTrace();
			Status s = new Status(1, DefaultOptionsManager.pluginID, "Cannot update bct configuration files, an exception occurred.",e);
			throw new CoreException(s);
		}

		//FIXME: maybe this is not the best solution
		IContainer containerToRefresh = ResourcesPlugin.getWorkspace().getRoot().getFile(dest).getParent();
		containerToRefresh.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		
		monitor.worked(1);

		
		
	}
	
	/**
	 * We will initialize file contents with a sample text.
	 */

	private InputStream openContentStream() {
		String contents =
			"This is the initial file contents for *..bctmr file that should be word-sorted in the Preview page of the multi-page editor";
		return new ByteArrayInputStream(contents.getBytes());
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, "BctJavaEclipse", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		componentsPage.setConfigurationName(resourcePage.getResourceName());
		System.out.println("UPDATED "+resourcePage.getResourceName());
	}
	
	public boolean canFinish() {
		return (componentsPage.isPageComplete()&&resourcePage.isPageComplete());
	}


}