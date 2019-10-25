/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
