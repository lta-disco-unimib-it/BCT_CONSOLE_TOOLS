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

import java.io.File;
import java.io.FileNotFoundException;
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

import cpp.gdb.ModifiedFunctionsAnalysisResult;
import cpp.gdb.ModifiedFunctionsDetector;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.componentsDeclaration.Component;

public class TestModifiedReturnValueISSRE_Cpp {

	private static String originalSrc = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/Store_V1/src/" );
	private static String originalProgram = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/Store_V1/Store" );
	private static String modifiedProgram = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/Store_V2/Store" );
	private static String modifiedSrc = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/Store_V2/src/" );
	
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc+"..");
		TestsRunner.compile(modifiedSrc+"..");
	}
	
	
	@Test
	public void testModifiedFunctionsDetector() throws FileNotFoundException, CoreException, ConfigurationFilesManagerException{
		
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		ModifiedFunctionsDetector mfd = new ModifiedFunctionsDetector();
		
//		List<FileChangeInfo> fileChanges = mfd.extractDiffs(new File(originalSrc), new File(modifiedSrc) );
//		System.out.println(fileChanges);
//		assertEquals(1, fileChanges.size());
		
		ModifiedFunctionsAnalysisResult changesResult = mfd.getModifiedFunctionsAnalysisResult(new File(originalSrc), new File(modifiedSrc), 
				new File("/tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.objdump"), new File("/tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/modifiedSoftware.objdump"));
		
		System.out.println(changesResult.getModifiedFunctions());
		
		Component comp = changesResult.getModifiedFunctionsComponent();
		System.out.println(comp.getRules());
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
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "_ZN5Store12availableQtyESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
		}
		
		
//		{
//			String functionName = "_Z12assertEqualsll :::EXIT";
//			String model = "returnValue.eax == 0";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -1));
//			violations.add(vd);
//		}
		
		{
			String functionName = "_ZN5Store12availableQtyESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
		}
		
		{
			String functionName = "_ZN5Store12availableQtyESs :::EXIT";
			String model = "returnValue.eax >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("returnValue.eax", -1));
			violations.add(vd);
		}
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		assertEquals(violations.get(0), actualViolations.get(0));
		assertEquals(violations.get(1), actualViolations.get(1));
		assertEquals(violations.get(2), actualViolations.get(2));
	}
	
	
	@Test
	public void testMonitoringAndInference_Debugging() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator(); 
//		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});
//		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{});
		
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{"testNoItems", "testOneItem", "testOneItemNotAvailable", "testTwoItems", "testHandlerAllItemsAvailable" }); 
		TestsRunner.runMonitoringModified(modifiedProgram, new String[]{"testHandlerSomeItemsAvailable"});

		
		AnomaliesDebugger identifier = new AnomaliesDebugger();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
//		assertEquals(2,anomalies.size());
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:32 :::ENTER";
			String model = "items >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			violations.add(vd);
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:33 :::ENTER";
			String model = "items >= 1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			violations.add(vd);
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:28 :::ENTER";
			String model = "total >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", -1));
			violations.add(vd);
		}
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:33 :::ENTER";
			String model = "items != total";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			vd.addVariableData(new VariableData("total", -1));
			violations.add(vd);
		}
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:28 :::ENTER";
			String model = "total >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", -2));
			violations.add(vd);
		}
		
//		{
//			String functionName = "_Z12assertEqualsll :::EXIT";
//			String model = "returnValue.eax == 0";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -1));
//			violations.add(vd);
//		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE";
			String model = "StoreHandler::availableItems(Store&,list ):29";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.INTERACTION);
			vd.setViolationStatesNames(new String[]{"q5"});
			violations.add(vd);
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:36 :::ENTER";
			String model = "total one of { 73, 117 }";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", 71));
			violations.add(vd);
		}
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		assertEquals(violations.get(0), actualViolations.get(0));
		assertEquals(violations.get(1), actualViolations.get(1));
		assertEquals(violations.get(2), actualViolations.get(2));
	}
}
