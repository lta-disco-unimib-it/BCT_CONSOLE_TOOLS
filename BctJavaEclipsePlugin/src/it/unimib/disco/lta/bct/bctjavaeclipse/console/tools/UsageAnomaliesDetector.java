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
package it.unimib.disco.lta.bct.bctjavaeclipse.console.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.NullProgressMonitor;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions.IdentifyAnomaliesRunnableWithProgress;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions.StaticallyIdentifyAnomaliesAction;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.StaticallyIdentifyUsageAnomaliesResult;

public class UsageAnomaliesDetector {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws FileNotFoundException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException, FileNotFoundException, ConfigurationFilesManagerException {
		
		File mcFile = new File(args[0]);
		final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(mcFile);
		
		
		StaticallyIdentifyUsageAnomaliesResult result = new StaticallyIdentifyUsageAnomaliesResult();
		result.setSkipPassingTests(true);
		result.setSkipPassingActions(true);
		
		
		File bctCBELog = new File(args[1]);
		
		StaticallyIdentifyAnomaliesAction.setTracesToExclude(mc,result,bctCBELog);
		
//		// TODO Auto-generated method stub
//		IdentifyAnomaliesRunnableWithProgress runner = new IdentifyAnomaliesRunnableWithProgress(null, null, null);
//		
//		NullProgressMonitor monitor = new NullProgressMonitor();
//		runner.run(monitor);
//		
	}

}
