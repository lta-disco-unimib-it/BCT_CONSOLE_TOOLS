package org.cprover.preferences;

import org.cprover.core.CProverPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Class: CbmcPreferencePage
 *
 * Purpose: Take preferences from user for using CBMC tool
 */

public class CbmcPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CbmcPreferencePage() {
		super(GRID);
		setPreferenceStore(CProverPlugin.getDefault().getPreferenceStore());
		setDescription("Default preferences for launching CBMC:");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		//addField(new BooleanFieldEditor(PreferenceConstants.cbmc_value_sets, "Show value-sets", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.cbmc_simplify, "Simplify", getFieldEditorParent()));
		//addField(new BooleanFieldEditor(PreferenceConstants.cbmc_all_claims, "Keep all claims", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.cbmc_unwind, "Number of unwindings", 
				new String[][]{{"0", "0"}, {"1", "1"}, {"5", "5"}, {"10", "10"}, {"20", "20"}, {"50", "50"}, {"100", "100"}}, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.cbmc_slice, "Do slicing", getFieldEditorParent()));
		//addField(new BooleanFieldEditor(PreferenceConstants.cbmc_substitution, "Perform substitution", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.cbmc_assertions, "Generate unwinding assertions", getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(PreferenceConstants.cbmc_beautify, "Beautify the counterexample", 1, 
				new String[][]{{"None", "none"},{"Greedy", "greedy"},{"PBS", "pbs"}}, getFieldEditorParent(), true));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}