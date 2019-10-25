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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import modelsFetchers.ModelsFetcherFactoy;
import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolatedModelsTypes;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;

import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

public class ViolationPropertySource implements IPropertySource {
	private IPropertyDescriptor[] IOModelViolationPropertyDescriptors;
	private IPropertyDescriptor[] FSAModelViolationPropertyDescriptors;
	private BctModelViolation violation;
	private MonitoringConfiguration mc;
	private boolean removeJavaInspectors;

	private static final String PROPERTY_ID = "id";
	private static final String PROPERTY_VIOLATED_MODEL = "Violated Model";
	private static final String PROPERTY_VIOLATION = "Violation";
	private static final String PROPERTY_CREATION_TIME = "Creation time";
	private static final String PROPERTY_PID = "Process ID";
	private static final String PROPERTY_THREAD_ID = "Thread ID";
	private static final String PROPERTY_VIOLATION_TYPE = "Violation type";
	private static final String PROPERTY_CURRENT_ACTIONS = "Current actions";
	private static final String PROPERTY_CURRENT_TESTS = "Current tests";
	private static final String PROPERTY_STACK_TRACE = "Stack trace";
	private static final String PROPERTY_PARAMETERS = "Parameters";
	private static final String PROPERTY_VIOLATION_STATES_NAMES = "Violation states names";
	private static final String PROPERTY_VIOLATION_END_STATE = "Violation end state";
	private static final String PROPERTY_VIOLATED_MODEL_TYPE = "Violated model type";
	private static final String PROPERTY_NON_MATCHING_EVENTS = "Non-matching events";
	private static final String INVALID_PARAMETERS = "Invalid Parameters";
	private static String PROPERTY_EXPECTED_EVENTS = "Expected Events";
	private static String PROPERTY_PREVIOUS_EVENT = "Previous Event";

