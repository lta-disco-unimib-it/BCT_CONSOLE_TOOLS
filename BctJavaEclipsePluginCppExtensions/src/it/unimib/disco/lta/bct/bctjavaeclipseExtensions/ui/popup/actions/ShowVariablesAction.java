package it.unimib.disco.lta.bct.bctjavaeclipseExtensions.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.VariablesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class ShowVariablesAction implements IObjectActionDelegate  {



	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {

		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

		if ( selection instanceof IStructuredSelection ){

			final Display display = PlatformUI.getWorkbench().getDisplay();
			final Shell shell = display.getActiveShell();
            
           


			IStructuredSelection ss = (IStructuredSelection) selection;
			
			Object first = ss.getFirstElement();
			
			final ITranslationUnit tu;
			if ( first instanceof ITranslationUnit ){
				tu = (ITranslationUnit) first;
//				IPath location = ((ITranslationUnit)first).getResource().getLocation();
//				file = ResourcesPlugin.getWorkspace().getRoot().getFile(location); 
//			} else if ( first instanceof IFile ){
//				file = (IFile) first;
			} else {
				return;
			}
//			final IFile selectedElement = (IFile) file;

		
//				final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);


				
				//Run it as a Job, we do not want to block users operations 
				Job job = new Job("Show variables : "){
					@Override
					protected IStatus run(IProgressMonitor monitor) {
							
							try {
								VariablesUtil.getVariables(tu, 0);
							} catch (CoreException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return Status.OK_STATUS;
					}

					
				};
				
				
				job.schedule();


			//TODO: Add message dialogs in case of errors
			} 
		

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
