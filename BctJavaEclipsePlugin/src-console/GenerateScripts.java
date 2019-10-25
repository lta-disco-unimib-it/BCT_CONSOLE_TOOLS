import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;


public class GenerateScripts {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException {
		console.ScriptsGenerator.main(args);
	}

}