	public ViolationPropertySource(BctModelViolation violation, MonitoringConfiguration mc) {
		super();
		this.violation = violation;
		this.mc = mc;
		if ( mc != null && ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config || mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ) ){
			removeJavaInspectors = true;
			
			PROPERTY_EXPECTED_EVENTS = "_Expected Instruction";
			PROPERTY_PREVIOUS_EVENT = "_Previous Instruction";
		}
	}

	public Object getEditableValue() {
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] propertyDescriptor = new IPropertyDescriptor[] {};
		if (violation != null) {
			ViolatedModelsTypes violatedModelType = violation.getViolatedModelType();
			
			if(violatedModelType.equals(ViolatedModelsTypes.IO)) {
				if (IOModelViolationPropertyDescriptors == null) {
					PropertyDescriptor idDesc = new PropertyDescriptor(PROPERTY_ID, "ID");
					PropertyDescriptor violatedModelDesc = new PropertyDescriptor(PROPERTY_VIOLATED_MODEL, "Violated model");
					PropertyDescriptor violationDesc = new PropertyDescriptor(PROPERTY_VIOLATION, "Violation");
					PropertyDescriptor violationTypeDesc = new PropertyDescriptor(PROPERTY_VIOLATION_TYPE, "Violation type");
					PropertyDescriptor creationTimeDesc = new PropertyDescriptor(PROPERTY_CREATION_TIME, "Creation time");
					PropertyDescriptor currentActionsDesc = new PropertyDescriptor(PROPERTY_CURRENT_ACTIONS, "Current actions");
					PropertyDescriptor currentTestsDesc = new PropertyDescriptor(PROPERTY_CURRENT_TESTS, "Current tests");
					PropertyDescriptor stackTraceDesc = new PropertyDescriptor(PROPERTY_STACK_TRACE, "Stack trace");
					PropertyDescriptor pidDesc = new PropertyDescriptor(PROPERTY_PID, "Process ID");
					PropertyDescriptor threadIdDesc = new PropertyDescriptor(PROPERTY_THREAD_ID, "Thread ID");
					PropertyDescriptor violatedModelTypeDesc = new PropertyDescriptor(PROPERTY_VIOLATED_MODEL_TYPE, "Violated model type");
					PropertyDescriptor parametersDesc = new PropertyDescriptor(PROPERTY_PARAMETERS, "Parameters");
					PropertyDescriptor invalidParametersDesc = new PropertyDescriptor(INVALID_PARAMETERS, "Invalid Parameters");
					
					IOModelViolationPropertyDescriptors = new IPropertyDescriptor[] { idDesc, violatedModelDesc, violationDesc,
							creationTimeDesc, pidDesc, threadIdDesc, violationTypeDesc, currentActionsDesc, currentTestsDesc,
							stackTraceDesc, violatedModelTypeDesc, parametersDesc, invalidParametersDesc };
				}
				propertyDescriptor = IOModelViolationPropertyDescriptors;
			} else if(violatedModelType.equals(ViolatedModelsTypes.FSA)) {
				if(FSAModelViolationPropertyDescriptors == null) {
					PropertyDescriptor idDesc = new PropertyDescriptor(PROPERTY_ID, "ID");
					PropertyDescriptor violatedModelDesc = new PropertyDescriptor(PROPERTY_VIOLATED_MODEL, "Violated model");
					//PropertyDescriptor violationDesc = new PropertyDescriptor(PROPERTY_VIOLATION, "Violation");
					PropertyDescriptor violationDesc = new PropertyDescriptor(PROPERTY_NON_MATCHING_EVENTS, "Non matching Events");
					PropertyDescriptor expectedDesc = new PropertyDescriptor(PROPERTY_EXPECTED_EVENTS, PROPERTY_EXPECTED_EVENTS);
					PropertyDescriptor previousEventDesc = new PropertyDescriptor(PROPERTY_PREVIOUS_EVENT, PROPERTY_PREVIOUS_EVENT);
					PropertyDescriptor violationTypeDesc = new PropertyDescriptor(PROPERTY_VIOLATION_TYPE, "Violation type");
					PropertyDescriptor creationTimeDesc = new PropertyDescriptor(PROPERTY_CREATION_TIME, "Creation time");
					PropertyDescriptor currentActionsDesc = new PropertyDescriptor(PROPERTY_CURRENT_ACTIONS, "Current actions");
					PropertyDescriptor currentTestsDesc = new PropertyDescriptor(PROPERTY_CURRENT_TESTS, "Current tests");
					PropertyDescriptor stackTraceDesc = new PropertyDescriptor(PROPERTY_STACK_TRACE, "Stack trace");
					PropertyDescriptor pidDesc = new PropertyDescriptor(PROPERTY_PID, "Process ID");
					PropertyDescriptor threadIdDesc = new PropertyDescriptor(PROPERTY_THREAD_ID, "Thread ID");
					PropertyDescriptor violatedModelTypeDesc = new PropertyDescriptor(PROPERTY_VIOLATED_MODEL_TYPE, "Violated model type");
					PropertyDescriptor violationStatesNamesDesc = new PropertyDescriptor(PROPERTY_VIOLATION_STATES_NAMES, "Violation states names");
					PropertyDescriptor violationEndState = new PropertyDescriptor(PROPERTY_VIOLATION_END_STATE, "Violation end state");
					
					FSAModelViolationPropertyDescriptors = new IPropertyDescriptor[] { idDesc, violatedModelDesc, violationDesc, expectedDesc,previousEventDesc,
							creationTimeDesc, pidDesc, threadIdDesc, violationTypeDesc, currentActionsDesc, currentTestsDesc,
							stackTraceDesc, violatedModelTypeDesc, violationStatesNamesDesc, violationEndState };
				}
				propertyDescriptor = FSAModelViolationPropertyDescriptors;
			}
		}
		return propertyDescriptor;
	}

	public Object getPropertyValue(Object id) {
		if(violation == null)
			return null;
		
		if (id.equals(PROPERTY_ID))
			return violation.getId();
		if (id.equals(PROPERTY_VIOLATED_MODEL))
			return violation.getViolatedModel();
		if (id.equals(PROPERTY_VIOLATION))
			return violation.getViolation();
		if (id.equals(PROPERTY_CREATION_TIME))
			return new Date(violation.getCreationTime());
		if (id.equals(PROPERTY_PID))
			return violation.getPid();
		if (id.equals(PROPERTY_THREAD_ID))
			return violation.getThreadId();
		if (id.equals(PROPERTY_VIOLATION_TYPE))
			return violation.getViolationType();
		if (id.equals(PROPERTY_CURRENT_ACTIONS)) {
			String actions = "";
			for (String action : violation.getCurrentActions())
				actions += action + " / ";
			return actions;
		}
		if (id.equals(PROPERTY_CURRENT_TESTS)) {
			String tests = "";
			for (String test : violation.getCurrentTests())
				tests += test + " / ";
			return tests;
		}
		if (id.equals(PROPERTY_STACK_TRACE)) {
			return new StringsPropertySource("Method Call", violation.getStackTrace());
		}
		if (id.equals(PROPERTY_VIOLATED_MODEL_TYPE)) {
			return violation.getViolatedModelType();
		}
		if(id.equals(INVALID_PARAMETERS) && violation instanceof BctIOModelViolation) {
			final ViolationData violationData = ViolationsUtil.getViolationData((BctIOModelViolation) violation);
			Map<String,String> parametersMap = ((BctIOModelViolation)violation).getParametersMap();
			HashMap<String, String> violatedVars = new HashMap<String,String>();
			
			for ( VariableData vd : violationData.getViolatedVariables() ){
				String var = vd.getVariableName();
				violatedVars.put(var,parametersMap.get(var));
			}
			
			
			return new ParametersPropertySource(violatedVars);
		}
		if(id.equals(PROPERTY_PARAMETERS) && violation instanceof BctIOModelViolation) {
			System.out.println("PARS "+((BctIOModelViolation)violation).getParameters());
			
			
			
			return new ParametersPropertySource(((BctIOModelViolation)violation).getParametersMap() );
			
		}
		if(id.equals(PROPERTY_VIOLATION_STATES_NAMES) && violation instanceof BctFSAModelViolation) {
			String[] violationStatesNames = ((BctFSAModelViolation)violation).getViolationStatesNames();
			return new StringsPropertySource("State name", violationStatesNames);
		}
		if(id.equals(PROPERTY_VIOLATION_END_STATE) && violation instanceof BctFSAModelViolation) {
			String violationEndState = ((BctFSAModelViolation)violation).getDestinationStateName();
			return violationEndState;
		}
		if(id.equals(PROPERTY_NON_MATCHING_EVENTS) && violation instanceof BctFSAModelViolation) {
			System.out.println("AAAB");
			BctAnomalousCallSequence anomalousSequence = ((BctFSAModelViolation)violation).getAnomalousCallSequence();
			if ( anomalousSequence == null ){
				return null;
			}
			String[] sequence = anomalousSequence.getAnomalousCallSequence();
			System.out.println("AAAC");
			return new StringsPropertySource("Non matching events", sequence);
		}
		if(id.equals(PROPERTY_EXPECTED_EVENTS) && violation instanceof BctFSAModelViolation) {
			System.out.println("AAAB");
			BctFSAModelViolation viol = ((BctFSAModelViolation)violation);
			String method = viol.getViolatedModel();
			FiniteStateAutomaton fsa;
			try {
				fsa = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory().getFSAModelsHandler().getRawFSA(new Method(method));
				ArrayList<String> expected = new ArrayList<String>();
				for ( String stateName : viol.getViolationStatesNames() ){
					State[] states = fsa.getStates();	
					for ( State state : states ){
						if ( state.getName().equals(stateName) ){
							Transition[] trans = fsa.getTransitionsFromState(state);
							for ( Transition t : trans ){
								String expectedMethod = t.getDescription().trim();
								expectedMethod = DisplayNamesUtil.getProgramPointToPrint(mc, expectedMethod );
								expected.add(expectedMethod);
							}
						}
					}
					
				}
				
				return new StringsPropertySource(PROPERTY_EXPECTED_EVENTS, expected.toArray(new String[expected.size()]), true );
				
			} catch (MapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		if(id.equals(PROPERTY_PREVIOUS_EVENT) && violation instanceof BctFSAModelViolation) {
			try {
				InteractionRawTrace faultyTrace = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory().getInteractionRawTraceHandler().findTrace(violation.getPid(), violation.getThreadId());
				BctFSAModelViolation viol = (BctFSAModelViolation) violation;
				int pos = viol.getCallId()-1;
				Iterator<MethodCallPoint> it = faultyTrace.getMethodCallPoints().iterator();
				int counter = 0;
				String expected = ""; 
				while ( it.hasNext() ){
					MethodCallPoint callPoint = it.next();
					if ( counter == pos ){
						expected = callPoint.getMethod().getSignature();
						expected = DisplayNamesUtil.getProgramPointToPrint(mc, expected );
						break;
					}
					if ( callPoint.isEnter() || callPoint.isGeneric() ){
						counter++;	
					}
					
				}
				return expected;
			} catch (MapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public boolean isPropertySet(Object id) {	
		return false;
	}

	public void resetPropertyValue(Object id) {}

	public void setPropertyValue(Object id, Object value) {}

}
