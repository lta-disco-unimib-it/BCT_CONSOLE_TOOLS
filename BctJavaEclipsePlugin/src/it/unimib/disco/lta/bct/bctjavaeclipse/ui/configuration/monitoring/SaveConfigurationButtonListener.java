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


import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class SaveConfigurationButtonListener implements SelectionListener {

	private ComponentsConfigurationComposite componentsConfigurationComposite;
	
	public SaveConfigurationButtonListener(ComponentsConfigurationComposite componentsConfigurationComposite )
	{
		this.componentsConfigurationComposite=componentsConfigurationComposite;

	}
	
	public void widgetDefaultSelected(SelectionEvent event) {
		// TODO Stub di metodo generato automaticamente

	}

	public void widgetSelected(SelectionEvent event) {

		Shell shell=new Shell();
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setText("Save");

		String userHome = System.getProperty("user.home");
		fd.setFilterPath(userHome);

		String[] filterExt = { "*.bctcc", "*.*" };
		fd.setFilterExtensions(filterExt);  	  
		String selected = fd.open();

		if ( selected == null )
			return;

		try{
			ComponentsConfigurationSerializer.serialize(new File(selected), componentsConfigurationComposite.createComponentsConfiguration());		
			System.out.println("Chiusura file");
		}catch(FileNotFoundException e){
			//FIXME: throw exception
			System.out.println("not found");
		}
	}

}

