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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class CRegressionWizardPage extends CompositeWizardPage<CRegressionConfigurationComposite, CRegressionConfiguration> {

	private CRegressionConfiguration toLoad;

	public CRegressionWizardPage(ISelection selection) {
		super(selection, "Monitoring Options", "This wizard cconfigure the options required to identify regressions in C/C++ projects.");
	}

	@Override
	public CRegressionConfiguration getConfiguration() {
		return regressionConfigurationComposite.getConfiguration();

	}

	@Override
	protected CRegressionConfigurationComposite createMainComposite(Composite parent) {
		CRegressionConfigurationComposite composite = new CRegressionConfigurationComposite(parent,SWT.NONE);
		composite.load(toLoad, null);
		return composite;
	}

	public void load(CRegressionConfiguration c) {
		if ( regressionConfigurationComposite == null ){
			toLoad = c;
			return;
		}
		regressionConfigurationComposite.load(c, null);
	}


	
	
}
