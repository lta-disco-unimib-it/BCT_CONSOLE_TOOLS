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
