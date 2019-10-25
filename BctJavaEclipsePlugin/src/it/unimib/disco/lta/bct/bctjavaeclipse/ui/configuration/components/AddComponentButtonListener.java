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


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import util.componentsDeclaration.Component;


public class AddComponentButtonListener implements SelectionListener {

	ComponentDefinitionShell shell;
	Text nameComponentFilter;
	ComponentsConfigurationManager componentManager;
	Text textPackage ;
	
	public AddComponentButtonListener(ComponentDefinitionShell shell,Text nameComponentFilter,ComponentsConfigurationManager componentManager)
	{
		
		this.shell=shell;
		this.nameComponentFilter=nameComponentFilter;

		this.componentManager = componentManager;
	}
	

	public void widgetDefaultSelected(SelectionEvent e) {
		

	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println("Salvataggio Componente");


		System.out.println("aggiunto componente");
		try{
		Component component = shell.getDefinedComponent();
		if ( ! componentManager.contains(component) ){
			try {
				componentManager.addComponent( component );
			} catch (ComponentManagerException e) {
				//FIXME
				// TODO Blocco catch generato automaticamente
				e.printStackTrace();
			}
		}
		} catch ( Throwable t ){
			t.printStackTrace();
		}
		
		

	}



}

