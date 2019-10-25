package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.EFSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

import automata.fsa.FiniteStateAutomaton;

public interface EFSAModelsFinder {

	public abstract List<EFSAModel> getEFSAModels() throws MapperException;

	public abstract EFSAModel getEFSAModel(Method m) throws MapperException;

	public abstract FiniteStateAutomaton getRawEFSA(Method m)
			throws MapperException;

}