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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration.ModelChecker;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis.RegressionAnalysisOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;




import conf.EnvironmentalSetter;
import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import tools.fshellExporter.CBMCAssertionsInjector;
import tools.fshellExporter.CBMCClaim;
import tools.fshellExporter.CBMCExecutor;
import tools.fshellExporter.CBMCAssertionsInjector.EntryPointsTypes;
import tools.fshellExporter.CBMCExecutor.ValidationResult;
import tools.fshellExporter.CBMCModelsExporter;
import tools.fshellExporter.CBMCModelsFilter;
import tools.fshellExporter.EvolCheckExecutor;
import tools.fshellExporter.GotoCC;
import tools.fshellExporter.ModelFilter;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.FileUtil;
import util.ProcessRunner;
import cpp.gdb.CSourcesFilter;
import cpp.gdb.EnvUtil;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.SourceLinesMapper;

public class CBCMRegressionsDetector {
	///PROPERTIES-DESCRIPTION: Properties that manage the execution of VART
	
	///export data properties that regard lines
	public static final String BCT_EXPORT_LINES = "bct.exportLines";
	
	///exclude data properties that include the vars in the list (separated by ;)
	public static final String BCT_EXPORTER_VARS_TO_EXCLUDE = "bct.exporter.varsToExclude";
	
	///include only the data properties that contain all the variables in the list. Provide a list of variables sets, each set is separated by ',', each variable is separated by ':' . The following list x:y,result will include only those data properties that contains both variables "x" and "y", or that contain variable "result"
	public static final String BCT_EXPORTER_INCLUDE_ONLY = "bct.exporter.includeOnly";
	
	///unwind option to be passed to the model checker (number of times loops should be unrolled)
	public static final String BCT_CBMC_UNWIND = "bct.cbmc.unwind";
	
	//path to the file that contains the CBMC output for the modified program
	public static final String BCT_CBMC_OUTPUT_MODIFIED = "bct.cbmc.output.modified";
	
	//path to the file that contains the CBMC output for the original program
	public static final String BCT_CBMC_OUTPUT_ORIGINAL = "bct.cbmc.output.original";
	
	///skip checking of anomalies that depend on intended changes, i.e. anomalies triggered by passing test cases of th eupgraded program
	public static final String BCT_SKIP_VALID_ANOMALIES_CHECK = "bct.skipValidAnomaliesCheck";
	
	///path to the goto program (relative to source folder)
	public static final String BCT_GOTO_PROGRAM = "bct.cbmc.gotoProgram";
	
	///path to the goto program (relative to source folder)
	public static final String BCT_GOTO_PROGRAM_COMPILED = "bct.cbmc.gotoProgram.compiled";
	
	///command to use to compile with goto-cc
	public static final String BCT_COMPILE_COMMAND = "bct.cbmc.compileCommand";

	///indicates whether to skip the execution of goto-cc
	public static final String BCT_SKIP_GOTOCC_EXECUTION = "bct.skipGoToCCExecution";
	
	///indicates whether to skip the execution of cbmc
	public static final String BCT_SKIP_CBMC_EXECUTION = "bct.skipCbmcExecution";
	
	///indicates whether to skip the processing (compile and execute cbmc) of the original program 
	public static final String BCT_SKIP_PROCESSING_OF_ORIGINAL = "bct.cbmc.skipProcessingOfOriginal";
	
	///indicates whether to skip the processing (compile and execute cbmc) of the upgraded program 
	public static final String BCT_SKIP_PROCESSING_OF_MODIFIED = "bct.cbmc.skipProcessingOfModified";
	
	//indicates whether to skip the exporting of models
	public static final String BCT_SKIP_EXPORT_MODELS = "bct.skipExportModels";
	
	//indicates whether to simply create the version of the original program  and execute CBMC
	public static final String BCT_CREATE_ONLY_ORIGINAL_WITH_ASSERTIONS = "bct.createOnlyOriginalWithAssertions";
	
	
	public static final String BCT_FILTER_REDUNDANT_MODELS = "bct.filterRedundantsModels";
	
