package console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;

import conf.EnvironmentalSetter;
import cpp.gdb.GdbRegressionConfigCreator;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConsoleHelper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration.MonitoringFramework;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.CleaningUtil;


public class ScriptsGenerator {
	///PROPERTIES-DESCRIPTION: Options that manage the generation of the BCT/RADAR setup folder



	///list of regular expressions that match the mangled name of a function. Separated by ;. Used to specify the functions to monitor. You can specify an expression that separately matches namesapce, class and function by using the space char. for example we can match all methods of Visitor classes implemented in namespace myNamespace using the following expression: myNamesapce::.* .*Visitor .* 
	private static final String BCT_INCLUSION_RULES = "bct.inclusionRules";
	
	///list of regular expressions that match the mangled name of a function to exclude form monitoring. Separated by ;. Used to specify the functions to monitor. You can specify an expression that separately matches namesapce, class and function by using the space char. for example we can match all methods of Visitor classes implemented in namespace myNamespace using the following expression: myNamesapce::.* .*Visitor .* 
	private static final String BCT_EXCLUSION_RULES = "bct.exclusionRules";
	
	///list of regular expressions that match the mangled name of an environment function to exclude form monitoring. Separated by ;. Used to specify the functions to monitor. You can specify an expression that separately matches namesapce, class and function by using the space char. for example we can match all methods of Visitor classes implemented in namespace myNamespace using the following expression: myNamesapce::.* .*Visitor .* 
	private static final String BCT_ENV_EXCLUSION_RULES = "bct.envExclusionRules";

	///monitor only the functions expressively indicated by the user
	private static final String BCT_CUSTOM_ONLY = "bct.customOnly";

	///if true filterout all the function for which at least an EXIT point is missing
	private static final String BCT_FILTER_NOT_CLOSED = "bct.filterNotClosed";

	///if true simulates the closing of functions when the execution is broken in the middle (useful for cyclic executions)
	private static final String BCT_SIMULATE_CLOSING_OF_FUNCTIONS = "bct.simulateClosingOfFunctions";

	///if true monitor all the lines of the target functions
	private static final String BCT_TRACE_ALL = "bct.traceAll";

	///if true monitor all the functions of the program
	private static final String BCT_COMPLETE_MONITORING = "bct.completeMonitoring";

	///if true monitor all the program functions if no change is identified
	private static final String BCT_MONITOR_ALL_IF_NO_CHANGE = "bct.monitorAllIfNoChange";

	///list of mangled function names not to monitor. Separated by ;
	private static final String BCT_FUNCTIONS_TO_FILTER_OUT = "bct.functionsToFilterOut";

	///if true, in C++ programs, record information about the object "this" (fault true)
	private static final String BCT_MONITOR_POINTER_TO_THIS = "bct.monitorPointerToThis";

	///if true monitor global variables
	private static final String BCT_MONITOR_GLOBAL_VARIABLES = "bct.monitorGlobalVariables";

	private static final String BCT_TESTING_RULES = "bct.testCasesSelectionRules";
	
	protected boolean customOnly;
	protected boolean traceAllLinesOfMonitoredFunctions = false;
	protected boolean filterAllNonTerminatingFunctions = false;
	protected boolean completeMonitoring = false;
	protected boolean traceAllLinesOfChildren = false;
	protected boolean monitorCallersOfModifiedFunctions = false;
	protected boolean excludeUnusedVariables = true;
	protected boolean dll = false;
	protected boolean monitorAllIfNoChange = false;
	protected boolean monitorPointerToThis = true;
	protected Set<String> functionsToFilterOut;
	protected boolean simulateClosingOfNotTerminatedFunctions = false;

	private List<String> customInclusionRules;

	private List<String> customExclusionRules;

	protected boolean monitorGlobalVariables = true;

	public boolean isMonitorGlobalVariables() {
		return monitorGlobalVariables;
	}

	public void setMonitorGlobalVariables(boolean monitorGlobalVariables) {
		this.monitorGlobalVariables = monitorGlobalVariables;
	}

	public boolean isMonitorAllIfNoChange() {
		return monitorAllIfNoChange;
	}

