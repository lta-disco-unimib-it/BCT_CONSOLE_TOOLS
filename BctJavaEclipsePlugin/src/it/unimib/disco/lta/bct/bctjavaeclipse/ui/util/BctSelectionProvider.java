package it.unimib.disco.lta.bct.bctjavaeclipse.ui.util;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public abstract class BctSelectionProvider implements ISelectionProvider {  



	ListenerList listeners = new ListenerList();
	private ISelectionProvider parent;  


	public BctSelectionProvider( ISelectionProvider parent ){
		this.parent = parent;
	}


	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);  
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection select) {
		// TODO Auto-generated method stub
		Object[] list = listeners.getListeners();  
		for (int i = 0; i < list.length; i++) {  
			((ISelectionChangedListener) list[i])  
			.selectionChanged(new SelectionChangedEvent(parent, select));  
		}  
	}


}
