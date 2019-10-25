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
import java.util.Iterator;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import cpp.gdb.EnvUtil;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

public class TestModifiedReturnValue_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/WorkersMap/src/" );
	private static String originalProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/WorkersMap/WorkersMap" );
	private static String modifiedProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/WorkersMap-v2/WorkersMap" );
	private static String modifiedSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/WorkersMap-v2/src/" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc+"..");
		TestsRunner.compile(modifiedSrc+"..");
	}
	
	@Test
	public void testMonitoringAndInference_Anomaly() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
//		assertEquals(2,anomalies.size());
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "_ZN10WorkersMap9getSalaryESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
		}
		
		
		{
			String functionName = "_ZN10WorkersMap9getSalaryESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
		}
		
		

		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		assertEquals(violations.get(0), actualViolations.get(0));
		assertEquals(violations.get(1), actualViolations.get(1));
		
	}
	
	
	@Test
	public void testMonitoringAndInference_Debugging() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator(); 
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
//		assertEquals(2,anomalies.size());
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		Iterator<ViolationData> actualViolationsIterator = actualViolations.iterator();
		{
			String functionName = "_ZN10WorkersMap9getSalaryESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
			
		}
		
		
		{
			String functionName = "_Z12assertEqualsll :::ENTER";
			String model = "v1 >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("v1", -1));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}

//		this is filtered out
//		{
//			String functionName = "_Z12assertEqualsll :::EXIT";
//			String model = "v1 one of { 0, 10000, 50000 }";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("v1", -1));
//			violations.add(vd);
//		}
		
		
		
		{
			String functionName = "_ZN10WorkersMap9getSalaryESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		
//		{
//			String functionName = "_Z12assertEqualsll :::EXIT";
//			String model = "returnValue.eax == 0";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -1));
//			violations.add(vd);
//		}
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:50 :::ENTER";
			String model = "salary >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("salary", -1));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:50 :::ENTER";
			String model = "salary >= workers";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("salary", -1));
			vd.addVariableData(new VariableData("workers", 0));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:53 :::ENTER";
			String model = "salary > workers";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("salary", -1));
			vd.addVariableData(new VariableData("workers", 0));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:54 :::ENTER";
			String model = "totalSalary > workers";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("totalSalary", -1));
			vd.addVariableData(new VariableData("workers", 0));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:49 :::ENTER";
			String model = "totalSalary >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("totalSalary", -1));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:49 :::ENTER";
			String model = "totalSalary >= workers";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("totalSalary", -1));
			vd.addVariableData(new VariableData("workers", 1));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:54 :::ENTER";
			String model = "salary <= totalSalary";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("salary", 10000));
			vd.addVariableData(new VariableData("totalSalary", 9999));
			violations.add(vd);
			assertEquals(vd, actualViolationsIterator.next() );
		}
		
		//TODO: why the following two violations aren't caught???
//		{
//			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:54 :::ENTER";
//			String model = "totalSalary one of { 60000, 118000 }";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("totalSalary", 59999));
//			violations.add(vd);
//			assertEquals(vd, actualViolationsIterator.next() );
//		}
//		
//		{
//			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE:54 :::ENTER";
//			String model = "workers one of { 2, 6 }";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("workers", 3));
//			violations.add(vd);
//			assertEquals(vd, actualViolationsIterator.next() );
//		}
		
		
		{
			String functionName = "_ZN10WorkersMap16getAverageSalaryESt4listISsSaISsEE :::EXIT";
			String model = "returnValue.eax one of { 19666, 30000 }";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", 19999));
			violations.add(vd);
			assertTrue( actualViolations.contains(vd) );
		}
		
		
		
//		assertEquals(violations.get(0), actualViolations.get(0));
//		assertEquals(violations.get(1), actualViolations.get(1));
//		assertEquals(violations.get(2), actualViolations.get(2));
//		assertEquals(violations.get(3), actualViolations.get(3));
//		assertEquals(violations.get(4), actualViolations.get(4));
		
	}
}
