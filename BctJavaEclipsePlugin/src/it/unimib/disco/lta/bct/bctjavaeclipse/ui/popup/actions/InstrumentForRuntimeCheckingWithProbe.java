package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InstrumentForRuntimeCheckingWithProbe  extends InstrumentWithProbe  {

	public InstrumentForRuntimeCheckingWithProbe() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected InstrumentProbeWithProgress getInstrumentWithProgress(
			Shell shell, Display display, IFile selectedElement,
			String[] resourcesToInstrument, Set<IProject> projectsToInstrument, MonitoringConfiguration mc, boolean updateLaunchConfiguration) {
		return new InstrumentRuntimeCheckingProbeWithProgress(
				shell,
				display,
				selectedElement,
				resourcesToInstrument,
				projectsToInstrument,
				mc,
				updateLaunchConfiguration
		);
	}


	@Override
	protected void doAdditionalActions(MonitoringConfiguration mc) throws CoreException, DefaultOptionsManagerException {
		BctDefaultOptions options = WorkspaceOptionsManager.getWorkspaceOptions();
		
		mc.setViolationsRecorderOptions(options.getViolationsRecorderOptions());
	}

}
