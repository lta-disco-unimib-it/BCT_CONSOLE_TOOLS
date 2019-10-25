package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.FlattenerOptionsDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.FlattenerOptionsSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DaikonPropertiesCreator;
import it.unimib.disco.lta.bct.eclipse.core.BctCoreActivator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import traceReaders.normalized.NormalizedTraceHandlerFile;
import traceReaders.raw.FileTracesReader;
import conf.InvariantGeneratorSettings;

public class DefaultOptionsManager {
	///setup the default daikon config to use during inference
	public static final String BCT_INFERENCE_DAIKON_CONFIG_DEFAULT = "bct.inference.daikon.config.default";
	
	public static final String pluginID = "bctJavaEclipse";
	private static final String defaultBctDataProjectName = "BCT_DATA";
	private static String monitoringConfigurationFileExtension = "bctmc";
	private static String componentsConfigurationFileExtension = "bctcc";
	private static File defaultOptionsDir;
	private static final String bctOptionsDirName = "bctDefault";
	private static final String flattenerOptionsFileName = "flattenerOptions.xml";
	private static BctDefaultOptions defaultOptions;
	private static BctDefaultOptionsObserver defaultOptionsObserver = new BctDefaultOptionsObserver();
	private static File bctJarFile;
	private static File tlcbe101JarFile;
	private static File tlcoreJarFile;
	private static File jgraphtJarFile;
	private static File avaJarFile;
	private static File daikonJarFile;
	private static final String kbehaviorOptionsFileName = "kbehavior.properties";
	private static final String reissOptionsFileName = "reiss.properties";
	private static final String ktailOptionsFileName = "ktail.properties";
	private static final String kinclusionOptionsFileName = "kinclusion.properties";
	private static final String daikonPropertiesDir = "daikon";
	
