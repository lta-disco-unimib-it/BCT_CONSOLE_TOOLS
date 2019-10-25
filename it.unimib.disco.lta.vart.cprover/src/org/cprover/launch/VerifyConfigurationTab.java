package org.cprover.launch;

import java.util.ArrayList;
import java.util.EnumSet;

import org.cprover.core.CProverPlugin;
import org.cprover.launch.VerifyConfig.Verifier;
import org.cprover.preferences.PreferenceConstants;
import org.cprover.ui.Constants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * @author Gérard Basler
 * @version $Revision: 1.1 $
 */
public class VerifyConfigurationTab extends AbstractLaunchConfigurationTab {

	private boolean useXmlBin;

	// common launch preferences
	private Composite groupCommon;
	private Combo cmbVerifier;
	private Text txtMainFun;
	private Button btEndian1;
	private Button btEndian2;
	private Button btEndian3;
	private Button btWordWidth1;
	private Button btWordWidth2;
	private Button btWordWidth3;
	private Button btWordWidth4;
	private Button btWordWidth5;
	private Button btWordWidth6;
	
	private Button btAssertions;
	private Button btArrayBounds;
	private Button btDivByZero;
	private Button btPointers;
	private Button btOverflow;
	// cbmc preferences
	private Composite groupCbmc;
	//private Button btValueSets;
	private Button btSimplify;
	//private Button btKeepClaims;
	private Combo cmbUnwind;
	private Button btSlice;
	//private Button btSubstitution;
	private Button btUnwindingAssertions;
	private Button btNancheck;
	
	private Button btBeautify1;
	private Button btBeautify2;
	private Button btBeautify3;

	private Button btRoundmode1;
	private Button btRoundmode2;
	private Button btRoundmode3;
	private Button btRoundmode4;
	private Button btRoundmode5;
	
	// hw-cbmc, ebmc, vcegar preferences
	private Composite groupHwCbmc;
	private Combo cmbModule;
	private Combo cmbBound;
	private Label lbBound;
	// satabs preferences
	private Composite groupSatabs;
	private Combo cmbModelchecker;
	private Combo cmbAbstractor;
	private Combo cmbRefiner;
	private Combo cmbIterations;
	private Button btDataRaces;
	private Button btLoopDetection;

	private Composite tabInclDef;
	private TabItem tabCommon;
	private TabItem tabSatabs;
	private TabItem tabCbmc;
	private TabItem tabHwCbmc;
	private TabFolder tabOptions;

