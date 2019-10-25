package org.cprover.model;

import java.util.LinkedList;
import java.util.List;

import org.cprover.communication.Trace;
import org.cprover.launch.VerifyConfig;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;

public class VerifyDebugTarget extends PlatformObject implements IDebugTarget {
	
	private VerifyConfig config;
	
	/**
	 * Launch that triggered the debug session.
	 */
	protected ILaunch launch;
	
	protected String name;
	
	protected List<VerifyThread> threads = new LinkedList<VerifyThread>();
	
	/**
	 * 
	 */
	private IProcess process;
	
    public VerifyDebugTarget(VerifyConfig config, ILaunch launch, IProcess process, String name, String root) {
    	this.config = config;
		this.launch = launch;
		this.process = process; // needed?
		this.name = name;
		
		launch.addDebugTarget(this);
	}

    public void addThread(VerifyThread verifyThread) {
        threads.add(verifyThread);
    }

    public String getName() throws DebugException {
		return name;
	}

	public IProcess getProcess() {
		return process;
	}

	public IThread[] getThreads() throws DebugException {
		return threads.toArray(new IThread[threads.size()]);
	}

	public boolean hasThreads() throws DebugException {
		return !threads.isEmpty();
	}

	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		return false;
	}

	public IDebugTarget getDebugTarget() {
		return this;
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

	public boolean canResume() {
		// TODO Auto-generated method stub
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

	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub

	}

	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub

	}

	public boolean canDisconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	public void disconnect() throws DebugException {
		// TODO Auto-generated method stub

	}

	public boolean isDisconnected() {
		return false;
	}

	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supportsStorageRetrieval() {
		// TODO Auto-generated method stub
		return false;
	}

}
