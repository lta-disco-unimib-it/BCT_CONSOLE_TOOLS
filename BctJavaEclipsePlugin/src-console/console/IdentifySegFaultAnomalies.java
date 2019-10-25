package console;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;

import console.AnomaliesIdentifier;

import tools.InvariantGenerator;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.PropertyUtil;


public class IdentifySegFaultAnomalies {
	
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException {
		
		if ( args.length != 1 ){
			System.out.println("Usage: "+IdentifySegFaultAnomalies.class.getCanonicalName()+" <BctOutputDir>");
			return;
		}
		
		
		PropertyUtil.setPropertyIfUndefined( "bct.processPointers", "true");
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS, "true");
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_EXPAND_EQUIVALENCES, "false" );
		PropertyUtil.setPropertyIfUndefined( "bct.checkWithDaikon", "true" );
		
		console.AnomaliesIdentifier.main(args);
	}
}
