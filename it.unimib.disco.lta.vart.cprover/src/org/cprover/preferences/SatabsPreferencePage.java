package org.cprover.preferences;

import org.cprover.core.CProverPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Class: SatabsPreferencePage
 *
 * Purpose: Take preferences from user for using Satabs tool
 */

public class SatabsPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public SatabsPreferencePage() {
		super(GRID);
		setPreferenceStore(CProverPlugin.getDefault().getPreferenceStore());
		setDescription("Default preferences for launching SATABS:");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new ComboFieldEditor(PreferenceConstants.satabs_modelchecker, "Modelchecker", 
				new String[][]{{"default", "default"},{"nusmv", "nusmv"},{"cadence-smv", "cadence-smv"},{"cmu-smv", "cmu-smv"},{"satmc", "satmc"},{"spin", "spin"},{"boppo", "boppo"}}, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.satabs_abstractor, "Abstractor", 
				new String[][]{{"default", "default"},{"none", "none"},{"wp", "wp"},{"prover", "prover"},{"satqe", "satqe"}}, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.satabs_refiner, "Refiner", 
				new String[][]{{"default", "default"},{"wp", "wp"},{"ipp", "ipp"}}, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.satabs_iterations, "Maximum number of refinement iterations", 
				new String[][]{{"0", "0"}, {"1", "1"}, {"5", "5"}, {"10", "10"}, {"20", "20"}, {"50", "50"}, {"100", "100"}}, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.satabs_data_races, "Detect data races", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.satabs_loop_detection, "Perform loop detection", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}