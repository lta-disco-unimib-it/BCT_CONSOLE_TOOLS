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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;

public class FileSystemDataManager {

	
	
	public static void deleteModels(MonitoringConfiguration mc) throws ConfigurationFilesManagerException{
		File modelsDir = ConfigurationFilesManager.getModelsDir(mc);
		
		if ( modelsDir.exists() ){
			deleteCascade( modelsDir );
		}
	}

	private static void deleteCascade(File fileToDelete) {
		if ( fileToDelete.isDirectory() ){
			File[] files = fileToDelete.listFiles();
			for ( File file : files ){
				deleteCascade(file);
			}
		}
		fileToDelete.delete();
	}

	public static void deleteNormalizedFiles(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		File tmpsDir = ConfigurationFilesManager.getPreprocessingDir(mc);
		
		if ( tmpsDir.exists() ){
			deleteCascade( tmpsDir );
		}
	}

	
	
}
