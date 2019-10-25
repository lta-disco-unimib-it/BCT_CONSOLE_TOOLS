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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MethodCallSequence {
	List<MethodCall> methodCalls = new ArrayList<MethodCall>();
	
	
	
	MethodCallSequence( Collection<MethodCall> methodCalls ){
		this.methodCalls.addAll(methodCalls);
	}

	public MethodCallSequence() {
		// TODO Auto-generated constructor stub
	}

	public void add(int index, MethodCall element) {
		methodCalls.add(index, element);
	}

	public boolean add(MethodCall o) {
		return methodCalls.add(o);
	}

	public MethodCall get(int index) {
		return methodCalls.get(index);
	}

	public boolean isEmpty() {
		return methodCalls.isEmpty();
	}

	public Iterator<MethodCall> iterator() {
		return methodCalls.iterator();
	}
	
}
