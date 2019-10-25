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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint.Type;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.ProgramPointRawMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

import util.componentsDeclaration.SystemElement;

public class ProgramPointRawMapperDB implements ProgramPointRawMapper {

	public List<ProgramPointRaw> find(IoRawTrace trace) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	public List<ProgramPointRaw> find(List<SystemElement> components, String programPointExpression, Type ppType,
			String varNameExpr, ProgramPointVariable.Type varTypeExpr, String varValueExpr,
			ModifiedInfo variableModified) {
		throw new RuntimeException("Not Implemented");
	}

	public void update(ProgramPointRaw entity) throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public ProgramPointRaw find(MethodCallPoint methodCall)
			throws MapperException {
		throw new RuntimeException("Not Implemented");
	}

	@Override
	public ProgramPointRaw find(String methodSignature, String methodCallId)
			throws MapperException {
		throw new RuntimeException("Not Implemented");
	}
}
