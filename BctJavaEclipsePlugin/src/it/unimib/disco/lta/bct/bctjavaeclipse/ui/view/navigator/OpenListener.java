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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.swt.widgets.Display;

public class OpenListener implements IOpenListener {

	public void open(OpenEvent event) {
		ISelection selection = event.getSelection();
		IStructuredSelection structuredSelection;
		Object selectedElement;
		BctMonitoringConfigurationTreeData data;
		Display display = event.getViewer().getControl().getDisplay();
		
		if (selection instanceof IStructuredSelection) {
			structuredSelection = (IStructuredSelection) selection;
			selectedElement = structuredSelection.getFirstElement();
			if (selectedElement instanceof BctMonitoringConfigurationTreeData) {
				data = (BctMonitoringConfigurationTreeData)selectedElement;			
				if (!data.isOpenable())	return;
				EditorOpener.openEditor(display, data.getURI());
			} else if(selectedElement instanceof IFile) { 
				//Opening .bctmc file
				IFile monitoringConfigurationFile = (IFile)selectedElement;
			
				/* 
				 * We have the guarantee that monitoringConfigurationFile is a Monitoring Configuration File
				 * (i.e. a file with .bctmc extension) because we have defined in plugin.xml (Extension tab) that
				 * action is enbled when open action occured on IFile with .bctmc extension and we haven't any other
				 * IFile in contents that we provide with BctMonitoringConfigurationContentProvider. 
				 */
				EditorOpener.openMonitoringConfigurationEditor(display, monitoringConfigurationFile);
			}
		}
	}
}
