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
