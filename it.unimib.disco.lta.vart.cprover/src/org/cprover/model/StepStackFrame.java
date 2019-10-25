package org.cprover.model;

import java.util.Map;

import org.cprover.communication.Location;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

public class StepStackFrame extends PlatformObject implements IStackFrame {

	VerifyThread thread;
	
	Location location;
	
	VerifyVariable variables[];
	
	protected StepStackFrame(VerifyThread thread, Location location) {
		this.thread = thread;
		this.location = location;
	}
	
	public StepStackFrame(VerifyThread thread, Location location, Map<String, VerifyVariable> variables) {
		this.thread = thread;
		this.location = location;
		this.variables = variables.values().toArray(new VerifyVariable[variables.size()]);
	}

	public int getCharEnd() throws DebugException {
		return -1;
	}

	public int getCharStart() throws DebugException {
		return -1;
	}

	public int getLineNumber() throws DebugException {
		return location.line;
	}

	public String getName() throws DebugException {
		// C++ Debug view example: "main() /home/file.cpp: 10 0x0000000"
	    String filename = location.file;
	    String root = thread.getRoot();
        if (filename.startsWith(root)) {
	        filename = filename.substring(root.length() + 1);
	    }
	    
		return filename + ": " + location.line;
	}

	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public IThread getThread() {
		return thread;
	}

	public IVariable[] getVariables() throws DebugException {
		return variables;
	}

	public boolean hasRegisterGroups() throws DebugException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasVariables() throws DebugException {
		return true;
	}

	public IDebugTarget getDebugTarget() {
		return thread.getDebugTarget();
	}

	public ILaunch getLaunch() {
		return thread.getLaunch();
	}

	public String getModelIdentifier() {
		return "getModelIdentifier()";
	}

	public Object getAdapter(Class adapter) {
        return super.getAdapter(adapter);
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

	public boolean canResume() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canSuspend() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	public void resume() throws DebugException {
		// TODO Auto-generated method stub

	}

	public void suspend() throws DebugException {
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
