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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import java.util.Properties;

public class InferModelWizardResult {

	Properties invariantGeneratorOptions;
	Properties inferenceEngineOptions;
	private boolean canceled;
	private boolean keepExistingModels;
	private boolean skipFailingTests;
	private boolean skipFailingActions;
	private boolean skipFailingProcesses;
	
	public boolean isKeepExistingModels() {
		return keepExistingModels;
	}
	public Properties getInvariantGeneratorOptions() {
		return invariantGeneratorOptions;
	}
	public void setInvariantGeneratorOptions(Properties invarinatGeneratorOptions) {
		this.invariantGeneratorOptions = invarinatGeneratorOptions;
	}
	public Properties getInferenceEngineOptions() {
		return inferenceEngineOptions;
	}
	public void setInferenceEngineOptions(Properties inferenceEngineOptions) {
		this.inferenceEngineOptions = inferenceEngineOptions;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	public boolean getCanceled() {
		return this.canceled;
	}
	public void setKeepExistingModels(boolean keepExistingModels) {
		this.keepExistingModels = keepExistingModels;
	}
	public boolean getSkipFailingTests() {
		return skipFailingTests;
	}
	public void setSkipFailingTests(boolean skipFailingTests) {
		this.skipFailingTests = skipFailingTests;
	}
	public boolean getSkipFailingActions() {
		return skipFailingActions;
	}
	public void setSkipFailingActions(boolean skipFailingActions) {
		this.skipFailingActions = skipFailingActions;
	}
	public boolean getSkipFailingProcesses() {
		return skipFailingProcesses;
	}
	public void setSkipFailingProcesses(boolean skipFailingProcesses) {
		this.skipFailingProcesses = skipFailingProcesses;
	}
}
