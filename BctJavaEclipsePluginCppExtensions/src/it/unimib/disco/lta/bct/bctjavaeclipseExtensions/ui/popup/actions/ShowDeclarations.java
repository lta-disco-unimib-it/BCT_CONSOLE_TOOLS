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
package it.unimib.disco.lta.bct.bctjavaeclipseExtensions.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.ProgramPointsUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ShowDeclarations implements IObjectActionDelegate {

	private ISelection selection;

	public ShowDeclarations() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IAction action) {
		
		if ( selection == null ){
			return;
		}
		// TODO Auto-generated method stub
		if ( selection instanceof IStructuredSelection ){
			IStructuredSelection ss = (IStructuredSelection) selection;
			final IFile selectedElement = (IFile) ss.getFirstElement();
			try {
				ProgramPointsUtil.getVariables(selectedElement, 0);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

}
