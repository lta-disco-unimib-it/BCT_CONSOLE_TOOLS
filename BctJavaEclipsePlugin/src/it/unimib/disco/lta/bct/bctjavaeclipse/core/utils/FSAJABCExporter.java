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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import tools.violationsAnalyzer.MultiHashMap;
import fsa.FSA;
import fsa.State;
import fsa.Transition;

public class FSAJABCExporter {

	public static void exportToJABC(FSA fsa, File destFile) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(destFile));
		
		writeHeader(bw,destFile.getName());
		
		HashMap<State,String> statesMap = new HashMap<State,String>();
		MultiHashMap<State, String> statesTransitionMap = new MultiHashMap<State, String>();
		
		for ( Transition t : fsa.getTransitions() ){
			writeTransition(bw,statesTransitionMap,t);
		}
		
		for ( State s : fsa.getStates() ){
			writeState(bw,statesMap,statesTransitionMap, s,fsa);
		}
		
		writeTemporalFormaula(bw,fsa);
		
		writeFooter(bw);
		
		bw.close();
		
	}

	private static void writeFooter(BufferedWriter bw) throws IOException {
		bw.write("</model>");
	}

	private static void writeTemporalFormaula(BufferedWriter bw, FSA fsa) throws IOException {
		bw.write("<userobject key=\"MODELCHECKING mapping: formula to description\">\n");

		bw.write("<map>\n");

		
		for ( String s : fsa.getTemporalFormula() ){
			bw.write("<entry>\n");
			bw.write("<string>"+s+"</string>\n");
			bw.write("<string>BCT Formula</string>\n");
			bw.write("</entry>\n");
		}
		
		
		bw.write("</map>\n");
		bw.write("</userobject>\n");
	}

	private static void writeTransition(BufferedWriter bw,
			MultiHashMap<State, String> statesTransitionMap, Transition t) throws IOException {
		bw.write("<edge>\n");
		bw.write("<source>"+t.getFrom().getName()+"</source>\n");
		bw.write("<target>"+t.getTo().getName()+"</target>\n");
		bw.write("<point>1.0,1.0</point>\n");
		bw.write("<point>1.0,1.0</point>\n");
		bw.write("<labelposition>1.0,1.0</labelposition>\n");
		
		String tname = formatXmlString(t.getDescription());
		statesTransitionMap.put(t.getFrom(), tname);
		
		bw.write("<branch>"+tname+"</branch>\n");
		bw.write("</edge>\n");
	}

	private static String formatXmlString(String string ){
		return string.replace("<", "&lt;").replace(">", "&gt;");
	}
	
	private static void writeState(BufferedWriter bw,
			HashMap<State, String> statesMap, MultiHashMap<State, String> statesTransitionMap, State s, FSA fsa) throws IOException {
		
		statesMap.put(s, s.getName());
		
		bw.write("<sib>\n");
		bw.write("<id>"+s.getName()+"</id>\n");
		bw.write("<name>"+s.getName()+"</name>\n");
		bw.write("<label>"+s.getName()+"</label>\n");
		
		bw.write("<sib-uid>00000000:00000000000000000000:0000000000022</sib-uid>\n");
		bw.write("<taxonomy>de.metaframe.abc.sib.ProxySIB</taxonomy>\n");
		
		bw.write("<position>1.0,1.0</position>\n");
		
		//outgoing transitions
		List<String> out = statesTransitionMap.get(s);
		if ( out != null ){
			for( String tName : out  ){
				bw.write("<branch>"+tName+"</branch>\n");
			}
		}
		
		bw.write("<userobject key=\"MODELCHECKING atomic propositions\">\n");
		bw.write("<linked-list>\n");
		
		for ( String p : s.getTemporalProperties() ){
			bw.write("<string>"+p+"</string>\n");
		}
		
		bw.write("</linked-list>\n");
		bw.write("</userobject>\n");
		
		if ( fsa.getInitialState() == s ){
			bw.write("<userobject key=\"ABC$START\">\n");
			bw.write("<boolean>true</boolean>\n");
			bw.write("</userobject>\n");
		}
		
		bw.write("</sib>\n");


	}

	private static void writeHeader(BufferedWriter bw,String name) throws IOException {
		String id = name.substring(0,name.length()-5);
		bw.write("<model version=\"2.1\">\n");
		bw.write("<id>"+"BCT_FSA_"+id+"</id>\n");
		bw.write("<name>"+id+"</name>\n");
		bw.write("<changecounter>0</changecounter>\n");
		
		
	}

}
