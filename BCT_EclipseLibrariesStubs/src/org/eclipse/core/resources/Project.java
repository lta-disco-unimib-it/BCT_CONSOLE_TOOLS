package org.eclipse.core.resources;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class Project implements IProject {

	private String name;
	private WorkspaceRoot workspaceRoot;

	public Project(WorkspaceRoot workspaceRoot, String name) {
		this.workspaceRoot = workspaceRoot;
		this.name = name;
	}

	@Override
	public boolean exists() {
		return toFile( ).exists();
	}

	private File toFile() {
		return new File( workspaceRoot.getFile(), name );
	}

	@Override
	public void create(IProjectDescription desc, IProgressMonitor mon) {
		throw new IllegalStateException("Should not create Eclipse project with the fake library: "+desc.getWorkspace() );
	}

	@Override
	public IPath getLocation() {
		return new Path(toFile().getAbsolutePath());
	}

	@Override
	public void touch(IProgressMonitor monitor) {

	}

	@Override
	public void open(NullProgressMonitor nullProgressMonitor) {
		// TODO Auto-generated method stub
		
	}
	
	

}
