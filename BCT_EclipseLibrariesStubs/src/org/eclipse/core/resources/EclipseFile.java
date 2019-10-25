package org.eclipse.core.resources;

import java.io.File;
import java.net.URI;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import console.WorkspaceUtil;

public class EclipseFile implements IFile {

	private File file;

	public EclipseFile(File file) {
		this.file = file;
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
	public void touch(IProgressMonitor p) {
		
	}

	@Override
	public IContainer getParent() {
		// TODO Auto-generated method stub
		return new Folder( file.getParentFile() );
	}

	@Override
	public void refreshLocal(int depth, IProgressMonitor p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public IPath getFullPath() {
		
		String filePath = file.getAbsolutePath();
		File root = WorkspaceUtil.getWorkspaceRootFile();
		if ( root == null ){
			return null;
		}
		
		String rootPath = root.getAbsolutePath();
		
		if ( ! filePath.startsWith(rootPath) ){
			return null;
		}
		
		return new Path( filePath.substring( rootPath.length() )  );
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public URI getLocationURI() {
		IPath location = getLocation();
		return location.toFile().toURI();
	}

	
}
