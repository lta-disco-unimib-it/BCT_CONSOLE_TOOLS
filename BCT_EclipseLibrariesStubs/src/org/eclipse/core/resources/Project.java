/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
