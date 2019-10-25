package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctResourcesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;

import java.io.File;
import java.util.Properties;

import conf.ExecutionContextRegistrySettings;
import conf.TestCasesRegistrySettings;
import executionContext.ExecutionContextStateMaintainerFile;
import executionContext.TestCasesRegistry;

public class TestCasesFileRegistryOptions implements TestCasesRegistryOptions {

	private String tmpFile;
	
	public String getTmpFile() {
		return tmpFile;
	}

	public void setTmpFile(String tmpFile) {
		this.tmpFile = tmpFile;
	}

	public TestCasesFileRegistryOptions(){
		
	}
	
	public TestCasesRegistrySettings getSettings(MonitoringConfiguration mc) {
		Properties p = new Properties();
		
		p.setProperty(ExecutionContextRegistrySettings.Options.stateRecorderType,
				ExecutionContextStateMaintainerFile.class.getCanonicalName());
		
		p.setProperty("type", TestCasesRegistry.class.getCanonicalName());
		
		if ( tmpFile == null ){
			
			File bctHome;
			try {
				bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
				File dest = new File( bctHome, "testCasesRegistryState" );
				p.setProperty(ExecutionContextStateMaintainerFile.Options.tmpFile, dest.getAbsolutePath() );
			} catch (ConfigurationFilesManagerException e) {
				Logger.getInstance().log(e);
				e.printStackTrace();
			}
			
		} else {
			p.setProperty(ExecutionContextStateMaintainerFile.Options.tmpFile, tmpFile);
		}
		return new TestCasesRegistrySettings(executionContext.TestCasesRegistry.class, p);
	}

}
