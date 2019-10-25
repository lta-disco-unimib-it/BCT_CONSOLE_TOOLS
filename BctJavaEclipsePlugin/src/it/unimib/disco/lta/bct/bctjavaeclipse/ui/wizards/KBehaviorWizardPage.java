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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines.KBehaviorOptionsComposite;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
/**
 * This class represents the KBehavior Wizard Page
 * 
 * @author Terragni Valerio
 *
 */
public class KBehaviorWizardPage extends InferenceEngineWizardPage{
	public static final String PAGE_NAME = "KBehavior";
	public KBehaviorOptionsComposite infeEngineComp;



	public KBehaviorWizardPage() {
		super("KBehavior");
		setTitle("KBehavior option");
		setDescription("Insert KBehavior option");

	}


	public void createControl(Composite parent) {

		infeEngineComp = new KBehaviorOptionsComposite(parent,SWT.None);
		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();
			infeEngineComp.load(workspaceOptions.getKbehaviorInferenceEngineOptions());

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPageComplete(true);
		canFlipToNextPage();
		setControl(infeEngineComp);

	}

	public boolean canFlipToNextPage(){

		return false;
	}

	private void initialize() {



		setPageComplete(true);
	}


	@Override
	/**
	 * return the options defined by user
	 * 
	 * @return
	 */
	public Properties getUserDefinedOptions() {
		return infeEngineComp.getUserDefinedOptions();
	}


	@Override
	/**
	 * load properties
	 */
	public void load(Properties options) {
		if ( infeEngineComp != null )
			infeEngineComp.load(options);
	}



}
