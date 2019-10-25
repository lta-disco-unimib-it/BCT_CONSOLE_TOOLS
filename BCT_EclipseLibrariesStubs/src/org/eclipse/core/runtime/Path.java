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