	public void setMonitorAllIfNoChange(boolean monitorAllIfNoChange) {
		this.monitorAllIfNoChange = monitorAllIfNoChange;
	}

	public boolean isDll() {
		return dll;
	}

	public void setDll(boolean dll) {
		this.dll = dll;
	}

	public boolean isExcludeUnusedVariables() {
		return excludeUnusedVariables;
	}

	public void setExcludeUnusedVariables(boolean excludeUnusedVariables) {
		this.excludeUnusedVariables = excludeUnusedVariables;
	}

	public boolean isMonitorCallersOfModifiedFunctions() {
		return monitorCallersOfModifiedFunctions;
	}

	public void setMonitorCallersOfModifiedFunctions(
			boolean monitorCallersOfModifiedFunctions) {
		this.monitorCallersOfModifiedFunctions = monitorCallersOfModifiedFunctions;
	}

	public boolean isFilterAllNonTerminatingFunctions() {
		return filterAllNonTerminatingFunctions;
	}

	public void setFilterAllNonTerminatingFunctions(
			boolean filterAllNonTerminatingFunctions) {
		this.filterAllNonTerminatingFunctions = filterAllNonTerminatingFunctions;
	}

	public boolean isTraceAllLinesOfMonitoredFunctions() {
		return traceAllLinesOfMonitoredFunctions;
	}

	public void setTraceAllLinesOfMonitoredFunctions(
			boolean traceAllLinesOfMonitoredFunctions) {
		this.traceAllLinesOfMonitoredFunctions = traceAllLinesOfMonitoredFunctions;
	}

	public boolean isTraceAllLinesOfChildren() {
		return traceAllLinesOfChildren;
	}

	public void setTraceAllLinesOfChildren(
			boolean traceAllLinesOfChildren) {
		this.traceAllLinesOfChildren = traceAllLinesOfChildren;
	}

	public CConfiguration createCConfiguration(String originalSW, String originalSources){

		CConfiguration 
		regressionConfiguration = new CConfiguration();



		regressionConfiguration.setOriginalSwExecutable(originalSW);
		regressionConfiguration.setOriginalSwSourcesFolder(originalSources);



		regressionConfiguration.setUseDemangledNames( false );
		regressionConfiguration.setMonitorProjectFunctionsOnly( true );

		regressionConfiguration.setFilterNotTerminatedFunctionCalls(false);
		regressionConfiguration.setFilterAllNonTerminatingFunctions(filterAllNonTerminatingFunctions);
		regressionConfiguration.setSimulateClosingOfLastNotTerminatedFunctions(simulateClosingOfNotTerminatedFunctions);


		regressionConfiguration.setTraceAllLinesOfMonitoredFunctions(traceAllLinesOfMonitoredFunctions);
		regressionConfiguration.setTraceAllLinesOfChildren(traceAllLinesOfChildren);

		regressionConfiguration.setDeriveFunctionInvariants(false);
		regressionConfiguration.setDeriveLineInvariants(true);

		regressionConfiguration.setMonitorGlobalVariables(monitorGlobalVariables);
		regressionConfiguration.setMonitorLocalVariables(true);


		regressionConfiguration.setMonitorFunctionsCalledByTargetFunctions(true);

		regressionConfiguration.setMonitorFunctionEnterExitPoints(true);
		regressionConfiguration.setMonitorLibraryCalls(false);

		regressionConfiguration.setRecordCallingContextData(false);

		regressionConfiguration.setExcludeLineInfoFromFSA(false);

		regressionConfiguration.setDll(dll);

		regressionConfiguration.setExcludeUnusedVariables(excludeUnusedVariables );
		
		regressionConfiguration.setMonitorPointerToThis(monitorPointerToThis);
		
		regressionConfiguration.setFunctionsToFilterOut(functionsToFilterOut);

		
		{
			String value = System.getProperty(BCT_MONITOR_GLOBAL_VARIABLES);
			if ( value != null) {
				regressionConfiguration.setMonitorGlobalVariables(Boolean.valueOf(value));
			}
		}
		
		regressionConfiguration.setMonitoringFramework(MonitoringFramework.GDB);
		boolean generatePinProbes = Boolean.getBoolean(GdbRegressionConfigCreator.BCT_GENERATE_PIN_PROBES);
		if ( generatePinProbes ){
			regressionConfiguration.setMonitoringFramework(MonitoringFramework.PIN);
			
			regressionConfiguration.setPinHome(System.getProperty(GdbRegressionConfigCreator.BCT_PIN_HOME));
		}
		
		return regressionConfiguration;
	}

