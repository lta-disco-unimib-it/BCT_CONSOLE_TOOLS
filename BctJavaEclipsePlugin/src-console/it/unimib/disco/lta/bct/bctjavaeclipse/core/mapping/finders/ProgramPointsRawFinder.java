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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

import util.componentsDeclaration.SystemElement;

public interface ProgramPointsRawFinder {
	
	/**
	 *
	 * Returns the program points associated with the given trace.
	 * 
	 * @param trace
	 * @return
	 * @throws MapperException
	 */
	public List<ProgramPointRaw> find( IoRawTrace trace ) throws MapperException;
	
	/**
	 * Find a list of program points that match the passed parameters.
	 * A parameter set to null is not considered as a discriminant for the search.
	 *  
	 * @param components	Components to which the ProgramPoints must belong to
	 * @param programPointExpression Regular expression that matches the program point name
	 * @param ppType type of the program  point
	 * @param varNameExpr Expression that matches the variable name
	 * @param varTypeExpr Expression that matches the variable type
	 * @param varValueExpr Expression that matches the variable value
	 * @param variableModified Parameter that indicates if modified state of the variable  
	 * @return
	 */
	public List<ProgramPointRaw> find( List<SystemElement> components, 
			String programPointExpression,
			ProgramPoint.Type ppType,
			String	varNameExpr,
			ProgramPointVariable.Type varTypeExpr,
			String varValueExpr,
			ProgramPointValue.ModifiedInfo variableModified
			);
	/**
	 * Finds the program point that correspond to a given MethodCallPoint
	 * @throws MapperException 
	 */
	public ProgramPointRaw find ( MethodCallPoint methodCall ) throws MapperException;
	
	/**
	 * Finds the program point that correspond to a given MethodCallId
	 * @throws MapperException 
	 */
	public ProgramPointRaw find ( String methodSignature, String methodCallId ) throws MapperException;
}
