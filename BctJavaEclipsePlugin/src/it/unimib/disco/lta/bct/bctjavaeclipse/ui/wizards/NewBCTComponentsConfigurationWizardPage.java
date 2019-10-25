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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (bctc).
 */

public class NewBCTComponentsConfigurationWizardPage extends WizardPage implements BCTObserver {
	private Text containerText;

	private Text fileText;

	private ISelection selection;

	private ComponentsConfigurationComposite componentConfigurationComposite;

	

	public void setContainer( IFolder folder ){
		containerText.setText(folder.getFullPath().toString());
	}
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewBCTComponentsConfigurationWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("BCT Components Definition");
		setDescription("This wizard creates a new file with *.bctc extension.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		componentConfigurationComposite = new ComponentsConfigurationComposite(container,SWT.NONE);
		
		componentConfigurationComposite.addBCTObserver(this);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		componentConfigurationComposite.setLayoutData(gd);

		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.setEditable(true);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		

		fileText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
			
		});
		
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		
		
		
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		
		//FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		SelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), true, 
				"Select new file container");

		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
		
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		
		
		
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		
		
		
		if ( container != null )
			System.out.println(container.getName());
		else 
			System.out.println("Container null");
		//String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getFileName() {
		return fileText.getText();
	}
	
	public ComponentsConfigurationComposite getComponentConfigurationComposite(){
		return componentConfigurationComposite;
	}

	public String getContainerName() {
		return containerText.getText();
	}



	private void componentConfigurationChanged() {
		
		String configName = componentConfigurationComposite.getConfigurationName();
		if ( configName != null  ){
			fileText.setText(configName+".bctcc");
		}
		
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		componentConfigurationChanged();

	}
}