package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.BCTMonitoringConfigurationPage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import util.componentsDeclaration.Component;
import util.componentsDeclaration.CppMangledSignatureParser;
import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;
import util.componentsDeclaration.SignatureParser;

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

public class NewBCTCRegressionConfigurationWizard extends Wizard implements INewWizard, BCTObserver {
	

	private BCTMonitoringConfigurationPage resourcePage;
	private ISelection selection;
	//private ObjectFlattenerWizardPage flattenerPage;

	private CRegressionWizardPage regressionConfigurationPage;

	/**
	 * Constructor for BCTMonitoringResource.
	 */
	public NewBCTCRegressionConfigurationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		resourcePage = new BCTMonitoringConfigurationPage(selection);
		resourcePage.setAutomaticallyUpdateDataFolders(true);
		resourcePage.setOnlyJavaProjectsAllowed(false);
		
		//TODO: add default location for files
		addPage(resourcePage);
		resourcePage.addBCTObserver(this);
		
		regressionConfigurationPage = new CRegressionWizardPage(selection);
		addPage(regressionConfigurationPage);
		initRegressionConfigurationPage();
		
		
		if ( selection != null && selection instanceof IStructuredSelection ){
			IStructuredSelection sselection = (IStructuredSelection) selection;
			Object el = sselection.getFirstElement();
			if ( el instanceof IProject ){
				resourcePage.setReferredProject((IProject)el);
			}
			
		}
	}


	private void initRegressionConfigurationPage() {
		// TODO Auto-generated method stub
		
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
		
		//final FlattenerOptions opt = flattenerPage.getDefinedFlattenerOptions();
		final String referredProjectName = resourcePage.getReferredProjectName();
		
		
		final CRegressionConfiguration regressionConfiguration = regressionConfigurationPage.getConfiguration();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					FlattenerOptions opt = new FlattenerOptions(false,3,"all",new ArrayList<String>());
					doFinish(monitor,resourceName,dest,storageConfiguration,regressionConfiguration,opt,referredProjectName);
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
	 * @param regressionConfiguration 
	 * @param storageConfiguration 
	 * @param referredProjectName 
	 * @param referredProject 
	 */

	private void doFinish(IProgressMonitor monitor, String resourceName, IPath dest, StorageConfiguration storageConfiguration, CRegressionConfiguration regressionConfiguration, FlattenerOptions opt, String referredProjectName)
		throws CoreException {
		
		//calls to monitor cause invalid thread access
		MonitoringConfigurationFactory.createAndStoreMonitoringConfiguration(monitor, resourceName, dest,
				storageConfiguration, regressionConfiguration, opt,
				referredProjectName);

		
		
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
		System.out.println("UPDATED "+resourcePage.getResourceName());
	}
	
	public boolean canFinish() {
		return (resourcePage.isPageComplete());
	}


}