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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CreateModifierDialog extends TitleAreaDialog {

	public CreateModifierDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Control createDialogArea(Composite parent) {  
		Composite contents = new Composite(parent, SWT.NONE);  
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label maxDepthLabel=new Label(contents, SWT.NULL);
		maxDepthLabel.setText("");
		

		
		return contents;
	}
	
	
}
