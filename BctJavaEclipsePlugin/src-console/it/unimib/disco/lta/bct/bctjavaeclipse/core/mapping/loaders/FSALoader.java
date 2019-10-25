package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import automata.fsa.FiniteStateAutomaton;

public class FSALoader extends ModelsLoader<FiniteStateAutomaton> {


	private FSAModelsFinder finder;

	public FSALoader(Method method, FSAModelsFinder finder) {
		super(method);
		this.finder = finder;
	}

	public FiniteStateAutomaton load() throws LoaderException {
		try {
			return finder.getRawFSA(getMethod());
		} catch (MapperException e) {
			throw new LoaderException("Cannot load FSA for method "+getMethod(),e);
		}
	}

}
