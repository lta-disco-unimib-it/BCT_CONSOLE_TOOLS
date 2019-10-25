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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TableTextItemListener implements Listener {

	private Text text;
	private TableItem item;
	private int column;
	private CCombo combo;
	private String rule;
	
	/**
	 * 
	 * @param text
	 * @param item
	 */
	public TableTextItemListener(Text text, TableItem item,int column)
	{
		this.text=text;
		this.item=item;
		this.column=column;
	}
	public TableTextItemListener(CCombo combo, TableItem item,int column)
	{
		this.combo=combo;
		this.item=item;
		this.column=column;

	}
	
	public void handleEvent(Event event) {

		//System.out.println("Item "+text.getText());

			//set items text, it is necessary in order to retrieve the filled values
//		if(column!=3)
//		{
//			item.setText(column,text.getText());
//		}
//		else
//		{
//			item.setText(column,combo.getText());
//		}	
	}

}
