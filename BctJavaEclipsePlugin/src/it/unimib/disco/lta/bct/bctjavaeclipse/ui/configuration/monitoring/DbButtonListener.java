package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class DbButtonListener implements SelectionListener {

	Button fileButton,dbButton;
	Text passwordText;
	Text userText;
	Text uriText;
	Text dataDirFileText;


	public DbButtonListener(Button fileButton, Button dbButton, Text passwordText, Text userText, Text uriText, Text dataDirFileText) {

		
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


		System.out.println("radio DB");
		
  		fileButton.setSelection(false);
  		dbButton.setSelection(true);
  		
  		dataDirFileText.setEditable(false);
  		passwordText.setEditable(true);
		uriText.setEditable(true);
		userText.setEditable(true);

	      

	}

}
