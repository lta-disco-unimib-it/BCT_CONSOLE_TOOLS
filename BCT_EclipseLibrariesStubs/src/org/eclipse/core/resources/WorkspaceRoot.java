package org.eclipse.core.resources;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

public class WorkspaceRoot implements IWorkspaceRoot {

	private File file;

	public WorkspaceRoot( File root ){
		this.file = root;
	}
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public IFile getFile(IPath path) {
		// TODO Auto-generated method stub
		return new EclipseFile( new File( file+"/" +path) );
	}

	@Override
	public IFolder getFolder(IPath path) {
		return new Folder( path.toFile() );
	}

	@Override
	public IProject getProject(String name) {
		// TODO Auto-generated method stub
		return new Project ( this, name );
	}

	@Override
	public IFile getFileForLocation(IPath location) {
		
		return new EclipseFile(location.toFile());
	}

	@Override
	public IContainer getContainerForLocation(IPath location) {
		return new Folder(location.toFile());
	}

	@Override
	public void refreshLocal(int depth, IProgressMonitor p) {

	}

	@Override
	public void touch(IProgressMonitor p) {

	}

	@Override
	public IContainer getParent() {
		return new Folder(file.getParentFile());
	}

	@Override
	public IPath getLocation() {
		return new Path( file.getAbsolutePath() );
	}

	@Override
	public IPath getRawLocation() {
		return new Path( file.getAbsolutePath() );
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return file.exists();
	}

	@Override
	public URI getLocationURI() {
		return file.toURI();
	}

	

}
