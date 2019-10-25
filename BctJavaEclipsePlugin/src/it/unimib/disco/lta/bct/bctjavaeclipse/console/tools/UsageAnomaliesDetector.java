package it.unimib.disco.lta.bct.bctjavaeclipse.console.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.NullProgressMonitor;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions.IdentifyAnomaliesRunnableWithProgress;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions.StaticallyIdentifyAnomaliesAction;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.StaticallyIdentifyUsageAnomaliesResult;

public class UsageAnomaliesDetector {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws FileNotFoundException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException, FileNotFoundException, ConfigurationFilesManagerException {
		
		File mcFile = new File(args[0]);
		final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(mcFile);
		
		
		StaticallyIdentifyUsageAnomaliesResult result = new StaticallyIdentifyUsageAnomaliesResult();
		result.setSkipPassingTests(true);
		result.setSkipPassingActions(true);
		
		
		File bctCBELog = new File(args[1]);
		
		StaticallyIdentifyAnomaliesAction.setTracesToExclude(mc,result,bctCBELog);
		
//		// TODO Auto-generated method stub
//		IdentifyAnomaliesRunnableWithProgress runner = new IdentifyAnomaliesRunnableWithProgress(null, null, null);
//		
//		NullProgressMonitor monitor = new NullProgressMonitor();
//		runner.run(monitor);
//		
	}

}
