package org.cprover.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.cprover.core.CProverPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

/**
 * Class: ToolsPreferencePage
 *
 * Purpose: Take preferences from user for using external tools
 */

public class ToolsPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public ToolsPreferencePage() {
		super(GRID);
		setPreferenceStore(CProverPlugin.getDefault().getPreferenceStore());
//		setDescription("External Verifiers");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
//		addField(
//				new BooleanFieldEditor(
//					PreferenceConstants.P_BOOLEAN,
//					"&Lookup tools in standard path",
//					getFieldEditorParent()));
		
		addField(new FileFieldEditor(PreferenceConstants.EXEC_SATABS,
				"&SATABS:", getFieldEditorParent()));

		addField(new FileFieldEditor(PreferenceConstants.EXEC_CBMC,
				"&CBMC:", getFieldEditorParent()));
		
		addField(new FileFieldEditor(PreferenceConstants.EXEC_HWCBMC,
				"&HW-CBMC:", getFieldEditorParent()));
		
		addField(new FileFieldEditor(PreferenceConstants.EXEC_EBMC,
				"&EBMC:", getFieldEditorParent()));
		
		addField(new FileFieldEditor(PreferenceConstants.EXEC_VCEGAR,
				"&VCEGAR:", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}