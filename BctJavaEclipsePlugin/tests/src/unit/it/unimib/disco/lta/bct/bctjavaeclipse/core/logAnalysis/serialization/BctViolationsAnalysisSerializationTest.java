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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfigurationFactory;
import it.unimib.disco.lta.ava.engine.configuration.ThresholdType;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AnomalyGraphResource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilterCorrectOut;
import tools.violationsAnalyzer.filteringStrategies.IdManager;
import tools.violationsAnalyzer.filteringStrategies.IdManagerAction;

public class BctViolationsAnalysisSerializationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSerialize() throws FileNotFoundException {
		BctViolationsAnalysisConfiguration c = new BctViolationsAnalysisConfiguration();
		
		String anomaliesGraphPath = "/Prova/agp";
		Set<String> actions = new HashSet<String>();
		Set<String> tests = new HashSet<String>();
		Set<String> processes = new HashSet<String>();
		
		actions.add("1");
		actions.add("2");
		
		tests.add("3");
		tests.add("4");
		
		processes.add("5");
		processes.add("6");
		
		c.setAnomaliesGraphsPath(anomaliesGraphPath );
		
		c.addFailingActions(actions);
		c.addFailingProcesses(processes);
		c.addFailingTests(tests);
		IdManager filteringStrategy = IdManagerAction.INSTANCE;
		
		c.defineFilteringStrategy(filteringStrategy);
		
		ArrayList<String> lfns = new ArrayList<String>();
		lfns.add("file1");
		
		c.setLogFilesNames(lfns);
		
		ViolationsAnalysisResult violationsAnalysisResult = new ViolationsAnalysisResult("1");
		String ar0name = "R1";
		String ar0path = "/P/R1";
		
		List<Set<String>> expectedCC = new ArrayList<Set<String>>();
		Set<String> expectedCCSet0 = new HashSet<String>();
		expectedCCSet0.add("V1");
		expectedCCSet0.add("V2");
		expectedCC.add(expectedCCSet0);
		
		Set<String> expectedCCSet1 = new HashSet<String>();
		expectedCCSet1.add("V3");
		expectedCCSet1.add("V4");
		expectedCCSet1.add("V5");
		expectedCC.add(expectedCCSet1);
		
		violationsAnalysisResult.newElement(ar0name, ar0path, null, 2, expectedCC, true);
		
		c.setViolationsAnalysisResult(violationsAnalysisResult);
		
		AvaConfiguration avaConf = AvaConfigurationFactory.createDefaultAvaConfiguration(3);
		
		c.setAvaConfiguration(avaConf);
		
		File file = new File("serialized.xml");
		BctViolationsAnalysisSerializer.serialize(file, c);
		
		
		BctViolationsAnalysisConfiguration loadedC = BctViolationsAnalysisDeserializer.deserialize(file);
		
		assertEquals(anomaliesGraphPath, loadedC.getAnomaliesGraphsPath());
		
		assertEquals(actions, loadedC.getFailingActions());
		
		assertEquals(processes, loadedC.getFailingProcesses());
		
		assertEquals(tests, loadedC.getFailingTests());
		
		assertEquals(lfns, loadedC.getLogFilesNames());
		
		assertEquals(filteringStrategy, loadedC.retrieveFilteringStrategy());
		
		ViolationsAnalysisResult loadedVAR = loadedC.getViolationsAnalysisResult();
		assertEquals("1",loadedVAR.getFailureId());
		ArrayList<AnomalyGraphResource> ar = loadedVAR.getResources();
		assertNotNull(ar);
		assertEquals(1, ar.size() );
		AnomalyGraphResource ar0 = ar.get(0);
		assertEquals(ar0name,ar0.getResourceName());
		assertEquals(ar0path, ar0.getResourcePath());
		
		List<Set<String>> CCels = ar0.getConnectedComponentsElements();
		Collections.sort(CCels, new Comparator<Set<String>>() {

			public int compare(Set<String> o1, Set<String> o2) {
				return o1.size()-o2.size();
			}
		});
		Set<String> observedCCSet0 = CCels.get(0);
		assertEquals(expectedCCSet0, observedCCSet0);
		
		Set<String> observedCCSet1 = CCels.get(1);
		assertEquals(expectedCCSet1, observedCCSet1);
		
		AvaConfiguration avaLoadedC = loadedC.getAvaConfiguration();
		assertNotNull(avaLoadedC);
		assertNotNull(avaLoadedC.getThresholds());
		assertTrue( ( avaConf.getThreshold(ThresholdType.compositePatternScore) -
				loadedC.getAvaConfiguration().getThreshold(ThresholdType.compositePatternScore) ) < 0.001);
	
		
		
	}

}
