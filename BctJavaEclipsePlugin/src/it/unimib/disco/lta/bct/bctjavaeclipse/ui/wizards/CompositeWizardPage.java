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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (bctc).
 */

public abstract class CompositeWizardPage<T, C> extends WizardPage implements BCTObserver {

	private ISelection selection;

	private ComponentsConfiguration ccToLoad;

	private boolean initialized;

	protected T regressionConfigurationComposite;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public CompositeWizardPage(ISelection selection,String title, String description) {
		super("wizardPage");

		setTitle(title);
		setDescription(description);
		this.selection = selection;

	}


	public abstract C getConfiguration();

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;

		regressionConfigurationComposite = createMainComposite(container); 





		initialize();
		dialogChanged();
		setControl(container);
	}

	protected abstract T createMainComposite(Composite container);


	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if ( ccToLoad != null ){
			internalLoad(ccToLoad);
			ccToLoad = null;
		}

	}


	/**
	 * Ensures that both text fields are set.
	 */

	protected void dialogChanged() {

		StringBuffer messageB = new StringBuffer();

		if ( messageB.length() == 0){
			updateStatus(null);
		} else {
			updateStatus(messageB.toString());
		}
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void componentConfigurationChanged() {
		dialogChanged();
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		componentConfigurationChanged();
	}

	public void load(ComponentsConfiguration cc) {
		if ( ! initialized ){
			ccToLoad = cc;
		} else {
			internalLoad(cc);
		}

	}

	private void internalLoad(ComponentsConfiguration cc) {

	}
	

}