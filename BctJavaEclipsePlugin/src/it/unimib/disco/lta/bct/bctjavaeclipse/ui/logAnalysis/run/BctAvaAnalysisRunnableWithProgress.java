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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.run;

import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.ava.engine.ViolationsAnalyzerException;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.ava.AvaUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AvaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.BctViolationsLogDataComposite;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import tools.violationsAnalyzer.BctRuntimeDataManager;
import tools.violationsAnalyzer.BctViolationsAnalyzer;
import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilter;
import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilterCorrectOut;
import util.cbe.AnomalousCallSequencesExporter;

public class BctAvaAnalysisRunnableWithProgress implements
		IRunnableWithProgress {

	private BctViolationsAnalyzer violationsAnalyzer;
	private AvaAnalysisResult avaResult;
	private String failureId;
	private List<BctAnomalousCallSequence> anomalousCallSequences;
	private MonitoringConfiguration monitoringConfiguration;
	private AvaConfiguration avaConfiguration;

	public BctAvaAnalysisRunnableWithProgress(
			AvaConfiguration avaConfiguration, MonitoringConfiguration mc,
			BctViolationsAnalyzer violationsAnalyzer,
			List<BctAnomalousCallSequence> anomalousCallSequences,
			String fid,
			AvaAnalysisResult avaResult) {
		this.avaConfiguration = avaConfiguration;
		this.violationsAnalyzer = violationsAnalyzer;
		this.failureId = fid;
		this.avaResult = avaResult;
		this.anomalousCallSequences = anomalousCallSequences;
		this.monitoringConfiguration = mc;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
		System.out.println("Anbomalous call sequences");
		
		for ( BctAnomalousCallSequence cs : anomalousCallSequences ){
			System.out.println(cs.getAnomalousCallSequenceList());
		}
		
		List<BctFSAModelViolation> fsaViolations = new ArrayList<BctFSAModelViolation>();
		
		for ( BctModelViolation bctViolation :  violationsAnalyzer.getFilteredBctViolations(failureId) ){
			if ( bctViolation.getViolatedModelType() == BctModelViolation.ViolatedModelsTypes.FSA ){
				fsaViolations.add((BctFSAModelViolation) bctViolation);
			}
		}
		
		BctRuntimeDataFilter filter = new BctRuntimeDataFilterCorrectOut();
		
		BctRuntimeDataManager<BctAnomalousCallSequence> manager = new BctRuntimeDataManager<BctAnomalousCallSequence>();
		manager.addData(anomalousCallSequences);
		List<BctAnomalousCallSequence> filteredAnomalousCallSequences = filter.getFilteredData(manager, violationsAnalyzer.getFailuresManager(), violationsAnalyzer.getIdManager(), failureId);
		
		try {
			AVAResult interpretations = AvaUtil.createAVAInterpretations( avaConfiguration, monitoringConfiguration, fsaViolations, filteredAnomalousCallSequences );
			avaResult.setAvaResult( interpretations );
		} catch (LoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ViolationsAnalyzerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO: complete method
	}

}
