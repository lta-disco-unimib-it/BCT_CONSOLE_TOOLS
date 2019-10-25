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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import modelsFetchers.ModelsFetcherException;

import org.eclipse.core.runtime.CoreException;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;


public class InferModels {

	/**
	 * @param args
	 * @throws ModelsFetcherException 
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws DefaultOptionsManagerException 
	 * @throws CoreException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException {
		// TODO Auto-generated method stub
		VART_Run.InferOnly.main(args);
	}

}
