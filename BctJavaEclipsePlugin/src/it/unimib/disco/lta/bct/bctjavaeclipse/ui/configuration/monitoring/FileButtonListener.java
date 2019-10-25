package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class FileButtonListener implements SelectionListener {

	Button fileButton,dbButton;
	Text passwordText;
	Text userText;
	Text uriText;
	Text dataDirFileText;
	Composite parent;
	


	public FileButtonListener( Button fileButton, Button dbButton,Text passwordText, Text userText, Text uriText, Text dataDirFileText) {
		
		this.fileButton=fileButton;
		this.dbButton=dbButton;
		this.passwordText=passwordText;
		this.uriText=uriText;
		this.userText=userText;
		this.dataDirFileText=dataDirFileText;

	}


	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Stub di metodo generato automaticamente

	}

	public void widgetSelected(SelectionEvent e) {
		
		System.out.println("RADIO file");
  		fileButton.setSelection(true);
  		dbButton.setSelection(false);
  		
  		dataDirFileText.setEditable(true);
  		passwordText.setEditable(false);
		uriText.setEditable(false);
		userText.setEditable(false);

	      
	}

}
