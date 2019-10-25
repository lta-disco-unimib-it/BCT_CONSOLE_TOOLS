package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

import automata.fsa.FiniteStateAutomaton;

public interface FSAModelsFinder  {

	public List<FSAModel> getFSAModels() throws MapperException;
	
	public FSAModel getFSAModel( Method m ) throws MapperException;
	
	public FiniteStateAutomaton getRawFSA( Method m ) throws MapperException;
	
}
