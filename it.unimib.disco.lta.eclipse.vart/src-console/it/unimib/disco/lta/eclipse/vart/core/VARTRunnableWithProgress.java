package it.unimib.disco.lta.eclipse.vart.core;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConsoleHelper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.AnomalyDetection;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.CleaningUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.CorrespondingLinesFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ConsoleDisplayMgr;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.CDTStandaloneCFileAnalyzer;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import console.AnomaliesIdentifier;

import util.FileUtil;
import util.ProcessRunner;
import cpp.gdb.CSourceAnalyzerRegistry;
import cpp.gdb.EnvUtil;

public class VARTRunnableWithProgress implements IRunnableWithProgress {
	
	///PROPERTIES-DESCRIPTION: Options that control the identification of anomalies with RADAR/VART
	
	///if true executes only the identification of anomalies, i.e. stops after inferring models and identifying anomalous values (default false)
	public static final String BCT_SKIP_CBMC = "bct.skipModelChecking";
	
	///if true does not run automated monitoring based on Makefile (default: false)
	public static final String BCT_SKIP_MONITORING = "bct.skipMonitoring";

	public static boolean skipModelChecking(){
		return Boolean.getBoolean(BCT_SKIP_CBMC);
	}
	
	private MonitoringConfiguration mc;
	private Set<String> violatedAssertions;

	public VARTRunnableWithProgress(MonitoringConfiguration mc) {
		this.mc = mc;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Executing VART", 7);
		
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();
		CSourceAnalyzerRegistry.setCSourceAnalyzer(analyzer);
		
		CRegressionConfiguration regConfig = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		VARTRegressionConfiguration config = (VARTRegressionConfiguration) mc.getAdditionalConfiguration(VARTRegressionConfiguration.class);
		
		try {
			
			
			
			if ( config.isUseMakefileForMonitoring() && ( ! skipMonitoring() ) ){
				
				File dumpFile = ConfigurationFilesManager.getModifiedSoftwareDumpFile(mc);
				File execFile = regConfig.getModifiedSwExecutableFile();
				
				if ( dumpFile.lastModified() < execFile.lastModified() ){
					monitor.subTask("Updating monitoring scripts");
					ConfigurationFilesManager.updatRegressionComponentsToMonitor(mc);
					ConfigurationFilesManager.updateMonitoringScripts(mc);
				}
				monitor.worked(1);
				
				monitor.subTask("Monitoring base version");
		
				File trace = ConfigurationFilesManager.getGdbValidTrace(mc);
				if ( trace.exists() ){
					trace.delete();
					Thread.sleep(500);
				}
				
				File tracesDir = ConfigurationFilesManager.getValidTracesFolder(mc);
				FileUtil.deleteDirectoryContents(tracesDir);
				
				runMake( regConfig.getOriginalSwSourcesFolder(), config.getTestTarget(), ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfig(mc), tracesDir );

				monitor.worked(1);

				if ( ! config.isUsingRandomTests() ){
					monitor.subTask("Monitoring upgrade version");
					
					trace = ConfigurationFilesManager.getGdbTraceToVerify(mc);
					if ( trace.exists() ){
						trace.delete();
						Thread.sleep(500);
					}
					
					tracesDir = ConfigurationFilesManager.getTracesToVerifyFolder(mc);
					FileUtil.deleteDirectoryContents(tracesDir);
					
					runMake( regConfig.getModifiedSwSourcesFolder(), config.getTestTarget(), ConfigurationFilesManager.getModifiedSoftwareGdbMonitoringConfig(mc), tracesDir );
				}
				monitor.worked(1);
				
			} else {
				monitor.worked(2);
			}
		
			ConsoleDisplayMgr.getDefault().printInfo("VART - Starting VART Analysis\n");
			
			
			
			if ( ! ( config.isSkipInference() || AnomaliesIdentifier.checkSkipInference() ) ){
				monitor.subTask("Inferring models");
				CleaningUtil.deleteInferenceAndAnalysisData(ConfigurationFilesManager.getBctHomeDir(mc));
				ByteArrayOutputStream is = new ByteArrayOutputStream();
				ModelInference.inferModels(mc, new NullProgressMonitor(), is, is );
				//ModelInference.inferModels(mc, new NullProgressMonitor(), System.out, System.err);
				
				String s = is.toString( java.nio.charset.StandardCharsets.UTF_8.displayName() );
				System.out.println(s);
				
				monitor.worked(1);
			}

			
			if ( ! ( config.isSkipIntendedChangesIdentification() || AnomaliesIdentifier.checkSkipIntendedCHangesIdentification() ) ){
				monitor.subTask("Identifying intended changes");
				AnomalyDetection.identifyAnomalies(mc);
				monitor.worked(1);
			}
			
			if ( skipModelChecking() ){
				return;
			}
			
			monitor.subTask("Verifying Properties");
			
			EnvUtil.setBctJarPath( DefaultOptionsManager.getBctJarFile().getAbsolutePath() );
			ModelsFetcher mf = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory().getModelsFetcher();
			CBCMRegressionsDetector detector = new CBCMRegressionsDetector(config, mf);
			
			detector.setUnwind(config.getUnwind());
			if ( config.getCompilerExecutable() != null ){
				detector.setGotoCompiler( config.getCompilerExecutable() );
			}
			
			if ( config.getModelCheckerExecutable() != null ){
				detector.setExecutable( config.getModelCheckerExecutable() );
			}
			
			detector.setRedefineMathFuncs(config.isOptimizeSpeed());
			detector.setExportTargetOnly(config.isValidateChangedFunctionsOnly());
			
			if ( config.getVariablesToCheck() != null ){
				detector.setVarsToInclude(config.getVariablesToCheck());
			}
			
			
			violatedAssertions = detector.identifyRegressions(mc);
			
			File monitoredFunctions = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc);
			
			File injectedAssertionsFile = ConfigurationFilesManager.getVARTresult_NonRegressionPropertiesInjected(mc);
			File injectedAssertionsFileWithLines = ConfigurationFilesManager.getVARTresult_NonRegressionPropertiesInjectedLines(mc);
			
			File srcFolder = regConfig.getModifiedSwSourcesFolderFile();
			File injectedFolder = ConfigurationFilesManager.getGotoCCSrcFolderModified(mc);
			
			CorrespondingLinesFinder f = new CorrespondingLinesFinder(monitoredFunctions, srcFolder, injectedFolder, injectedAssertionsFile, injectedAssertionsFileWithLines);
			
		} catch (ConfigurationFilesManagerException e) {
			monitor.worked(4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelsFetcherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private boolean skipMonitoring() {
		return Boolean.getBoolean(BCT_SKIP_MONITORING);
	}

	private void runMake(String originalSwSourcesFolder, String testTarget, File monitoringScript, File tracesDir) {
		String make = EnvUtil.getMakeExecutablePath();
		
		ArrayList<String> cmd = new ArrayList<String>();
		cmd.add(make);
		cmd.add(testTarget);
		
		CRegressionConfiguration config = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		switch ( config.getMonitoringFramework() ){
		case PIN:
			cmd.add("VART_RECORDER="+ConsoleHelper.createPinCommandToExecuteAsSingleString(mc, config, tracesDir));
			break;
		default:
			cmd.add("VART_RECORDER="+ConsoleHelper.createGdbCommandToExecuteAsSingleString(monitoringScript));
		}

		try {
			StringBuffer out = new StringBuffer();
			ProcessRunner.run(cmd, out, out, 0, new File( originalSwSourcesFolder), null );
			System.out.println(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public Set<String> getViolatedAssertions() {
		return violatedAssertions;
	}

}
