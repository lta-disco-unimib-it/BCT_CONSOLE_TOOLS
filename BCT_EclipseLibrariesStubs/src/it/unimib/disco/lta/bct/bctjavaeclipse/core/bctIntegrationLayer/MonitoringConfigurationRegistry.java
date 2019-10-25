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
