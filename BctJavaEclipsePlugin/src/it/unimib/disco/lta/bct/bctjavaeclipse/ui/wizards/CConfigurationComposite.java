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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConsoleHelper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.ResourcesPage;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.GridData;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cpp.gdb.GdbRegressionConfigCreator;

public class CConfigurationComposite extends Composite implements BCTObservable {

	private Text originalSwFolderText;

	private Text originalSwText;
	private CConfiguration toLoad;
	private boolean initialized;
	private Label originalCommands;
	private Label modifiedCommands;
	protected MonitoringConfiguration monitoringConfiguration;

	private Button useDemangledNamesButton;
	private Button monitorProjectFunctionsOnlyButton;
	private BCTObservaleIncapsulated observer = new BCTObservaleIncapsulated(this);

	private Button simulateClosingOfLastUnclosedFunctionCallsButton;

	private Button discardUnclosedFunctionCallsButton;

	private Button discardUnclosedFunctionButton;

	protected Button traceAllLinesOfMonitoredFunctionsButton;

	protected CConfiguration loadedConfiguration;

	private Button deriveFunctionInvariantsButton;

	protected Button deriveLineInvariantsButton;

	private Button monitorGlobalVariablesButton;

	private Button recordCallingContextDataButton;

	private Button monitorLocalVariablesButton;

	private Button recordFunctionsCalledByTargetsButton;

	private Button monitorLibraryCallsButton;

	private Button monitorFunctionEnterExitPointsButton;

//	private Button showButtonsButton;

//	private boolean visible = true;

	private Composite parent;

	private Button excludeLineInfoFromFSAButton;

	private Button dllButton;

	private Button excludeUnusedVariablesButton;

	private Text testsFolderText;

	
	public CConfigurationComposite(Composite container, int none) {
//		super(container,none);
		super(container, SWT.FILL);
		Composite parentCont = this;
//				new Composite(container, SWT.NONE);
//		parentCont = new Composite(container, SWT.None);
		

		





		GridLayout layout = new GridLayout();
		layout.numColumns = 3;

		parentCont.setLayout(layout);


		//NEW ROW

		Label label = new Label(parentCont,SWT.NONE);
		label.setText("Original software sources:");

		originalSwFolderText = new Text(parentCont, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		originalSwFolderText.setEditable(true);
		org.eclipse.swt.layout.GridData gd = new org.eclipse.swt.layout.GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		originalSwFolderText.setLayoutData(gd);
		
		Button button = new Button(parentCont, SWT.NONE);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(originalSwFolderText);
			}
		});



		//NEW ROW


		label = new Label(parentCont,SWT.NONE);
		label.setText("Original software executable:");

		originalSwText = new Text(parentCont, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		originalSwText.setEditable(true);
		
		gd = new org.eclipse.swt.layout.GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		originalSwText.setLayoutData(gd);

		button = new Button(parentCont, SWT.NONE);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(originalSwText);
			}
		});


		createAdditionalLocations(parentCont);
		parent=parentCont;

		
		//NEW ROW


		label = new Label(parentCont,SWT.NONE);
		label.setText("Tests folder:");

		testsFolderText = new Text(parentCont, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		testsFolderText.setEditable(true);
		
		gd = new org.eclipse.swt.layout.GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = true;
		testsFolderText.setLayoutData(gd);

		Label l = new Label(parentCont,SWT.NONE);




		//NEW ROW

		button = new Button(parentCont, SWT.NONE);
		button.setText("Help");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				openMonitoringHelp();
			}
		});

		label = new Label(parentCont,SWT.NONE);
		label.setText("Click the button for help on monitoring C/C++ programs");

		new Label(parentCont,SWT.NONE);



