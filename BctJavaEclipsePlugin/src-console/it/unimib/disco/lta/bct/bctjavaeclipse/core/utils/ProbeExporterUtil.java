package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;

public class ProbeExporterUtil {

	/**
	 * This method create a probe script from a ComponentConfiguration.
	 * 
	 * Pay attention the probe script is without the WITHIN rules, so it must be used only with the wrapped probeinstrumenter
	 * 
	 *  FIXME: create the extended probe 
	 *  
	 * @param dest
	 * @param cc
	 */
	public static void createDataRecordingProbeScript(BufferedWriter bw, ComponentsConfiguration cc) {
		createProbeScript("bctLP",bw,cc,false, false);
	}
	
	public static void createDataRecordingProbeScriptMonitorTests(BufferedWriter bw, ComponentsConfiguration cc) {
		createProbeScript("bctTCLP",bw,cc,true, false);
	}
	
	/**
	 * This method create a runtime checking probe script from a ComponentConfiguration.
	 * 
	 * Pay attention the probe script is without the WITHIN rules, so it must be used only with the wrapped probeinstrumenter
	 * 
	 * @param dest
	 * @param cc
	 */
	public static void createRuntimeCheckingProbeScript(BufferedWriter bw, ComponentsConfiguration cc) {
		createProbeScript("bctCP",bw,cc,false, false);
	}
	
	public static void createDataRecordingProbeScriptMonitorInternalAndTests(BufferedWriter bw,
			ComponentsConfiguration cc) {
		createProbeScript("bctTCLPAll",bw,cc,true, false);
		createMonitorIncomingOnlyProbeScript(bw,"BctExceptionsMonitorProbe",cc.getComponents(),true);
	}
	
	public static void createDataRecordingProbeScriptMonitorInternal(BufferedWriter bw,
			ComponentsConfiguration cc) {
		createProbeScript("bctLPAll",bw,cc,false, false);
	}
	
	public static void createRuntimeCheckingProbeScriptMonitorInternal(
			BufferedWriter bw, ComponentsConfiguration cc) {
		createProbeScript("bctCPAll",bw,cc,false, false);
	}
	
	
	
