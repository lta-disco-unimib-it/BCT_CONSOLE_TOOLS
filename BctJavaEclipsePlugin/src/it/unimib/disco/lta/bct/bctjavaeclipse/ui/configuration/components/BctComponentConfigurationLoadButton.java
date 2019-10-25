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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class BctComponentConfigurationLoadButton implements SelectionListener{

	private ComponentsConfigurationComposite monitoringConfigurationManager;
	
	public BctComponentConfigurationLoadButton(ComponentsConfigurationComposite componentsConfigurationComposite)
	{
		this.monitoringConfigurationManager=componentsConfigurationComposite;
	}
	
	public void widgetDefaultSelected(SelectionEvent e)
	{
		
	}
	
	public void widgetSelected(SelectionEvent e) {
		Shell shell=new Shell();
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Open");

		String userHome = System.getProperty("user.home");
		fd.setFilterPath(userHome);

		String[] filterExt = { "*.bctcc", "*.*" };
		fd.setFilterExtensions(filterExt);  	  
		String selected = fd.open();

		if ( selected == null )
			return;

		try {
			ComponentsConfiguration monitoringConfiguration = ComponentsConfigurationDeserializer.deserialize(new File(selected));
			
			monitoringConfigurationManager.load(monitoringConfiguration);
		} catch (FileNotFoundException e1) {
			MessageDialog.openError(new Shell(), "Error", "Cannot load the configuration "+e1.getMessage());
			e1.printStackTrace();
		} catch (ComponentManagerException e1) {
			MessageDialog.openError(new Shell(), "Error", "Cannot load the configuration "+e1.getMessage());
			e1.printStackTrace();
		}

	}
}
