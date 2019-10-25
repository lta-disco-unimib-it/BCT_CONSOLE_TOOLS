package it.unimib.disco.lta.bct.bctjavaeclipse.core.operations;

import failureDetection.Failure;
import failureDetection.FailureDetector;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfigurationUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.BctRegressionAnalysisRunnableWithProgress;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.BctViolationsAnalysisSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;

import tools.violationsAnalyzer.BctViolationsAnalyzer;
import tools.violationsAnalyzer.BctViolationsLogData;
import tools.violationsAnalyzer.BctViolationsManager;
import tools.violationsAnalyzer.CBEBctViolationsLogLoader;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.FailuresManager;
import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilter;
import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilterCorrectOut;
import tools.violationsAnalyzer.filteringStrategies.IdManager;
import tools.violationsAnalyzer.filteringStrategies.IdManagerProcess;
import tools.violationsAnalyzer.filteringStrategies.IdManagerTest;

public class RegressionAnalysis {
	
	

	public static class RegressionAnalysisOutput {
		private IFile analysisFile;
		private RegressionAnalysisResult violationAnalysisResult;
		
		public RegressionAnalysisOutput(IFile analysisFile,
				RegressionAnalysisResult violationAnalysisResult) {
			super();
			this.analysisFile = analysisFile;
			this.violationAnalysisResult = violationAnalysisResult;
		}

		public IFile getAnalysisFile() {
			return analysisFile;
		}

		public RegressionAnalysisResult getViolationAnalysisResult() {
			return violationAnalysisResult;
		}
		
		
	}
	
