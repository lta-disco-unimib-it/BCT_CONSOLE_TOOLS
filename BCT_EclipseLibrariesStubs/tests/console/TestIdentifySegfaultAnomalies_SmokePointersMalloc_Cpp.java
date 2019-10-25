package console;

import static org.junit.Assert.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import conf.EnvironmentalSetter;

import cpp.gdb.EnvUtil;

import tools.gdbTraceParser.GdbThreadTraceParser;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.JavaRunner;

/**
 * We check that we can spot anomalies that regard pointers to some reagion allocated with malloc.
 * 
 * Pointer "c" is expected to have a specific value in all the executions.
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@gmail.com
 *
 */
public class TestIdentifySegfaultAnomalies_SmokePointersMalloc_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/smoke_pointers_malloc" );
	private static String originalProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/smoke_pointers_malloc/program" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		
	}
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		//We need this at the beginning of the test to properly locate the bct.jar
		ProjectSetup projectVars = ProjectSetup.setupProject(originalSrc+"/An");
		
		HashMap<String, String> env = new HashMap<String,String>();
		env.put("CLASSPATH", DefaultOptionsManager.getBctJarFile().getAbsolutePath() );
		
		TestsRunner.executeCommand(originalSrc, new String[]{"make","CLASSPATH="+DefaultOptionsManager.getBctJarFile().getAbsolutePath(),"BCT-monitor"},env);
		
		List<String> vmArgs = new ArrayList<String>();
//		vmArgs.add("-D"+GdbThreadTraceParser.BCT_ALWAYS_TRACE_NOT_NULL+"=true");
		vmArgs.add("-Dbct.inference.daikon.config.default=essentials");
		vmArgs.add("-Dbct.check.removeInvalidIO=true");
		vmArgs.add("-Dbct.output.folder=bct_reports");
		vmArgs.add("-Dbct.inference.undoDaikonOptimizations=false");
		vmArgs.add("-Dbct.inference.skipArrays=true");
		vmArgs.add("-Dbct.maxHeap=1024M");
		
		
		List<String> args = new ArrayList<String>();
		args.add(originalSrc+"/An");
		
		JavaRunner.runMainInClass(IdentifySegFaultAnomalies.class, vmArgs, args, 0, null, false, null, null);
		
		
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		List<BctModelViolation> anomalies = RegressionAnalysis.identifyRegressions(mrc, false).getViolationAnalysisResult().getFilteredViolations();
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		{
			String functionName = "main:23 :::ENTER";
			String model = "&c == 123456";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			TestsUtil.updateModelNames("&c == [0-9]+", "&c == 123456", functionName, actualViolations );
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "&c");
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "main:18 :::ENTER";
			String model = "&c == 123456";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			TestsUtil.updateModelNames("&c == [0-9]+", "&c == 123456", functionName, actualViolations );
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "&c");
			assertFalse("At this line we should not have  aviolation for &c. If we have a violation it could be because the way Daikon derives integer invariants for hex pointers is not compatible with the way RADAR transform hex values.", actualViolations.contains(vd));
		}
		
		for ( ViolationData vd : actualViolations ){
			
				assertFalse("The tool should not have inferred OneOf models "+vd, vd.getModel().contains("one of") );
			
		}
		
		for ( ViolationData vd : actualViolations ){
			
			assertFalse("The tool should not have inferred models for arrays "+vd, vd.getModel().contains("[") );
		
		}
		
		for ( ViolationData vd : actualViolations ){
			
			assertFalse("The tool should not have inferred ShiftZero models "+vd, vd.getModel().contains(">>") );
		
		}
	
	}
	
}
