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
package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;

public class ShowMonitoredFunctions {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		String projectDir = args[0];
		boolean showOriginal = true;

		if ( args.length > 1 ){
			if ( args[1].equals("-m")){
				showOriginal = false;
			}
		}

		File dir = new File ( projectDir );
		File functionMonitoringDataFile;
		if ( dir.isDirectory() ){
			ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);

			MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());


			if( showOriginal ){
				functionMonitoringDataFile = ConfigurationFilesManager.getMonitoredFunctionsDataFile(mrc);
			} else {
				functionMonitoringDataFile = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mrc);
			}
		}else{
			functionMonitoringDataFile = dir;
		}
		Map<String, FunctionMonitoringData> functionData = FunctionMonitoringDataSerializer.load(functionMonitoringDataFile);

		System.out.println("Monitored Functions");
		for ( Entry<String, FunctionMonitoringData> e : functionData.entrySet() ){
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}

}
