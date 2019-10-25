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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;


/**
 * This class represent the value assumed by a program point variable.
 * 
 * @author Fabrizio Pastore
 *
 */
public class ProgramPointValue  {
	
	public static enum ModifiedInfo {NotAssigned,Assigned,Nonsensical};
	
	private ProgramPointVariable variable;
	private String value;
	private ModifiedInfo modified;
	
	/**
	 * Constructor takes as input the variable it refers to, its value and the modified info.
	 * Modified info refers to the Daikon file format, it is NotAssigned (value 0 in Daikon files) if the variable was never assigned 
	 * since the last time the program pointwas reached,
	 * assigned (value 1 in Daion files) if it was assigned, nonsensical (value 2 in daikon files if the value is not present).
	 * 
	 * @param ppv
	 * @param value
	 * @param modified
	 */
	public ProgramPointValue(ProgramPointVariable ppv, String value, ModifiedInfo modified) {
		this.variable = ppv;
		this.value = value;
		this.modified = modified;
	}
	
	/**
	 * Return the variable value
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the program point variable this value is referred to. 
	 * @return
	 */
	public ProgramPointVariable getVariable() {
		return variable;
	}

	public ModifiedInfo getModified() {
		return modified;
	}

	public void setVariable(ProgramPointVariable variable) {
		this.variable = variable;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setModified(ModifiedInfo modified) {
		this.modified = modified;
	}
}
