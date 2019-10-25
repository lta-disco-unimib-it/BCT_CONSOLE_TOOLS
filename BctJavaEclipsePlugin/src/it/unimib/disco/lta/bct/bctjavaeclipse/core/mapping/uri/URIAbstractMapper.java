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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.Trace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.net.URI;
import java.util.List;

public abstract class URIAbstractMapper {
	
	protected Resource resource;
	
	public URIAbstractMapper(Resource resource) {
		this.resource = resource;
	}
	
	public abstract Method getMethod(URI uri);
	public abstract IoModel getIoModel(URI uri) throws MapperException;
	public abstract Trace getIoTrace(URI uri) throws MapperException;
	public abstract InteractionNormalizedTrace getInteractionNormalizedTrace(URI uri) throws MapperException;
	public abstract InteractionRawTrace getInteractionRawTrace(URI uri) throws MapperException;
	
	public abstract List<IoModel> getIoModels(URI uri) throws MapperException;
	public abstract List<FSAModel> getFSAModels(URI uri) throws MapperException;
	public abstract List<Trace> getIOTraces(URI uri) throws MapperException;
	public abstract List<InteractionNormalizedTrace> getInteractionNormalizedTraces(URI uri) throws MapperException;
	public abstract List<InteractionRawTrace> getInteractionRawTraces(URI uri) throws MapperException;
	
	
	public abstract void saveIoModel(IoModel model) throws MapperException;
	public abstract void saveIoTrace(Trace trace) throws MapperException;
}
