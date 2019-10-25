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

import java.io.File;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CRegressionConfigurationComposite extends CConfigurationComposite {

	private Text modifiedSwFolderText;
	private Text modifiedSwText;
	private Button monitorParentsOfModifiedButton;
	private Button traceAllLinesOfChildrenButton;
	private Button monitorOnlyNotModifiedLinesButton;
	private Button useUpdatedReferencesForModelsButton;
	
	private Button monitorAdedAndDeltedFunctionsButton;
	private Button hideAddedAndDeletedFunctionsButton;


	

	@Override
	protected void internaLoad(CConfiguration _config) {
		
		if ( _config == null ){
			return;
		}
		
		super.internaLoad(_config);
		
		
		CRegressionConfiguration config = (CRegressionConfiguration) _config;
		if ( config.getModifiedSwExecutable() != null ){
			modifiedSwText.setText(config.getModifiedSwExecutable());
		}
		if ( config.getModifiedSwSourcesFolder() != null ){
			modifiedSwFolderText.setText(config.getModifiedSwSourcesFolder());
		}
		
		monitorParentsOfModifiedButton.setSelection(config.isMonitorCallersOfModifiedFunctions());
		
		monitorOnlyNotModifiedLinesButton.setSelection(config.isMonitorOnlyNotModifiedLines());
		
		traceAllLinesOfChildrenButton.setSelection(config.isTraceAllLinesOfChildren());
		
		useUpdatedReferencesForModelsButton.setSelection(config.isUseUpdatedReferencesForModels() );
		
		monitorAdedAndDeltedFunctionsButton.setSelection(config.isMonitorAddedAndDeletedFunctions());
		
		hideAddedAndDeletedFunctionsButton.setSelection(config.isHideAddedAndDeletedFunctions());
	}

	public CRegressionConfigurationComposite(Composite container, int none) {
		super(container, none);
	}

	@Override
	protected void createAdditionalLocations(Composite parent) {
		//NEW ROW

		Label label = new Label(parent,SWT.NONE);
		label.setText("Modified software sources:");

		modifiedSwFolderText = new Text(parent, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		modifiedSwFolderText.setEditable(true);
		
		GridData gd = new org.eclipse.swt.layout.GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		modifiedSwFolderText.setLayoutData(gd);

		Button button = new Button(parent, SWT.NONE);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(modifiedSwFolderText);
			}
		});








		//NEW ROW


		label = new Label(parent,SWT.NONE);
		label.setText("Modified software executable:");

		modifiedSwText = new Text(parent, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		modifiedSwText.setEditable(true);
		
		gd = new org.eclipse.swt.layout.GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		modifiedSwText.setLayoutData(gd);

		button = new Button(parent, SWT.NONE);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(modifiedSwText);
			}
		});
	}


	public CRegressionConfiguration getConfiguration(){
		CRegressionConfiguration regressionConfiguration;
		if ( loadedConfiguration == null ){
			CConfiguration parentConfig = super.getConfiguration();
			regressionConfiguration = new CRegressionConfiguration( parentConfig );
		} else {
			regressionConfiguration = (CRegressionConfiguration) super.getConfiguration();
		}
		

		regressionConfiguration.setModifiedSwExecutable(this.modifiedSwText.getText());
		regressionConfiguration.setModifiedSwSourcesFolder(this.modifiedSwFolderText.getText());
		regressionConfiguration.setMonitorCallersOfModifiedFunctions(this.monitorParentsOfModifiedButton.getSelection());

		regressionConfiguration.setTraceAllLinesOfChildren(traceAllLinesOfChildrenButton.getSelection());
		
		regressionConfiguration.setHideAddedAndDeletedFunctions(hideAddedAndDeletedFunctionsButton.getSelection());
		regressionConfiguration.setMonitorOnlyNotModifiedLines(monitorOnlyNotModifiedLinesButton.getSelection());
		
		regressionConfiguration.setUseUpdatedReferencesForModels(useUpdatedReferencesForModelsButton.getSelection());
		regressionConfiguration.setMonitorAddedAndDeletedFunctions(monitorAdedAndDeltedFunctionsButton.getSelection());
		
		return regressionConfiguration;

	}

	boolean visible = true;
	
	
	@Override
	protected void createAdditionalButtons(Composite _parent) {
		
//		traceAllLinesOfMonitoredFunctionsButton.setSelection(false);
		deriveLineInvariantsButton.setSelection(true);
//		showButtonsButton = new Button(_parent, SWT.CHECK);
//		
//		Label label = new Label(_parent,SWT.NONE);
//		label.setText("Show Buttons");
//
//
//		label = new Label(_parent,SWT.NONE);
		
		Composite parent = _parent;
		
//		final Composite parent = new Composite(_parent, SWT.NONE);
//		parent.setVisible(visible);
//		showButtonsButton.setSelection(visible);
//		showButtonsButton.addSelectionListener( new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
//				visible = ! visible;
//				parent.setVisible(visible);
//				layout();
//			}
//		});

		GridData ld = new GridData();
		ld.horizontalSpan = 3;
		parent.setLayoutData(ld);
		
		GridLayout l = new GridLayout(3, false);
		parent.setLayout(l);
		
		//NEW ROW

		monitorParentsOfModifiedButton = new Button(parent, SWT.CHECK);
		monitorParentsOfModifiedButton.setSelection(true);
		monitorParentsOfModifiedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		Label label = new Label(parent,SWT.NONE);
		label.setText("Monitor also callers of modified methods");


		label = new Label(parent,SWT.NONE);
		
		//NEW ROW

		traceAllLinesOfChildrenButton = new Button(parent, SWT.CHECK);
		traceAllLinesOfChildrenButton.setSelection(false);
		traceAllLinesOfChildrenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Trace all lines of children");


		label = new Label(parent,SWT.NONE);
		
		
		//add trace children all lines+load+getCOnfig
		
		
		//NEW ROW

		monitorAdedAndDeltedFunctionsButton = new Button(parent, SWT.CHECK);
		monitorAdedAndDeltedFunctionsButton.setSelection(true);
		monitorAdedAndDeltedFunctionsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor added and deleted functions");


		label = new Label(parent,SWT.NONE);

		//NEW ROW

		hideAddedAndDeletedFunctionsButton = new Button(parent, SWT.CHECK);
		hideAddedAndDeletedFunctionsButton.setSelection(true);
		hideAddedAndDeletedFunctionsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Hide added and deleted functions from models");


		label = new Label(parent,SWT.NONE);
		
		//NEW ROW

		monitorOnlyNotModifiedLinesButton = new Button(parent, SWT.CHECK);
		monitorOnlyNotModifiedLinesButton.setSelection(true);
		monitorOnlyNotModifiedLinesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor only not modified lines");


		label = new Label(parent,SWT.NONE);


		
		//NEW ROW

		useUpdatedReferencesForModelsButton = new Button(parent, SWT.CHECK);
		useUpdatedReferencesForModelsButton.setSelection(true);
		useUpdatedReferencesForModelsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Use updated names in models");


		label = new Label(parent,SWT.NONE);
		
		
		
		
	}
	
	
	protected void openMonitoringHelp(){
		
		Shell shell = new Shell(SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(650, 550);
		
		//Composite main = new Composite(shell, SWT.NONE); 
		Shell main = shell;
		
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		main.setLayout(layout);
		
		String message = "Execute the following commands on a command shell to monitor the base program:\n";
		
		Label label = new Label(main,SWT.NONE);
		label.setText(message);
		
		Text baseCommand = new Text(main,SWT.MULTI);
		
		if ( monitoringConfiguration != null ){
			try {
				File validTrace = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfig(monitoringConfiguration);
				baseCommand.setText( createGdbMonitoringcommandHelp( validTrace ) );
				
			} catch (ConfigurationFilesManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		label = new Label(main,SWT.NONE);
		label.setText( "Execute the following commands on a command shell " +
				"to monitor the program under analysis (the modified program):\n");
		
		Text toAnalyzeCommand = new Text(main,SWT.MULTI);
		if ( monitoringConfiguration != null ){
			try {
				File traceToVerify = ConfigurationFilesManager.getModifiedSoftwareGdbMonitoringConfig(monitoringConfiguration);


				toAnalyzeCommand.setText( createGdbMonitoringcommandHelp( traceToVerify ) );
			} catch (ConfigurationFilesManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		shell.pack();
		shell.open();
		
		
	}

}
