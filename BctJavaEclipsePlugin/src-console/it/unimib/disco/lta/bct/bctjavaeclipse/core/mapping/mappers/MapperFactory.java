package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FinderFactory;

public abstract class MapperFactory <T extends Resource> implements FinderFactory {

	protected T resource;
	private MethodMapperNonPersistent methodMapper;
	
	protected MapperFactory( T resource ){
		this.resource = resource;
		this.methodMapper = new MethodMapperNonPersistent();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.bct.bctjavaeclipse.core.dataLayer.FinderFactory#getIoRawTraceFinder()
	 */
	public abstract IoRawTraceMapper getIoRawTraceHandler();

	public MethodMapper getMethodHandler() {
		return methodMapper;
	}
	
}
