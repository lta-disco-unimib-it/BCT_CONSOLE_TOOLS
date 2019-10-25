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
package org.eclipse.core.runtime;

import java.io.File;

public class Path implements IPath {

	private String path;

	public Path(String path){
		this.path = path;
	}
	
	public String toString(){
		return path;
	}

	@Override
	public File toFile() {
		return new File( path );
	}

	@Override
	public boolean isPrefixOf(IPath fullpath) {
		return fullpath.toString().startsWith(this.toString());
	}

	@Override
	public IPath append(String name) {
		return new Path( path + "/" + name);
	}

	public String segment(int i) {
		String _path = path;
		if ( _path.startsWith("/") ){
			_path = _path.substring(1);
		}
		
		String[] segments = path.split("/");
		return segments[i];
	}
}
