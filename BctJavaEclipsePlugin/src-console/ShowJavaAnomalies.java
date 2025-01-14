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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis.RegressionAnalysisOutput;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import console.AnomaliesIdentifier;
import console.ProjectSetup;
import executionContext.TestCasesRegistry;


public class ShowJavaAnomalies {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws CBEBctViolationsLogLoaderException CBEBctViolationsLogLoaderException
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws ConfigurationFilesManagerException 
	 */
	public static void main(String[] args) throws FileNotFoundException, ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException {
		if ( args.length < 1 ){
			System.out.println(ShowJavaAnomalies.class.getCanonicalName()+"\n"+
					"\n\t <bct_project> : Path to the BCT Analysis folder" +
					"\n\t -f <failure id> : id of the failure" +
					"\n\t -p : use process id instead of test id" +
					"\n\t -all : analyze all executions, not only failing ones"
					);
			return;
		}
		
		String failureId = null;
		boolean useTests = true;
		boolean onlyFailing = true;
		
		for ( int i = 0; i < args.length; i++ ){
			if ( "-f".equals(args[i]) ){ 
				failureId = args[++i];
			}
			
			if ( "-p".equals(args[i]) ){
				useTests = false;
			}
			
			if ( "-all".equals(args[i]) ){
				onlyFailing = false;
			}
		}
		
		DisplayNamesUtil.setIsJava(true);
		
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		
		RegressionAnalysisOutput intended = RegressionAnalysis.identifyRegressions(mrc, failureId, onlyFailing, useTests, false );
		
		File f = new File("bct.anomalies.txt");
		AnomaliesIdentifier.processResults(intended.getViolationAnalysisResult().getFilteredViolations(),f);
		
		File fCsv = new File("bct.anomalies.csv");
		AnomaliesIdentifier.setSeparator("\t");
		AnomaliesIdentifier.setAddTests(true);
		AnomaliesIdentifier.processResultsCsv(intended.getViolationAnalysisResult().getFilteredViolations(),fCsv);
	}

}
