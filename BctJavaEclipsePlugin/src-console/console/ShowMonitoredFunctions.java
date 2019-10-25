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
