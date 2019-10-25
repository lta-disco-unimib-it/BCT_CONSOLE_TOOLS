package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.invariantGenerator;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptionsException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentDefinitionShell.ComponentDefinitionShelException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines.FsaEngineOptionsComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines.KBehaviorOptionsComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.preferences.AddNewDaikonConfigDialog;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.InferenceEngineWizardPage;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.KInclusionWizardPage;

import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import conf.InvariantGeneratorSettings;
import dfmaker.core.DaikonDeclarationMaker.DaikonComparisonCriterion;
/**
 * this is the composite for Invariant Generator Options
 * 
 * @author Terragni Valerio
 *
 */
public class InvariantGeneratorOptionsComposite extends Composite implements BCTObservable {

	private BCTObservaleIncapsulated observer = new BCTObservaleIncapsulated(this);
	private Properties loadedOptions;

	public List daikonConfigList;
	public Combo fsaEngineCombo;
	public Text daikonPathText;
	public Button addAdditionalCheckButton;
	public Button expandReferencecheckButton;

	private Button inferComponentsInteractionModelsButton;

	private Button inferClassUsageInteractionModelsButton;

	private Button inferIoModelsButton;

	private Button inferComponentsIOInteractionModelsButton;

	private Combo daikonComparisonCriterionCombo;

	private Button skipInferenceButton;

	private Button keepExistingModelsButton;

	private Button skipFailingTests;

	private Button skipFailingActions;

	private Button skipFailingProcesses;

	private Button inferClassUsageInteractionModelsWithOutgoingCallsButton;

	private Properties loadedFsaOptions;
	private Button undoDaikonOptimizationsButton;

	private static final String[] daikonComparisonCriterionItems = new String[]{ 
			"Compare all", 
			"Compare only values with same representation type",
			"Compare only values of the same type"
	};
	
	private static final DaikonComparisonCriterion[] daikonComparisonCriterions = new DaikonComparisonCriterion[]{ 
		DaikonComparisonCriterion.CompareAll, 
		DaikonComparisonCriterion.CompareOnlySameDaikonRepresentationType,
		DaikonComparisonCriterion.CompareOnlySameValueType
	};
	
	@Override
	public void addBCTObserver(BCTObserver bctObserver) {
		observer.addBCTObserver(bctObserver);
	}
	
	public boolean getKeepExistingModels(){
		return keepExistingModelsButton.getSelection();
	}

	public InvariantGeneratorOptionsComposite(Composite parent, int style) { // create content
		this(parent, style, false);
	}
	
	public InvariantGeneratorOptionsComposite(Composite parent, int style, boolean showFSAOptions) { // create content
//		super(parent, SWT.FILL);
		super(parent, SWT.FILL);
		
		GridLayout layout = new GridLayout();
		layout.numColumns=3;
		this.setLayout(layout);
		
		
		
		Label modelsLabel = new Label(this,SWT.NULL);
		//fsaEngineLabel.setBounds(5,30,100,25);
		modelsLabel.setText("Models to infer : ");
		{
			GridData ld = new GridData();
			ld.horizontalSpan=3;
			modelsLabel.setLayoutData(ld);
		}
		
		//add an empty cell before
		new Label(this,SWT.NONE).setVisible(false);
		inferIoModelsButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			inferIoModelsButton.setLayoutData(ld);
		}
		inferIoModelsButton.setText("component IO models");
		inferIoModelsButton.setSelection(true);
		
