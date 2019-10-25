package org.eclipse.core.resources;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public interface IProject {

	public boolean exists();
	
	public void create(IProjectDescription desc, IProgressMonitor mon );
	
	public IPath getLocation();
	
	public void touch(IProgressMonitor monitor);

	public void open(NullProgressMonitor nullProgressMonitor);
}
