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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public class VARTRegressionConfiguration {
	public enum ModelChecker { CBMC, EvolCheck };
	
	private boolean skipMonitoring;
	private boolean skipInference;
	private boolean skipIntendedChangesIdentification;
	private boolean skipChecking;
	
	private boolean useMakefileForMonitoring = true;
	private boolean usingRandomTests;
	private boolean validateChangedFunctionsOnly;
	private boolean optimizeSpeed = true;
	private int unwind = 5;
	
	private ModelChecker modelChecker = ModelChecker.CBMC;
	private String testTarget = "test";
	private String modelCheckerExecutable;
	private String compilerExecutable;
	private String variablesToCheck;

	private boolean unitVerification = true;
	private boolean exportLines = true;
	private boolean checkAssertionsCoverage = true;
	private boolean useSimmetricIntegers = false;

	public VARTRegressionConfiguration(){
		
	}
	
	public void setUseSimmetricIntegers(boolean useSimmetricIntegers) {
		this.useSimmetricIntegers = useSimmetricIntegers;
	}

	public void setCheckAssertionsCoverage(boolean checkAssertionsCoverage) {
		this.checkAssertionsCoverage = checkAssertionsCoverage;
	}

	public boolean isUnitVerification() {
		return unitVerification;
	}

	public void setUnitVerification(boolean unitVerification) {
		this.unitVerification = unitVerification;
	}

	public boolean getExportLines() {
		return exportLines;
	}

	public void setExportLines(boolean exportLines) {
		this.exportLines = exportLines;
	}
	
	public String getVariablesToCheck() {
		return variablesToCheck;
	}

	public void setVariablesToCheck(String variablesToCheck) {
		this.variablesToCheck = variablesToCheck;
	}

	public boolean isUseMakefileForMonitoring() {
		return useMakefileForMonitoring;
	}

	public void setUseMakefileForMonitoring(boolean useMakefileForMonitoring) {
		this.useMakefileForMonitoring = useMakefileForMonitoring;
	}

	public boolean isValidateChangedFunctionsOnly() {
		return validateChangedFunctionsOnly;
	}

	public void setValidateChangedFunctionsOnly(boolean validateChangedFunctionsOnly) {
		this.validateChangedFunctionsOnly = validateChangedFunctionsOnly;
	}

	public ModelChecker getModelChecker() {
		return modelChecker;
	}

	public void setModelChecker(ModelChecker modelChecker) {
		this.modelChecker = modelChecker;
	}
	
	public String getModelCheckerExecutable() {
		return modelCheckerExecutable;
	}

	public void setModelCheckerExecutable(String modelCheckerExecutable) {
		this.modelCheckerExecutable = modelCheckerExecutable;
	}

	public String getCompilerExecutable() {
		return compilerExecutable;
	}

	public void setCompilerExecutable(String compilerExecutable) {
		this.compilerExecutable = compilerExecutable;
	}
	
	public String getTestTarget() {
		return testTarget;
	}

	public void setTestTarget(String testTarget) {
		this.testTarget = testTarget;
	}
	
	public boolean isUsingRandomTests() {
		return usingRandomTests;
	}

	public void setUsingRandomTests(boolean monitorUpgrade) {
		this.usingRandomTests = monitorUpgrade;
	}

	public boolean isOptimizeSpeed() {
		return optimizeSpeed;
	}

	public void setOptimizeSpeed(boolean optimizeSpeed) {
		this.optimizeSpeed = optimizeSpeed;
	}

	public int getUnwind() {
		return unwind;
	}

	public void setUnwind(int unwind) {
		this.unwind = unwind;
	}

	public void addVariableToCheck(String buf) {
		if ( variablesToCheck == null ){
			variablesToCheck = buf;
		} else {
			variablesToCheck+="||"+buf;
		}
	}

	public boolean isCheckAssertionsCoverage() {
		return checkAssertionsCoverage;
	}

	public boolean isSkipMonitoring() {
		return skipMonitoring;
	}

	public void setSkipMonitoring(boolean skipMonitoring) {
		this.skipMonitoring = skipMonitoring;
	}

	public boolean isSkipInference() {
		return skipInference;
	}

	public void setSkipInference(boolean skipInference) {
		this.skipInference = skipInference;
	}

	public boolean isSkipIntendedChangesIdentification() {
		return skipIntendedChangesIdentification;
	}

	public void setSkipIntendedChangesIdentification(
			boolean skipIntendedChangesIdentification) {
		this.skipIntendedChangesIdentification = skipIntendedChangesIdentification;
	}

	public boolean isSkipChecking() {
		return skipChecking;
	}

	public void setSkipChecking(boolean skipChecking) {
		this.skipChecking = skipChecking;
	}

	public boolean isUseSimmetricIntegers() {
		return useSimmetricIntegers;
	}
	

}
