package console;

import static org.junit.Assert.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;

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

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

public class TestStructPointers_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/StructPointers/src" );
	private static String originalProgram = TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/StructPointers/program" );
	private static String modifiedProgram = TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/StructPointers_v2/program" );
	private static String modifiedSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/StructPointers_v2/src" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc+"/..");
		TestsRunner.compile(modifiedSrc+"/..");
	}
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		DebuggingScriptsGenerator generator = new DebuggingScriptsGenerator();
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "main:48 :::ENTER";
			String model = "pointerToArr != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pointerToArr", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "main:12 :::ENTER";
			String model = "pointer != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pointer", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		 
		
		{
			String functionName = "main:19 :::ENTER";
			String model = "node.value == 3";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			//svd.addVariableData(new VariableData("node", "!NULL"));
			vd.addVariableData(new VariableData("node.value", 5));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "main:35 :::ENTER";
			String model = "pnodes[3] != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pnodes[3]", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
	}
}
