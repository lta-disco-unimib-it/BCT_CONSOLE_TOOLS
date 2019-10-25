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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import tools.fshellExporter.ModelFilter;
import tools.fshellExporter.ModelInfo;

public class InclusionFilter implements ModelFilter {
	Logger LOGGER = Logger.getLogger(InclusionFilter.class.getCanonicalName());
	
	private List<List<String>> toInclude = new ArrayList<List<String>>();

	@Override
	public String processDataProperty(String dataProperty, ModelInfo modelInfo) {
		for ( List<String> list : toInclude ){
			if ( allIncluded ( list , dataProperty ) ){
				return dataProperty;
			}
		}
		
		LOGGER.fine("Excluding following data property because some of the expected strings were not included: " + dataProperty);
		return null;
	}

	private boolean allIncluded(List<String> list, String dataProperty) {
		for ( String token : list ){
			if ( ! dataProperty.contains(token) ){
				return false;
			}
		}
		return true;
	}

	public void addInclusionToken(String nextToken) {
		LOGGER.fine("New inclusion token : "+nextToken);
		
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer t = new StringTokenizer(nextToken, ":");
		while( t.hasMoreTokens() ){
			list.add(t.nextToken());
		}
		
		toInclude.add(list);
	}

}
