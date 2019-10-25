import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis.RegressionAnalysisOutput;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import console.AnomaliesIdentifier;
import console.ProjectSetup;


public class ShowIntendedAnomalies {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws CBEBctViolationsLogLoaderException CBEBctViolationsLogLoaderException
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException {
		if ( args.length != 1 ){
			return;
		}
		
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		
		RegressionAnalysisOutput intended = RegressionAnalysis.identifyRegressions(mrc, false);
		
		AnomaliesIdentifier.setSeparator("\t");
		int exitValue = AnomaliesIdentifier.generateOutputFiles(mrc,intended.getViolationAnalysisResult().getFilteredViolations());
		
		
	}

}
