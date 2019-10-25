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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.dataExporting;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class IoExportWizardPage extends WizardPage {

	private Text destinationFolderText;

	protected IoExportWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		
		Composite c = new Composite(parent, SWT.NONE);
		super.setControl(c);
		
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		
		c.setLayout(gl);
		
		Label l = new Label(c, SWT.NONE);
		l.setText("Destination folder");
		
		destinationFolderText = new Text(c, SWT.BORDER);

		Button daikonpathButton = new Button(c,SWT.NULL);
		daikonpathButton.setText("Browse");

		daikonpathButton.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				Shell shell = new Shell();

				DirectoryDialog fileDialog = new DirectoryDialog(shell, SWT.OPEN);
				String dir = fileDialog.open();
				if(dir != null) {
					destinationFolderText.setText(dir);
				}
				updateState();// isPageComplete
			}

			


		});
	}
	
	private void updateState() {
		StringBuffer sb = new StringBuffer();

		if ( destinationFolderText.getText().isEmpty() ){
			sb.append("You must select a destination folder");
		}

		if (sb.length() == 0){ // no violations
			setMessage(null);
			setPageComplete(true);
		}else{
			setPageComplete(false);
			setMessage(sb.toString());
		}
	}

	public String getDestinationFolder() {
		// TODO Auto-generated method stub
		return destinationFolderText.getText();
	}


	


}
