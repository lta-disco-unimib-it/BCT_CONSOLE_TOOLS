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

import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.ANOMALIES;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.ANOMALIES_ANALISYS;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.BCT_MONITORING_CONFIGURATION_FILE_EXTENSION;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.COMPONENTS;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.DATA_RECORDED_NORMALIZED;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.DATA_RECORDED_RAW;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.ENVIRONMENT_COMPONENT;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.FSA;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_TRACE;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_TRACE;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.MODELS;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData.ContentType;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import conf.EnvironmentalSetter;
import cpp.gdb.TraceUtils;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.SystemElement;

public class BctMonitoringConfigurationContentProvider implements ITreeContentProvider {

	private final String BCTMC_FILE_EXTENSION = BCT_MONITORING_CONFIGURATION_FILE_EXTENSION;

	// Strings in the next vectors have semantic values, because are utilized to
	// determine children in getChildren method.
	private String[] btcmcChildrenTxt = { COMPONENTS, DATA_RECORDED_RAW, DATA_RECORDED_NORMALIZED, MODELS, ANOMALIES, ANOMALIES_ANALISYS };
	private String[] dataRecordedRawChildrenTxt = { IO_TRACE, INTERACTION_TRACE };
	private String[] dataRecordedNormalizedChildrenTxt = { IO_TRACE, INTERACTION_TRACE };
	private String[] modelsChildrenTxt = { FSA, IO };
	
