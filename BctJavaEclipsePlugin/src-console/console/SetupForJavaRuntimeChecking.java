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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions.ProbeInstrumenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleInclude;

import modelsViolations.BctModelViolation;

public class SetupForJavaRuntimeChecking {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ConfigurationFilesManagerException, InvocationTargetException {
		
		
		String[] toInstrument = Arrays.copyOfRange(args, 1, args.length );
		
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);

		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
//		TestCasesMonitoringOptions tc = mrc.getTestCasesMonitoringOptions();
//		tc.setMonitorTestCases(true);
//		
//		List<Component> testCasesGroupsToMonitor = new ArrayList<Component>();
//		
//		List<MatchingRule> rules = new ArrayList<MatchingRule>();
//		rules.add(new MatchingRuleInclude(".*", "Test.*", "test.*"));
//		rules.add(new MatchingRuleInclude(".*", ".*Test", "test.*"));
//		
//		Component testsComponent = new Component("tests", rules);
//		testCasesGroupsToMonitor.add(testsComponent);
//		tc.setTestCasesGroupsToMonitor(testCasesGroupsToMonitor);
//		
//		MonitoringConfigurationSerializer.serialize(projectVars.getMonitoringConfigurationFile(), mrc);
//		
//		
		
		boolean useAspects = Boolean.getBoolean("bct.useAspects");
		if ( ! useAspects ){

			File script = ConfigurationFilesManager.getRuntimeCheckingProbeScript(mrc);

			SetupForJavaDataRecording.performInstrumentation(toInstrument, mrc, null, script);

		}
		
		
		
		//project setup
		//generate scripts
		//instrmemter
	}

}
