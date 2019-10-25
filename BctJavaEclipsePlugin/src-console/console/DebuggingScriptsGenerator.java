package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.CoreException;

public class DebuggingScriptsGenerator extends ScriptsGenerator {
	
	
	
	public DebuggingScriptsGenerator() {
		super();
		this.setMonitorCallersOfModifiedFunctions(true);
		this.setTraceAllLinesOfMonitoredFunctions(true);
		this.setCompleteMonitoring(true);
	}

	public static void main( String args[]) throws FileNotFoundException, CoreException, ConfigurationFilesManagerException{
		ScriptsGenerator generator = new DebuggingScriptsGenerator();
		
		
		ScriptsGenerator.execute(args, generator);
	}

}
