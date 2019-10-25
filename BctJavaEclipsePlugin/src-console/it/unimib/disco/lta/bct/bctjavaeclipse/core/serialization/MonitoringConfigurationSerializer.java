package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class MonitoringConfigurationSerializer {

	public static void serialize(File destination, MonitoringConfiguration monitoringConfiguration) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new  FileOutputStream(destination)));
		
		
		System.out.println("Scrittura oggetto in Xml iniziata");
		try{
			//System.out.println(monitoringConfiguration.getStorageConfiguration());
			encoder.writeObject(monitoringConfiguration);
		}
		catch ( Throwable t ){

			t.printStackTrace();
			Logger.getInstance().log(t);
		}
		encoder.close();
		
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(destination.getAbsolutePath()));
		
		try {
			if ( file.exists() ){
				file.touch(new NullProgressMonitor());
			}
		} catch (CoreException e) {
			Logger.getInstance().log(e);
		}
		
		System.out.println("Scrittura oggetto in Xml riuscita");

	}
}