	private DomainObjectsLabelProvider labelProvider = new DomainObjectsLabelProvider();

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFile) {
			IFile bctmc = (IFile) parentElement;
			if (bctmc.getFileExtension().equals(BCTMC_FILE_EXTENSION)) // parentElement is .bctmc file
				return createChildrenFolders(bctmc, btcmcChildrenTxt, false);
		} else if (parentElement instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData parent = (BctMonitoringConfigurationTreeData) parentElement;
			String parentTxt = parent.getTxtToDisplay();
			
			if (parentTxt.equals(ANOMALIES)) {
				return getAnomaliesFolderChildren(parent);
			} else if (parentTxt.equals(ANOMALIES_ANALISYS)) {
				return getAnomaliesAnalysisFolderChildren(parent);
			} else if (parentTxt.equals(INTERACTION_TRACE) && getParentFolderName(parent).equals(DATA_RECORDED_RAW)) {
				ComponentTreeData treeData = new ComponentTreeData("", parent); //Fake ComponentTreeData, we use this only for store
				treeData.setContentType(ContentType.RAW_INTERACTION_TRACES); //content type and pass it to getChildren method.
				return getChildren(treeData);
			} else if (parentTxt.equals(COMPONENTS) || parentTxt.equals(FSA) || parentTxt.equals(IO) || parentTxt.equals(IO_TRACE) ||
					parentTxt.equals(INTERACTION_TRACE)) {
				return createComponentsTreeEntries(parent);
			} else if (parentTxt.equals(MODELS) || parentTxt.equals(DATA_RECORDED_RAW) || parentTxt.equals(DATA_RECORDED_NORMALIZED)) {
				return createChildrenFolders(parent, getChildrenLabels(parentTxt), true);
			}
			
			if (parentElement instanceof ComponentTreeData) {
				ComponentTreeData componentData = (ComponentTreeData) parentElement;
				String txt = getParentFolderName(parent);
				if (txt.equals(COMPONENTS)) { // Components folders requires special treatment
					return componentData.getMonitoredPackages().toArray();
				} else
					return getChildren(componentData);
			}
		}
		return null;
	}

	public Object getParent(Object element) {
		if (element instanceof BctMonitoringConfigurationTreeData) {
			BctMonitoringConfigurationTreeData data = (BctMonitoringConfigurationTreeData) element;
			return data.getParent();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof IFile) {
			String extension = ((IFile) element).getFileExtension();
			if(extension != null && extension.equals(BCTMC_FILE_EXTENSION))
				return true;
		} else if (element instanceof BctMonitoringConfigurationTreeData) {
			return !((BctMonitoringConfigurationTreeData) element).isLeaf();
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	// ******* Methods not in ITreeContentProvider interface ****************
 	
	/**
	 * Creates children for <code>parent</code> element. Created children are generic "folder" container in the tree.
	 * 
	 * @param parent
	 *            the parent element in the tree
	 * @param labels
	 *            children names
	 * @return children tree entries of <code>parent</code> element
	 */
	private BctMonitoringConfigurationTreeData[] createChildrenFolders(Object parent, String[] labels, boolean addContentType) {
		BctMonitoringConfigurationTreeData[] folders = new BctMonitoringConfigurationTreeData[labels.length];
		for (int i = 0; i < labels.length; i++) {
			folders[i] = new BctMonitoringConfigurationTreeData(labels[i], parent);
			folders[i].setImage(labelProvider.getImage(folders[i]));
			
			if(addContentType) {
				folders[i].setContentType(getContentType(folders[i]));
				MonitoringConfiguration mc = folders[i].getMonitoringConfiguration();
				folders[i].setURI(URIBuilderFactory.getBuilder(mc).buildURI(folders[i].getContentType()));
				folders[i].setOpenable(true);
			}
		}
		return folders;
	}

	/**
	 * Creates children for <code>parent</code> element. Created children are instances of ComponentTreeData
	 * representing monitored components.
	 * 
	 * @param parent
	 *            the parent element in the tree
	 * @param mc
	 *            the monitoring configuration associated with <code>parent</code>
	 * @return components tree entries of <code>parent</code> element
	 */
	private ComponentTreeData[] createComponentsTreeEntries(BctMonitoringConfigurationTreeData parent) {
		Collection<Component> components = parent.getMonitoringConfiguration().getComponentsConfiguration().getComponents();
		List<ComponentTreeData> children = new ArrayList<ComponentTreeData>(components.size());
		ContentType type = parent.getContentType();
		
		for (Component component : components) {
			ComponentTreeData child = new ComponentTreeData(component.getName(), parent);
			child.setImage(labelProvider.getImage(child));
			if (type != null)
				child.setContentType(type);
			children.add(child);
		}

		ComponentTreeData child = new ComponentTreeData(ENVIRONMENT_COMPONENT, parent);
		child.setImage(labelProvider.getImage(child));
		if (type != null)
			child.setContentType(type);
		children.add(child);

		return children.toArray(new ComponentTreeData[children.size()]);
	}

	private BctMonitoringConfigurationTreeData[] getChildren(ComponentTreeData parent) {
		MonitoringConfiguration mc = parent.getMonitoringConfiguration();
		List<?> items;
		Collection<?> children = null;
		ContentType type = parent.getContentType();
		
		
		
		try {
			
			File bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
		
			EnvironmentalSetter.setBctHome(bctHome.getAbsolutePath());
			
			switch (type) {
			case FSA_MODELS:
				children = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory()
				.getFSAModelsHandler().getFSAModels();
				break;
			case IO_MODELS:
				children = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory()
				.getIoModelsHandler().getIoModels();
				break;
			case RAW_IO_TRACES:
				children = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory()
				.getIoRawTraceHandler().findAllTraces();
				break;
			case NORMALIZED_IO_TRACES:
				children = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory()
				.getIoNormalizedTraceHandler().findAllTraces();
				break;
			case NORMALIZED_INTERACTION_TRACES:
				children = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory()
				.getInteractionNormalizedTraceHandler().findAllTraces();
				break;
			case RAW_INTERACTION_TRACES:
				children = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory()
				.getInteractionRawTraceHandler().findAllTraces();
			}

			SystemElement expandedComponent = parent.getComponent(); //expandedComponent is null in case of raw interaction trace
			items = getComponentItems(mc, children, expandedComponent);
			
			return createTreeItems(parent, items);
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		} catch (ConfigurationFilesManagerException e) {
			Logger.getInstance().log(e);
		}
		return new BctMonitoringConfigurationTreeData[0];
	}

	
	/**
	 * Creates leaf tree items. Every created item is openable and it has an associated URI.  
	 * @param <T> type of List passed as parameter
	 * @param parent tree parent item
	 * @param items list of items to insert in the tree
	 * @return array of created items
	 */
	private <T> BctMonitoringConfigurationTreeData[] createTreeItems(BctMonitoringConfigurationTreeData parent,
			List<T> items) {
		BctMonitoringConfigurationTreeData[] treeItems = new BctMonitoringConfigurationTreeData[items.size()];
		MonitoringConfiguration mc = parent.getMonitoringConfiguration();
		String signature;
		DomainObjectsLabelProvider labelProvider = new DomainObjectsLabelProvider(mc);
		for (int i = 0; i < treeItems.length; i++) {
			T item = items.get(i);
//			signature = labelProvider.getText(item);
			signature = labelProvider.getText(item);
			treeItems[i] = new BctMonitoringConfigurationTreeData(signature, parent);
			treeItems[i].setImage(labelProvider.getImage(item));
			treeItems[i].setLeaf(true);
			treeItems[i].setOpenable(true);
			treeItems[i].setURI(URIBuilderFactory.getBuilder(mc).buildURI(item));
		}
		return treeItems;
	}

	
	/**
	 * Select all items which belong to passed component, from a given collection of items.
	 * @param <T>
	 * @param items items to filter
	 * @param component items owner component
	 * @return a list of items which belong to passed <code>component</code>. If given component is null then
	 * a list with all items is returned (no filtering done).
	 */
	private <T> List<T> getComponentItems(MonitoringConfiguration mc, Collection<T> items, SystemElement component) {
		List<T> componentOwnedItems = new ArrayList<T>();

		if(component == null) {
			componentOwnedItems.addAll(items);
		} else {
			for (T item : items) {
				String signature = labelProvider.getText(item);
				signature = DisplayNamesUtil.getBytecodeSignatureOnly(mc, signature);
				if (component.acceptBytecodeMethodSignature(signature))
					componentOwnedItems.add(item);
			}
		}
		return componentOwnedItems;
	}
	
	private ContentType getContentType(BctMonitoringConfigurationTreeData data) {
		String parentFolder = getParentFolderName(data);
		String folderName = data.getTxtToDisplay();
		
		if(folderName.equals(IO))
			return ContentType.IO_MODELS;
		else if(folderName.equals(FSA))
			return ContentType.FSA_MODELS;
		else if(folderName.equals(IO_TRACE)) {
			if(parentFolder.equals(DATA_RECORDED_RAW))
				return ContentType.RAW_IO_TRACES;
			else
				return ContentType.NORMALIZED_IO_TRACES;
		}
		else if(folderName.equals(INTERACTION_TRACE)) {
			if(parentFolder.equals(DATA_RECORDED_RAW))
				return ContentType.RAW_INTERACTION_TRACES;
			else
				return ContentType.NORMALIZED_INTERACTION_TRACES;
		}
		return null;
	}
	
	private String getParentFolderName(BctMonitoringConfigurationTreeData data) {
		Object parent = data.getParent();
		if (parent instanceof BctMonitoringConfigurationTreeData)
			return ((BctMonitoringConfigurationTreeData) parent).getTxtToDisplay();
		return null;
	}
	
	private String[] getChildrenLabels(String parentTxt) {
		if(parentTxt.equals(MODELS))
			return modelsChildrenTxt;
		else if(parentTxt.equals(DATA_RECORDED_NORMALIZED))
			return dataRecordedNormalizedChildrenTxt;
		else if(parentTxt.equals(DATA_RECORDED_RAW))
			return dataRecordedRawChildrenTxt;
		return null;
	}
	
	private IFile[] getAnomaliesFolderChildren(BctMonitoringConfigurationTreeData parent) {
		try {
			File cbeLogFile = ConfigurationFilesManager.getBCTCbeLogFile(parent.getMonitoringConfiguration());
			Path path = new Path(cbeLogFile.getAbsolutePath()); 
			IFile[] logFiles = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path);
			List<IFile> logFilesList = new ArrayList<IFile>();
			
			for (int i = 0; i < logFiles.length; i++) {
				IFile logFile = logFiles[i];
				if(logFile.exists()) {
					logFilesList.add(logFile);
				}
			}
			
			return logFilesList.toArray(new IFile[0]);
		} catch (ConfigurationFilesManagerException e) {
			Logger.getInstance().log(e);
		}
		return null;
	}
	
	private IFile[] getAnomaliesAnalysisFolderChildren(BctMonitoringConfigurationTreeData parent) {
		try {
			File violationsLogAnalysisFolder = ConfigurationFilesManager.getViolationsLogAnalysisFolder(parent.getMonitoringConfiguration());
			File[] anomaliesFiles = violationsLogAnalysisFolder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if(name.endsWith(".bctla"))
						return true;
					return false;
				}
			});

			// For each found File, we get the IFile
			List<IFile> anomaliesIFiles = new ArrayList<IFile>(); 
			for (int i = 0; i < anomaliesFiles.length; i++) {
				Path path = new Path(anomaliesFiles[i].getAbsolutePath());
				IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path);
								
				if (files[0].exists()) {
					anomaliesIFiles.add(files[0]);
				}
			}
			return anomaliesIFiles.toArray(new IFile[0]);
		} catch (ConfigurationFilesManagerException e) {
			Logger.getInstance().log(e);
		} 	
		return null;
	}
}
