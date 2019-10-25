package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

/**
 * This interface maintain a list of defined components 
 *  
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public interface MonitoringConfigurationManager {
	
	public MonitoringConfiguration createMonitoringConfiguration();

	public void load(MonitoringConfiguration monitoringConfiguration);
	
}