		//add an empty cell before
		new Label(this,SWT.NONE).setVisible(false);
		inferComponentsInteractionModelsButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			inferComponentsInteractionModelsButton.setLayoutData(ld);
		}
		inferComponentsInteractionModelsButton.setText("component interaction models");
		inferComponentsInteractionModelsButton.setSelection(true);
		
		
		
		
		//add an empty cell before
		new Label(this,SWT.NONE).setVisible(false);
		inferComponentsIOInteractionModelsButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			inferComponentsIOInteractionModelsButton.setLayoutData(ld);
		}
		inferComponentsIOInteractionModelsButton.setText("component IO/interaction models");
		
		
		
		
		//add an empty cell before
		new Label(this,SWT.NONE).setVisible(false);
		inferClassUsageInteractionModelsButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			inferClassUsageInteractionModelsButton.setLayoutData(ld);
		}
		inferClassUsageInteractionModelsButton.setText("class usage models");
		
		
		//add an empty cell before
		new Label(this,SWT.NONE).setVisible(false);
		inferClassUsageInteractionModelsWithOutgoingCallsButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			inferClassUsageInteractionModelsWithOutgoingCallsButton.setLayoutData(ld);
		}
		inferClassUsageInteractionModelsWithOutgoingCallsButton.setText("class usage models with outgoing interactions");
		
		
		//add an empty cell before
		
		skipInferenceButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			skipInferenceButton.setLayoutData(ld);
		}
		skipInferenceButton.setText("Skip model inference. Only preprocess traces.");
		new Label(this,SWT.NONE).setVisible(false);
		
		

		
		skipFailingTests = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			skipFailingTests.setLayoutData(ld);
		}
		skipFailingTests.setText("Skip failing tests.");
		skipFailingTests.setSelection(true);
		new Label(this,SWT.NONE).setVisible(false);
		
		
		
		
		
		skipFailingActions = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			skipFailingActions.setLayoutData(ld);
		}
		skipFailingActions.setText("Skip failing actions.");
		skipFailingActions.setSelection(true);
		new Label(this,SWT.NONE).setVisible(false);
		

		
		skipFailingProcesses = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			skipFailingProcesses.setLayoutData(ld);
		}
		skipFailingProcesses.setText("Skip failing processes.");
		new Label(this,SWT.NONE).setVisible(false);
		
		
		
		
		keepExistingModelsButton = new Button(this,SWT.CHECK);
		{
			GridData ld = new GridData();
			ld.horizontalAlignment = SWT.LEFT;
			ld.horizontalSpan = 2;
			keepExistingModelsButton.setLayoutData(ld);
		}
		keepExistingModelsButton.setText("keep existing models");
		new Label(this,SWT.NONE).setVisible(false);
		
		//TODO: this should be removed when the functionality will be completely implemented in the library
		//Graphical check
		final Button interactionButtons [] = { inferClassUsageInteractionModelsButton, inferComponentsInteractionModelsButton, inferComponentsIOInteractionModelsButton };
		SelectionListener interactionButtonsSL = new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				
				for ( Button button : interactionButtons ){
					if ( e.getSource() != button ){
						button.setSelection(false);
					}
				}
			}
			
		};
		
		for ( Button interactionButton : interactionButtons ){
			interactionButton.addSelectionListener(interactionButtonsSL);
		}
		
		
		
		Label fsaEngineLabel = new Label(this,SWT.NULL);
		//fsaEngineLabel.setBounds(5,30,100,25);
		fsaEngineLabel.setText("FSA Inference Engine : ");


		fsaEngineCombo = new Combo(this, SWT.READ_ONLY);
		String[] fsaengineLabel = new String[]{"KBehavior", "KTail", "Reiss", "KInclusion","GKTail"};   // type of fsaengines are static
		for(int i=0; i<fsaengineLabel.length; i++)
			fsaEngineCombo.add(fsaengineLabel[i]);   //add in combo
		fsaEngineCombo.select(0);
		
		Button b = new Button(this, SWT.PUSH);
		b.setText("Customize");
		b.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showFSAOptions();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// end fsaengine field




		// start daikonConfig field	

		Group daikonGroup = new Group(this,SWT.NONE);
		GridLayout groupLayout = new GridLayout();
		groupLayout.numColumns = 3;
		daikonGroup.setLayout(layout);		
		daikonGroup.setText("Daikon Configuration");


		GridData groupLData = new GridData();
		groupLData.horizontalSpan = 3;
		groupLData.grabExcessHorizontalSpace = true;
		groupLData.horizontalAlignment = GridData.FILL;
		
		daikonGroup.setLayoutData(groupLData);
		
		
		// start daikonPath field
		Label daikonPathLabel = new Label(daikonGroup,SWT.NULL);
		daikonPathLabel.setText("Daikon path: ");
		daikonPathText = new Text(daikonGroup, SWT.BORDER);
		{
			GridData ld = new GridData();
			ld.grabExcessHorizontalSpace = true;
			ld.horizontalAlignment = SWT.FILL;
			daikonPathText.setLayoutData(ld);
		}


		Button daikonpathButton = new Button(daikonGroup,SWT.NULL);
		daikonpathButton.setText("Browse");

		daikonpathButton.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				Shell shell = new Shell();
				FileDialog fileDialog = new FileDialog(shell);
				String dir = fileDialog.open();
				if(dir != null) {
					daikonPathText.setText(dir); // get daikon path, choose by user
				}
			}


		});
		// end daikonPath field
		
		
		String[] daikonConfigLabel;
        
		//laod daikon configs in the list
		try {
			Set<String> daikonConfigOptions = DefaultOptionsManager.getDefaultOptions().getDaikonConfigPropertiesNames();// get daikon config saved

			daikonConfigLabel = new String[daikonConfigOptions.size()];
			int i=0;
			for( String daikonConfigName : daikonConfigOptions ){
				daikonConfigLabel[i] = daikonConfigName;
				++i;
			}

		} catch (CoreException e) {
			daikonConfigLabel = new String[0];
			MessageDialog.openError(getShell(), "Error", "Cannot load Daikon configs");
		} catch (DefaultOptionsManagerException e) {
			daikonConfigLabel = new String[0];
			MessageDialog.openError(getShell(), "Error", "Cannot load Daikon configs");
		}

		Label label = new Label(daikonGroup, SWT.NONE);
		label.setText("Invariants set to enable: ");
		
		daikonConfigList = new List(daikonGroup, SWT.BORDER | SWT.V_SCROLL);
		daikonConfigList.setItems(daikonConfigLabel);
		
		groupLData = new GridData();
		groupLData.grabExcessHorizontalSpace = true;
		groupLData.horizontalAlignment = SWT.FILL;
		groupLData.horizontalAlignment = GridData.FILL;
		daikonConfigList.setLayoutData(groupLData);

		Composite buttonsComposite = new Composite(daikonGroup,SWT.NONE);
		GridLayout bcld = new GridLayout();
		buttonsComposite.setLayout(bcld);

		Button addButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		
		addButton.setText("Add..."); 
		addButton.addSelectionListener(new SelectionAdapter() {  // add new daikon config
			public void widgetSelected(SelectionEvent event) {
				Shell shell = new Shell();
				AddNewDaikonConfigDialog w = new AddNewDaikonConfigDialog(shell); // open new daikon dialog
				String newEntry = w.open();
				daikonConfigList.add(newEntry, daikonConfigList.getItemCount()); //add new entry
				daikonConfigList.select(daikonConfigList.getItemCount()-1); // select new entry just added
			}
		});




		Button removeButton = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		removeButton.setText("Remove"); 
		
		removeButton.addSelectionListener(new SelectionAdapter() { //delete daikon config
			public void widgetSelected(SelectionEvent event) {
				Shell shell = new Shell();
				MessageBox messageBoxAsk = new MessageBox(shell , SWT.ICON_WARNING | SWT.OK| SWT.CANCEL);

				messageBoxAsk.setText("Delete");
				messageBoxAsk.setMessage("Do you really want to delete "+daikonConfigList.getSelection()[0]+" ?");
				int rc = messageBoxAsk.open(); // msgbox open 

				switch (rc) {
				case SWT.OK:
					String configToRemove = daikonConfigList.getItem(daikonConfigList.getSelectionIndex());
					try {
						DefaultOptionsManager.getDefaultOptions().removeDaikonConfigProperties(configToRemove);
						daikonConfigList.remove(daikonConfigList.getSelectionIndex());

					} catch (BctDefaultOptionsException e) {
						MessageDialog.openError(shell, "Error", "Cannot remove " + e.getMessage());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DefaultOptionsManagerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				}
			}});

		Label dclLabel = new Label(daikonGroup,SWT.NULL);
		dclLabel.setText("Daikon comparison criterion :");
		
		daikonComparisonCriterionCombo = new Combo(daikonGroup, SWT.NONE);
		daikonComparisonCriterionCombo.setItems(daikonComparisonCriterionItems );
		daikonComparisonCriterionCombo.select(0);

		{
			GridData ld = new GridData();
			ld.grabExcessHorizontalSpace = true;
			ld.horizontalSpan = 1;
			ld.horizontalAlignment = SWT.FILL;
			daikonComparisonCriterionCombo.setLayoutData(ld);
		}

		addAdditionalCheckButton = new Button(daikonGroup, SWT.CHECK);
		addAdditionalCheckButton.setText("Add additional invariants");
		GridData ld = new GridData();
		ld.grabExcessHorizontalSpace = true;
		ld.horizontalSpan = 3;
		ld.horizontalAlignment = SWT.FILL;
		addAdditionalCheckButton.setLayoutData(ld);
		

		expandReferencecheckButton = new Button(daikonGroup, SWT.CHECK);
		expandReferencecheckButton.setText("Expand References");
		expandReferencecheckButton.setLayoutData(ld);
		
		

		undoDaikonOptimizationsButton = new Button(daikonGroup, SWT.CHECK);
		undoDaikonOptimizationsButton.setText("Undo Daikon optimizations");
		undoDaikonOptimizationsButton.setLayoutData(ld);
		
		
		this.pack();
	}
	
	protected void showFSAOptions() {

		final Shell shell = new Shell(SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(650, 550);
//		observable= new BCTObservaleIncapsulated(this);
		

		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		shell.setLayout(layout);
		
		FsaEngineOptionsComposite inferenceEnginePage = null;
		String fsaEngineType = fsaEngineCombo.getItem( fsaEngineCombo.getSelectionIndex() );
		if ( fsaEngineType.equals("KBehavior") ){
			inferenceEnginePage = new KBehaviorOptionsComposite(shell,SWT.None);

		} else if( fsaEngineType.equals("KInclusion")){
			inferenceEnginePage = new FsaEngineOptionsComposite(shell,SWT.None);
		} else if( fsaEngineType.equals("Reiss")){
			inferenceEnginePage = new FsaEngineOptionsComposite(shell,SWT.None);

		} else if( fsaEngineType.equals("KTail")){
			inferenceEnginePage = new FsaEngineOptionsComposite(shell,SWT.None);
		}
		
		GridData ld = new GridData();
		ld.grabExcessHorizontalSpace = true;
		ld.horizontalSpan = 2;
		inferenceEnginePage.setLayoutData(ld);
		
		if ( loadedFsaOptions != null ){
			inferenceEnginePage.load(loadedFsaOptions);
			loadedFsaOptions = null;
		}
		
		
		
		final FsaEngineOptionsComposite fsaOptionsPage = inferenceEnginePage;
		
		Button okButton=new Button(shell, SWT.PUSH);
		okButton.setText("Ok");
		okButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent ev) {
				loadedFsaOptions = fsaOptionsPage.getUserDefinedOptions();
				observer.notifyBCTObservers("");
				shell.close();
			}
			
		});
		
		Button cancelButton=new Button(shell, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
			
		});
		shell.setVisible(true);
		shell.open();
		shell.pack();
	}

	/**
	 * fill the composite fields with invariantGeneratorOptions Properties
	 * 
	 * @param iinvariantGeneratorOptions Properties
	 */
	public void load( Properties invariantGeneratorOptions ){


		loadedOptions = invariantGeneratorOptions;

		String fsaEngine = (String) loadedOptions.get(InvariantGeneratorSettings.Options.fsaEngine);
		fsaEngineCombo.select(fsaEngineCombo.indexOf(fsaEngine)); //make combo select the preferencestore

		String daikonConfig = (String) loadedOptions.get(InvariantGeneratorSettings.Options.daikonConfig);
		daikonConfigList.select(daikonConfigList.indexOf(daikonConfig)); //select the store value

		daikonPathText.setText(invariantGeneratorOptions.getProperty(InvariantGeneratorSettings.Options.daikonPath));

		if (Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.addAdditionalInvariants))){

			addAdditionalCheckButton.setSelection(true);
		}else{
			addAdditionalCheckButton.setSelection(false);

		}
		
		if (Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.expandReferences))){

			expandReferencecheckButton.setSelection(true);
		}else{
			expandReferencecheckButton.setSelection(false);

		}
		
		if ( Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.inferClassesUsageInteractionModelsWithOutgoingCalls))){
			inferClassUsageInteractionModelsWithOutgoingCallsButton.setSelection(true);
		}
		
		if ( Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.inferClassesUsageInteractionModels))){
			inferClassUsageInteractionModelsButton.setSelection(true);
		}
		
		if ( Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.inferComponentsInteractionModels))){
			inferComponentsInteractionModelsButton.setSelection(true);
		}
		
		if ( Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.inferComponentsIOInteractionModels))){
			inferComponentsIOInteractionModelsButton.setSelection(true);
		}

		if ( Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.inferComponentsIOModels))){
			inferIoModelsButton.setSelection(true);
		}
		
		if ( Boolean.parseBoolean(loadedOptions.getProperty(InvariantGeneratorSettings.Options.daikonUndoOptmizations) ) ) {
			undoDaikonOptimizationsButton.setSelection(true);
		}
		
		String comparisonCriterion = loadedOptions.getProperty(InvariantGeneratorSettings.Options.daikonComparisonCriterion);
		if ( comparisonCriterion != null ){
			//identify the comparison criterion previously selected
			 
			for ( int i = 0; i <  daikonComparisonCriterions.length; i++ ){
				DaikonComparisonCriterion item = daikonComparisonCriterions [i];
				if (item.name().equals(comparisonCriterion) ){
					daikonComparisonCriterionCombo.select(i);
				}
			}
		}
		
		
	}

	/**
	 * This method returns a Properties object containing the invariant generator options as set by the user
	 * 
	 * @return
	 */
	public Properties getUserDefinedOptions() {
		Properties p = new Properties();
		p.putAll(loadedOptions);

		//here set the options with the values selected by the user
		p.setProperty( InvariantGeneratorSettings.Options.fsaEngine, fsaEngineCombo.getItem(fsaEngineCombo.getSelectionIndex()) );
		System.out.println(fsaEngineCombo.getItem(fsaEngineCombo.getSelectionIndex()));
		p.setProperty( InvariantGeneratorSettings.Options.daikonPath, daikonPathText.getText() );
		p.setProperty( InvariantGeneratorSettings.Options.daikonConfig, daikonConfigList.getItem(daikonConfigList.getSelectionIndex()));
		
		
		p.setProperty(InvariantGeneratorSettings.Options.inferClassesUsageInteractionModels, String.valueOf(inferClassUsageInteractionModelsButton.getSelection()) );
		p.setProperty(InvariantGeneratorSettings.Options.inferClassesUsageInteractionModelsWithOutgoingCalls, String.valueOf(inferClassUsageInteractionModelsWithOutgoingCallsButton.getSelection()) );
		p.setProperty(InvariantGeneratorSettings.Options.inferComponentsInteractionModels, String.valueOf(inferComponentsInteractionModelsButton.getSelection()) );
		p.setProperty(InvariantGeneratorSettings.Options.inferComponentsIOModels, String.valueOf(inferIoModelsButton.getSelection()) );
		p.setProperty(InvariantGeneratorSettings.Options.skipInference, String.valueOf(skipInferenceButton.getSelection()) );
		p.setProperty(InvariantGeneratorSettings.Options.inferComponentsIOInteractionModels, String.valueOf( inferComponentsIOInteractionModelsButton.getSelection()) );
		
		p.setProperty( InvariantGeneratorSettings.Options.daikonComparisonCriterion, 
				daikonComparisonCriterions[ daikonComparisonCriterionCombo.getSelectionIndex() ].toString() 
		);
		
		p.setProperty( InvariantGeneratorSettings.Options.daikonUndoOptmizations, String.valueOf(undoDaikonOptimizationsButton.getSelection()) );
		
		String addAdditionalValue;
		if (addAdditionalCheckButton.getSelection()==true){
			addAdditionalValue = "true";
		}else{
			addAdditionalValue = "false";
		}
		
		p.setProperty( InvariantGeneratorSettings.Options.addAdditionalInvariants,addAdditionalValue );
		String expandReferencesValue;
		if (expandReferencecheckButton.getSelection()==true){
			expandReferencesValue = "true";

		}else{
			expandReferencesValue = "false";
		}
		p.setProperty( InvariantGeneratorSettings.Options.expandReferences,expandReferencesValue );

		return p;
	}

	public boolean getSkipFailingActions() {
		return skipFailingActions.getSelection();
	}

	public boolean getSkipFailingTests() {
		return skipFailingTests.getSelection();
	}
	
	public boolean getSkipFailingProcesses() {
		return skipFailingProcesses.getSelection();
	}
	
	public void loadFSAOptions( Properties fsaOptions ){
		this.loadedFsaOptions = fsaOptions;
	}
	
	public Properties getFSAEngineOptions(){
		return loadedFsaOptions;
	}
}