	public static class Options {
		public static final String bctHomeReplacementKey = "%%BCT_HOME%%";
		//public final static String bctHome = "bctHome";
		public final static String violationsFile = "violationsFile";
	}
	
	
	private static class BctDefaultOptionsObserver implements BCTObserver {
		public void bctObservableUpdate(Object modifiedObject, Object message) {
			if ( modifiedObject == defaultOptions ){
				try {
					saveDaikonOptions();
				} catch (DefaultOptionsManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void saveDaikonOptions() throws DefaultOptionsManagerException  {
			//first deletes existing files
			File[] files = getDaikonAdditionalPropertiesFiles();
			for ( File file : files ){
				file.delete();
			}
			
			//then add the eisting properties
			for ( String configName : defaultOptions.getDaikonConfigPropertiesNames() ){
				try {
					saveDaikonAdditionalOption(configName,defaultOptions.getDaikonConfigProperties(configName));
				} catch (DefaultOptionsManagerException e) {
					throw e;
				} catch (BctDefaultOptionsException e) {
					throw new DefaultOptionsManagerException(e);
				}
			}
		}

		private void saveDaikonAdditionalOption(String configName,
				Properties daikonConfigProperties) throws DefaultOptionsManagerException {
			File dest = getDaikonConfigFile(configName);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(dest);
				daikonConfigProperties.store(fos, "Autogenrated");
				
			} catch (FileNotFoundException e) {
				throw new DefaultOptionsManagerException(e);
			} catch (IOException e) {
				throw new DefaultOptionsManagerException(e);
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

		private void saveDefaultOptions() throws DefaultOptionsManagerException {
			try {
				FlattenerOptionsSerializer.serialize(  new File ( defaultOptionsDir, flattenerOptionsFileName ) , defaultOptions.getFlattenerOptions());
			} catch (FileNotFoundException e) {
				throw new DefaultOptionsManagerException(e);
			}
			
			
			try {
				ConfigurationFilesManager.saveInvariantGeneratorOptions(defaultOptionsDir,defaultOptions.getInvariantGeneratorOptions());
			} catch (ConfigurationFilesManagerException e) {
				throw new DefaultOptionsManagerException(e);
			}
			
			try {
				ConfigurationFilesManager.saveInferenceEngineOptionsToFile(new File( defaultOptionsDir, kbehaviorOptionsFileName), defaultOptions.getKbehaviorInferenceEngineOptions());
			} catch (ConfigurationFilesManagerException e) {
				throw new DefaultOptionsManagerException(e);
			}
		}
	}
	
	public static String getBctDataProjectName(){
		return defaultBctDataProjectName;
	}
	
	public static String getMonitoringConfigurationFileExtension(){
		return monitoringConfigurationFileExtension;
	}
	
	public static String getComponentsConfigurationFileExtension(){
		return componentsConfigurationFileExtension;
	}
	
	/**
	 * Returns the default project that contain bct data, if the project doeas not exists creates it.
	 * 
	 * @return
	 * @throws CoreException
	 */
	public static IProject getBctDataProject() throws CoreException{
		IProjectDescription pd = ResourcesPlugin.getWorkspace().newProjectDescription(getBctDataProjectName());
		
		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(getBctDataProjectName());
		
		if ( ! prj.exists() ){
			prj.create(pd, new NullProgressMonitor());
			prj.open(new NullProgressMonitor());
		}
		
		return prj;
	}
	
	
	public static File getBctDataDir() throws CoreException {
		return DefaultOptionsManager.getBctDataProject().getLocation().toFile();
	}
	
	/**
	 * Return a BctDefaultOptions object containing all the default configuration options
	 * 
	 * @return
	 * @throws CoreException
	 * @throws DefaultOptionsManagerException
	 */
	public static BctDefaultOptions getDefaultOptions() throws CoreException, DefaultOptionsManagerException{
		if ( defaultOptions == null ){
			loadOptions();
			//System.out.println("Options loaded");
		}
		//System.out.println("Returning options");
		return defaultOptions;
	}

	/**
	 * Load the default options.
	 * Options are loaded from the files stored in th econfiguration directory (usually stored in BCT_DATA/.bctDefault), or if th edirectory is not present 
	 * default options are created by the plugin itself.
	 * 
	 * @throws CoreException
	 * @throws DefaultOptionsManagerException
	 * @throws CoreException 
	 */
	private static void loadOptions() throws DefaultOptionsManagerException, CoreException {
		File defaultOptionsDir = getBctDefaultOptionsDir();

		FlattenerOptions flattenerOptions;
		Properties invariantGeneratorOptions;
		Properties kbehaviorInferenceEngineOptions;
		Properties reissInferenceEngineOptions;
		Properties ktailInferenceEngineOptions;
		Properties kinclusionInferenceEngineOptions;

		if ( ! defaultOptionsDir.exists() ){
			defaultOptionsDir.mkdirs();
		}

		try{	
			File flattenerOptionsFile = new File ( defaultOptionsDir, flattenerOptionsFileName );
			if ( ! flattenerOptionsFile.exists() ){
				flattenerOptions = createFlattenerOptions();
			} else {
				flattenerOptions = FlattenerOptionsDeserializer.deserialize( flattenerOptionsFile );
			}
			//System.out.println("Flattener loaded");
			File kbehaviorFile = new File(defaultOptionsDir,kbehaviorOptionsFileName );
			if ( kbehaviorFile.exists() ){
				kbehaviorInferenceEngineOptions = ConfigurationFilesManager.loadInferenceEngineOptionsFromFile(kbehaviorFile);
			} else {
				kbehaviorInferenceEngineOptions = createKbehaviorInferenceEngineOptions();
			}
			//System.out.println("KB loaded");
			File ktailFile = new File(defaultOptionsDir,ktailOptionsFileName );
			if ( ktailFile.exists() ){
				ktailInferenceEngineOptions = ConfigurationFilesManager.loadInferenceEngineOptionsFromFile(ktailFile);
			} else {
				ktailInferenceEngineOptions = createKTailInferenceEngineOptions();
			}
			//System.out.println("KT loaded");
			File reissFile = new File(defaultOptionsDir,reissOptionsFileName );
			if ( reissFile.exists()){
				reissInferenceEngineOptions = ConfigurationFilesManager.loadInferenceEngineOptionsFromFile(reissFile);
			}else{
				reissInferenceEngineOptions = createReissInferenceEngineOptions();
			}
			//System.out.println("REISS loaded");
			File kinclusionFile = new File(defaultOptionsDir,kinclusionOptionsFileName );
			if ( kinclusionFile.exists() ){
				kinclusionInferenceEngineOptions = ConfigurationFilesManager.loadInferenceEngineOptionsFromFile(kinclusionFile);
			} else {
				kinclusionInferenceEngineOptions = createKInclusionInferenceEngineOptions();
			}
			//System.out.println("KINC loaded");
			
			try {
				invariantGeneratorOptions = ConfigurationFilesManager.loadInvariantGeneratorOptions(defaultOptionsDir);
			} catch (ConfigurationFilesManagerException e) {
				//Default options were not saved
				invariantGeneratorOptions = createInvariantGeneratorOptions();
			}

			
			Properties violationsRecorderOptions = createViolationsRecorderOptions();
			
			
			HashMap<String, Properties> defaultDaikonConfigs = getDaikonConfigs(); 
			
			
			
			//System.out.println("INVGEN loaded");

			defaultOptions = new BctDefaultOptions(flattenerOptions,invariantGeneratorOptions,kbehaviorInferenceEngineOptions,ktailInferenceEngineOptions,reissInferenceEngineOptions,kinclusionInferenceEngineOptions, violationsRecorderOptions, defaultDaikonConfigs);
			
			defaultOptions.addImmutableDaikonConfigs(DaikonPropertiesCreator.getDefaultConfigsNames());
			
			//System.out.println("Default options created");
			
			defaultOptions.addBCTObserver(defaultOptionsObserver);
			
			//System.out.println("Observer added");

		} catch (ConfigurationFilesManagerException e) {
			throw new DefaultOptionsManagerException("Cannot load default options",e);
		} catch (FileNotFoundException e) {
			throw new DefaultOptionsManagerException("Cannot load default options",e);
		}
	}

	private static Properties createViolationsRecorderOptions() {
		Properties p = new Properties();
		p.put("type",recorders.FileViolationsRecorder.class.getCanonicalName());
		p.put(Options.violationsFile , Options.bctHomeReplacementKey+"/bctCBELog");
		return p;
	}

	private static HashMap<String, Properties> getDaikonConfigs() {
		HashMap<String, Properties> daikonOptions = DaikonPropertiesCreator.getDefaultDaikonProperties();
		
		try {
			daikonOptions.putAll( loadAdditionalDaikonOptions() );
		} catch (DefaultOptionsManagerException e) {
			
		}
		
		return daikonOptions;
	}

	private static HashMap<String, Properties> loadAdditionalDaikonOptions() throws DefaultOptionsManagerException {
		HashMap<String, Properties> daikonOptions = new HashMap<String, Properties>();
		
		File[] files = getDaikonAdditionalPropertiesFiles();
		for ( File file : files ){
			Properties p = new Properties();
			try {
				FileInputStream is = new FileInputStream(file);
				p.load(is);
				String configName = getDaikonConfigName(file);
				daikonOptions.put(configName, p);
			} catch (FileNotFoundException e) {
				throw new DefaultOptionsManagerException(e);
			} catch (IOException e) {
				throw new DefaultOptionsManagerException(e);
			}
		}
		return daikonOptions;
	}

	private static String getDaikonConfigName(File file) {
		return file.getName();
	}
	
	private static File getDaikonConfigFile(String configName) throws DefaultOptionsManagerException {
		File daikonDir = getDaikonAdditionalPropertiesDir();
		return new File(daikonDir,configName);
	}

	private static File[] getDaikonAdditionalPropertiesFiles() throws DefaultOptionsManagerException {
		File daikonDir = getDaikonAdditionalPropertiesDir(); 
		return daikonDir.listFiles();
	}

	private static File getDaikonAdditionalPropertiesDir() throws DefaultOptionsManagerException  {
		try {
			File bctDefaultDir = getBctDefaultOptionsDir();
			File daikonDir = new File(bctDefaultDir,daikonPropertiesDir);
			if ( ! daikonDir.exists() ){
				daikonDir.mkdirs();
			}
			return daikonDir;
		} catch (CoreException e) {
			throw new DefaultOptionsManagerException(e);
		}
		
	}

	private static Properties createReissInferenceEngineOptions() {
		Properties p = new Properties();
		
		p.setProperty("minTrustLen","2");
		
		p.setProperty("level","1");
		p.setProperty("logger","console");
		
		p.setProperty("enableMinimization","end");
		p.setProperty("cutOffSearch","true");
		
		return p;
	}
	
	private static Properties createKTailInferenceEngineOptions() {
		Properties p = new Properties();
		
		p.setProperty("minTrustLen","2");
		
		p.setProperty("level","1");
		p.setProperty("logger","console");
		
		p.setProperty("enableMinimization","end");
		//p.setProperty("cutOffSearch","true");
		
		return p;
	}
	
	private static Properties createKInclusionInferenceEngineOptions() {
		Properties p = new Properties();
		
		p.setProperty("minTrustLen","2");
		
		p.setProperty("level","1");
		p.setProperty("logger","console");
		
		p.setProperty("enableMinimization","end");
		p.setProperty("cutOffSearch","true");
		
		return p;
	}

	public static Properties createKbehaviorInferenceEngineOptions() {
		Properties p = new Properties();
		
		p.setProperty("minTrustLen","2");
		p.setProperty("maxTrustLen","4");
		
		p.setProperty("level","1");
		p.setProperty("logger","console");
		
		p.setProperty("enableMinimization","step");
		p.setProperty("cutOffSearch","true");
		
		p.setProperty("stepSave","false");
	
		
		return p;
	}

	private static Properties createInvariantGeneratorOptions() {
		System.out.println("CREATING  INV G OPT");
		Properties p = new Properties();
		p.setProperty("type", tools.InvariantGenerator.class.getCanonicalName());
		
		String bctHomeValue = Options.bctHomeReplacementKey;
		
		String preprocessingDir = bctHomeValue+"/Preprocessing";
		
		p.setProperty(InvariantGeneratorSettings.Options.temporaryDir, bctHomeValue+"/tmp");
		p.setProperty(InvariantGeneratorSettings.Options.deleteTemporaryDir, "true");
		
		p.setProperty(InvariantGeneratorSettings.Options.fsaEngine, "KBehavior");
		p.setProperty(InvariantGeneratorSettings.Options.fsaEngine, "KBehavior");
		//p.setProperty(InvariantGeneratorSettings.Options.daikonPath, "/opt/daikon/daikon.jar");
		try {
			p.setProperty(InvariantGeneratorSettings.Options.daikonPath, getDaikonJarFile().getAbsolutePath());
		} catch (DefaultOptionsManagerException e) {
			p.setProperty(InvariantGeneratorSettings.Options.daikonPath, "/opt/daikon/daikon.jar");
		}
		p.setProperty(InvariantGeneratorSettings.Options.daikonConfidenceLevel, "0"); //was 0.99
		
		String defaultConfigString = System.getProperty(BCT_INFERENCE_DAIKON_CONFIG_DEFAULT);
		String defaultConfig;
		if ( defaultConfigString != null ){
			defaultConfig = defaultConfigString;
		} else {
			defaultConfig = "essentials";
		}
		p.setProperty(InvariantGeneratorSettings.Options.daikonConfig, defaultConfig); //was default
		p.setProperty(InvariantGeneratorSettings.Options.addAdditionalInvariants, "true");
		p.setProperty(InvariantGeneratorSettings.Options.expandReferences, "true");
		
		p.setProperty(InvariantGeneratorSettings.Options.traceReaderType, FileTracesReader.class.getCanonicalName());
		p.setProperty(FileTracesReader.Options.tracesPath,bctHomeValue+"/DataRecording/");
		p.setProperty(FileTracesReader.Options.ioTracesDirName, "ioInvariantLogs");
		p.setProperty(FileTracesReader.Options.interactionTracesDirName, "interactionInvariantLogs");
		
		p.setProperty(InvariantGeneratorSettings.Options.normalizedTraceHandlerType, NormalizedTraceHandlerFile.class.getCanonicalName());
		p.setProperty(NormalizedTraceHandlerFile.Options.declsDir, preprocessingDir+"/decls");
		p.setProperty(NormalizedTraceHandlerFile.Options.dtraceDir, preprocessingDir+"/dtrace");
		p.setProperty(NormalizedTraceHandlerFile.Options.interactionDir, preprocessingDir+"/interaction");
		
		System.out.println("CREATED INV G OPT");
		return p;
	}

	/**
	 * Create a new FlattenerOptions object filled with default values
	 * 
	 * @return
	 */
	private static FlattenerOptions createFlattenerOptions() {
		FlattenerOptions fo = new FlattenerOptions();
		fo.setFieldRetriever("all");
		fo.setMaxDepth(5);
		fo.setSmashAggregation(true);
		return fo;
	}

	/**
	 * Return the File object representing the default bct options dir
	 * 
	 * @return
	 * @throws CoreException
	 */
	private static File getBctDefaultOptionsDir() throws CoreException {
		if ( defaultOptionsDir == null ){
			defaultOptionsDir = new File(getBctDataDir(),bctOptionsDirName);	
		}
		
		
		return defaultOptionsDir;
	}

	public static File getBctJarFile() throws DefaultOptionsManagerException {
		if ( bctJarFile != null ){
			return bctJarFile; 
		}
		
		URL url = FileLocator.find(BctCoreActivator.getDefault().getBundle(), new Path("/lib/"), null);
		try {
			File libDir = new File ( FileLocator.resolve(url).getFile() );
			for ( File lib : libDir.listFiles() ){
				if ( lib.getName().equals("bct-standalone.jar") ){
					bctJarFile = lib;
				}
			}
			
		} catch (IOException e) {
			throw new DefaultOptionsManagerException(e);
		}
		
		if ( bctJarFile == null ){
			throw new DefaultOptionsManagerException("No BCT lib found in "+url);
		}
		
		return bctJarFile;
	}

	
	/**
	 * Returns the ava library File
	 * @return
	 * @throws DefaultOptionsManagerException
	 * @Deprecated 
	 */
	public static File getAvaJarFile() throws DefaultOptionsManagerException {
		if ( avaJarFile == null ){
			avaJarFile = getLibraryFile("ava.jar");
		}
		return avaJarFile;
	}
	
	
	/**
	 * Returns the tlcbe library File
	 * @return
	 * @throws DefaultOptionsManagerException
	 * @Deprecated
	 */
	public static File getTLCBE101JarFile() throws DefaultOptionsManagerException {
		if ( tlcbe101JarFile == null ){
			tlcbe101JarFile = getLibraryFile("tlcbe101.jar");
		}
		return tlcbe101JarFile;
	}

	/**
	 * Returns the tlcore library File
	 * @return
	 * @throws DefaultOptionsManagerException
	 * @Deprecated
	 */
	public static File getTLCOREJarFile() throws DefaultOptionsManagerException {
		if ( tlcoreJarFile == null ){
			tlcoreJarFile = getLibraryFile("tlcore.jar");
		}
		return tlcoreJarFile;
	}
	
	/**
	 * Returns the JGRAPHT library File
	 *  
	 * @return
	 * @throws DefaultOptionsManagerException
	 * @Deprecated
	 */
	public static File getJGRAPHTJarFile() throws DefaultOptionsManagerException {
		if ( jgraphtJarFile == null ){
			jgraphtJarFile = getLibraryFile("jgrapht-jdk1.5.jar");
		}
		return jgraphtJarFile;
	}
	
	/**
	 * Returns the File object identifying the jar library saved in the BctEclipseCore lib folder
	 * 
	 * @param jarFullName
	 * @return
	 * @throws DefaultOptionsManagerException
	 */
	private static File getLibraryFile(String jarFullName) throws DefaultOptionsManagerException {
		URL url = FileLocator.find(BctCoreActivator.getDefault().getBundle(), new Path("/lib/"+jarFullName), null);

		try {
			return new File ( FileLocator.resolve(url).getFile() );
			
		} catch (IOException e) {
			throw new DefaultOptionsManagerException(e);
		}

	}

	public static void setDaikonJarFile(File jar){
		daikonJarFile = jar;
	}
	
	public static File getDaikonJarFile() throws DefaultOptionsManagerException {
		if ( daikonJarFile != null ){
			return daikonJarFile;
		}
		URL url = FileLocator.find(BctCoreActivator.getDefault().getBundle(), new Path("/lib/daikon.jar"), null);
		try {
			return new File ( FileLocator.resolve(url).getFile() );
		} catch (IOException e) {
			throw new DefaultOptionsManagerException(e);
		}
	}

	public static boolean use_BCT_DATA_projectAsDefaultDataDir() {
		//From 2011-11-21 BCT data is created within the porject and not in /BCT_DATA/
		return false;
	}

	public static void setBctJarFile(File file) {
		bctJarFile = file;
	}

	
}
