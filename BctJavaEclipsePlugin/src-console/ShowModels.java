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
