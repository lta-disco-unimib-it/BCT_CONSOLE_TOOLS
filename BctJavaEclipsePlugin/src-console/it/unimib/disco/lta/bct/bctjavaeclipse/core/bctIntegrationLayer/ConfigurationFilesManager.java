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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.AdditionalInferenceOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.DBStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourcesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.ProbeExporterUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import modelsFetchers.DBModelsFetcher;
import modelsFetchers.DaikonFileModelsFetcher;
import modelsFetchers.FileModelsFetcher;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.sun.org.apache.xpath.internal.operations.Bool;

import recorders.BufferedRecorder;
import recorders.DBDataRecorder;
import recorders.FileDataRecorder;
import recorders.FileOptimizedDataRecorder;
import tools.fshellExporter.FShellModelsExporter;
import traceReaders.metaData.ExecutionMetaDataHandlerSettings;
import util.componentsDeclaration.Component;
import conf.EnvironmentalSetter;
import conf.FineInteractionCheckerSettings;
import conf.InvariantGeneratorSettings;
import cpp.gdb.EnvUtil;
import cpp.gdb.GdbRegressionConfigCreator;
import executionContext.ActionsRegistry;
import executionContext.ExecutionContextStateMaintainerMemory;
import flattener.flatteners.BreadthObjectFlattener;
import flattener.utilities.FieldFilter;
import flattener.utilities.FieldFilterExporter;

public class ConfigurationFilesManager {

	public static class Files {
		public static final String flattenerPropertiesFileName = "objectFlattener.conf";
		public static final String fieldsFilterFileName = "FieldsFilters.properties";
		public static final String classesToIgnoreFileName = "classesToIgnore.list";
		public static final String interactionCheckerPropertiesFileName = "InteractionCheckerSettings.properties";
		public static final String invariantGeneratorPropertiesFileName = "InvariantGenerator.properties";
		public static final String inferenceEnginePropertiesFileName = "InferenceEngine.properties";
		public static final String bctPropertiesFileName = "BCT.properties";
		public static final String dataRecorderSettingsFileName = "DataRecorderSettings.properties";
		public static final String modelsFetcherSettingsFileName = "ModelsFetcherSettings.properties";
		public static final String violationsRecorderSettingsFileName = "ViolationsRecorderSettings.properties";
		public static final String actionsRegistrySettingsFileName = "ActionsRegistrySettings.properties";
		public static final String testCasesRegistrySettingsFileName = "TestCasesRegistrySettings.properties";
		public static final String dbConnectionSettingsFileName = "DBConnectionSettings.properties";
		
		public static final String dataRecordingDirName = "DataRecording";
		public static final String modelsDirName = "Models";
		public static final String scriptDirName="scripts";
		public static final String preprocessingDirName = "Preprocessing";
		public static final String tmpDirName = "tmp";
		
		public static final String runtimeCheckingProbeName = "bctCP.probescript";
		public static final String dataRecordingProbeName = "bctLP.probescript";
		
		public static final String bctCBELogFileName = "bctCBELog";
		public static final String bctViolationsLogAnalysisFolder = "ViolationsLogAnalysis";
		public static final String bctInferenceLogAnalysisFolder = "InferenceLogAnalysis";
		
		public static final String invariantGeneratorTestsToIgnoreFileName = "testsToIgnore.properties";
		public static final String invariantGeneratorActionsToIgnoreFileName = "actionsToIgnore.properties";
		public static final String invariantGeneratorProcessesToIgnoreFileName = "processesToIgnore.properties";
		
		public static final String originalSoftwareDump = "originalSoftware.objdump";
		public static final String modifiedSoftwareDump = "modifiedSoftware.objdump";
		
		public static final String originalSoftwareGdbConfig = "originalSoftware.gdb.config.txt";
		public static final String modifiedSoftwareGdbConfig = "modifiedSoftware.gdb.config.txt";
		public static final String originalSoftwareGdbConfigToVerify = "originalSoftware.gdb.config.toVerify.txt";
		
		public static final String originalSoftwarePinConfig = "originalSoftware.gdb.config.txt.probes";
		public static final String modifiedSoftwarePinConfig = "modifiedSoftware.gdb.config.txt.probes";
		
		
		public static final String validTracesFolder = "validTraces";
		public static final String tracesToVerifyFolder = "tracesToVerify";
		
		public static final String oldConfigurationFile = "recorded.bctmc.cache";
		
		
	}
	
	private static class BctProperties {
		
		private static final String defaultFlattenerType = BreadthObjectFlattener.class.getCanonicalName();
	}

	private static final String CBMC_SRC_FOLDER_ORIGINAL = "VART.SRC.ORIGINAL";
	private static final String CBMC_SRC_FOLDER_MODIFIED = "VART.SRC.MODIFIED";
	public static final String CBMC_VART_RESULTS_FOLDER = "VART";
	
	private static final String CBMC_VART_NRP_INJECTED = "assertions-V1.txt.trueProperties.filtered.injected";
	private static final String CBMC_VART_NRP_INJECTED_LINES = "assertions-V1.txt.trueProperties.filtered.injected.lines";
	
