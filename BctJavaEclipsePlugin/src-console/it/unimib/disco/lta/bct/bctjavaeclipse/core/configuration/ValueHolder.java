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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.Loader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

public class ValueHolder<T> {

	private Loader<T> loader;
	private T value = null;
	
	public ValueHolder( Loader<T> loader ){
		this.loader = loader;
	}
	
	public T getValue() throws LoaderException{
		if ( value == null )
			value = loader.load();
		return value;
	}

}
