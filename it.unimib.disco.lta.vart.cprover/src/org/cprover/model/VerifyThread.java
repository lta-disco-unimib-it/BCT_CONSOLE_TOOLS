package org.cprover.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.cprover.communication.Assertion;
import org.cprover.communication.Assignment;
import org.cprover.communication.Step;
import org.cprover.communication.Trace;
import org.cprover.launch.VerifyConfig;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

public class VerifyThread extends PlatformObject implements IThread {

	public VerifyConfig config;
	
	/**
	 * Launch that triggered the debug session.
	 */
	protected ILaunch launch;
	
	protected Trace trace;
	
	/**
	 * Workspace root. Will be cut for viewing. 
	 */
	protected String root;
	
	private String name;
	
	protected StepStackFrame[] stackFrames;
	
	public VerifyThread(VerifyConfig config, ILaunch launch, Trace trace, String root, String name) {
		this.config = config;
		this.launch = launch;
		this.trace = trace;
		this.root = root;
		this.name = name;
		createStackFrames();
	}

	/**
     * @return the root
     */
    public String getRoot() {
        return root;
    }

    private void createStackFrames() {
	    Map<String, VerifyVariable> variables = new HashMap<String, VerifyVariable>(); // keeps actual values of variables
		LinkedList<StepStackFrame> frames = new LinkedList<StepStackFrame>();
		
		int i=0;
		for (Step step : trace.steps) {
			if( step.location == null ) continue; // ignore steps without location
			
		    if (step instanceof Assignment) {
                Assignment assign = (Assignment) step;
                frames.add(new AssignStackFrame(this, assign, variables));
                variables.put(assign.identifier, new VerifyVariable(launch, assign.identifier, assign.type, assign.value, false,variables.size()));
            }
		    else if(step instanceof Assertion) {
		        frames.add(new StepStackFrame(this, step.location, variables));
		    } 
		    else {
		        frames.add(new FailureStackFrame(this, step.location, variables));
		    }
		}
		stackFrames = frames.toArray(new StepStackFrame[frames.size()]);
	}

	public IBreakpoint[] getBreakpoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() throws DebugException {
		return name;
	}

	public int getPriority() throws DebugException {
		// TODO Auto-generated method stub
		return 0;
	}

	public IStackFrame[] getStackFrames() throws DebugException {
		return stackFrames;
	}

	public IStackFrame getTopStackFrame() throws DebugException {
//		return stackFrames[0];
		return stackFrames[stackFrames.length-1];
	}

	public boolean hasStackFrames() throws DebugException {
		return stackFrames != null;
	}

	public IDebugTarget getDebugTarget() {
		return launch.getDebugTarget();
	}

	public ILaunch getLaunch() {
		return launch;
	}

	public String getModelIdentifier() {
		return "getModelIdentifier()";
	}

	public Object getAdapter(Class adapter) {
        return super.getAdapter(adapter);
	}

	public boolean canResume() {
		return false;
	}

	public boolean canSuspend() {
		return true;
	}

	public boolean isSuspended() {
		return true;
	}

	public void resume() throws DebugException {
		// TODO Auto-generated method stub

	}

	public void suspend() throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean canStepInto() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canStepOver() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canStepReturn() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStepping() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stepInto() throws DebugException {
		// TODO Auto-generated method stub

	}

	public void stepOver() throws DebugException {
		// TODO Auto-generated method stub

	}

	public void stepReturn() throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean canTerminate() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	public void terminate() throws DebugException {
		// TODO Auto-generated method stub

	}

}
