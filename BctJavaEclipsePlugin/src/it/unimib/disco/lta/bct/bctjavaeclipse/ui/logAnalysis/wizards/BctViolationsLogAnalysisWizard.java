package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.wizards;

import failureDetection.Failure;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfigurationUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.BctViolationsAnalysisSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import modelsViolations.BctModelViolation;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.tptp.logging.events.cbe.FormattingException;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import util.cbe.CBELogLoader;


public class BctViolationsLogAnalysisWizard extends Wizard implements INewWizard {
	private BctViolationsLogAnalysisOptionsPage page;
	private IStructuredSelection selection;
	private ArrayList<IFile> filesToOpen = new ArrayList<IFile>();
	private IFile startContainer;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public BctViolationsLogAnalysisWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new BctViolationsLogAnalysisOptionsPage();
		page.setFilesToOpen(filesToOpen);
		if ( startContainer != null ){
			page.setContainer(startContainer);
		}
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final IFile anomalyGraphsFolder = page.getBCTAnomalyGraphsFolder();
		final IFile violationsLogAnalysisFile = page.getBCTViolationsLogAnalysisFile();
		final List<IFile> logFiles = page.getLogFiles();
		final boolean copyLogs = page.copyLogs();
		
		
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(anomalyGraphsFolder, violationsLogAnalysisFile, monitor, logFiles, copyLogs);
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
	 * @param copyLogs 
	 * @param config 
	 */

	private void doFinish(
		IFile anomalyGraphsFolder,
		final IFile violationsLogAnalysisFile,
		IProgressMonitor monitor, List<IFile> files, boolean copyLogs)
		throws CoreException {

		monitor.beginTask("Creating folder "+anomalyGraphsFolder.getName(), 3);
		
		
		monitor.worked(1);
		
		
		
		//Save configuration
		monitor.setTaskName("Creating bctla file " + violationsLogAnalysisFile.getName());
		
		final BctViolationsAnalysisConfiguration config = BctViolationsAnalysisConfigurationUtil.createAndStoreBctViolationsAnalysisConfiguration(violationsLogAnalysisFile, copyLogs,files,anomalyGraphsFolder);
		
		

		monitor.worked(1);
		
		
		
		//Open editor
		
		monitor.setTaskName("Opening anomaly analysis editor...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, violationsLogAnalysisFile, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}
	






	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		
		
		Iterator it = selection.iterator();
		
		while ( it.hasNext() ){
			Object next = it.next();
			if ( next instanceof org.eclipse.core.resources.IFile ){
				IFile file = (IFile)next;
				filesToOpen.add(file);
			}
		}
	}
	
	public void setFilesToOpen( Collection<IFile> files){
		if ( page == null ){
			filesToOpen.addAll(files);
		} else {
			page.setFilesToOpen(files);	
		}
		
	}

	public void setFileToOpen(IFile fileToOpen) {
		if ( page == null ){
			filesToOpen.add(fileToOpen);
		} else {
		page.setFileToOpen(fileToOpen);
		}
	}

	public void setAnalysisFilesContainer(IFile fileContainer) {
		if ( page == null ){
			startContainer = fileContainer;
		} else {
			page.setContainer(fileContainer);
		}
	}
}