package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;



import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IEditorSite;

public class RegressionAnalysisResultsComposite extends Composite {

	private ViolationsComposite violationsComposite;
	private RegressionAnalysisResult regressionAnalysisResult;

	public RegressionAnalysisResultsComposite(Composite parent, int style) {
		super(parent, style);
		
		
		FillLayout parentLayout = new FillLayout();
//		parentLayout.numColumns=1;
//		parentLayout.marginWidth=0;
//		parentLayout.marginHeight=0;
		
		this.setLayout(parentLayout);
		
				
		violationsComposite = new ViolationsComposite(this, SWT.BORDER, true);
		violationsComposite.setSize(300, 400);
		
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.setLayoutData(gridData);
		this.setSize(300, 400);
		
//		this.pack();
	}

	public void load(RegressionAnalysisResult result) {
		if ( result == null ){
			return;
		}
		this.regressionAnalysisResult = result;
		violationsComposite.setItems(result.getFilteredViolations());
		violationsComposite.redraw();
	}

	public RegressionAnalysisResult getRegressionAnalysisResult() {
		return regressionAnalysisResult;
	}

	public void setSite(IEditorSite site) {
		violationsComposite.setSelectionProvider(site);
	}
	
	public void setMonitoringConfiguration(MonitoringConfiguration mc){
		violationsComposite.setMonitoringConfiguration(mc);
	}

}
