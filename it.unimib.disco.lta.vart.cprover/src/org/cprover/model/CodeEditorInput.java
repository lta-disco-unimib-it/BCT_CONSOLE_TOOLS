package org.cprover.model;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.editors.text.ILocationProvider;

public class CodeEditorInput implements IEditorInput, ILocationProvider {

	private File file;

	public CodeEditorInput(File file) {
		this.file= file;
	}

	public boolean exists() {
		return file.exists();
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return file.getName();
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return file.getAbsolutePath();
	}

	public Object getAdapter(Class adapter) {
		if (ILocationProvider.class.equals(adapter))
			return this;
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public IPath getPath(Object element) {
		if (element instanceof CodeEditorInput) {
			CodeEditorInput input= (CodeEditorInput) element;
			return new Path(input.file.getAbsolutePath());
		}
		return null;
	}
	
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o instanceof CodeEditorInput) {
			CodeEditorInput input = (CodeEditorInput) o;
			return file.equals(input.file);		
		}
		
		return false;
	}
	
	public int hashCode() {
		return file.hashCode();
	}
}
