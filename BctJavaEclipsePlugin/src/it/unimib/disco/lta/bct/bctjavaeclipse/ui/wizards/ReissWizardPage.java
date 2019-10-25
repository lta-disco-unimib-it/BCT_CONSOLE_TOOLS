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
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.inferencesEngines.FsaEngineOptionsComposite;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
/**
 * This class represents the Reiss wizard page
 * 
 * @author Terragni Valerio
 *
 */
public class ReissWizardPage extends InferenceEngineWizardPage{
	public static final String PAGE_NAME = "Reiss";
	private FsaEngineOptionsComposite reissComp;



	public ReissWizardPage() {
		super("Reiss");
		setTitle("Reiss option");
		setDescription("Insert Reiss option");

	}


	public void createControl(Composite parent) {

		reissComp = new FsaEngineOptionsComposite(parent,SWT.None); //create composite
		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();

			reissComp.load(workspaceOptions.getReissEnigineOptions());
			//load workspace options

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		setControl(reissComp);

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
		return reissComp.getUserDefinedOptions();

	}


	@Override
	/**
	 * load properties
	 */
	public void load(Properties options) {
		if ( reissComp != null )
			reissComp.load(options);
	}



}
