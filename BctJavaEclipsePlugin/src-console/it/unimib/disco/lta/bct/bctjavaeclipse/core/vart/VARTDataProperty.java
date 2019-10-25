package it.unimib.disco.lta.bct.bctjavaeclipse.core.vart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import tools.fshellExporter.CBMCExecutor.ValidationResult;

public abstract class VARTDataProperty {

	private String file;
	private String assertion;
	protected ValidationResult result;
	protected MonitoringConfiguration mc;
	private IFile iFile;
	private int injectedLine;
	
	

	public int getInjectedLine() {
		return injectedLine;
	}

	public VARTDataProperty(MonitoringConfiguration mc, String file, String assertion,
			ValidationResult result, int injectedLine) {
		this.mc = mc;
		this.file = file;
		this.assertion = assertion;
		this.result = result;
		this.injectedLine = injectedLine;
	}

	public String getFile() {
		return file;
	}

	public String getAssertion() {
		return assertion;
	}

	public ValidationResult getResult() {
		return result;
	}
	
	public abstract int getLine();

	public IFile getIFile() {
		if ( iFile != null ){
			return iFile;
		}
		CRegressionConfiguration conf = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		String folder = conf.getModifiedSwSourcesFolder();
		iFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(folder+"/"+getFile()));
		return iFile;
	}

	public MonitoringConfiguration getMonitoringConfiguration() {
		return mc;
	}
	
	

}