	///skip the injection of assertions (true/false)
	public static final String BCT_SKIP_ASSERTIONS_INJECTION = "bct.skipAssertionsInjection";

	///export only assertions for target functions
	private static final String BCT_EXPORT_TARGETS_ONLY = "bct.exportTargetsOnly";

	///redefine functions declared in math.h and hide math.h for speedup
	public static final String BCT_REDEFINE_MATH_FUNCS = "bct.vart.redefineMathFuncs";
	

	//CONFIGURATION VARIABLES


//	private ArrayList<String> additionalDefs = new ArrayList<String>();
//	private boolean unitVerification;
//	private boolean exportLines = true;
	private String gotoProgram = "program.goto";
	private String compileCommand;

	private boolean skipCBMCExecution;
	private boolean skipInferenceAndCheck;
	private boolean skipProcessingOfOriginal;
	private boolean skipProcessingOfModified;
	
	public boolean isSkipProcessingOfOriginal() {
		return skipProcessingOfOriginal;
	}

	public void setSkipProcessingOfOriginal(boolean skipProcessingOfOriginal) {
		this.skipProcessingOfOriginal = skipProcessingOfOriginal;
	}



	private boolean skipExportModels;
	private boolean onlyAssertionsInOriginal;
	private boolean filterRedundants;
	private boolean skipAssertionsInjection;
	private File originalSrc;
	private File modifiedSrc;
	private File bctHome;
	private File allModelsV0;
	private File allModelsV1;
	private MonitoringConfiguration mrc;
	private Map<String, FunctionMonitoringData> originalFunctions;
	private Map<String, FunctionMonitoringData> modifiedFunctions;
	private File monitoredFunctionsFile;
	private File gotoCCsrcFolderOriginal;
	private File validatedFile;
	private File allModelsV0TrueProperties;
	private File allModelsV1TrueProperties;
	private File allModelsV0Filtered;
	private File allModelsV1Filtered;
	
	private File allModelsV0Outdated;
	private File allModelsV1Outdated;
	
	private File gotoCCsrcFolderModified;
	private File monitoredFunctionsV1File;
	private int unwind = 5;
	private boolean processPointers;

	private boolean exportTargetOnly;

	private boolean redefineMathFuncs;

	private String gotoCompiler;

	private String cbmcExecutable;

	private String varsToInclude;

	private VARTRegressionConfiguration vartConfig;

	private ModelsFetcher mf;

	private String gotoProgramCompiled;

	private boolean noAssertionsCoverage = false;




	private static Logger LOGGER;
	
	
	{
		
		LOGGER   = Logger.getLogger(CBCMRegressionsDetector.class.getCanonicalName());
		
		String skipCBMCExecutionString = System.getProperty(BCT_SKIP_CBMC_EXECUTION);
		skipCBMCExecution = Boolean.parseBoolean(skipCBMCExecutionString); 


		String gotoProgramName = System.getProperty(BCT_GOTO_PROGRAM);
		if ( gotoProgramName != null ){
			gotoProgram = gotoProgramName;
		}

		gotoProgramCompiled = System.getProperty(BCT_GOTO_PROGRAM_COMPILED);
		if ( gotoProgramCompiled != null ){
			gotoProgram = gotoProgramCompiled;
			
			LOGGER.info("ATTENTION: when you manually compile with CBMC, assertions reachability cannot be considered");
			noAssertionsCoverage  = true;
			skipAssertionsInjection = true;
		}

		{
			String skipString = System.getProperty(BCT_SKIP_VALID_ANOMALIES_CHECK);
			skipInferenceAndCheck = Boolean.parseBoolean(skipString);	
		}
		
		{
			String skipString = System.getProperty(BCT_SKIP_PROCESSING_OF_ORIGINAL);
			skipProcessingOfOriginal = Boolean.parseBoolean(skipString);	
		}
		
		{
			String skipString = System.getProperty(BCT_SKIP_PROCESSING_OF_MODIFIED);
			skipProcessingOfModified = Boolean.parseBoolean(skipString);	
		}

		{
			String skipString = System.getProperty(BCT_SKIP_EXPORT_MODELS);
			skipExportModels = Boolean.parseBoolean(skipString);	
		}
	
		{
			String skipString = System.getProperty(BCT_CREATE_ONLY_ORIGINAL_WITH_ASSERTIONS);
			onlyAssertionsInOriginal = Boolean.parseBoolean(skipString);	
		}
		
		{
			String skipString = System.getProperty(BCT_FILTER_REDUNDANT_MODELS);
			filterRedundants = Boolean.parseBoolean(skipString);	
		}
		
		{
			String skipString = System.getProperty(BCT_SKIP_ASSERTIONS_INJECTION);
			skipAssertionsInjection = Boolean.parseBoolean(skipString);	
			
			
			LOGGER.info("ATTENTION: if you skip assertions injection, i.e. creating a new fresh copy of teh software with assertions in it, assertions reachability cannot be considered");
			noAssertionsCoverage  = true;
		}
		
		
	}


