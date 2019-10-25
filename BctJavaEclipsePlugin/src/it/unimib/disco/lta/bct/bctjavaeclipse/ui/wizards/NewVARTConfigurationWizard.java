package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.BCTMonitoringConfigurationPage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import conf.InvariantGeneratorSettings;

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

public class NewVARTConfigurationWizard extends Wizard implements INewWizard, BCTObserver {
	

	private BCTMonitoringConfigurationPage resourcePage;
	private ISelection selection;
	//private ObjectFlattenerWizardPage flattenerPage;

	private CRegressionWizardPage regressionConfigurationPage;
	private VARTRegressionWizardPage vartConfigurationPage;
	
	/**
	 * Constructor for BCTMonitoringResource.
	 */
	public NewVARTConfigurationWizard() {
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
		
		CRegressionConfiguration c = new CRegressionConfiguration();
		c.setTraceAllLinesOfChildren(false);
		c.setTraceAllLinesOfMonitoredFunctions(true);
		
		regressionConfigurationPage.load(c);
		
		vartConfigurationPage = new VARTRegressionWizardPage(selection);
		addPage(vartConfigurationPage);
		
		
		if ( selection != null && selection instanceof IStructuredSelection ){
			IStructuredSelection sselection = (IStructuredSelection) selection;
			Object el = sselection.getFirstElement();
			if ( el instanceof IProject ){
				resourcePage.setReferredProject((IProject)el);
			}
			
		}
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
	  	final VARTRegressionConfiguration vartConfiguration = vartConfigurationPage.getConfiguration();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					FlattenerOptions opt = new FlattenerOptions(false,3,"all",new ArrayList<String>());
					doFinish(monitor,resourceName,dest,storageConfiguration,regressionConfiguration,vartConfiguration,opt,referredProjectName);
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
	 * @param vartConfiguration 
	 * @param referredProjectName 
	 * @param referredProject 
	 */

	private void doFinish(IProgressMonitor monitor, String resourceName, IPath dest, StorageConfiguration storageConfiguration, CRegressionConfiguration regressionConfiguration, VARTRegressionConfiguration vartConfiguration, FlattenerOptions opt, String referredProjectName)
		throws CoreException {
		
		//calls to monitor cause invalid thread access
		MonitoringConfiguration mc = MonitoringConfigurationFactory.createAndStoreMonitoringConfiguration(monitor, resourceName, dest,
				storageConfiguration, regressionConfiguration, opt,
				referredProjectName);
		mc.setConfigurationType(MonitoringConfiguration.ConfigurationTypes.VART_Config);
		mc.putAdditionalConfiguration(VARTRegressionConfiguration.class, vartConfiguration);
		
		mc.getInvariantGeneratorOptions().put(InvariantGeneratorSettings.Options.daikonUndoOptmizations, "true");
		
		MonitoringConfigurationFactory.storeMonitoringConfiguration(dest, mc);
		
		
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
		return (resourcePage.isPageComplete() && regressionConfigurationPage.isPageComplete() && vartConfigurationPage.isPageComplete() );
	}


}