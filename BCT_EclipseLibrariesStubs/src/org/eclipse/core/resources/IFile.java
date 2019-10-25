package org.eclipse.core.resources;

import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IFile extends IResource {


	public IPath getFullPath();

	public String getName();

	public URI getLocationURI();

}