	public CBCMRegressionsDetector(VARTRegressionConfiguration c, ModelsFetcher mf) {
		vartConfig = c;
		this.mf = mf;
	}



	

	public Set<String> identifyRegressions(MonitoringConfiguration mrc)
			throws IOException, ModelsFetcherException, FileNotFoundException,
			ClassNotFoundException, ConfigurationFilesManagerException {

		initialize(mrc);


		List<BctModelViolation> validViolations;

		if ( ! skipInferenceAndCheck ){
			validViolations = identifyIntendedAnomalies();
		} else {
			LOGGER.info("Skipping checking of modified program runtime data");
			validViolations = new ArrayList<BctModelViolation>();
		}

		
		
		
		if ( ! skipExportModels ){
			exportModels();
		} else {
			LOGGER.info("Skipping exporting models");
		}
		
		
		if ( ! skipProcessingOfOriginal ) {
			LOGGER.info("Processing original sources");
			
			executeCBMC_Original();

			if ( onlyAssertionsInOriginal ){
				return null;
			}
			
			filterTruePropertiesAndIntendedAnomalies(validViolations);
		} else {
			LOGGER.info("Skipping the processing of original sources");
		}
		
		if ( skipProcessingOfModified ){
			LOGGER.info("Skipping the processing of upgraded sources");
			return null;
		}
		
		return executeCBMC_Modified();

	}

	public Set<String> executeCBMC_Modified() throws IOException,
			ClassNotFoundException, FileNotFoundException {
		String cbmcOutputModified = System.getProperty(BCT_CBMC_OUTPUT_MODIFIED);
	
		Map<String, ValidationResult> coverageResult = null;
		if ( cbmcOutputModified == null && executeAdditionalAssertionCoverageCheck() ){
			coverageResult = executeCBMC( true, modifiedSrc, allModelsV1Filtered, monitoredFunctionsV1File, gotoCCsrcFolderModified, null, null);
		}
		
		Map<String, ValidationResult> resultMap = executeCBMC( false, modifiedSrc, allModelsV1Filtered, monitoredFunctionsV1File, gotoCCsrcFolderModified, cbmcOutputModified, coverageResult);

		HashSet<String> violatedProperties = new HashSet<String>();
		for ( Entry<String,ValidationResult> entry : resultMap.entrySet() ){
			if ( ! ( entry.getValue() == ValidationResult.VALID ) ) {
				System.out.println(" "+entry.getKey()+" "+entry.getValue());
				violatedProperties.add(entry.getKey());
			}
		}

		return violatedProperties;
	}

	private boolean executeAdditionalAssertionCoverageCheck() {
		if ( noAssertionsCoverage ){
			return false;
		}
		
		return vartConfig.isCheckAssertionsCoverage() && ( vartConfig.getModelChecker() == ModelChecker.CBMC );
	}

