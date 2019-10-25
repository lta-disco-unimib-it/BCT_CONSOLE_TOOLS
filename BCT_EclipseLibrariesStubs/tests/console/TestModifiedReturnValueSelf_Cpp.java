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

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

public class TestModifiedReturnValueSelf_Cpp {

	private static String originalSrc = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/WorkersMap-self/src/");
	private static String originalProgram = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/WorkersMap-self/V1");
	private static String modifiedProgram = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/WorkersMap-self/V2");
	private static String modifiedSrc = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/WorkersMap-self/src/");
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc+"..");
		TestsRunner.compile(modifiedSrc+"..");
	}
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.setMonitorAllIfNoChange(true); //from 11/09/2013 it is necessary to explicitely state to monitor everything in case of no change 
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
//		assertEquals(3,anomalies.size());
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE :::EXIT";
			String model = "returnValue.eax one of { 19666, 30000 }";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", 19999));
			violations.add(vd);
		}
		
		
		{
			String functionName = "_ZN10WorkersMap9getSalaryESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
		}

		{
			String functionName = "_Z12assertEqualsll :::ENTER";
			String model = "v1 == v2";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("v1", 0));
			vd.addVariableData(new VariableData("v2", -1));
			violations.add(vd);
		}
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
//		assertEquals(violations, actualViolations);
		assertTrue( actualViolations.contains(violations.get(0)) );
		assertTrue( actualViolations.contains(violations.get(1)) );
		assertTrue( actualViolations.contains(violations.get(2)) );
	}
}
