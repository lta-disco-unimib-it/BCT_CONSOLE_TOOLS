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

import java.util.List;

import util.componentsDeclaration.Component;

/**
 * This class include options to monitor test cases executions 
 * 
 * @author Fabrizio Pastore
 *
 */
public class TestCasesMonitoringOptions {
	private boolean monitorTestCases = false;
	private List<Component> TestCasesGroupsToMonitor;
	private boolean traceTestExecution = true;
	private TestCasesRegistryOptions registryOptions = new TestCasesMemoryRegistryOptions();
	
	public TestCasesRegistryOptions getTestCasesRegistryOptions() {
		return registryOptions;
	}
	public void setTestCasesRegistryOptions(TestCasesRegistryOptions registryOptions) {
		this.registryOptions = registryOptions;
	}
	public List<Component> getTestCasesGroupsToMonitor() {
		return TestCasesGroupsToMonitor;
	}
	public void setTestCasesGroupsToMonitor(List<Component> testCasesGroupsToMonitor) {
		TestCasesGroupsToMonitor = testCasesGroupsToMonitor;
	}
	public boolean isMonitorTestCases() {
		return monitorTestCases;
	}
	public void setMonitorTestCases(boolean monitorTestCases) {
		this.monitorTestCases = monitorTestCases;
	}
	public boolean isTraceTestExecution() {
		return traceTestExecution;
	}
	public void setTraceTestExecution(boolean traceTestExecution) {
		this.traceTestExecution = traceTestExecution;
	}
	




}
