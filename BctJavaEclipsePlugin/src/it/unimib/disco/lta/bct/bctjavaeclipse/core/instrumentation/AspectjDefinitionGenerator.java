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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.instrumentation;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.instrumentation.RuleToPointcutGenerator.MethodVisibility;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.instrumentation.RuleToPointcutGenerator.PointcutType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.ComponentsDefinitionFactory;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;
import conf.EnvironmentalSetter;

public class AspectjDefinitionGenerator {

	public enum Phase { DataRecording, Checking }

	private Phase phase;
	
	/**
	 * Create an aspects definition that generates BCT aspects for the given phase
	 * 
	 * @param phase
	 */
	public AspectjDefinitionGenerator ( Phase phase ){
		this.phase = phase;
	}
	
	/**
	 * 
	 * @param destinationPath e.g. aop.xml
	 * @param ioPointcuts
	 * @param interactionPointcuts
	 * @throws IOException
	 */
	public void createDefinitionFile( File destinationPath, String aspectPrefix,
			MonitoringConfiguration mc, Phase phase )
			throws IOException {
		PrintWriter definition = new PrintWriter(new BufferedWriter(
				new FileWriter(
						destinationPath )));
		// HEADER
		definition.println("<aspectj>");
		definition.println("<aspects>");
		
		
		ComponentsConfiguration componentsConfiguration = mc.getComponentsConfiguration();
		
		Collection<Component> components = componentsConfiguration.getComponents();
		
		
		
		int i = 0;
		for ( Component component : components ){
			generateAspectForComponent( definition, component, i++, mc );
		}
		
		
		// FOOTER
		definition.println("</aspectj>");
		definition.println("</aspects>");
		definition.close();
		
		

	}


	String PC_NAME_EXEC = "exec";
	String PC_NAME_CONSTR = "constr";
	String PC_NAME_CALL = "call";
	String PC_NAME_CONSTR_CALL = "constructorCall";
	String PC_NAME_STATIC_INIT = "staticInitialization";


	private void generateAspectForComponent(PrintWriter definition, Component component, int componentNumber, MonitoringConfiguration mc) {
		// INTERACTIONLOGGINGASPECT
		definition.println("<concrete-aspect name=\""+getAspectClassName(component,mc)+"\" extends=\""+getAbstractAspectClassName(mc)+"\">");
	
		//
		//Component enter pointcut
		//
		String componentEnterPC = createComponentEnterPointcut(component);
	
		definition.print("<pointcut name=\""+PC_NAME_EXEC+componentNumber+"\" expression=\""+componentEnterPC+"\"/>");
		
		//
		//Constructor pointcut
		//
		definition.print("<pointcut name=\""+PC_NAME_CONSTR+componentNumber+"\" expression="+createConstructorPointcut(component)+"\"/>");
		
		
		
		//
		//Call pointcut
		//
		definition.print("<pointcut name=\""+PC_NAME_CALL+componentNumber+"\" expression="+createCallPointcut(component,mc)+"\"/>");
		
		
		//
		//Constructor call pointcut
		//
		definition.print("<pointcut name=\""+PC_NAME_CONSTR_CALL+componentNumber+"\" expression="+createConstructorCallPointcut(component,mc)+"\"/>");
		
		
		//
		//Static initialization pointcut
		//
		definition.print("<pointcut name=\""+PC_NAME_STATIC_INIT+componentNumber+"\" expression="+createStaticInitializationPointcut(component)+"\"/>");
		

		definition.print("<pointcut name=\"componentEnterScope\" expression=\""+PC_NAME_EXEC+componentNumber+"\"/>");
		definition.print("<pointcut name=\"callScope\" expression=\""+PC_NAME_CALL+componentNumber+"()\"/>");
		definition.print("<pointcut name=\"componentConstructorsScope\" expression=\""+PC_NAME_STATIC_INIT+componentNumber+"() || "+PC_NAME_CONSTR+componentNumber+"()\"/>");
		definition.print("<pointcut name=\"constructorsScope\" expression=\""+PC_NAME_CONSTR_CALL+componentNumber+"()\"/>");
		
		definition.print("</concrete-aspect>");
	}

