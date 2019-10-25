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
package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.eclipse.vart.core.VARTRunnableWithProgress;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import tools.InvariantGenerator;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.PropertyUtil;

public class RegressionsDetector {


	
	/**
	 * @param args
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws DefaultOptionsManagerException 
	 * @throws CoreException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws ClassNotFoundException 
	 * @throws ModelsFetcherException 
	 */
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
		execute( args );
	}

	public static void execute(String[] args)
			throws IOException, ConfigurationFilesManagerException,
			CoreException, DefaultOptionsManagerException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InterruptedException,
			CBEBctViolationsLogLoaderException, FileNotFoundException,
			ModelsFetcherException, ClassNotFoundException {
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS, "true");
		
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_EXPAND_EQUIVALENCES, "false" );
		
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		VARTRegressionConfiguration vartConfig = (VARTRegressionConfiguration) mrc.getAdditionalConfiguration(VARTRegressionConfiguration.class);
		
//		CBCMRegressionsDetector detector = new CBCMRegressionsDetector(vartConfig,ModelsFetcherFactoy.modelsFetcherInstance);
		System.out.println("\n\n\n======= DEBUGGING INFO ========\n\n\n");
		
		
		VARTRunnableWithProgress vartRunner = new VARTRunnableWithProgress(mrc);
		vartRunner.run(new NullProgressMonitor());

		
		Set<String> result = vartRunner.getViolatedAssertions();
		
			
		System.out.println("\n\n\n======= DEBUGGING INFO END ========\n\n\n\n\n\n");
		
		if ( result == null ){
			System.out.println("Incomplete execution, list of violated properties not available.");
		} else if ( result.size() == 0 ){
			System.out.println("No property has been violated!");
		} else {
			System.out.println("WARNING:"+ result.size()+" properties have been violated:");
			for ( String prop : result ){
				System.out.println("\t"+prop);
			}
			
		}
	}

}
