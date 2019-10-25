package it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTResultsLoader;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;

public class VartDynamicPropertiesView extends VartDataPropertiesView {

	public VartDynamicPropertiesView() {
		super();
		markerSeverityForInvalid = IMarker.SEVERITY_WARNING;
	}

	@Override
	protected Collection<? extends VARTDataProperty> loadResults(
			VARTResultsLoader resLoader)
			throws ConfigurationFilesManagerException {
		return resLoader.loadDynamicProperties();
	}

}
