package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class FsaEngineOptionPage {
	public Shell shell;
	public String fsa;
	public FsaEngineOptionPage(Shell shell,String fsa) {
		
	    this.fsa = fsa;
	    this.shell = shell;
	    
	    
	  }
	
	public void open(){


	final Shell sh =
	    new Shell(shell,SWT.TITLE | SWT.BORDER  | SWT.RESIZE);
	    sh.setText("FsaEngine configuration : " + fsa);
        sh.setBounds(200, 200, 600, 600);
		sh.open();
		Button nextButton = new Button(sh, SWT.PUSH | SWT.CENTER);
		nextButton.setBounds(250, 300, 60, 20);
		nextButton.setText("Next");
		Button cancelButton = new Button(sh, SWT.PUSH | SWT.CENTER);
		cancelButton.setBounds(320, 300, 60, 20);
		cancelButton.setText("Cancel"); 
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				sh.dispose();
		      }
				
			
		});
	 	
	}
}
