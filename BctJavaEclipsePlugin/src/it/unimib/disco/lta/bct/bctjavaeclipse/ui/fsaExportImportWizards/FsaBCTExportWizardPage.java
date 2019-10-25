package it.unimib.disco.lta.bct.bctjavaeclipse.ui.fsaExportImportWizards;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * this is the export wizard page for fsa file
 * 
 *@author Terragni Valerio
 */
public class FsaBCTExportWizardPage extends WizardPage {
	private Button buttonSer; // checkbox Ser
	private Button buttonJFlapXml;// checkbox JFlap xml
	private Button buttonJEti; // checkbox Jeti xml
	
	private Text destinationFolderText;
	private CheckboxTreeViewer tv;
	

	public FsaBCTExportWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		setTitle(pageName); 
		setDescription("Export a Fsa File"); 
	}

	public void createAdvancedControls(Composite parent) {

	}

	protected void createLinkTarget() {
	}


	public void createControl(Composite parent) {


		Composite fileSelectionArea = new Composite(parent, SWT.NONE);



		GridLayout gl = new GridLayout(1, true);
		gl.marginLeft = 1;
		gl.marginRight = 1;
		fileSelectionArea.setLayout(gl);
		Label label = new Label(fileSelectionArea, SWT.NONE );
		label.setText("select the FSA files to export:");
		label.setSize(150,25);

		
		//checkboxtree for make choose the fsa file to export
		tv = new CheckboxTreeViewer(fileSelectionArea); 
		GridData g = new GridData(400,200);

		tv.getTree().setLayoutData(g);
		tv.setContentProvider(new FileTreeContentProvider());
		tv.setLabelProvider(new FileTreeLabelProvider());
        
		// get workspace path
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath location = root.getLocation();

		tv.setInput(new File(location.toString())); // set input to root tree, the workspace main folder
		tv.addFilter(new AllowOnlyFsaFileFilter()); // tree filter only folder and fsa file


		tv.addCheckStateListener(new ICheckStateListener() { // this listener on check a node
			public void checkStateChanged(CheckStateChangedEvent event) {
				tv.setSubtreeChecked(event.getElement(), event.getChecked()); //checked all child element
				updateState(); // check isPageComplete
			}});

		Group groupDestination = new Group(fileSelectionArea, SWT.NULL);
		groupDestination.setText("Select destination folder: ");


		destinationFolderText = new Text(groupDestination, SWT.BORDER);
		destinationFolderText.setBounds(5,30,350,25);


		Button daikonpathButton = new Button(groupDestination,SWT.NULL);
		daikonpathButton.setText("Browse");
		daikonpathButton.setBounds(360,30,60,25);

		daikonpathButton.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				Shell shell = new Shell();

				DirectoryDialog fileDialog = new DirectoryDialog(shell, SWT.OPEN);
				String dir = fileDialog.open();
				if(dir != null) {
					destinationFolderText.setText(dir);
				}
				updateState();// isPageComplete
			}


		});


		Group group1 = new Group(fileSelectionArea, SWT.NULL);
		group1.setText("Export into the following formats: ");

		buttonSer = new Button(group1, SWT.CHECK);
		buttonSer.setBounds(5, 20,20,20);
		buttonSer.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				updateState();
			}
		});
		Label labelSer = new Label(group1,SWT.NULL);
		labelSer.setText("Ser ");
		labelSer.setBounds(25, 22,30,30);


		buttonJFlapXml = new Button(group1, SWT.CHECK);
		buttonJFlapXml.setBounds(5, 50,20,20);
		buttonJFlapXml.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				updateState();
			}
		});
		Label labelJFlapXml = new Label(group1,SWT.NULL);
		labelJFlapXml.setText("JFlap xml ");
		labelJFlapXml.setBounds(25, 52,70,30);


		buttonJFlapXml.addSelectionListener(new SelectionAdapter() {//click button for open fileDialog
			public void widgetSelected(SelectionEvent event) {
				updateState();
			}
		});
		buttonJEti = new Button(group1, SWT.CHECK);
		buttonJEti.setBounds(5, 80,20,20);

		Label labelJEtiXml = new Label(group1,SWT.NULL);
		labelJEtiXml.setText("JEti ");
		labelJEtiXml.setBounds(25, 82,70,30);

		
		setControl(fileSelectionArea);
		updateState();
	}
/**
 * call this method for every change page status
 */
	private void updateState() { 

		StringBuffer sb = new StringBuffer();
		File destinationFolder = getDestinationFolder();
		int cont = 0;
		//one checkbox ser or xml must be selected
		if((getSerSelection()==false)&&(getJFlapXmlSelection()==false)&&(getJEtiXmlSelection()==false)){
			sb.append("Select one format ");
			setPageComplete(false);
			cont++;
		}

		// target folder must be selected

		if(destinationFolder == null ){
			sb.append("select a destination ");
			setPageComplete(false);
			cont++;
		}
		if ( tv.getCheckedElements().length == 0 ){
			sb.append("Select at least an FSA or a folder containing FSAs");
			setPageComplete(false);
			cont++;
		}



		if (cont == 0){ // no violations
			setPageComplete(true);
			setMessage(sb.toString());
		}else
			setMessage(sb.toString());


	}
/**
 * 
 * get destination folder
 * 
 * @return
 */
	public File getDestinationFolder(){	

		if (destinationFolderText.getText()!="")
			return (new File(destinationFolderText.getText()));
		else 
			return null;

	}


/**
 * 
 * get checked file to export
 * 
 * @return checked file elements
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

	public boolean getSerSelection() {
	    return buttonSer.getSelection();
	}

	public boolean getJFlapXmlSelection() {
		return buttonJFlapXml.getSelection();
	}

	public boolean getJEtiXmlSelection() {
		return buttonJEti.getSelection();
	}

	

}

