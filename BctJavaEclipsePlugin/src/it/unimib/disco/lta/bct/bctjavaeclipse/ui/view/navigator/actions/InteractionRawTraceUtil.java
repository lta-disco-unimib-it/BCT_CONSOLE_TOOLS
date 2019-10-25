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
