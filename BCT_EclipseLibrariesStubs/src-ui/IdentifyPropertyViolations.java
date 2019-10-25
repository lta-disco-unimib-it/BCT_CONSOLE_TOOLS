
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
