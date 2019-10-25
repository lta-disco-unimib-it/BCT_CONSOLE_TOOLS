/*
 * Created on 20.01.2009
 *
 * $author
 * 
 */
package org.cprover.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import org.cprover.communication.Assignment;

public class AssignStackFrame extends StepStackFrame {
	
    Assignment assign;
    
    public AssignStackFrame(VerifyThread thread, Assignment assign, Map<String, VerifyVariable> variables) {
        super(thread, assign.location);
        variables.put(assign.identifier, new VerifyVariable(thread.getLaunch(), assign, true, variables.size() ));
		this.variables = variables.values().toArray(new VerifyVariable[variables.size()]);
		
		Arrays.sort(this.variables, new Comparator<VerifyVariable>() {

			@Override
			public int compare(VerifyVariable o1, VerifyVariable o2) {
				return o2.getOrder()-o1.getOrder();
			}
		} );
		
		variables.remove(assign.identifier);
    }
}
