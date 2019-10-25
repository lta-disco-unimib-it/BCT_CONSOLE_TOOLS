package org.eclipse.core.resources;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public interface IWorkspaceRoot extends IResource {
	
	public File getFile();
	
	public IFile getFile(IPath path);
	
	public IFolder getFolder(IPath path);
	
	public IProject getProject(String name);

	public IFile getFileForLocation( IPath location );

	public IContainer getContainerForLocation(IPath location);

	public URI getLocationURI();
}
