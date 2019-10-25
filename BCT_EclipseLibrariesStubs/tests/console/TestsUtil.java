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
