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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;

public class RefreshBCTContentsAction extends Action {

	private StructuredViewer viewer;

	public RefreshBCTContentsAction(StructuredViewer structuredViewer) {
		this.viewer = structuredViewer;
		super.setText("Refresh BCT contents!!");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 * 
	 * run() method behavior: if no tree items are selected then refresh only the viewer;
	 * else refresh all selected item in the viewer and associated monitoring configuration data directories.
	 */
	@Override
	public void run() {
		ISelection selection = viewer.getSelection();
		if (selection.isEmpty())
			viewer.refresh();
		else {
			TreeSelection treeSelection = (TreeSelection) selection;
			for (Iterator<?> iterator = treeSelection.iterator(); iterator.hasNext();) {
				Object element = iterator.next();
				refreshMCDataDir(element);
				refreshViewer(element);
			}
		}
	}

	/**
	 * Refresh the monitoring configuration data directory in order to reflect the local file system changes.
	 * 
	 * @param element
	 *            selected element in the view.
	 */
	private void refreshMCDataDir(Object element) {
		MonitoringConfiguration mc = getAssociatedMonitoringConfiguration(element);

		if (mc != null) {
			StorageConfiguration storageConfiguration = mc.getStorageConfiguration();
			if (storageConfiguration instanceof FileStorageConfiguration) {
				FileStorageConfiguration fileStorageConfiguration = (FileStorageConfiguration) storageConfiguration;
				String dataDirPath = fileStorageConfiguration.getDataDirPath();
	
				IResource folder = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(dataDirPath));
				try {
					folder.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				} catch (CoreException e) {
					Logger.getInstance().log(e);
				}
			}
		}
	}

	private MonitoringConfiguration getAssociatedMonitoringConfiguration(Object element) {
		MonitoringConfiguration mc = null;
		if (element instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData data = (BctMonitoringConfigurationTreeData) element;
			mc = data.getMonitoringConfiguration();
		} else if (element instanceof IFile) {
			IFile bctmcFile = (IFile) element;
			mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(bctmcFile);
		}
		return mc;
	}

	private void refreshViewer(Object element) {
		viewer.refresh(element);
	}
}
