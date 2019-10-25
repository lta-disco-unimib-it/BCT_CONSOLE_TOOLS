package org.eclipse.core.runtime;

import java.io.File;

public interface IPath {
	

	public File toFile();

	public boolean isPrefixOf(IPath fullpath);

	public IPath append(String name);
	
}
