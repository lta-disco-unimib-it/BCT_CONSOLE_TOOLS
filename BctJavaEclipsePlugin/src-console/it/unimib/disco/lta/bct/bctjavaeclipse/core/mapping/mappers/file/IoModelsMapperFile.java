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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.IoExpressionEnterLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.IoExpressionExitLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.IoModelsMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelsFetchers.IoModelIterator;
import modelsFetchers.ModelsFetcherException;

public class IoModelsMapperFile extends AbstractModelsMapperFile implements
		IoModelsMapper {


	IoModelsMapperFile(ResourceFile resource) {
		super(resource);
	}

//	private Map<Method,IoModel> cache = new HashMap<Method, IoModel>();

	public List<IoModel> getIoModels() {
		ArrayList<IoModel> models = new ArrayList<IoModel>();
		
		for ( String methodSignature : modelsFetcher.getIoModelsNames() ){
			Method method = resource.getFinderFactory().getMethodHandler().getMethod(methodSignature);
			
			models.add(  getIoModel(method)	);
			
		}
		
		return models;
	}



	public IoModel getIoModel(Method method) {
		return new IoModel(
				"-1",
				method,
				new ValueHolder<List<IoExpression>>(new IoExpressionEnterLoader(method, this)), 
				new ValueHolder<List<IoExpression>>(new IoExpressionExitLoader(method, this)));
//		IoModel model = cache.get(method);
//
//		if ( model != null ){
//			model = new IoModel(
//					"-1",
//					method,
//					new ValueHolder<List<IoExpression>>(new IoExpressionEnterLoader(method, this)), 
//					new ValueHolder<List<IoExpression>>(new IoExpressionExitLoader(method, this)));
//			cache.put(method, model);
//		}
//
//		return model;
	}

	public List<IoExpression> getIoExpressionsEnter(Method method) throws MapperException {
		try {
			return getIoExpressions( modelsFetcher.getIoModelIteratorEnter(method.getSignature()) );
		} catch (ModelsFetcherException e) {
			throw new MapperException("Cannot load IO Expressions",e);
		}
	}

	private List<IoExpression> getIoExpressions(IoModelIterator ioModelIterator) {
		List<IoExpression> result = new ArrayList<IoExpression>();
		
		while( ioModelIterator.hasNext() ){
			String expressionString = ioModelIterator.next();
			IoExpression e = new IoExpression(expressionString);
			result.add(e);
		}
		
		return result;
	}

	public List<IoExpression> getIoExpressionsExit(Method method) throws MapperException {
		try {
			return getIoExpressions( modelsFetcher.getIoModelIteratorExit(method.getSignature()) );
		} catch (ModelsFetcherException e) {
			throw new MapperException("Cannot load IO Expressions",e);
		}
	}

	public void update(IoModel entity) throws MapperException {
		try {
			modelsFetchers.IoModel model = new modelsFetchers.IoModel();
			
			for ( IoExpression expression : entity.getExpressionsEnter() ){
				System.out.println("ADDING "+expression);
				model.addPrecondition(expression.toString());
			}
			
			for ( IoExpression expression : entity.getExpressionsExit() ){
				System.out.println("ADDING "+expression);
				model.addPostcondition(expression.toString());
			}
			
			modelsFetcher.addIoModel(entity.getMethod().getSignature(), model);
		} catch (LoaderException e) {
			throw new MapperException("Cannot update",e);
		} catch (ModelsFetcherException e) {
			throw new MapperException("Cannot update",e);
		}
	}




}
