package it.unimib.disco.lta.bct.bctjavaeclipse.core.operations;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.ProcessesRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import cpp.gdb.EnvUtil;

import tools.InvariantGenerator;
import tools.gdbTraceParser.GdbTraceParser;
import tools.violationsAnalyzer.BctViolationsLogData;
import tools.violationsAnalyzer.CBEBctViolationsLogLoader;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.FileUtil;
import util.JavaRunner;

public class AnomalyDetection {

	public static void identifyAnomalies(MonitoringConfiguration mc ) throws IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException{
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.JavaMonitoring ){
			ConfigurationFilesManager.updateConfigurationFiles(mc);
			ProcessesRunner.runProcess(mc, "BCT "+AnomalyDetection.class.getCanonicalName(), check.AnomaliesDetector.class.getName());
		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config || 
				mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config 
				|| mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config ){
			
			if ( mc.getFlattenerOptions() == null ){
				FlattenerOptions opt = new FlattenerOptions(false,3,"all",new ArrayList<String>());
				mc.setFlattenerOptions(opt);
			}
			
			if ( mc.getActionsMonitoringOptions() == null ){
				ActionsMonitoringOptions mo = new ActionsMonitoringOptions();
				mc.setActionsMonitoringOptions(mo);
			}
			
			if ( mc.getTestCasesMonitoringOptions() == null ){
				TestCasesMonitoringOptions tcMo = new TestCasesMonitoringOptions();
				tcMo.setTestCasesRegistryOptions(new TestCasesMemoryRegistryOptions());
				mc.setTestCasesMonitoringOptions(tcMo);
			}
			
			ConfigurationFilesManager.updateConfigurationFiles(mc);
			
			for ( File trace : FileUtil.getDirContents(ConfigurationFilesManager.getTracesToVerifyFolder(mc) ) ){
				checkTrace(mc, trace);
			}
			
//			util.ProcessRunner.run(cmd, System.out, System.err, 0);
		}
	}

	public static void checkTrace(MonitoringConfiguration mc, 
			File trace) throws DefaultOptionsManagerException,
			ConfigurationFilesManagerException, IOException {
		
		File configHome = ConfigurationFilesManager.getBctHomeDir(mc);
		
		ArrayList<String> vmCmds = new ArrayList<String>();
		vmCmds.add("-Dbct.home="+configHome.getAbsolutePath());
		vmCmds.add(EnvUtil.getMaxAvailableHeap());
		
		
//		String dd = System.getProperty("bct.checkWithDaikon");
//		if ( dd != null ){
//			if ( Boolean.parseBoolean(dd) ){
//				vmCmds.add("-Dbct.checkWithDaikon=true");
//			}
//		}
		Set<Object> keySet = System.getProperties().keySet();
		for ( Object key : keySet){
			if ( key == null ){
				continue;
			}
			if ( key.toString().startsWith("bct.") ){
				Object value = System.getProperty(key.toString());
				if ( value != null ){
					vmCmds.add("-D"+key+"="+value.toString());
				}
			}
		}
		
		if ( ! keySet.contains(GdbTraceParser.BCT_CHECK_WITH_DAIKON) ){
			vmCmds.add(ModelInference.createPropertyParameter(GdbTraceParser.BCT_CHECK_WITH_DAIKON, "false"));
		}
		
		if ( ! keySet.contains(InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS) ){
			vmCmds.add(ModelInference.createPropertyParameter(InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS, "false"));
		}
		
		ArrayList<String> classPath = new ArrayList<String>();
		classPath.add(DefaultOptionsManager.getBctJarFile().getAbsolutePath());
		
		
		
		
		
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add("-check");
		cmd.add("-skipConfigCreation");
		
		
		
		CConfiguration configuration = null;
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			configuration = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
		} else if ( mc.getConfigurationType() ==  MonitoringConfiguration.ConfigurationTypes.C_Regression_Config || mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config ){
			configuration = (CConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		}
		
		//We always use the original software.
		//For now the executable is used to get numeric ids instead of names for struct members
		cmd.add(GdbTraceParser.Options.ANALIZED_EXECUTABLE);
		cmd.add(configuration.getOriginalSwExecutable());
		
		if ( configuration.isDll() ){
			cmd.add(GdbTraceParser.Options.DLL);
		}
		
		if ( configuration.isSimulateClosingOfLastNotTerminatedFunctions() ){
			cmd.add(GdbTraceParser.Options.SIMULATE_CLOSING_OF_LAST_NOT_TERMINATED_FUNCTIONS);
		}
		
		if ( configuration.isFilterAllNonTerminatingFunctions() ){
			cmd.add(GdbTraceParser.Options.FILTER_ALL_NON_TERMINATING_FUNCTIONS);
		}
		
		if ( configuration.isFilterNotTerminatedFunctionCalls() ){
			cmd.add(GdbTraceParser.Options.FILTER_NOT_TERMINATED_FUNCTIONS);
		}
		
		if ( configuration.isUseDemangledNames() ){
			cmd.add(GdbTraceParser.Options.USE_DEMANGLED_NAMES );
		}
		
		if ( configuration instanceof CRegressionConfiguration ){
			CRegressionConfiguration regressionConfig = (CRegressionConfiguration) configuration;
			if ( regressionConfig.isMonitorAddedAndDeletedFunctions() ){
				cmd.add(GdbTraceParser.Options.HIDE_ADDED_MODIFIED );
				if ( regressionConfig.isHideAddedAndDeletedFunctions() ){
					cmd.add(GdbTraceParser.Options.COMPONENTS_DEFINITION_FILE );
					cmd.add( ConfigurationFilesManager.getComponentsDefinitionFile( mc ).getAbsolutePath() );
				}
			}
		}
		
		if ( ModelInference.doProcessPointers() ){
			cmd.add(GdbTraceParser.Options.PROCESS_POINTERS);
		}
		
		String pp = System.getProperty("bct.programPointsToInclude");
		if ( pp != null ){
			cmd.add(GdbTraceParser.Options.PP_TO_INCLUDE);
			cmd.add( pp );
		}
		
		if ( configuration.isExcludeUnusedVariables() ){
			cmd.add(GdbTraceParser.Options.FILTER_USED_VARIABLES );
			
			
			
			cmd.add(GdbTraceParser.Options.MODIFIED_SOFTWARE_FOLDER);
			
			if ( configuration instanceof CRegressionConfiguration ){
				cmd.add( ((CRegressionConfiguration) configuration).getModifiedSwSourcesFolder() );
			} else {
				cmd.add( configuration.getOriginalSwSourcesFolder() );
			}
			
			
			cmd.add(GdbTraceParser.Options.MODIFIED_SOFTWARE_FUNCTIONS);
			
			if ( configuration instanceof CRegressionConfiguration ){
				cmd.add( ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc).getAbsolutePath() );
			} else {
				cmd.add( ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc).getAbsolutePath() );
			}
			
