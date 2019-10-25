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

import java.util.List;

import flattener.utilities.FieldFilter;
import flattener.utilities.FieldFilterVoid;


public class FlattenerOptions {

	
	private FieldFilter fieldsFilter = new FieldFilterVoid();
	
	private boolean smashAggregation;
	private int maxDepth;
	private String fieldRetriever;
	private List <String> classToIgnore;
	String id=null;

	private boolean skipObjectsAlreadyVisited;
	
	public FlattenerOptions(boolean smashAggregation,int maxDepth, String fieldRetriever,List<String> classToIgnore)
	{
		this.fieldRetriever=fieldRetriever;
		this.smashAggregation=smashAggregation;
		this.maxDepth=maxDepth;
		this.classToIgnore=classToIgnore;
		this.fieldRetriever = fieldRetriever;
	}
	
	public FlattenerOptions(){}
	
	public FlattenerOptions(FlattenerOptions flattenerOptions) {
		this(flattenerOptions.smashAggregation,flattenerOptions.maxDepth,flattenerOptions.fieldRetriever,flattenerOptions.getClassToIgnore());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldRetriever() {
		return fieldRetriever;
	}

	public void setFieldRetriever(String fieldRetriver) {
		this.fieldRetriever = fieldRetriver;
	}

	public List<String> getClassToIgnore() {
		return classToIgnore;
	}

	public void setClassToIgnore(List<String> classToIgnore) {
		this.classToIgnore = classToIgnore;
	}

	public FieldFilter getFieldsFilter() {
		return fieldsFilter;
	}

	public void setFieldsFilter(FieldFilter fieldsFilter) {
		this.fieldsFilter = fieldsFilter;
	}

	public boolean isSmashAggregation() {
		return smashAggregation;
	}

	public void setSmashAggregation(boolean smashAggregation) {
		this.smashAggregation = smashAggregation;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public boolean isSkipObjectsAlreadyVisited() {
		return skipObjectsAlreadyVisited;
	}

	public void setSkipObjectsAlreadyVisited(boolean skipObjectsAlreadyVisited) {
		this.skipObjectsAlreadyVisited = skipObjectsAlreadyVisited;
	}
	
	
}
