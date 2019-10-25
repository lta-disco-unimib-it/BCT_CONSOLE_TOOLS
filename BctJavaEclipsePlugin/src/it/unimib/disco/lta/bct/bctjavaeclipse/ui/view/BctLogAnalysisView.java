package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.BctLogAnalysisComposite;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class BctLogAnalysisView extends ViewPart {

	private BctLogAnalysisComposite c;

	
	public BctLogAnalysisView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		
		
		
		ScrolledComposite composite = new ScrolledComposite(parent, SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
		
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 1;
		
		c = new BctLogAnalysisComposite(composite,SWT.NONE, true);

        //loadFiles();
        
        
        composite.setContent(c);	
		composite.setMinSize(c.computeSize(SWT.DEFAULT,SWT.DEFAULT));
        
		//c.layout(true);
	}

	
	
	@Override
	public void setFocus() {
		c.setFocus();
	}


	

	public void openMess(){
		System.out.println("TERMINATED");
		MessageDialog.openWarning(getSite().getShell(), "HEI", "END");
		System.out.println("DIALOG OPENED");
	}

	public void setFiles(ArrayList<File> files) {
		c.loadFiles(files);
	}
}
