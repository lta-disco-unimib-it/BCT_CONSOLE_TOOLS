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
