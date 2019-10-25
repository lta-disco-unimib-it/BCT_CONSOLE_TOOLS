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
