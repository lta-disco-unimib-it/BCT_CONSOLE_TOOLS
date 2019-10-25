package it.unimib.disco.lta.bct.bctjavaeclipse.core.vart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import tools.fshellExporter.CBMCExecutor.ValidationResult;

public class VARTDataPropertyLine extends VARTDataProperty {

	private int line;

	public VARTDataPropertyLine( MonitoringConfiguration mc, String file, int line, String assertion,
			ValidationResult result, int injectedLine ) {
		super( mc, file, assertion, result, injectedLine );
		this.line = line;
		
	}

	@Override
	public int getLine() {
		return line;
	}

}
