import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;


public class MonitorModifiedOneLineAtime {

	/**
	 * Same as MonitorOneLineAtime, but monitors the upgraded version of the software.
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, IOException, ClassNotFoundException {
		MonitorOneLineAtime.setOriginal(false);
		MonitorOneLineAtime.main(args);
	}

}
