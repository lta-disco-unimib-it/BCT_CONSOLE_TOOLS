package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import tools.violationsAnalyzer.BctViolationsLogData;
import tools.violationsAnalyzer.CBEBctViolationsLogLoader;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;

public class AssertionAnomaliesUtil {
	
	public static List<BctModelViolation> retrieveTestAnomalies(File logFile)
			throws CBEBctViolationsLogLoaderException {
		CBEBctViolationsLogLoader loader = new CBEBctViolationsLogLoader();
		Collection<File> cbeLogs = new ArrayList<File>();
		cbeLogs.add(logFile);
		
		List<IFile> cbeILogs = new ArrayList<IFile>();
		Path logFilePath = new Path( logFile.getAbsolutePath() );
		cbeILogs.add( ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(logFilePath) );
		
		
		
		BctViolationsLogData logData = loader.load(cbeLogs );
		List<BctModelViolation> violations = logData.getViolations();
		
		violations = filter ( violations );
		return violations;
	}

	
	private static List<BctModelViolation> filter(
			List<BctModelViolation> violations) {
		List<BctModelViolation> filtered = new ArrayList<BctModelViolation>();
		
		for ( BctModelViolation viol : violations ){
			if ( viol instanceof BctFSAModelViolation ){
				
			} else if ( ! viol.getViolation().contains("orig")
					&& ! viol.getViolation().contains("==>")
					){
				filtered.add(viol);
			}
		}
		
		return filtered;
	}
	
	
}
