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
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import console.ProjectSetup;
//import daikon.inv.Invariant;


public class ShowModels {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws ModelsFetcherException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ModelsFetcherException {
		ProjectSetup projectVars = ProjectSetup.setupProject(args[0]);
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		
		ModelsFetcher mf = ModelsFetcherFactoy.modelsFetcherInstance;
		
		Set<String> models;
		
		if ( args.length > 1 ){
			models = new HashSet<String>();
			for ( int i = 1; i < args.length; i++ ){
				models.add(args[i]);
			}
		} else {
			models = mf.getIoModelsNames();
		}
		
		for ( String ppName : models ){
			
			List list = mf.getSerializedIoModelsEnter(ppName);
			System.out.println(ppName+" "+list.size());
			for ( Object o : list ){
				System.out.println(ppName+" "+o.toString()+" "+o.getClass().getCanonicalName());
			}
		}
	}

}
