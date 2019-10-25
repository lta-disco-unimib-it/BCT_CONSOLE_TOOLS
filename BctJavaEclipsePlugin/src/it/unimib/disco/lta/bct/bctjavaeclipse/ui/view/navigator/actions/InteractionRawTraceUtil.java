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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

public class InteractionRawTraceUtil {

	
	
	public static String getTextualRepresentation(
			InteractionRawTrace interactionTrace, boolean analyzeParameters) {
		StringBuffer sb = new StringBuffer();
		
		try {
			
			for ( MethodCallPoint mcp : interactionTrace.getMethodCallPoints() ){
				sb.append(mcp.getMethod().getSignature());
				sb.append(",");
				if ( analyzeParameters ){
					sb.append( getTextualRepresentation( mcp.getCorrespondingProgramPoint() ) );
				}
				sb.append("\n");
			}
		} catch (LoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	private static String getTextualRepresentation(
			ProgramPoint correspondingProgramPoint) {
		
		StringBuffer sb = new StringBuffer();
		
		List<ProgramPointValue> vars = correspondingProgramPoint.getProgramPointVariableValues();
		Collections.sort(vars,new Comparator<ProgramPointValue>() {

			@Override
			public int compare(ProgramPointValue o1, ProgramPointValue o2) {
				return o1.getVariable().getName().compareTo(o2.getVariable().getName());
			}
		});
		
		for ( ProgramPointValue var : vars ){
			sb.append(var.getVariable().getName());
			sb.append(":\"");
			sb.append(var.getValue());
			sb.append("\",");
		}
		
		return sb.toString();
	}

}
