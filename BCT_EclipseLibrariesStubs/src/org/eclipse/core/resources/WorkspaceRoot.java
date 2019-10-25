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
