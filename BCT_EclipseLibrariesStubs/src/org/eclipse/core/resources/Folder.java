package org.eclipse.core.resources;

import java.io.File;

import org.eclipse.core.runtime.IPath;

public class Folder extends EclipseFile implements IFolder {


	public Folder(File file) {
		super(file);
	}

	@Override
	public IFile getFile(IPath path) {
		return new EclipseFile ( new File( getRawLocation()+"/"+path.toString() ) );
	}

}
