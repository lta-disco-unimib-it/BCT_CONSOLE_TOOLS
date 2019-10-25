package console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;

import conf.EnvironmentalSetter;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConsoleHelper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.CleaningUtil;


public class ScriptsGeneratorJava extends ScriptsGenerator {

	

	


	///specifies the criterion to use to retrieve the object fields to save
	private static final String BCT_FLATTENER_FIELDS_RETRIEVER = "bct.flattener.fieldsRetriever";
	private static final String BCT_FLATTENER_DEPTH = "bct.flattener.depth";







	public static void main(String args[]) throws CoreException, ConfigurationFilesManagerException, FileNotFoundException{

		ScriptsGeneratorJava generator = new ScriptsGeneratorJava(); 

		execute(args, generator);
	}

	public static void execute(String[] args, ScriptsGeneratorJava generator)
			throws CoreException, FileNotFoundException,
			ConfigurationFilesManagerException {






			String projectDir = args[0];


			generator.generateScripts(projectDir );
		
	}



	public void generateScripts(String projectDir) {
		ProjectSetup projectVariables = ProjectSetup.setupProject(projectDir);

//		CConfiguration cConfig = createCConfiguration(originalExec, originalSrc);

		int flattenerDepth = Integer.getInteger(BCT_FLATTENER_DEPTH, 3);
		
		
		String flattenerFieldRetriever = System.getProperty(BCT_FLATTENER_FIELDS_RETRIEVER);
		if ( flattenerFieldRetriever == null ){
			flattenerFieldRetriever = "all";
		}
		
		FlattenerOptions opt = new FlattenerOptions(false,flattenerDepth,flattenerFieldRetriever,new ArrayList<String>());

		StorageConfiguration storageConfiguration = createStorageCOnfiguration(projectVariables);

		MonitoringConfiguration mc = MonitoringConfiguration.createMonitoringConfiguration("BCT", storageConfiguration, opt, "/"+projectVariables.getProjectName(), MonitoringConfiguration.ConfigurationTypes.JavaMonitoring  );
//		mc.putAdditionalConfiguration(CConfiguration.class, cConfig);




		try {
		
			addComponentsToMonitor(mc, projectVariables.getMonitoringConfigurationFile() );
			
			MonitoringConfigurationFactory.storeMonitoringConfiguration(projectVariables.getMonitoringConfigurationPath(), mc);

			
			CleaningUtil.deleteAllBctData(ConfigurationFilesManager.getBctHomeDir(mc));

			

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private  void addComponentsToMonitor(MonitoringConfiguration mc, File destination) throws FileNotFoundException, ConfigurationFilesManagerException {

		ComponentsConfiguration components = mc.getComponentsConfiguration();
		
		List<Component> componentsList = new ArrayList<Component>();
		
		components.setComponents(componentsList);


		componentsList.add(getMainComponentToMonitor());
		
		List<CallFilter> callFilters = new ArrayList<CallFilter>();
		List<MatchingRule> rules = callfilterRules();
		
		CallFilter cf = new CallFilterGlobal(rules);
		callFilters.add(cf);
		components.setCallFilters(callFilters);
		components.setMonitorInternalCalls(true);

		MonitoringConfigurationSerializer.serialize(destination, mc);
		

	}

	public List<MatchingRule> callfilterRules() {
		ArrayList<MatchingRule> rules = new ArrayList<MatchingRule>();
		
		List<MatchingRuleExclude> customRules = customCallFilterRules();
		
		if ( customRules != null ){
			rules.addAll(customRules);
			return rules;
		}
		
		
		rules.add(new MatchingRuleExclude(".*", ".*", "<init>"));
		rules.add(new MatchingRuleExclude("java.lang.*", ".*", ".*"));
		rules.add(new MatchingRuleExclude("java.util.*", ".*", ".*"));
		rules.add(new MatchingRuleExclude("java.io.*", ".*", ".*"));
		return rules;
	}

	private List<MatchingRuleExclude> customCallFilterRules() {
		List<String> stringRules = getCustomEnvExclusionRules();
		if ( stringRules == null )
			return null;
		return processUserDefinedEclusionRules(stringRules);
	}

	public void setCompleteMonitoring(boolean completeMonitoring) {
		this.completeMonitoring = completeMonitoring;

	}







	private static StorageConfiguration createStorageCOnfiguration(ProjectSetup projectVariables) {
		FileStorageConfiguration sc = new FileStorageConfiguration(projectVariables.getDataDirPath()); 

		return sc;
	}
}
