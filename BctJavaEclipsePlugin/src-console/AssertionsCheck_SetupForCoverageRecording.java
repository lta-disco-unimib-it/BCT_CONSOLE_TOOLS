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
import console.ProjectSetup;
import console.ScriptsGeneratorJava;


public class AssertionsCheck_SetupForCoverageRecording {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ConfigurationFilesManagerException, InvocationTargetException {
		ScriptsGeneratorJava g = new ScriptsGeneratorJava();
		g.generateScripts(args[0]);
		
		String[] toInstrument = Arrays.copyOfRange(args, 2, args.length );
		
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);

		File mcFile = projectVars.getMonitoringConfigurationFile();
		
		
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(mcFile);
		{
			Component component = mrc.getComponentsConfiguration().getComponents().iterator().next();
			List<MatchingRule> rules = component.getRules();
			rules.clear();
			String[] classesRules = args[1].split(":");
			for ( String classRule : classesRules ){
				if ( classRule.isEmpty() ){
					continue;
				}
				
				String[] rule = classRule.split(",");
				String classExpr = rule[1];
				String packageExpr = rule[0];
				rules.add(new MatchingRuleInclude(packageExpr, classExpr, ".*"));
			}
		}
		
		TestCasesMonitoringOptions tc = mrc.getTestCasesMonitoringOptions();
		tc.setMonitorTestCases(true);
		
		List<Component> testCasesGroupsToMonitor = new ArrayList<Component>();
		
		List<MatchingRule> rules = new ArrayList<MatchingRule>();
		rules.add(new MatchingRuleInclude(".*", "Test.*", "test.*"));
		rules.add(new MatchingRuleInclude(".*", ".*Test", "test.*"));

		Component testsComponent = new Component("tests", rules);
		testCasesGroupsToMonitor.add(testsComponent);
		tc.setTestCasesGroupsToMonitor(testCasesGroupsToMonitor);


		MonitoringConfigurationSerializer.serialize(mcFile, mrc);



	}

}
