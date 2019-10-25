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

public class TestCBMCIntegration {

	private String originalSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/CBMCIntegration/src" );
	private String originalProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/CBMCIntegration/program" );
	private String modifiedProgram = TestsRunner.testPath (  "/home/BCT/workspace_BCT_Testing/CBMCIntegration-V2/program" );
	private String modifiedSrc = TestsRunner.testPath ( "/home/BCT/workspace_BCT_Testing/CBMCIntegration-V2/src" );

//	private File monitoredFunctionsFile = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.original.ser");
//	private File monitoredFunctionsV1File = new File("/tmp/BCT/BCT_DATA/BCT/conf/files/scripts/monitoredFunctions.modified.ser");
	
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile( "/home/BCT/workspace_BCT_Testing/CBMCIntegration");
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/CBMCIntegration-V2");
	}
	
	
	
	@Test
	public void testMonitoringAndInference() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
//		generator.setTraceAllLinesOfMonitoredFunctions(true);
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgram, modifiedSrc, modifiedProgram);
		
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"17"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"11","32","24"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"1","3","4","200","120"});
		TestsRunner.runMonitoringOriginal(originalProgram, new String[]{"1","300","4"});

		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		VARTRegressionConfiguration vartConfig = new VARTRegressionConfiguration();
		CBCMRegressionsDetector regressionsDetector = new CBCMRegressionsDetector(vartConfig,ModelsFetcherFactoy.modelsFetcherInstance);
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File( TestsRunner.testPath( "/tmp/BCT/BCT/BCT.bctmc") ));
		
		Set<String> regressedProperties = regressionsDetector.identifyRegressions(mrc);
		
		System.out.println(regressedProperties);
//		HashSet<String> expected = new HashSet<String>();
//		expected.add("program.c\tcalcAvailableQty\tassert( return >= 0 );");
		
//		assertEquals(expected, regressedProperties);
		
		assertTrue ( regressedProperties.contains("program.c\tcalcAvailableQty\tassert( return >= 0 );") );
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
