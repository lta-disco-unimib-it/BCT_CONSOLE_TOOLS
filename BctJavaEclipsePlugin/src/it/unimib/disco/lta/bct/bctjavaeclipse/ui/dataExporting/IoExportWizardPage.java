package it.unimib.disco.lta.bct.bctjavaeclipse.ui.dataExporting;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class IoExportWizardPage extends WizardPage {

	private Text destinationFolderText;

	protected IoExportWizardPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		
		Composite c = new Composite(parent, SWT.NONE);
		super.setControl(c);
		
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		
		c.setLayout(gl);
		
		Label l = new Label(c, SWT.NONE);
		l.setText("Destination folder");
		
		destinationFolderText = new Text(c, SWT.BORDER);

		Button daikonpathButton = new Button(c,SWT.NULL);
		daikonpathButton.setText("Browse");

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
	}
	
	private void updateState() {
		StringBuffer sb = new StringBuffer();

		if ( destinationFolderText.getText().isEmpty() ){
			sb.append("You must select a destination folder");
		}

		if (sb.length() == 0){ // no violations
			setMessage(null);
			setPageComplete(true);
		}else{
			setPageComplete(false);
			setMessage(sb.toString());
		}
	}

	public String getDestinationFolder() {
		// TODO Auto-generated method stub
		return destinationFolderText.getText();
	}


	


}
