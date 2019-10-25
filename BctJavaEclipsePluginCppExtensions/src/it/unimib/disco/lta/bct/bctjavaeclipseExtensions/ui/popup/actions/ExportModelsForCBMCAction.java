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
package it.unimib.disco.lta.bct.bctjavaeclipseExtensions.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.SourceLineMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.logAnalysis.VoidReturnViolationsFilterAction;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.modelsExportation.CModelFilter;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.FunctionsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherException;

import org.eclipse.core.resources.IFile;
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

import conf.EnvironmentalSetter;

import tools.fshellExporter.CBMCModelsExporter;
import tools.fshellExporter.ModelFilter;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.SourceLinesMapper;

public class ExportModelsForCBMCAction  implements IObjectActionDelegate  {

	public ExportModelsForCBMCAction() {
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
//		File bctHomeFile = ConfigurationFilesManager.getBctHomeDir(mc);
		
		
		addReturnTypes(mc);
		
		File validatedModelsDir = ConfigurationFilesManager.getValidatedModelsDir( mc ) ;
		
		
		validatedModelsDir.mkdirs();
		
		
//		File workingFolder;
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config || mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
//			CRegressionConfiguration config = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
//			CRegressionConfiguration rc = (CRegressionConfiguration) config;
//			workingFolder =  new File ( rc.getModifiedSwSourcesFolder() );
			ModelFilter modelFilter = new CModelFilter(mc);
			SourceLinesMapper sourceMapper = SourceLineMapperFactory.createSourceLinesMapper(mc);
			
			EnvironmentalSetter.setBctHome(ConfigurationFilesManager.getBctHomeDir(mc).getAbsolutePath());
			ModelsFetcher mf = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory().getModelsFetcher();
			try {
				CBMCModelsExporter exporter = new CBMCModelsExporter( sourceMapper, mf );
				exporter.addAdditionalVariablesFilter(modelFilter);
				exporter.setExportEntryPoints(true);
				exporter.setExportLines(false);
				exporter.exportModels(new File( validatedModelsDir, "CBMC_assertions_entryPoints_v0.txt"), new File( validatedModelsDir, "CBMC_assertions_entryPoints_v1.txt"));
			} catch (ModelsFetcherException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( Throwable t ){
				t.printStackTrace();
			}
			
			
			try {
				CBMCModelsExporter exporter = new CBMCModelsExporter( sourceMapper, mf );
				exporter.addAdditionalVariablesFilter(modelFilter);
				exporter.setExportEntryPoints(true);
				exporter.setExportExitPoints(true);
				exporter.setExportLines(false);
				exporter.exportModels(new File( validatedModelsDir, "CBMC_assertions_entryExitPoints_v0.txt"), new File( validatedModelsDir, "CBMC_assertions_entryExitPoints_v1.txt"));
			} catch (ModelsFetcherException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( Throwable t ){
				t.printStackTrace();
			}
			
			try {
				CBMCModelsExporter exporter = new CBMCModelsExporter( sourceMapper, mf );
				exporter.addAdditionalVariablesFilter(modelFilter);
				exporter.setExportEntryPoints(false);
				exporter.setExportLines(true);
				exporter.exportModels(new File( validatedModelsDir, "CBMC_assertions_lines_v0.txt"), new File( validatedModelsDir, "CBMC_assertions_lines_v1.txt"));
			} catch (ModelsFetcherException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch ( Throwable t ){
				t.printStackTrace();
			}
			
			
			return Status.OK_STATUS;
		} else {
			return Status.OK_STATUS;
		}
		
		
		
	}

	

	public static void addReturnTypes(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File monitoredFunctionsFile = ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc);
		
		addReturnTypes(mc,monitoredFunctionsFile);
		
		monitoredFunctionsFile = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc);
		if ( monitoredFunctionsFile.exists() ){
			addReturnTypes(mc,monitoredFunctionsFile);
		}
	}

	private static void addReturnTypes(MonitoringConfiguration mc,
			File monitoredFunctionsFile) {
		try {
			Map<String, FunctionMonitoringData> functionsData = FunctionMonitoringDataSerializer.load(monitoredFunctionsFile);
			for ( FunctionMonitoringData fd : functionsData.values() ){
				if ( fd.isImplementedWithinProject() ){
					String returnType = FunctionsUtil.extractMethodReturnType(fd);
					fd.setReturnType(returnType);
				}
			}
			
			FunctionMonitoringDataSerializer.store(functionsData.values(), monitoredFunctionsFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}


}
