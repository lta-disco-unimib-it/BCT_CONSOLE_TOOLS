package it.unimib.disco.lta.bct.bctjavaeclipse.ui.preferences;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

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
 * 
 * @author Valerio Terragni
 * 
 */

public class BCTPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public BCTPreferencePage() {
		super(GRID);
		setPreferenceStore(BctJavaEclipsePlugin.getDefault().getPreferenceStore());
		setDescription("BCT preference");
	
	}
	
	
	public void createFieldEditors() {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}