package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;

public class AnomaliesDebugger extends AnomaliesIdentifier {

	public AnomaliesDebugger(){
		processFailingTracesOnly = true;
	}
	
	/**
	 * @param args
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws DefaultOptionsManagerException 
	 * @throws CoreException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException {
		AnomaliesDebugger debugger = new AnomaliesDebugger();
		AnomaliesIdentifier.run(args, debugger);
	}

}
