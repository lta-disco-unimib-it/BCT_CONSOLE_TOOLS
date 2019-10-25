package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.Trace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData.ContentType;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;

public abstract class URIAbstractBuilder {
	protected final String PATH_SEPARATOR = PluginConstants.URI_PATH_SEPARATOR;
	protected final String SCHEME = "bct";
	protected final String AUTHORITY;
	protected final String FRAGMENT = null;
	protected String path, query = "";

	private MonitoringConfiguration mc;

	public URIAbstractBuilder(MonitoringConfiguration mc) {
		this.mc = mc;
		AUTHORITY = getMonitoringConfigurationFilePath();
	}

	public URI buildURI(Object resource) {
		if (resource instanceof IoModel)
			return buildIOModelURI((IoModel) resource);
		else if (resource instanceof FSAModel)
			return buildFSAModelURI((FSAModel) resource);
		else if (resource instanceof Trace)
			return buildTraceURI((Trace) resource);
		else if (resource instanceof ContentType)
			return buildResourcesIndexURI((ContentType) resource);
		return null;
	}

	protected URI buildURI() {
		URI uri = null;
		try {
			uri = new URI(SCHEME, AUTHORITY, path, query, FRAGMENT);
			//System.out.println("Built URI: " + uri);
		} catch (URISyntaxException e) {
			Logger.getInstance().log(e);
		}
		return uri;
	}

	protected void setPath(String path) {
		this.path = path;
	}

	protected void addParameterToQueryString(String id, String value) {
		if (!query.isEmpty()) {
			query = query + "&" + id + "=" + value;
		} else {
			query = id + "=" + value;
		}
	}

	private String getMonitoringConfigurationFilePath() {
		IFile mcFile = MonitoringConfigurationRegistry.getInstance().getMonitoringConfigurationFile(mc);
		
		return PATH_SEPARATOR + mcFile.getProject().getName() + PATH_SEPARATOR + mcFile.getProjectRelativePath();
	}

	protected abstract URI buildIOModelURI(IoModel model);
	protected abstract URI buildFSAModelURI(FSAModel model);
	protected abstract <T extends Trace> URI buildTraceURI(T trace);
	protected abstract URI buildResourcesIndexURI(ContentType type);

	public abstract URI buildRawTraceURI(String pid, String threadId, int callId, Types callPointType);
}
