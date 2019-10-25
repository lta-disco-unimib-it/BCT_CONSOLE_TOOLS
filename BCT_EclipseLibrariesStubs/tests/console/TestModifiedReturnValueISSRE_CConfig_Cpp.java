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

import static org.junit.Assert.assertTrue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import modelsViolations.BctModelViolation;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData.Type;
import util.JavaRunner;

public class TestModifiedReturnValueISSRE_CConfig_Cpp {

	private static String modifiedProgram = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/FaultyStore/StoreTest" );
	private static String modifiedSrc = TestsRunner.testPath("/home/BCT/workspace_BCT_Testing/FaultyStore/src/" );
	
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/FaultyStore/");
	}
	

	@Test
	public void testMonitoringAndInference_Debugging() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		DebuggingScriptsGenerator generator = new DebuggingScriptsGenerator(); 
//		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, modifiedSrc, modifiedProgram);
		
		
		File BCT_workspace = new File( TestsRunner.defaultProjectDir );
		File BCT_Prj = new File ( BCT_workspace, "BCT" );
		File BCT_conf = new File(BCT_Prj, "BCT.bctmc");
		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(BCT_conf);
		CConfiguration regressionConfig = (CConfiguration) mc.getAdditionalConfigurations().get(CConfiguration.class);
		
		assertTrue(regressionConfig.isExcludeUnusedVariables());
	
	

//		TestsRunner.runMonitoringOriginal(modifiedProgram, new String[]{"testHandlerOneOfTwoItemsAvailable"}); 
		TestsRunner.runMonitoringOriginal(modifiedProgram, new String[]{"testHandlerOneOfTwoItemsAvailable", "testOneItemNotAvailable", "testNoItems", "testOneItem", "testTwoItems", "testHandlerOneItemAvailable", "testHandlerAllItemsAvailable" }); 
		TestsRunner.runMonitoringOriginalToVerify(modifiedProgram, new String[]{"testHandlerSomeItemsAvailable"});

		
		AnomaliesDebugger identifier = new AnomaliesDebugger();
		
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
//		assertEquals(2,anomalies.size());
		
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:32 :::ENTER";
			String model = "items >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:33 :::ENTER";
			String model = "items >= 1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:28 :::ENTER";
			String model = "total >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:32 :::ENTER";
			String model = "items != total";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			vd.addVariableData(new VariableData("total", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:28 :::ENTER";
			String model = "total >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", -2));
			assertTrue(actualViolations.contains(vd));
		}
		
//		{
//			String functionName = "_Z12assertEqualsll :::EXIT";
//			String model = "returnValue.eax == 0";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -1));
//			violations.add(vd);
//		}
		
		{
			for ( ViolationData vd : actualViolations ){
				if ( vd.getType() ==  Type.INTERACTION ){
					String[] names = vd.getViolationStatesNames();
					for ( int i = 0 ; i< names.length ; i++ ){
						names[i]="qX";
					}
					vd.setViolationStatesNames(names);
				}
			}
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE ";
			String model = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:29 " ;/// on May-2-2013 was: "StoreHandler::availableItems(Store&,list ):29";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.INTERACTION);
			vd.setViolationStatesNames(new String[]{"qX"});
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:36 :::ENTER";
			String model = "total one of { 7, 37, 117 }";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", 71));
			assertTrue(actualViolations.contains(vd));
		}
		
//		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
//		assertEquals(violations.get(0), actualViolations.get(0));
//		assertEquals(violations.get(1), actualViolations.get(1));
//		assertEquals(violations.get(2), actualViolations.get(2));
	}
	
	
	@Test
	public void testMonitoringAndInference_Debugging_Console() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{

		List<String> args = Arrays.asList( new String[]{TestsRunner.defaultProjectDir, modifiedSrc, modifiedProgram});
		JavaRunner.runMainInClass(DebuggingScriptsGenerator.class, args, 0);
		
		File BCT_workspace = new File( TestsRunner.defaultProjectDir );
		File BCT_Prj = new File ( BCT_workspace, "BCT" );
		File BCT_conf = new File(BCT_Prj, "BCT.bctmc");
		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(BCT_conf);
		CConfiguration regressionConfig = (CConfiguration) mc.getAdditionalConfigurations().get(CConfiguration.class);
		
		assertTrue(regressionConfig.isExcludeUnusedVariables());

		
		TestsRunner.runMonitoringOriginal(modifiedProgram, new String[]{"testHandlerOneOfTwoItemsAvailable", "testOneItemNotAvailable", "testNoItems", "testOneItem", "testTwoItems", "testHandlerOneItemAvailable", "testHandlerAllItemsAvailable" }); 
		TestsRunner.runMonitoringOriginalToVerify(modifiedProgram, new String[]{"testHandlerSomeItemsAvailable"});

		
		args = Arrays.asList( new String[]{TestsRunner.defaultProjectDir});
		JavaRunner.runMainInClass(AnomaliesDebugger.class, args, 0);
		
		ProjectSetup projectVars = ProjectSetup.setupProject(TestsRunner.defaultProjectDir);
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		List<BctModelViolation> anomalies = RegressionAnalysis.identifyRegressions(mrc, false).getViolationAnalysisResult().getFilteredViolations();
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:32 :::ENTER";
			String model = "items >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:33 :::ENTER";
			String model = "items >= 1";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:28 :::ENTER";
			String model = "total >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:32 :::ENTER";
			String model = "items != total";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("items", -1));
			vd.addVariableData(new VariableData("total", -1));
			assertTrue(actualViolations.contains(vd));
		}
		
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:28 :::ENTER";
			String model = "total >= 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", -2));
			assertTrue(actualViolations.contains(vd));
		}
		
//		{
//			String functionName = "_Z12assertEqualsll :::EXIT";
//			String model = "returnValue.eax == 0";
//			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
//			vd.addVariableData(new VariableData("returnValue.eax", -1));
//			violations.add(vd);
//		}
		
		{
			for ( ViolationData vd : actualViolations ){
				if ( vd.getType() ==  Type.INTERACTION ){
					String[] names = vd.getViolationStatesNames();
					for ( int i = 0 ; i< names.length ; i++ ){
						names[i]="qX";
					}
					vd.setViolationStatesNames(names);
				}
			}
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE ";
			String model = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:29 " ;/// on May-2-2013 was: "StoreHandler::availableItems(Store&,list ):29";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.INTERACTION);
			vd.setViolationStatesNames(new String[]{"qX"});
			assertTrue(actualViolations.contains(vd));
		}
		
		{
			String functionName = "_ZN12StoreHandler14availableItemsER5StoreSt4listISsSaISsEE:36 :::ENTER";
			String model = "total one of { 7, 37, 117 }";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("total", 71));
			assertTrue(actualViolations.contains(vd));
		}
		
	}
	
}
