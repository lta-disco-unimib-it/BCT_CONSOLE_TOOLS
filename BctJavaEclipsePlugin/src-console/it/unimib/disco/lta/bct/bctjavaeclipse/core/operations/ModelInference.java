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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.operations;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import tools.InvariantGenerator;
import tools.gdbTraceParser.BctGdbThreadTraceListener;
import tools.gdbTraceParser.GdbThreadTraceParser;
import tools.gdbTraceParser.GdbTraceParser;
import util.FileUtil;
import util.JavaRunner;
import conf.EnvironmentalSetter;

public class ModelInference {

	///PROPERTIES-DESCRIPTION: Options that control model inference with BCT/RADAR/VART

	///indicate whether or not to record information about pointer values
	private static final String BCT_PROCESS_POINTERS = "bct.processPointers";
	
	///list of regular expressions separated by ';' that indicates the program points to include when analyzing a trace. This option allows to analyze only a subset of the program points recorded in a trace.
	private static final String BCT_PROGRAM_POINTS_TO_INCLUDE = "bct.programPointsToInclude";

	private static int maxExecutionTime;

	public static void setupInferenceFilters(MonitoringConfiguration mc,
			boolean skipFailingTests, boolean skipFailingActions,
			boolean skipFailingProcesses)
			throws ConfigurationFilesManagerException {
		File logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);
		
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
		
		File resultingViolationsLogAnalysisFile = new File ( violationsAnalysisFolder, "BctInferenceLogAnalisys-"+System.currentTimeMillis()+".bctla" );
		IFile resultingViolationsLogAnalysisFileResource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(resultingViolationsLogAnalysisFile.getAbsolutePath()));
		
		
		File anomalyGraphsFolder = new File ( violationsAnalysisFolder, "BctInferenceLogAnalisys-"+System.currentTimeMillis() );
		IFile anomalyGraphsFolderResource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(anomalyGraphsFolder.getAbsolutePath()));
		
		
		BctViolationsAnalysisConfiguration config = BctViolationsAnalysisConfigurationUtil.createAndStoreBctViolationsAnalysisConfiguration(resultingViolationsLogAnalysisFileResource, true, filesToOpen, anomalyGraphsFolderResource);
		
		AdditionalInferenceOptions opts = mc.getAdditionalInferenceOptions();
		if ( opts == null ){ 
			opts = new AdditionalInferenceOptions();
			mc.setAdditionalInferenceOptions( opts );
		}
		
		if ( skipFailingTests ){
			opts.setTestCasesToIgnore( config.getFailingTests() );
		} else {
			opts.setTestCasesToIgnore( new HashSet<String>() );
		}
		
		

		if ( skipFailingActions ){
			opts.setActionsToIgnore( config.getFailingActions() );
		} else {
			opts.setActionsToIgnore( new HashSet<String>() );
		}
		
		
		
		
		if ( skipFailingProcesses ){
			opts.setProcessesToIgnore( config.getFailingProcesses() );
		} else {
			opts.setProcessesToIgnore( new HashSet<String>() );
		}
		
		//
		//
	}
	
	public static void inferModels(final MonitoringConfiguration mc, final IProgressMonitor monitor,
			final OutputStream os, final OutputStream eos)
			throws ConfigurationFilesManagerException,
			DefaultOptionsManagerException, IOException, InterruptedException {
		File configHome = ConfigurationFilesManager.getBctHomeDir(mc);



		ConfigurationFilesManager.updateConfigurationFiles(mc);
		System.setProperty("bct.home", configHome.getAbsolutePath());

		//setProperty does not work use this
		EnvironmentalSetter.setBctHome(configHome.getAbsolutePath());
		
//		String cmdarray[] = null;

		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.JavaMonitoring ){
//			cmdarray = new String[] {
//					"java",
//					"-Dbct.home="+configHome.getAbsolutePath(),
//					"-cp",
//					DefaultOptionsManager.getBctJarFile().getAbsolutePath(),
//					tools.InvariantGenerator.class.getName(),
//					"-default"
//			};
			
			ArrayList<String> vmArgs = new ArrayList();
			vmArgs.add("-Dbct.home="+configHome.getAbsolutePath());
			
			ArrayList<String> args = new ArrayList();
			args.add("-default");
			
			int exitValue = JavaRunner.runMainInClass(tools.InvariantGenerator.class, vmArgs, args, maxExecutionTime, null, true, null, null, null);
			System.out.println("Process terminated "+exitValue);
			
		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config
				|| mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ||
				mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ) {
			ArrayList<String> vmArgs = new ArrayList<String>();


			CConfiguration additionalConfig;
			
			if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ||
					mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config ){
				additionalConfig = (CRegressionConfiguration) mc.getAdditionalConfiguration( CRegressionConfiguration.class );
			} else {
				additionalConfig = (CConfiguration) mc.getAdditionalConfiguration( CConfiguration.class );
			}


			vmArgs.add("-Dbct.home="+configHome.getAbsolutePath());
			
			
			if ( JavaRunner.addCurrentLogging ){
				String configPropertyName = "java.util.logging.config.file";
				String logginingPath = System.getProperty(configPropertyName);
				if ( logginingPath != null ){
					vmArgs.add("-D"+configPropertyName+"="+logginingPath);
				}
			}
			
			vmArgs.add(createPropertyParameter(GdbTraceParser.BCT_NO_FSA_MODELS, "false"));
			vmArgs.add(createPropertyParameter(InvariantGenerator.BCT_SKIP_ARRAYS, "false"));
			vmArgs.add(createPropertyParameter(GdbThreadTraceParser.BCT_AVOID_FIXING_LINE_NUMBERS, "false"));
			vmArgs.add(createPropertyParameter(InvariantGenerator.BCT_INFERENCE_EXPAND_EQUIVALENCES, "true"));
			vmArgs.add(createPropertyParameter(InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS, "false"));
			vmArgs.add(createPropertyParameter(GdbThreadTraceParser.BCT_ALWAYS_TRACE_NOT_NULL, "false"));
			vmArgs.add(createPropertyParameter(GdbThreadTraceParser.BCT_PROCESSING_HEX_TO_INT, "false"));
			
