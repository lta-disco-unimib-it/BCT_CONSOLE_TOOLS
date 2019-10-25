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
