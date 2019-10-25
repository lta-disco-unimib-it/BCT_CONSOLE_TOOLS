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
import org.junit.Ignore;
import org.junit.Test;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

public class TestFork_Cpp {

	private static String originalSrc = "/home/BCT/workspace_BCT_Testing/ForkExample/src";
	private static String originalProgram = "/home/BCT/workspace_BCT_Testing/ForkExample/Test";
	private static String modifiedProgram = "/home/BCT/workspace_BCT_Testing/ForkExample-V2/Test";
	private static String modifiedSrc = "/home/BCT/workspace_BCT_Testing/ForkExample-V2/src";
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc);
		TestsRunner.compile(modifiedSrc);
	}
	
	@Ignore
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "_ZN6Worker9calculateEii :::ENTER";
			String model = "myGlobal == -1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("myGlobal", 0));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "_ZN6Worker9calculateEii :::ENTER";
			String model = "x == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("x", 1));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		
		{
			String functionName = "_ZN6Worker9calculateEii:11 :::ENTER";
			String model = "myGlobal == -1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("myGlobal", 0));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
			
			
		}
		
		{
			//This is for the child
			String functionName = "_ZN6Worker9calculateEii :::ENTER";
			String model = "x == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("x", 2));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
	}
}
