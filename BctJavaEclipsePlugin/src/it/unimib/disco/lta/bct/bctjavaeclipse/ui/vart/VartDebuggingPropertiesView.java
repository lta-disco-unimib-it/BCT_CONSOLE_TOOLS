package it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTResultsLoader;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IMarker;

public class VartDebuggingPropertiesView extends VartDataPropertiesView {

	public VartDebuggingPropertiesView() {
		super();
		markerSeverityForInvalid = IMarker.SEVERITY_ERROR;
	}

	@Override
	protected Collection<? extends VARTDataProperty> loadResults(
			VARTResultsLoader resLoader)
			throws ConfigurationFilesManagerException {
		
		List<VARTDataProperty> res = new LinkedList<>();
		res.addAll(resLoader.loadRegressionAnalysisResults());
		res.addAll( resLoader.loadOutdatedProperties() );
		
		return res;
	}

}
