package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;

public class URIMapperFactory {
	
	public static URIAbstractMapper getMapper(MonitoringConfiguration mc) {
		Resource resource = MonitoringConfigurationRegistry.getInstance().getResource(mc);
		
		StorageConfiguration sc = mc.getStorageConfiguration();
		
		if (sc instanceof FileStorageConfiguration)
			return new URIFileMapper(resource);
		
		//TODO: Database storage case.
		return null;
	}
}
