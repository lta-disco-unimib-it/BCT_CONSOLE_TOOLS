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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.util.ActionUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.InferModelWizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import org.eclipse.core.internal.jobs.JobManager;
import org.eclipse.core.internal.jobs.JobQueue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
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

import conf.InvariantGeneratorSettings;

public class InferModelsAction  implements IObjectActionDelegate  {

	public InferModelsAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		run(action, null);
	}
	
	public void run(IAction action, IJobChangeListener jobListener) {
		try {
			File selectedFile = ActionUtil.getSelectedMonitoringConfigurationFile( action );
			final MonitoringConfiguration mc = ActionUtil.getSelectedMonitoringConfiguration( action );
		
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = display.getActiveShell();
		
				//Open the bct console for debug info
				//BctConsoleView bctConsoleView = (BctConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
				
				//Read the monitoring configuration
				

				//Set the invariant generation options into the monitoring configuration
				 
				//the code below is incomplete, the configuration options must also be set by the user from the GUI
				
				//Retrieve the current workspace configuration options
				BctDefaultOptions workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();
				
				Properties invariantGeneratorOptions = mc.getInvariantGeneratorOptions();
				
				if ( invariantGeneratorOptions == null ){ 
					invariantGeneratorOptions = workspaceOptions.getInvariantGeneratorOptions(); 
				}
				
				
				InferModelWizardResult result = new InferModelWizardResult();
				InferModelWizard wizardDialogContent = new InferModelWizard(result);
				wizardDialogContent.loadInvariantGeneratorOptions(invariantGeneratorOptions);
				
//				wizardDialogContent.loadKBehaviorOptions( workspaceOptions.getKbehaviorInferenceEngineOptions() );
//				wizardDialogContent.loadKTailOptions( workspaceOptions.getKtailEnigineOptions() );
//				wizardDialogContent.loadReissOptions( workspaceOptions.getReissEnigineOptions() );
//				wizardDialogContent.loadKInclusionOptions( workspaceOptions.getKinclusionEnigineOptions() );
				
				
				Properties fsaEngineOptions = mc.getFsaEngineOptions();
				
				if ( fsaEngineOptions == null & mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
					//HACK to improve C/C++ monitoringwith default
					fsaEngineOptions = new Properties();
					fsaEngineOptions.setProperty("maxTrustLen","7" );
					fsaEngineOptions.setProperty("minTrustLen","7" );
					fsaEngineOptions.setProperty("cutOffSearch", "true");
				}
				
				if ( fsaEngineOptions != null ){
					wizardDialogContent.loadSelectedOptions(fsaEngineOptions);
				}
				
				WizardDialog dialog = new WizardDialog(shell, wizardDialogContent );
				
				  
			    if ( dialog.open() == SWT.CANCEL ){
			    	return;
			    }
				
			    if ( result.getCanceled() ){
			    	return;
			    }
			    
			    
			    
				//get the options as set by the user
			    invariantGeneratorOptions = result.getInvariantGeneratorOptions();
			    
			    invariantGeneratorOptions.setProperty( InvariantGeneratorSettings.Options.daikonConfidenceLevel, "0" );
			    
			    Properties inferenceEngineOptions = result.getInferenceEngineOptions();
				//After having modified the options set them to the mc 
				mc.setInvariantGeneratorOptions(invariantGeneratorOptions);
				mc.setFsaEngineOptions(inferenceEngineOptions);
				
				if ( ! result.isKeepExistingModels() ){

					//Delete existing models
					IRunnableWithProgress r = new DeleteModelsRunnableWithProgress(mc);
					ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);

					try {
						progressMonitorDialog.run(true, true, r);
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				setTracesToExclude(mc, result);
				

				
				//store tests/actions to ignore within updateCOnfigFIle 
				
				
				
				
				//save the monitoring configuration
				//TODO: it should be better to have a centralize save method, like, MonitoringCOnfiguration.doSave()
				MonitoringConfigurationSerializer.serialize(selectedFile, mc);
				
				//
				//Run the bct inference engine
				//
				
				final InferModelsRunnableWithProgress runnable = new InferModelsRunnableWithProgress(shell, display, mc);
				
				//Run it as a Job, we do not want to block users operations 
				Job job = new Job("BCT Model Inference : "+mc.getConfigurationName()){
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
				
				if ( jobListener != null ){
					job.addJobChangeListener(jobListener);
				}
				
				job.schedule();
				

			//TODO: Add message dialogs in case of errors
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DefaultOptionsManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConfigurationFilesManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}



	private void setTracesToExclude(MonitoringConfiguration mc, InferModelWizardResult result) throws ConfigurationFilesManagerException {
		
		
		boolean skipFailingTests = result.getSkipFailingTests();
		boolean skipFailingActions = result.getSkipFailingActions();
		boolean skipFailingProcesses = result.getSkipFailingProcesses();
		
		ModelInference.setupInferenceFilters(mc, skipFailingTests, skipFailingActions, skipFailingProcesses);
		//
	}

	

	public void selectionChanged(IAction action, ISelection selection) {
	}


}
