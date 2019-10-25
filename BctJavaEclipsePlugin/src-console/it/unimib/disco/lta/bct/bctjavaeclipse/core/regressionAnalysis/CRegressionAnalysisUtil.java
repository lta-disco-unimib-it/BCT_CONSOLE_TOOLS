package it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ProgramPoint;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import tools.gdbTraceParser.FunctionEntryPointDetector;
import util.FileUtil;
import util.componentsDeclaration.Component;
import util.componentsDeclaration.ComponentDefinitionExporter;
import cpp.gdb.EnvUtil;
import cpp.gdb.FileChangeInfo;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.GdbRegressionConfigCreator;
import cpp.gdb.GlobalVariablesCommandsCreator;
import cpp.gdb.GlobalVariablesMapSerializer;
import cpp.gdb.ModifiedFunctionsAnalysisResult;
import cpp.gdb.ModifiedFunctionsDetector;
import cpp.gdb.ObjDumper;
import cpp.gdb.SourceLinesMapper;
import cpp.gdb.SourceMapperException;
import cpp.gdb.StaticFunctionsFinderFile;

public class CRegressionAnalysisUtil {

	///indicates whether to identify the address where to set the break point for function ENTRY using an additional gdb batch run for each function (dafault true)
	private static final String BCT_USE_ENTRY_POINT_DETECTOR = "bct.useEntryPointDetector";


	public static void createObjdumpFile( MonitoringConfiguration mrc ) throws ConfigurationFilesManagerException, IOException{
		ObjDumper dumper = new ObjDumper();
		
		CConfiguration regressionConfiguration = (CConfiguration) mrc.getAdditionalConfiguration(CConfiguration.class);
		
		File dumpOriginal = ConfigurationFilesManager.getOriginalSoftwareDumpFile( mrc );
		File execOriginal = new File( regressionConfiguration.getOriginalSwExecutable() );
		
		
		dumper.dump(execOriginal, dumpOriginal);
	}
	
	
	public static ArrayList<Component> getSourceChanges(
			MonitoringConfiguration loadedMonitoringConfiguration) throws ConfigurationFilesManagerException, IOException {
		return getComponentsToMonitor(loadedMonitoringConfiguration, (CRegressionConfiguration) loadedMonitoringConfiguration.getAdditionalConfiguration(CRegressionConfiguration.class) );
	}

	
	public static List<FileChangeInfo> getDiffs(
			MonitoringConfiguration mrc, CRegressionConfiguration regressionConfiguration) throws ConfigurationFilesManagerException, IOException {
		
		ObjDumper dumper = new ObjDumper();
		
		File dumpOriginal = ConfigurationFilesManager.getOriginalSoftwareDumpFile( mrc );
		File dumpModified = ConfigurationFilesManager.getModifiedSoftwareDumpFile( mrc );
		//dumper.
		
		
		File execOriginal = new File( regressionConfiguration.getOriginalSwExecutable() );
		File execModified = new File( regressionConfiguration.getModifiedSwExecutable() );
		
		dumper.dump(execOriginal, dumpOriginal);
		
		dumper.dump(execModified, dumpModified);
		
		File originalFolder = new File ( regressionConfiguration.getOriginalSwSourcesFolder() );
		
		File modifiedFolder = new File ( regressionConfiguration.getModifiedSwSourcesFolder() );
		
		ModifiedFunctionsDetector mfd = new ModifiedFunctionsDetector();
		mfd.setUseDemangledNames(regressionConfiguration.isUseDemangledNames());
		
		
		
		return mfd.extractDiffs( originalFolder, modifiedFolder );
		
	}
	
	public static Map<String, FunctionMonitoringData> getFunctionsDeclaredInOriginalSoftware(
			MonitoringConfiguration mrc, CRegressionConfiguration regressionConfiguration) throws ConfigurationFilesManagerException, IOException {
		
		File dumpOriginal = ConfigurationFilesManager.getOriginalSoftwareDumpFile( mrc );
		File originalFolder = new File ( regressionConfiguration.getOriginalSwSourcesFolder() );
		
		List<String> locations = new ArrayList<String>();
		locations.add(originalFolder.getAbsolutePath());
		
		return GdbRegressionConfigCreator.getDeclaredFunctions(dumpOriginal, locations);
	}
	
