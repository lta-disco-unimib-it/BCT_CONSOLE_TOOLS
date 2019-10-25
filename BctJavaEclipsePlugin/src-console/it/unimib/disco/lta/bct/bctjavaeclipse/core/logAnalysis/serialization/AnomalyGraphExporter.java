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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import modelsViolations.BctRuntimeData;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import tools.violationsAnalyzer.anomalyGraph.AnomalyGraph;

/**
 * This class manages the exporting of anomaly graphs as files.
 * It is used to easily use anomalyGraph editor to open the analysis results
 * 
 * @author Fabrizio Pastore
 *
 */
public class AnomalyGraphExporter {

	

	public static void exportAnomalyGraph(AnomalyGraph agr, File file) throws IOException {
		
		
		BufferedWriter bw = new BufferedWriter( new FileWriter(file) );
		
		String graphId = file.getName().replace(".", "_");
		writeHeader(bw,graphId);
		
		for ( BctRuntimeData v : agr.getViolations()){
			if ( v instanceof BctFSAModelViolation ){
				writeFSAViolation(bw,(BctFSAModelViolation) v);
			} else if ( v instanceof BctIOModelViolation  ){
				writeIOViolation(bw,(BctIOModelViolation) v);
			}
		}
		
		
		
		DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> graph = agr.getGraph();
		
		int i = 0;
		for ( DefaultWeightedEdge edge : graph.edgeSet()){
			String id = "E"+i;
			++i;
			writeEdge( bw, graph.getEdgeSource(edge), graph.getEdgeTarget(edge), id, graph.getEdgeWeight(edge) );
		}
		    
		    
		writeFooter(bw,graphId,file.getName());    
		bw.close();
		  
	}

	private static void writeFooter(BufferedWriter bw,String graphId, String fileName) throws IOException {
	  bw.write("</it.unimib.disco.lta.eclipse.anomalyGraph:Graph>\n");
	  bw.write("<notation:Diagram xmi:id=\""+"_"+graphId+"\" type=\"AnomalyGraph\" element=\""+graphId+"\" name=\""+fileName+"\" measurementUnit=\"Pixel\">\n</notation:Diagram>\n");
	  bw.write("</xmi:XMI>\n");
	}

	private static void writeEdge(BufferedWriter bw, String from, String to, String id, double weigth) throws IOException {
		
		bw.write("<relationships xmi:type=\"it.unimib.disco.lta.eclipse.anomalyGraph:Relationship\" ");
		bw.write("xmi:id=\""+id+"\" from=\""+from+"\" to=\""+to+"\" weight=\""+weigth+"\"/>\n");
	}

	private static void writeFSAViolation(BufferedWriter bw, BctFSAModelViolation v) throws IOException {
		writeModelViolationHeaderBegin(bw,"BctFSAModelViolation", v);
		
		
		
		writeModelViolationHeaderEnd(bw, v);
		
		writeModelViolationContent(bw, v);
		
		for ( String state : v.getViolationStatesNames() ){
			bw.write("<currentStates>"+state+"</currentStates>");
		}
		
		writeModelViolationFooter(bw, v);
	}
	
	private static void writeModelViolationContent(BufferedWriter bw,
			BctModelViolation v) throws IOException {
		
		//stack trace
		for ( String stElement : v.getStackTrace() ){
			bw.write("<stackTrace>"+formatStringForXml(stElement)+"</stackTrace>\n");
		}
		
		for ( String action : v.getCurrentActions() ){
			bw.write("<activeActions>"+formatStringForXml(action)+"</activeActions>\n");
		}
		
		for ( String test : v.getCurrentTests() ){
			bw.write("<activeTests>"+formatStringForXml(test)+"</activeTests>\n");
		}
	}

	private static void writeIOViolation(BufferedWriter bw, BctIOModelViolation v) throws IOException {
		
		writeModelViolationHeaderBegin(bw, "BctIOModelViolation", v);
		
			
		
		writeModelViolationHeaderEnd(bw, v);
		
		bw.write("<actualParametersStates>"+v.getParameters()+"</actualParametersStates>");
		
		writeModelViolationContent(bw, v);
		
		writeModelViolationFooter(bw, v);
	}
	
	private static void writeModelViolationHeaderBegin(BufferedWriter bw, String typeName, BctModelViolation v) throws IOException {
		
		bw.write("<violations xmi:type=\"it.unimib.disco.lta.eclipse.anomalyGraph:"+typeName+"\" ");
		
		bw.write("xmi:id=\""+v.getId()+"\" id=\""+v.getId()+"\" ");
		
		bw.write("violatedModel=\""+formatStringForXml(v.getViolatedModel())+"\" "); 
		bw.write("violation=\""+formatStringForXml(v.getViolation())+"\" ");
		bw.write("pid=\""+v.getPid()+"\" ");
		bw.write("threadId=\""+v.getThreadId()+"\" ");
		
		String vType = v.getViolationType();
		String violationType;
		if ( vType.equals(BctModelViolation.ViolationType.UNEXPECTED_EVENT)){
			violationType = "UnexpectedEvent";
		} else if ( vType.equals(BctModelViolation.ViolationType.UNEXPECTED_TERMINATION) ){
			violationType = "PrematureEnd";
		} else {
			violationType = "ParameterInvariantViolated";
		}
		bw.write("violationType=\""+violationType+"\" ");
		
	}
	
	private static String formatStringForXml( String input ){
		return input.replace("<", "&lt;")
					.replace(">", "&gt;")
					.replace("\"", "&quot;");
	}
	
	private static void writeModelViolationHeaderEnd(BufferedWriter bw, BctRuntimeData v) throws IOException {
		bw.write(">\n");
	}
	
	private static void writeModelViolationFooter(BufferedWriter bw, BctRuntimeData v) throws IOException {
		
		bw.write("</violations>\n");
	}

	private static void writeHeader(BufferedWriter bw, String string) throws IOException{
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xmi:XMI xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:it.unimib.disco.lta.eclipse.anomalyGraph=\"it.unimib.disco.lta.eclipse.anomalyGraph\" xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.1/notation\">\n");
		bw.write("<it.unimib.disco.lta.eclipse.anomalyGraph:Graph xmi:id=\""+string+"\" >\n");
		
	}
}
