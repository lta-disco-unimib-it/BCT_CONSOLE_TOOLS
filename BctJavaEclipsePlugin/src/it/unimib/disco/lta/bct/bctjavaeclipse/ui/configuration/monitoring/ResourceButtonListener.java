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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class ResourceButtonListener implements SelectionListener {

	ResourcesPage composite;
	Resource resource;
	
	public ResourceButtonListener(ResourcesPage composite) {
	
		this.composite=composite;
		
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Stub di metodo generato automaticamente

	}

	public void widgetSelected(SelectionEvent event) {
		
////		System.out.println("DIR "+ composite.getConfigurationName().getText());
//		if (composite.isSelectedFile()==true)
//		{
////			System.out.println("SALVA FILE");
//			resource = new ResourceFile(composite.getResourceName().getText(),composite.getDataDirFileText().getText(),monitorConfiguration.getConfigurationName().getText());
//		}
//		else
//		{
//			if(composite.isSelectedDB()==true)
//			{
////				System.out.println("SALVA DB");
//				resource = new ResourceDB(composite.getResourceName().getText(),composite.getUriText().getText(),composite.getUserText().getText(),composite.getPasswordText().getText(),monitorConfiguration.getConfigurationName().getText());
//			}
//		}
		
//		try{
//
//			System.out.println("Creazione RISORSA XML ");
//			File filename= new File("c:/risorsa.xml");	
//
//			ResourceSerializer.serialize(filename, composite.createResources());		
//			System.out.println("Chiusura file");
//			
//			
//
//			}catch(FileNotFoundException e){
//			      System.out.println("not found");}
//
	}

}
