package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.wizards.BctViolationsLogAnalysisWizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;

public class NewAnomaliesAnalysisAction  implements IObjectActionDelegate {

	public NewAnomaliesAnalysisAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		
		if ( selection instanceof IStructuredSelection ){

			final Display display = PlatformUI.getWorkbench().getDisplay();
			final Shell shell = display.getActiveShell();
            
           


			IStructuredSelection ss = (IStructuredSelection) selection;
			IFile selectedElement = (IFile) ss.getFirstElement();
			File selectedFile = selectedElement.getLocation().toFile();
				
			
			try {
				//Read the monitoring configuration
				final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);
				runAnomaliesAnalysis(mc);
				
			} catch (FileNotFoundException e) {
				
			} catch (ConfigurationFilesManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void runAnomaliesAnalysis(final MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException {
		File logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);
		IFile fileToOpen = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(logFile.getAbsolutePath()));

		File violationsAnalysisFolder = ConfigurationFilesManager.getViolationsLogAnalysisFolder(mc);
		IFile fileContainer = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(violationsAnalysisFolder.getAbsolutePath()));
		
		if ( ! fileToOpen.exists() ){
			//TODO: add message dialog
			
			MessageDialog.openInformation(null, "Anomalies not Available", "The tool did not identify any anoamly");
			
			return;
		}
		
//		try {
//			RegressionAnalysis.identifyRegressions(mc);
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CBEBctViolationsLogLoaderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		BctViolationsLogAnalysisWizard wizard = new BctViolationsLogAnalysisWizard();
		
		WizardDialog dialog = new WizardDialog(new Shell(), wizard);
		dialog.create();
		wizard.setFileToOpen(fileToOpen);
		wizard.setAnalysisFilesContainer(fileContainer);
		dialog.open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
