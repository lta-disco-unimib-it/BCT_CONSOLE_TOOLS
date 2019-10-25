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
