package console;

import java.util.List;

import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

import modelsViolations.BctModelViolation;

public class TestsUtil {


	public static void removeAnomaly(List<BctModelViolation> anomalies,
			String violatedFunction, String violation) {

		for(int i = anomalies.size()-1; i >=0 ; i-- ){
			BctModelViolation anomaly = anomalies.get(i);
			//			System.out.println(":: "+anomaly.getViolatedModel()+ " "+ anomaly.getViolation());

			if ( anomaly.getViolatedModel().equals(violatedFunction) && 
					anomaly.getViolation().equals(violation) ){
				anomalies.remove(i);
			}
		}
	}

	public static void removeVariableDataFromAnomaly(
			List<ViolationData> actualViolations, String functionName,
			String model, String variableToRemove) {

		for(ViolationData anomaly : actualViolations ){


			if ( anomaly.getFunctionName().equals(functionName) && 
					anomaly.getModel().equals(model) ){
				List<VariableData> violatedVars = anomaly.getViolatedVariables();
				for( int i = violatedVars.size()-1; i >= 0; i-- ){
					VariableData violatedVar = violatedVars.get(i);
					if ( violatedVar.getVariableName().equals(variableToRemove) ){
						//						violatedVars.set(i, new AnyValueVar( variableToRemove ) );
						violatedVars.remove(i);
					}
				}
			}
		}
	}

	public static void updateModelNames(String modelPattern, String newModel, String functionName,
			List<ViolationData> actualViolations) {
		for(ViolationData anomaly : actualViolations ){
		
			if ( anomaly.getFunctionName().equals(functionName) && 
					anomaly.getModel().matches(modelPattern) ){
				anomaly.setModel( newModel );
			}
		
		}
		
	}


}
