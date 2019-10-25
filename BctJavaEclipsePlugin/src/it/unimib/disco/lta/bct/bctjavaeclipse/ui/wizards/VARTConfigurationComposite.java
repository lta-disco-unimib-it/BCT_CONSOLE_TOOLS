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


import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration.ModelChecker;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class VARTConfigurationComposite extends Composite implements BCTObservable {

	private Text compilerPathText;
	private Text modelCheckerExecutable;
	

	private boolean initialized;

	protected MonitoringConfiguration monitoringConfiguration;

	private Button cbmcButton;
	private Button evolcheckButton;
	
	private BCTObservaleIncapsulated observer = new BCTObservaleIncapsulated(this);

	private VARTRegressionConfiguration toLoad;
	protected VARTRegressionConfiguration loadedConfiguration;

	private Button usingRandomTestsButton;
	private Button checkAssertionsCoverageButton;

//	private Button showButtonsButton;

//	private boolean visible = true;

	private Composite parent;




	private Text variablesToCheckText;
	private Text testTargetText;
	private Button useMakeButton;
	private Button customizeMCButton;
	private Button validateChangedFunctionsOnlyButton;
	private Button useMathSpeedUpButton;
	private Spinner loopUnwindingSpinner;

	
	public VARTConfigurationComposite(Composite container, int none) {
//		super(container,none);
		super(container, SWT.FILL);
		Composite parentCont = this;


		GridLayout layout = new GridLayout();
		layout.numColumns = 3;

		parentCont.setLayout(layout);


		parent=parentCont;

		Label label;

		createModelCHeckerSelectionWidgets(parentCont);
		
		createCustomMCConfigurationWidgets(parentCont);
		
		createMakefileOptionsWidgets(parentCont);
		


		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		{
			GridData ld = new GridData(GridData.FILL_HORIZONTAL);
			ld.horizontalSpan = 4;
			group.setLayoutData(ld);
		}
		{
			GridLayout l = new GridLayout();
			l.numColumns = 3;
			group.setLayout(l);
		}
		group.setText("Optimizations");
		
		parent=group;
		
		//NEW ROW

		label = new Label(parent,SWT.NONE);
		label.setText("Loop unwinding:");
		loopUnwindingSpinner = new Spinner (parent, SWT.BORDER);
		loopUnwindingSpinner.setMinimum(0);
		loopUnwindingSpinner.setMaximum(1000);
		loopUnwindingSpinner.setSelection(5);
		loopUnwindingSpinner.setIncrement(1);
		
		{
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = false;
			gd.horizontalSpan = 2;
			loopUnwindingSpinner.setLayoutData(gd);
		}
		
		loopUnwindingSpinner.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent arg0) {
				notifyChange();
			}
		});
		
		
		label = new Label(parent,SWT.NONE);
		label.setText("Variables to check:");

		variablesToCheckText = new Text(parent, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		variablesToCheckText.setEditable(true);
		
		{
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 2;
			variablesToCheckText.setLayoutData(gd);
		}
		
		variablesToCheckText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				notifyChange();
			}
		});
		
		//NEW ROW

		usingRandomTestsButton = new Button(parent, SWT.CHECK);
		usingRandomTestsButton.setText("Random tests");
		usingRandomTestsButton.setSelection(false);
		usingRandomTestsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});
		
		{
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 3;
			usingRandomTestsButton.setLayoutData(gd);
		}
		
		
		//NEW ROW

		validateChangedFunctionsOnlyButton = new Button(parent, SWT.CHECK);
		validateChangedFunctionsOnlyButton.setText("Validate changed functions only");
		validateChangedFunctionsOnlyButton.setSelection(false);
		validateChangedFunctionsOnlyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});

		{
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 3;
			validateChangedFunctionsOnlyButton.setLayoutData(gd);
		}
		
		
		//NEW ROW

		useMathSpeedUpButton = new Button(parent, SWT.CHECK);
		useMathSpeedUpButton.setText("Use math optimizations");
		useMathSpeedUpButton.setSelection(false);
		useMathSpeedUpButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});

		{
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 3;
			useMathSpeedUpButton.setLayoutData(gd);
		}
		
		//NEW ROW

		checkAssertionsCoverageButton = new Button(parent, SWT.CHECK);
		checkAssertionsCoverageButton.setText("Verify assertions coverage");
		checkAssertionsCoverageButton.setSelection(true);
		checkAssertionsCoverageButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});

		{
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 3;
			checkAssertionsCoverageButton.setLayoutData(gd);
		}
		
		parent = parentCont;
		initialized = true;

		internaLoad(toLoad);
	}

	public void createModelCHeckerSelectionWidgets(Composite parent) {
		Label label;
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		{
			GridData ld = new GridData(GridData.FILL_HORIZONTAL);
			ld.horizontalSpan = 4;
			ld.grabExcessHorizontalSpace = true;
			group.setLayoutData(ld);
		}
		{
			GridLayout l = new GridLayout();
			l.numColumns = 3;
			group.setLayout(l);
		}
		group.setText("Model checker to use");
		
		parent=group;
		
		//NEW ROW
		label = new Label(parent,SWT.NONE);
		cbmcButton = new Button(parent, SWT.CHECK);
		cbmcButton.setText("CBMC");
		cbmcButton.setSelection(true);
		cbmcButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				evolcheckButton.setSelection(false);
				notifyChange();
			}
		});

		evolcheckButton = new Button(parent, SWT.CHECK);
		evolcheckButton.setText("EvolCheck");
		evolcheckButton.setSelection(false);
		//		useDemangledNamesButton.setEnabled(false);//ONLY MANGLED NAMES WORK FOR NOW
		evolcheckButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cbmcButton.setSelection(false);
				notifyChange();
			}
		});
		
	}

	public void createMakefileOptionsWidgets(Composite parent) {
		Label label;
		Group group;
		group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		{
			GridData ld = new GridData(GridData.FILL_HORIZONTAL);
			ld.horizontalSpan = 3;
			group.setLayoutData(ld);
		}
		{
			GridLayout l = new GridLayout();
			l.numColumns = 3;
			group.setLayout(l);
		}
		group.setText("Makefile configuration");
		parent = group;
		
		useMakeButton = new Button(parent, SWT.CHECK);
		useMakeButton.setText("Monitor with predefined make target");
		useMakeButton.setSelection(true);
		useMakeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				testTargetText.setEnabled( ! testTargetText.getEnabled() );
				notifyChange();
			}
		});

		GridData ld = new GridData(GridData.FILL_HORIZONTAL);
		ld.horizontalSpan = 3;
		useMakeButton.setLayoutData(ld);
		
		//NEW ROW
		
		label = new Label(parent,SWT.NONE);
		label = new Label(parent,SWT.NONE);
		label.setText("Target name:");

		testTargetText = new Text(parent, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		testTargetText.setEditable(true);
		testTargetText.setText("test");


	}

	public void createCustomMCConfigurationWidgets(Composite parent) {
		Label label;
		Group group;
		group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		{
			GridData ld = new GridData(GridData.FILL_HORIZONTAL);
			ld.horizontalSpan = 3;
			group.setLayoutData(ld);
		}
		{
			GridLayout l = new GridLayout();
			l.numColumns = 3;
			group.setLayout(l);
		}
		group.setText("Custom model checker configuration");
		
		parent=group;
		
		//NEW ROW
		
		customizeMCButton = new Button(parent, SWT.CHECK);
		customizeMCButton.setText("Customize Model Checker configuration");
		{
			GridData ld = new GridData(GridData.FILL_HORIZONTAL);
			ld.horizontalSpan = 3;
			customizeMCButton.setLayoutData(ld);
		}
		customizeMCButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean enable = ! modelCheckerExecutable.getEnabled();
				modelCheckerExecutable.setEnabled(enable);
				compilerPathText.setEnabled(enable);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		
		
		
		//NEW ROW

		
		
		label = new Label(parent,SWT.NONE);
		label.setText("Compiler path:");

		compilerPathText = new Text(parent, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		compilerPathText.setEditable(true);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		compilerPathText.setLayoutData(gd);

		Button button = new Button(parent, SWT.NONE);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(compilerPathText);
			}
		});

		label = new Label(parent,SWT.NONE);
		label.setText("Model checker executable:");

		modelCheckerExecutable = new Text(parent, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		modelCheckerExecutable.setEditable(true);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		modelCheckerExecutable.setLayoutData(gd);

		button = new Button(parent, SWT.NONE);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(modelCheckerExecutable);
			}
		});

		compilerPathText.setEnabled(false);
		modelCheckerExecutable.setEnabled(false);
		
	}

	void notifyChange(){
		if ( !  initialized ){
			return;
		}
		observer.notifyBCTObservers("");
	}

	protected void handleBrowse(Text dataDirFileText) {

		DirectoryDialog fd = new DirectoryDialog(getShell(), SWT.OPEN);
		fd.setText("Select project folder");
		String current = dataDirFileText.getText();
		if ( current == null || current.length() == 0 ){
			fd.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile().getAbsolutePath());	
		} else {
			fd.setFilterPath(current);
		}
		
		String selected = fd.open();

		dataDirFileText.setText(selected);
		
		notifyChange();

	}


	protected void handleFileBrowse(Text dataDirFileText) {

		FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setText("Select executable");
		
		String current = dataDirFileText.getText();
		if ( current == null || current.length() == 0 ){
			fd.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile().getAbsolutePath());	
		} else {
			fd.setFilterPath(current);
		}
		
		String selected = fd.open();

		dataDirFileText.setText(selected);

	}

	public VARTRegressionConfiguration getConfiguration(){

		VARTRegressionConfiguration regressionConfiguration;

		if ( loadedConfiguration == null ){
			regressionConfiguration = new VARTRegressionConfiguration();
		} else {
			regressionConfiguration = loadedConfiguration;
		}

		if ( customizeMCButton.getSelection() ){
			String text = modelCheckerExecutable.getText();
			if ( text != null && !text.isEmpty() ){
				regressionConfiguration.setModelCheckerExecutable(text);
			} else {
				regressionConfiguration.setModelCheckerExecutable(null);
			}
			
			text = compilerPathText.getText();
			if ( text != null && !text.isEmpty() ){
				regressionConfiguration.setCompilerExecutable(text);
			} else  {
				regressionConfiguration.setCompilerExecutable(null);
			}
		}
	
		if (cbmcButton.getSelection() ){
			regressionConfiguration.setModelChecker(ModelChecker.CBMC);
		} else {
			regressionConfiguration.setModelChecker(ModelChecker.EvolCheck);
		}
	
		if ( useMakeButton.getSelection() ){
			regressionConfiguration.setUseMakefileForMonitoring(true);
		}
		
		String varsToCheck = variablesToCheckText.getText();
		if ( varsToCheck != null ){
			if ( varsToCheck.length() == 0 ){
				varsToCheck = null;
			}
			regressionConfiguration.setVariablesToCheck(varsToCheck);
		}
		
		regressionConfiguration.setUnwind(loopUnwindingSpinner.getSelection());
		regressionConfiguration.setOptimizeSpeed(useMathSpeedUpButton.getSelection());
		regressionConfiguration.setTestTarget(testTargetText.getText());
		regressionConfiguration.setCheckAssertionsCoverage(checkAssertionsCoverageButton.getSelection());
		regressionConfiguration.setUsingRandomTests(usingRandomTestsButton.getSelection());
		regressionConfiguration.setValidateChangedFunctionsOnly(validateChangedFunctionsOnlyButton.getSelection());
		
		return regressionConfiguration;
	}


	public void load(VARTRegressionConfiguration config, MonitoringConfiguration monConfig) {
		monitoringConfiguration = monConfig;
		if ( initialized ){
			internaLoad( config );
		} else {
			toLoad = config;

		}
	}

	protected void internaLoad(VARTRegressionConfiguration config) {



		if ( config == null ){
			return;
		}

		loadedConfiguration = config;

		boolean enableCustomize = ( config.getModelCheckerExecutable() != null && config.getCompilerExecutable() != null );
		if ( enableCustomize ){
			customizeMCButton.setEnabled(true);
			modelCheckerExecutable.setText(config.getModelCheckerExecutable());
			compilerPathText.setText(config.getCompilerExecutable());
		}

		switch ( config.getModelChecker() ){
		case CBMC:
			evolcheckButton.setSelection(false);
			cbmcButton.setSelection(true);
			break;
		case EvolCheck:
			evolcheckButton.setSelection(true);
			cbmcButton.setSelection(false);
		}
		checkAssertionsCoverageButton.setSelection(config.isCheckAssertionsCoverage());
		loopUnwindingSpinner.setSelection(config.getUnwind());
		usingRandomTestsButton.setSelection( config.isUsingRandomTests() );
		validateChangedFunctionsOnlyButton.setSelection(config.isValidateChangedFunctionsOnly());
		useMakeButton.setSelection(config.isUseMakefileForMonitoring());
		testTargetText.setText(config.getTestTarget());
		useMathSpeedUpButton.setSelection(config.isOptimizeSpeed());
		
		String varsToCheck = config.getVariablesToCheck();
		if ( varsToCheck != null ){
			variablesToCheckText.setText( varsToCheck );
		}
	}



	@Override
	public void addBCTObserver(BCTObserver bctObserver) {
		observer.addBCTObserver(bctObserver);
	}

}
