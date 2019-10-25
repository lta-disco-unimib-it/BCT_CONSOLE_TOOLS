package it.unimib.disco.lta.bct.bctjavaeclipse.ui.fsaExportImportWizards;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * this is the page for import a Ser,JFLap files into a Fsa file;
 * 
 *@author Terragni Valerio
 */
public class FsaBCTImportWizardPage extends WizardPage {
	private CheckboxTreeViewer tv;
	private TreeViewer dtv;

	public FsaBCTImportWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		setTitle(pageName); 
		setDescription("Import a Fsa File"); 
	}

	public void createAdvancedControls(Composite parent) {

	}

	protected void createLinkTarget() {
	}


	public void createControl(Composite parent) {

		Composite fileSelectionArea = new Composite(parent, SWT.NONE);



		GridLayout gl = new GridLayout(2, true);
		gl.marginLeft = 1;
		gl.marginRight = 1;
		fileSelectionArea.setLayout(gl);
		Label label = new Label(fileSelectionArea, SWT.NONE );
		label.setText("select file to import: ");
		label.setSize(150,25);
		Label label2 = new Label(fileSelectionArea,SWT.TOP);
		label2.setText("Select destination folder: ");

		tv = new CheckboxTreeViewer(fileSelectionArea); //checkboxtree
		GridData g = new GridData(200,200);
		tv.getTree().setLayoutData(g);
		tv.setContentProvider(new FileTreeSystemContentProvider()); //contenent system drive
		tv.setLabelProvider(new FileTreeLabelProvider());
		tv.addFilter(new AllowOnlyXmlSerFileFilter()); // tree filter only folder a xml or ser file
		tv.setInput("root");

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath location = root.getLocation();

		dtv = new TreeViewer(fileSelectionArea); // tree view to select destination folder
		GridData g2 = new GridData(200, 200);
		dtv.getTree().setLayoutData(g2);
		dtv.setContentProvider(new FileTreeContentProvider());
		dtv.setLabelProvider(new FileTreeLabelProvider());

		dtv.setInput(new File(location.toString())); 
		dtv.addFilter(new AllowOnlyFoldersFilter()); // tree filter only folder

		tv.addCheckStateListener(new ICheckStateListener() { // this listener on check folder, checked all subfolders
			public void checkStateChanged(CheckStateChangedEvent event) {
				tv.setSubtreeChecked(event.getElement(), event.getChecked());


				updateState();
			}});
		dtv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				updateState(); // check if the page is complete
			}


		});


		setControl(fileSelectionArea);	

	}
	/**
	 * 
	 * @return the destination folder, select on tree view
	 */
	public File getDestinationFolder(){	

		if (!dtv.getSelection().isEmpty()){
			ITreeSelection selection = (ITreeSelection) dtv.getSelection();
			Object el = selection.getFirstElement();

			return (File) el;
		}else
			return null;
	}


	/**
	 * 
	 * 
	 * @return the checked elements
	 */

	public File[] getFileToExport(){	
		if (tv.getCheckedElements().length != 0){

			Object[] selection = tv.getCheckedElements();
			File[] files = new File[selection.length];

			for(int i = 0; i<selection.length;i++)
				files[i] = (File) selection[i];


			return files;
		}else
			return null;
	}

	/**
	 * 
	 * method for check if the page is complete, and the button finish must be enabled
	 */
	private void updateState() {

		StringBuffer sb = new StringBuffer();
		File destinationFolder = getDestinationFolder();
		int cont = 0;


		// target folder must be selected

		if(destinationFolder == null ){
			sb.append("select a destination ");
			setPageComplete(false);
			cont++;
		}

		//checked elements must be selected
		if ( tv.getCheckedElements().length == 0 ){
			sb.append("Select at least an XML or SER");
			setPageComplete(false);
			cont++;
		}

		if (cont == 0){
			setPageComplete(true);
			setMessage(sb.toString());
		}else
			setMessage(sb.toString());


	}
}