	private String createStaticInitializationPointcut(Component component) {
		
		RuleToTypePointcutGenerator generator = new RuleToTypePointcutGenerator(false, MethodVisibility.Protected, PointcutType.staticinitialization );
		return generator.createPointcut(component);
		
	}

	private String createCallPointcut(Component component,
			MonitoringConfiguration mc) {
		StringBuffer result = new StringBuffer();
		
		//withincode( public * test..*(..) ) AND ( call ( * *(..) )
		
		RuleToMethodPointcutGenerator generator = new RuleToMethodPointcutGenerator(false, MethodVisibility.Protected, PointcutType.withincode );
		result.append( generator.createPointcut(component) );
		
		result.append( " AND ( " );
		
		//Exclude calls to monitored components
		
		for ( Component monitoredComponent : mc.getComponentsConfiguration().getComponents() ){
			RuleToMethodPointcutGenerator generatorExclude = new RuleToMethodPointcutGenerator(true, MethodVisibility.Protected, PointcutType.call );
			result.append( generatorExclude.createPointcut(monitoredComponent) );
		}
		
		Collection<CallFilter> callFilters = mc.getComponentsConfiguration().getCallFilters();
		for ( CallFilter  cf : callFilters ){
			RuleToMethodPointcutGenerator generatorExclude = new RuleToMethodPointcutGenerator(false, MethodVisibility.Protected, PointcutType.call );
			result.append( generatorExclude.createPointcut(cf.getCallToRules()) );
		}
		
		result.append( " ) " );
		
		return result.toString();
	}
	
	
	private String createConstructorCallPointcut(Component component,
			MonitoringConfiguration mc) {
		StringBuffer result = new StringBuffer();
		
		//withincode( public * test..*(..) ) AND ( call ( * *(..) )
		
		RuleToMethodPointcutGenerator generator = new RuleToMethodPointcutGenerator(false, MethodVisibility.Protected, PointcutType.withincode );
		result.append( generator.createPointcut(component) );
		
		result.append( " AND ( " );
		
		//Exclude calls to monitored components
		
		for ( Component monitoredComponent : mc.getComponentsConfiguration().getComponents() ){
			RuleToConstructorPointcutGenerator generatorExclude = new RuleToConstructorPointcutGenerator(true, MethodVisibility.Protected, PointcutType.call );
			result.append( generatorExclude.createPointcut(component) );
		}
		
		result.append( " ) " );
		
		return result.toString();
	}
	
	

	private String createConstructorPointcut(Component component) {
		RuleToConstructorPointcutGenerator generator = new RuleToConstructorPointcutGenerator(false, MethodVisibility.Protected, PointcutType.execution );
		return generator.createPointcut(component);
	}

	private String createComponentEnterPointcut(Component component) {
		RuleToMethodPointcutGenerator generator = new RuleToMethodPointcutGenerator(false, MethodVisibility.Protected, PointcutType.execution );
		return generator.createPointcut(component);
	}


	private String getAbstractAspectClassName(MonitoringConfiguration mc) {
		return mc.getConfigurationName()+"Aspect";
	}

	private String getAspectClassName(Component component, MonitoringConfiguration mc) {
		TestCasesMonitoringOptions tcOptions = mc.getTestCasesMonitoringOptions();
		
		String phaseId;
		if ( phase == Phase.DataRecording ){
			phaseId = "LA";
		} else 		if ( phase == Phase.Checking ){
			phaseId = "CA";
		} else {
			phaseId = "-UNDEFINED";
		}
		
		if ( tcOptions != null ){
			if ( tcOptions.isTraceTestExecution() ){
				return "BctTCIntegrated"+phaseId+"_"+component.getName();
			}
		}
		
		return "BctIntegrated"+phaseId+"_"+component.getName();
	}


}