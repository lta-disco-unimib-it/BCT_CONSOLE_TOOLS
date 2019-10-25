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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class CallFilterBox implements BCTObservable, BCTObserver{
	private Group group;
	private CallFilterTable callFilterTable;
	private BCTObservaleIncapsulated observable;
	
	public CallFilterBox(Composite parent, List<String> componentsNames, ComponentsConfigurationManager cm) {
		//super(parent,SWT.NONE);
//		

	    callFilterTable = new CallFilterTable(parent, componentsNames, cm);
	    callFilterTable.addBCTObserver(this);
	    observable = new BCTObservaleIncapsulated(this);
	}

	public void addComponentName(String name) {
		callFilterTable.addComponentName(name);
	}

	public void removeComponentName(String name) {
		callFilterTable.removeComponentName(name);
	}

	public List<CallFilter> getCallFilters(){
		return callFilterTable.getCallFilters();
	}
	
	public void loadCallFilters( Collection<CallFilter> filters ){
		callFilterTable.loadCallFilters(filters);
	}

	public void clear() {
		callFilterTable.clear();
	}

	public void reset(ArrayList<String> componentNames) {
		callFilterTable.reset(componentNames);

	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observable.notifyBCTObservers(message);
	}
}