	private static void createProbeScript(String bctProbeName, BufferedWriter bw,
			ComponentsConfiguration cc, boolean monitorCalledObject, boolean monitorExceptionObject) {
		try {
			
			
			
			for( Component c : cc.getComponents() ){
				writeComponentPart(bctProbeName,bw,cc,c,monitorCalledObject, monitorExceptionObject);
			}

			writeProbeScriptFooter(bctProbeName,cc,bw);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeProbeScriptFooter(String bctProbeName, ComponentsConfiguration cc, BufferedWriter bw) throws IOException {
		Collection<CallFilter> callFilters = cc.getCallFilters();
		
		if ( callFilters == null ){
			return ;
		}
		
		for ( CallFilter callFilter : callFilters ){
			if ( callFilter.isCallFilterGlobal() ){
				CallFilterGlobal cfg = (CallFilterGlobal) callFilter;
				List<MatchingRule> rules = cfg.getCallToRules();
				for ( MatchingRule rule : rules ){
					writeFilter(bw, rule);
				}
			}
		}
	}

	

	private static void writeComponentPart(String bctProbeName,
			BufferedWriter bw, ComponentsConfiguration cc, Component c, boolean monitorCalledObject, boolean monitorException) throws IOException {
		
		writeProbeHeader(bctProbeName,bw);
		
		for( MatchingRule r : c.getRules() ){
			writeRule(bw,r);
			
		}
		
		String exceptionObjectParameter = "";
		String exceptionObjectDefinition = "";
		
		if ( monitorException ){
			exceptionObjectParameter="Ljava/lang/Throwable;";
			exceptionObjectDefinition="exceptionObject,";
		}
		
		String calledObjectParameter = "";
		String calledObjectDefinition = "";
		
		if ( monitorCalledObject ){
			calledObjectParameter="Ljava/lang/Object;";
			calledObjectDefinition="thisObject,";
		}
			
		bw.write("RULE * * * * exclude\n");
		bw.write("REF ONENTRY "+bctProbeName+"_probe$Probe_0 _entry (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;"+calledObjectParameter+"[Ljava/lang/Object;)V className,methodName,methodSig,"+calledObjectDefinition+"args\n");
		bw.write("REF ONEXIT "+bctProbeName+"_probe$Probe_0 _exit ("+exceptionObjectParameter+"Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;"+calledObjectParameter+"[Ljava/lang/Object;)V "+exceptionObjectDefinition+"returnedObject,className,methodName,methodSig,"+calledObjectDefinition+"args\n");
	}

	private static void writeFilter(BufferedWriter bw, MatchingRule r) throws IOException {
		writeRule("FILTER", bw, r);
	}
	
	private static void writeRule(BufferedWriter bw, MatchingRule r) throws IOException {
		writeRule("RULE", bw, r);
	}
	
	private static void writeRule(String type,BufferedWriter bw, MatchingRule r) throws IOException {
		String action;
		if ( r instanceof MatchingRuleExclude ){
			action = "exclude";
		} else {
			action = "include";
		}
		bw.write(type+" "+r.getPackageExpr().replace(".*", "*")+" "+r.getClassExpr().replace(".*", "*")+" "+r.getMethodExpr().replace(".*", "*")+" * "+action+"\n");
	}

	private static void writeProbeHeader(String bctProbeName, BufferedWriter bw) throws IOException {
		bw.write("REM "+bctProbeName+"\n");
		bw.write("PROBE\n");
	}
	
	/**
	 * Create the probe script for automatically monitoring user actions
	 * 
	 * @param bw
	 * @param actionsMonitoringOptions
	 */
	public static void createMonitorActionsProbeScript(BufferedWriter bw,
			ActionsMonitoringOptions actionsMonitoringOptions) {
		
		List<Component> actionsToMonitor = actionsMonitoringOptions.getActionsGroupsToMonitor();
		createMonitorIncomingOnlyProbeScript(bw, "BctActionsMonitorProbe",actionsToMonitor,false);
	}
	
	/**
	 * Creates a probe that monitor just the execution of incoming calls
	 *  
	 * @param bw
	 * @param probeName name of the probe script corresponding (without final _probe)
	 * @param componentsToMonitor
	 */
	private static void createMonitorIncomingOnlyProbeScript(BufferedWriter bw, 
			String probeName,
			Collection<Component> componentsToMonitor, boolean monitorExceptions) {
		try {
			writeProbeHeader(probeName, bw);
			
			
			
			for ( Component c : componentsToMonitor ){
				for ( MatchingRule rule : c.getRules()){
					writeRule(bw, rule);
				}
			} 
			
			bw.write("RULE * * * * exclude\n");
			bw.write("REF ONENTRY "+probeName+"_probe$Probe_0 _entry (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V className,methodName,methodSig\n");
			if ( monitorExceptions ){
				bw.write("REF ONEXIT "+probeName+"_probe$Probe_0 _exit (Ljava/lang/Throwable;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V exceptionObject,className,methodName,methodSig\n");
			} else {
				bw.write("REF ONEXIT "+probeName+"_probe$Probe_0 _exit (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V className,methodName,methodSig\n");
			}
		
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Create the probe script for automatically monitoring test cases
	 * 
	 * @param bw
	 * @param actionsMonitoringOptions
	 */
	public static void createMonitorTestCasesProbeScript(BufferedWriter bw,
			TestCasesMonitoringOptions testCasesMonitoringOptions) {
		List<Component> tcToMonitor = testCasesMonitoringOptions.getTestCasesGroupsToMonitor();
		createMonitorIncomingOnlyProbeScript(bw, "BctTestCasesMonitorProbe",tcToMonitor,true);
	}



}