	private CRegressionConfiguration createCRegressionConfig(String originalSources, String originalSW, String modifiedSources, String modifiedSw){
		CRegressionConfiguration regressionConfiguration = new CRegressionConfiguration( createCConfiguration(originalSW, originalSources) );



		regressionConfiguration.setModifiedSwExecutable(modifiedSw);
		regressionConfiguration.setModifiedSwSourcesFolder(modifiedSources);
		regressionConfiguration.setMonitorCallersOfModifiedFunctions(monitorCallersOfModifiedFunctions);

		regressionConfiguration.setTraceAllLinesOfChildren(false);

		regressionConfiguration.setHideAddedAndDeletedFunctions(true);
		regressionConfiguration.setMonitorOnlyNotModifiedLines(true);

		regressionConfiguration.setUseUpdatedReferencesForModels(true);
		regressionConfiguration.setMonitorAddedAndDeletedFunctions(true);
	
		{	
			String value = System.getProperty("bct.monitoring.linesOriginal");
			if ( value != null ){

				String[] lines = value.split(",");
				for( String line : lines ){

					String[] location = line.split(":");
					String openFile = new File(location[0]).getAbsolutePath();
					int startLine = Integer.valueOf(location[1]);

					regressionConfiguration.addSourceProgramPoint(openFile, startLine);
				}
			}
		}
		
		{
			String value = System.getProperty("bct.monitoring.linesModified");
			if ( value != null ){

				String[] lines = value.split(",");
				for( String line : lines ){

					String[] location = line.split(":");
					String openFile = new File(location[0]).getAbsolutePath();
					int startLine = Integer.valueOf(location[1]);

					regressionConfiguration.addSourceProgramPoint(openFile, startLine);
				}
			}
		}		

		return regressionConfiguration;
	}




	public static void main(String args[]) throws CoreException, ConfigurationFilesManagerException, FileNotFoundException{

		ScriptsGenerator generator = new ScriptsGenerator(); 

		execute(args, generator);
	}

	public boolean isMonitorPointerToThis() {
		return monitorPointerToThis;
	}

	public void setMonitorPointerToThis(boolean monitorPointerToThis) {
		this.monitorPointerToThis = monitorPointerToThis;
	}

	public Set<String> getFunctionsToFilterOut() {
		return functionsToFilterOut;
	}

	public void setFunctionsToFilterOut(Set<String> functionsToFilterOut) {
		this.functionsToFilterOut = functionsToFilterOut;
	}

