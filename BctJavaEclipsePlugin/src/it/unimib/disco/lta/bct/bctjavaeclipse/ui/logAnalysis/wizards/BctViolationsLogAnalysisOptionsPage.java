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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
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

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class BctViolationsLogAnalysisOptionsPage extends WizardPage {
	private Text containerText;

	private Text fileText;

	private ISelection selection;

	private ArrayList<IFile> filesToOpen = new ArrayList<IFile>();

	private Button copyLogs;

	private IFile startContainer;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public BctViolationsLogAnalysisOptionsPage() {
		super("wizardPage");
		setTitle("Violations Log Analysis");
		setDescription("This wizard creates a Violation Log Analysis configuration and stores it in a .bctla file.");
		
		
	}

	public void setFilesToOpen(Collection<IFile> files){
		filesToOpen = new ArrayList<IFile>();
		filesToOpen.addAll(files);
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
		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		if ( startContainer != null ){
			containerText.setText(startContainer.getFullPath().toOSString());
		}

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
		gd.horizontalSpan=2;
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		label = new Label(container, SWT.NULL);
		label.setText("&Copy logs:");
		
		copyLogs = new Button(container, SWT.CHECK);
		copyLogs.setSelection(true);
		
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		
		fileText.setText("BctViolationsLogAnalisys-"+System.currentTimeMillis()+".bctla");
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
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
		String fileName = getFileName();

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
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("bctla") == false) {
				updateStatus("File extension must be \"bctla\"");
				return;
			}
		}
		updateStatus(null);
	}

	private String getFileName() {
		return fileText.getText();
	}

	private String getContainerName() {
		return containerText.getText();
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}


	
	public IFile getBCTAnomalyGraphsFolder(){
		String containerParentPath = containerText.getText();
		String containerPath = containerParentPath+"/"+getLogAnalysisFileName();
		
		IFile folderFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(containerPath));
		return folderFile;
	}

	private String getLogAnalysisFileName() {
		String fullname = fileText.getText();
		int idx = fullname.lastIndexOf('.');
		return fullname.substring(0,idx);
	}
	
	private String getLogAnalysisFullFileName() {
		return fileText.getText();
	}

	public IFile getBCTViolationsLogAnalysisFile() {
		IPath path = new Path(containerText.getText()+"/"+getLogAnalysisFullFileName());
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
	}

	public List<IFile> getLogFiles() {
		return filesToOpen;
	}
	
	public boolean copyLogs(){
		return copyLogs.getSelection();
	}

	public void setContainer(IFile fileContainer) {
		this.startContainer = fileContainer;
		if ( containerText != null ){
			containerText.setText(fileContainer.getFullPath().toOSString());
		}
	}

	public void setFileToOpen(IFile fileToOpen) {
		filesToOpen.clear();
		filesToOpen.add(fileToOpen);
	}
}