package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization;

import static org.junit.Assert.*;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.junit.Test;

import tools.violationsAnalyzer.BctViolationsAnalysisResult;
import tools.violationsAnalyzer.anomalyGraph.AnomalyGraph;

public class BctViolationsAnalysisSerializerTest {

	@Test
	public void testSerialize() throws FileNotFoundException {
		BctIOModelViolation ioViol = ceateIoViolation();
		
		String failureId = "1";
		BctViolationsAnalysisResult result = new BctViolationsAnalysisResult(failureId , new ArrayList<AnomalyGraph>(), null);
		
		BctViolationsAnalysisConfiguration conf = new BctViolationsAnalysisConfiguration();
		RegressionAnalysisResult violationAnalysisResult = new RegressionAnalysisResult(failureId);
		List<BctModelViolation> filteredViolations = new ArrayList<BctModelViolation>();
		filteredViolations.add(ioViol);
		violationAnalysisResult.setFilteredViolations(filteredViolations );
		
		conf.setFilteringResult(violationAnalysisResult );
		
		
		File dest = new File("tests/artifacts/unit/violationsAnalysis/testSerialize1.xml");
		
		BctViolationsAnalysisSerializer.serialize(dest, conf);
		
		BctViolationsAnalysisConfiguration conf1 = BctViolationsAnalysisDeserializer.deserialize(dest);
		
		assertEquals(conf1.getFilteringResult(), conf.getFilteringResult());
	}

	public BctIOModelViolation ceateIoViolation() {
		String[] st1 = new String[]{
				"javax.servlet.jsp.JspFactory.getDefaultFactory:75",
				"eltest.ChipsListener.contextInitialized:18",
				"org.apache.catalina.core.StandardContext.listenerStart:3827",
				"org.apache.catalina.core.StandardContext.start:4336",
				"org.apache.catalina.core.ContainerBase.addChildInternal:760",
				"org.apache.catalina.core.ContainerBase.addChild:740",
				"org.apache.catalina.core.StandardHost.addChild:525",
				"org.apache.catalina.startup.HostConfig.deployWAR:825",
				"org.apache.catalina.startup.HostConfig.deployWARs:714",
				"org.apache.catalina.startup.HostConfig.deployApps:490",
				"org.apache.catalina.startup.HostConfig.start:1138",
				"org.apache.catalina.startup.HostConfig.lifecycleEvent:311",
				"org.apache.catalina.util.LifecycleSupport.fireLifecycleEvent:120",
				"org.apache.catalina.core.ContainerBase.start:1022",
				"org.apache.catalina.core.StandardHost.start:719",
				"org.apache.catalina.core.ContainerBase.start:1014",
				"org.apache.catalina.core.StandardEngine.start:443",
				"org.apache.catalina.core.StandardService.start:451",
				"org.apache.catalina.core.StandardServer.start:710",
				"org.apache.catalina.startup.Catalina.start:552",
				"sun.reflect.NativeMethodAccessorImpl.invoke0:-2",
				"sun.reflect.NativeMethodAccessorImpl.invoke:39",
				"sun.reflect.DelegatingMethodAccessorImpl.invoke:25",
				"java.lang.reflect.Method.invoke:585",
				"org.apache.catalina.startup.Bootstrap.start:288",
				"org.apache.catalina.startup.Bootstrap.main:413",
		};
		
		BctIOModelViolation viol1 = new BctIOModelViolation(
				"1",
				"javax.servlet.jsp.JspFactory.getDefaultFactory(()Ljavax.servlet.jsp.JspFactory;):::EXIT",
				"returnValue != null",
				BctModelViolation.ViolationType.NOT_VALID,
				System.currentTimeMillis(),
				new String[0],
				new String[0],
				st1,
				"1",
				"1",
				"");
		
		return viol1;
	}

}
