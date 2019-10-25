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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
import org.junit.Ignore;
import org.junit.Test;

import cpp.gdb.CSourcesFilter;
import cpp.gdb.EnvUtil;
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
import util.componentsDeclaration.Environment;

public class TestCBMCIntegration_VSSE_filtersAndFileFinding {


	private static final String EXPECTED_TOTAL_GT_0_38 = "src/store.c\t38\tassert( total >= 0 );";
	private static final String EXPECTED_TOTAL_GT_0_41 = "src/store.c\t41\tassert( total >= 0 );";
	private static final String EXPECTED_return_0_1 = "src/store.c\tisAvailable\tassert( return == 0 || return == 1 );";
	private static final String EXPECTED_TOTAL_GT_0_37 = "src/store.c\t37\tassert( total >= 0 );";


	private static String originalSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/VSSE_short_v5" );
	private static String originalProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/VSSE_short_v5/program" );
	private static String modifiedProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/VSSE_short_v6/program" );
	private static String modifiedSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/VSSE_short_v6" );



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

		System.out.println("Regressed properties:");
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


	@Test
	public void testConsoleExecution_UnitChecking() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);



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

		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(last-3) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(last-2) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_return_0_1, lines.get(last) );
	}


	@Test
	public void testConsoleExecution_UnitChecking_cbmcFromConsole() throws IOException{
		List<String> args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(GenerateUnitCheckingScripts.class, args, 0);



		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});


		//INFER MODELS AND INSERT ASSERTIONS IN ORIGINAL CODE
		{
			args = Arrays.asList(new String[]{TestsRunner.defaultProjectDir});
			StringBuffer outputBuffer = new StringBuffer();
			StringBuffer errorBuffer = new StringBuffer();

			List<String> vmargs = Arrays.asList(new String[]{"-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc","-D"+CBCMRegressionsDetector.BCT_SKIP_CBMC_EXECUTION+"=true"});
			JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		}


		String cbmcOutputOriginal = TestsRunner.testPath(TestsRunner.defaultProjectDir+"/cbmc.output.original.txt");
		String cbmcOutputModified = TestsRunner.testPath(TestsRunner.defaultProjectDir+"/cbmc.output.modified.txt");

		//EXECUTE CBMC ON ORIGINAL
		{
			String gotoProgram = EnvUtil.getOSAbsolutePath( TestsRunner.defaultProjectDir+"BCT/BCT_DATA/BCT/CBMC.SRC.ORIGINAL/program");
			executeCBMCCommand(cbmcOutputOriginal, gotoProgram);
		}


		//READ CBMC OUTPUT AND INSERT ASSERTIONS IN MODIFIED CODE
		{
			StringBuffer outputBuffer = new StringBuffer();
			StringBuffer errorBuffer = new StringBuffer();
			List<String> vmargs = Arrays.asList(new String[]{"-D"+AnomaliesIdentifier.BCT_SKIP_INFERENCE+"=true","-D"+AnomaliesIdentifier.BCT_SKIP_CHECK+"=true","-D"+CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK+"=true","-Dbct.gotoProgram=program","-Dbct.compileCommand=make CC=goto-cc","-D"+CBCMRegressionsDetector.BCT_CBMC_OUTPUT_ORIGINAL+"="+cbmcOutputOriginal});
			JavaRunner.runMainInClass(console.RunUnitChecking.class, vmargs, args, 0, null, true, outputBuffer, errorBuffer);
		}


		//EXECUTE CBMC ON MODIFIED
		{
			String gotoProgram = EnvUtil.getOSAbsolutePath( TestsRunner.defaultProjectDir+"BCT/BCT_DATA/BCT/CBMC.SRC.MODIFIED/program");
			executeCBMCCommand(cbmcOutputModified, gotoProgram);
		}


		//READ CBMC OUTPUT AND INSERT ASSERTIONS IN MODIFIED CODE
		{
			StringBuffer outputBuffer = new StringBuffer();
			StringBuffer errorBuffer = new StringBuffer();
			List<String> vmargs = Arrays.asList(new String[]{"-D"+AnomaliesIdentifier.BCT_SKIP_INFERENCE+"=true","-D"+AnomaliesIdentifier.BCT_SKIP_CHECK+"=true","-Dbct.gotoProgram=program","-D"+CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK+"=true","-D"+CBCMRegressionsDetector.BCT_CBMC_OUTPUT_MODIFIED+"="+cbmcOutputModified,"-D"+CBCMRegressionsDetector.BCT_SKIP_PROCESSING_OF_ORIGINAL+"=true"});
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
			assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(last-3) );
			assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(last-2) );
			assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(last-1) );
			assertEquals( "\t"+EXPECTED_return_0_1, lines.get(last) );
		
		}



		
	}



	public void executeCBMCCommand(String cbmcOutputOriginal, String gotoProgram)
			throws IOException {
		StringBuffer outputBuffer = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();
		List<String> command = new ArrayList<String>();
		command.add("cbmc");
		command.add("--all-claims");
		command.add("--unwind");
		command.add("3");
		command.add("--no-unwinding-assertions");
		command.add( gotoProgram ); 
		ProcessRunner.run(command, outputBuffer, errorBuffer, 0);

		BufferedWriter writer = new BufferedWriter( new FileWriter ( cbmcOutputOriginal ) );
		writer.write(outputBuffer.toString());
		writer.close();
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

		int last = lines.size()-1;
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_37, lines.get(last-3) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_38, lines.get(last-2) );
		assertEquals( "\t"+EXPECTED_TOTAL_GT_0_41, lines.get(last-1) );
		assertEquals( "\t"+EXPECTED_return_0_1, lines.get(last) );
	}





}
