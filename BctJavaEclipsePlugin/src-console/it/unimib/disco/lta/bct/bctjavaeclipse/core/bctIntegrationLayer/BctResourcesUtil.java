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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;

import java.util.Properties;

import conf.DBConnectionSettings;

/**
 * This is a util that translates the plugin resources objects into original BCT configuration objects
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public class BctResourcesUtil {

	/**
	 * Returns an InvariantGeneratorSettings object given a Resource
	 * @param resource
	 * @return
	 */
	public static Properties getInvariantGeneratorSettings(Resource resource) {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Returns a DBConnectionSettings object given a resource
	 * 
	 * @param resource
	 * @return
	 */
	public static DBConnectionSettings getDBConnectionSettings(Resource resource) {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Returns a ModelsFectherSettings object taht permits to refer to passed resource data
	 * 
	 * @param resource
	 * @return
	 */
	public static Properties getModelsFetcherSettings(Resource resource) {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Returns the name of the method as it is recorded in the trace
	 * 
	 * @param resource
	 * @param method
	 * @return
	 */
	public static String getRawMethodName ( Resource resource, Method method ){
		//FIXME
		return method.getSignature();
	}
	
	
}
