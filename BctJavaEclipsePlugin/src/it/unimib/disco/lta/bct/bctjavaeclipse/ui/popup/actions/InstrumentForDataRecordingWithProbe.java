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
