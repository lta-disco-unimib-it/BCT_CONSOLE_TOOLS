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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

public class MonitoringConfigurationFactory {
	
	public static MonitoringConfiguration createAndStoreMonitoringConfiguration(IProgressMonitor monitor,
			String resourceName, IPath dest,
			StorageConfiguration storageConfiguration,
			CRegressionConfiguration regressionConfiguration,
			FlattenerOptions opt, String referredProjectName) throws CoreException{
		return createAndStoreMonitoringConfiguration(monitor, resourceName, dest, storageConfiguration, regressionConfiguration, opt, referredProjectName, true);
	}
	
	
	public static MonitoringConfiguration createAndStoreMonitoringConfiguration(IProgressMonitor monitor,
			String resourceName, IPath dest,
			StorageConfiguration storageConfiguration,
			CRegressionConfiguration regressionConfiguration,
			FlattenerOptions opt, String referredProjectName, boolean identifyComponentsToMonitor)
			throws CoreException {
		monitor.beginTask("Analyze project and storing monitoring configuration...",3);
		
		//get data
		//is it correct or we have to get this data before, at the caller side? It should happen that the configuration window have bee disposed? 
		
		monitor.subTask("Creating the monitoring configuration.");
		
		
		MonitoringConfiguration mrc = MonitoringConfiguration.createMonitoringConfiguration(
				resourceName, storageConfiguration, regressionConfiguration,
				opt, referredProjectName, identifyComponentsToMonitor );
		
		monitor.worked(1);
		
		monitor.subTask("Serializing BCT configuration and updating files");
		storeMonitoringConfiguration(dest, mrc);
		monitor.worked(1);
		
		monitor.subTask("Refreshing project");
		//FIXME: maybe this is not the best solution
		IContainer containerToRefresh = ResourcesPlugin.getWorkspace().getRoot().getFile(dest).getParent();
		containerToRefresh.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		
		monitor.worked(1);
		
		return mrc;
	}

	public static void storeMonitoringConfiguration(
			IPath dest, MonitoringConfiguration mrc) throws CoreException {
		try {
			File serFile = ResourcesPlugin.getWorkspace().getRoot().getFile(dest).getRawLocation().toFile();
			serFile.getParentFile().mkdirs();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			MonitoringConfigurationSerializer.serialize(serFile, mrc);
			ConfigurationFilesManager.updateConfigurationFiles(mrc);
			//ConfigurationFilesManager.updatRegressionComponentsToMonitor(mrc); //Commented out on 2014-05-16: This is not the place for updating the components to monitor. If needed the update should be forced elsewhere before storing the configuration.
			ConfigurationFilesManager.updateMonitoringScripts(mrc);
			
			File coverageFolder = ConfigurationFilesManager.getCoverageFolder(mrc);
			coverageFolder.mkdirs();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Status s = new Status(1, DefaultOptionsManager.pluginID, "Cannot save monitoring configuration to "+dest+", an exception occurred.",e);
			throw new CoreException(s);
		} catch (ConfigurationFilesManagerException e) {
			e.printStackTrace();
			Status s = new Status(1, DefaultOptionsManager.pluginID, "Cannot update bct configuration files, an exception occurred.",e);
			throw new CoreException(s);
		}
	}
}
