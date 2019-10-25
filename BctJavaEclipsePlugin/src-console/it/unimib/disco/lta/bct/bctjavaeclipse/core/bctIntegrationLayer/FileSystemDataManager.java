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
