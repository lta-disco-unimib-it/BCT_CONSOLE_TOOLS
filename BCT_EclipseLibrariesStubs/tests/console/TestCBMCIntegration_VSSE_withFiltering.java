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
package console;

import static org.junit.Assert.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfigurationFactory;
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
import java.util.Collections;
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
import org.junit.Ignore;
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

public class TestCBMCIntegration_VSSE_withFiltering {

	
	private static final String EXPECTED_TOTAL_GT_0_38 = "src/store.c\t42\tassert( total >= 0 );";
	private static final String EXPECTED_TOTAL_GT_0_41 = "src/store.c\t45\tassert( total >= 0 );";
	private static final String EXPECTED_return_0_1 = "src/store.c\tisAvailable\tassert( return == 0 || return == 1 );";
	private static final String EXPECTED_TOTAL_GT_0_37 = "src/store.c\t41\tassert( total >= 0 );";
	private static final String EXPECTED_return_GT_1 = "src/store.c\tavailable_items\tassert( return >= 0 );";

	
	private static String originalSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/VSSE_short_v3" );
	private static String originalProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/VSSE_short_v3/program" );
	private static String modifiedProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/VSSE_short_v4/program" );
	private static String modifiedSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/VSSE_short_v4" );
	
	

//	private File monitoredFunctionsFile = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.original.ser");
//	private File monitoredFunctionsV1File = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.modified.ser");
	
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile( originalSrc );
		TestsRunner.compile( modifiedSrc );
	}
	
	
	@Ignore
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);

		ProjectSetup.setupProject(TestsRunner.testPath( "/tmp/BCT/"));
		
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		
		File BCT_workspace = new File( TestsRunner.defaultProjectDir );
		File BCT_Prj = new File ( BCT_workspace, "BCT" );
		File BCT_conf = new File(BCT_Prj, "BCT.bctmc");
		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(BCT_conf);
		CRegressionConfiguration regressionConfig = (CRegressionConfiguration) mc.getAdditionalConfigurations().get(CRegressionConfiguration.class);
		
		assertTrue(regressionConfig.isExcludeUnusedVariables());
		assertTrue(regressionConfig.isMonitorCallersOfModifiedFunctions());

		
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		

		VARTRegressionConfiguration vartConfig = new VARTRegressionConfiguration();
		vartConfig.setUnitVerification( false );
		vartConfig.setExportLines( true );
		CBCMRegressionsDetector detector = new CBCMRegressionsDetector(vartConfig,ModelsFetcherFactoy.modelsFetcherInstance);
		detector.setGotoProgram("program");
		detector.setCompileCommand("make CC=goto-cc");
		Set<String> regressedProperties = detector.identifyRegressions(mrc);
		
//		console.RegressionsDetector.main(new String[]{TestsRunner.defaultProjectDir});
		
		for ( String regr : regressedProperties ){
			System.out.println(regr);
		}
		
//		HashSet<String> expected = new HashSet<String>();

//		assertEquals(expected, regressedProperties);
		assertTrue( regressedProperties.contains( EXPECTED_TOTAL_GT_0_38 ) );
		assertTrue( regressedProperties.contains( EXPECTED_TOTAL_GT_0_41 ) );
		assertTrue( regressedProperties.contains( EXPECTED_return_0_1 ) );
		
//		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
	}
	
	@Ignore
	@Test
	public void testMonitoringAndInference_Filter() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);

		ProjectSetup.setupProject(TestsRunner.testPath( "/tmp/BCT/"));
		
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});

		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
//		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		

		VARTRegressionConfiguration vartConfig = new VARTRegressionConfiguration();
		vartConfig.setUnitVerification( false );
		vartConfig.setExportLines( true );
		CBCMRegressionsDetector detector = new CBCMRegressionsDetector(vartConfig,ModelsFetcherFactoy.modelsFetcherInstance);
		detector.setGotoProgram("program");
		detector.setCompileCommand("make CC=goto-cc");
		Set<String> regressedProperties = detector.identifyRegressions(mrc);
		
//		console.RegressionsDetector.main(new String[]{TestsRunner.defaultProjectDir});
		
		
		for ( String regr : regressedProperties ){
			System.out.println(regr);
		}
		
//		HashSet<String> expected = new HashSet<String>();

//		assertEquals(expected, regressedProperties);
		assertTrue( regressedProperties.contains( EXPECTED_TOTAL_GT_0_38 ) );
		assertTrue( regressedProperties.contains( EXPECTED_TOTAL_GT_0_41 ) );
		assertFalse( regressedProperties.contains( EXPECTED_return_0_1 ) );
		
//		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
	}
	
	
	@Ignore
	@Test
	public void testMonitoringAndInference_Filter_Stub() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);

		ProjectSetup.setupProject(TestsRunner.testPath( "/tmp/BCT/"));
		
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});

		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		
		System.setProperty(CBMCAssertionsInjector.COMMENT_OUT_LINES_NOT_MONITORED,"true");
		
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
//		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		

		VARTRegressionConfiguration vartConfig = new VARTRegressionConfiguration();
		vartConfig.setUnitVerification( false );
		vartConfig.setExportLines( true );
		CBCMRegressionsDetector detector = new CBCMRegressionsDetector(vartConfig,ModelsFetcherFactoy.modelsFetcherInstance);
		detector.setGotoProgram("program");
		detector.setCompileCommand("make CC=goto-cc");
		Set<String> regressedProperties = detector.identifyRegressions(mrc);
		
