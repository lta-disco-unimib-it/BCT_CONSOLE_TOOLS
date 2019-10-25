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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.ResourcesPage;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
 * OR with the extension that matches the expected one (.bctmr).
 */

public class BCTMonitoringConfigurationPage extends WizardPage implements MouseListener, KeyListener, BCTObservable, BCTObserver {
	private Text containerText;

	//private Text fileText;

	private ISelection selection;

	private ResourcesPage mrc;

	private Text dataDirFileText;

	private BCTObservaleIncapsulated observer;

	private Path destFolder;

	private String oldDataDirFile;

	private boolean ready = false;

	private String referred;

	private boolean automaticallyUpdateDataFolders;

	private boolean onlyJavaProjectsAllowed = true;

	
	//private MonitoringResourceComposite mrc;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public BCTMonitoringConfigurationPage(ISelection selection) {
		super("BCTMonitoringResourcePage");
		setTitle("BCT Monitoring Resource");
		setDescription("This wizard creates a new file with *..bctmr extension.");
		this.selection = selection;
		this.observer = new BCTObservaleIncapsulated(this);
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
		
		//mrc = new MonitoringResourceComposite(container,SWT.NONE);
		
		
		
		
		//componentConfigurationComposite.addObserver(this);
		//MonitoringConfigurationComposite mrc = new MonitoringConfigurationComposite(container,SWT.V_SCROLL);
		//ObjectFlattenerPage mrc = new ObjectFlattenerPage(container, new FlattenerOptions("","","",new ArrayList<String>()));
		mrc = new ResourcesPage(container);
		mrc.addBCTObserver(this);
		mrc.setOnlyJavaProjectsAllowed(onlyJavaProjectsAllowed);
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		mrc.setLayoutData(gd);
		mrc.addMouseListener(this);
		mrc.addKeyListener(this);
		
		
		
		
		//set the default filesystem dir to store bct data
		
		
			
			
			//Calendar cal = new GregorianCalendar();
			
			//String destDataDir = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+
			//				cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.HOUR_OF_DAY)+
			//				cal.get(Calendar.MINUTE)+"-"+cal.get(Calendar.SECOND);

		setMRCDefaultDataDirName(mrc.getResourceName());



		mrc.pack();
		
		
		
		Label dataDirFileLabel=new Label(container, SWT.NULL);
        dataDirFileLabel.setText("Save to: ");
        //dataDirFileLabel.setBounds(15, 50, 75, 20);
		dataDirFileText = new Text(container, SWT.SINGLE | SWT.BORDER |SWT.FILL);
		dataDirFileText.setEditable(false);
		
		
		GridData ld = new GridData(GridData.FILL_HORIZONTAL);
		dataDirFileText.setLayoutData(ld);
		dataDirFileText.setToolTipText("Folder to save the Monitoring Configuration to.");
		
		//dataDirFileText.setBounds(95, 50, 250, 20);

		Button button = new Button(container, SWT.NONE);
		button.setText("Browse...");
		//button.setBounds(355, 50, 100, 20);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		
		dialogChanged();
		setControl(container);
		
		container.pack();
		
		if (referred != null){
			mrc.setReferredProjectName(referred);
		}
		
		this.mrc.setAutomaticallyUpdateDataFolders(automaticallyUpdateDataFolders);
		
