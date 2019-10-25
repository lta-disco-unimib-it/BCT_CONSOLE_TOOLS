package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;


import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilterCorrectOut;
import tools.violationsAnalyzer.filteringStrategies.IdManager;
import tools.violationsAnalyzer.filteringStrategies.IdManagerAction;
import tools.violationsAnalyzer.filteringStrategies.IdManagerProcess;
import tools.violationsAnalyzer.filteringStrategies.IdManagerTest;

/**
 * Class for the configuration of a violations analysis
 * 
 * @author Fabrizio Pastore
 *
 */
public class BctViolationsAnalysisConfiguration {
	private Set<String> failingProcesses = new HashSet<String>();
	private Set<String> failingActions = new HashSet<String>();
	private Set<String> failingTests = new HashSet<String>();
	private List<String> logFilesNames = new ArrayList<String>();
	private String anomaliesGraphsPath;
	private AvaConfiguration avaConfiguration;
	private String failureToAnalyze;
	
	public AvaConfiguration getAvaConfiguration() {
		return avaConfiguration;
	}

	public void setAvaConfiguration(AvaConfiguration avaConfiguration) {
		this.avaConfiguration = avaConfiguration;
	}

	private IdManager filteringStrategies[] = { IdManagerAction.INSTANCE, 
		IdManagerProcess.INSTANCE, 
		IdManagerTest.INSTANCE };
	
	private int filteringStrategyId;
	
	private ViolationsAnalysisResult violationsAnalysisResult;
	private AvaAnalysisResult avaAnalysisResult;
	private RegressionAnalysisResult filteringResult;

	public BctViolationsAnalysisConfiguration(){
		
	}
	
	public IdManager retrieveFilteringStrategy() {
		if  ( filteringStrategyId > filteringStrategies.length -1){
			return filteringStrategies[0];
		}
		return filteringStrategies[filteringStrategyId];
	}

	public void defineFilteringStrategy(
			IdManager filteringStrategy) {
		int count = -1;
		for ( IdManager fs  : filteringStrategies){
			count++;
			if ( fs == filteringStrategy ){
				this.filteringStrategyId = count;
			}
		}
	}

	public Set<String> getFailingProcesses() {
		return failingProcesses;
	}

	public Set<String> getFailingActions() {
		return failingActions;
	}

	public Set<String> getFailingTests() {
		return failingTests;
	}
	
	public void addFailingProcess(String process) {
		failingProcesses.add(process);
	}
	
	public void addFailingTest(String test) {
		failingTests.add(test);
	}
	
	public void addFailingAction(String action) {
		failingActions.add(action);
	}
	
	public void addFailingProcesses(Collection<String> process) {
		failingProcesses.addAll(process);
	}
	
	public void addFailingTests(Collection<String> test) {
		failingTests.addAll(test);
	}
	
	public void addFailingActions(Collection<String> action) {
		failingActions.addAll(action);
	}

	public String getAnomaliesGraphsPath() {
		return anomaliesGraphsPath;
	}

	public void setAnomaliesGraphsPath(String anomaliesGraphsPath) {
		this.anomaliesGraphsPath = anomaliesGraphsPath;
	}

	public ViolationsAnalysisResult getViolationsAnalysisResult() {
		return violationsAnalysisResult;
	}

	public void setViolationsAnalysisResult(
			ViolationsAnalysisResult violationsAnalysisResult) {
		this.violationsAnalysisResult = violationsAnalysisResult;
	}

	public List<File> retrieveLogFiles() {
		ArrayList<File> files = new ArrayList<File>();
		for ( String fileName : logFilesNames ){
				
				IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
				
				//check for backward data compatibility: previously we did not record log files path relative to workspace
				//but we recorded the absolute filesystem path 
				if ( ifile == null ){
					//ifile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileName));
					files.add( new File( fileName ) );
				} else {
					if ( ifile.exists() ){
						files.add( ifile.getLocation().toFile() );
					}
				}
	
		}
		return files;
	}

//	public void defineLogFiles(List<File> logFiles) {
//		logFilesNames.clear();
//		for ( File file : logFiles ){
//			logFilesNames.add(file.getAbsolutePath());
//		}
//	}

	public List<String> getLogFilesNames() {
		return logFilesNames;
	}

	/**
	 * Set log files.
	 * Must be relative to the workspace
	 * @param logFilesNames
	 */
	public void setLogFilesNames(List<String> logFilesNames) {
		this.logFilesNames = logFilesNames;
	}

	public int getFilteringStrategyId() {
		return filteringStrategyId;
	}
	
	public void setFilteringStrategyId(int filteringStrategyId) {
		this.filteringStrategyId = filteringStrategyId;
	}

	public void setFailingProcesses(Set<String> failingProcesses) {
		this.failingProcesses = failingProcesses;
	}

	public void setFailingActions(Set<String> failingActions) {
		this.failingActions = failingActions;
	}

	public void setFailingTests(Set<String> failingTests) {
		this.failingTests = failingTests;
	}

	public void setAvaAnalysisResult(AvaAnalysisResult avaResult) {
		this.avaAnalysisResult = avaResult;
	}

	public AvaAnalysisResult getAvaAnalysisResult() {
		return avaAnalysisResult;
	}

	public String getFailureToAnalyze() {
		return failureToAnalyze;
	}

	public void setFailureToAnalyze(String failureToAnalyze) {
		this.failureToAnalyze = failureToAnalyze;
	}

	public void setFilteringResult(
			RegressionAnalysisResult violationAnalysisResult) {
		this.filteringResult = violationAnalysisResult;
	}

	public RegressionAnalysisResult getFilteringResult() {
		return filteringResult;
	}

	@Override
	public boolean equals(Object arg0) {
		
		if ( ! ( arg0 instanceof BctViolationsAnalysisConfiguration ) ){
			return false;
		}
		
		BctViolationsAnalysisConfiguration rhs = (BctViolationsAnalysisConfiguration) arg0;
		
		if ( ! failingProcesses.equals(rhs.failingProcesses ) ){
			return false;
		}
		
		if ( ! failingActions.equals(rhs.failingActions ) ) {
			return false;
		}
		
		if ( ! failingTests.equals(rhs.failingTests ) ) {
			return false;
		}
		
		if ( ! logFilesNames.equals(rhs.logFilesNames ) ) {
			return false;
		}
		
		if ( anomaliesGraphsPath == null ){
			if ( rhs.anomaliesGraphsPath != null ){
				return false;
			}
		} else {
			if ( ! anomaliesGraphsPath.equals(rhs.anomaliesGraphsPath ) ) {
				return false;
			}	
		}
		
		if ( avaConfiguration == null ){
			if ( rhs.avaConfiguration != null ){
				return false;
			}
		} else {
			if ( ! avaConfiguration.equals(rhs.avaConfiguration ) ) {
				return false;
			}	
		}
		
		return true;
	}
	
}
