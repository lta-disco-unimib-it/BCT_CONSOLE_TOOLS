package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.eclipse.core.resources.IFile;










public class CConfiguration {

	protected boolean deriveFunctionInvariants;
	protected boolean deriveLineInvariants = true;
	private boolean dll;
	private boolean excludeLineInfoFromFSA;
	protected boolean excludeUnusedVariables = true;
	protected boolean filterAllNonTerminatingFunctions;
	protected boolean filterNotTerminatedFunctionCalls;
	private ProgramPoint lastPP;
	protected boolean monitorFunctionEnterExitPoints = true; 
	protected boolean monitorFunctionsCalledByTargetFunctions = true; 
	private boolean monitorGlobalVariables = true; 
	private boolean monitorLibraryCalls; 
	private boolean monitorLocalVariables = true;
	protected boolean monitorProjectFunctionsOnly = true;
	protected String originalSwExecutable;
	protected String originalSwSourcesFolder;
	protected boolean recordCallingContextData;
	protected boolean simulateClosingOfLastNotTerminatedFunctions;
	protected List<ProgramPoint> sourceProgramPoints = new ArrayList<ProgramPoint>();
	protected boolean traceAllLinesOfChildren;
	protected boolean traceAllLinesOfMonitoredFunctions = true;
	protected boolean useDemangledNames;
	protected Set<String> functionsToFilterOut;
	
	protected boolean monitorPointerToThis = true;
	private String testsFolder;

	public void setFunctionsToFilterOut(Set<String> functionsToFilterOut) {
		this.functionsToFilterOut = functionsToFilterOut;
	}

	public CConfiguration() {
		super();
	}
	
	public CConfiguration( CConfiguration parent ){
		
		
		deriveFunctionInvariants = parent.deriveFunctionInvariants;
		deriveLineInvariants = parent.deriveLineInvariants;
		dll = parent.dll;
		excludeLineInfoFromFSA = parent.excludeLineInfoFromFSA;
		excludeUnusedVariables = parent.excludeUnusedVariables;
		filterAllNonTerminatingFunctions=parent.filterAllNonTerminatingFunctions;
		filterNotTerminatedFunctionCalls=parent.filterNotTerminatedFunctionCalls;
		lastPP=parent.lastPP;
		monitorFunctionEnterExitPoints = parent.monitorFunctionEnterExitPoints;
		monitorFunctionsCalledByTargetFunctions = parent.monitorFunctionsCalledByTargetFunctions;
		monitorProjectFunctionsOnly=parent.monitorProjectFunctionsOnly;
		monitorGlobalVariables=parent.monitorGlobalVariables;
		monitorLibraryCalls=parent.monitorLibraryCalls;
		monitorLocalVariables=parent.monitorLocalVariables;
		originalSwExecutable=parent.originalSwExecutable;
		originalSwSourcesFolder=parent.originalSwSourcesFolder;
		recordCallingContextData = parent.recordCallingContextData;
		simulateClosingOfLastNotTerminatedFunctions=parent.simulateClosingOfLastNotTerminatedFunctions;
		sourceProgramPoints.addAll(parent.sourceProgramPoints);
		traceAllLinesOfChildren = parent.traceAllLinesOfChildren;
		traceAllLinesOfMonitoredFunctions = parent.traceAllLinesOfMonitoredFunctions;
		useDemangledNames = parent.useDemangledNames;
		
		functionsToFilterOut = parent.functionsToFilterOut;
		monitorPointerToThis = parent.monitorPointerToThis;
		
		monitoringFramework = parent.monitoringFramework;
		pinHome = parent.pinHome;
	}

	public boolean isMonitorPointerToThis() {
		return monitorPointerToThis;
	}

	public void setMonitorPointerToThis(boolean monitorPointerToThis) {
		this.monitorPointerToThis = monitorPointerToThis;
	}

	public void addSourceProgramPoint(IFile openFile, int startLine) throws IOException {
		String fileToMonitor = openFile.getFullPath().toString();
		
		sourceProgramPoints.add( new ProgramPoint( fileToMonitor, startLine) );
	}

	public void addSourceProgramPoint(ProgramPoint programPoint) {
		sourceProgramPoints.add(programPoint);
		
		lastPP = programPoint;
	}

	public ProgramPoint getLastPP() {
		return lastPP;
	}

	public String getOriginalSwExecutable() {
		return originalSwExecutable;
	}

	public File getOriginalSwSourcesFolderFile() {
		return new File( originalSwSourcesFolder );
	}
	
	public String getOriginalSwSourcesFolder() {
		return originalSwSourcesFolder;
	}

	public List<ProgramPoint> getSourceProgramPoints() {
		return sourceProgramPoints;
	}

	public boolean isDeriveFunctionInvariants() {
		return deriveFunctionInvariants;
	}

	public boolean isDeriveLineInvariants() {
		return deriveLineInvariants;
	}

	public boolean isDll() {
		return dll;
	}

	public boolean isExcludeLineInfoFromFSA() {
		return excludeLineInfoFromFSA;
	}

	public boolean isExcludeUnusedVariables() {
		return excludeUnusedVariables;
	}

	public boolean isFilterAllNonTerminatingFunctions() {
		return filterAllNonTerminatingFunctions;
	}

	public boolean isFilterNotTerminatedFunctionCalls() {
		return filterNotTerminatedFunctionCalls;
	}

	public boolean isMonitorFunctionEnterExitPoints() {
		return monitorFunctionEnterExitPoints;
	}