	public static void execute(String[] args, ScriptsGenerator generator)
			throws CoreException, FileNotFoundException,
			ConfigurationFilesManagerException {



		{
			String value = System.getProperty(BCT_MONITOR_POINTER_TO_THIS);
			if ( value != null ){
				generator.setMonitorPointerToThis( Boolean.parseBoolean(value) );
			}
		}

		
		{
			String value = System.getProperty(BCT_FUNCTIONS_TO_FILTER_OUT);
			if ( value != null) {
				System.out.println("!!!Configuring functions to filter out");
				String[] toFilterOut = value.split(";");
				Set<String> functionsToFilterOut = new HashSet<String>();
				for ( String func : toFilterOut ){
					functionsToFilterOut.add(func);	
				}
				generator.setFunctionsToFilterOut(functionsToFilterOut);
			}
		}

		
		{
			String value = System.getProperty(BCT_MONITOR_ALL_IF_NO_CHANGE);
			if ( value != null) {
				generator.setMonitorAllIfNoChange( Boolean.parseBoolean(value) );
			}
		}

		{
			String value = System.getProperty(BCT_COMPLETE_MONITORING);
			if ( value != null) {
				generator.setCompleteMonitoring ( Boolean.parseBoolean(value) );
			}
		}

		{
			String value = System.getProperty(BCT_TRACE_ALL);
			if ( value != null) {
				generator.setTraceAllLinesOfMonitoredFunctions(Boolean.parseBoolean(value));
			}
		}

		{
			String value = System.getProperty(BCT_SIMULATE_CLOSING_OF_FUNCTIONS);
			if ( value != null) {
				generator.setSimulateClosingOfNotTerminatedFunctions(Boolean.parseBoolean(value));
			}
		}
		
		{
			String value = System.getProperty(BCT_FILTER_NOT_CLOSED);
			if ( value != null) {
				generator.setFilterAllNonTerminatingFunctions(Boolean.parseBoolean(value));
			}
		}
		
		{
			String value = System.getProperty(BCT_CUSTOM_ONLY);
			if ( value != null) {
				generator.setCustomOnly( Boolean.parseBoolean(value) );
			}
		}
		
		

		if ( args.length == 5 ){

			String projectDir = args[0];
			String originalSrc = args[1];
			String originalExec = args[2];

			String modifiedSrc = args[3];
			String modifiedExec = args[4];

			generator.generateScripts(projectDir, originalSrc, originalExec, modifiedSrc,
					modifiedExec);
		} else if ( args.length == 3 ){
			String projectDir = args[0];
			String originalSrc = args[1];
			String originalExec = args[2];


			generator.generateScripts(projectDir, originalSrc, originalExec );
		}
	}

	private void setSimulateClosingOfNotTerminatedFunctions(boolean parseBoolean) {
		simulateClosingOfNotTerminatedFunctions = parseBoolean;
	}

	public void setCustomOnly(boolean bool) {
		customOnly = bool;
	}

