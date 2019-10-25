package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FlattenerOptionsSerializer {

	public static void serialize(File destination, FlattenerOptions flattenerOptions) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new  FileOutputStream(destination)));
		
		
		try{
			encoder.writeObject(flattenerOptions);
		}
		catch ( Throwable t ){

			t.printStackTrace();
		
		}
		encoder.close();


	}
	
}
