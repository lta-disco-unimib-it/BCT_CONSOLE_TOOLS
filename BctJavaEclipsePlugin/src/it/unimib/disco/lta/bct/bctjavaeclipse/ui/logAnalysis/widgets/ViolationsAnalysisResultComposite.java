package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AnomalyGraphResource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import modelsViolations.BctModelViolation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * A violation analysis composite show the following information
 * 
 * processes/actions/tests reported in the log file
 * 
 * 
 * @author Fabrizio Pastore
 *
 */
public class ViolationsAnalysisResultComposite extends Composite implements BCTObservable {
	private ViolationsTableComposite anomalyTable;
	
	private BCTObservaleIncapsulated observable;

	private ViolationsAnalysisResult violationsAnalysisResult;

	private List<BctModelViolation> violations;
	
	public ViolationsAnalysisResultComposite( Composite parent, int swt) {
		super(parent,swt);
		observable = new BCTObservaleIncapsulated(this);
		anomalyTable = new ViolationsTableComposite(this, SWT.NONE);
        
        anomalyTable.setTitles("Graph #", "Resource", "Connected components", "Open");
        
	}

	public void load(MonitoringConfiguration mc, ViolationsAnalysisResult analysisResult, List<BctModelViolation> list) {
		this.violationsAnalysisResult = analysisResult;
		this.violations = list;
		
		anomalyTable.setViolations(violations);
		anomalyTable.setMonitoringConfiguration(mc);
		removeAll();
		
		if ( analysisResult == null ){
			return;
		}
		
		for ( AnomalyGraphResource r : analysisResult.getResources() ){
			
			boolean highLight;
			if ( r.isOptimalGraph() ){
				highLight = true;
			} else {
				highLight = false;
			}
			
			StringBuffer sizeBuffer = new StringBuffer();
			sizeBuffer.append( String.valueOf(r.getConnectedComponents()) );
			sizeBuffer.append( ": " );
			
			
			anomalyTable.addElement( r.getResourceName(), r.getResourcePath(), r.getConnectedComponentsElements(), highLight);
		
		}
		changed();
		
	}
	
	private void changed() {
		observable.notifyBCTObservers(null);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		
		anomalyTable.setBounds(0, 0, width-10, height-10);
		super.setBounds(x, y, width, height);
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public ViolationsAnalysisResult getViolationsAnalysisResult() {
		return violationsAnalysisResult;
	}

	public void removeAll() {
		anomalyTable.removeAll();
	}
	

}
