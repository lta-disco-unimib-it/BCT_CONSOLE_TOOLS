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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.klfa;

import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;
import it.unimib.disco.lta.ava.automataExtension.AutomataExtension;
import it.unimib.disco.lta.ava.automataExtension.AutomataExtensionType;
import it.unimib.disco.lta.ava.automataExtension.KBehaviorFSAExtender;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIAbstractMapper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.URIUtil;

import java.util.ArrayList;
import java.util.List;

import conf.InteractionInferenceEngineSettings;

import recorders.ViolationsRecorder;
import recorders.ViolationsRecorder.InteractionViolationType;

import automata.fsa.FiniteStateAutomaton;

import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctModelViolation;

public class KLFAUtil {

	private static int vcount;

	public static List<BctFSAModelViolation> createRefinedInteractionViolations(
			MonitoringConfiguration mc, List<BctAnomalousCallSequence> filteredAnomalousCallSequences) throws LoaderException, MapperException {
		List<BctFSAModelViolation> violations = new ArrayList<BctFSAModelViolation>();
		
		for ( BctAnomalousCallSequence anomalousCallSequence : filteredAnomalousCallSequences ){
			violations.addAll(createRefinedInteractionViolations(mc, anomalousCallSequence));
		}
		
		return violations;
		
	}
	
	public static List<BctFSAModelViolation> createRefinedInteractionViolations(
			MonitoringConfiguration mc, BctAnomalousCallSequence anomalousCallSequence) throws LoaderException, MapperException {
		
		Trace trace = getTrace( anomalousCallSequence );
		
		int minTrustLen = 2;
		InteractionInferenceEngineSettings fsaSett;
		if ( ( fsaSett = mc.getFsaEngineSettings() ) != null ) {
			minTrustLen = fsaSett.getMinTrustLen();
		}
		
		
		KBehaviorFSAExtender extender = new KBehaviorFSAExtender(minTrustLen);
		
		String methodName = anomalousCallSequence.getViolatedModel(); 
		Method m = new Method(methodName);
		
		FSAModel fsaModel;
		
		fsaModel = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory().getFSAModelsHandler().getFSAModel(m);
		
		FiniteStateAutomaton fsa = fsaModel.getFSA();
		
		List<AutomataExtension> extensions = extender.extendFSA(fsa, trace);
		
		List<BctFSAModelViolation> violations = new ArrayList<BctFSAModelViolation>();
		
		int i = 0;
		long time = anomalousCallSequence.getCreationTime();
		for ( AutomataExtension extension : extensions ){
			String[] fromStates = new String[1];
			fromStates[0]=extension.getFromState();
			
			String violType = null;
			AutomataExtensionType extensionType = extension.getExtensionType();
			String invokedMethod = null;
			if ( extensionType == AutomataExtensionType.Branch ){
				violType = BctModelViolation.ViolationType.UNEXPECTED_INVOCATION_SEQUENCE;
				invokedMethod = extension.getExtensionEvents().get(0);
			} else if ( extensionType == AutomataExtensionType.Tail){
				violType = BctModelViolation.ViolationType.UNEXPECTED_TERMINATION_SEQUENCE;
				invokedMethod = extension.getExtensionEvents().get(0);
			} else if ( extensionType == AutomataExtensionType.FinalState){
				violType = BctModelViolation.ViolationType.UNEXPECTED_TERMINATION;
			}
			
			String[] stack;
			if ( invokedMethod != null ){
				String[] recordedStack = anomalousCallSequence.getStackTrace();
				stack = new String[recordedStack.length+1];
				System.arraycopy(recordedStack, 0, stack, 1, recordedStack.length);
				stack[0]=invokedMethod;
			} else {
				stack = anomalousCallSequence.getStackTrace();
			}
			
			String violation;
			if ( extension.getExtensionEvents() == null && extension.getExtensionEvents().size() > 0){
				violation = "";
			} else {
				violation = extension.getExtensionEvents().get(0); 
			}
			time += i;
			BctFSAModelViolation viol = new BctFSAModelViolation(
					getNewViolationId(), 
					methodName,
					violation, 
					violType, 
					time,
					anomalousCallSequence.getCurrentActions(), 
					anomalousCallSequence.getCurrentTests(),
					stack, 
					anomalousCallSequence.getPid(), 
					anomalousCallSequence.getThreadId(), 
					fromStates,
					extension.getExtensionEvents().toArray(new String[0]),
					extension.getToState(),
					extension.getExtensionPosition());
			
			viol.setAnomalousCallSequence(anomalousCallSequence);
			violations.add(viol);
			
			++i;
		}
		
		return violations;
	}

	private static String getNewViolationId() {
		return "Refined-"+String.valueOf(vcount++);
	}

	private static Trace getTrace(BctAnomalousCallSequence anomalousCallSequence) {
		Trace t = new VectorTrace();
		for ( String call : anomalousCallSequence.getAnomalousCallSequenceList() ){
			t.addSymbol(call);	
		}
		
		return t;
	}

}
