package org.cprover.preferences;

import org.cprover.core.CProverPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Class: CProverMainPreferencePage
 *
 * Purpose: Take preferences from user for launching CProver plugin
 */

public class CProverMainPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public CProverMainPreferencePage() {
		super(GRID);
		setPreferenceStore(CProverPlugin.getDefault().getPreferenceStore());
		setDescription("Default preferences for launching CProver:");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new ComboFieldEditor(PreferenceConstants.verifier, "Verifier", 
				new String[][]{{"satabs", "satabs"},{"cbmc", "cbmc"},{"hw-cbmc", "hw-cbmc"},{"ebmc", "ebmc"},{"vcegar", "vcegar"}}, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.max_threads, "Maximum number of verification threads", 
				new String[][]{{"1", "1"},{"2", "2"},{"3", "3"},{"4", "4"},{"5", "5"},{"6", "6"},{"7", "7"},{"8", "8"},{"9", "9"},{"10", "10"}}, getFieldEditorParent()));
		addField(new EnvListEditor(PreferenceConstants.environment, "Environment (key=value pairs)", getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(PreferenceConstants.word_width, "Word width", 3, 
				new String[][]{{"Machine default", "machine_default"},{"LP64", "_LP64"},{"ILP64", "_ILP64"},{"LLP64", "_LLP64"},{"ILP32", "_ILP32"},{"LP32", "_LP32"}}, getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(PreferenceConstants.endianess, "Endianess", 1, 
				new String[][]{{"Machine default", "machine_default"}, {"Little-endian", "little_endian"},{"Big-endian", "big_endian"}}, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.assertions_check, "Check assertions", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.array_bounds_check, "Check array bounds", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.div_by_zero_check, "Check division by zero", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.pointers_check, "Check pointers", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.overflow_check, "Check arithmetic overflow", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.nan_check, "Nan check", getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(PreferenceConstants.rounding_mode, "Floating point rounding mode", 3, 
				new String[][]{{"Machine default", "machine_nearest"},{"to nearest", "Round_to_nearest"},{"to plus inf", "round_to_plus_inf"},{"to minus inf", "round_to_minus_inf"},{"to zero", "round_to_zero"}}, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.main_function, "Main function", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}