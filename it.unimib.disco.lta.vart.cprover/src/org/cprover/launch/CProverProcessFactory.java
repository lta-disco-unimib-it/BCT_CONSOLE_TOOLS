package org.cprover.launch;

import java.util.Map;

import org.cprover.model.CProverProcess;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.IProcessFactory;
import org.eclipse.debug.core.model.IProcess;

public class CProverProcessFactory implements IProcessFactory {

	public IProcess newProcess(ILaunch launch, Process process, String label, Map attributes) {
		return new CProverProcess(launch, process, label, attributes);
	}

}