	public static Map<String, FunctionMonitoringData> getFunctionsDeclaredInModifiedSoftware(
			MonitoringConfiguration mrc, CRegressionConfiguration regressionConfiguration) throws ConfigurationFilesManagerException, IOException {
		
		File dumpOriginal = ConfigurationFilesManager.getModifiedSoftwareDumpFile( mrc );
		File originalFolder = new File ( regressionConfiguration.getModifiedSwSourcesFolder() );
		
		List<String> locations = new ArrayList<String>();
		locations.add(originalFolder.getAbsolutePath());
		
		return GdbRegressionConfigCreator.getDeclaredFunctions(dumpOriginal, locations);
	}
	
	public static ArrayList<Component> getComponentsToMonitor(
			MonitoringConfiguration mrc, CRegressionConfiguration regressionConfiguration) throws ConfigurationFilesManagerException, IOException {
		
		ObjDumper dumper = new ObjDumper();
		
		File dumpOriginal = ConfigurationFilesManager.getOriginalSoftwareDumpFile( mrc );
		File dumpModified = ConfigurationFilesManager.getModifiedSoftwareDumpFile( mrc );
		//dumper.
		
		
		File execOriginal = new File( regressionConfiguration.getOriginalSwExecutable() );
		File execModified = new File( regressionConfiguration.getModifiedSwExecutable() );
		
		dumper.dump(execOriginal, dumpOriginal);
		
		dumper.dump(execModified, dumpModified);
		
		File originalFolder = new File ( regressionConfiguration.getOriginalSwSourcesFolder() );
		
		File modifiedFolder = new File ( regressionConfiguration.getModifiedSwSourcesFolder() );
		
		ModifiedFunctionsDetector mfd = new ModifiedFunctionsDetector();
		mfd.setUseDemangledNames(regressionConfiguration.isUseDemangledNames());
		
		ArrayList<Component> components = new ArrayList<Component>();
		
		if ( regressionConfiguration.isMonitorAddedAndDeletedFunctions() ){
			ModifiedFunctionsAnalysisResult result = mfd.getModifiedFunctionsAnalysisResult(originalFolder, modifiedFolder, dumpOriginal, dumpModified );
			components.add(result.getModifiedFunctionsComponent());
			components.add(result.getAddedFunctionsComponent());
			components.add(result.getDeletedFunctionsComponent());
		} else {
			Component c = mfd.getChangesAsComponent(originalFolder, modifiedFolder, dumpOriginal );
			components.add(c);
		}
		
		
		return components;
	}

	
	
	
	public static void createGdbMonitoringConfigurations(
			MonitoringConfiguration mc, Class<? extends CConfiguration> configurationClass) throws ConfigurationFilesManagerException, IOException {
		
		File objDumpFileOriginal = ConfigurationFilesManager.getOriginalSoftwareDumpFile(mc);
		
		File fileWithVarsToExclude = ConfigurationFilesManager.getFileWithVariablesToExclude( mc );
		List<String> variablesToExclude = null;
		if ( fileWithVarsToExclude.exists() ){
			variablesToExclude = FileUtil.getLines(fileWithVarsToExclude);
			if ( variablesToExclude.size() == 0 ){
				variablesToExclude = null;
			}
		} else { //we create it just to improve usability
			fileWithVarsToExclude.createNewFile();
		}
		
		boolean useEntryPointDetector = setupUseEntryPointDetector();
		
//		boolean useAdditionalCommandsCreator = setupUseAdditionalCommandsCreator();
		
		//CRegressionConfiguration.class
		CConfiguration regressionConfig = (CConfiguration) mc.getAdditionalConfiguration( configurationClass );
		
		GdbRegressionConfigCreator.Configuration config = new GdbRegressionConfigCreator.Configuration();
		
		if ( regressionConfig instanceof CRegressionConfiguration  ){
			config.setMonitorCallersOfModifiedFunctions(((CRegressionConfiguration)regressionConfig).isMonitorCallersOfModifiedFunctions());
		}
		
		config.setMonitorPointerToThis(regressionConfig.isMonitorPointerToThis());
		
		if ( regressionConfig.getTestsFolder() != null ){
			String folder = regressionConfig.getTestsFolder();
			List<String> folders = new ArrayList<String>();
			folders.add(folder);
			config.setFoldersToFilterOut(folders);
		}
		
		config.setFunctionsToFilterOut(regressionConfig.getFunctionsToFilterOut());
		
		config.setUseDemangledNames(regressionConfig.isUseDemangledNames());
		
		config.setMonitorInternalLines(regressionConfig.isTraceAllLinesOfMonitoredFunctions());
		config.setMonitorChildrenInternalLines(regressionConfig.isTraceAllLinesOfChildren());
		
		config.setMonitorFunctionsDefinedOutsideProject( ! regressionConfig.isMonitorProjectFunctionsOnly());
		
		config.setMonitorLibraryCalls( regressionConfig.isMonitorLibraryCalls() );
		
		config.setMonitorInternalLines(regressionConfig.isTraceAllLinesOfMonitoredFunctions());
		config.setMonitorChildrenInternalLines(regressionConfig.isTraceAllLinesOfChildren());
		config.setMonitorLocalVariables(regressionConfig.isMonitorLocalVariables());
		config.setMonitorFunctionsCalledByTargetFunctions(regressionConfig.isMonitorFunctionsCalledByTargetFunctions());
		config.setRecordCallingContextData(regressionConfig.isRecordCallingContextData());
		
		config.setMonitorFunctionEnterExitPoints(regressionConfig.isMonitorFunctionEnterExitPoints());
		
		config.setDll(regressionConfig.isDll());
		
		GdbRegressionConfigCreator creator = new GdbRegressionConfigCreator(config);

		File originalGdbConfig = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfig(mc);
		
		ComponentDefinitionExporter.export((List)mc.getComponentsConfiguration().getComponents(), ConfigurationFilesManager.getComponentsDefinitionFile( mc ) );
		
		ArrayList<String> sourceFolders;
		sourceFolders = new ArrayList<String>(); 
		sourceFolders.add( regressionConfig.getOriginalSwSourcesFolder() );
		
		
		
		config.setLoggingFilePath( getLoggingFilePath(ConfigurationFilesManager.getValidTracesFolder(mc)) );
		
		IProject monitoredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(mc.getReferredProjectName());

	
		GlobalVariablesCommandsCreator additionalCommandsCreator = null;
		if ( regressionConfig.isMonitorGlobalVariables() ){
			additionalCommandsCreator = new GlobalVariablesCommandsCreator( new File( regressionConfig.getOriginalSwExecutable() ), variablesToExclude );
			creator.setAdditionalCommandsCreator(additionalCommandsCreator);
		}
		
		if ( useEntryPointDetector ){	
			FunctionEntryPointDetector functionEntryPOintDetector = new FunctionEntryPointDetector( new File( regressionConfig.getOriginalSwExecutable() ));
			creator.setFunctionEntryPointDetector(functionEntryPOintDetector);
		}
		
		List<FileChangeInfo> diffs = null;
		
		ArrayList<File> coverageFiles = collectCoverageFiles( mc );
		for ( File cf : coverageFiles){
			config.addCallgrindCoverageFile(cf);
		}
		
		SourceLinesMapper sourceLineMapper = null;
		
		if ( regressionConfig instanceof CRegressionConfiguration  ){
			CRegressionConfiguration _regressionConfig = (CRegressionConfiguration) regressionConfig;
			config.setMonitorOnlyNotModifiedLines( _regressionConfig.isMonitorOnlyNotModifiedLines() );
			
			//diffs are necessary for SOurceLineMapper
//			if ( config.isMonitorOnlyNotModifiedLines() ){
//				diffs = CRegressionAnalysisUtil.getDiffs(mc, _regressionConfig);
//			}
			
			diffs = CRegressionAnalysisUtil.getDiffs(mc, _regressionConfig);
			sourceLineMapper = new SourceLinesMapper( regressionConfig.getOriginalSwSourcesFolderFile(), ((CRegressionConfiguration) regressionConfig).getModifiedSwSourcesFolderFile(), diffs);	
		}

		
		List<String> additionalProgramPoints = createAdditionalProgramPointsForOriginalSW( monitoredProject, regressionConfig, sourceLineMapper );
		
		config.setWorkingOnOriginalSoftware(true);
		
		
		config.setStaticFunctionsFinder(new StaticFunctionsFinderFile(ConfigurationFilesManager.getStaticFunctionsFileOriginal(mc)));
		
		Set<FunctionMonitoringData> monitoredFunctions = creator.createConfig(objDumpFileOriginal, mc.getComponentsConfiguration().getComponents(), originalGdbConfig, sourceFolders, additionalProgramPoints, sourceLineMapper );

		try {
			if ( additionalCommandsCreator != null ){
				GlobalVariablesMapSerializer.store( additionalCommandsCreator.getGlobalVariableMap() , ConfigurationFilesManager.getGlobalVariablesMapFile(mc) );
			}
			FunctionMonitoringDataSerializer.store(monitoredFunctions, ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc));
//			throw new RuntimeException();
		} catch ( Throwable t ){
			//FIXME: removed for review demo
//			StringWriter sw = new StringWriter();
//			PrintWriter s = new PrintWriter( sw );
//			t.printStackTrace(s);
			Logger.getInstance().logError("Problem storing monitored functions data: "+t.getMessage(), t );
			t.printStackTrace();
		}
		
