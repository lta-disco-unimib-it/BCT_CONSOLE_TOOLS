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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import modelsFetchers.ModelsFetcherException;

import org.eclipse.core.runtime.CoreException;

import console.RegressionsDetector;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;


public class IdentifyPropertyViolations {

	/**
	 * This program identifies property violations using the CBMC model checker
	 * 
	 * @param args
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ConfigurationFilesManagerException
	 * @throws CoreException
	 * @throws DefaultOptionsManagerException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @throws CBEBctViolationsLogLoaderException
	 * @throws ModelsFetcherException
	 * @throws ClassNotFoundException
	 * 
	 * @Deprecated
	 * 
	 */
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
		if ( args.length != 1 ){
			System.out.println("Usage: "+IdentifyPropertyViolations.class.getCanonicalName()+" <BctOutputDir>");
			return;
		}
		console.RegressionsDetector.main(args);
	}

}