	/**
	 * Identifies a list of anomalies that may pinpoint a regression.
	 * Correspondes to  identifyRegressions(mc, false);
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @throws CBEBctViolationsLogLoaderException
	 */
	public static RegressionAnalysisOutput identifyRegressions(MonitoringConfiguration mc) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		return identifyRegressions(mc, false);
	}	
	
	/**
	 * Identifies a list of anomalies that may pinpoint a regression.
	 * If onlyFailing is true only the anomalies that belong to failing processes are considered.
	 * Otherwise the anomalies identified in all the processes are returned.
	 * 
	 * @param mc	
	 * @param onlyFailing
	 * @return
	 * @throws ConfigurationFilesManagerException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @throws CBEBctViolationsLogLoaderException
	 */
	public static RegressionAnalysisOutput identifyRegressions(MonitoringConfiguration mc, boolean onlyFailing) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		return identifyRegressions(mc, null, onlyFailing, true, false);
	}	
	
	/**
	 *  Identifies a list of anomalies that may pinpoint a regression.
	 * Returns only the anomalies that belong to the process whose failure ID is passed as input.
	 * 
	 * @param mc
	 * @param failureId
	 * @return
	 * @throws ConfigurationFilesManagerException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @throws CBEBctViolationsLogLoaderException
	 */
	public static RegressionAnalysisOutput identifyRegressionsForPassingProcess(MonitoringConfiguration mc, String failureId) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		return identifyRegressions(mc, failureId, false, true, false);
	}
	
	public static RegressionAnalysisOutput identifyRegressionsForFailingProcess(MonitoringConfiguration mc, String failureId) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		return identifyRegressions(mc, failureId, true, true, true );
	}
	
	public static RegressionAnalysisOutput identifyRegressions(MonitoringConfiguration mc, String failureId, boolean onlyFailing, boolean workWithTests, boolean analyzeFirstFailure ) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		Set<String> failuresToAnalyze = null;
		
		if ( failureId != null ){
			failuresToAnalyze = new HashSet<String>();
			failuresToAnalyze.add(failureId);
		}
		
		return identifyRegressionsOnMultipleFailures(mc, failuresToAnalyze, onlyFailing, workWithTests, analyzeFirstFailure);
	}
	
	public static RegressionAnalysisOutput identifyRegressionsOnMultipleFailures(MonitoringConfiguration mc, Set<String> failuresToAnalyze, boolean onlyFailing, boolean workWithTests, boolean analyzeFirstFailure ) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
				
		CBEBctViolationsLogLoader loader = new CBEBctViolationsLogLoader();
		File logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);
		

		Collection<File> cbeLogs = new ArrayList<File>();
		cbeLogs.add(logFile);
		
		List<IFile> cbeILogs = new ArrayList<IFile>();
		Path logFilePath = new Path( logFile.getAbsolutePath() );
		cbeILogs.add( ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(logFilePath) );
		
		
		
		BctViolationsLogData logData = loader.load(cbeLogs );
		
		List<BctModelViolation> violations = new ArrayList<BctModelViolation>();
		
		
		if ( failuresToAnalyze == null && workWithTests ) {
			workWithTests = hasFailingTests(logData);
		}
		
		
		
		if ( failuresToAnalyze == null ) {
			failuresToAnalyze = identifyFailuresToAnalyze( onlyFailing, logData, workWithTests, analyzeFirstFailure);
		} else {
			Set<String> failuresToAdd = new HashSet<String>();
			failuresToAdd.addAll(failuresToAnalyze);
			
			for ( Failure f : logData.getFailures() ){
				if( workWithTests ){
						failuresToAdd.removeAll(Arrays.asList(f.getCurrentTests()));
				} else {
					failuresToAdd.remove(f.getPid());
				}
			}	
			
			FailureDetector fd = new FailureDetector();			
			for ( String f : failuresToAdd ){
				
				//fd.throwableCaughtInTest(e, catchingMethod, testId);
			}
		}

		
		

		RegressionAnalysisResult violationAnalysisResult = new RegressionAnalysisResult(failuresToAnalyze.toString());


		File violationsAnalysisFolder = ConfigurationFilesManager.getViolationsLogAnalysisFolder(mc);
		IContainer fileContainer = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(new Path(violationsAnalysisFolder.getAbsolutePath()));

		IdManager idManager;
		if ( workWithTests ){
			idManager = IdManagerTest.INSTANCE;
		} else {
			idManager = IdManagerProcess.INSTANCE;
		}
		
		BctViolationsAnalyzer analyzer = createViolationsAnalyzer(logData,onlyFailing,idManager);
		
		String logName = "RegressionAnalysis-"+System.currentTimeMillis();

		IFile violationsLogAnalysisFile = fileContainer.getFile( new Path( logName+".bctla" ) );
		IFile anomalyGraphsFolder = fileContainer.getFile( new Path( logName ) );

		final BctViolationsAnalysisConfiguration config = BctViolationsAnalysisConfigurationUtil.createAndStoreBctViolationsAnalysisConfiguration(violationsLogAnalysisFile, true, cbeILogs,anomalyGraphsFolder);

		final IRunnableWithProgress runnable = new BctRegressionAnalysisRunnableWithProgress(null, mc, analyzer, failuresToAnalyze, violationAnalysisResult);
		runnable.run(new NullProgressMonitor());
		
		config.defineFilteringStrategy(idManager);
		
		if ( failuresToAnalyze != null && failuresToAnalyze.size() == 1 ){
			config.setFailureToAnalyze(failuresToAnalyze.iterator().next());
		}
		
		config.setFilteringResult(violationAnalysisResult);
		violations.addAll( violationAnalysisResult.getFilteredViolations() );
		
		try {
			BctViolationsAnalysisSerializer.serialize(violationsLogAnalysisFile.getLocation().toFile(), config);
		} catch (FileNotFoundException e) {
			Logger.getInstance().log(e);
		}


		return new RegressionAnalysisOutput(violationsLogAnalysisFile, violationAnalysisResult);
	}

	public static boolean hasFailingTests(BctViolationsLogData logData) {
		boolean workWithTests = false;
		for ( Failure failure : logData.getFailures() ){

			String testId = failure.getFailingTestId(); 
			if (  testId != null && ( ! testId.trim().isEmpty() ) ){
				workWithTests = true;
				break;
			}
		}
		return workWithTests;
	}

	public static Set<String> identifyFailuresToAnalyze( boolean onlyFailing, BctViolationsLogData logData, boolean hasTests, boolean analyzeFirstFailureOnly) {
		Set<String> failuresToAnalyze;
		
			
		if ( onlyFailing ){
			failuresToAnalyze = new HashSet<String>();
			
			for ( Failure failure : logData.getFailures() ){
				if ( hasTests ){
					failuresToAnalyze.add(failure.getFailingTestId());
				} else {
					failuresToAnalyze.add(failure.getFailingPID()); 
				}
				
				if ( analyzeFirstFailureOnly ){
					break;
				}
			}
		} else {
			if ( hasTests ){
				failuresToAnalyze = logData.getTestsIds();
			} else {
				failuresToAnalyze = logData.getProcessesIds();
			}
		}

		return failuresToAnalyze;
	}
	
	private static BctViolationsAnalyzer createViolationsAnalyzer(BctViolationsLogData logData, boolean onlyFailing, IdManager idManager ) {
		FailuresManager fm = new FailuresManager();

		if ( onlyFailing ){
			for ( Failure f : logData.getFailures() ){
				fm.addFailingProcess(f.getFailingPID());
				fm.addFailingTest(f.getFailingTestId());
			}
			Set<String> failingPs = fm.getFailingProcesses();
			for ( String pid : logData.getProcessesIds() ){
				if ( ! failingPs.contains(pid) ){
					fm.addCorrectProcess(pid);
				}
			}
			
			Set<String> failingTests = fm.getFailingTests();
			for ( String pid : logData.getTestsIds() ){
				if ( ! failingTests.contains(pid) ){
					fm.addCorrectTest(pid);
				}
			}
			
		} else {
			for ( String id : logData.getProcessesIds() ){
				fm.addFailingProcess(id);
			}
			for ( String id : logData.getTestsIds() ){
				fm.addFailingTest(id);
			}
		}


		

		//For now create the correct out filtering strategy, in the future we will insert other filtering strategies
		//Probably we will put the default choiche in the BctCOnfiguration and let the user change through a menu openable from the composite
		BctRuntimeDataFilter filter = new BctRuntimeDataFilterCorrectOut();
		
		BctViolationsManager manager = new BctViolationsManager();
		
		for ( modelsViolations.BctModelViolation data : logData.getViolations() ){
			manager.addDatum(data);
		}

		return new BctViolationsAnalyzer(manager,fm, filter,idManager);

	}
}
