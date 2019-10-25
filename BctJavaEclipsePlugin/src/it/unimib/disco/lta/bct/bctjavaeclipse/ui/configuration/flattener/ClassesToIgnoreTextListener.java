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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;


import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener.ObjectFlattenerPage.TableRow;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class ClassesToIgnoreTextListener implements Listener {

	

	private TableRow tableRow;
	private Text classToIgnoreText;

	public ClassesToIgnoreTextListener(TableRow row, Text classToIgnoreText) {
		this.tableRow = row;
		this.classToIgnoreText = classToIgnoreText;
	}

	public void handleEvent(Event event) {
		
		//tableRow.setContent(classToIgnoreText.getText());
		
	}

}
