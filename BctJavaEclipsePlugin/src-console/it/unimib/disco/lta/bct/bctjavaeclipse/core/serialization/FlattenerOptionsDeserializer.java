package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;



import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationDeserializer.NullOutputStream;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FlattenerOptionsDeserializer {


//	static MonitoringConfiguration result;

	public static FlattenerOptions deserialize(File file)throws FileNotFoundException
	{
		//
		//HACK HACK HACK
		//


		FlattenerOptions fo = new FlattenerOptions();



		XMLEncoder encoder = new XMLEncoder(new NullOutputStream());
		encoder.writeObject(fo);
		encoder.close();

		

		//HACK END


		XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

		FlattenerOptions result = (FlattenerOptions) decoder.readObject();

		decoder.close();

		return result;

	}
}
