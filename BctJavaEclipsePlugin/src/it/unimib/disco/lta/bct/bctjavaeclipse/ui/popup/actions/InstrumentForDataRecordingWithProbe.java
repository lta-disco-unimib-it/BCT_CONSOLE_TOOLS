package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InstrumentForDataRecordingWithProbe  extends InstrumentWithProbe  {

	public InstrumentForDataRecordingWithProbe() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected InstrumentProbeWithProgress getInstrumentWithProgress(
			Shell shell, Display display, IFile selectedElement,
			String[] resourcesToInstrument, Set<IProject> projectsToInstrument, MonitoringConfiguration mc, boolean updateLaunchConfiguration) {
		return new InstrumentDataRecordingProbeWithProgress(
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
	protected void doAdditionalActions(MonitoringConfiguration mc) {
		
	}

}
