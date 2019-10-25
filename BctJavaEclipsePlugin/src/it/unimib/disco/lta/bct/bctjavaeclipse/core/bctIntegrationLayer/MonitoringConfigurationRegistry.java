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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.DBStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.URIUtil;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class MonitoringConfigurationRegistry implements IResourceChangeListener {
	private boolean initializing = false;
	
	private static final MonitoringConfigurationRegistry registry = new MonitoringConfigurationRegistry();

	// Keeps association between BCT data directory folders and MonitoringConfiguration objects.
	private ConcurrentHashMap<String, MonitoringConfiguration> map = new ConcurrentHashMap<String, MonitoringConfiguration>();

	// Keeps association between BCT monitoring configuration files and MonitoringConfiguration objects.
	private ConcurrentHashMap<IFile, MonitoringConfiguration> MCPathMap = new ConcurrentHashMap<IFile, MonitoringConfiguration>();
	private ConcurrentHashMap<MonitoringConfiguration, IFile> pathMCMap = new ConcurrentHashMap<MonitoringConfiguration, IFile>();

	private MonitoringConfigurationRegistry() {
		try {
			init();
		} catch (Exception e) {
			Logger.getInstance().log(e);
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	public static MonitoringConfigurationRegistry getInstance() {
		return registry;
	}

	/**
	 * @param resource A resource in BCT data folder.
	 * @return A MonitoringConfiguration represents BCT Monitoring Configuration at which <code>resource</code> belongs to.<br>
	 *         If <code>resource</code> isn't found in any BCT data folders present in the workspace return <code>null</code>.
	 */
	public MonitoringConfiguration getAssociatedMonitoringConfiguration(IFile resource) {
		if (resource.exists()) {
			IResource container = (IResource) resource;

			do {
				MonitoringConfiguration mc = map.get(container.getFullPath().toString());

				if (mc != null)
					return mc;
				container = container.getParent();
			} while (container != null);
		}
		return null;
	}

	/**
	 * @param bctMonitoringConfigurationFile File stores BCT monitoring configuration.
	 * @return MonitoringConfiguration object that represent configuration stored in <code>bctMonitoringConfigurationFile</code>. <br>
	 *         If <code>bctMonitoringConfigurationFile</code> isn't BCT monitoring configuration file, <code>null</code> is returned.
	 */
	public MonitoringConfiguration getMonitoringConfiguration(IFile bctMonitoringConfigurationFile) {
		MonitoringConfiguration result = MCPathMap.get(bctMonitoringConfigurationFile);
		if ( result == null ){ //TODO: This is a patch on the fly, i think we should throw an exception not return null
			try {
				init();
				result = MCPathMap.get(result);
			} catch (Exception e) {
				Logger.getInstance().log(e);
			}
		}
		return result;
	}

	public MonitoringConfiguration getMonitoringConfiguration(URI bctURI) {
		String monitoringConfigurationPath = bctURI.getAuthority();
		IPath path = new Path(monitoringConfigurationPath);
		IFile monitoringConfigurationFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		return MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(monitoringConfigurationFile);
	}

	public IFile getMonitoringConfigurationFile(MonitoringConfiguration mc) {
		IFile result = pathMCMap.get(mc);
		if ( result == null ){ //TODO: This is a patch on the fly, i think we should throw an exception not return null
			try {
				init();
				result = pathMCMap.get(mc);
			} catch (Exception e) {
				Logger.getInstance().log(e);
			}
		}
		return result;
	}

	public Resource getResource(MonitoringConfiguration mc) {
		StorageConfiguration sc = mc.getStorageConfiguration();
		if (sc instanceof FileStorageConfiguration) {
			FileStorageConfiguration fsc = (FileStorageConfiguration) sc;

			URI uri = URIUtil.buildURI(fsc.getDataDirPath());
			IPath path = new Path(uri.getPath());
			
			IResource members = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
			
			
			IPath rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			
			IPath resourcePath = path;
			path = path.makeRelativeTo(rootPath);
			//Handle problem related to projects linked to folder outside workspace
			String projectName = path.segment(0);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if ( ! project.exists() ){
				for ( IProject prj : ResourcesPlugin.getWorkspace().getRoot().getProjects() ){
					if ( prj.getLocation().isPrefixOf(resourcePath) ){
						project = prj;
						break;
					}
				}
				//iterate over all projects to find the one that matches
			}
			path = path.removeFirstSegments(1);
			String absolutePath = project.getFile(path).getLocation().toFile().getAbsolutePath();
			
			System.out.println(members);
			
			//old version
//			String absolutePath = path.toFile().getAbsolutePath();
			
			return new ResourceFile(null, absolutePath, null);
		}
		// TODO Add implementation of DatabaseStorageConfiguration case.
		return null;
	}

	private void init() throws CoreException, FileNotFoundException {
		initializing = true;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		
		for (IProject project : projects) {
			
			//FIXME: we inspect only open projects, we need to add 
			//monitoring configuration files of closed projects when they are opened
			if (!project.isOpen()) {
				continue;
			}

			for (IResource resource : project.members()){
				searchMonitoringConfigurationFiles(resource);
			}
			
			
		}
		initializing = false;
	}

	/**
	 * This method searches for monitoring configuration files and then for every bctmc found 
	 * populates the maps with the file referenced by the corresponding monitoring ocnfiguration
	 * 
	 * @param resource
	 * @throws FileNotFoundException
	 * @throws CoreException
	 */
	private void searchMonitoringConfigurationFiles(IResource resource) throws FileNotFoundException, CoreException {
		if (!resource.exists())
			return;

		if (resource.getType() == IResource.FILE) {
			String fileExtension = resource.getFileExtension();
			if (fileExtension != null && fileExtension.equals(PluginConstants.BCT_MONITORING_CONFIGURATION_FILE_EXTENSION)) {
				IFile file = (IFile) resource;

				if (MCPathMap.get(file) == null) {
					MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(resource.getLocation().toFile());
					System.out.println(this.getClass().getSimpleName() + ": deserialized MC named " + mc.getNameConf());

					MCPathMap.put(file, mc);
					pathMCMap.put(mc, file);

					StorageConfiguration sc = mc.getStorageConfiguration();
					if (sc instanceof FileStorageConfiguration) {
						String dataDir = ((FileStorageConfiguration) sc).getDataDirPath();
						map.put(dataDir, mc);
					}
					else if (sc instanceof DBStorageConfiguration) { // TODO: verify if database storage case is correct
						String dataDir = ((FileStorageConfiguration) sc).getDataDirPath();
						map.put(dataDir, mc);
					}
					
				}
			}
			return;
		}

		if (resource.getType() == IResource.FOLDER) {
			IFolder folder = (IFolder) resource;
			for (IResource folderChild : folder.members()) {
				searchMonitoringConfigurationFiles(folderChild);
			}
		}
	}

	public void resourceChanged(IResourceChangeEvent event) {
		if ( initializing ){
			return;
		}
		IResourceDelta rootDelta = event.getDelta();
		ArrayList<MonitoringConfiguration> monitoringConfigurationsToUpdate = Collections.list(MCPathMap.elements());
		
		//Check for monitoring configuration files updates:
		//at this point we check if the user modified a bctmc file
		//in this case we need to remove the MonitoringConfiguration object from all the maps and repopulate them
		Enumeration<IFile> mcFiles = pathMCMap.elements();
		while(mcFiles.hasMoreElements()) {
			IFile file = mcFiles.nextElement();
			MonitoringConfiguration mc = MCPathMap.get(file);
			
			IResourceDelta delta = rootDelta.findMember(file.getFullPath());
			if(delta != null) { //if delta != null the monitoring conf has been updated
				StorageConfiguration sc = mc.getStorageConfiguration();
				if (sc instanceof FileStorageConfiguration) {
					String dataDir = ((FileStorageConfiguration) sc).getDataDirPath();
					removeMonitoringConfiguration(dataDir);
					monitoringConfigurationsToUpdate.remove(mc);
				}
				if (sc instanceof DBStorageConfiguration) {
//					String dataDir = ((DBStorageConfiguration) sc).getDataDirPath();
//					removeMonitoringConfiguration(dataDir);
					removeMonitoringConfiguration(mc);
					monitoringConfigurationsToUpdate.remove(mc);
				}
			}
		}
		
		//Check for bct data folders updates
		for(MonitoringConfiguration mc : monitoringConfigurationsToUpdate) {
			StorageConfiguration sc = mc.getStorageConfiguration();
			if (sc instanceof FileStorageConfiguration) {
				String dataDir = ((FileStorageConfiguration) sc).getDataDirPath();
				IPath path = new Path(dataDir);
				IResourceDelta delta = rootDelta.findMember(path);

				if (delta != null){
					//DO SOMETHING IF NECESSARY
				}
			}
		}
		
		try {
			init();
		} catch (Exception e) {
			Logger.getInstance().log(e);
		}
	}

	private void removeMonitoringConfiguration(String dataDirPath) {
		System.out.println("REMOVING DATA PATH "+dataDirPath);
		MonitoringConfiguration mc = map.get(dataDirPath);
		IFile mcFile = pathMCMap.get(mc);

		MCPathMap.remove(mcFile);
		pathMCMap.remove(mc);
		map.remove(dataDirPath);
	}
	
	private void removeMonitoringConfiguration(MonitoringConfiguration mc) {
		
		
		IFile mcFile = pathMCMap.get(mc);

		MCPathMap.remove(mcFile);
		pathMCMap.remove(mc);
		
	}
	
	public Collection<MonitoringConfiguration> getMonitoringConfgurations(){
		return map.values();
	}

	public void save(MonitoringConfiguration config) throws FileNotFoundException {
		
		MonitoringConfigurationSerializer.serialize(getMonitoringConfigurationFile(config).getLocation().toFile(), config);
		
	}
}