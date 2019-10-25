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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils;

import java.util.List;

import flattener.handlers.RawHandler;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import recorders.FileDataRecorder;
import recorders.RecorderException;

/**
 * This class provides some utility methods for recording entities using the bct data layer
 * 
 * @author Fabrizio Pastore
 *
 */
public class TraceMapperUtil {

	public static void recordIoRawTrace(FileDataRecorder recorder,
			IoRawTrace entity) throws MapperException {
		try {
			for ( ProgramPointRaw ppr : entity.getProgramPoints() ){
				List<ProgramPointValue> values = ppr.getProgramPointVariableValues();

				RawHandler[] parametersHandlers = new RawHandler[values.size()];
				for ( int i = 0 ; i < values.size(); ++i ){
					ProgramPointValue value = values.get(i);
					parametersHandlers[i]=new RawHandler("");
					parametersHandlers[i].addNode(value.getVariable().getName(), value.getValue());
				}

				if ( ppr.isEnter() ){

					recorder.recordIoEnter(entity.getMethod().getSignature(), parametersHandlers);

				} else if ( ppr.isExit() ){

					recorder.recordIoExit(entity.getMethod().getSignature(), parametersHandlers);
				} else if ( ppr.isGeneric() ){
					throw new MapperException("Not implemented");
//					recorder.recordIo(entity.getMethod().getSignature(), parametersHandlers);
				} else {
					throw new MapperException("");
				}
			}
		} catch (RecorderException e) {
			throw new MapperException("Cannot record entity. ",e);
		} catch (LoaderException e) {
			throw new MapperException("Cannot record entity. ",e);
		}




	}

}
