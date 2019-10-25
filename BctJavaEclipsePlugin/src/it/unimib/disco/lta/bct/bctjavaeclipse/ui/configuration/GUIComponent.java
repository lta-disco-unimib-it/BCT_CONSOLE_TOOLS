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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import org.eclipse.swt.widgets.Table;

public class GUIComponent{

	String text;
	Table table;
	String componentName;
	
	public GUIComponent(String text, Table table) {
		this.text=text;
		this.table=table;
	}
	
	public String getNameC()
	{
		componentName=text;
		return componentName;
	}
	
	public String getPackage(int i)
	{
		
		return table.getItem(i).getText(0);
	}
	
	
}
