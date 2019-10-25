package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ComponentsConfigurationSerializer {
	
	public static void serialize(File destination, ComponentsConfiguration componentsConfiguration) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new  FileOutputStream(destination)));
		
		System.out.println("Scrittura oggetto in Xml iniziata");
		try{
			encoder.writeObject(componentsConfiguration);
		}
		catch ( Throwable t ){

			t.printStackTrace();
		
		}
		encoder.close();
		System.out.println("Scrittura oggetto in Xml riuscita");

	}
}
