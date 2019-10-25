package org.cprover.preferences;

import org.cprover.core.CProverPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Class: HwCbmcPreferencePage
 *
 * Purpose: Take preferences from user about using HW-CBMC/EBMC tool
 */

public class HwCbmcPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public HwCbmcPreferencePage() {
		super(GRID);
		setPreferenceStore(CProverPlugin.getDefault().getPreferenceStore());
		setDescription("Default preferences for launching HW-CBMC/EBMC:");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.hwcbmc_module, "Module to unwind", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceConstants.hwcbmc_bound, "Number of transitions", 
				new String[][]{{"1", "1"}, {"5", "5"}, {"10", "10"}, {"20", "20"}, {"50", "50"}, {"100", "100"}}, getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}