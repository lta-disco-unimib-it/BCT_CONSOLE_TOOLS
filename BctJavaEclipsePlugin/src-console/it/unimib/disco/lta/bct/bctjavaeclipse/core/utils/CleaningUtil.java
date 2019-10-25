package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import java.io.File;

import util.FileUtil;

public class CleaningUtil {

	public static void deleteInferenceAndAnalysisData(File bctHome) {
		String[] toDelete = new String[]{"bctCBELog","DataRecording","DataRecording.checking","Models",
		         "Preprocessing.checking",  "rawGDBThreadTraces.checking",   "coverage",
		       "DataRecording",  "InferenceLogAnalysis",    "Preprocessing",  "rawGDBThreadTraces",      
		       "ViolationsLogAnalysis"};
		
		deleteFolders(bctHome, toDelete);
	}
	
	public static void deleteAllBctData(File bctHome) {
		String[] toDelete = new String[]{"bctCBELog","DataRecording","DataRecording.checking","Models",
		         "Preprocessing.checking",  "rawGDBThreadTraces.checking",   "coverage",
		       "DataRecording",  "InferenceLogAnalysis",    "Preprocessing",  "rawGDBThreadTraces",      
		       "ViolationsLogAnalysis"};
		
		deleteFolders(bctHome, toDelete);
		
		FileUtil.deleteDirectoryContents( new File( bctHome, "validTraces") );
		FileUtil.deleteDirectoryContents( new File( bctHome, "tracesToVerify") );
		
	}

	public static void deleteFolders(File bctHome, String[] toDelete) {
		for ( String fileToDelete : toDelete ){
			File file = new File( bctHome, fileToDelete );
			FileUtil.deleteRecursively(file);
		}
	}

}
