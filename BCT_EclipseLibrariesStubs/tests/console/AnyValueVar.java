package console;

import tools.violationsAnalyzer.ViolationsUtil.VariableData;

public class AnyValueVar extends VariableData {

	public AnyValueVar(String variableName) {
		super(variableName, null);
	}

	@Override
	public boolean equals(Object arg0) {
		return true;
	}

	
	
}
