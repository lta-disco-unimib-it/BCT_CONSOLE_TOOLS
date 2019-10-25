package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.Properties;

import conf.TestCasesRegistrySettings;

public interface TestCasesRegistryOptions {
	
	/**
	 * Returns the properties corresponding to this option object
	 * @param mc 
	 * 
	 * @return
	 */
	public TestCasesRegistrySettings getSettings(MonitoringConfiguration mc);

}
