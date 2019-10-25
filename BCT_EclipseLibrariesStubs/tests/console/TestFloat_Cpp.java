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

public class TestFloat_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/Float/src" );
	private static String originalProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/Float/program" );
	private static String modifiedProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/Float_v2/program" );
	private static String modifiedSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/Float_v2/src" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/Float");
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/Float_v2");
	}
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		
		System.setProperty("bct.inference.daikon.config.default", "default");
		
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		System.setProperty("bct.inference.skipArrays","true");
		//System.setProperty("bct.inference.undoDaikonOptimizations", "true");
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		
		
		{
			String functionName = "main:26 :::ENTER";
			String model = "pointer != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pointer", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "main:49 :::ENTER";
			String model = "pnodes[3] != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pnodes[3]", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "main:63 :::ENTER";
			String model = "pointerToArr != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pointerToArr", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		 
		
		{
			String functionName = "main:38 :::ENTER";
			String model = "node.value == 3.3";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("node", "!NULL"));
			vd.addVariableData(new VariableData("node.value", 5.5));
			violations.add(vd);
			
			if ( ! actualViolations.contains(vd)  ){
				functionName = "main:38 :::ENTER";
				model = "node.value == nodes[3].value";
				vd = new ViolationData(functionName,model,ViolationData.Type.IO);
				vd.addVariableData(new VariableData("node.value", 5.5));
				vd.addVariableData(new VariableData("nodes[3].value", 3.3));
				violations.add(vd);
				
			}
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "calculateFloat :::ENTER";
			String model = "inp == 2.20000005";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("inp", -2.20000005));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateFloat :::EXIT";
			String model = "res == 6.4000001";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("res", -2.4000001));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateFloat :::EXIT";
			String model = "returnValue.eax == 6.4";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -2.4));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateDouble :::ENTER";
			String model = "inp == 1.1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("inp", -1.1));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		

		
		{
			String functionName = "calculateDouble :::EXIT";
			String model = "res == 4.2";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("res", -0.20000000000000018));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		//We don't have the following cause the model is "res == returnValue.eax"
//		{
//			String functionName = "calculateDouble :::EXIT";
//			String model = "returnValue.eax == 4.2";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -0.2));
//			violations.add(vd);
//
//
//			assertTrue ( actualViolations.contains(vd) );
//		}
		
		
		{
			String functionName = "main:67 :::ENTER";
			String model = "d == 5.2";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("d", 0.7999999999999998));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "main:67 :::ENTER";
			String model = "f == 6.4000001";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("f", -2.4000001));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		
	
	}
	@Ignore
	@Test
	public void testMonitoringAndInference_WithPointers() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		
		
		{
			String functionName = "main:26 :::ENTER";
			String model = "pointer != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pointer", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "main:49 :::ENTER";
			String model = "pnodes[3] != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pnodes[3]", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "main:63 :::ENTER";
			String model = "pointerToArr != null";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("pointerToArr", null));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		 
		
		{
			String functionName = "main:63 :::ENTER";
			String model = "(node != null)  ==>  (node.value == 3.3)";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("node", "!NULL"));
			vd.addVariableData(new VariableData("node.value", 5.5));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "calculateFloat :::ENTER";
			String model = "inp == 2.20000005";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("inp", -2.20000005));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateFloat :::EXIT";
			String model = "res == 6.4000001";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("res", -2.4000001));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateFloat :::EXIT";
			String model = "returnValue.eax == 6.4";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -2.4));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateDouble :::EXIT";
			String model = "inp == 1.1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("inp", -1.1));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "calculateDouble :::EXIT";
			String model = "inp == 1.1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("inp", -1.1));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "calculateDouble :::EXIT";
			String model = "res == 4.2";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("res", -0.20000000000000018));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		//We don't have the following cause the model is "res == returnValue.eax"
//		{
//			String functionName = "calculateDouble :::EXIT";
//			String model = "returnValue.eax == 4.2";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -0.2));
//			violations.add(vd);
//
//
//			assertTrue ( actualViolations.contains(vd) );
//		}
		
		
		{
			String functionName = "main:67 :::ENTER";
			String model = "d == 5.2";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("d", 0.7999999999999998));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		{
			String functionName = "main:67 :::ENTER";
			String model = "f == 6.4000001";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("f", -2.4000001));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
		
		
	
	}
	
	
	@Ignore
	@Test
	public void testMonitoringAndInference_TODO() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);

		List<ViolationData> violations = new ArrayList<ViolationData>();
		//Seems that at function entering gdb does not properly parse double values 
		{
			String functionName = "calculateDouble :::ENTER";
			String model = "inp == 1.1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("inp", -1.1));
			violations.add(vd);


			assertTrue ( actualViolations.contains(vd) );
		}
	}
}
