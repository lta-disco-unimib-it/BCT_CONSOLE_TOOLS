package console;

import static org.junit.Assert.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import cpp.gdb.EnvUtil;

import tools.gdbTraceParser.GdbThreadTraceParser;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.JavaRunner;

public class TestNotInitializedVars_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/UninitializedVars" );
	private static String originalProgram = TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/UninitializedVars/program" );
	private static String modifiedProgram = TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/UninitializedVars-V2/program" );
	private static String modifiedSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/UninitializedVars-V2" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc);
		TestsRunner.compile(modifiedSrc);
	}
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		List<String> vmArgs = new ArrayList<String>();
		vmArgs.add("-D"+GdbThreadTraceParser.BCT_ALWAYS_TRACE_NOT_NULL+"=true");
		
		List<String> args = new ArrayList<String>();
		args.add(TestsRunner.defaultProjectDir);
		
		JavaRunner.runMainInClass(AnomaliesIdentifier.class, vmArgs, args, 0, null, false, null, null);
		
//		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
//		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		

		MonitoringConfiguration mc = TestsRunner.getDefaultMonitoringConfiguration(TestsRunner.defaultProjectDir);
		List<BctModelViolation> anomalies = RegressionAnalysis.identifyRegressions(mc, false).getViolationAnalysisResult().getFilteredViolations();

		
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "_ZN6Worker9calculateEii :::ENTER";
			String model = "myGlobal == -1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("myGlobal", 0));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
//		if ( ! EnvUtil.isWindows() ){
			{
				String functionName = "_ZN6Worker9calculateEii :::ENTER";
				String model = "(*this != null)  ==>  (*this.state == 1)";
				ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
				vd.addVariableData(new VariableData("*this", "!NULL"));
				violations.add(vd);

				TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "*this.state" );

				assertTrue ( actualViolations.contains(vd) );
			}
//		} else {
//			//Undr window the pointer to "this" is 0x1 at the beginning of the method
//			//In the followng case "this" become assigned at instruction "4011ff", 
//			//we could decide to for the entry point monitoring command the instructions ni, ni to make "this" assigned
//			//
//			//OBJDUMP EXAMPLE
//			///cygdrive/c/workspaceBCT/BCT_TestCasesProject/BCT/workspace_BCT_Testing/UninitializedVars/src/Worker.cpp:16
//			//4011fc:	55                   	push   %ebp
//			//4011fd:	89 e5                	mov    %esp,%ebp
//			//4011ff:	83 ec 0c             	sub    $0xc,%esp
//			//
//			//
//			//FIX FOR SCRIPT
//			//echo !!!BCT-ENTER: _ZN6Worker9calculateEii\n
//			//ni
//			//ni
//			//echo !!!BCT-stack\n
//			//bt
//			//echo !!!BCT-stack-end\n
//			//
//			//
//			{
//				String functionName = "_ZN6Worker9calculateEii:18 :::ENTER";
//				String model = "(*this != null)  ==>  (*this.state == 1)";
//				ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//				vd.addVariableData(new VariableData("*this", "!NULL"));
//				violations.add(vd);
//
//				TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "*this.state" );
//
//				assertTrue ( actualViolations.contains(vd) );
//			}
//		}
		
		{
			String functionName = "_ZN6Worker9calculateEii :::ENTER";
			String model = "t == 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("t", 1));
			violations.add(vd);
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			
			ViolationData tEnter;
			{
				String functionName = "_ZN6Worker9calculateEii :::ENTER";
				String model = "t == y";
				ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
				vd.addVariableData(new VariableData("t", 1));
				violations.add(vd);

				TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "y" );

				tEnter = vd;
				
			}
			
			ViolationData j20;
			{
				String functionName = "_ZN6Worker9calculateEii:20 :::ENTER";
				String model = "j == t";
				ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
				vd.addVariableData(new VariableData("t", 1));
				violations.add(vd);
				j20 = vd;
				
				TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "j" );
			}
			
			assertTrue ( actualViolations.contains(tEnter) || actualViolations.contains(j20) );
			
		}

		//Excluded because already reported as entry point
//		{
//			String functionName = "_ZN6Worker9calculateEii:18 :::ENTER";
//			String model = "t == 0";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("t", 1));
//			violations.add(vd);
//			
//			assertTrue ( actualViolations.contains(vd) );
//		}
		
		
		{
			String functionName = "_ZN6Worker9calculateEii:20 :::ENTER";
			String model = "(*this != null)  ==>  (*this.state == 1)";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("*this", "!NULL"));
			violations.add(vd);
			
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "*this.state" );
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "_ZN6Worker9calculateEii:20 :::ENTER";
			String model = "j == t";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("t", 1));
			violations.add(vd);
			
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "j" );
			
			assertTrue ( actualViolations.contains(vd) );
			
			
		}
		
		{
			String functionName = "_ZN6Worker9calculateEii:20 :::ENTER";
			String model = "j == t";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("t", 1));
			violations.add(vd);
			
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "j" );
			
			assertTrue ( actualViolations.contains(vd) );
			
			
		}
		
//		{
//			String functionName = "_ZN6Worker9calculateEii:20 :::ENTER";
//			String model = "myGlobal == -1";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("myGlobal", 0));
//			violations.add(vd);
//			
//			
//			assertTrue ( actualViolations.contains(vd) );
//			
//			
//		}
		
		
	}
}
