/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
