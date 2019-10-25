package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.ProgramPointsRawFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public class ProgramPointsRawLoader implements Loader<List<ProgramPointRaw>> {

	private ProgramPointsRawFinder finder;
	private IoRawTrace trace;

	public ProgramPointsRawLoader( ProgramPointsRawFinder finder, IoRawTrace trace ) {
		this.finder = finder;
		this.trace = trace;
	}
	
	public List<ProgramPointRaw> load() throws LoaderException {
		try {
			return finder.find(trace);
		} catch (MapperException e) {
			throw new LoaderException("Cannot load data",e);
		}
	}


}
