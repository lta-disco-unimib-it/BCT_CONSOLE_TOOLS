/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
