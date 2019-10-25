

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;

import console.DebuggingScriptsGenerator;
import console.ScriptsGenerator;

public class GenerateFunctionMonitoringScripts  {
	
	

	public static void main( String args[]) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
		generator.setMonitorCallersOfModifiedFunctions(false);
		generator.setTraceAllLinesOfMonitoredFunctions(false);
		
		ScriptsGenerator.execute(args, generator);
	}

}
