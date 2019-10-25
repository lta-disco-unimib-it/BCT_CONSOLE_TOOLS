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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.FileSystemDataManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class DeleteModelsRunnableWithProgress implements IRunnableWithProgress {

	private MonitoringConfiguration mc;

	public DeleteModelsRunnableWithProgress(MonitoringConfiguration mc) {
		this.mc = mc;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		try {
			FileSystemDataManager.deleteNormalizedFiles(mc);
			FileSystemDataManager.deleteModels(mc);
		} catch (ConfigurationFilesManagerException e) {
			throw new InvocationTargetException(e);
		}
	}

}
