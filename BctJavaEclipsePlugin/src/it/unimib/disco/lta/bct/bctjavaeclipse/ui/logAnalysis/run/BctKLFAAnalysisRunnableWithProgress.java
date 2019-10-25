package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.run;

import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.ava.AvaUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.klfa.KLFAUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.klfa.KlfaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AvaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.BctViolationsLogDataComposite;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

public class BctKLFAAnalysisRunnableWithProgress implements
		IRunnableWithProgress {

	private BctViolationsAnalyzer violationsAnalyzer;
	private KlfaAnalysisResult avaResult;
	private String failureId;
	private List<BctAnomalousCallSequence> anomalousCallSequences;
	private MonitoringConfiguration monitoringConfiguration;

	public BctKLFAAnalysisRunnableWithProgress(
			BctViolationsAnalyzer violationsAnalyzer,
			MonitoringConfiguration monitoringConfiguration, List<BctAnomalousCallSequence> anomalousCallSequences,
			String fid,
			KlfaAnalysisResult avaResult) {
		this.violationsAnalyzer = violationsAnalyzer;
		this.failureId = fid;
		this.monitoringConfiguration = monitoringConfiguration;
		this.avaResult = avaResult;
		this.anomalousCallSequences = anomalousCallSequences;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
		List<BctFSAModelViolation> fsaViolations = new ArrayList<BctFSAModelViolation>();
		
		for ( BctModelViolation bctViolation :  violationsAnalyzer.getFilteredBctViolations(failureId) ){
			if ( bctViolation instanceof BctFSAModelViolation ){
				fsaViolations.add((BctFSAModelViolation) bctViolation);
			}
		}
		
		BctRuntimeDataFilter filter = new BctRuntimeDataFilterCorrectOut();
		
		BctRuntimeDataManager<BctAnomalousCallSequence> manager = new BctRuntimeDataManager<BctAnomalousCallSequence>();
		manager.addData(anomalousCallSequences);
		List<BctAnomalousCallSequence> filteredAnomalousCallSequences = filter.getFilteredData(manager, violationsAnalyzer.getFailuresManager(), violationsAnalyzer.getIdManager(), failureId);
		
		
		Trace t = new VectorTrace();
		t.addSymbol("sss");
		t.getSymbol(0);
		try {
			List<BctFSAModelViolation> refinedInteractionViolations = KLFAUtil.createRefinedInteractionViolations( monitoringConfiguration, filteredAnomalousCallSequences );
			avaResult.setOriginalInteractionViolations( fsaViolations );
			avaResult.setRefinedInteractionViolations( refinedInteractionViolations );
			
			for ( BctFSAModelViolation viol : refinedInteractionViolations ){
				System.out.println("Refined anomaly: "+viol);
			}
		} catch (LoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
