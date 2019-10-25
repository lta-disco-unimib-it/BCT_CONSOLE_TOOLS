package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import java.util.ArrayList;
import java.util.List;

import modelsFetchers.ModelsFetcherException;
import automata.fsa.FiniteStateAutomaton;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.FSALoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.FSAModelsMapper;

public class FSAModelsMapperFile extends AbstractModelsMapperFile implements
		FSAModelsMapper {

	
	FSAModelsMapperFile(ResourceFile resource) {
		super(resource);
	}

	public List<FSAModel> getFSAModels() {
		ArrayList<FSAModel> models = new ArrayList<FSAModel>();
		
		for ( String methodSignature : modelsFetcher.getInteractionModelsNames() ){
			Method method = resource.getFinderFactory().getMethodHandler().getMethod(methodSignature);
			
			models.add(  getFSAModel(method)	);
			
		}
		
		return models;
	}
	
	public FSAModel getFSAModel(Method m) {
		return new FSAModel("-1",m,new ValueHolder<FiniteStateAutomaton>(new FSALoader(m,this)));
	}
	

	public FiniteStateAutomaton getRawFSA(Method m) throws MapperException {
		try {
			return modelsFetcher.getInteractionModel(m.getSignature());
		} catch (ModelsFetcherException e) {
			throw new MapperException("Cannot load FSA for method "+m.getSignature(),e);
		}
	}

	public void update(FSAModel entity) {
		throw new RuntimeException("Not Implemented");
	}

	
}
