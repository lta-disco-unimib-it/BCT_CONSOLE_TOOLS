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
 * This class represents the KInclusion wizard page
 *
 * @author Terragni Valerio
 *
 */
public class KInclusionWizardPage extends InferenceEngineWizardPage{
	public static final String PAGE_NAME = "KInclusion";
	public FsaEngineOptionsComposite kInclusionComp;



	public KInclusionWizardPage() {
		super("KInclusion");
		setTitle("KInclusion option");
		setDescription("Insert KInclusion option");

	}


	public void createControl(Composite parent) {

		kInclusionComp = new FsaEngineOptionsComposite(parent,SWT.None);
		BctDefaultOptions workspaceOptions;
		try {
			workspaceOptions = WorkspaceOptionsManager.getWorkspaceOptions();

			kInclusionComp.load(workspaceOptions.getKinclusionEnigineOptions());
			//load options

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		setControl(kInclusionComp);

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
		return kInclusionComp.getUserDefinedOptions();

	}


	@Override
	/**
	 * load properties
	 */
	public void load(Properties options) {
		if ( kInclusionComp != null )
			kInclusionComp.load(options);
	}



}
