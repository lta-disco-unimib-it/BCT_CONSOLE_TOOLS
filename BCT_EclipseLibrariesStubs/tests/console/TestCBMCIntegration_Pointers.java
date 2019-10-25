package console;

import static org.junit.Assert.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.textui.TestRunner;

import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import cpp.gdb.CSourcesFilter;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.SourceLinesMapper;

import tools.fshellExporter.CBMCAssertionsInjector;
import tools.fshellExporter.CBMCExecutor;
import tools.fshellExporter.CBMCModelsExporter;
import tools.fshellExporter.CBMCModelsFilter;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.FileUtil;
import util.JavaRunner;
import util.ProcessRunner;

public class TestCBMCIntegration_Pointers {

	private static final String EXPECTED_PROCESSOR_Y = "program.cpp\t_Z12processPointP5Point\tassert( (*point).y <= return );";
	//TILL 9-05-2014 was:
	//private static final String EXPECTED_PROCESSOR_Y = "program.cpp\t_Z12processPointP5Point\tassert( ! (point != 0) || (point != 0 && (*point).y <= return) );";

	
	private static final String EXPECTED_POINTPROCESSOR_Y = "PointProcessor.cpp\t_ZN14PointProcessor12processPointEP5Point\tassert( (*p).y <= return );";
	
	//TILL 9-05-2014 was:
	//private static final String EXPECTED_POINTPROCESSOR_Y = "PointProcessor.cpp\t_ZN14PointProcessor12processPointEP5Point\tassert( ! (p != 0) || (p != 0 && (*p).y <= return) );";
	private static String originalSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V0" );
	private static String originalProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V0/program" );
	private static String modifiedProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V1/program" );
	private static String modifiedSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V1" );
	
	private static String modifiedMovedProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V1_linesMoved/program" );
	private static String modifiedMovedSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V1_linesMoved" );

//	private File monitoredFunctionsFile = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.original.ser");
//	private File monitoredFunctionsV1File = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.modified.ser");
	
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile( "/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V0");
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/CBMC_CPP_StructRefs_V1");
		TestsRunner.compile(modifiedMovedSrc);
	}
	
	
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"0","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"2","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"3","2"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"5","9"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"7","12"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"300","212"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"367","232212"});
		
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		
		VARTRegressionConfiguration conf = new VARTRegressionConfiguration();
		conf.setUnitVerification( false );
		conf.setExportLines( true );
		CBCMRegressionsDetector detector = new CBCMRegressionsDetector(conf,ModelsFetcherFactoy.modelsFetcherInstance);
		detector.setGotoProgram("program");
		detector.setCompileCommand("make CXX=goto-cc");
		Set<String> regressedProperties = detector.identifyRegressions(mrc);
		
//		console.RegressionsDetector.main(new String[]{TestsRunner.defaultProjectDir});
		
		for ( String regr : regressedProperties ){
			System.out.println(regr);
		}
		
//		HashSet<String> expected = new HashSet<String>();

//		assertEquals(expected, regressedProperties);
		assertTrue( regressedProperties.contains(EXPECTED_POINTPROCESSOR_Y ) );
		assertTrue( regressedProperties.contains(EXPECTED_PROCESSOR_Y) );
//		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
	}

	
	@Test
	public void testMonitoringAndInference_linesMoved() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedMovedSrc, modifiedMovedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"0","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"2","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"3","2"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"5","9"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"7","12"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"300","212"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"367","232212"});
		
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		
		VARTRegressionConfiguration conf = new VARTRegressionConfiguration();
		conf.setUnitVerification( false );
		conf.setExportLines( true );
		CBCMRegressionsDetector detector = new CBCMRegressionsDetector(conf,ModelsFetcherFactoy.modelsFetcherInstance);
		detector.setGotoProgram("program");
		detector.setCompileCommand("make CXX=goto-cc");
		Set<String> regressedProperties = detector.identifyRegressions(mrc);
		
//		console.RegressionsDetector.main(new String[]{TestsRunner.defaultProjectDir});
		
		for ( String regr : regressedProperties ){
			System.out.println(regr);
		}
		
//		HashSet<String> expected = new HashSet<String>();

//		assertEquals(expected, regressedProperties);
		assertTrue( regressedProperties.contains(EXPECTED_POINTPROCESSOR_Y ) );
		assertTrue( regressedProperties.contains(EXPECTED_PROCESSOR_Y) );
//		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
	}
	
	@Test
	public void testConsoleExecution() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(DebuggingScriptsGenerator.class, args, 0);
		
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"0","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"2","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"3","2"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"5","9"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"7","12"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"300","212"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"367","232212"});
		
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CXX=goto-cc"});
		JavaRunner.runMainInClass(console.RegressionsDetector.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_POINTPROCESSOR_Y, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_PROCESSOR_Y, lines.get(last) );
	}


	@Test
	public void testConsoleExecution_UnitChecking() throws IOException, ClassNotFoundException, CoreException, ConfigurationFilesManagerException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
//		JavaRunner.runMainInClass(Class.forName("SetupVART"), args, 0);
		
		VARTScriptsGenerator scriptsGen = new VARTScriptsGenerator();
		ScriptsGenerator.execute(args.toArray(new String[0]), scriptsGen);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"0","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"2","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"3","2"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"5","9"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"7","12"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"300","212"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"367","232212"});
		
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CXX=goto-cc"});
		JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_POINTPROCESSOR_Y, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_PROCESSOR_Y, lines.get(last) );
	}
	
	
}
