package it.unimib.disco.lta.bct.bctjavaeclipse.core.vart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import tools.fshellExporter.CBMCExecutor.ValidationResult;

public class VARTDataPropertyExit extends VARTDataProperty {

	private String functionaName;

	public String getFunctionaName() {
		return functionaName;
	}

	public VARTDataPropertyExit( MonitoringConfiguration mc, String file, String functionaName,
			String assertion, ValidationResult result, int injectedLine ) {
		super(mc,file, assertion, result, injectedLine);
		this.functionaName = functionaName;
	}

	@Override
	public int getLine() {
		try {
			File functionDataF = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc);
			Map<String, FunctionMonitoringData> functionData = FunctionMonitoringDataSerializer.load(functionDataF);
			FunctionMonitoringData fd = functionData.get(functionaName);
			if ( fd != null ){
				return fd.getLastSourceLine();
			}
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}


}
