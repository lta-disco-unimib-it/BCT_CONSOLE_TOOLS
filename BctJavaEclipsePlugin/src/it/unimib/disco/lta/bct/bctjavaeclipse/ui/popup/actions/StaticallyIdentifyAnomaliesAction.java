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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.AdditionalInferenceOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfigurationUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.ProcessesRunner;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.InferModelWizard;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.StaticallyIdentifyUsageAnomaliesResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.StaticallyIdentifyUsageAnomaliesWizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class StaticallyIdentifyAnomaliesAction  implements IObjectActionDelegate  {

	private ISelection selection;

	public StaticallyIdentifyAnomaliesAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		

		if ( selection == null ){
		 selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		}
		
		if ( selection instanceof IStructuredSelection ){

			final Display display = PlatformUI.getWorkbench().getDisplay();
			final Shell shell = display.getActiveShell();
            
           


			IStructuredSelection ss = (IStructuredSelection) selection;
			IFile selectedElement = (IFile) ss.getFirstElement();
			File selectedFile = selectedElement.getLocation().toFile();
			try {
				//Open the bct console for debug info
				//BctConsoleView bctConsoleView = (BctConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
				
				//Read the monitoring configuration
				final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);

				StaticallyIdentifyUsageAnomaliesResult result = new StaticallyIdentifyUsageAnomaliesResult();
				StaticallyIdentifyUsageAnomaliesWizard wizard = new StaticallyIdentifyUsageAnomaliesWizard(result);
				
				
				
				WizardDialog dialog = new WizardDialog(shell, wizard );
				
				if ( dialog.open() == SWT.CANCEL ){
			    	return;
			    }
				
			    if ( result.getCanceled() ){
			    	return;
			    }
			    
			    
			    identifyAnomalies(display, shell, mc, result);


			//TODO: Add message dialogs in case of errors
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	}

	public void identifyAnomalies(final Display display, final Shell shell,
			final MonitoringConfiguration mc,
			StaticallyIdentifyUsageAnomaliesResult result) {
		try {
			setTracesToExclude(mc,result);
		} catch (ConfigurationFilesManagerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		final IdentifyAnomaliesRunnableWithProgress runnable = new IdentifyAnomaliesRunnableWithProgress(shell, display, mc);
		
		//Run it as a Job, we do not want to block users operations 
		Job job = new Job("BCT identify anomalies: "+mc.getConfigurationName()){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					runnable.run(monitor);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					return Status.CANCEL_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};
		
		
		job.schedule();
	}

	public static void setTracesToExclude(MonitoringConfiguration mc, StaticallyIdentifyUsageAnomaliesResult result) throws ConfigurationFilesManagerException {
		// TODO Auto-generated method stub
		//Folowing lines are necessary to exclude failing executions
		//
		//Exclude failing executions
		//
		File logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);
		
		setTracesToExclude(mc, result, logFile);
		
	}
	
	public static void setTracesToExclude(MonitoringConfiguration mc, StaticallyIdentifyUsageAnomaliesResult result, File logFile ) throws ConfigurationFilesManagerException {
		
		if ( ! logFile.exists() ){
			AdditionalInferenceOptions opts = mc.getAdditionalInferenceOptions();
			if ( opts == null ){ 
				opts = new AdditionalInferenceOptions();
				mc.setAdditionalInferenceOptions( opts );
			}

			opts.setTestCasesToIgnore( new HashSet<String>() );
			opts.setActionsToIgnore( new HashSet<String>() );
			opts.setProcessesToIgnore( new HashSet<String>() );

			return;
		}
		
		
		ArrayList<IFile> filesToOpen = new ArrayList<IFile>();
		
		IFile fileToOpen = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(logFile.getAbsolutePath()));
		filesToOpen.add(fileToOpen);
		
		File violationsAnalysisFolder = ConfigurationFilesManager.getInferenceLogAnalysisFolder(mc);
		//IFile fileContainer = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(violationsAnalysisFolder.getAbsolutePath()));
		
		File resultingViolationsLogAnalysisFile = new File ( violationsAnalysisFolder, "BctStaticUsageCheckingLogAnalisys-"+System.currentTimeMillis()+".bctla" );
		IFile resultingViolationsLogAnalysisFileResource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(resultingViolationsLogAnalysisFile.getAbsolutePath()));
		
		
		File anomalyGraphsFolder = new File ( violationsAnalysisFolder, "BctStaticUsageCheckingLogAnalisys-"+System.currentTimeMillis() );
		IFile anomalyGraphsFolderResource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(anomalyGraphsFolder.getAbsolutePath()));
		
		
		BctViolationsAnalysisConfiguration config = BctViolationsAnalysisConfigurationUtil.createAndStoreBctViolationsAnalysisConfiguration(resultingViolationsLogAnalysisFileResource, true, filesToOpen, anomalyGraphsFolderResource);
		
		AdditionalInferenceOptions opts = mc.getAdditionalInferenceOptions();
		if ( opts == null ){ 
			opts = new AdditionalInferenceOptions();
			mc.setAdditionalInferenceOptions( opts );
		}
		
		boolean invert;
		if ( result.getSkipPassingTests() ){
			invert=true;
			opts.setTestCasesToIgnore( config.getFailingTests() );
		} else {
			opts.setTestCasesToIgnore( new HashSet<String>() );
		}
		
		
		if ( result.getSkipPassingActions() ){
			invert=true;
			opts.setActionsToIgnore( config.getFailingActions() );
		} else {
			opts.setActionsToIgnore( new HashSet<String>() );
		}
		
		
		
		if ( result.getSkipPassingProcesses() ){
			invert=true;
			opts.setProcessesToIgnore( config.getFailingProcesses() );
		} else {
			opts.setProcessesToIgnore( new HashSet<String>() );
		}
		
		
		opts.setInvertFiltering( true );
		//
		//
		//
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}


}
