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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.preferences;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines.KBehaviorOptionsComposite;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

public class KBehaviorPreferencePage
extends PreferencePage
implements IWorkbenchPreferencePage {

	private KBehaviorOptionsComposite kBehaviorComp;
	public KBehaviorPreferencePage() {

		setPreferenceStore(BctJavaEclipsePlugin.getDefault().getPreferenceStore());
		setDescription("Kbehaviorpage page");

	}



	public void init(IWorkbench workbench) {
	}


	protected Control createContents(Composite parent) {
		kBehaviorComp = new KBehaviorOptionsComposite(parent,SWT.None);
		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();

			if (workspaceOptions.getKbehaviorInferenceEngineOptions().containsValue("")){ //if KtailEnigineOptions are empty load default options


				BctDefaultOptions defaultOptions = DefaultOptionsManager.getDefaultOptions();
				kBehaviorComp.load(defaultOptions.getKbehaviorInferenceEngineOptions());




			}else{


				kBehaviorComp.load(workspaceOptions.getKbehaviorInferenceEngineOptions());



			}


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
			kBehaviorComp.load(defaultOptions.getKbehaviorInferenceEngineOptions());

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}

	public boolean performOk() {
		Properties p = kBehaviorComp.getUserDefinedOptions();

		WorkspaceOptionsManager.setKbehaviorInferenceEngineOptions(p);

		return true;

	}

}