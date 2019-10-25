package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.File;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InstrumentDataRecordingProbeWithProgress extends InstrumentProbeWithProgress  {
	
	


	public InstrumentDataRecordingProbeWithProgress(Shell shell,
			Display display, IFile selectedElement,
			String[] resourcesToInstrument,Set<IProject> projectsToInstrument, MonitoringConfiguration mc,boolean updateLaunchConfiguration) {
		super(shell, display, selectedElement, resourcesToInstrument, projectsToInstrument, mc,updateLaunchConfiguration);
	}

	@Override
	protected File getProbeScript(MonitoringConfiguration mc) throws ConfigurationFilesManagerException {
		return ConfigurationFilesManager.getDataRecordingProbeScript(mc);
	}
	

}
