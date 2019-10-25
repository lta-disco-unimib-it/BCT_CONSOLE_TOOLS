package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.Properties;

import conf.ExecutionContextRegistrySettings;
import conf.TestCasesRegistrySettings;
import executionContext.ExecutionContextStateMaintainerMemory;
import executionContext.TestCasesRegistry;

public class TestCasesMemoryRegistryOptions implements TestCasesRegistryOptions {

	public TestCasesMemoryRegistryOptions(){};
	
	public TestCasesRegistrySettings getSettings(MonitoringConfiguration mc) {
		Properties p = new Properties();
		
		p.setProperty(ExecutionContextRegistrySettings.Options.stateRecorderType,
				ExecutionContextStateMaintainerMemory.class.getCanonicalName());
		
		p.setProperty("type", TestCasesRegistry.class.getCanonicalName());
		
		return new TestCasesRegistrySettings(executionContext.TestCasesRegistry.class, p);
	}

}
