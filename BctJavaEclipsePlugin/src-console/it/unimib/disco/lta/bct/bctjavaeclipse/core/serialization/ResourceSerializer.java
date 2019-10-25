package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ResourceSerializer {

	public static void serialize(File destination, Resource resource) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new  FileOutputStream(destination)));
		
		System.out.println("Scrittura RISORSA in Xml iniziata");
		try{
			encoder.writeObject(resource);
		}
		catch ( Throwable t ){

			t.printStackTrace();
		
		}
		encoder.close();
		System.out.println("Scrittura RISORSA in Xml riuscita");

	}
}
