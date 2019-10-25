package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

public class URIBuilderFactory {
	
	public static URIAbstractBuilder getBuilder(MonitoringConfiguration mc) {
		if(mc.getStorageConfiguration() instanceof FileStorageConfiguration) {
			return new URIFileBuilder(mc);
		}
		return null;
	}
}
