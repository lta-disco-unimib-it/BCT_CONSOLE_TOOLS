package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpp.gdb.FileChangeInfo;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.ModifiedFunctionsDetector;
import cpp.gdb.SourceLinesMapper;

public class SourceLineMapperFactory {

	public static SourceLinesMapper createSourceLinesMapper(MonitoringConfiguration mc){
		try {
			File bctHome = ConfigurationFilesManager.getBctHomeDir(mc);


			File functionDataFile = ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc);

			ArrayList<File> originalSources = new ArrayList<File>();
			ArrayList<File> modifiedSources = new ArrayList<File>();

			File functionDataModifiedFile;
			if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
				functionDataModifiedFile =  ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc);

				CConfiguration config = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);

				originalSources.add( new File( config.getOriginalSwSourcesFolder() ) );
				modifiedSources.add( new File( config.getOriginalSwSourcesFolder() ) );
			} else {
				functionDataModifiedFile =  ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc);

				CRegressionConfiguration config = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);


				originalSources.add( new File( config.getOriginalSwSourcesFolder() ) );
				modifiedSources.add( new File( config.getModifiedSwSourcesFolder() ) );
			}




			Map<String, FunctionMonitoringData> monitoredFunctionsData = FunctionMonitoringDataSerializer.load(functionDataFile);
			Map<String, FunctionMonitoringData> monitoredFunctionsModifiedData = FunctionMonitoringDataSerializer.load(functionDataModifiedFile);

			ModifiedFunctionsDetector mfd = new ModifiedFunctionsDetector();





			List<FileChangeInfo> diffs = mfd.extractDiffsFromMultipleSourceFolders(originalSources, modifiedSources);

			SourceLinesMapper mapper = new SourceLinesMapper(diffs, monitoredFunctionsData, monitoredFunctionsModifiedData);

			return mapper;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}
}
