package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import java.util.List;

import automata.fsa.FiniteStateAutomaton;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.EFSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.EFSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.EFSAModelsMapper;

public class EFSAModelsMapperFile extends AbstractModelsMapperFile implements
		EFSAModelsMapper {
	
	

	EFSAModelsMapperFile(ResourceFile resource) {
		super(resource);
	}



	public List<EFSAModel> getEFSAModels() {
		throw new RuntimeException("Not Implemented");
	}
	
	

	public EFSAModel getEFSAModel(Method m) {
		throw new RuntimeException("Not Implemented");
	}
	

	public FiniteStateAutomaton getRawEFSA(Method m) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}



	public void update(EFSAModel entity) {
		throw new RuntimeException("Not Implemented");
	}



}