	private SelectionListener selectionListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			updateLaunchConfigurationDialog();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}
	};

	public void createControl(Composite parent) {
		Font font = parent.getFont();
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setFont(font);

		Composite group = new Composite(composite, SWT.LEFT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		Label label = new Label(group, SWT.LEFT);
		label.setText("Verifier");

		cmbVerifier = new Combo(group, SWT.LEFT | SWT.READ_ONLY);
		cmbVerifier.setItems(this.getVerifiers());
		cmbVerifier.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				adaptToVerifier();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		tabOptions = new TabFolder(composite, SWT.NONE);
		tabOptions.setLayoutData(new GridData(GridData.FILL_BOTH));

		// common
		groupCommon = new Composite(tabOptions, SWT.NONE);
		groupCommon.setLayout(new GridLayout());
		groupCommon.setLayoutData(new GridData(GridData.FILL_BOTH));

		btAssertions = new Button(groupCommon, SWT.CHECK | SWT.LEFT);
		btAssertions.setText("Check assertions");
		btAssertions.addSelectionListener(selectionListener);

		btArrayBounds = new Button(groupCommon, SWT.CHECK | SWT.LEFT);
		btArrayBounds.setText("Check array bounds");
		btArrayBounds.addSelectionListener(selectionListener);

		btDivByZero = new Button(groupCommon, SWT.CHECK | SWT.LEFT);
		btDivByZero.setText("Check division by zero");
		btDivByZero.addSelectionListener(selectionListener);

		btPointers = new Button(groupCommon, SWT.CHECK | SWT.LEFT);
		btPointers.setText("Check pointers");
		btPointers.addSelectionListener(selectionListener);

		btOverflow = new Button(groupCommon, SWT.CHECK | SWT.LEFT);
		btOverflow.setText("Check arithmetic overflow");
		btOverflow.addSelectionListener(selectionListener);
		
		btNancheck = new Button(groupCommon, SWT.CHECK | SWT.LEFT);
		btNancheck.setText("Nan check");
		btNancheck.addSelectionListener(selectionListener);

		group = new Composite(groupCommon, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 7;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Word width");
		btWordWidth1 = new Button(group, SWT.RADIO);
		btWordWidth1.setText("Machine default");
		btWordWidth1.addSelectionListener(selectionListener);
		btWordWidth2 = new Button(group, SWT.RADIO);
		btWordWidth2.setText("LP64");
		btWordWidth2.addSelectionListener(selectionListener);
		btWordWidth3 = new Button(group, SWT.RADIO);
		btWordWidth3.setText("ILP64");
		btWordWidth3.addSelectionListener(selectionListener);
		btWordWidth4 = new Button(group, SWT.RADIO);
		btWordWidth4.setText("LLP64");
		btWordWidth4.addSelectionListener(selectionListener);
		btWordWidth5 = new Button(group, SWT.RADIO);
		btWordWidth5.setText("ILP32");
		btWordWidth5.addSelectionListener(selectionListener);
		btWordWidth6 = new Button(group, SWT.RADIO);
		btWordWidth6.setText("LP32");
		btWordWidth6.addSelectionListener(selectionListener);

		group = new Composite(groupCommon, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 4;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Endianess");
		btEndian1 = new Button(group, SWT.RADIO);
		btEndian1.setText("Machine default");
		btEndian1.addSelectionListener(selectionListener);
		btEndian2 = new Button(group, SWT.RADIO);
		btEndian2.setText("Little-endian");
		btEndian2.addSelectionListener(selectionListener);
		btEndian3 = new Button(group, SWT.RADIO);
		btEndian3.setText("Big-endian");
		btEndian3.addSelectionListener(selectionListener);

		group = new Composite(groupCommon, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 6;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Rounding mode");
		btRoundmode1 = new Button(group, SWT.RADIO);
		btRoundmode1.setText("Machine default");
		btRoundmode1.addSelectionListener(selectionListener);
		btRoundmode2 = new Button(group, SWT.RADIO);
		btRoundmode2.setText("to nearest");
		btRoundmode2.addSelectionListener(selectionListener);
		btRoundmode3 = new Button(group, SWT.RADIO);
		btRoundmode3.setText("to plus inf");
		btRoundmode3.addSelectionListener(selectionListener);
		btRoundmode4 = new Button(group, SWT.RADIO);
		btRoundmode4.setText("to minus inf");
		btRoundmode4.addSelectionListener(selectionListener);
		btRoundmode5 = new Button(group, SWT.RADIO);
		btRoundmode5.setText("to zero");
		btRoundmode5.addSelectionListener(selectionListener);
		
		group = new Composite(groupCommon, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Main function");

		txtMainFun = new Text(group, SWT.LEFT | SWT.BORDER);
		GridData gdata = new GridData();
		gdata.widthHint = 200;
		txtMainFun.setLayoutData(gdata);
		txtMainFun.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CR) {
					txtMainFun.setBackground(txtMainFun.getDisplay().getSystemColor(SWT.COLOR_WHITE));
					updateLaunchConfigurationDialog();
				}
			}
		});
		txtMainFun.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				txtMainFun.setBackground(txtMainFun.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
				updateLaunchConfigurationDialog();
			}
		});
		
		this.createCbmcOptions();

		// hw-cbmc, ebmc, vcegar
		groupHwCbmc = new Composite(tabOptions, SWT.NONE);
		groupHwCbmc.setLayout(new GridLayout());
		groupHwCbmc.setLayoutData(new GridData(GridData.FILL_BOTH));

		group = new Composite(groupHwCbmc, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Module to unwind");

		cmbModule = new Combo(group, SWT.LEFT);
		gdata = new GridData();
		gdata.widthHint = 200;
		cmbModule.setLayoutData(gdata);
		cmbModule.addSelectionListener(selectionListener);

		final Button btModules = new Button(group, SWT.PUSH | SWT.LEFT);
		btModules.setText("Get modules");
		btModules.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				btModules.setEnabled(false);
				// refreshModules(packTask());
				btModules.setEnabled(true);
				updateLaunchConfigurationDialog();
			}
		});

		group = new Composite(groupHwCbmc, SWT.RIGHT);
		layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		lbBound = new Label(group, SWT.LEFT);
		lbBound.setText("Number of transitions");

		cmbBound = new Combo(group, SWT.LEFT);
		cmbBound.setItems(this.getBounds());
		cmbBound.addSelectionListener(selectionListener);

		// satabs
		groupSatabs = new Composite(tabOptions, SWT.NONE);
		groupSatabs.setLayout(new GridLayout());
		groupSatabs.setLayoutData(new GridData(GridData.FILL_BOTH));

		group = new Composite(groupSatabs, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Modelchecker");

		cmbModelchecker = new Combo(group, SWT.LEFT | SWT.READ_ONLY);
		cmbModelchecker.setItems(this.getModelCheckers());
		cmbModelchecker.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				adaptToModelChecker();
				updateLaunchConfigurationDialog();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});

		group = new Composite(groupSatabs, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Abstractor");

		cmbAbstractor = new Combo(group, SWT.LEFT | SWT.READ_ONLY);
		cmbAbstractor.setItems(this.getAbstractors());
		cmbAbstractor.addSelectionListener(selectionListener);

		group = new Composite(groupSatabs, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Refiner");

		cmbRefiner = new Combo(group, SWT.LEFT | SWT.READ_ONLY);
		cmbRefiner.setItems(this.getRefiners());
		cmbRefiner.addSelectionListener(selectionListener);

		group = new Composite(groupSatabs, SWT.LEFT);
		layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Maximum number of refinement iterations");

		cmbIterations = new Combo(group, SWT.LEFT);
		cmbIterations.setItems(this.getIterations());
		cmbIterations.addSelectionListener(selectionListener);

		btDataRaces = new Button(groupSatabs, SWT.CHECK | SWT.LEFT);
		btDataRaces.setText("Detect data races");
		btDataRaces.addSelectionListener(selectionListener);

		btLoopDetection = new Button(groupSatabs, SWT.CHECK | SWT.LEFT);
		btLoopDetection.setText("Perform loop detection");
		btLoopDetection.addSelectionListener(selectionListener);

		setControl(composite);
	}

	private void createCbmcOptions() {
		// cbmc
		groupCbmc = new Composite(tabOptions, SWT.NONE);
		groupCbmc.setLayout(new GridLayout());
		groupCbmc.setLayoutData(new GridData(GridData.FILL_BOTH));

		/*btValueSets = new Button(groupCbmc, SWT.CHECK | SWT.LEFT);
		btValueSets.setText("Show value-sets");
		btValueSets.addSelectionListener(selectionListener);
		 */
		btSimplify = new Button(groupCbmc, SWT.CHECK | SWT.LEFT);
		btSimplify.setText("Simplify");
		btSimplify.addSelectionListener(selectionListener);

		/*btKeepClaims = new Button(groupCbmc, SWT.CHECK | SWT.LEFT);
		btKeepClaims.setText("Keep all claims");
		btKeepClaims.addSelectionListener(selectionListener);
		 */
		btSlice = new Button(groupCbmc, SWT.CHECK | SWT.LEFT);
		btSlice.setText("Do slicing");
		btSlice.addSelectionListener(selectionListener);
		/*
		btSubstitution = new Button(groupCbmc, SWT.CHECK | SWT.LEFT);
		btSubstitution.setText("Perform substitution");
		btSubstitution.addSelectionListener(selectionListener);
		*/
		btUnwindingAssertions = new Button(groupCbmc, SWT.CHECK | SWT.RIGHT);
		btUnwindingAssertions.setText("Generate unwinding assertions");
		btUnwindingAssertions.addSelectionListener(selectionListener);

		Composite group = new Composite(groupCbmc, SWT.RIGHT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		Label label = new Label(group, SWT.LEFT);
		label.setText("Number of unwindings");

		cmbUnwind = new Combo(group, SWT.LEFT);
		cmbUnwind.setItems(this.getUnwindings());
		cmbUnwind.addSelectionListener(selectionListener);

		group = new Composite(groupCbmc, SWT.RIGHT);
		layout = new GridLayout();
		layout.numColumns = 4;
		group.setLayout(layout);

		label = new Label(group, SWT.LEFT);
		label.setText("Beautification");
		btBeautify1 = new Button(group, SWT.RADIO);
		btBeautify1.setText("None");
		btBeautify1.addSelectionListener(selectionListener);
		btBeautify2 = new Button(group, SWT.RADIO);
		btBeautify2.setText("Greedy");
		btBeautify2.addSelectionListener(selectionListener);
		btBeautify3 = new Button(group, SWT.RADIO);
		btBeautify3.setText("PBS");
		btBeautify3.addSelectionListener(selectionListener);
	}

	private void adaptToVerifier() {
		String verifier = cmbVerifier.getText();
		
		if (tabCommon != null)
			tabCommon.dispose();
		if (tabCbmc != null)
			tabCbmc.dispose();
		if (tabHwCbmc != null)
			tabHwCbmc.dispose();
		if (tabSatabs != null)
			tabSatabs.dispose();
		if (!verifier.equals("ebmc") && !verifier.equals("vcegar")) {
			tabCommon = new TabItem(tabOptions, SWT.NONE);
			tabCommon.setText("Common options");
			tabCommon.setControl(groupCommon);
		}
		if (verifier.endsWith("cbmc")) {
			tabCbmc = new TabItem(tabOptions, SWT.NONE);
			tabCbmc.setText("CBMC");
			tabCbmc.setControl(groupCbmc);
		}
		if (verifier.equals("hw-cbmc") || verifier.equals("ebmc") || verifier.equals("vcegar")) {
			tabHwCbmc = new TabItem(tabOptions, SWT.NONE);
			tabHwCbmc.setControl(groupHwCbmc);
			if (verifier.equals("hw-cbmc"))
				tabHwCbmc.setText("HW-CBMC");
			else if (verifier.equals("ebmc"))
				tabHwCbmc.setText("EBMC");
			else
				tabHwCbmc.setText("VCEGAR");
			lbBound.setEnabled(!verifier.equals("vcegar"));
			cmbBound.setEnabled(!verifier.equals("vcegar"));
		}
		if (verifier.equals("satabs")) {
			tabSatabs = new TabItem(tabOptions, SWT.NONE);
			tabSatabs.setText("SATABS");
			tabSatabs.setControl(groupSatabs);
		}
		adaptToModelChecker();
	}

	private String[] getUnwindings() {
		return new String[] { "0", "1", "5", "10", "20", "50", "100" };
	}

	private String[] getBounds() {
		return new String[] { "1", "5", "10", "20", "50", "100" };
	}

	private String[] getIterations() {
		return new String[] { "0", "1", "5", "10", "20", "50", "100" };
	}

	private String[] getVerifiers() {
		ArrayList<String> verifiers = new ArrayList<String>();
		for (Verifier s : EnumSet.allOf(Verifier.class)) {
			verifiers.add(s.getName());
		}
		return (String[]) verifiers.toArray(new String[verifiers.size()]);
	}

	private String[] getModelCheckers() {
		return new String[] { "default", "nusmv", "cadence-smv", "cmu-smv", "satmc", "spin", "boppo", "boom" };
	}

	private String[] getAbstractors() {
		return new String[] { "default", "none", "wp", "prover", "satqe" };
	}

	private String[] getRefiners() {
		return new String[] { "default", "wp", "ipp" };
	}

	public String getName() {
		return "Launch Options";
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		IPreferenceStore prefs = CProverPlugin.getDefault().getPreferenceStore();

		try {
			// common options
			cmbVerifier.setText(configuration.getAttribute(Constants.ATTR_VERIFIER, 
					prefs.getString(PreferenceConstants.verifier)));
			txtMainFun.setText(configuration.getAttribute(Constants.ATTR_MAINFUN, 
					prefs.getString(PreferenceConstants.main_function)));
			btEndian1.setSelection(configuration.getAttribute(Constants.ATTR_ENDIANESS, 
					prefs.getString(PreferenceConstants.endianess)).equals("machine_default"));
			btEndian2.setSelection(configuration.getAttribute(Constants.ATTR_ENDIANESS, 
					prefs.getString(PreferenceConstants.endianess)).equals("little_endian"));
			btEndian3.setSelection(configuration.getAttribute(Constants.ATTR_ENDIANESS, 
					prefs.getString(PreferenceConstants.endianess)).equals("big_endian"));
			
			btWordWidth1.setSelection(configuration.getAttribute(Constants.ATTR_WORD_WIDTH, 
					prefs.getString(PreferenceConstants.word_width)).equals("machine_default"));
			btWordWidth2.setSelection(configuration.getAttribute(Constants.ATTR_WORD_WIDTH, 
					prefs.getString(PreferenceConstants.word_width)).equals("LP64"));
			btWordWidth3.setSelection(configuration.getAttribute(Constants.ATTR_WORD_WIDTH, 
					prefs.getString(PreferenceConstants.word_width)).equals("ILP64"));
			btWordWidth4.setSelection(configuration.getAttribute(Constants.ATTR_WORD_WIDTH, 
					prefs.getString(PreferenceConstants.word_width)).equals("LLP64"));
			btWordWidth5.setSelection(configuration.getAttribute(Constants.ATTR_WORD_WIDTH, 
					prefs.getString(PreferenceConstants.word_width)).equals("ILP32"));
			btWordWidth6.setSelection(configuration.getAttribute(Constants.ATTR_WORD_WIDTH, 
					prefs.getString(PreferenceConstants.word_width)).equals("LP32"));
			
			btAssertions.setSelection(configuration.getAttribute(Constants.ATTR_ASSERTIONS_CHECK,
					prefs.getBoolean(PreferenceConstants.assertions_check))); 
			btArrayBounds.setSelection(configuration.getAttribute(Constants.ATTR_ARRAY_BOUNDS_CHECK,
					prefs.getBoolean(PreferenceConstants.array_bounds_check))); 
			btDivByZero.setSelection(configuration.getAttribute(Constants.ATTR_DIV_BY_ZERO_CHECK,
					prefs.getBoolean(PreferenceConstants.div_by_zero_check))); 
			btPointers.setSelection(configuration.getAttribute(Constants.ATTR_POINTERS_CHECK,
					prefs.getBoolean(PreferenceConstants.assertions_check))); 
			btOverflow.setSelection(configuration.getAttribute(Constants.ATTR_OVERFLOW_CHECK,
					prefs.getBoolean(PreferenceConstants.overflow_check))); 
			btNancheck.setSelection(configuration.getAttribute(Constants.ATTR_NANCHECK,
					prefs.getBoolean(PreferenceConstants.nan_check)));

			btRoundmode1.setSelection(configuration.getAttribute(Constants.ATTR_ROUND, 
					prefs.getString(PreferenceConstants.rounding_mode)).equals("machine_default"));
			btRoundmode2.setSelection(configuration.getAttribute(Constants.ATTR_ROUND, 
					prefs.getString(PreferenceConstants.rounding_mode)).equals("round_to_nearest"));
			btRoundmode3.setSelection(configuration.getAttribute(Constants.ATTR_ROUND, 
					prefs.getString(PreferenceConstants.rounding_mode)).equals("round_to_plus_inf"));
			btRoundmode4.setSelection(configuration.getAttribute(Constants.ATTR_ROUND, 
					prefs.getString(PreferenceConstants.rounding_mode)).equals("round_to_minus_inf"));
			btRoundmode5.setSelection(configuration.getAttribute(Constants.ATTR_ROUND, 
					prefs.getString(PreferenceConstants.rounding_mode)).equals("round_to_zero"));

			// cbmc
			//btValueSets.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_VALUE_SETS,
			//		prefs.getBoolean(PreferenceConstants.cbmc_value_sets))); 
			btSimplify.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_SIMPLIFY,
					prefs.getBoolean(PreferenceConstants.cbmc_simplify))); 
			//btKeepClaims.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_ALL_CLAIMS,
			//		prefs.getBoolean(PreferenceConstants.cbmc_all_claims))); 
			cmbUnwind.setText(String.valueOf(configuration.getAttribute(Constants.ATTR_CBMC_UNWIND,
					prefs.getInt(PreferenceConstants.cbmc_unwind))));
			btSlice.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_SLICE,
					prefs.getBoolean(PreferenceConstants.cbmc_slice)));
			btUnwindingAssertions.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_ASSERTIONS,
					prefs.getBoolean(PreferenceConstants.cbmc_assertions)));
			//btSubstitution.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_SUBSTITUTION,
			//		prefs.getBoolean(PreferenceConstants.cbmc_substitution)));
			
			btBeautify1.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_BEAUTIFY, 
					prefs.getString(PreferenceConstants.cbmc_beautify)).equals("none"));
			btBeautify2.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_BEAUTIFY, 
					prefs.getString(PreferenceConstants.cbmc_beautify)).equals("greedy"));
			btBeautify3.setSelection(configuration.getAttribute(Constants.ATTR_CBMC_BEAUTIFY, 
					prefs.getString(PreferenceConstants.cbmc_beautify)).equals("pbs"));
			
			cmbModule.setText(configuration.getAttribute(Constants.ATTR_HWCBMC_MODULE, 
					prefs.getString(PreferenceConstants.hwcbmc_module)));
			cmbBound.setText(String.valueOf(configuration.getAttribute(Constants.ATTR_HWCBMC_BOUND, 
					prefs.getInt(PreferenceConstants.hwcbmc_bound))));
			
			// satabs
			cmbAbstractor.setText(configuration.getAttribute(Constants.ATTR_SATABS_ABSTRACTOR,
					prefs.getString(PreferenceConstants.satabs_abstractor)));
			cmbModelchecker.setText(configuration.getAttribute(Constants.ATTR_SATABS_MODELCHECKER,
					prefs.getString(PreferenceConstants.satabs_modelchecker)));
			cmbRefiner.setText(configuration.getAttribute(Constants.ATTR_SATABS_REFINER,
					prefs.getString(PreferenceConstants.satabs_refiner)));
			cmbIterations.setText(String.valueOf(configuration.getAttribute(Constants.ATTR_SATABS_ITERATIONS,
					prefs.getInt(PreferenceConstants.satabs_iterations))));
			btDataRaces.setSelection(configuration.getAttribute(Constants.ATTR_SATABS_DATA_RACES,
					prefs.getBoolean(PreferenceConstants.satabs_data_races)));
			btLoopDetection.setSelection(configuration.getAttribute(Constants.ATTR_SATABS_LOOP_DETECTION,
					prefs.getBoolean(PreferenceConstants.satabs_loop_detection)));
			
