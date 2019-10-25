package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionRawTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public class MethodCallPointLoader implements Loader<List<MethodCallPoint>> {

	private InteractionRawTraceFinder finder;
	private String traceId;

	/**
	 * Loader of method call point values.
	 * 
	 * @param finder
	 * @param threadId
	 * @param threadId2 
	 */
	public MethodCallPointLoader( InteractionRawTraceFinder finder, String traceId ) {
		this.finder = finder;
		this.traceId = traceId;
	}

	public List<MethodCallPoint> load() throws LoaderException {
		try {
			return finder.findMethodCallPointsForTrace(traceId);
		} catch (MapperException e) {
			throw new LoaderException("Cannot load call points for thread "+traceId,e);
		}
	}

}
