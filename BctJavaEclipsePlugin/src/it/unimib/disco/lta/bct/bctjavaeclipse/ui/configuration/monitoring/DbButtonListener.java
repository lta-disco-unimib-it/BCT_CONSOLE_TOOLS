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
