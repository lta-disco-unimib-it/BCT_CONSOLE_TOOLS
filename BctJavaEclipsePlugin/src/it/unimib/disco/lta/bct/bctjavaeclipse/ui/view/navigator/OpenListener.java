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
