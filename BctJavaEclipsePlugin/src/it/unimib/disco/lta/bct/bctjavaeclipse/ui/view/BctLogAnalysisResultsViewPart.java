package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.IndexTableComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class BctLogAnalysisResultsViewPart extends ViewPart {

	private IndexTableComposite anomalyTable;
	

	public BctLogAnalysisResultsViewPart() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		
		ScrolledComposite composite = new ScrolledComposite(parent, SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
//		
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 1;
//		
		
		Composite c = new Composite(composite, SWT.NONE);
		GridLayout l = new GridLayout();
		l.numColumns = 1;
		composite.setLayout(l);
		
		anomalyTable = new IndexTableComposite(composite, SWT.NONE);
        anomalyTable.setBounds(20, 20, 400, 400);
        anomalyTable.setTitles("Graph #", "Resource", "Connected components", "Open");
		
		composite.setContent(c);	
		composite.setMinSize(c.computeSize(SWT.DEFAULT,SWT.DEFAULT));
		
		}

	@Override
	public void setFocus() {
		
	}

}