	public void generateScripts(String projectDir, String originalSrc,
			String originalExec) {
		ProjectSetup projectVariables = ProjectSetup.setupProject(projectDir);

		

		FlattenerOptions opt = new FlattenerOptions(false,3,"all",new ArrayList<String>());

		StorageConfiguration storageConfiguration = createStorageCOnfiguration(projectVariables);

		MonitoringConfiguration mc = MonitoringConfiguration.createMonitoringConfiguration("BCT", storageConfiguration, opt, "/"+projectVariables.getProjectName(), MonitoringConfiguration.ConfigurationTypes.C_Config  );
		
		createAdditionalConfigurations(originalSrc, originalExec, mc);


		try {
			
			addComponentsToMonitor(mc, projectVariables.getMonitoringConfigurationFile() );
			
			MonitoringConfigurationFactory.storeMonitoringConfiguration(projectVariables.getMonitoringConfigurationPath(), mc);

			
			CleaningUtil.deleteAllBctData(ConfigurationFilesManager.getBctHomeDir(mc));

			printHelperCommands(mc);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAdditionalConfigurations(String originalSrc,
			String originalExec, MonitoringConfiguration mc) {
		CConfiguration cConfig = createCConfiguration(originalExec, originalSrc);
		mc.putAdditionalConfiguration(CConfiguration.class, cConfig);
	}

	

	private  void addComponentsToMonitor(MonitoringConfiguration mc, File destination) throws FileNotFoundException, ConfigurationFilesManagerException {

		ComponentsConfiguration components = mc.getComponentsConfiguration();
		
		List<Component> componentsList = new ArrayList<Component>();
		
		components.setComponents(componentsList);

		componentsList.add( getMainComponentToMonitor() );



		CConfiguration additonalConfig = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
		additonalConfig.setMonitorFunctionsCalledByTargetFunctions(false);
		additonalConfig.setMonitorProjectFunctionsOnly(true);


		MonitoringConfigurationSerializer.serialize(destination, mc);
		ConfigurationFilesManager.updateMonitoringScripts(mc);


	}

	public Component getMainComponentToMonitor() {
		Component modifiedElements = new Component("MonitoredFunctions");
		
		populateWithCustomRules(modifiedElements);
		
		
		if ( completeMonitoring || ( ! customOnly && modifiedElements.getRules().size() == 0 ) ){
			modifiedElements.addRule(new MatchingRuleInclude(".*", ".*", ".*"));
		}
		
		
		System.out.println("MonitoredComponent "+modifiedElements);
		return modifiedElements;
	}

	private void populateWithCustomRules(Component modifiedElements) {
		{
			List<MatchingRuleExclude> rules = getCustomMatchingRuleExclude();
			for( MatchingRuleExclude rule : rules ){
				modifiedElements.addRule(rule);
			}
		}

		{
			List<MatchingRuleInclude> rules = getCustomMatchingRuleInclude();
			for( MatchingRuleInclude rule : rules ){
				modifiedElements.addRule(rule);
			}
		}
	}

	public List<MatchingRuleInclude> getCustomMatchingRuleInclude() {
		List<String> customInclusionRules = getCustomInclusionRules();
		List<MatchingRuleInclude> rules = processUserDefinedInclusionRules(customInclusionRules);
		return rules;
	}

	private List<MatchingRuleInclude> processUserDefinedInclusionRules(List<String> customInclusionRules) {
		List<MatchingRuleInclude> rules = new ArrayList<MatchingRuleInclude>();
		if ( customInclusionRules != null ){
			for ( String inclRule : customInclusionRules ){
				String[] ruleTokens = inclRule.split(" ");
				if ( ruleTokens.length == 0 ) {
					//skip empty rule
				} else if ( ruleTokens.length == 1 ) {
					rules.add(new MatchingRuleInclude(".*", ".*", inclRule) );
				} else {
					rules.add(new MatchingRuleInclude(ruleTokens[0], ruleTokens[1], ruleTokens[2]) );
				}
			}
		}
		return rules;
	}
	
	public List<MatchingRuleExclude> getCustomMatchingRuleExclude() {
		List<String> stringRules = getCustomExclusionRules();
		
		List<MatchingRuleExclude> rules = processUserDefinedEclusionRules(stringRules);
		return rules;
	}
	
	public List<MatchingRuleInclude> getCustomMatchingRulesForTestCases() {
		String testRule = System.getProperty(BCT_TESTING_RULES);
		if ( testRule == null ){
			return null;
		}
		
		List<String> stringRules = getCustomTestingRules();
		List<MatchingRuleInclude> rules = processUserDefinedInclusionRules(stringRules);
		return rules;
	}

	private List<String> getCustomTestingRules() {
		return getCustomRules(BCT_TESTING_RULES);
	}

	public List<MatchingRuleExclude> processUserDefinedEclusionRules(
			List<String> stringRules) {
		List<MatchingRuleExclude> rules = new ArrayList<MatchingRuleExclude>();
		List<String> customInclusionRules = stringRules;
		if ( customInclusionRules != null ){
			for ( String inclRule : customInclusionRules ){
				String[] ruleTokens = inclRule.split(" ");
				if ( ruleTokens.length == 1 ) {
					rules.add(new MatchingRuleExclude(".*", ".*", inclRule) );
				} else {
					rules.add(new MatchingRuleExclude(ruleTokens[0], ruleTokens[1], ruleTokens[2]) );
				}
			}
		}
		return rules;
	}

	public void setCompleteMonitoring(boolean completeMonitoring) {
		this.completeMonitoring = completeMonitoring;

	}

	public void generateScripts(String projectDir, String originalSrc,
			String originalExec, String modifiedSrc, String modifiedExec)
					throws CoreException, FileNotFoundException,
					ConfigurationFilesManagerException {
		ProjectSetup projectVariables = ProjectSetup.setupProject(projectDir);

		File originalSrcFile = new File( originalSrc );
		File modifiedSrcFile = new File( modifiedSrc );
		
		CRegressionConfiguration regressionConfig = createCRegressionConfig( originalSrcFile.getAbsolutePath(), originalExec, modifiedSrcFile.getAbsolutePath(), modifiedExec);
		FlattenerOptions opt = new FlattenerOptions(false,3,"all",new ArrayList<String>());

		StorageConfiguration storageConfig = createStorageCOnfiguration(projectVariables);

		boolean identifyComponentsToMonitor = true;
		if ( this.customOnly ){
			identifyComponentsToMonitor = false;
		}
		
//		Removed on 2014-05-16: Since we are storing it later we do not need to store it now, we need just to create it
//		MonitoringConfiguration mc = MonitoringConfigurationFactory.createAndStoreMonitoringConfiguration(new NullProgressMonitor(), 
//				"BCT", projectVariables.getMonitoringConfigurationPath(), storageConfig, regressionConfig, opt, "/"+projectVariables.getProjectName(), 
//				identifyComponentsToMonitor);

		MonitoringConfiguration mc = MonitoringConfiguration.createMonitoringConfiguration(
				"BCT", storageConfig, regressionConfig, opt, "/"+projectVariables.getProjectName(), 
				identifyComponentsToMonitor);
		
		updateAccordingToChanges(mc, projectVariables.getMonitoringConfigurationFile());
		
		
		CleaningUtil.deleteAllBctData(ConfigurationFilesManager.getBctHomeDir(mc));

		createAdditionalConfigurations(originalSrc, originalExec, mc);
		
		MonitoringConfigurationFactory.storeMonitoringConfiguration(projectVariables.getMonitoringConfigurationPath(), mc);
		
		printHelperCommands(mc);
	}

	/**
	 * If the two software versions do not show any difference updates the configuration to monitor all the functions.
	 * 
	 * @param mc
	 * @param destination
	 * @throws FileNotFoundException
	 * @throws ConfigurationFilesManagerException
	 */
	private  void updateAccordingToChanges(MonitoringConfiguration mc, File destination) throws FileNotFoundException, ConfigurationFilesManagerException {
		Component modifiedElements = null;
		for ( Component c : mc.getComponentsConfiguration().getComponents() ){
			if ( c.getName().equals("ModifiedFunctions") ){
				modifiedElements = c;
			}
		}
		
		if ( modifiedElements == null ){
			modifiedElements = new Component("ModifiedFunctions");
			ArrayList<Component> components = new ArrayList<>();
			components.add(modifiedElements);
			mc.getComponentsConfiguration().setComponents(components);
		}
		
		if ( customOnly || completeMonitoring ){
			System.out.println("RESET FOR COMPLETE");
			modifiedElements.setRules(new ArrayList<MatchingRule>()); //clean existing rules
		}

		int originalRulesSize = modifiedElements.getRules().size();
		
		populateWithCustomRules(modifiedElements);

		//Fabrizio: replaced by the function call above 2016-01-08
//		List<String> customInclusionRules = getCustomInclusionRules();
//		if ( customInclusionRules != null ){
//			for ( String inclRule : customInclusionRules ){
//				String packageExpr = ".*";
//				String classExpr = ".*";
//				
//				if ( inclRule.contains(" ") ){
//					String[] ruleTokens = inclRule.split(" ");
//					packageExpr = ruleTokens[0];
//					classExpr = ruleTokens[1];
//					inclRule = ruleTokens[2];
//				}
//				
//				modifiedElements.addRule(new MatchingRuleInclude(packageExpr, classExpr, inclRule) );
//			}
//		}

		if ( completeMonitoring || ( modifiedElements.getRules().size() == 0 && monitorAllIfNoChange ) ){
			modifiedElements.addRule(new MatchingRuleInclude(".*", ".*", ".*"));
		}

		if ( originalRulesSize != modifiedElements.getRules().size() || customOnly || completeMonitoring ){
			CRegressionConfiguration additonalConfig = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			additonalConfig.setMonitorCallersOfModifiedFunctions(false);
			additonalConfig.setMonitorFunctionsCalledByTargetFunctions(false);
			
			//Fabrizio: Commented out on 04-02-2016 to speed up scripts generation for BDCI
//			MonitoringConfigurationSerializer.serialize(destination, mc);
//			ConfigurationFilesManager.updateMonitoringScripts(mc);
		}

	}

	private List<String> getCustomInclusionRules() {
		if ( customInclusionRules != null ){
			return customInclusionRules;
		}
		return getCustomRules(BCT_INCLUSION_RULES);
	}

	private List<String> getCustomExclusionRules() {
		if ( customExclusionRules != null ){
			return customExclusionRules;
		}
		return getCustomRules(BCT_EXCLUSION_RULES);
	}
	
	protected List<String> getCustomEnvExclusionRules() {
		return getCustomRules(BCT_ENV_EXCLUSION_RULES);
	}
	
	private List<String> getCustomRules(String rulesProperty) {
		String inclusionRulesString = System.getProperty(rulesProperty);
		if ( inclusionRulesString == null || inclusionRulesString.isEmpty() ){
			return null;
		}
		String[] inclRules = inclusionRulesString.split(";");
		return Arrays.asList(inclRules);
	}

	public static void printHelperCommands(MonitoringConfiguration mc) {
		// TODO Auto-generated method stub
		try {
			File validTrace = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfig(mc);


			File traceToVerify = ConfigurationFilesManager.getModifiedSoftwareGdbMonitoringConfig(mc);

			
			if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config  ){

				
				CRegressionConfiguration regressionConfig = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
				switch ( regressionConfig.getMonitoringFramework() ){
				case GDB :
					printGdbRegressionConfigHelp(validTrace, traceToVerify);
					break;
				case PIN :
					printPinRegressionConfigHelp(mc, regressionConfig);
					break;
				}
			} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config  ){
				
				
				CConfiguration regressionConfig = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
				switch ( regressionConfig.getMonitoringFramework() ){
				case GDB :
					printGdbCConfigHelp(mc, validTrace);
					break;
				case PIN :
					printPinCConfigHelp(mc, regressionConfig);
					break;
				}
			}

		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private static void printPinRegressionConfigHelp(MonitoringConfiguration mc, CRegressionConfiguration regressionConfig) {
		
		
		try {
			File validTrace = ConfigurationFilesManager.getValidTracesFolder(mc);
			File traceToVerify = ConfigurationFilesManager.getTracesToVerifyFolder(mc);
			
			System.out.println("Use the following commands to monitor the original software:");
			System.out.println(ConsoleHelper.createPinMonitoringcommandHelp(mc, regressionConfig, validTrace));

			System.out.println("Use the following commands to monitor the modified software:");
			System.out.println(ConsoleHelper.createPinMonitoringcommandHelp(mc, regressionConfig, traceToVerify));

		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void printPinCConfigHelp(MonitoringConfiguration mc, CConfiguration regressionConfig) {
		try{
			File validTrace = ConfigurationFilesManager.getValidTracesFolder(mc);
			File traceToVerify = ConfigurationFilesManager.getTracesToVerifyFolder(mc);

			System.out.println("Use the following commands to monitor the original software:");
			System.out.println(ConsoleHelper.createPinMonitoringcommandHelp(mc, regressionConfig, validTrace));

			System.out.println("Use the following commands to monitor failing executions:");
			System.out.println(ConsoleHelper.createPinMonitoringcommandHelp(mc, regressionConfig, traceToVerify));
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printGdbCConfigHelp(MonitoringConfiguration mc,
			File validTrace) throws ConfigurationFilesManagerException {
		File traceToVerify;
		System.out.println("Use the following commands to monitor passing executions:");
		System.out.println(ConsoleHelper.createGdbMonitoringcommandHelp(validTrace));

		traceToVerify = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfigToVerify(mc);
		System.out.println("Use the following commands to monitor failing executions:");
		System.out.println(ConsoleHelper.createGdbMonitoringcommandHelp(traceToVerify));
	}

	private static void printGdbRegressionConfigHelp(File validTrace,
			File traceToVerify) {
		System.out.println("Use the following commands to monitor the original software:");
		System.out.println(ConsoleHelper.createGdbMonitoringcommandHelp(validTrace));

		System.out.println("Use the following commands to monitor the modified software:");
		System.out.println(ConsoleHelper.createGdbMonitoringcommandHelp(traceToVerify));
	}

	private static StorageConfiguration createStorageCOnfiguration(ProjectSetup projectVariables) {
		FileStorageConfiguration sc = new FileStorageConfiguration(projectVariables.getDataDirPath()); 

		return sc;
	}
	
	
	public void setCustomInclusionRules(List<String> customInclusionRules) {
		this.customInclusionRules = customInclusionRules;
	}

	public void setCustomExclusionRules(List<String> customExclusionRules) {
		this.customExclusionRules = customExclusionRules;
	}
	
}
