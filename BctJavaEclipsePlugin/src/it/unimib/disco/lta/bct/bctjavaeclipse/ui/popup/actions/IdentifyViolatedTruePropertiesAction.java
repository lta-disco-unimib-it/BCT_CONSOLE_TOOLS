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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.AdditionalInferenceOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfigurationUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.SourceLineMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.FindShell;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.InferModelWizard;
import it.unimib.disco.lta.bct.eclipse.core.BctCoreActivator;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.internal.core.SourceMapper;
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

import tools.fshellExporter.FShellExecutor;
import tools.fshellExporter.FShellExecutor.FShellResult;
import tools.fshellExporter.FShellModelsExporter;
import util.FileUtil;
import util.JavaRunner;

import conf.InvariantGeneratorSettings;
import cpp.gdb.SourceLinesMapper;

public class IdentifyViolatedTruePropertiesAction  implements IObjectActionDelegate  {

	public IdentifyViolatedTruePropertiesAction() {
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
			
				final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);


				
				//Run it as a Job, we do not want to block users operations 
				Job job = new Job("FShellValidation : "+mc.getConfigurationName()){
					@Override
					protected IStatus run(IProgressMonitor monitor) {
							
							try {
								return runIdentifyViolatedTrueProperties(mc);
							} catch (ConfigurationFilesManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DefaultOptionsManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return Status.OK_STATUS;
					}

					
				};
				
				
				job.schedule();


			//TODO: Add message dialogs in case of errors
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	}

	public static IStatus runIdentifyViolatedTrueProperties(
			final MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException,
			DefaultOptionsManagerException, IOException {
		File bctHomeFile = ConfigurationFilesManager.getBctHomeDir(mc);
		
		
		
		
		File validatedModelsDir = ConfigurationFilesManager.getValidatedModelsDir( mc ) ;
		File fshellScript;
		
		
		
		FShellExecutor executor = new FShellExecutor();
		
		CConfiguration config = null;
		File workingFolder;
		
		
		FShellModelsExporter fsm = new FShellModelsExporter(bctHomeFile);
		
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			config = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
			workingFolder =  new File ( config.getOriginalSwSourcesFolder() );
			fshellScript = FShellModelsExporter.getValidModelsFile(validatedModelsDir);
		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			config = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			CRegressionConfiguration rc = (CRegressionConfiguration) config;
			workingFolder =  new File ( rc.getModifiedSwSourcesFolder() );
			
			SourceLinesMapper sourceMapper = SourceLineMapperFactory.createSourceLinesMapper(mc);
			fshellScript = fsm.exportValidModelsForUpgradedVersion(validatedModelsDir, sourceMapper );
			
			
		} else {
			return Status.OK_STATUS;
		}
		
		
		
		
		ArrayList<File> filesToCheck = FileUtil.getContentsRecursively(  workingFolder , new FileFilter() {
			
			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				return arg0.getName().endsWith(".C") || arg0.getName().endsWith(".c") || arg0.getName().endsWith(".CPP") || arg0.getName().endsWith(".cpp");
			}
		});
		
		ArrayList<File> files = new ArrayList<File>();
		for ( File file : filesToCheck ){
			files.add( new File(file.getAbsolutePath().substring(workingFolder.getAbsolutePath().length()+1)) );
		}
		
		
		FShellResult fresult = executor.execute(fshellScript, workingFolder, files);
		
		
		fsm.exportInvalidModelsV1(fresult, validatedModelsDir );
		
		return Status.OK_STATUS;
	}

	

	public void selectionChanged(IAction action, ISelection selection) {
	}


}