//		showButtonsButton = new Button(parentCont, SWT.CHECK);
//		
//		label = new Label(parentCont,SWT.NONE);
//		label.setText("Show buttons.");
//		showButtonsButton.setSelection(visible);
//
//		new Label(parentCont,SWT.NONE);
		
		
//		parent = parentCont;
//		createHiddenButtonsComposite( this );
		
		

		//NEW ROW

		dllButton = new Button(parent, SWT.CHECK);
		dllButton.setSelection(false);
		dllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("DLL");
		new Label(parent,SWT.NONE);


		

		//NEW ROW

		useDemangledNamesButton = new Button(parent, SWT.CHECK);
		useDemangledNamesButton.setSelection(false);
		//		useDemangledNamesButton.setEnabled(false);//ONLY MANGLED NAMES WORK FOR NOW
		useDemangledNamesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});

		label = new Label(parent,SWT.NONE);
		label.setText("Use demangled names.");


		new Label(parent,SWT.NONE);


		//NEW ROW

		monitorProjectFunctionsOnlyButton = new Button(parent, SWT.CHECK);
		monitorProjectFunctionsOnlyButton.setSelection(true);
		monitorProjectFunctionsOnlyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor project functions only.");
		new Label(parent,SWT.NONE);

		//NEW ROW

		monitorLibraryCallsButton = new Button(parent, SWT.CHECK);
		monitorLibraryCallsButton.setSelection(false);
		monitorLibraryCallsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor calls to library functions.");
		new Label(parent,SWT.NONE);
		
		//NEW ROW

		simulateClosingOfLastUnclosedFunctionCallsButton = new Button(parent, SWT.CHECK);
		simulateClosingOfLastUnclosedFunctionCallsButton.setSelection(false);
		simulateClosingOfLastUnclosedFunctionCallsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Simulate termination of function calls not terminated at the end of the trace.");


		new Label(parent,SWT.NONE);

		

		//NEW ROW

		discardUnclosedFunctionCallsButton = new Button(parent, SWT.CHECK);
		discardUnclosedFunctionCallsButton.setSelection(false);
		discardUnclosedFunctionCallsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Filter out function calls not terminated.");


		new Label(parent,SWT.NONE);


		//NEW ROW

		discardUnclosedFunctionButton = new Button(parent, SWT.CHECK);
		discardUnclosedFunctionButton.setSelection(false);
		discardUnclosedFunctionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Filter out functions that are not terminated at least once.");
		new Label(parent,SWT.NONE);

		
		//NEW ROW


		monitorFunctionEnterExitPointsButton = new Button(parent, SWT.CHECK);
		monitorFunctionEnterExitPointsButton.setSelection(true);
		monitorFunctionEnterExitPointsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor function Enter/Exit points.");
		new Label(parent,SWT.NONE);
		
		//NEW ROW

		traceAllLinesOfMonitoredFunctionsButton = new Button(parent, SWT.CHECK);
		traceAllLinesOfMonitoredFunctionsButton.setSelection(true);
		traceAllLinesOfMonitoredFunctionsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Trace all lines of monitored functions.");
		new Label(parent,SWT.NONE);

		//NEW ROW

		deriveFunctionInvariantsButton = new Button(parent, SWT.CHECK);
		deriveFunctionInvariantsButton.setSelection(false);
		deriveFunctionInvariantsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Derive function invariants.");
		new Label(parent,SWT.NONE);

		//NEW ROW

		deriveLineInvariantsButton = new Button(parent, SWT.CHECK);
		deriveLineInvariantsButton.setSelection(true);
		deriveLineInvariantsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Derive line invariants.");
		new Label(parent,SWT.NONE);

		//NEW ROW
		monitorLocalVariablesButton = new Button(parent, SWT.CHECK);
		monitorLocalVariablesButton.setSelection(true);
		monitorLocalVariablesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});

		label = new Label(parent,SWT.NONE);
		label.setText("Monitor local variables.");
		new Label(parent,SWT.NONE);

		//NEW ROW

		monitorGlobalVariablesButton = new Button(parent, SWT.CHECK);
		monitorGlobalVariablesButton.setSelection(true);
		monitorGlobalVariablesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor global variables.");
		new Label(parent,SWT.NONE);

		//NEW ROW

		recordFunctionsCalledByTargetsButton = new Button(parent, SWT.CHECK);
		recordFunctionsCalledByTargetsButton.setSelection(true);
		recordFunctionsCalledByTargetsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Monitor functions called by target functions.");
		new Label(parent,SWT.NONE);

		
		
		
		
		//NEW ROW


		recordCallingContextDataButton = new Button(parent, SWT.CHECK);
		recordCallingContextDataButton.setSelection(false);
		recordCallingContextDataButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Record calling context data.");
		new Label(parent,SWT.NONE);


		
		//NEW ROW

		excludeLineInfoFromFSAButton = new Button(parent, SWT.CHECK);
		excludeLineInfoFromFSAButton.setSelection(false);
		excludeLineInfoFromFSAButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Exclude line info from FSA.");
		new Label(parent,SWT.NONE);


		
		//NEW ROW

		excludeUnusedVariablesButton = new Button(parent, SWT.CHECK);
		excludeUnusedVariablesButton.setSelection(true);
		excludeUnusedVariablesButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				notifyChange();
			}
		});


		label = new Label(parent,SWT.NONE);
		label.setText("Exclude unused variables.");
		new Label(parent,SWT.NONE);


		
		//NEW ROW
		

