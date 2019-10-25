package org.eclipse.core.resources;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public interface IWorkspace {

	public IWorkspaceRoot getRoot();
	
	public IProjectDescription newProjectDescription(String project);

	public IStatus copy(IResource[] src, IPath dest, boolean force,
			IProgressMonitor monitor) throws CoreException;
}
