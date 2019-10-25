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