//		console.RegressionsDetector.main(new String[]{TestsRunner.defaultProjectDir});
		
		
		for ( String regr : regressedProperties ){
			System.out.println(regr);
		}
		
		
		
//		HashSet<String> expected = new HashSet<String>();

//		assertEquals(expected, regressedProperties);
		assertTrue( regressedProperties.contains( EXPECTED_TOTAL_GT_0_38 ) );
		assertTrue( regressedProperties.contains( EXPECTED_TOTAL_GT_0_41 ) );
		assertFalse( regressedProperties.contains( EXPECTED_return_0_1 ) );
		
//		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
	}
	
	@Ignore
	@Test
	public void testConsoleExecution() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(DebuggingScriptsGenerator.class, args, 0);
		
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RegressionsDetector.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = lines.subList(lines.size()-11, lines.size());
//		int last = lines.size()-1;
		assertTrue( lines.contains("\t"+EXPECTED_TOTAL_GT_0_37) );
		assertTrue( lines.contains( "\t"+EXPECTED_TOTAL_GT_0_38) );
		assertTrue( lines.contains( "\t"+EXPECTED_TOTAL_GT_0_41) );
		assertTrue( lines.contains( "\t"+EXPECTED_return_0_1) );
	}
	
	@Ignore
	@Test
	public void testConsoleExecution_Filter() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(DebuggingScriptsGenerator.class, args, 0);
		
		
		File BCT_workspace = new File( TestsRunner.defaultProjectDir );
		File BCT_Prj = new File ( BCT_workspace, "BCT" );
		File BCT_conf = new File(BCT_Prj, "BCT.bctmc");
		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(BCT_conf);
		CRegressionConfiguration regressionConfig = (CRegressionConfiguration) mc.getAdditionalConfigurations().get(CRegressionConfiguration.class);
		
		assertTrue(regressionConfig.isExcludeUnusedVariables());
	
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RegressionsDetector.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = lines.subList(lines.size()-11, lines.size());
//		int last = lines.size()-1;
		assertTrue( lines.contains("\t"+EXPECTED_TOTAL_GT_0_37) );
		assertTrue( lines.contains( "\t"+EXPECTED_TOTAL_GT_0_38) );
		assertTrue( lines.contains( "\t"+EXPECTED_TOTAL_GT_0_41) );
		assertFalse( lines.contains( "\t"+EXPECTED_return_0_1) );
	}


	@Test
	public void testConsoleExecution_UnitChecking() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);
		
		
		File BCT_workspace = new File( TestsRunner.defaultProjectDir );
		File BCT_Prj = new File ( BCT_workspace, "BCT" );
		File BCT_conf = new File(BCT_Prj, "BCT.bctmc");
		
		
		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(BCT_conf);
		CRegressionConfiguration regressionConfig = (CRegressionConfiguration) mc.getAdditionalConfigurations().get(CRegressionConfiguration.class);
		
		assertFalse(regressionConfig.isExcludeUnusedVariables());
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = TestsRunner.getResultLines( lines );
		
		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(last-3) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(last-2) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_return_0_1, lines.get(last) );
	}
	
	
	@Test
	public void testConsoleExecution_UnitChecking_Filter() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);
		
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = TestsRunner.getResultLines( lines );
		
		assertEquals( 3, lines.size() );
		
		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(last-2) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(last) );
//		assertEquals( "\t"+EXPECTED_return_0_1, lines.get(last) );
	}
	
	
	@Test
	public void testConsoleExecution_UnitChecking_singleClaim() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);
		
			
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-D"+CBMCExecutor.BCT_CBMC_ALL_CLAIMS+"=false","-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = TestsRunner.getResultLines( lines );
		
		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(last-3) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(last-2) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_return_0_1, lines.get(last) );
	}
	
	
	
	@Test
	public void testConsoleExecution_UnitChecking_singleClaim_Filter() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);
		
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-D"+CBMCExecutor.BCT_CBMC_ALL_CLAIMS+"=false","-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = TestsRunner.getResultLines( lines );
		
		assertEquals(4, lines.size());
		
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(0) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(1) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(2) );
		assertEquals( "\t"+EXPECTED_return_GT_1, lines.get(3) );
		
	}
	
	
	@Test
	public void testConsoleExecution_UnitChecking_singleClaim_Filter_Stub() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);
		
		
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		
		
		args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		
		List<String> vmargs = Arrays.asList(new String[]{"-D"+CBMCAssertionsInjector.COMMENT_OUT_LINES_NOT_MONITORED+"=true","-D"+CBMCExecutor.BCT_CBMC_ALL_CLAIMS+"=false","-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc"});
		JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		
		
		BufferedReader reader = new BufferedReader(new StringReader(outputBuffer.toString()));
		String line;
		List<String> lines = new ArrayList<String>();
		while ( ( line = reader.readLine() ) != null ){
			lines.add(line);
		}
	
		System.out.println(errorBuffer.toString());
		System.out.println(outputBuffer.toString());
		
		lines = TestsRunner.getResultLines( lines );
		
		assertEquals(4, lines.size());
		
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(0) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(1) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(2) );
		assertEquals( "\t"+EXPECTED_return_GT_1, lines.get(3) );
		
	}



	
}