	public void filterTruePropertiesAndIntendedAnomalies(
			List<BctModelViolation> validViolations) throws IOException,
			ClassNotFoundException, FileNotFoundException {
		CBMCModelsFilter.filterTrueProperties( allModelsV0, validatedFile, allModelsV1, allModelsV1TrueProperties, allModelsV0TrueProperties );

		CBMCModelsFilter.filterIntendedAnomalies(allModelsV1TrueProperties, allModelsV1Filtered, validViolations, originalFunctions, allModelsV0TrueProperties, allModelsV0Filtered, allModelsV1Outdated, allModelsV0Outdated );
		
		//create a version of V0 with only the true properties not violated by any test case (necessary to work with EvolCheck) 
		File gotoCCsrcFolderOriginalTrueFiltered = new File ( gotoCCsrcFolderOriginal.getAbsolutePath() + ".TRUE.FILTERED" );
		injectAssertions(false, originalSrc, allModelsV0Filtered, monitoredFunctionsFile, gotoCCsrcFolderOriginalTrueFiltered );
	}

	public void executeCBMC_Original() throws IOException,
			ClassNotFoundException, FileNotFoundException {
		String cbmcOutputOriginal = System.getProperty(BCT_CBMC_OUTPUT_ORIGINAL);
		
		Map<String, ValidationResult> coverageResults = null;
		
		if ( cbmcOutputOriginal == null && executeAdditionalAssertionCoverageCheck() ){
			coverageResults = executeCBMC(true,originalSrc, allModelsV0, monitoredFunctionsFile, gotoCCsrcFolderOriginal, null, null);
		}
		
		executeCBMC(false,originalSrc, allModelsV0, monitoredFunctionsFile, gotoCCsrcFolderOriginal, cbmcOutputOriginal, coverageResults);
	}

	public void exportModels() throws IOException, ModelsFetcherException,
			FileNotFoundException {
		LOGGER.info("Exporting models");
		
		CBMCModelsExporter exporter = new CBMCModelsExporter(SourceLinesMapper.createMapperFromFunctionData(
				originalSrc, 
				modifiedSrc, 
				originalFunctions,
				modifiedFunctions), 
				mf);
		exporter.setExportExitPoints(true);
		exporter.setExportEntryPoints(true);
		exporter.setFilterRedundants(filterRedundants);
		
		ModelFilter modelFilter = buildIncludeFilter();
		exporter.addAdditionalVariablesFilter(modelFilter);

		modelFilter = buildVarsFilter();
		exporter.addAdditionalVariablesFilter(modelFilter);

		String redefineMathFuncsString = System.getProperty(BCT_REDEFINE_MATH_FUNCS);
		if ( redefineMathFuncsString != null ){
			redefineMathFuncs = Boolean.parseBoolean( redefineMathFuncsString );
		}		
		
		String exportLinesString = System.getProperty(BCT_EXPORT_LINES);

		if ( exportLinesString != null ){
			vartConfig.setExportLines( Boolean.parseBoolean( exportLinesString ) );
		}

		LOGGER.fine("Export lines: "+vartConfig.getExportLines());
		LOGGER.fine("Unit verification: "+vartConfig.isUnitVerification());
		
		exporter.setExportLines(vartConfig.getExportLines());
		exporter.setUnitVerification( vartConfig.isUnitVerification() );

		String exportTargetOnlyString = System.getProperty(BCT_EXPORT_TARGETS_ONLY);
		if ( exportTargetOnlyString != null ){
			exportTargetOnly = Boolean.parseBoolean( exportTargetOnlyString );
		}
		
		if ( exportTargetOnly ){
			exporter.addAdditionalVariablesFilter( new TargetOnlyFilter() );
		}
		
		allModelsV0.getParentFile().mkdirs();

		exporter.setModelNamesMatchV0(false);
		
		exporter.exportModels(allModelsV0, allModelsV1);

		{	
			List<String> lines = FileUtil.getLines(allModelsV0);
			//			lines = lines.subList(0, 8);
			FileUtil.writeToTextFile(lines, allModelsV0);
		}
		{
			List<String> lines = FileUtil.getLines(allModelsV1);
			//			lines = lines.subList(0, 8);
			FileUtil.writeToTextFile(lines, allModelsV1);
		}
	}

