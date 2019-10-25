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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;


public class MonitorModifiedOneLineAtime {

	/**
	 * Same as MonitorOneLineAtime, but monitors the upgraded version of the software.
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, IOException, ClassNotFoundException {
		MonitorOneLineAtime.setOriginal(false);
		MonitorOneLineAtime.main(args);
	}

}
