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

import java.util.List;

public class CollectionWrappingLoader<T> extends WrappingLoader<List<T>> {

	public CollectionWrappingLoader(List<T> data) {
		super(data);
	}

	public boolean removeItem(T itemToRemove){
		return ((List<T>)data).remove(itemToRemove);
	}
	
	public boolean containsItem(T itemToRemove){
		return ((List<T>)data).contains(itemToRemove);
	}
}
