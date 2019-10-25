package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import java.io.File;

import org.eclipse.core.resources.ResourcesPlugin;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;

public class MonitoringConfigurationRegistry {

	private static final MonitoringConfigurationRegistry instance = new MonitoringConfigurationRegistry();
	
	public static MonitoringConfigurationRegistry getInstance() {
		return instance;
	}

	public Resource getResource(MonitoringConfiguration mc) {
		File workspace = ResourcesPlugin.getWorkspace().getRoot().getFile();
		StorageConfiguration sc = mc.getStorageConfiguration();
		if (sc instanceof FileStorageConfiguration) {
			FileStorageConfiguration fsc = (FileStorageConfiguration) sc;
			return new ResourceFile(null, workspace.getAbsolutePath()+"/"+fsc.getDataDirPath(), null);
		}
		return null;
	}
}
