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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class StaticallyIdentifyUsageAnomaliesWizardPage  extends WizardPage{

	private Button skipPassingTests;
	private Button skipPassingActions;
	private Button skipPassingProcesses;
	private Composite main;
	
	public static final String PAGE_NAME = "Configurationpage";

	public StaticallyIdentifyUsageAnomaliesWizardPage() {
		super("Invariant generator option");
		setTitle("Invariant generator option");
		setDescription("Insert Invariant generator option");
		this.setPageComplete(true);
	}




	public void createControl(Composite parent) {

		main = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns=2;
		main.setLayout(layout);
		
		
		new Label(main,SWT.NONE).setVisible(false);
		new Label(main,SWT.NONE).setVisible(false);
		
		skipPassingTests = new Button(main,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			skipPassingTests.setLayoutData(ld);
		}
		skipPassingTests.setText("Skip passing tests.");
		skipPassingTests.setSelection(true);
		new Label(main,SWT.NONE).setVisible(false);
		
		
		
		new Label(main,SWT.NONE).setVisible(false);
		new Label(main,SWT.NONE).setVisible(false);
		
		skipPassingActions = new Button(main,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			skipPassingActions.setLayoutData(ld);
		}
		skipPassingActions.setText("Skip passing actions.");
		skipPassingActions.setSelection(true);
		new Label(main,SWT.NONE).setVisible(false);
		
		
		new Label(main,SWT.NONE).setVisible(false);
		new Label(main,SWT.NONE).setVisible(false);
		
		skipPassingProcesses = new Button(main,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			skipPassingProcesses.setLayoutData(ld);
		}
		skipPassingProcesses.setText("Skip passing processes.");
		new Label(main,SWT.NONE).setVisible(false);
		
		setControl(main);
	}

	public boolean getSkipPassingTests() {
		// TODO Auto-generated method stub
		return skipPassingTests.getSelection();
	}

	public boolean getSkipPassingActions() {
		// TODO Auto-generated method stub
		return skipPassingActions.getSelection();
	}
	
	public boolean getSkipPassingProcesses() {
		// TODO Auto-generated method stub
		return skipPassingProcesses.getSelection();
	}


}
