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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.CleaningUtil;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import util.FileUtil;

public class DeleteBctData implements IObjectActionDelegate  {

	

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		run(action, null);
	}
	
	public void run(IAction action, IJobChangeListener jobListener) {

		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

		if ( selection instanceof IStructuredSelection ){

			final Display display = PlatformUI.getWorkbench().getDisplay();
			final Shell shell = display.getActiveShell();
            
           


			IStructuredSelection ss = (IStructuredSelection) selection;
			IFile selectedElement = (IFile) ss.getFirstElement();
			File selectedFile = selectedElement.getLocation().toFile();
				//Open the bct console for debug info
				//BctConsoleView bctConsoleView = (BctConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
				
			
			
			
				//Read the monitoring configuration
				try {
					final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);
					
					boolean result = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Delete BCT data?", "Are you sure to delete data recorded for "+mc.getConfigurationName()+" ?" );
					
					if ( ! result ){
						return;
					}
					
					final File bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
					
					Job job = new Job("Deleting data for "+mc.getConfigurationName()){

						@Override
						protected IStatus run(IProgressMonitor monitor) {
							CleaningUtil.deleteAllBctData(bctHome);
							
							
							return Status.OK_STATUS;
						}
						
					};
					
					job.schedule();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConfigurationFilesManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
	

}
