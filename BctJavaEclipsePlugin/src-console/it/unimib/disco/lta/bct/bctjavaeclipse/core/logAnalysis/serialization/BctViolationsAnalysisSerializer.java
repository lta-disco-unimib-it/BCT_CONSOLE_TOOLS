package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BctViolationsAnalysisSerializer {

	public static void serialize(File destination, BctViolationsAnalysisConfiguration configuration ) throws FileNotFoundException{
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new  FileOutputStream(destination)));
		
		try{
			encoder.writeObject(configuration);
		}
		catch ( Throwable t ){

			t.printStackTrace();
		
		}
		encoder.close();
		
	}
	
}
