package org.cprover.runners;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

public class DummyLaunch implements ILaunch {

	ILaunchConfiguration config;
	
	List<IDebugTarget> targets = new LinkedList<IDebugTarget>();
	
	List<IProcess> processes = new LinkedList<IProcess>();
	
	public DummyLaunch(ILaunchConfiguration config) {
		this.config = config;
	}

	public DummyLaunch(IDebugTarget target) {
	    targets.add(target);
	}

	public void addDebugTarget(IDebugTarget target) {
	    targets.add(target);
	}

	public void addProcess(IProcess process) {
	    processes.add(process);
	}

	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] getChildren() {
		// TODO Auto-generated method stub
		return getDebugTargets();
	}

	public IDebugTarget getDebugTarget() {
		return targets.get(0);
	}

	public IDebugTarget[] getDebugTargets() {
		return targets.toArray(new IDebugTarget[targets.size()]); 
	}

	public ILaunchConfiguration getLaunchConfiguration() {
		return config;
	}

	public String getLaunchMode() {
		// TODO Auto-generated method stub
		return "debug";
	}

	public IProcess[] getProcesses() {
		return processes.toArray(new IProcess[processes.size()]);
	}

	public ISourceLocator getSourceLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeDebugTarget(IDebugTarget target) {
		// TODO Auto-generated method stub

	}

	public void removeProcess(IProcess process) {
		// TODO Auto-generated method stub

	}

	public void setAttribute(String key, String value) {
		// TODO Auto-generated method stub

	}

	public void setSourceLocator(ISourceLocator sourceLocator) {
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

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
