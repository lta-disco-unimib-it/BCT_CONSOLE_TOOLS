package org.eclipse.core.resources;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IResource {

	public static final int DEPTH_INFINITE = 0;

	public void refreshLocal(int depth, IProgressMonitor p)  throws CoreException;

	public void touch(IProgressMonitor p)  throws CoreException;
	
	public IContainer getParent();
	
	public IPath getLocation();
	
	public IPath getRawLocation();
	
	public boolean exists();
}
