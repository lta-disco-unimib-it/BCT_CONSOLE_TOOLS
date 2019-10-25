
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.preferences;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.invariantGenerator.InvariantGeneratorOptionsComposite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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
 */
public class InvariantGeneratorPreferencePage

extends PreferencePage

implements IWorkbenchPreferencePage {


	public List daikonConfigList;
	public  Combo fsaEngineCombo;
	public Text daikonPathText;
	private InvariantGeneratorOptionsComposite invGenComp;

	public InvariantGeneratorPreferencePage() {
		setPreferenceStore(BctJavaEclipsePlugin.getDefault().getPreferenceStore());
		setDescription("Invariant generator page");

	}


	public void init(IWorkbench workbench) {



	}

	@Override
	protected Control createContents(Composite parent) {


		invGenComp = new InvariantGeneratorOptionsComposite(parent,SWT.None);


		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();
			invGenComp.load(workspaceOptions.getInvariantGeneratorOptions());

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





		return parent; 

	}



	protected void performDefaults() {  //when restore default button select


		try {
			BctDefaultOptions defaultOptions = DefaultOptionsManager.getDefaultOptions();
			invGenComp.load(defaultOptions.getInvariantGeneratorOptions());

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}


	public boolean performOk() {
		if (invGenComp.daikonConfigList.getSelectionCount()==0){
			Shell shell = new Shell();
			MessageBox messageBoxError = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			messageBoxError.setText("Error");
			messageBoxError.setMessage("Please select a daikon config");
			messageBoxError.open();
			return false;
		}else{

			WorkspaceOptionsManager.setInvariantGeneratorOptions(invGenComp.getUserDefinedOptions());

			return true;
		}

	}
}