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
