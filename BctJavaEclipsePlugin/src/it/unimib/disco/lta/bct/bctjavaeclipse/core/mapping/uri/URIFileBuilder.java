package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri;

import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.*;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.Trace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.DomainObjectsLabelProvider;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.BctMonitoringConfigurationTreeData.ContentType;

import java.net.URI;

public class URIFileBuilder extends URIAbstractBuilder {

	public URIFileBuilder(MonitoringConfiguration mc) {
		super(mc);
	}

	@Override
	protected URI buildIOModelURI(IoModel model) {
		String path = IO_MODELS_PATH;
		setPath(path);
		addParameterToQueryString(RESOURCE_IDENTIFIER, model.getMethod().getSignature());
		return buildURI();
	}

	@Override
	protected URI buildFSAModelURI(FSAModel model) {
		String path = INTERACTION_MODELS_PATH;
		setPath(path);
		addParameterToQueryString(RESOURCE_IDENTIFIER, model.getMethod().getSignature());
		return buildURI();
	}

	@Override
	protected <T extends Trace> URI buildTraceURI(T trace) {
		String path = null;
		DomainObjectsLabelProvider labelProvider = new DomainObjectsLabelProvider();

		if (trace instanceof IoRawTrace) {
			path = IO_RAW_TRACES_PATH;
		} else if (trace instanceof IoNormalizedTrace) {
			path = IO_NORMALIZED_TRACES_PATH;
		} else if (trace instanceof InteractionNormalizedTrace) {
			path = INTERACTION_NORMALIZED_TRACES_PATH;
		} else if (trace instanceof InteractionRawTrace) {
			path = INTERACTION_RAW_TRACES_PATH;
		}

		setPath(path);
		//getText is a UI method, we need to decouple UI from URI
		//addParameterToQueryString(PluginConstants.RESOURCE_IDENTIFIER, labelProvider.getText(trace));
		
		String text = null;
		
		
		
		if (trace instanceof InteractionRawTrace) {

			text = trace.getId();
		} else {
			Method method = Method.getMethod(trace);
			if (method != null){
				text = method.getSignature();
			}
		}
		
		addParameterToQueryString(PluginConstants.RESOURCE_IDENTIFIER, text);
		return buildURI();
	}

	@Override
	public URI buildRawTraceURI( String pid , String threadId, int callId, Types callPointType){
		String path = INTERACTION_RAW_TRACES_PATH;
		setPath(path);
	
		addParameterToQueryString(PluginConstants.RESOURCE_IDENTIFIER, "**");
		addParameterToQueryString(PluginConstants.PID, pid);
		addParameterToQueryString(PluginConstants.THREADID, threadId);
		addParameterToQueryString(PluginConstants.CALLID, String.valueOf(callId) );
		addParameterToQueryString(PluginConstants.CALL_POINT_TYPE, callPointType.toString() );
		
		return buildURI();
	}
	
	@Override
	protected URI buildResourcesIndexURI(ContentType type) {
		String path = null;
		
		switch(type) {
		case FSA_MODELS:
			path = INTERACTION_MODELS_PATH;
			break;
		case IO_MODELS:
			path = IO_MODELS_PATH;
			break;
		case RAW_IO_TRACES:
			path = IO_RAW_TRACES_PATH;
			break;
		case NORMALIZED_IO_TRACES:
			path = IO_NORMALIZED_TRACES_PATH;
			break;
		case NORMALIZED_INTERACTION_TRACES:
			path = INTERACTION_NORMALIZED_TRACES_PATH;
			break;
		case RAW_INTERACTION_TRACES:
			path = INTERACTION_RAW_TRACES_PATH;
			break;
		}
		
		setPath(path);
		addParameterToQueryString(PluginConstants.RESOURCE_IDENTIFIER, "*");
		return buildURI();
	}
}