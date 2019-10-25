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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.core.runtime.CoreException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import cpp.gdb.CSourcesFilter;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.SourceLinesMapper;

import tools.fshellExporter.CBMCAssertionsInjector;
import tools.fshellExporter.CBMCExecutor;
import tools.fshellExporter.CBMCModelsExporter;
import tools.fshellExporter.CBMCModelsFilter;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.FileUtil;
import util.ProcessRunner;

public class TestCBMCIntegration_StructPointers {

	private static String originalSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/StructPointers" );
	private String originalProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/StructPointers/program" );
	private String modifiedProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/StructPointers_v2/program" );
	private static String modifiedSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/StructPointers_v2" );

//	private File monitoredFunctionsFile = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.original.ser");
//	private File monitoredFunctionsV1File = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.modified.ser");
	
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile( originalSrc);
		TestsRunner.compile(modifiedSrc);
	}
	
	
	
	@Ignore
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{});


		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		VARTRegressionConfiguration conf = new VARTRegressionConfiguration();
		CBCMRegressionsDetector regressionsDetector = new CBCMRegressionsDetector(conf,ModelsFetcherFactoy.modelsFetcherInstance);
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		
		Set<String> regressedProperties = regressionsDetector.identifyRegressions(mrc);
		
		System.out.println(regressedProperties);
//		HashSet<String> expected = new HashSet<String>();
//		expected.add("program.c\tcalcAvailableQty\tassert( return >= 0 );");
		
//		assertEquals(expected, regressedProperties);
		
//		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
	}

	

	

//	private void updateReturnTypes() throws FileNotFoundException, IOException, ClassNotFoundException {
//		Map<String, FunctionMonitoringData> functionsData = FunctionMonitoringDataSerializer.load(monitoredFunctionsFile);
//		for ( FunctionMonitoringData fd : functionsData.values() ){
//			if ( fd.isImplementedWithinProject() ){
//				String returnType = null; //HACK
//				fd.setReturnType(returnType);
//			}
//		}
//		
//		FunctionMonitoringDataSerializer.store(functionsData.values(), monitoredFunctionsFile);
//	}
}
