/*
 * Created on 22.01.2009
 *
 * $author
 * 
 */
package org.cprover.model;

import java.util.Map;

import org.cprover.communication.Location;

public class FailureStackFrame extends StepStackFrame {

    public FailureStackFrame(VerifyThread thread, Location location, Map<String, VerifyVariable> variables) {
        super(thread, location, variables);
    }

}
