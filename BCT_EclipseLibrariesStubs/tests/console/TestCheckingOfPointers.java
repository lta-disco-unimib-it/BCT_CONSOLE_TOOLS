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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;

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

import tools.gdbTraceParser.GdbThreadTraceParser;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData.Type;
import util.JavaRunner;
import util.componentsDeclaration.Component;

/**
 * This test checks if in the exit of methods we record local variables and fields
 * @author fabrizio
 *
 */
public class TestCheckingOfPointers {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/smoke_pointers" );
	private static String originalProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/smoke_pointers/program" );

	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile(originalSrc);
	}
	
	
	
	
	@Test
	public void testIntegerPointers() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		 
		
		ScriptsGenerator generator = new ScriptsGenerator();
		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram );
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"-1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"1","-1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"1","-1","1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"1","2","3","4","5","6","7","8","9","-1"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"1","2","3","4","-1"});

		TestsRunner.runMonitoringOriginalToVerify(originalProgram, new String[]{"1"});
		
		
		List<String> vmArgs = new ArrayList<String>();
		vmArgs.add("-D"+tools.InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS+"=false");
		
		List<String> args = new ArrayList<String>();
		args.add(TestsRunner.defaultProjectDir);
		
		JavaRunner.runMainInClass(IdentifySegFaultAnomalies.class, vmArgs, args, 0, null, false, null, null);
		
		MonitoringConfiguration mc = TestsRunner.getDefaultMonitoringConfiguration(TestsRunner.defaultProjectDir);
		List<BctModelViolation> anomalies = RegressionAnalysis.identifyRegressions(mc, false).getViolationAnalysisResult().getFilteredViolations();

		
		AnomaliesIdentifier.processResults(anomalies);
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
//		assertEquals(3,anomalies.size());
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		

		
		{
			String functionName = "main:23 :::ENTER";
			String model = "end >= ptr";
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "end");
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "ptr");
			
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			
			violations.add(vd);
			
			assertTrue(  actualViolations.contains(vd) );
		}
		
		
		
		
		
//		assertEquals(violations, actualViolations);
//		assertEquals( violations.get(0)) );



	}
}
