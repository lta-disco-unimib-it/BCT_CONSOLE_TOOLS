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
