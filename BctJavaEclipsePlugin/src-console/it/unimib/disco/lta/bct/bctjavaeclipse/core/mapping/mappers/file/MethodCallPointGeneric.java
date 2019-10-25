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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;

public class MethodCallPointGeneric extends MethodCallPoint {

	public MethodCallPointGeneric(Method method, String methodCallId,
			ProgramPointRawLoader programPointRawLoader) {
		super(method, methodCallId, programPointRawLoader, Types.GENERIC);
		// TODO Auto-generated constructor stub
	}

	public int getLineNumber(){
		String point = getMethod().getSignature().trim();
		int pos = point.lastIndexOf(':');
		if ( pos == -1 ){
			return -1;
		}

		String intNumber = point.substring(pos+1);

		try {
			return Integer.valueOf(intNumber);
		} catch ( NumberFormatException e ){
			e.printStackTrace();
			return -1;
		}
	}

}
