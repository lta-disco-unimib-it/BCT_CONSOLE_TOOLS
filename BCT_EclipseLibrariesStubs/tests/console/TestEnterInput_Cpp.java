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

public class TestEnterInput_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/EnterAddress" );
	private static String originalProgram =TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/EnterAddress/program" );
	private static String modifiedProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/EnterAddress-v2/program" );
	private static String modifiedSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/EnterAddress-v2" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/EnterAddress/");
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/EnterAddress-v2/");
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
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		
		
		{
			String functionName = "f :::ENTER";
			String model = "x == 3";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("x", 4));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "p :::ENTER";
			String model = "global == 0";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("global", -1));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		
		{
			String functionName = "func :::ENTER";
			String model = "x == 5";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("x", 9));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "func :::ENTER";
			String model = "y == 7";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("y", 17));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
		
		{
			String functionName = "f :::ENTER";
			String model = "x == 3";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			vd.addVariableData(new VariableData("x", 5));
			violations.add(vd);
			
			
			assertTrue ( actualViolations.contains(vd) );
		}
		
	}
		
}
