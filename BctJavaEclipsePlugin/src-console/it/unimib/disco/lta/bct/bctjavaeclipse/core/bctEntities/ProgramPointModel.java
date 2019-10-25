package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent the model of a program point, the list of the variables it can cointain.
 * 
 * @author Fabrizio Pastore
 *
 */
public class ProgramPointModel {
	private Set<ProgramPointVariable> variables;
	
	public ProgramPointModel( Set<ProgramPointVariable> variables){
		variables = new HashSet<ProgramPointVariable>(variables);
	}

	public Set<ProgramPointVariable> getVariables() {
		return variables;
	}
}