	public List<BctModelViolation> identifyIntendedAnomalies()
			throws ConfigurationFilesManagerException {
		List<BctModelViolation> validViolations;
		LOGGER.info("Checking of modified program runtime data");
		
		RegressionAnalysisOutput violatedModels;

		try {
			violatedModels = RegressionAnalysis.identifyRegressions(mrc, null, false, false ,false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (CBEBctViolationsLogLoaderException e) {
			e.printStackTrace();
			return null;
		}
		validViolations = violatedModels.getViolationAnalysisResult().getFilteredViolations();
		return validViolations;
	}

	public void initialize(MonitoringConfiguration mrc)
			throws ConfigurationFilesManagerException {
		CRegressionConfiguration config;
		if ( mrc.getConfigurationType() != MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){

		}
		this.mrc = mrc;
		
		config = (CRegressionConfiguration) mrc.getAdditionalConfiguration(CRegressionConfiguration.class);

		originalSrc = new File ( config.getOriginalSwSourcesFolder() );
		modifiedSrc = new File ( config.getModifiedSwSourcesFolder() );


		bctHome = ConfigurationFilesManager.getBctHomeDir(mrc);
		allModelsV0 =  new File(bctHome+"/"+ConfigurationFilesManager.CBMC_VART_RESULTS_FOLDER+"/assertions-V0.txt"); 
		allModelsV1 =  new File(bctHome+"/"+ConfigurationFilesManager.CBMC_VART_RESULTS_FOLDER+"/assertions-V1.txt");

		LOGGER.info("Setting BCT_HOME to "+bctHome.getAbsolutePath()+" ("+bctHome+")");
		EnvironmentalSetter.setBctHome(bctHome.getAbsolutePath());
		
		try {
			originalFunctions = FunctionMonitoringDataSerializer.load(ConfigurationFilesManager.getMonitoredFunctionsDataFile(mrc));
			modifiedFunctions = FunctionMonitoringDataSerializer.load(ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mrc));
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
		
		
		monitoredFunctionsFile = ConfigurationFilesManager.getMonitoredFunctionsDataFile(mrc);
		gotoCCsrcFolderOriginal = ConfigurationFilesManager.getGotoCCSrcFolderOriginal( mrc );
		validatedFile = new File(allModelsV0.getAbsolutePath()+".validated");
		
		allModelsV0TrueProperties = new File(allModelsV0.getAbsolutePath()+".trueProperties");
		allModelsV1TrueProperties = new File(allModelsV1.getAbsolutePath()+".trueProperties");
		
		allModelsV0Filtered = new File(allModelsV0TrueProperties.getAbsolutePath()+".filtered");
		allModelsV1Filtered = new File(allModelsV1TrueProperties.getAbsolutePath()+".filtered");
		
		allModelsV0Outdated = new File(allModelsV0TrueProperties.getAbsolutePath()+".outdated");
		allModelsV1Outdated = new File(allModelsV1TrueProperties.getAbsolutePath()+".outdated");
		
		
		gotoCCsrcFolderModified = ConfigurationFilesManager.getGotoCCSrcFolderModified( mrc );


		monitoredFunctionsV1File = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mrc);


		
		
		String unwindString = System.getProperty(BCT_CBMC_UNWIND);

		if ( unwindString != null ){
			unwind = Integer.parseInt(unwindString);
		}
	}

	private ModelFilter buildIncludeFilter() {
		
		String varsToIncludeString = System.getProperty(BCT_EXPORTER_INCLUDE_ONLY);
		
		if ( varsToIncludeString != null ){
			if ( varsToInclude == null ){
				varsToInclude = varsToIncludeString;
			} else  {
				varsToInclude = varsToInclude+","+varsToIncludeString;
			}
			
		}
		
		if ( varsToInclude == null ){
			return null;
		}
		
		InclusionFilter filter = new InclusionFilter();
		
		StringTokenizer t = new StringTokenizer(varsToInclude, ",");
		while( t.hasMoreTokens() ){
			filter.addInclusionToken(t.nextToken().replace("&&", ":"));
		}
		
		
		return filter;
	}
	
	private ModelFilter buildVarsFilter() {
		
		String varsToExclude = System.getProperty(BCT_EXPORTER_VARS_TO_EXCLUDE);
		if ( varsToExclude == null ){
			return null;
		}
		
		VariableNamesFilter filter = new VariableNamesFilter();
		
		StringTokenizer t = new StringTokenizer(varsToExclude, ",");
		while( t.hasMoreTokens() ){
			filter.addVarToExclude(t.nextToken());
		}
		
		
		return filter;
	}

	public Map<String, ValidationResult> executeCBMC(boolean checkCoverage, File sourcesToCopy, File modelsFile,
			File monitoredFunctionsFile, File gotoCCsrcFolder, String cbmcOutput, Map<String, ValidationResult> coverageResults)
					throws IOException, ClassNotFoundException, FileNotFoundException {





		File gotoProgramFile = new File( gotoCCsrcFolder, gotoProgram );
		String gotoProgram = gotoProgramFile.getAbsolutePath();

		LOGGER.info("Initial gotoProgram "+gotoProgramFile.getAbsolutePath());
		
		
		CBMCExecutor executor = null;
		String gotoCCexecutable;
		
		if ( vartConfig.getModelChecker() == ModelChecker.EvolCheck ){
			executor = new EvolCheckExecutor();
			gotoCCexecutable = EnvUtil.getGotoCCProgram()+".evolcheck";
		} else {
			executor = new CBMCExecutor();
			gotoCCexecutable = EnvUtil.getGotoCCProgram();
		}
		
		executor.setUnwindN(unwind);
		executor.setGotoCCSourceFolder(gotoCCsrcFolder);

		String compileCommandProp = System.getProperty(CBCMRegressionsDetector.BCT_COMPILE_COMMAND);
		if ( compileCommandProp != null ){
			compileCommand = compileCommandProp;
		}
		
		
		GotoCC gotoCompiler = new GotoCC(compileCommand,gotoCCsrcFolder,gotoProgram);
		gotoCompiler.setGotoCCexecutable(gotoCCexecutable);
		gotoCompiler.setRedefineMathFuncs( redefineMathFuncs );
		
		if ( cbmcOutput != null ) {
			LOGGER.info("Setting cbmc output from file: "+ cbmcOutput );
			executor.setCBMCOutput( FileUtil.getLines(new File( cbmcOutput )) );
		} else {
			
			if  ( ! this.skipAssertionsInjection ){
				injectAssertions(checkCoverage, sourcesToCopy, modelsFile,
						monitoredFunctionsFile, gotoCCsrcFolder );
				
				skipAssertionsInjection = false; //it will be necessary to inject on Modified program
			}

			//			CBMCExecutor.main(new String[]{gotoProgram, allModelsV0.getAbsolutePath(), "main", ""+unwind});
		}




		Map<String, ValidationResult> resultMap = new HashMap<String, CBMCExecutor.ValidationResult>();
		
		if ( ! skipCBMCExecution ){
			
			
			
			LOGGER.info("Compiling with goto-cc "+ gotoCCsrcFolder.getAbsolutePath() );
			
			if ( gotoProgramCompiled == null ){
				//the goto program is updated when the user does not specify any compile-cmd and BCT uses a Makefile to build teh system with goto-cc
				String gotoProgramPath = gotoCompiler.compileWithGotoCC();
				gotoProgramFile = new File ( gotoProgramPath ); //updated only if necessary
			}
			
			LOGGER.info("Invoking CBMC on "+gotoProgramFile.getAbsolutePath());
			Map<String, CBMCClaim> resultMapClaims = executor.validate(gotoCompiler, gotoProgramFile, "main", modelsFile);
			LOGGER.info("Size of checked assertions: "+resultMap.size());
			for ( Entry<String, CBMCClaim>  e : resultMapClaims.entrySet() ){
				
				ValidationResult coverageResult = null;
				ValidationResult verificationResult = e.getValue().getResult();
				
				if ( coverageResults != null ){ 
					coverageResult = coverageResults.get(e.getKey());
					
					if ( coverageResult == ValidationResult.VALID ){
						//WAS NOT COVERED
						verificationResult = ValidationResult.UNREACHABLE;
						e.getValue().setResult(verificationResult); //Update also the claim result
					}
				}
				
				resultMap.put(e.getKey(), verificationResult );
			}
			
			CBMCExecutor.writeValidationResults(resultMapClaims, modelsFile);
		} else {
			LOGGER.info("Skipping CBMC invocation");
			resultMap = new HashMap<String, ValidationResult>();
		}

		return resultMap;

	}

	public void injectAssertions(boolean checkCoverage, File sourcesToCopy, File modelsFile,
			File monitoredFunctionsFile, File gotoCCsrcFolder ) throws IOException, ClassNotFoundException,
			FileNotFoundException {
		
		LOGGER.info("Copying sources from " + sourcesToCopy.getAbsolutePath() + " to "+ gotoCCsrcFolder.getAbsolutePath() +" and injecting assertions specified in file "+modelsFile.getAbsolutePath() );


		CBMCAssertionsInjector injector = CBMCAssertionsInjector.buildAssertionsInjector( sourcesToCopy, 
				modelsFile, 
				gotoCCsrcFolder, 
				monitoredFunctionsFile );
		
		injector.setUnitVerification( vartConfig.isUnitVerification() );
		injector.setRedefineMathFuncs( redefineMathFuncs );
		injector.setCheckCoverage( checkCoverage );
		
		injector.setUseSimmetricIntegers(vartConfig.isUseSimmetricIntegers());
		
		if ( exportTargetOnly ){
			injector.setEntryPointsType(EntryPointsTypes.Targets);
		} else {
			injector.setEntryPointsType(EntryPointsTypes.CallersOfTargets);
		}

		injector.injectAssertions(modelsFile);
		
		


		

		
	}

	public boolean isExportLines() {
		return vartConfig.getExportLines();
	}

//	public void setExportLines(boolean exportLines) {
//		this.exportLines = exportLines;
//	}


	
	


	public void setGotoProgram(String gotoProgram) {
		this.gotoProgram = gotoProgram;
	}

	public void setCompileCommand(String compileCommand) {
		this.compileCommand = compileCommand;
	}



//	public void setUnitVerification(boolean b) {
//		unitVerification = b;
//	}

	public void setProcessPointers(boolean b) {
		processPointers = b;
	}

	public void setGotoCompiler(String compilerExecutable) {
		gotoCompiler = compilerExecutable;
	}

	public void setExecutable(String cbmcExecutable) {
		this.cbmcExecutable = cbmcExecutable;
	}

	public boolean isExportTargetOnly() {
		return exportTargetOnly;
	}

	public void setExportTargetOnly(boolean exportTargetOnly) {
		this.exportTargetOnly = exportTargetOnly;
	}

	public boolean isRedefineMathFuncs() {
		return redefineMathFuncs;
	}

	public void setRedefineMathFuncs(boolean redefineMathFuncs) {
		this.redefineMathFuncs = redefineMathFuncs;
	}

	public String getVarsToInclude() {
		return varsToInclude;
	}

	public void setVarsToInclude(String varsToInclude) {
		this.varsToInclude = varsToInclude;
	}

	public int getUnwind() {
		return unwind;
	}

	public void setUnwind(int unwind) {
		this.unwind = unwind;
	}


}
