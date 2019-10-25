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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import console.ProjectSetup;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.coverage.BranchRegressionSuiteGenerator;


public class ChangeRegressionSuiteBuilder {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		
		File functionMonitoringDataFile = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mrc);
		
		Map<String, FunctionMonitoringData> functionData = FunctionMonitoringDataSerializer.load(functionMonitoringDataFile);
	
		String[] files = Arrays.copyOfRange(args, 1, args.length);
		BranchRegressionSuiteGenerator bg = new BranchRegressionSuiteGenerator();
		
		for ( FunctionMonitoringData func : functionData.values() ){
			if ( func.isTargetFunction() ){
				int last = func.getLastSourceLine();
				for ( int i = func.getFirstSourceLine() ; i < last; i++){
					String path = func.getFirstSourceLineData().getFileLocation();
					System.out.println( path +" " + i);
					bg.addLineToInclude(path, i);
				}
			}
		}
		
		bg.run(files);
	}

}
