package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import modelsFetchers.FileModelsFetcher;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;

public abstract class AbstractModelsMapperFile extends AbstractMapperFile {

	protected FileModelsFetcher modelsFetcher;
	
	AbstractModelsMapperFile(ResourceFile resource) {
		super(resource);
		modelsFetcher = new FileModelsFetcher(resource.getIoModelsDir(),resource.getInteractionModelsDir());
	}

	protected FileModelsFetcher getModelsFetcher() {
		return modelsFetcher;
	}


	

}
