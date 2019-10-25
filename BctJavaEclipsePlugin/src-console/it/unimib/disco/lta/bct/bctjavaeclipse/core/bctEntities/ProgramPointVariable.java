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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;

/**
 * This class represent a program point variable.
 * 
 * @author Fabrizio Pastore
 *
 */
public class ProgramPointVariable {
	public enum Type { Unknown, Int, Hash, String, Double };
	private String name;
	private Method method;
	private Type type;

	/**
	 * Consructor
	 * 
	 * @param method Method to wich the variable is associated
	 * @param name name of the variable
	 * @param type variable type
	 */
	public ProgramPointVariable(Method method, String name, Type type) {
		this.method = method;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Method getMethod() {
		return method;
	}

	public Type getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
