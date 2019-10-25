package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;

public class GenerateUnitCheckingScripts {

	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException {
		VARTScriptsGenerator scriptsGen = new VARTScriptsGenerator();
		
		
//		scriptsGen.setTraceAllLinesOfChildren(true);
		ScriptsGenerator.execute(args, scriptsGen);
	}

}
