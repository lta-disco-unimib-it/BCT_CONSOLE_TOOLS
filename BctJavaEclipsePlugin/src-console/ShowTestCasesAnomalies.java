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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AssertionAnomaliesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
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
import console.AnomaliesIdentifier;
import console.WorkspaceUtil;


public class ShowTestCasesAnomalies {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws CBEBctViolationsLogLoaderException CBEBctViolationsLogLoaderException
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException {
		if ( args.length != 2 ){
			return;
		}
		
		WorkspaceUtil.setWorkspaceRoot(new File(args[1]));
//		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(new File(args[0]));
		
		
		
//		File logFile = ConfigurationFilesManager.getBCTCbeLogFile(mrc);
		
		File logFile = new File( args[0] );

		List<BctModelViolation> violations = AssertionAnomaliesUtil.retrieveTestAnomalies(logFile);
		
		
		//File f = new File("bct.anomalies.txt");
		//AnomaliesIdentifier.processResults(violations,f);
		
		File fCsv = new File("bct.anomalies.csv");
		AnomaliesIdentifier.setSeparator("\t");
		AnomaliesIdentifier.setAddTests(true);
		AnomaliesIdentifier.processResultsCsv(violations,fCsv);
	}





}
