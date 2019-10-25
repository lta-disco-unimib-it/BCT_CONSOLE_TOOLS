package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;


public class ComponentsConfigurationDeserializer implements ExceptionListener {

	public static class NullOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
			
		}
		
	}

	public static ComponentsConfiguration deserialize(File xmlSerializedObject) throws FileNotFoundException {
		
		
		//
		//   HACK HACK HACK
		//
		//  XmlEncoders Decoders are not the right choice for eclipse, should switch to another solution
		//  To decode an object we need taht its classes are loaded, so we need to run a fake serialier before running the deserializer
		//
		
		ComponentsConfiguration cc = HackUtil.getFakeComponnetsConfiguration();
		XMLEncoder enc = new XMLEncoder(new NullOutputStream());
		enc.writeObject(cc);
		
		
		
		//Decode
		
		XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlSerializedObject)));
		decoder.setExceptionListener(new ComponentsConfigurationDeserializer());
		System.out.println("Inizio decodifica XML");

		ComponentsConfiguration result = (ComponentsConfiguration) decoder.readObject();

		decoder.close();

		return result;

	}

	public void exceptionThrown(Exception e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
	}

}
