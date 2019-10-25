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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.ava;



import it.unimib.disco.lta.ava.automataExtension.AutomataExtension;
import it.unimib.disco.lta.ava.automataExtension.AutomataExtensionType;
import it.unimib.disco.lta.ava.automataExtension.AutomataExtension.StateType;
import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.ava.engine.AutomataViolationsAnalyzer;
import it.unimib.disco.lta.ava.engine.ComponentBehavioralData;
import it.unimib.disco.lta.ava.engine.ComponentBehavioralDataMemory;
import it.unimib.disco.lta.ava.engine.ViolationsAnalyzerException;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.klfa.KLFAUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.ArrayList;
import java.util.List;

import automata.fsa.FiniteStateAutomaton;

import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctModelViolation.ViolationType;


public class AvaUtil {

	public static ComponentBehavioralDataMemory createBehavioralDataForViolation(
			MonitoringConfiguration mc,
			BctAnomalousCallSequence anomalousCallSequence,
			List<BctFSAModelViolation> bctViolations) throws MapperException, LoaderException {
		
		ArrayList<AutomataExtension> extensions = new ArrayList<AutomataExtension>();
		for ( BctFSAModelViolation bctViolation : bctViolations ){
			AutomataExtension automataExtension = getAutomataExtension( bctViolation );
			extensions.add(automataExtension);
		}
		
		Method m = new Method(anomalousCallSequence.getViolatedModel());
		
		FSAModel fsaModel;
		
		fsaModel = MonitoringConfigurationRegistry.getInstance().getResource(mc).getFinderFactory().getFSAModelsHandler().getFSAModel(m);
		
		FiniteStateAutomaton fsa = fsaModel.getFSA();
		
		ComponentBehavioralDataMemory cdata = new ComponentBehavioralDataMemory(
				anomalousCallSequence.getViolatedModel(), 
				anomalousCallSequence.getAnomalousCallSequenceList(), 
				extensions, 
				fsa );
		
		return cdata;
		
		

	}

	private static AutomataExtension getAutomataExtension(
			BctFSAModelViolation bctViolation) {
		
		AutomataExtensionType extensionType = null;
		
		String violationType = bctViolation.getViolationType();
		
		if ( violationType.equals(ViolationType.UNEXPECTED_INVOCATION_SEQUENCE) ){
			extensionType = AutomataExtensionType.Branch;
		} else if ( violationType.equals(ViolationType.UNEXPECTED_TERMINATION) ){
			extensionType = AutomataExtensionType.FinalState;
		} else if ( violationType.equals(ViolationType.UNEXPECTED_TERMINATION_SEQUENCE) ){
			extensionType = AutomataExtensionType.Tail;
		}
		
		int anomalousEventPosition = bctViolation.getAnomalousEventPosition();
		
		ArrayList<String> events = new ArrayList<String>();
		
		
		
		AutomataExtension extension = new  AutomataExtension(
				extensionType, 
				anomalousEventPosition,
				anomalousEventPosition,
				bctViolation.getViolationStatesNames()[0], 
				StateType.Existing,
				bctViolation.getUnexpectedSequenceList(),
				bctViolation.getUnexpectedSequenceList(),
				bctViolation.getDestinationStateName(), 
				bctViolation.getUnexpectedSequence().length, 
				new ArrayList<String>(0),
				new ArrayList<String>(0));
		
		return extension;
	}

	public static AVAResult createAVAInterpretations(
			AvaConfiguration avaConfiguration, MonitoringConfiguration mc,
			List<BctFSAModelViolation> fsaViolations,
			List<BctAnomalousCallSequence> anomalousCallSequences) throws LoaderException, MapperException, ViolationsAnalyzerException {
		boolean fineGrain = true;
		for ( BctFSAModelViolation bctFSAModelViolation :  fsaViolations){
			if ( ! bctFSAModelViolation.isFineGrain() ){
				fineGrain = false;
				break;
			}
		}
		
		List<ComponentBehavioralData> data = new ArrayList<ComponentBehavioralData>();


		if ( ! fineGrain ){
			fsaViolations = KLFAUtil.createRefinedInteractionViolations(mc, anomalousCallSequences);
		} 

		for ( BctAnomalousCallSequence anomalousCallSequence : anomalousCallSequences ) {
			List<BctFSAModelViolation> violations = new ArrayList<BctFSAModelViolation>();

			for ( BctFSAModelViolation bctFSAModelViolation :  fsaViolations){
				if ( bctFSAModelViolation.getAnomalousCallSequence() == anomalousCallSequence ){
					violations.add(bctFSAModelViolation);
				}
			}

			ComponentBehavioralDataMemory cdata = createBehavioralDataForViolation(mc, anomalousCallSequence, violations);
			data.add(cdata);		
		}


		
		
	
		AutomataViolationsAnalyzer analyzer = new AutomataViolationsAnalyzer( avaConfiguration );

		return analyzer.processViolations(data);

	}

}