//			cmd.add("-cp");
//			cmd.add(DefaultOptionsManager.getBctJarFile().getAbsolutePath());
			
			ArrayList<String> args = new ArrayList<String>();
			
			
			args.add(GdbTraceParser.Options.SKIP_CLEANING);
			args.add(GdbTraceParser.Options.SKIP_CONFIG_CREATION);
			
			boolean processPointers = doProcessPointers();
			if ( processPointers ){
				args.add(GdbTraceParser.Options.PROCESS_POINTERS);
			}
			
			args.add(GdbTraceParser.Options.ANALIZED_EXECUTABLE);
			args.add(additionalConfig.getOriginalSwExecutable());

			
			String pp = System.getProperty(BCT_PROGRAM_POINTS_TO_INCLUDE);
			if ( pp != null ){
				args.add(GdbTraceParser.Options.PP_TO_INCLUDE);
				args.add( pp );
			}
			
			if ( additionalConfig.isDeriveFunctionInvariants() ){
				args.add(GdbTraceParser.Options.GENERATE_FUNCTION_INVARIANTS);
			}
			
			if ( additionalConfig.isUseDemangledNames() ){
				args.add(GdbTraceParser.Options.USE_DEMANGLED_NAMES);
			}
			
			if ( additionalConfig.isDeriveLineInvariants() ){
				args.add(GdbTraceParser.Options.GENERATE_SINGLE_LINE_INVARIANTS);
			}
			
			
			if ( additionalConfig.isFilterAllNonTerminatingFunctions() ){
				args.add(GdbTraceParser.Options.FILTER_ALL_NON_TERMINATING_FUNCTIONS);
			}

			if ( additionalConfig.isFilterNotTerminatedFunctionCalls() ){
				args.add(GdbTraceParser.Options.FILTER_NOT_TERMINATED_FUNCTIONS);
			}

			if ( additionalConfig.isSimulateClosingOfLastNotTerminatedFunctions() ){
				args.add(GdbTraceParser.Options.SIMULATE_CLOSING_OF_LAST_NOT_TERMINATED_FUNCTIONS);
			}

			if ( additionalConfig.isExcludeLineInfoFromFSA() ){
				args.add(GdbTraceParser.Options.EXCLUDE_GENERIC_PROGRAM_POINTS_FROM_FSA );
			}
			
			
			
			if ( additionalConfig instanceof CRegressionConfiguration ){
				CRegressionConfiguration regressionConfig  = (CRegressionConfiguration) additionalConfig;
				if ( regressionConfig.isUseUpdatedReferencesForModels() ){
					args.add(GdbTraceParser.Options.RENAME_FOR_MODIFIED );
					
					args.add(GdbTraceParser.Options.ORIGINAL_SOFTWARE_FOLDER );
					args.add(regressionConfig.getOriginalSwSourcesFolder() );
					

					
					
					args.add(GdbTraceParser.Options.MODIFIED_SOFTWARE_FOLDER );
					args.add(regressionConfig.getModifiedSwSourcesFolder() );

					args.add(GdbTraceParser.Options.ORIGINAL_SOFTWARE_FUNCTIONS);
					args.add( ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc).getAbsolutePath() );
					
					args.add(GdbTraceParser.Options.MODIFIED_SOFTWARE_FUNCTIONS);
					args.add( ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc).getAbsolutePath() );
					
