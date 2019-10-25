package it.unimib.disco.lta.bct.bctjavaeclipse.ui.preferences;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptionsException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * this class make a dialog for add new daikon cofiguraton
 * this dialog is use by invariant generator composite
 *
 * @author Valerio Terragni
 *
 */

public class AddNewDaikonConfigDialog extends Dialog {
	String newconfigname;
	String newconfig;

	/**
	 * @param parent
	 */
	public AddNewDaikonConfigDialog(Shell parent) {
		super(parent);
	}

	public String open() { // open dialog
		Shell parent = getParent(); //new Shell
		final Shell shell =
			new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE);
		shell.setText("New Daikon Config");
		shell.setBounds(200, 200, 450, 350);

		Label labelName = new Label(shell, SWT.NULL);
		labelName.setText("name :");
		labelName.setBounds(15, 15, 30, 25);

		final Text textconfigname = new Text(shell, SWT.SINGLE | SWT.BORDER);
		textconfigname.setBounds(50, 12, 80, 20);

		Label labelConfig = new Label(shell, SWT.NULL);
		labelConfig.setText("config :");
		labelConfig.setBounds(15, 40, 40, 15);

		final Text textconfig = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		textconfig.setBounds(10, 60, 400, 200);

		final Button buttonOK = new Button(shell, SWT.PUSH);
		buttonOK.setText("Add");
		buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		buttonOK.setBounds(130,270,40,20);

		Button buttonCancel = new Button(shell, SWT.PUSH);
		buttonCancel.setText("Cancel");
		buttonCancel.setBounds(170,270,70,20);

		textconfigname.addListener(SWT.Modify, new Listener() { 
			public void handleEvent(Event event) {
				try {
					newconfigname = textconfigname.getText();
					buttonOK.setEnabled(true);
				} catch (Exception e) {
					buttonOK.setEnabled(false);
				}
			}
		});



		buttonOK.addListener(SWT.Selection, new Listener() { // button ok clicked
			public void handleEvent(Event event) {
				try {
					newconfigname = textconfigname.getText();
					newconfig = textconfig.getText();
					if (newconfigname != "" & newconfig != ""){ //if both field aren't empty, can add new daikon config
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						DefaultOptionsManager.getDefaultOptions().addDaikonConfigProperties(newconfigname, newconfig);
						messageBox.setText("Information");
						messageBox.setMessage("New daikon config add");
						messageBox.open(); // open messagebox
						shell.dispose(); // close new shell

					}else{  
						MessageBox messageBoxError = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
						messageBoxError.setText("Error");
						messageBoxError.setMessage("Please fill all fields");
						messageBoxError.open();

					}    
				} catch (BctDefaultOptionsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DefaultOptionsManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});


		buttonCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				newconfigname = null;
				shell.dispose(); // cancel operation, close shell
			}
		});

		shell.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if(event.detail == SWT.TRAVERSE_ESCAPE)
					event.doit = false;
			}
		});


		shell.open(); // open

		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return newconfigname;

	}

}

