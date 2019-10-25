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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;

import java.util.ArrayList;
import java.util.List;

public class CallPointDifferences {
	
	public class Difference {
		private ProgramPointVariable variable;
		private String value1, value2;
		
		public Difference(ProgramPointVariable variable, String value1, String value2) {
			this.variable = variable;
			this.value1 = value1;
			this.value2 = value2;
		}
		
		public ProgramPointVariable getVariable() {
			return variable;
		}
		
		public String getValue1() {
			return value1;
		}
		
		public String getValue2() {
			return value2;
		}
	}
	
	MethodCallPoint cp1, cp2;
	
	public CallPointDifferences(MethodCallPoint cp1, MethodCallPoint cp2) {
		this.cp1 = cp1;
		this.cp2 = cp2;
	}
	
	public List<Difference> getDifferences() {
		List<Difference> differences = new ArrayList<CallPointDifferences.Difference>();
		
		if ( cp1 == null || cp2 == null ){
			return differences;
		}
		
		
		if ( cp1.getCorrespondingProgramPoint() == null ){
			System.err.println("Cannot find corresponding program point for "+cp1.getMethodCallId());
			return differences;
		}
		
		if ( cp2.getCorrespondingProgramPoint() == null ){
			System.err.println("Cannot find corresponding program point for "+cp2.getMethodCallId());
			return differences;
		}
		List<ProgramPointValue> cp1Values = cp1.getCorrespondingProgramPoint().getProgramPointVariableValues();
		List<ProgramPointValue> cp2Values = cp2.getCorrespondingProgramPoint().getProgramPointVariableValues();
		
		for (ProgramPointValue value1 : cp1Values) {
			ProgramPointVariable variable1 = value1.getVariable();
			
			for (ProgramPointValue value2 : cp2Values) {
				ProgramPointVariable variable2 = value2.getVariable();
				if (variable1.getName().equals(variable2.getName()) && !value1.getValue().equals(value2.getValue())) {
					differences.add(new Difference(variable2, value1.getValue(), value2.getValue()));
				}
			}
		}
		
		return differences;
	}
	
}