//			if (this.useXmlBin) {
//				txtMkTarget.setBackground(txtMkTarget.getDisplay().getSystemColor(SWT.COLOR_WHITE));
//				txtXmlBin.setBackground(txtXmlBin.getDisplay().getSystemColor(SWT.COLOR_WHITE));
//			}
//			else
//				txtFilter.setBackground(txtFilter.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			txtMainFun.setBackground(txtMainFun.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			
			adaptToVerifier();
		} catch (CoreException e) {
			CProverPlugin.log(e);
		}
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {

		// common options
		configuration.setAttribute(Constants.ATTR_VERIFIER, cmbVerifier.getText());
	    configuration.setAttribute(Constants.ATTR_MAINFUN, txtMainFun.getText());
	    if (btEndian1.getSelection())
	    	configuration.setAttribute(Constants.ATTR_ENDIANESS, "machine_default");
	    if (btEndian2.getSelection())
	    	configuration.setAttribute(Constants.ATTR_ENDIANESS, "little_endian");
	    if (btEndian3.getSelection())
	    	configuration.setAttribute(Constants.ATTR_ENDIANESS, "big_endian");
		if (btWordWidth1.getSelection())
			configuration.setAttribute(Constants.ATTR_WORD_WIDTH, "machine_default");
		if (btWordWidth2.getSelection())
			configuration.setAttribute(Constants.ATTR_WORD_WIDTH, "LP64");
		if (btWordWidth3.getSelection())
			configuration.setAttribute(Constants.ATTR_WORD_WIDTH, "ILP64");
		if (btWordWidth4.getSelection())
			configuration.setAttribute(Constants.ATTR_WORD_WIDTH, "LLP64");
		if (btWordWidth5.getSelection())
			configuration.setAttribute(Constants.ATTR_WORD_WIDTH, "ILP32");
		if (btWordWidth6.getSelection())
			configuration.setAttribute(Constants.ATTR_WORD_WIDTH, "LP32");

		configuration.setAttribute(Constants.ATTR_ASSERTIONS_CHECK, btAssertions.getSelection());
		configuration.setAttribute(Constants.ATTR_ARRAY_BOUNDS_CHECK, btArrayBounds.getSelection());
		configuration.setAttribute(Constants.ATTR_DIV_BY_ZERO_CHECK, btDivByZero.getSelection());
		configuration.setAttribute(Constants.ATTR_POINTERS_CHECK, btPointers.getSelection());
		configuration.setAttribute(Constants.ATTR_OVERFLOW_CHECK, btOverflow.getSelection());
		configuration.setAttribute(Constants.ATTR_NANCHECK, btNancheck.getSelection());
		
		// cbmc
		//configuration.setAttribute(Constants.ATTR_CBMC_VALUE_SETS, btValueSets.getSelection());
		configuration.setAttribute(Constants.ATTR_CBMC_SIMPLIFY, btSimplify.getSelection());
		//configuration.setAttribute(Constants.ATTR_CBMC_ALL_CLAIMS, btKeepClaims.getSelection());
		configuration.setAttribute(Constants.ATTR_CBMC_UNWIND, Integer.parseInt(cmbUnwind.getText()));
		configuration.setAttribute(Constants.ATTR_CBMC_SLICE, btSlice.getSelection());
		//configuration.setAttribute(Constants.ATTR_CBMC_SUBSTITUTION, btSubstitution.getSelection());
		configuration.setAttribute(Constants.ATTR_CBMC_ASSERTIONS, btUnwindingAssertions.getSelection());

		if (btBeautify1.getSelection())
			configuration.setAttribute(Constants.ATTR_CBMC_BEAUTIFY, "none");
		if (btBeautify2.getSelection())
			configuration.setAttribute(Constants.ATTR_CBMC_BEAUTIFY, "greedy");
		if (btBeautify3.getSelection())
			configuration.setAttribute(Constants.ATTR_CBMC_BEAUTIFY, "pbs");
		
		if (btRoundmode1.getSelection())
			configuration.setAttribute(Constants.ATTR_ROUND, "machine_default");
		if (btRoundmode2.getSelection())
			configuration.setAttribute(Constants.ATTR_ROUND, "round_to_nearest");
		if (btRoundmode3.getSelection())
			configuration.setAttribute(Constants.ATTR_ROUND, "round_to_plus_inf");
		if (btRoundmode4.getSelection())
			configuration.setAttribute(Constants.ATTR_ROUND, "round_to_minus_inf");
		if (btRoundmode5.getSelection())
			configuration.setAttribute(Constants.ATTR_ROUND, "round_to_zero");
		configuration.setAttribute(Constants.ATTR_HWCBMC_MODULE, cmbModule.getText());
		configuration.setAttribute(Constants.ATTR_HWCBMC_BOUND, Integer.parseInt(cmbBound.getText()));

		// satabs
		configuration.setAttribute(Constants.ATTR_SATABS_MODELCHECKER, cmbModelchecker.getText());
		configuration.setAttribute(Constants.ATTR_SATABS_REFINER, cmbRefiner.getText());
		configuration.setAttribute(Constants.ATTR_SATABS_ABSTRACTOR, cmbAbstractor.getText());
		configuration.setAttribute(Constants.ATTR_SATABS_ITERATIONS, Integer.parseInt(cmbIterations.getText()));
		configuration.setAttribute(Constants.ATTR_SATABS_DATA_RACES, btDataRaces.getSelection());
		configuration.setAttribute(Constants.ATTR_SATABS_LOOP_DETECTION, btLoopDetection.getSelection());
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		IPreferenceStore prefs = CProverPlugin.getDefault().getPreferenceStore();

		// common options
		configuration.setAttribute(Constants.ATTR_VERIFIER, prefs.getString(PreferenceConstants.verifier));
	    configuration.setAttribute(Constants.ATTR_MAINFUN, prefs.getString(PreferenceConstants.main_function));
	    configuration.setAttribute(Constants.ATTR_ENDIANESS, prefs.getString(PreferenceConstants.endianess));
	    configuration.setAttribute(Constants.ATTR_WORD_WIDTH, prefs.getString(PreferenceConstants.word_width));
		configuration.setAttribute(Constants.ATTR_ASSERTIONS_CHECK, prefs
				.getBoolean(PreferenceConstants.assertions_check));
		configuration.setAttribute(Constants.ATTR_ARRAY_BOUNDS_CHECK, prefs
				.getBoolean(PreferenceConstants.array_bounds_check));
		configuration.setAttribute(Constants.ATTR_DIV_BY_ZERO_CHECK, prefs
				.getBoolean(PreferenceConstants.div_by_zero_check));
		configuration.setAttribute(Constants.ATTR_POINTERS_CHECK, prefs.getBoolean(PreferenceConstants.pointers_check));
		configuration.setAttribute(Constants.ATTR_OVERFLOW_CHECK, prefs.getBoolean(PreferenceConstants.overflow_check));
	    configuration.setAttribute(Constants.ATTR_ROUND, prefs.getString(PreferenceConstants.rounding_mode));
	    
		// cbmc
		//configuration.setAttribute(Constants.ATTR_CBMC_VALUE_SETS, prefs
		//		.getBoolean(PreferenceConstants.cbmc_value_sets));
		configuration.setAttribute(Constants.ATTR_CBMC_SIMPLIFY, prefs.getBoolean(PreferenceConstants.cbmc_simplify));
		//configuration.setAttribute(Constants.ATTR_CBMC_ALL_CLAIMS, prefs
		//		.getBoolean(PreferenceConstants.cbmc_all_claims));
		configuration.setAttribute(Constants.ATTR_CBMC_UNWIND, prefs.getInt(PreferenceConstants.cbmc_unwind));
		configuration.setAttribute(Constants.ATTR_CBMC_SLICE, prefs.getBoolean(PreferenceConstants.cbmc_slice));
		//configuration.setAttribute(Constants.ATTR_CBMC_SUBSTITUTION, prefs
		//		.getBoolean(PreferenceConstants.cbmc_substitution));
		configuration.setAttribute(Constants.ATTR_CBMC_ASSERTIONS, prefs
				.getBoolean(PreferenceConstants.cbmc_assertions));
		configuration.setAttribute(Constants.ATTR_CBMC_BEAUTIFY, prefs.getString(PreferenceConstants.cbmc_beautify));
		configuration.setAttribute(Constants.ATTR_HWCBMC_MODULE, prefs.getString(PreferenceConstants.hwcbmc_module));
		configuration.setAttribute(Constants.ATTR_HWCBMC_BOUND, prefs.getInt(PreferenceConstants.hwcbmc_bound));

		// satabs
		configuration.setAttribute(Constants.ATTR_SATABS_MODELCHECKER, prefs
				.getString(PreferenceConstants.satabs_modelchecker));
		configuration.setAttribute(Constants.ATTR_SATABS_REFINER, prefs.getString(PreferenceConstants.satabs_refiner));
		configuration.setAttribute(Constants.ATTR_SATABS_ABSTRACTOR, prefs
				.getString(PreferenceConstants.satabs_abstractor));
		configuration.setAttribute(Constants.ATTR_SATABS_ITERATIONS, prefs
				.getInt(PreferenceConstants.satabs_iterations));
		configuration.setAttribute(Constants.ATTR_SATABS_DATA_RACES, prefs
				.getBoolean(PreferenceConstants.satabs_data_races));
		configuration.setAttribute(Constants.ATTR_SATABS_LOOP_DETECTION, prefs
				.getBoolean(PreferenceConstants.satabs_loop_detection));
		
		configuration.setAttribute(DebugPlugin.ATTR_PROCESS_FACTORY_ID, "org.cprover.launch.CProverProcessFactory");
	}

	private void adaptToModelChecker() {
		btLoopDetection.setEnabled(cmbModelchecker.getText().equals("boppo"));
	}

}
