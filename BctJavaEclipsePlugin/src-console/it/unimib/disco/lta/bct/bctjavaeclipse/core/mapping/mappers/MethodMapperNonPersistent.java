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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

/**
 * This mapper return Method objects taht are not stored on File/DB, this is used to maintain unicity of methods among a resource.
 * 
 * @author Fabrizio Pastore
 *
 */
public class MethodMapperNonPersistent extends NonPersistentMapper<String, Method> implements MethodMapper {
	
	public Method getMethod(String signature) {
		return getElement(signature);
	}

	protected Method newElement(String signature) {
		return new Method(signature);
	}
}
