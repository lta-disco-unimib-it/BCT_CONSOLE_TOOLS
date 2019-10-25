package it.unimib.disco.lta.bct.bctjavaeclipse.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;

public class MonitoringConfigurationDeserializerTest {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Stub di metodo generato automaticamente
		testSerializeSimpleConfiguration();
	}

	private static void testSerializeSimpleConfiguration() {

		File dest = new File("simpleMonitoringConfiguration.xml");
		try {
			MonitoringConfigurationDeserializer.deserialize(dest);
			
		} catch (FileNotFoundException e) {
			System.err.println("FAILURE!");
			e.printStackTrace();
		}
	}
	


}
