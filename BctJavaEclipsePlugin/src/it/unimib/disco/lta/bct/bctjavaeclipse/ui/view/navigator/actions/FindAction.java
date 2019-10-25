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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.ProgramPointsVisualizerEditor;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.ComponentTreeData;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData.ContentType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import util.componentsDeclaration.SystemElement;

public class FindAction extends Action {

	private FindDialog findDialog;
	private FindDialogInput dialogInput;
	private StructuredViewer viewer;
	private static final int RAW_IO_TRACES = 0, NORMALIZED_IO_TRACES = 1;

	public FindAction(StructuredViewer structuredViewer) {
		super.setText("Find Program Points");
		viewer = structuredViewer;
	}

	@Override
	public void run() {
		dialogInput = new FindDialogInput();
		findDialog = new FindDialog(viewer.getControl().getShell(), dialogInput);

		if (findDialog.open() == Dialog.OK) {
			// Get selected SystemElement. We use SystemElement instead of Component to support the selection
			// of Environment component that isn't Component object, but a SystemElement object.
			TreeSelection treeSelection = (TreeSelection) viewer.getSelection();
			List<SystemElement> components = getSelectedComponents(treeSelection);

			// Getting resource associated with monitoring configuration.
			MonitoringConfiguration mc = getMonitoringConfiguration(treeSelection);
			Resource resource = MonitoringConfigurationRegistry.getInstance().getResource(mc);

			// FIXME: We use the following IFile as fake editor input. There would be a better solution.
			IFile monitoringConfigurationFile = MonitoringConfigurationRegistry.getInstance()
					.getMonitoringConfigurationFile(mc);

			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorPart editor = IDE.openEditor(page, monitoringConfigurationFile,
						"bctjavaeclipse.editors.ProgramPointsVisualizerEditor");
				if (editor instanceof ProgramPointsVisualizerEditor) {
					ProgramPointsVisualizerEditor ppEditor = (ProgramPointsVisualizerEditor) editor;

					int tracesType = getIoTracesType(treeSelection);
					List<? extends ProgramPoint> programPoints = getProgramPoints(tracesType, resource, components);
					ppEditor.setProgramPoints(programPoints);
				}
			} catch (PartInitException e) {
				Logger.getInstance().log(e);
			}
		}
	}

	@Override
	public boolean isEnabled() {
		// check if selection contains only components elements
		TreeSelection treeSelection = (TreeSelection) viewer.getSelection();
		
		if (checkComponents(treeSelection) && checkMonitoringConfiguration(treeSelection) && checkFolder(treeSelection)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean checkFolder(TreeSelection treeSelection) {
		// Check if selected components have the same parent folder.
		Object parent = null;
		
		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData component = (BctMonitoringConfigurationTreeData) firstElement;
			parent = component.getParent();
		}
		
		Iterator<?> iterator = treeSelection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof BctMonitoringConfigurationTreeData) {
				BctMonitoringConfigurationTreeData data = (BctMonitoringConfigurationTreeData) next;
				if (!parent.equals(data.getParent())) {
					return false;
				}
			}
		}
		
		// Check if selected components are children of raw io traces or normalized io traces folder.
		if (parent instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData parentFolder = (BctMonitoringConfigurationTreeData) parent;
			ContentType folderType = parentFolder.getContentType();
			
			if (folderType != null && (folderType.equals(ContentType.RAW_IO_TRACES) || folderType.equals(ContentType.NORMALIZED_IO_TRACES))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if given selection contains only components folder.
	 * 
	 * @param treeSelection
	 *            tree selection
	 * @return <code>true</code> if selection contains only components folder, <code>false</code> otherwise
	 */
	private boolean checkComponents(TreeSelection treeSelection) {
		Iterator<?> iterator = treeSelection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (!(next instanceof ComponentTreeData)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if given selection is made up of components belong to the same monitoring configuration.
	 * 
	 * @param treeSelection
	 *            tree selection
	 * @return <code>true</code> if selection contains only components belongs to the same monitoring configuration,
	 *         <code>false</code> otherwise
	 */
	private boolean checkMonitoringConfiguration(TreeSelection treeSelection) {
		MonitoringConfiguration mc = null;

		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof ComponentTreeData) {
			ComponentTreeData component = (ComponentTreeData) firstElement;
			mc = component.getMonitoringConfiguration();
		}

		Iterator<?> iterator = treeSelection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof BctMonitoringConfigurationTreeData) {
				BctMonitoringConfigurationTreeData data = (BctMonitoringConfigurationTreeData) next;
				if (!mc.equals(data.getMonitoringConfiguration())) {
					return false;
				}
			}
		}
		return true;
	}

	private List<? extends ProgramPoint> getProgramPoints(int tracesType, Resource resource,
			List<SystemElement> components) {
		if (tracesType == RAW_IO_TRACES) {
			return resource.getFinderFactory().getProgramPointsRawHandler().find(components,
					dialogInput.getProgramPointNameExpression(), dialogInput.getProgramPointType(),
					dialogInput.getVariableNameExpression(), dialogInput.getVariableType(),
					dialogInput.getVariableValueExpression(), dialogInput.getVariableModifiedInfo());
		} /*
			 * else if (tracesType == NORMALIZED_IO_TRACES) { return
			 * resource.getFinderFactory().getProgramPointsNormalizedHandler().find(components,
			 * findDialogInput.getProgramPointNameExpression(), findDialogInput.getProgramPointType(),
			 * findDialogInput.getVariableNameExpression(), findDialogInput.getVariableType(),
			 * findDialogInput.getVariableValueExpression(), findDialogInput.getVariableModifiedInfo()); }
			 */else {
			return null;
		}
	}

	private MonitoringConfiguration getMonitoringConfiguration(TreeSelection treeSelection) {
		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof ComponentTreeData) {
			ComponentTreeData component = (ComponentTreeData) firstElement;
			return component.getMonitoringConfiguration();
		}
		return null;
	}

	private List<SystemElement> getSelectedComponents(TreeSelection treeSelection) {
		List<SystemElement> selected = new ArrayList<SystemElement>();

		Iterator<?> iterator = treeSelection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof ComponentTreeData) {
				ComponentTreeData componentItem = (ComponentTreeData) next;
				SystemElement component = componentItem.getComponent();
				if (!selected.contains(component)) {
					selected.add(component);
				}
			}
		}
		return selected;
	}

	private int getIoTracesType(ITreeSelection treeSelection) {
		Object firstElement = treeSelection.getFirstElement();
		if (firstElement instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData data = (BctMonitoringConfigurationTreeData) firstElement;
			Object parent = data.getParent();
			if (parent instanceof BctMonitoringConfigurationTreeData) {
				BctMonitoringConfigurationTreeData parentFolder = (BctMonitoringConfigurationTreeData) parent;
				ContentType type = parentFolder.getContentType();
				
				if (type.equals(ContentType.RAW_IO_TRACES)) {
					return RAW_IO_TRACES;
				} else if (type.equals(ContentType.NORMALIZED_IO_TRACES)) {
					return NORMALIZED_IO_TRACES;
				} 
			}
		}
		return -1;
	}
}