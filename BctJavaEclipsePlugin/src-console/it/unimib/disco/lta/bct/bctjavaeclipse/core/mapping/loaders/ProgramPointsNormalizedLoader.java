package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointNormalized;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ProgramPointsNormalizedFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public class ProgramPointsNormalizedLoader implements
		Loader<List<ProgramPointNormalized>> {

	private ProgramPointsNormalizedFinder finder;
	private IoNormalizedTrace trace;

	public ProgramPointsNormalizedLoader( ProgramPointsNormalizedFinder finder, IoNormalizedTrace trace ) {
		this.finder = finder;
		this.trace = trace;
	}
	
	public List<ProgramPointNormalized> load() throws LoaderException {
		try {
			return finder.find(trace);
		} catch (MapperException e) {
			throw new LoaderException("Cannot load data",e);
		}
	}

}
