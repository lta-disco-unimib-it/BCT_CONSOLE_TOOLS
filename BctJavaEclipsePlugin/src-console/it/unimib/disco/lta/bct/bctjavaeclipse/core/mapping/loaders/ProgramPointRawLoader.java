package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

public class ProgramPointRawLoader implements Loader<ProgramPointRaw> {

	private ResourceFile resource;
	private String methodCallId;
	private String methodSignature;

	public ProgramPointRawLoader(ResourceFile resource, String methodSignature, String methodCallId) {
		this.resource = resource;
		this.methodCallId = methodCallId;
		this.methodSignature = methodSignature;
	}

	@Override
	public ProgramPointRaw load() throws LoaderException {
		try {
			return resource.getFinderFactory().getProgramPointsRawHandler().find(methodSignature, methodCallId);
		} catch (MapperException e) {
			throw new LoaderException("Problem ivoking finder",e);
		}
		
	}

}