		if ( regressionConfig instanceof CRegressionConfiguration  ){
			CRegressionConfiguration _regressionConfig = (CRegressionConfiguration) regressionConfig;
			File modifiedGdbConfig = ConfigurationFilesManager.getModifiedSoftwareGdbMonitoringConfig(mc);
			creator = new GdbRegressionConfigCreator(config);
			
			if ( useEntryPointDetector ){
				FunctionEntryPointDetector functionEntryPOintDetector = new FunctionEntryPointDetector( new File( ((CRegressionConfiguration) regressionConfig).getModifiedSwExecutable() ));
				creator.setFunctionEntryPointDetector(functionEntryPOintDetector);
			}
			
			sourceFolders = new ArrayList<String>(); 
			sourceFolders.add( _regressionConfig.getModifiedSwSourcesFolder() );
			File objDumpFileModified = ConfigurationFilesManager.getModifiedSoftwareDumpFile(mc);
			config.setLoggingFilePath( getLoggingFilePath( ConfigurationFilesManager.getTracesToVerifyFolder(mc) ) );
			
			
			if ( regressionConfig.isMonitorGlobalVariables() ){
				additionalCommandsCreator = new GlobalVariablesCommandsCreator( new File( ((CRegressionConfiguration) regressionConfig).getModifiedSwExecutable() ), variablesToExclude );
				creator.setAdditionalCommandsCreator(additionalCommandsCreator);
			}
			
			config.setWorkingOnOriginalSoftware(false);
			
			config.setStaticFunctionsFinder(new StaticFunctionsFinderFile(ConfigurationFilesManager.getStaticFunctionsFileModified(mc)));
			
			
			
			Set<FunctionMonitoringData> monitoredFunctionsForModifiedVersion = creator.createConfig(objDumpFileModified, mc.getComponentsConfiguration().getComponents(), modifiedGdbConfig, sourceFolders, createAdditionalProgramPointsForModifiedSW(monitoredProject, _regressionConfig, sourceLineMapper ), sourceLineMapper );
		
			if ( additionalCommandsCreator != null ){
				GlobalVariablesMapSerializer.store( additionalCommandsCreator.getGlobalVariableMap() , ConfigurationFilesManager.getGlobalVariablesMapModifiedFile(mc) );
			}
			FunctionMonitoringDataSerializer.store(monitoredFunctionsForModifiedVersion, ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc));
		} else if ( regressionConfig instanceof CConfiguration  ){
			CConfiguration _regressionConfig = (CConfiguration) regressionConfig;
			File modifiedGdbConfig = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfigToVerify(mc);
			creator = new GdbRegressionConfigCreator(config);
			sourceFolders = new ArrayList<String>(); 
			sourceFolders.add( _regressionConfig.getOriginalSwSourcesFolder() );
			config.setLoggingFilePath( ConfigurationFilesManager.getTracesToVerifyFolder(mc) + "/" + GdbRegressionConfigCreator.Configuration.DEFAULT_LOGGNG_FILE_NAME );
			
			if ( useEntryPointDetector ){	
				FunctionEntryPointDetector functionEntryPOintDetector = new FunctionEntryPointDetector( new File( regressionConfig.getOriginalSwExecutable() ));
				creator.setFunctionEntryPointDetector(functionEntryPOintDetector);
			}
			
			if ( regressionConfig.isMonitorGlobalVariables() ){
				additionalCommandsCreator = new GlobalVariablesCommandsCreator( new File( regressionConfig.getOriginalSwExecutable() ), variablesToExclude );
				creator.setAdditionalCommandsCreator(additionalCommandsCreator);
			}
			
			creator.createConfig(objDumpFileOriginal, mc.getComponentsConfiguration().getComponents(), modifiedGdbConfig, sourceFolders, additionalProgramPoints );
		}
	}


