import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;


public class GenerateDebuggingScripts {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException {
		
		if ( args.length != 5  && args.length != 3 ){
			System.out.println("Usage: " +
					"\n\t"+GenerateDebuggingScripts.class.getCanonicalName()+" <BctOutputDir> <originalSrc> <originalExec> <modifiedSrc> <modifiedExec>"+
					"\n\t"+GenerateDebuggingScripts.class.getCanonicalName()+" <BctOutputDir> <src> <exec>"
					);
			return;
		}
		

		
		console.DebuggingScriptsGenerator.main(args);
	}

}