//					cmd.add(GdbTraceParser.Options.ORIGINAL_SOFTWARE_OBJDUMP );
//					cmd.add( ConfigurationFilesManager.getOriginalSoftwareDumpFile(mc).getAbsolutePath() );
					
//					cmd.add(GdbTraceParser.Options.MODIFIED_SOFTWARE_OBJDUMP );
//					cmd.add( ConfigurationFilesManager.getModifiedSoftwareDumpFile(mc).getAbsolutePath() );
				}
				
				
				if ( regressionConfig.isMonitorAddedAndDeletedFunctions() ){
					args.add(GdbTraceParser.Options.HIDE_ADDED_MODIFIED );
					if ( regressionConfig.isHideAddedAndDeletedFunctions() ){
						args.add(GdbTraceParser.Options.COMPONENTS_DEFINITION_FILE );
						args.add( ConfigurationFilesManager.getComponentsDefinitionFile( mc ).getAbsolutePath() );
					}
				}
			}
			
			args.add(GdbTraceParser.Options.RESULTS_DIR);
			args.add(configHome.getAbsolutePath());

			//2016-02-16 enough to indicate the trace folder
//			for ( File trace : FileUtil.getDirContents(ConfigurationFilesManager.getValidTracesFolder(mc) ) ){
//				cmd.add(trace.getAbsolutePath());
//			}
			args.add(ConfigurationFilesManager.getValidTracesFolder(mc).getAbsolutePath());

			
			List<String> classPath = new ArrayList<String>();
			classPath.add( DefaultOptionsManager.getBctJarFile().getAbsolutePath() );
			
			int exitValue = JavaRunner.runMainInClass(tools.gdbTraceParser.GdbTraceParser.class, vmArgs, args, maxExecutionTime, classPath, true, null, null, null);
			System.out.println("Process terminated "+exitValue);

		}

		
		
//		final Process process = Runtime.getRuntime().exec(cmdarray);




//		final InputStream es =  process.getErrorStream();
//		final InputStream is = process.getInputStream();
//		final int bufSize = 256;
//		
//
//		Thread ir = new Thread(){
//			public void run(){
//				byte buf[] = new byte[bufSize];
//				int readed;
//				try {
//					while ( ( readed = is.read(buf, 0, bufSize) ) > 0 ){
//
//						//c.println(new String(buf,"utf-8"), 1);
//						try {
//							os.write(buf, 0, readed);
//							System.out.write(buf,0,readed);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//
//		Thread er = new Thread(){
//			public void run(){
//				byte buf[] = new byte[bufSize];
//				int readed;
//				try {
//					while ( ( readed = es.read(buf, 0, bufSize) ) > 0 ){
//						//c.println(new String(buf,"utf-8"), 1);
//						try {
//							eos.write(buf, 0, readed);
//							System.out.write(buf,0,readed);
//						} catch (IOException e) {
//
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//
//		ir.start();
//		er.start();



//		//Monitor for cancel operations
//		Thread monitorChecker = new Thread(){
//			public void run(){
//				while ( true ){
//					if ( monitor.isCanceled() ){
//						System.out.println("Process canceled");
//						process.destroy();
//						try {
//							eos.write("PROCESS TERMINATED".getBytes());
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						break;
//					}
//					try {
//						int exit = process.exitValue();
//						return;
//					} catch ( IllegalThreadStateException e ){
//						//process still running do nothing;
//					}
//					try {
//						sleep(3000);	
//						
//					} catch (InterruptedException e) {
//						e.printStackTrace();
////						return;
//					}
//				}
//
//			}
//		};
//
//		monitorChecker.start();

		
//		process.waitFor();
//		System.out.println("Process terminated "+process.exitValue());
		
//		synchronized (monitorChecker) {
//			monitorChecker.interrupt();	
//		}
		
	}

	public static boolean doProcessPointers() {
		boolean processPointers = false;
		String ppString = System.getProperty(BCT_PROCESS_POINTERS);
		if ( ppString != null ){
			processPointers = Boolean.valueOf(ppString);
		}
		return processPointers;
	}

	public static String createPropertyParameter(String property,
			String defaultValue) {
		String expandEquivalences = System.getProperty(property);
		if ( expandEquivalences == null ){
			System.out.println("Default value for "+property+" : "+defaultValue);
			expandEquivalences = defaultValue;
		}
		String result = "-D"+property+"="+expandEquivalences;
		return result;
	}
}
