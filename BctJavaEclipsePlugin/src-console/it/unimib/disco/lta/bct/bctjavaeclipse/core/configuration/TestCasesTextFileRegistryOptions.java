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
