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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.MonitoringConfigurationManager;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class SaveObjectFlattenerListener implements SelectionListener {

	ObjectFlattenerPage of;
	MonitoringConfigurationManager mcm;
	
	public SaveObjectFlattenerListener(ObjectFlattenerPage of, MonitoringConfigurationManager mcm)
	{

		this.of=of;
		this.mcm=mcm;
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Stub di metodo generato automaticamente

	}

	public void widgetSelected(SelectionEvent e) {
		System.out.println("SaveFlattenerButtonPressed");
		System.out.println("gettingFlattenerOptions");
		FlattenerOptions flattenerOptions = of.getDefinedFlattenerOptions();
		System.out.println("settingFlattenerOptions");
		//mcm.setFlattenerOptions( flattenerOptions );

		of.shell.close();
	}
	

}