//				cmd.add(GdbTraceParser.Options.MODIFIED_SOFTWARE_OBJDUMP);
//				
//				if ( configuration instanceof CRegressionConfiguration ){
//					cmd.add( ConfigurationFilesManager.getModifiedSoftwareDumpFile(mc).getAbsolutePath() );
//				} else {
//					cmd.add( ConfigurationFilesManager.getOriginalSoftwareDumpFile(mc).getAbsolutePath() );
//				}
			
			
		}
		
		if ( configuration.isExcludeLineInfoFromFSA() ){
			cmd.add(GdbTraceParser.Options.EXCLUDE_GENERIC_PROGRAM_POINTS_FROM_FSA );
		}
		
		cmd.add("-resultsDir");
		cmd.add(configHome.getAbsolutePath());
				
		
		
		cmd.add(trace.getAbsolutePath());
		
		System.out.println(cmd);
		
		String[] cmdarray = new String[cmd.size()];
		
		cmdarray = cmd.toArray(cmdarray);
		
//		StringBuffer outputBuffer = new StringBuffer();
		
		
//		StringBuffer errorBuffer = new StringBuffer();
		
		JavaRunner.runMainInClass(tools.gdbTraceParser.GdbTraceParser.class, vmCmds, cmd, 0, classPath, false, null, null);
		
//		System.out.println(outputBuffer.toString());
		
//		System.err.println(errorBuffer.toString());
	}
	
	public static BctViolationsLogData identifyAnomaliesAndLoad(MonitoringConfiguration mc) throws IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, CBEBctViolationsLogLoaderException{
		identifyAnomalies(mc);
		
		BctViolationsLogData logData = loadAnomalies(mc);
		
		return logData;
	}

	public static BctViolationsLogData loadAnomalies(MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException,
			CBEBctViolationsLogLoaderException {
		CBEBctViolationsLogLoader loader = new CBEBctViolationsLogLoader();
		File logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);
		
		Collection<File> cbeLogs = new ArrayList<File>();
		cbeLogs.add(logFile);
		
		BctViolationsLogData logData = loader.load(cbeLogs );
		return logData;
	}
	
	
//	public runAnalysis( List<IFile> files){
//		IFile anomalyGraphsFolder;
//		final IFile violationsLogAnalysisFile;
//		final BctViolationsAnalysisConfiguration config = BctViolationsAnalysisConfigurationUtil.createAndStoreBctViolationsAnalysisConfiguration(violationsLogAnalysisFile, copyLogs,files,anomalyGraphsFolder);
//		
//	}
}
