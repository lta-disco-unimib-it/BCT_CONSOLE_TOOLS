package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.MethodCallSequence;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.InteractionNormalizedTraceMapperFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public class MethodCallSequencesLoader implements Loader<List<MethodCallSequence>> {

	private InteractionNormalizedTraceMapperFile finder;
	private Method method;

	public MethodCallSequencesLoader(Method method, InteractionNormalizedTraceMapperFile tracesFinder) {
		this.finder = tracesFinder;
		this.method = method;
	}

	public List<MethodCallSequence> load() throws LoaderException {
		try {
			return finder.getMethodCallSequences(method);
		} catch (MapperException e) {
			throw new LoaderException("Canno load call sequences",e);
		}
	}

}
