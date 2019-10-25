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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.invariantGenerator.InvariantGeneratorOptionsComposite;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
/**
 * This class represents the wizard's page, content invariant
 * generator options
 * 
 *
 * @author Terragni Valerio
 *
 */
public class ConfigurationWizardPage extends WizardPage{

	private Properties invariantGeneratorOptions;
	public InvariantGeneratorOptionsComposite invGenComp;
	public static final String PAGE_NAME = "Configurationpage";

	public ConfigurationWizardPage(Properties p) {
		super("Invariant generator option");
		setTitle("Invariant generator option");
		setDescription("Insert Invariant generator option");

		invariantGeneratorOptions = p;
	}

	public ConfigurationWizardPage() {
		this(null);
	}

	public void createControl(Composite parent) {

		invGenComp = new InvariantGeneratorOptionsComposite(parent,SWT.None);  // composite

		initialize(); 

		setControl(invGenComp);

	}
	private void initialize() {


		try {

			if (invariantGeneratorOptions == null ){ //if  are empty load default options
				BctDefaultOptions defaultOptions = DefaultOptionsManager.getDefaultOptions();
				invGenComp.load(defaultOptions.getInvariantGeneratorOptions());
			}else{
				invGenComp.load(invariantGeneratorOptions);
			}


		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}
/**
 * load @param Properties invariantGeneratorOptions in configuration page
 * 
 * @param invariantGeneratorOptions
 */
	public void load ( Properties invariantGeneratorOptions ){
		System.out.println("LOADING OPTIONS "+invariantGeneratorOptions);
		this.invariantGeneratorOptions = invariantGeneratorOptions;
		if ( invGenComp != null ){
			invGenComp.load(invariantGeneratorOptions);
		}
	}
/**
 * return the options defined by user
 * 
 * @return
 */
	public Properties getInvariantGeneratorOptions(){
		return invGenComp.getUserDefinedOptions();
	}
	
	public boolean getKeepExistingModels(){
		return invGenComp.getKeepExistingModels();
	}

	public boolean canFlipToNextPage(){
		return true;
	}

	public boolean canFinish(){
		return false;
	}

	public boolean getSkipFailingActions() {
		return invGenComp.getSkipFailingActions();
	}

	public boolean getSkipFailingTests() {
		return invGenComp.getSkipFailingTests();
	}
	
	public boolean getSkipFailingProcesses() {
		return invGenComp.getSkipFailingProcesses();
	}
}