//	public static boolean setupUseAdditionalCommandsCreator() {
//		boolean useAdditionalCommandsCreator = true;
//		{
//			String use = System.getProperty("bct.useAdditionalCommandsCreator");
//			if ( use != null ){
//				useAdditionalCommandsCreator = Boolean.valueOf(use);
//				System.out.println("Set additional commands creator: "+useAdditionalCommandsCreator);
//			}
//		}
//		System.out.println("Use additional commands creator: "+useAdditionalCommandsCreator);
//		return useAdditionalCommandsCreator;
//	}


	private static boolean setupUseEntryPointDetector() {
		boolean useEntryPointDetector = true;
		{
			String use = System.getProperty(BCT_USE_ENTRY_POINT_DETECTOR);
			if ( use != null ){
				useEntryPointDetector = Boolean.valueOf(use);
				System.out.println("Set use entry point detector: "+useEntryPointDetector);
			}
		}
		System.out.println("Use entry point detector: "+useEntryPointDetector);
		return useEntryPointDetector;
	}


	public static String getLoggingFilePath(File validTracesFolder) {
		String logginfFilePath = validTracesFolder.getAbsolutePath() + File.separator + GdbRegressionConfigCreator.Configuration.DEFAULT_LOGGNG_FILE_NAME;
		logginfFilePath = EnvUtil.getOSScriptPath( logginfFilePath );
		return logginfFilePath;
	}

	private static ArrayList<File> collectCoverageFiles(MonitoringConfiguration mc) {
		ArrayList<File> result = new ArrayList<File>();
		try {
			File folder = ConfigurationFilesManager.getCoverageFolder(mc);

			File[] files = folder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return pathname.getName().startsWith("callgrind");
				}
			}
					);
			
			if (files != null ){
				for ( File f : files ){
					result.add( f );
				}
			}
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	private static List<String> createAdditionalProgramPointsForOriginalSW(
			IProject monitoredProject, CConfiguration regressionConfig, SourceLinesMapper sourceLineMapper) throws IOException {
		List<ProgramPoint> programPoints = regressionConfig.getSourceProgramPoints();

		ArrayList<ProgramPoint> programPointsToMonitor = new ArrayList<ProgramPoint>();

		programPointsToMonitor.addAll(programPoints);
		
		if ( regressionConfig instanceof CRegressionConfiguration ){
			programPointsToMonitor.addAll(retrieveCorrespondingProgramPoints(sourceLineMapper.inOriginal,
					((CRegressionConfiguration) regressionConfig).getSourceProgramPointsForModifiedSoftware())
					);	
		}
		
		
		return createAdditionalProgramPoints(monitoredProject, programPointsToMonitor, regressionConfig);
	}
	
	private static List<String> createAdditionalProgramPointsForModifiedSW(
			IProject monitoredProject, CRegressionConfiguration regressionConfig, SourceLinesMapper sourceLineMapper) throws IOException {
		List<ProgramPoint> programPoints = regressionConfig.getSourceProgramPointsForModifiedSoftware();
		
		ArrayList<ProgramPoint> programPointsToMonitor = new ArrayList<ProgramPoint>();

		programPointsToMonitor.addAll(programPoints);
		
		programPointsToMonitor.addAll(retrieveCorrespondingProgramPoints(sourceLineMapper.inModified,
				regressionConfig.getSourceProgramPoints()));
		
		return createAdditionalProgramPoints(monitoredProject, programPointsToMonitor, regressionConfig);
	}


	public static List<ProgramPoint> retrieveCorrespondingProgramPoints(
			SourceLinesMapper.CommonInterface sourceLineMapper, List<ProgramPoint> list) {

		List<ProgramPoint> programPointsToMonitor = new ArrayList<ProgramPoint>();
		for ( ProgramPoint pp : list ){
			String fileName = pp.getAbsolutePath();
			int lineNumber = pp.getLineNumber();
			try {
				
				int corresponding = sourceLineMapper.getCorrespondingLine(fileName, lineNumber);
				fileName = sourceLineMapper.getCorrespondingFile( fileName );
				ProgramPoint ppOrig = new ProgramPoint(fileName, corresponding);
				programPointsToMonitor.add(ppOrig);
			} catch (SourceMapperException e) {
				e.printStackTrace();
			}
		}
		
		return programPointsToMonitor;
	}

	private static List<String> createAdditionalProgramPoints(
			IProject monitoredProject, List<ProgramPoint> programPoints, CConfiguration regressionConfig) throws IOException {
		List<String> result = new ArrayList<String>();
		for ( ProgramPoint pp : programPoints ){
			//IFile fileToMonitor = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path( pp.getAbsolutePath() ) );
			//IFile fileToMonitor = monitoredProject.getFile( new Path(  ) );
			//String gdbString = fileToMonitor.getLocation().toFile().getCanonicalPath() +":"+ pp.getLineNumber();
			String gdbString = pp.getAbsolutePath() +":"+ pp.getLineNumber();
			result.add(gdbString);
		}
		
		return result;
	}

	public static ArrayList<Component> getComponentsToMonitor(
			MonitoringConfiguration loadedMonitoringConfiguration) throws ConfigurationFilesManagerException, IOException {
		return getComponentsToMonitor(loadedMonitoringConfiguration, (CRegressionConfiguration) loadedMonitoringConfiguration.getAdditionalConfiguration(CRegressionConfiguration.class) );
	}

	public static void createTracesFolders(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File folder = ConfigurationFilesManager.getValidTracesFolder(mc);
		folder.mkdirs();
		folder = ConfigurationFilesManager.getTracesToVerifyFolder(mc);
		folder.mkdirs();
	}


	public static void createStaticFunctionsFile(MonitoringConfiguration mc) throws ConfigurationFilesManagerException, IOException {
		
		
		File file = ConfigurationFilesManager.getStaticFunctionsFileOriginal(mc);
		if ( ! file.exists() ){
			file.createNewFile();
		}
		
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			file = ConfigurationFilesManager.getStaticFunctionsFileModified(mc);
			if ( ! file.exists() ){
				file.createNewFile();
			}	
		}
	}

}