	private static final String CBMC_VART_NRP_VERIFICATION = "assertions-V1.txt.trueProperties.filtered.validated";
	private static final String CBMC_VART_DP_VERIFICATION = "assertions-V0.txt.validated";
	private static final String CBMC_VART_OUTDATED_PROPERTIES = "assertions-V1.txt.trueProperties.outdated";
	
	public static File getGdbValidTrace(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getValidTracesFolder(mc), GdbRegressionConfigCreator.Configuration.DEFAULT_LOGGNG_FILE_NAME );
	}

	public static File getGdbTraceToVerify(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getTracesToVerifyFolder(mc), GdbRegressionConfigCreator.Configuration.DEFAULT_LOGGNG_FILE_NAME );
	}
	
	public static File getValidTracesFolder(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), Files.validTracesFolder );
	}

	public static File getTracesToVerifyFolder(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), Files.tracesToVerifyFolder );
	}
	
	
	public static File getModifiedSoftwareGdbMonitoringConfig(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getScriptsDir(mc);
		return new File ( dir, Files.modifiedSoftwareGdbConfig );
	}
	
	public static File getOriginalSoftwareGdbMonitoringConfig(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getScriptsDir(mc);
		return new File ( dir, Files.originalSoftwareGdbConfig );
	}
	
	public static File getOriginalSoftwarePinMonitoringDir(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getScriptsDir(mc);
		return new File ( dir, Files.originalSoftwarePinConfig );
	}
	
	public static File getModifiedSoftwarePinMonitoringDir(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getScriptsDir(mc);
		return new File ( dir, Files.modifiedSoftwarePinConfig );
	}
	
	public static File getOriginalSoftwarePinMonitoringProbe(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getOriginalSoftwarePinMonitoringDir(mc);
		return new File ( dir, "bdciProbe.so" );
	}
	
	public static File getModifiedSoftwarePinMonitoringProbe(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getModifiedSoftwarePinMonitoringDir(mc);
		return new File ( dir, "bdciProbe.so" );
	}
	
	public static File getOriginalSoftwareGdbMonitoringConfigToVerify(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File dir = getScriptsDir(mc);
		return new File ( dir, Files.originalSoftwareGdbConfigToVerify );
	}
	
	public static File getOriginalSoftwareDumpFile( MonitoringConfiguration mrc ) throws ConfigurationFilesManagerException{
		File dir = getScriptsDir(mrc);
		return new File ( dir, Files.originalSoftwareDump );
	}
	
	
	public static File getModifiedSoftwareDumpFile( MonitoringConfiguration mrc ) throws ConfigurationFilesManagerException{
		File dir = getScriptsDir(mrc);
		return new File ( dir, Files.modifiedSoftwareDump );
	}
	
	/**
	 * Return the BCT_HOME folder for the given MonitoringCOnfiguration
	 * 
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException
	 */
	public static File getBctHomeDir( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException {
		try {
			StorageConfiguration storageConfig = mc.getStorageConfiguration();
			if ( storageConfig instanceof FileStorageConfiguration ){
//				return new File ( ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile().getAbsolutePath() + File.separator + ((FileStorageConfiguration)storageConfig).getDataDirPath() );
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( new Path  ( ((FileStorageConfiguration)storageConfig).getDataDirPath() ) );
				return file.getLocation().toFile();
			}
			return new File ( DefaultOptionsManager.getBctDataProject().getLocation().toFile(), mc.getConfigurationName() );
		} catch (CoreException e) {
			throw new ConfigurationFilesManagerException(e);
		}
	}
	
	/**
	 * Return the directory containing the BCT configuration files for a MonitoringCOnfiguration 
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException
	 */
	public static File getConfigurationFilesDir( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		File bctHome;
		try {
			bctHome = getBctHomeDir(mc);
			return new File(bctHome+File.separator+"conf"+File.separator+"files");
		} catch (ConfigurationFilesManagerException e) {
			throw new ConfigurationFilesManagerException(e);
		}
		
	}
	
	/**
	 * Updates the bct properties files used by BCT during its runtime phases (Data Recording, Model Inference, Runtime Checking).
	 * The files are usually created under BCT_DATA/<monitoring_configuration_name>/conf/file.
	 * 
	 * @param mc
	 * @throws ConfigurationFilesManagerException
	 */
	public static void updateConfigurationFiles( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{

		//TODO: check if mc was changed, otherwise do not create files

		File confDir = getConfigurationFilesDir(mc);

		if ( ! confDir.exists() ){
			boolean result = confDir.mkdirs();
			if ( ! result ){
				throw new ConfigurationFilesManagerException("Cannot create configuration files directory "+confDir.getAbsolutePath());
			}
		}
		try{
			saveBCTProperties(confDir);

			saveDataRecorderSettings(mc, confDir);

			if ( mc.getStorageConfiguration().getConfigurationType() == StorageConfiguration.ConfigurationType.DB ){
				saveDBConnectionSettings(mc, confDir);
			}

			saveActionsManagerSettings(confDir);

			saveTestCasesRegistrySettings(mc, confDir);

			saveModelsFetcherSettings(mc, confDir );

			saveViolationsRecorderSettings(mc,confDir);

			saveInvariantGeneratorSettings(mc,confDir);

			saveInferenceEngineSettings(mc,confDir);

			saveObjectFlattenerOptions(mc,confDir);

			saveDaikonConfigOptions(mc,confDir);

			saveInteractionCheckerSettings(mc,confDir);

			

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DefaultOptionsManager.getBctDataProject().touch(new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}


	}
	
	
	public static void updatRegressionComponentsToMonitor ( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		try {
			if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ||  mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config ){

				CRegressionConfiguration regressionConfiguration  = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
				ArrayList<Component> components = CRegressionAnalysisUtil.getComponentsToMonitor( mc, regressionConfiguration );
				ComponentsConfiguration componentsConfiguration = new ComponentsConfiguration(mc.getConfigurationName(), new ArrayList<CallFilter>(), components, true );
				mc.setComponentsConfiguration(componentsConfiguration);
				

			} 

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationFilesManagerException(e);
		}
	}
	
	public static void updateMonitoringScripts( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		try {
			if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ||  mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config){
				CRegressionAnalysisUtil.createStaticFunctionsFile( mc );
				CRegressionAnalysisUtil.createGdbMonitoringConfigurations( mc, CRegressionConfiguration.class );
				CRegressionAnalysisUtil.createTracesFolders( mc );
			} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
				CRegressionAnalysisUtil.createObjdumpFile(mc);
				CRegressionAnalysisUtil.createStaticFunctionsFile( mc );
				CRegressionAnalysisUtil.createGdbMonitoringConfigurations( mc, CConfiguration.class );
				CRegressionAnalysisUtil.createTracesFolders( mc );
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigurationFilesManagerException(e);
		}
	}

	public static void storeAsPreviousConfiguration(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File mcFile = getPreviousConfigurationFile(mc);
		try {
			MonitoringConfigurationSerializer.serialize(mcFile, mc);
		} catch (FileNotFoundException e) {
			throw new ConfigurationFilesManagerException(e);
		}
	}
	
	
	public static MonitoringConfiguration getPreviousConfiguration(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File mcFile = getPreviousConfigurationFile(mc);
		
		if ( ! mcFile.exists() ){
			return null;
		}
		
		try {
			return MonitoringConfigurationDeserializer.deserialize(mcFile);
		} catch (FileNotFoundException e) {
			throw new ConfigurationFilesManagerException(e);
		}
	}
	
	private static File getPreviousConfigurationFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File confDir = getConfigurationFilesDir(mc);
		return new File ( confDir , Files.oldConfigurationFile );
	}

	private static void saveInteractionCheckerSettings(
			MonitoringConfiguration mc, File confDir) throws ConfigurationFilesManagerException {
		Properties p = createInteractionCheckerSettings(mc);
		saveProperty(p, confDir, Files.interactionCheckerPropertiesFileName );
	}

	private static Properties createInteractionCheckerSettings(
			MonitoringConfiguration mc) {
		Properties p = mc.getInteractionCheckerOptions();
		
		if ( p == null ){ //TODO: add in the gui a page for these settings
			
			FineInteractionCheckerSettings s = new FineInteractionCheckerSettings();
			s.setAnomalousSequencesRecordingEnabled(true);
			s.setAvaPathLen(2);
			s.setDfa(false);
			s.setFineAnalysisEnabled(false);
			p=s.toProperties();
		}
		
		return p;
	}

	private static void saveActionsManagerSettings(File confDir) throws ConfigurationFilesManagerException {
		Properties p = createActionsManagerOptionsObject();
		
		saveProperty(p, confDir, Files.actionsRegistrySettingsFileName );
	}

	private static void saveTestCasesRegistrySettings(MonitoringConfiguration mc,File confDir) throws ConfigurationFilesManagerException {
		Properties p = createTestCasesRegistryOptionsObject(mc);
		
		saveProperty(p, confDir, Files.testCasesRegistrySettingsFileName );
	}
	
	private static Properties createActionsManagerOptionsObject() {
		Properties p = new Properties();
		
		p.setProperty("type", ActionsRegistry.class.getCanonicalName());
		p.setProperty("stateRecorderType", ExecutionContextStateMaintainerMemory.class.getCanonicalName());
		return p;
	}

	private static Properties createTestCasesRegistryOptionsObject(
			MonitoringConfiguration mc) {
		
		TestCasesMonitoringOptions tcm = mc.getTestCasesMonitoringOptions();
		
		if ( tcm == null ){
			return new Properties();
		}
		
		return tcm.getTestCasesRegistryOptions().getSettings(mc).getProperties();
//		Properties p = new Properties();
//		
//		p.setProperty("type", TestCasesRegistry.class.getCanonicalName());
//		p.setProperty("stateRecorderType", ExecutionContextStateMaintainerMemory.class.getCanonicalName());
//		return p;
	}
	
	private static void saveDaikonConfigOptions(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		 Properties invOpts;
		try {
			invOpts = mc.getInvariantGeneratorOptions();
			if ( invOpts == null ){
				return;
			}
			
			conf.management.ConfigurationFilesManager.createDaikonConfigFiles(confDir);
			
			String daikonConfig = invOpts.getProperty(InvariantGeneratorSettings.Options.daikonConfig);
			Properties properties = DefaultOptionsManager.getDefaultOptions().getDaikonConfigProperties(daikonConfig);
			saveProperty(properties, confDir, daikonConfig+".txt", false );
			
			
		} catch (BctDefaultOptionsException e) {
			throw new ConfigurationFilesManagerException(e);
		} catch (CoreException e) {
			throw new ConfigurationFilesManagerException(e);
		} catch (DefaultOptionsManagerException e) {
			throw new ConfigurationFilesManagerException(e);
		} catch (conf.management.ConfigurationFilesManagerException e) {
			throw new ConfigurationFilesManagerException(e);
		}
	}

	private static void saveObjectFlattenerOptions(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		saveFlattenerProperties(mc,confDir);
		
		saveFieldsFilter(mc,confDir);
		
		saveClassesToIgnore(mc,confDir);
	}

	private static void saveClassesToIgnore(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		Properties p = createClassesToIgnorePropertiesObject(mc); 
		saveProperty(p, confDir, Files.classesToIgnoreFileName );
	}

	private static Properties createClassesToIgnorePropertiesObject(
			MonitoringConfiguration mc) {
		Properties p = new Properties();
		
		
		FlattenerOptions fo = mc.getFlattenerOptions(); 
		
		if ( fo == null){
			return p;
		}
		
		List<String> toIgnore = fo.getClassToIgnore();
		for ( String classToIgnore : toIgnore ){
			p.setProperty(classToIgnore, "true");
		}
		return p;
	}

	private static void saveFieldsFilter(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		
		FlattenerOptions fo = mc.getFlattenerOptions();
		
		if ( fo == null ){
			return;
		}
		
		FieldFilter ff = fo.getFieldsFilter();
		File dest = new File( confDir , Files.fieldsFilterFileName );
		try {
			FieldFilterExporter.export(ff, dest);
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException(e);
		}
		
	}

	private static void saveFlattenerProperties(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		Properties p = createFlattenerPropertiesObject(mc,confDir);
		saveProperty(p, confDir, Files.flattenerPropertiesFileName );
	}

	private static Properties createFlattenerPropertiesObject(
			MonitoringConfiguration mc, File confDir) {
		Properties p = new Properties();
		
		FlattenerOptions fo = mc.getFlattenerOptions();
		
		if ( fo == null ){
			return p;
		}
		p.setProperty("objectFlattener.skipObjectsAlreadyVisited", String.valueOf(fo.isSkipObjectsAlreadyVisited()) );
		p.setProperty("objectFlattener.smashAggregations", String.valueOf(fo.isSmashAggregation()) );
		p.setProperty("objectFlattener.maxDepth", String.valueOf(fo.getMaxDepth()) );
		p.setProperty("fieldsRetrieverConfig", fo.getFieldRetriever().toLowerCase() );
		p.setProperty("classesToIgnore.load", fo.getClassToIgnore().size() > 0 ? "true" : "false" );
		
		return p;
	}

	private static void saveInferenceEngineSettings(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		Properties p = createInferenceEngineSettingsObject(mc);
		saveProperty(p, confDir, Files.inferenceEnginePropertiesFileName );
	}

	private static Properties createInferenceEngineSettingsObject(
			MonitoringConfiguration mc) {
		
		Properties opts = mc.getFsaEngineOptions();
		if ( opts == null ){
			return new Properties();
		}
		
		return opts;

			

		
		
	}

	private static void saveInvariantGeneratorSettings(
			MonitoringConfiguration mc, File confDir) throws ConfigurationFilesManagerException {
		Properties p = createInvariantGeneratorSettingsObject(mc);
		System.out.println(p);
		
		
		saveInvariantGeneratorAdditionalOptions(mc, p, confDir );
		
		saveProperty(p, confDir, Files.invariantGeneratorPropertiesFileName );
		
		
	}

	private static void saveInvariantGeneratorAdditionalOptions(MonitoringConfiguration mc, Properties p,
			File confDir) throws ConfigurationFilesManagerException {
		
		System.out.println("Saving additional invariant generator options");
		
		AdditionalInferenceOptions opts = mc.getAdditionalInferenceOptions();
		
		if ( opts == null ){
			return;
		}
		
		{
			Properties toSave = new Properties();
			for ( String el : opts.getTestCasesToIgnore() ){
				toSave.put(el, "true");
			}

			saveProperty(toSave, confDir, Files.invariantGeneratorTestsToIgnoreFileName );
			
			p.put(InvariantGeneratorSettings.Options.testsToIgnoreFile, "%%BCT_HOME%%/conf/files/"+Files.invariantGeneratorTestsToIgnoreFileName);
		}
		
		
		{
			Properties toSave = new Properties();
			for ( String el : opts.getActionsToIgnore() ){
				toSave.put(el, "true");
			}

			saveProperty(toSave, confDir, Files.invariantGeneratorActionsToIgnoreFileName );
			
			
			p.put(InvariantGeneratorSettings.Options.actionsToIgnoreFile, "%%BCT_HOME%%/conf/files/"+Files.invariantGeneratorActionsToIgnoreFileName);
		}
		
		
		
		{
			Properties toSave = new Properties();
			for ( String el : opts.getProcessesToIgnore() ){
				toSave.put(el, "true");
			}

			saveProperty(toSave, confDir, Files.invariantGeneratorProcessesToIgnoreFileName );
			
			p.put(InvariantGeneratorSettings.Options.processesToIgnoreFile, "%%BCT_HOME%%/conf/files/"+Files.invariantGeneratorProcessesToIgnoreFileName);
		}
		
		if ( opts.getInvertFiltering() ){
			p.put(InvariantGeneratorSettings.Options.invertFiltering, "true");
		}
		
	}

	private static Properties createInvariantGeneratorSettingsObject(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		
		
		Properties opts = mc.getInvariantGeneratorOptions();
		
		if ( opts == null ){
			opts = new Properties();
		}
		
//		if ( ! opts.containsKey(InvariantGeneratorSettings.Options.excludeConstantLikeVariables) ){
//			opts.put(InvariantGeneratorSettings.Options.excludeConstantLikeVariables, "true");
//		}
//		
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.JavaMonitoring ){
			if ( ! opts.containsKey(InvariantGeneratorSettings.Options.metaDataHandlerSettingsType) ){
				opts.put(InvariantGeneratorSettings.Options.metaDataHandlerSettingsType, ExecutionMetaDataHandlerSettings.class.getCanonicalName());
			}
		}
		
		return opts;


	}

	private static void saveViolationsRecorderSettings(
			MonitoringConfiguration mc, File confDir) throws ConfigurationFilesManagerException {
		Properties p = createViolationsRecorderSettingsObject(mc);
		saveProperty(p, confDir, Files.violationsRecorderSettingsFileName );
	}

	private static Properties createViolationsRecorderSettingsObject(
			MonitoringConfiguration mc) {


		Properties opts = mc.getViolationsRecorderOptions();
		if ( opts == null ){
			return new Properties();
		}
		
		
		return opts;

		
	}

	private static void saveModelsFetcherSettings(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		Properties p = createModelsFetcherSettingsObject(mc);
		saveProperty(p, confDir, Files.modelsFetcherSettingsFileName );
	}

	private static Properties createModelsFetcherSettingsObject(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		Properties p = new Properties();
		
		StorageConfiguration sc = mc.getStorageConfiguration();
		if ( sc.getConfigurationType() == StorageConfiguration.ConfigurationType.FILE ){
			FileStorageConfiguration fsc = (FileStorageConfiguration) mc.getStorageConfiguration();
			p.setProperty("type", DaikonFileModelsFetcher.class.getCanonicalName() );
			
			
			
			p.setProperty(FileModelsFetcher.Options.modelsDir,  DefaultOptionsManager.Options.bctHomeReplacementKey +"/"+ Files.modelsDirName);
			//p.setProperty(FileModelsFetcher.Options.exportFormat,  "ser");
			
			p.setProperty(FileModelsFetcher.Options.exportFormat,  "fsa");
			
		} else if ( sc.getConfigurationType() == StorageConfiguration.ConfigurationType.DB ) {
			p.setProperty("type", DBModelsFetcher.class.getCanonicalName() );
		} else {
			throw new ConfigurationFilesManagerException("Unrecognized storage configuration type");
		}
		return p;
	}

	private static void saveDBConnectionSettings(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		Properties p = createDBConnectionSettingsObject(mc);
		saveProperty(p, confDir, Files.dbConnectionSettingsFileName );
	}

	private static Properties createDBConnectionSettingsObject(
			MonitoringConfiguration mc) {
		Properties p = new Properties();
		
		DBStorageConfiguration dbsc = (DBStorageConfiguration) mc.getStorageConfiguration();
		p.setProperty("databaseURI", dbsc.getUri());
		p.setProperty("databaseUSER", dbsc.getUser());
		p.setProperty("databasePASSWORD", dbsc.getPassword());
		
		return p;
	}

	private static void saveDataRecorderSettings(MonitoringConfiguration mc,
			File confDir) throws ConfigurationFilesManagerException {
		Properties p = createDataRecorderSettingsObject(mc);
		saveProperty(p, confDir, Files.dataRecorderSettingsFileName);
	}

	private static Properties createDataRecorderSettingsObject(MonitoringConfiguration mc) {
		Properties p = new Properties();
		
		
		StorageConfiguration sc = mc.getStorageConfiguration();
		if ( sc.getConfigurationType() == StorageConfiguration.ConfigurationType.FILE ) {
			String optDr = System.getProperty("bct.optimizedDataRecorder");
			if ( optDr != null && !Boolean.parseBoolean(optDr)  ){
				p.setProperty("type", FileDataRecorder.class.getCanonicalName());
			} else {
				p.setProperty("type", BufferedRecorder.class.getCanonicalName());
				p.setProperty("optimizedDataRecorder", FileOptimizedDataRecorder.class.getCanonicalName());
			}
			
			FileStorageConfiguration scf = (FileStorageConfiguration) sc;
			IFolder dataDir = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(scf.getDataDirPath()) );
			
			
			p.setProperty(FileDataRecorder.Options.loggingDataDir, DefaultOptionsManager.Options.bctHomeReplacementKey +"/"+ Files.dataRecordingDirName );
			
		} else {
			p.setProperty("type", DBDataRecorder.class.getCanonicalName() );
			
			
		}
		
		return p;
	}

	/**
	 * Create the BCT.properties file
	 * @param mc
	 * @param confDir
	 * @throws ConfigurationFilesManagerException
	 */
	private static void saveBCTProperties(File confDir) throws ConfigurationFilesManagerException {
		Properties p = createBCTPropertiesObject();
		saveProperty ( p, confDir, Files.bctPropertiesFileName);
	}

	private static void saveProperty(Properties p, File destDir, String fileName) throws ConfigurationFilesManagerException {
		saveProperty(p, destDir, fileName, true);
	}
	
	private static void saveProperty(Properties p, File destDir, String fileName, boolean force) throws ConfigurationFilesManagerException {
		FileOutputStream fos = null;
		try {
			
			File dest = new File(destDir, fileName);
			
			if ( dest.exists() ){
				if ( ! force ){
					return;
				}
			}
			
			fos = new FileOutputStream( dest );
			
			StackTraceElement[] st = Thread.currentThread().getStackTrace();
			String pos = " "+st[1].getFileName()+""+st[1].getLineNumber()+" , "+st[2].getLineNumber()+" , "+st[3].getLineNumber();
			
			p.store(fos, "Auto generated by class "+ConfigurationFilesManager.class.getCanonicalName()+pos);
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException(e);
		} finally {
			if ( fos != null ){
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Creates the default properties object
	 * 
	 * @return
	 */
	private static Properties createBCTPropertiesObject() {
		Properties p = new Properties();
		
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.flattenerType, BctProperties.defaultFlattenerType);
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.dataRecorderSettings, Files.dataRecorderSettingsFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.interactionInferenceEngineSettings, Files.inferenceEnginePropertiesFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.invariantGeneratorSettings, Files.invariantGeneratorPropertiesFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.interactionCheckerSettings, Files.interactionCheckerPropertiesFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.modelsFetcherSettings, Files.modelsFetcherSettingsFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.violationsRecorderSettings, Files.violationsRecorderSettingsFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.actionsRegistrySettings, Files.actionsRegistrySettingsFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.testCasesRegistrySettings, Files.testCasesRegistrySettingsFileName );
		p.setProperty(EnvironmentalSetter.BctPropertiesOptions.dbConnectionSettings, Files.dbConnectionSettingsFileName );
		
		return p;
	}

	public static Properties loadInvariantGeneratorOptions(File defaultOptionsDir) throws ConfigurationFilesManagerException {
		Properties p = new Properties();
		FileInputStream inStream;
		try {
			System.out.println("LOADING");
			inStream = new FileInputStream( new File(defaultOptionsDir,Files.invariantGeneratorPropertiesFileName) );
			p.load(inStream);
			System.out.println("LOADED");
		} catch (FileNotFoundException e) {
			throw new ConfigurationFilesManagerException("Cannot load invariant generator properties ",e);
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException("Cannot load invariant generator properties ",e);
		}
		return p;
	}

	public static Properties loadInferenceEngineOptionsFromFile(File inputFile) throws ConfigurationFilesManagerException {
		Properties p = new Properties();
		FileInputStream inStream;
		try {
			inStream = new FileInputStream( inputFile );
			p.load(inStream);
		} catch (FileNotFoundException e) {
			throw new ConfigurationFilesManagerException("Cannot load inference negine properties ",e);
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException("Cannot load inference engine properties ",e);
		}
		return p;
	}

	public static void saveInvariantGeneratorOptions( File defaultOptionsDir,
			Properties invariantGeneratorOptions) throws ConfigurationFilesManagerException {
		FileOutputStream os;
		try {
			os = new FileOutputStream( new File(defaultOptionsDir,Files.invariantGeneratorPropertiesFileName) );
			invariantGeneratorOptions.store(os, "");
		} catch (FileNotFoundException e) {
			throw new ConfigurationFilesManagerException("Cannot save invarinat generator properties ",e);
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException("Cannot save invarinat generator properties ",e);
		}
		
	}
	
	public static void saveInferenceEngineOptionsToFile( File dest,
			Properties inferenceEngineOptions) throws ConfigurationFilesManagerException {
		FileOutputStream os;
		try {
			os = new FileOutputStream( dest );
			inferenceEngineOptions.store(os, "");
		} catch (FileNotFoundException e) {
			throw new ConfigurationFilesManagerException("Cannot save inference engine properties ",e);
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException("Cannot save inference engine properties ",e);
		}
		
	}

	public static boolean isMonitoringConfiguration(File selectedFile) {
		return selectedFile.getName().endsWith(DefaultOptionsManager.getMonitoringConfigurationFileExtension());
	}

	/**
	 * Return the data recording script dir where to store scripts
	 * @param mc
	 * @return 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static File getScriptsDir(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File bctHome = getConfigurationFilesDir(mc);
		File scriptsDir = new File(bctHome,Files.scriptDirName);
		if ( ! scriptsDir.exists() ){
			scriptsDir.mkdirs();
		}
		return scriptsDir;
	}

	public static File getDataRecordingProbeScript(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File scriptsDir = getScriptsDir(mc);
		System.out.println(mc);
		ComponentsConfiguration cc = mc.getComponentsConfiguration();
		System.out.println(cc);
		File dest = new File ( scriptsDir, Files.dataRecordingProbeName );
		
		if ( dest.exists() ){
			if ( ! dest.delete() ){
				throw new ConfigurationFilesManagerException("Cannor remove "+dest.getAbsolutePath());
			}
		}
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(dest));
		
			
		
		if ( mc.getActionsMonitoringOptions().isMonitorActions() == true ){
			ProbeExporterUtil.createMonitorActionsProbeScript(bw, mc.getActionsMonitoringOptions());
		}
		
		TestCasesMonitoringOptions tcmo = mc.getTestCasesMonitoringOptions();
		if ( tcmo.isMonitorTestCases() == true ){
			
			if ( cc.getMonitorInternalCalls() ){
				if ( tcmo.isTraceTestExecution() ){
					ProbeExporterUtil.createDataRecordingProbeScriptMonitorInternalAndTests(bw, cc);
				} else {
					ProbeExporterUtil.createDataRecordingProbeScriptMonitorInternal(bw, cc);
				}
			} else {
				if ( tcmo.isTraceTestExecution() ){
					ProbeExporterUtil.createDataRecordingProbeScriptMonitorTests(bw, cc);
				} else {
					ProbeExporterUtil.createDataRecordingProbeScript(bw, cc);
				}
			}
			
			ProbeExporterUtil.createMonitorTestCasesProbeScript(bw, mc.getTestCasesMonitoringOptions());
			
			
		} else {

			if ( cc.getMonitorInternalCalls() ){
				ProbeExporterUtil.createDataRecordingProbeScriptMonitorInternal(bw, cc);
			} else {
				ProbeExporterUtil.createDataRecordingProbeScript(bw, cc);
			}
		}
		
		bw.close();
		
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException("Cannot create probeScript "+dest.getAbsolutePath(),e);
		}
		
		return dest;
	}
	
	public static File getRuntimeCheckingProbeScript(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File scriptsDir = getScriptsDir(mc);
		ComponentsConfiguration cc = mc.getComponentsConfiguration();
		File dest = new File ( scriptsDir, Files.runtimeCheckingProbeName );

		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(dest));


			if ( mc.getActionsMonitoringOptions().isMonitorActions() == true ){
				ProbeExporterUtil.createMonitorActionsProbeScript(bw, mc.getActionsMonitoringOptions());
			}
			
			if ( mc.getTestCasesMonitoringOptions().isMonitorTestCases() == true ){
				ProbeExporterUtil.createMonitorTestCasesProbeScript(bw, mc.getTestCasesMonitoringOptions());
			}

			if ( cc.getMonitorInternalCalls() ){
				ProbeExporterUtil.createRuntimeCheckingProbeScriptMonitorInternal(bw, cc);
			} else {
				ProbeExporterUtil.createRuntimeCheckingProbeScript(bw, cc);
			}
			bw.close();
		} catch (IOException e) {
			throw new ConfigurationFilesManagerException("Cannot create probeScript "+dest.getAbsolutePath(),e);
		}

		return dest;
	}

	public static File getDataRecordingDir( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		return new File(getBctHomeDir(mc),Files.dataRecordingDirName);
	}
	
	public static File getModelsDir( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		return new File(getBctHomeDir(mc),Files.modelsDirName);
	}
	
	public static File getPreprocessingDir( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		return new File(getBctHomeDir(mc),Files.preprocessingDirName );
	}
	
	public static File getTemporaryDir( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		return new File(getBctHomeDir(mc),Files.tmpDirName );
	}
	
	/**
	 * Returns an array containing the libraries required to run the given MonitoringConfiguration.
	 * The array always contains the BCT library.
	 * 
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException
	 */
	public static File[] getRequiredLibraries ( MonitoringConfiguration mc ) throws ConfigurationFilesManagerException{
		//FIXME: we are always adding CBE libraries, should check if necessary
		File[] libs = new File[1];
		try {
			libs[0] = DefaultOptionsManager.getBctJarFile();
			//libs[1] = DefaultOptionsManager.getTLCBE101JarFile();
			//libs[2] = DefaultOptionsManager.getTLCOREJarFile();
			//libs[3] = DefaultOptionsManager.getJGRAPHTJarFile();
			//libs[4] = DefaultOptionsManager.getAvaJarFile();
			return libs;
		} catch (DefaultOptionsManagerException e) {
			throw new ConfigurationFilesManagerException(e);
		}
	}
	
	/**
	 * Returns the CBE log file associated to a monitoring configuration.
	 * The file is created when an application is monitored for checking its behavior with the given monitorin cnfiguration.
	 * 
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException 
	 */
	public static File getBCTCbeLogFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException{
		File home = getBctHomeDir(mc);
		return new File(home, Files.bctCBELogFileName);
	}

	
	
	/**
	 * Returns the folder in which CBE log analysis data is stored. If the folder does not exists the method creates it. 
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException
	 */
	public static File getViolationsLogAnalysisFolder(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File home = getBctHomeDir(mc);
		File folder = new File(home, Files.bctViolationsLogAnalysisFolder );
		
		createFolder( folder );
		
		
		return folder;
	}
	
	
	/**
	 * Returns the folder in which CBE log analysis data is stored when generated for models inference. 
	 * If the folder does not exists the method creates it. 
	 * @param mc
	 * @return
	 * @throws ConfigurationFilesManagerException
	 */
	public static File getInferenceLogAnalysisFolder(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File home = getBctHomeDir(mc);
		File folder = new File(home, Files.bctInferenceLogAnalysisFolder );
		
		createFolder( folder );
		
		
		return folder;
	}
	
	
	
	
	private static void createFolder(File folder) throws ConfigurationFilesManagerException {
		
		File home = folder.getParentFile();
		
		IFile folderResource = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(folder.getAbsolutePath()));
		
		if ( ! folder.exists() ){
			folder.mkdirs();
		}
		
		if ( ! folderResource.exists() ){
			try {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(home.getAbsolutePath())).refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				throw new ConfigurationFilesManagerException(e);
			}
		}
	}

