package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceDB;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils.LazyListElementsLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils.LazyListElementsLoaderException;
import traceReaders.raw.InteractionTrace;

public class MethodCallPointListLoaderDB implements LazyListElementsLoader<MethodCallPoint> {

	private InteractionTrace trace;
	private ResourceDB resource;

	public MethodCallPointListLoaderDB(ResourceDB resource,
			InteractionTrace originalTrace) {
		this.resource = resource;
	this.trace = originalTrace;
	}

	public int getLoadedElementsCount() {
		throw new RuntimeException("Not Implemented");
	}

	public int getTotalElementsCount() throws LazyListElementsLoaderException {
		throw new RuntimeException("Not Implemented");
	}

	public boolean hasNext() throws LazyListElementsLoaderException {
		throw new RuntimeException("Not Implemented");
	}

	public MethodCallPoint next() throws LazyListElementsLoaderException {
		throw new RuntimeException("Not Implemented");
	}

}
