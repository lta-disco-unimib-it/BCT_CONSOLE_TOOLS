package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;

import java.io.File;
import java.util.Properties;

import conf.ExecutionContextRegistrySettings;
import conf.TestCasesRegistrySettings;
import executionContext.ExecutionContextStateMaintainerMemory;
import executionContext.ExecutionContextStateMaintainerTextFile;

public class TestCasesTextFileRegistryOptions extends
		TestCasesFileRegistryOptions implements TestCasesRegistryOptions {

	@Override
	public TestCasesRegistrySettings getSettings(MonitoringConfiguration mc) {
		TestCasesRegistrySettings s = super.getSettings(mc);
		Properties p = s.getProperties();
		
		p.setProperty(ExecutionContextRegistrySettings.Options.stateRecorderType,
				ExecutionContextStateMaintainerTextFile.class.getCanonicalName());
		
		File bctHome;
		try {
			bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
			File dest = new File( bctHome, "testFileName.txt" );
			p.setProperty(ExecutionContextStateMaintainerTextFile.Options.testNameFile, dest.getAbsolutePath() );
		} catch (ConfigurationFilesManagerException e) {
			Logger.getInstance().log(e);
			e.printStackTrace();
		}
		
		return new TestCasesRegistrySettings(executionContext.TestCasesRegistry.class, p);
	}

}