//	public static MonitoringConfiguration getAssociatedMonitoringConfiguration(IFile resource) {
//		return MonitoringConfigurationRegistry.getInstance().getAssociatedMonitoringConfiguration(resource);
//	}

	public static File getCoverageFolder(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), "coverage" );
	}

	public static File getStaticFunctionsFileOriginal(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File parent = getScriptsDir(mc);
		return new File( parent, "staticFunctions.original.properties");
	}

	public static File getStaticFunctionsFileModified(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File parent = getScriptsDir(mc);
		return new File( parent, "staticFunctions.modified.properties");
	}

	public static File getMonitoredFunctionsDataFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File parent = getScriptsDir(mc);
		return new File( parent, "monitoredFunctions.original.ser");
	}

	public static File getComponentsDefinitionFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getScriptsDir(mc), "comp.txt" );
	}

	public static File getValidatedModelsDir(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), FShellModelsExporter.VALIDATED_MODELS_FOLDER );
	}

	public static File getFileWithVariablesToExclude(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getScriptsDir(mc), "variablesToExclude.txt" );
	}

	public static File getMonitoredFunctionsDataFileModifiedVersion( MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File parent = getScriptsDir(mc);
		return new File( parent, "monitoredFunctions.modified.ser");
	}

	public static File getGlobalVariablesMapFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File parent = getScriptsDir(mc);
		return new File( parent, "globalVariables.original.ser");
	}

	public static File getGlobalVariablesMapModifiedFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File parent = getScriptsDir(mc);
		return new File( parent, "globalVariables.modified.ser");
	}

	public static File getGotoCCSrcFolderOriginal(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), CBMC_SRC_FOLDER_ORIGINAL );
	}
	
	
	public static File getGotoCCSrcFolderModified(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), CBMC_SRC_FOLDER_MODIFIED );
	}
	
	public static File getGotoCCProgramModified(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File gotoCCsrc = getGotoCCSrcFolderModified(mc);
		
		return retrieveGotoCCprogramInFolder(gotoCCsrc);
	}

	public static File retrieveGotoCCprogramInFolder(File gotoCCsrc) {
		File[] files = gotoCCsrc.listFiles();
		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				long delta = o1.lastModified() - o2.lastModified();
				if ( delta == 0 ){
					return 0;
				}
				if ( delta < 0 ){
					return 1;
				}
				return -1;
			}
		});
		
		for ( File f : files){
			if ( f.isFile() ){
				return f;
			}
		}
		
		return null;
	}

	public static File getCBMCfolder(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getBctHomeDir(mc), CBMC_VART_RESULTS_FOLDER );
	}

	public static File getVARTresult_NonRegressionPropertiesVerification( MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getCBMCfolder(mc), CBMC_VART_NRP_VERIFICATION );
	}

	
	
	public static File getVARTresult_DynamicPropertiesVerification(
			MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getCBMCfolder(mc), CBMC_VART_DP_VERIFICATION );
	}

	public static File getVARTresult_OutdatedProperties( MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getCBMCfolder(mc), CBMC_VART_OUTDATED_PROPERTIES );
	}

	public static File getVARTresult_NonRegressionPropertiesInjected( MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getCBMCfolder(mc), CBMC_VART_NRP_INJECTED );
	}
	
	public static File getVARTresult_NonRegressionPropertiesInjectedLines( MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return new File( getCBMCfolder(mc), CBMC_VART_NRP_INJECTED_LINES );
	}
}
