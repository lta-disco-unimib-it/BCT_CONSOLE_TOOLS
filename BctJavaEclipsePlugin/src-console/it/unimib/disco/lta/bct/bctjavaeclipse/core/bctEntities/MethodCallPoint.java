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
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;

/**
 * This class represent a point of execution of a method. It is the basic block of an interaction trace.
 * 
 * @author Fabrizio Pastore
 *
 */
public abstract class MethodCallPoint {

	private Method method;
	private String methodCallId;
	private ProgramPointRawLoader programPointRawLoader;

	public enum Types { ENTER, EXIT, GENERIC }
	private Types type;
	
	/**
	 * Constructor
	 * 
	 * @param method the method this call point refers to
	 * @param methodCallId 
	 * @param programPointRawLoader 
	 */
	public MethodCallPoint( Method method, String methodCallId, ProgramPointRawLoader programPointRawLoader, Types type ){
		this.method = method;
		this.methodCallId = methodCallId;
		this.programPointRawLoader = programPointRawLoader;
		this.type = type;
	}

	public Method getMethod() {
		return method;
	}

	public String getMethodCallId() {
		return methodCallId;
	}
	
	public final boolean isExit() {
		return type == Types.EXIT;
	}

	public final boolean isEnter(){
		return type == Types.ENTER;
	}
	
	public ProgramPoint getCorrespondingProgramPoint() {
		try {
			return programPointRawLoader.load();
		} catch (LoaderException e) {
			return null;
		}
	}

	public final boolean isGeneric() {
		return type == Types.GENERIC;
	}
}
