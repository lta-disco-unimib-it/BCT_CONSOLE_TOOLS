package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;


/**
 * This class is used to populate the daikon configs hash map with the default daikon options
 * 
 * @author Fabrizio Pastore
 *
 */
public class DaikonPropertiesCreator {

	private static final String[] defaultConfigsNames = { "default", "essentials", "intermediates" };
	
	/**
	 * Load the default daikon properties objects in the given HashMap, using the config name as key
	 * 
	 * @param daikonDefaultConfigs
	 * @return 
	 * @throws IOException
	 */
	public static HashMap<String, Properties> getDefaultDaikonProperties() {

		HashMap<String, Properties> daikonDefaultConfigs = new HashMap<String, Properties>();
		

		for ( String configName : defaultConfigsNames ){

			
			URL resource = DaikonPropertiesCreator.class.getResource("/conf/files/"+configName+".txt");
			InputStream inputStream = null;
			try {
				inputStream = resource.openStream();
				if ( inputStream != null ){
					Properties properties = new Properties();

					properties.load(inputStream);
					daikonDefaultConfigs.put(configName, properties);

				}
			} catch (IOException e){

			} finally  {
				if ( inputStream != null ){
					try {
						inputStream.close();
					} catch (IOException e) {

					}
				}
			}
		}
		return daikonDefaultConfigs;
	}

	public static String[] getDefaultConfigsNames() {
		return defaultConfigsNames;
	}
}
