package console;

import static org.junit.Assert.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import check.ioInvariantParser.LocalVariablesWrapper;

import cpp.gdb.FileChangeInfo;
import cpp.gdb.ModifiedFunctionsAnalysisResult;
import cpp.gdb.ModifiedFunctionsDetector;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData.Type;
import util.componentsDeclaration.Component;

/**
 * This test checks if the monitoring o fthe return instruction constrst with the monitoring of the last line, when all the lines are monitored
 * @author fabrizio
 *
 */
@Ignore
public class TestABB11_V4_Cpp {

	private static String originalSrc = "/home/BCT/workspace_BCT_Testing/ABB-11_V4_orig";
	private static String originalProgram = "/home/BCT/workspace_BCT_Testing/ABB-11_V4_orig/ABB";
	private static String modifiedProgram = "/home/BCT/workspace_BCT_Testing/ABB-11_V4/ABB";
	private static String modifiedSrc = "/home/BCT/workspace_BCT_Testing/ABB-11_V4";

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
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
//		assertEquals(3,anomalies.size());
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		 
		TestsUtil.removeAnomaly( anomalies, "_ZN4Data7getDataEv :::ENTER", "(*this != null)  ==>  (*this.empty == 97196323)");
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		//check if at the exit point we properly record state variables (and locals too)
		{
			String functionName = "_ZN4Data7getDataEv:29 :::ENTER";
			String model = "*this.data == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("*this", "!NULL"));//new LocalVariablesWrapper("*this", null, null)
			vd.addVariableData(new VariableData("*this.data",-1)); 
			violations.add(vd);
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "_ZN4Data7getDataEv :::EXIT";
			String model = "*this.data == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("*this", "!NULL"));//new LocalVariablesWrapper("*this", null, null)
			vd.addVariableData(new VariableData("*this.data",-1)); 
			violations.add(vd);
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "_ZN4Data7getDataEv :::ENTER";
			String model = "*this.data == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("*this", "!NULL"));//new LocalVariablesWrapper("*this", null, null)
			vd.addVariableData(new VariableData("*this.data",30)); 
			violations.add(vd);
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "_ZN4Data7getDataEv:24 :::ENTER";
			String model = "*this.data == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("*this", "!NULL"));//new LocalVariablesWrapper("*this", null, null)
			vd.addVariableData(new VariableData("*this.data",30)); 
			violations.add(vd);
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "_ZN4Data7getDataEv ";
			String model =  "_ZN4Data7getDataEv:25 ";

			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.INTERACTION); 
			vd.setViolationStatesNames(new String[]{"q2"});
			violations.add(vd);
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "_ZN4Data7getDataEv:25 :::ENTER";
			String model = "*this.data == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("*this", "!NULL"));//new LocalVariablesWrapper("*this", null, null)
			vd.addVariableData(new VariableData("*this.data",30)); 
			violations.add(vd);
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		
		
		
		assertEquals(violations, actualViolations);
//		assertEquals( violations.get(0)) );
//		assertTrue( actualViolations.contains(violations.get(0)) );

	}


}
