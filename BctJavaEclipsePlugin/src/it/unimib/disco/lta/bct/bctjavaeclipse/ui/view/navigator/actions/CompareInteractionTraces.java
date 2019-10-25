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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.navigator.CommonNavigator;

public class CompareInteractionTraces implements IViewActionDelegate {

	IViewPart view;

	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {
		System.out.println(view.getClass().getCanonicalName());
		if (view instanceof CommonNavigator) {
			CommonNavigator navigator = (CommonNavigator) view;
			new CompareInteractionTracesAction(navigator.getCommonViewer(),true).run();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
