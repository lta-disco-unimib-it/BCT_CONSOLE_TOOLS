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
import org.junit.Test;

import cpp.gdb.FileChangeInfo;
import cpp.gdb.ModifiedFunctionsAnalysisResult;
import cpp.gdb.ModifiedFunctionsDetector;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData.Type;
import util.componentsDeclaration.Component;

public class TestABB11_MoreBehavior_Cpp {

	private String originalSrc = TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/ABB-11" );
	private String originalProgram =TestsRunner.testPath(  "/home/BCT/workspace_BCT_Testing/ABB-11/ABB-11" );
	private String modifiedProgram = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/ABB-11_V2/ABB-11" );
	private String modifiedSrc = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/ABB-11_V2" );
	private String originalProgramMore = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/ABB-11/ABB-11_more" );
	private String modifiedProgramMore = TestsRunner.testPath( "/home/BCT/workspace_BCT_Testing/ABB-11_V2/ABB-11_more" );
	
	@BeforeClass
	public static void setupClass() throws IOException {
		TestsRunner.compile( "/home/BCT/workspace_BCT_Testing/ABB-11");
		TestsRunner.compile("/home/BCT/workspace_BCT_Testing/ABB-11_V2");
	}
	

	
	@Test
	public void testMonitoringAndInference_MoreBehavior() throws IOException, CoreException, ConfigurationFilesManagerException, SecurityException, IllegalArgumentException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		ScriptsGenerator generator = new ScriptsGenerator(); 
		generator.generateScripts(TestsRunner.defaultProjectDir, originalSrc, originalProgramMore, modifiedSrc, modifiedProgramMore);
		
		TestsRunner.runMonitoringOriginal(originalProgramMore, new String[]{});
		TestsRunner.runMonitoringModified(modifiedProgramMore, new String[]{});
		
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		List<BctModelViolation> anomalies = identifier.identifyAnomalies(TestsRunner.defaultProjectDir);
		
		AnomaliesIdentifier.processResults(anomalies);
		
//		assertEquals(3,anomalies.size());
		
		List<ViolationData> violations = new ArrayList<ViolationData>();
		
		{
			String functionName = "_ZN4Data7getDataEv ";
			String model = "!SIGNAL!SIGABRT";
			ViolationData vd = new ViolationData(functionName,model,Type.INTERACTION);
			vd.setViolationStatesNames(new String[]{"q1"});
			violations.add(vd);
		}
		
		
		
		
		List<ViolationData> actualViolations = ViolationsUtil.getViolationData(anomalies);
//		assertEquals(violations, actualViolations);
		assertTrue( actualViolations.contains(violations.get(0)) );

	}
}