		ready  = true;
	}

	
	
	
	
	public boolean areOnlyJavaProjectsAllowed() {
		return mrc.areOnlyJavaProjectsAllowed();
	}

	public void setOnlyJavaProjectsAllowed(boolean onlyJavaProjectsAllowed) {
		this.onlyJavaProjectsAllowed = onlyJavaProjectsAllowed;
		if ( mrc != null ){
			mrc.setOnlyJavaProjectsAllowed(onlyJavaProjectsAllowed);
		}
	}

	private void handleBrowse() {
		
		//FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		SelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), true, 
				"Select new file container");

		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				destFolder=((Path) result[0]);
			}
			updateDestFile();
			dialogChanged();
		}
		
	}
	
	
	
	
	

	private String computeDestFile() {
		if ( destFolder == null ){
			return "";
		}
		
		return destFolder.toString()+File.separator+mrc.getResourceName()+"."+DefaultOptionsManager.getMonitoringConfigurationFileExtension();
	}
	
	private void updateDestFile() {
		dataDirFileText.setText(computeDestFile());
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		String status="";
		
		if ( automaticallyUpdateDataFolders ){
//			this.dataDirFileText.setText();
			
			String referredProject = mrc.getReferredProjectName();
			if ( referredProject != null && referredProject.length() > 0 ) {
				destFolder=new Path( referredProject );
				updateDestFile();
			}
		}
		
		if ( mrc.getResourceName() == null || mrc.getResourceName().length() == 0){
			status += "A configuration name must be specified\n";
		} else {
			//FIXME: check other configurations with the same name?
		}
		
		if (oldDataDirFile == null ||  mrc.getDataDirFile().equals(oldDataDirFile) ){
			setMRCDefaultDataDirName(mrc.getResourceName());
			oldDataDirFile = mrc.getDataDirFile();
		}
		
		
		if ( mrc.fileButtonSelected() ){
			if ( mrc.getDataDirFile().length() == 0 ){
				status += "Select a folder to store BCT data in\n";
			}
		} else if ( mrc.dbButtonSelected() ){
			
			if ( mrc.getUser().length() == 0 ){
				status += "DB user must be not null.\n";
			}
			if ( mrc.getUriText().length() == 0 ){
				status += "DB uri must be not null.\n";
			}
			
		} else {
			status = "A resource type must be selected\n";
		}
		
		if ( destFolder == null ){
			status += "A destination folder must be selected\n";
		} else {
			 IFile dest = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(dataDirFileText.getText()));
			 if ( dest.exists() ){
				 status += "A monitoring configuration resource named "+mrc.getResourceName()+" already exists\n";
			 }
		}
		
		if ( status.length() == 0 ){
			status = null;
		}
		
		
		
		updateStatus(status);
	}

	private void setMRCDefaultDataDirName(String destDataDir) {
		try{
			
			
			if ( DefaultOptionsManager.use_BCT_DATA_projectAsDefaultDataDir() ){
				IProject p = DefaultOptionsManager.getBctDataProject();
				String dest = p.getFullPath() + "/" + destDataDir;
				mrc.setDataDir(dest);
			} else{
				String referredProject = mrc.getReferredProjectName();
				String dest = referredProject +  "/BCT_DATA"+"/" + destDataDir;
				mrc.setDataDir(dest);
			}
			
			//System.out.println("data dir set to "+dest);
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Error during BCT project initialization", "Cannot create the default BCT_DATA project: "+e.getMessage());
		}
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public IPath getDestination() {
		return new Path(dataDirFileText.getText());
	}

	public void mouseDoubleClick(MouseEvent e) {
		dialogChanged();
	}

	public void mouseDown(MouseEvent e) {
		dialogChanged();
	}

	public void mouseUp(MouseEvent e) {
		dialogChanged();
	}

	public void keyPressed(KeyEvent e) {
		dialogChanged();
	}

	public void keyReleased(KeyEvent e) {
		dialogChanged();
	}

	public void addBCTObserver(BCTObserver l) {
		observer.addBCTObserver(l);
	}

	public String getResourceName() {
		return mrc.getResourceName();
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		updateDestFile();
		dialogChanged();
		observer.notifyBCTObservers(message);
	}

	public StorageConfiguration createStorageConfiguration() {
		return mrc.createStorageConfiguration();
	}

	public boolean dbButtonSelected() {
		return mrc.dbButtonSelected();
	}

	public boolean fileButtonSelected() {
		return mrc.fileButtonSelected();
	}

	public String getReferredProjectName() {
		return mrc.getReferredProjectName();
	}

	public void setReferredProject(IProject jproject) {
		if ( jproject != null ){
			IPath path = jproject.getFullPath();
			
			referred = path.toString();
		} else {
			return;
		}
		
		if ( ready ){
			mrc.setReferredProjectName(referred);
		}
	}

	public void setAutomaticallyUpdateDataFolders(boolean b) {
		automaticallyUpdateDataFolders=b;
		
		if ( ready ){
			this.mrc.setAutomaticallyUpdateDataFolders(automaticallyUpdateDataFolders);
		}
	}
	
	
}