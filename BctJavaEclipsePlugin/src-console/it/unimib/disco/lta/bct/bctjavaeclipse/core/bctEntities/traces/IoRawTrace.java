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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

import java.util.List;

/**
 * This is an IO raw trace.
 *  
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public class IoRawTrace extends RawTrace {

	private ValueHolder<List<ProgramPointRaw>> programPoints;
	private Method method;
	
	public IoRawTrace(String id, Method method) {
		super(id);
		this.method = method;
	}

	public void setProgramPoints( ValueHolder<List<ProgramPointRaw>> valueHolder ){
		this.programPoints = valueHolder;
	}
	
	public IoRawTrace(String id, Method method, ValueHolder<List<ProgramPointRaw>> programPoints) {
		this(id,method);
		this.programPoints = programPoints;
	}

	public List<ProgramPointRaw> getProgramPoints() throws LoaderException {
		return programPoints.getValue();
	}
	
	public Method getMethod() {
		return method;
	}

}
