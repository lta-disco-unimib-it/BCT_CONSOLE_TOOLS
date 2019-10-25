package it.unimib.disco.lta.bct.bctjavaeclipse.core.compare;

import grammarInference.Record.Trace;
import it.unimib.disco.lta.ava.automataExtension.AutomataExtension;
import it.unimib.disco.lta.ava.automataExtension.KBehaviorFSAExtender;
import it.unimib.disco.lta.ava.engine.AVAResult;
import it.unimib.disco.lta.ava.engine.AutomataViolationsAnalyzer;
import it.unimib.disco.lta.ava.engine.ComponentBehavioralData;
import it.unimib.disco.lta.ava.engine.ComponentBehavioralDataMemory;
import it.unimib.disco.lta.ava.engine.ViolationsAnalyzerException;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import automata.fsa.FiniteStateAutomaton;

public class TracesComparator {
	
	
	public AVAResult runAnalysis( FiniteStateAutomaton fsa, Trace trace ){
		
		
		AvaConfiguration conf = AvaConfigurationFactory.createDefaultAvaConfiguration(2);
		
		
		AutomataViolationsAnalyzer analyzer = new AutomataViolationsAnalyzer( conf );
		
		
		FiniteStateAutomaton original = (FiniteStateAutomaton) fsa.clone();
		
		KBehaviorFSAExtender extender = new KBehaviorFSAExtender(2);
		
		
		
		List<AutomataExtension> extensions = extender.extendFSA(fsa, trace);
		
		for ( AutomataExtension extension: extensions ){
			System.out.println(extension);
		}
		
		List<String> els = new ArrayList<String>();
		Iterator<String> sit = trace.getSymbolIterator();
		while ( sit.hasNext() ){
			els.add(sit.next());
		}
		
		
		ComponentBehavioralDataMemory cdata = new ComponentBehavioralDataMemory("C1", els, extensions, original );
		
		List<ComponentBehavioralData> data = new ArrayList<ComponentBehavioralData>();
		data.add(cdata);
		
		try {
			AVAResult result = analyzer.processViolations(data);
			
			return result;
		} catch (ViolationsAnalyzerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public void compare(String threadId, String pid) throws MapperException{
		Resource res = MonitoringConfigurationRegistry.getInstance().getResource(null);

		InteractionRawTrace trace = res.getFinderFactory().getInteractionRawTraceHandler().findTrace(pid, threadId);
		
		//trace.getMethodCallPoints().get(0);
	}
}
