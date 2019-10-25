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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import conf.EnvironmentalSetter;

import cpp.gdb.EnvUtil;

import tools.gdbTraceParser.GdbThreadTraceParser;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.JavaRunner;

public class TestIdentifySegfaultAnomalies_SmokePointersChars_Cpp {

	private static String originalSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/smoke_pointers_char" );
	private static String originalProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/smoke_pointers_char/program" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		
	}
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		//We need this at the beginning of the test to properly locate the bct.jar
		ProjectSetup projectVars = ProjectSetup.setupProject(originalSrc+"/An");
		
		HashMap<String, String> env = new HashMap<String,String>();
		env.put("CLASSPATH", DefaultOptionsManager.getBctJarFile().getAbsolutePath() );
		
		TestsRunner.executeCommand(originalSrc, new String[]{"make","CLASSPATH="+DefaultOptionsManager.getBctJarFile().getAbsolutePath(),"BCT-monitor"},env);
		
		List<String> vmArgs = new ArrayList<String>();
//		vmArgs.add("-D"+GdbThreadTraceParser.BCT_ALWAYS_TRACE_NOT_NULL+"=true");
		vmArgs.add("-Dbct.inference.daikon.config.file=/home/BCT/workspace_BCT_Testing/smoke_pointers_char/conf.txt");
		vmArgs.add("-Dbct.check.dontComparePointersAndConstants=true");
		vmArgs.add("-Dbct.check.removeInvalidIO=true");
		vmArgs.add("-Dbct.output.folder=bct_reports");
		vmArgs.add("-Dbct.inference.undoDaikonOptimizations=true");
		vmArgs.add("-Dbct.inference.skipArrays=true");
		vmArgs.add("-Dbct.maxHeap=1024M");
		
		
		List<String> args = new ArrayList<String>();
		args.add(originalSrc+"/An");
		
		JavaRunner.runMainInClass(IdentifySegFaultAnomalies.class, vmArgs, args, 0, null, false, null, null);
		
		
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		List<BctModelViolation> anomalies = RegressionAnalysis.identifyRegressions(mrc, false).getViolationAnalysisResult().getFilteredViolations();
		AnomaliesIdentifier.processResults(anomalies);
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
		
		{
			String functionName = "main:23 :::ENTER";
			String model = "&end > &ptr";
			ViolationData vd = new ViolationData(functionName,model,ViolationData.Type.IO);
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "&end");
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, model, "&ptr");
			assertTrue(actualViolations.contains(vd));
		}
		
		{	
			String functionName = "main:25 :::ENTER";
			TestsUtil.updateModelNames("&ptr <= [0-9]+", "&ptr <= 123456", functionName, actualViolations);
			TestsUtil.removeVariableDataFromAnomaly(actualViolations, functionName, "&ptr <= 123456", "&ptr");
			ViolationData vd = new ViolationData(functionName,"&ptr <= 123456",ViolationData.Type.IO);
			assertFalse(actualViolations.contains(vd));
		}
		
		for ( ViolationData vd : actualViolations ){
			
				assertFalse("The tool shoul not have inferred OneOf models "+vd, vd.getModel().contains("one of") );
			
		}
		
		for ( ViolationData vd : actualViolations ){
			
			assertFalse("The tool shoul not have inferred models for arrays "+vd, vd.getModel().contains("[") );
		
		}
		
		for ( ViolationData vd : actualViolations ){
			
			assertFalse("The tool shoul not have inferred ShiftZero models "+vd, vd.getModel().contains(">>") );
		
		}
	
	}
	
}
