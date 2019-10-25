package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri;

import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.RESOURCE_IDENTIFIER;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.Trace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.URIUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class URIFileMapper extends URIAbstractMapper {

	public URIFileMapper(Resource resource) {
		super(resource);
	}

	@Override
	public IoModel getIoModel(URI uri) throws MapperException {
		Method method = getMethod(uri);
		return resource.getFinderFactory().getIoModelsHandler().getIoModel(method);
	}

	@Override
	public List<IoModel> getIoModels(URI uri) throws MapperException {
		return resource.getFinderFactory().getIoModelsHandler().getIoModels();
	}
	
	@Override
	public List<FSAModel> getFSAModels(URI uri) throws MapperException {
		return resource.getFinderFactory().getFSAModelsHandler().getFSAModels();
	}

	@Override
	public Method getMethod(URI uri) {
		Hashtable<String, String> queryStringElements = URIUtil.parseQueryString(uri.getQuery());
		return resource.getFinderFactory().getMethodHandler().getMethod(
				queryStringElements.get(RESOURCE_IDENTIFIER));
	}

	@Override
	public Trace getIoTrace(URI uri) throws MapperException {
		Method method = getMethod(uri);
		if(URIUtil.rawTraceURI(uri))
			return resource.getFinderFactory().getIoRawTraceHandler().findTrace(method);
		else
			return resource.getFinderFactory().getIoNormalizedTraceHandler().findTrace(method);
	}
	
	@Override
	public List<Trace> getIOTraces(URI uri) throws MapperException {
		List<Trace> traces = new ArrayList<Trace>();
		if(URIUtil.rawTraceURI(uri))
			traces.addAll(resource.getFinderFactory().getIoRawTraceHandler().findAllTraces());
		else
			traces.addAll(resource.getFinderFactory().getIoNormalizedTraceHandler().findAllTraces());
		return traces;
	}
	
	@Override
	public InteractionNormalizedTrace getInteractionNormalizedTrace(URI uri) throws MapperException {
		Method method = getMethod(uri);
		return resource.getFinderFactory().getInteractionNormalizedTraceHandler().findTrace(method);
	}
	
	@Override
	public InteractionRawTrace getInteractionRawTrace(URI uri) throws MapperException {
		Hashtable<String, String> queryStringElements = URIUtil.parseQueryString(uri.getQuery());
		String resourceId = queryStringElements.get(RESOURCE_IDENTIFIER); 
		
		if ( resourceId == null || resourceId.isEmpty() || resourceId.equals("**")){
			String pid = queryStringElements.get(PluginConstants.PID);
			String threadId = queryStringElements.get(PluginConstants.THREADID);
			return resource.getFinderFactory().getInteractionRawTraceHandler().findTrace(pid,threadId);
		}
		
		return resource.getFinderFactory().getInteractionRawTraceHandler().findTrace(resourceId);
	}
	
	@Override
	public List<InteractionNormalizedTrace> getInteractionNormalizedTraces(URI uri) throws MapperException {
		List<InteractionNormalizedTrace> traces = new ArrayList<InteractionNormalizedTrace>();
		traces.addAll(resource.getFinderFactory().getInteractionNormalizedTraceHandler().findAllTraces());
		return traces;
	}
	
	@Override
	public List<InteractionRawTrace> getInteractionRawTraces(URI uri) throws MapperException {
		List<InteractionRawTrace> traces = new ArrayList<InteractionRawTrace>();
		traces.addAll(resource.getFinderFactory().getInteractionRawTraceHandler().findAllTraces());
		return traces;
	}

	@Override
	public void saveIoModel(IoModel model) throws MapperException {
		resource.saveEntity(model);
	}

	@Override
	public void saveIoTrace(Trace trace) {
		try {
			resource.saveEntity(trace);
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		}
	}
}
