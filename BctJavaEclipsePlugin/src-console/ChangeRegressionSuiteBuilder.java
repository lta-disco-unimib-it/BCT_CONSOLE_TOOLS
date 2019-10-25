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