//		parent.pack();
//		sash.setMaximizedControl(parent);
		
//		child = new Composite(parentCont, style)
		
		
		
		
		createAdditionalButtons(parentCont);
		
//		this.setAlwaysShowScrollBars(true);
//		this.setContent(parentCont);
//		this.setSize(parentCont.computeSize(SWT.DEFAULT, SWT.DEFAULT));
//		this.layout(true);
//		this.pack();
		
//		label = new Label(container,SWT.NONE);
		
		initialized = true;

		internaLoad(toLoad);
	}

//	private void createHiddenButtonsComposite(Composite parentCont) {
//		// TODO Auto-generated method stub
//		//START_PARENT
//		
//		
////		sc1 = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		
//		
//		
////		parent.setVisible(visible);
//
//		parent = parentCont;
////		parent = new Composite(sc1, SWT.NONE);
////		
////		GridData ld = new GridData();
////		ld.horizontalSpan = 3;
////		parent.setLayoutData(ld);
//////		
////		GridLayout l = new GridLayout(3, false);
////		parent.setLayout(l);
//		
////
////		showButtonsButton.addSelectionListener( new SelectionAdapter() {
////			
////
////			public void widgetSelected(SelectionEvent e) {
////				visible = ! visible;
////				parent.setVisible(visible);
////				parent.setSize(0, 0);
////				layout();
////				pack();
////				
////			}
////		});
//		//END_PARENT
//	}

	protected void createAdditionalButtons(Composite parent) {
		// TODO Auto-generated method stub

	}

	protected void createAdditionalLocations(Composite parent) {

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
		
		File f = new File(selected);
		
		String[] testFoldersNames = new String[]{"test","tests"};
		for ( String name : testFoldersNames ){
			File testsFolder = new File( f, name );
			if ( testsFolder.exists() ){
				testsFolderText.setText(name);
				break;
			}
		}
		
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

	public CConfiguration getConfiguration(){

		CConfiguration regressionConfiguration;

		if ( loadedConfiguration == null ){
			regressionConfiguration = new CConfiguration();
		} else {
			regressionConfiguration = loadedConfiguration;
		}


		regressionConfiguration.setOriginalSwExecutable(this.originalSwText.getText());
		regressionConfiguration.setOriginalSwSourcesFolder(this.originalSwFolderText.getText());

		String testsFolder = testsFolderText.getText();
		if ( testsFolder != null && ! testsFolder.isEmpty() ){
			regressionConfiguration.setTestsFolder(testsFolder);
		}

		regressionConfiguration.setUseDemangledNames( this.useDemangledNamesButton.getSelection() );
		regressionConfiguration.setMonitorProjectFunctionsOnly( this.monitorProjectFunctionsOnlyButton.getSelection() );

		regressionConfiguration.setFilterNotTerminatedFunctionCalls(discardUnclosedFunctionCallsButton.getSelection());
		regressionConfiguration.setFilterAllNonTerminatingFunctions(discardUnclosedFunctionButton.getSelection());
		regressionConfiguration.setSimulateClosingOfLastNotTerminatedFunctions(simulateClosingOfLastUnclosedFunctionCallsButton.getSelection());


		regressionConfiguration.setTraceAllLinesOfMonitoredFunctions(traceAllLinesOfMonitoredFunctionsButton.getSelection());
		regressionConfiguration.setDeriveFunctionInvariants(deriveFunctionInvariantsButton.getSelection());
		regressionConfiguration.setDeriveLineInvariants(deriveLineInvariantsButton.getSelection());

		regressionConfiguration.setMonitorGlobalVariables(monitorGlobalVariablesButton.getSelection());
		regressionConfiguration.setMonitorLocalVariables(monitorLocalVariablesButton.getSelection());
		regressionConfiguration.setMonitorFunctionsCalledByTargetFunctions(recordFunctionsCalledByTargetsButton.getSelection());

		regressionConfiguration.setMonitorFunctionEnterExitPoints(monitorFunctionEnterExitPointsButton.getSelection());
		regressionConfiguration.setMonitorLibraryCalls(monitorLibraryCallsButton.getSelection());

		regressionConfiguration.setRecordCallingContextData(recordCallingContextDataButton.getSelection());
		
		regressionConfiguration.setExcludeLineInfoFromFSA(excludeLineInfoFromFSAButton.getSelection());

		regressionConfiguration.setDll(dllButton.getSelection());
		
		regressionConfiguration.setExcludeUnusedVariables(excludeUnusedVariablesButton.getSelection());
		
		return regressionConfiguration;
	}


	public void load(CConfiguration config, MonitoringConfiguration monConfig) {
		monitoringConfiguration = monConfig;
		if ( initialized ){
			internaLoad( config );
		} else {
			toLoad = config;

		}
	}

	protected void internaLoad(CConfiguration config) {



		if ( config == null ){
			return;
		}

		loadedConfiguration = config;

		if ( config.getOriginalSwExecutable() != null ){
			originalSwText.setText(config.getOriginalSwExecutable());
		}
		if ( config.getOriginalSwSourcesFolder() != null ){
			originalSwFolderText.setText(config.getOriginalSwSourcesFolder());
		}


		String testsFolder = config.getTestsFolder();
		if ( testsFolder != null ){
			testsFolderText.setText(testsFolder);
		}
		
		useDemangledNamesButton.setSelection( config.isUseDemangledNames() );
		monitorProjectFunctionsOnlyButton.setSelection( config.isMonitorProjectFunctionsOnly() );
		monitorLibraryCallsButton.setSelection( config.isMonitorLibraryCalls() );

		discardUnclosedFunctionCallsButton.setSelection(config.isFilterNotTerminatedFunctionCalls());
		discardUnclosedFunctionButton.setSelection(config.isFilterAllNonTerminatingFunctions());
		simulateClosingOfLastUnclosedFunctionCallsButton.setSelection(config.isSimulateClosingOfLastNotTerminatedFunctions());

		traceAllLinesOfMonitoredFunctionsButton.setSelection(config.isTraceAllLinesOfMonitoredFunctions());
		deriveLineInvariantsButton.setSelection(config.isDeriveLineInvariants());
		deriveFunctionInvariantsButton.setSelection(config.isDeriveFunctionInvariants());

		monitorGlobalVariablesButton.setSelection(config.isMonitorGlobalVariables());
		monitorLocalVariablesButton.setSelection(config.isMonitorLocalVariables());
		recordFunctionsCalledByTargetsButton.setSelection(config.isMonitorFunctionsCalledByTargetFunctions());


		monitorFunctionEnterExitPointsButton.setSelection( config.isMonitorFunctionEnterExitPoints());
		
		recordCallingContextDataButton.setSelection(config.isRecordCallingContextData());
		
		excludeLineInfoFromFSAButton.setSelection(config.isExcludeLineInfoFromFSA());
		
		excludeUnusedVariablesButton.setSelection( config.isExcludeUnusedVariables() );
		
		dllButton.setSelection(config.isDll());
	}

	protected void openMonitoringHelp(){

		Shell shell = new Shell(SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(650, 550);

		//Composite main = new Composite(shell, SWT.NONE); 
		Shell main = shell;


		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		main.setLayout(layout);

		String message = "Execute the following commands on a command shell to record traces of valid executions:\n";

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
				"to record traces to analyze:\n");

		Text toAnalyzeCommand = new Text(main,SWT.MULTI);
		if ( monitoringConfiguration != null ){
			try {
				File traceToVerify = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfigToVerify(monitoringConfiguration);


				toAnalyzeCommand.setText( createGdbMonitoringcommandHelp( traceToVerify ) );
			} catch (ConfigurationFilesManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		shell.pack();
		shell.open();


	}


	protected String createGdbMonitoringcommandHelp(File traceToVerify) {
		return ConsoleHelper.createGdbMonitoringcommandHelp(traceToVerify);
	}


	@Override
	public void addBCTObserver(BCTObserver bctObserver) {
		observer.addBCTObserver(bctObserver);
	}

}
