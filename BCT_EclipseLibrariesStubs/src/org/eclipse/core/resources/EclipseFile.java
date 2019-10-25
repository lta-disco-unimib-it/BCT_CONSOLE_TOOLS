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
