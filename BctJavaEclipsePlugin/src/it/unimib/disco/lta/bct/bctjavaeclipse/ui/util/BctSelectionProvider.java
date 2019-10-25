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
