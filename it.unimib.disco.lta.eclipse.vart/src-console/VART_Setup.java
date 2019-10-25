import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;

import console.ScriptsGenerator;
import console.VARTScriptsGenerator;


public class VART_Setup {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException {
		VARTScriptsGenerator scriptsGen = new VARTScriptsGenerator();
		ScriptsGenerator.execute(args, scriptsGen);
	}

}
