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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.IOException;

import console.ProjectSetup;


public class UpdateGdbMonitoringConfiguration {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, IOException, ClassNotFoundException {
		String projectDir = args[0];

		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);

		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());

		if ( mrc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			CRegressionAnalysisUtil.createGdbMonitoringConfigurations( mrc, CConfiguration.class );
		} else if ( mrc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			CRegressionAnalysisUtil.createGdbMonitoringConfigurations( mrc, CRegressionConfiguration.class );	
		}
		
		console.ShowMonitoredFunctions.main(args);
	}

}