	public boolean isMonitorFunctionsCalledByTargetFunctions() {
		return monitorFunctionsCalledByTargetFunctions;
	}

	
	
	
	public boolean isMonitorGlobalVariables() {
		return monitorGlobalVariables;
	}

	public boolean isMonitorLibraryCalls() {
		return monitorLibraryCalls;
	}

	public boolean isMonitorLocalVariables() {
		return monitorLocalVariables;
	}

	public boolean isMonitorProjectFunctionsOnly() {
		return monitorProjectFunctionsOnly;
	}

	public boolean isRecordCallingContextData() {
		return recordCallingContextData;
	}

	public boolean isSimulateClosingOfLastNotTerminatedFunctions() {
		return simulateClosingOfLastNotTerminatedFunctions;
	}

	public boolean isTraceAllLinesOfChildren() {
		return traceAllLinesOfChildren;
	}

	public boolean isTraceAllLinesOfMonitoredFunctions() {
		return traceAllLinesOfMonitoredFunctions;
	}

	public boolean isUseDemangledNames() {
		return useDemangledNames;
	}

	public void setDeriveFunctionInvariants(boolean deriveFunctionInvariants) {
		this.deriveFunctionInvariants = deriveFunctionInvariants;
	}

	public void setDeriveLineInvariants(boolean deriveLineInvariants) {
		this.deriveLineInvariants = deriveLineInvariants;
	}

	public void setDll(boolean selection) {
		dll = selection;
	}

	public void setExcludeLineInfoFromFSA(boolean value) {
		this.excludeLineInfoFromFSA = 		value;
	}

	public void setExcludeUnusedVariables(boolean excludeUnusedVariables) {
		this.excludeUnusedVariables = excludeUnusedVariables;
	}

	public void setFilterAllNonTerminatingFunctions(
			boolean filterAllNonTerminatingFunctions) {
		this.filterAllNonTerminatingFunctions = filterAllNonTerminatingFunctions;
	}

	public void setFilterNotTerminatedFunctionCalls(
			boolean filterNotTerminatedFunctionCalls) {
		this.filterNotTerminatedFunctionCalls = filterNotTerminatedFunctionCalls;
	}

	public void setLastPP(ProgramPoint lastPP) {
		this.lastPP = lastPP;
	}

	public void setMonitorFunctionEnterExitPoints(
			boolean monitorFunctionEnterExitPoints) {
		this.monitorFunctionEnterExitPoints = monitorFunctionEnterExitPoints;
	}

	public void setMonitorFunctionsCalledByTargetFunctions(
			boolean monitorFunctionsCalledByTargetFunctions) {
		this.monitorFunctionsCalledByTargetFunctions = monitorFunctionsCalledByTargetFunctions;
	}

	public void setMonitorGlobalVariables(boolean monitorGlobalVariables) {
		this.monitorGlobalVariables = monitorGlobalVariables;
	}

	public void setMonitorLibraryCalls(boolean monitorLibraryCalls) {
		this.monitorLibraryCalls = monitorLibraryCalls;
	}

	public void setMonitorLocalVariables(boolean monitorLocalVariables) {
		this.monitorLocalVariables = monitorLocalVariables;
	}

	public void setMonitorProjectFunctionsOnly(boolean monitorProjectFunctionsOnly) {
		this.monitorProjectFunctionsOnly = monitorProjectFunctionsOnly;
	}

	public void setOriginalSwExecutable(String originalSwExecutable) {
		this.originalSwExecutable = originalSwExecutable;
	}

	public void setOriginalSwSourcesFolder(String originalSwSourcesFolder) {
		this.originalSwSourcesFolder = originalSwSourcesFolder;
	}

	public void setRecordCallingContextData(boolean recordCallingContextData) {
		this.recordCallingContextData = recordCallingContextData;
	}

	public void setSimulateClosingOfLastNotTerminatedFunctions(
			boolean simulateClosingOfLastNotTerminatedFunctions) {
		this.simulateClosingOfLastNotTerminatedFunctions = simulateClosingOfLastNotTerminatedFunctions;
	}

	public void setSourceProgramPoints(List<ProgramPoint> sourceProgramPoints) {
		this.sourceProgramPoints = sourceProgramPoints;
	}

	public void setTraceAllLinesOfChildren(boolean traceAllLinesOfChildren) {
		this.traceAllLinesOfChildren = traceAllLinesOfChildren;
	}

	public void setTraceAllLinesOfMonitoredFunctions(
			boolean traceAllLinesOfMonitoredFunctions) {
		this.traceAllLinesOfMonitoredFunctions = traceAllLinesOfMonitoredFunctions;
	}

	public void setUseDemangledNames(boolean useDemangledNames) {
		this.useDemangledNames = useDemangledNames;
	}

	public Set<String> getFunctionsToFilterOut() {
		return functionsToFilterOut;
	}

	public String getTestsFolder() {
		return testsFolder;
	}

	public void setTestsFolder(String testsFolder) {
		this.testsFolder = testsFolder;
	}


	public static enum MonitoringFramework { GDB, PIN };
	private MonitoringFramework monitoringFramework = MonitoringFramework.GDB;
	
	public MonitoringFramework getMonitoringFramework() {
		return monitoringFramework;
	}
	
	public void setMonitoringFramework(MonitoringFramework mf) {
		monitoringFramework = mf;
	}

	
	
	
	private String pinHome;
	public void setPinHome(String pinHome){
		this.pinHome = pinHome;
	}
	public String getPinHome() {
		return pinHome;
	}
	
	
}