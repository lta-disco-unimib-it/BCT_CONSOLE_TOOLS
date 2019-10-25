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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ava;

import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfigurationFactory;
import it.unimib.disco.lta.ava.engine.configuration.ThresholdType;
import it.unimib.disco.lta.ava.graphs.TransitionDirection;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

public class AvaConfigurationUtil {

	public static AvaConfiguration createDefaultAvaConfiguration ( MonitoringConfiguration mc ){
		int k = mc.getFsaEngineSettings().getMinTrustLen();
		
		AvaConfiguration conf = AvaConfigurationFactory.createDefaultAvaConfiguration(k);
		
		conf.setStepsToPerform(TransitionDirection.forward,k);
		conf.setStepsToPerform(TransitionDirection.backward,k);
		
		
		conf.setThreshold(ThresholdType.singlePatternScore, (float)0.5 );
		conf.setThreshold(ThresholdType.compositePatternScore, (float)0.5 );
		conf.setThreshold(ThresholdType.interComponentPatternScore, (float)0.5 );
		
		conf.setBasicInterpretationsForViolationLimit(-1);
		conf.setCompositeInterpretationsForViolationLimit(-1);
		conf.setInterComponentInterpretationsForViolationLimit(-1);
		
		conf.setExpectedSequencesLimit(20);
		conf.setExpectedSubSequencesLimit(20);
		
		conf.setMaxInterpretationsPerPattern(-1);
		
		
		return conf;
		
	}
	

}
