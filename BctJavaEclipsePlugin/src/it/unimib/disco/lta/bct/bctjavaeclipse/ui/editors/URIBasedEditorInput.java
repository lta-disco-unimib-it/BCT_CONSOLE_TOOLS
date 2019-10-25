package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import java.net.URI;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IURIEditorInput;

public class URIBasedEditorInput extends PlatformObject implements IURIEditorInput, IPersistableElement {

	private URI uri;
	
	public URIBasedEditorInput(URI uri) {
		this.uri = uri;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof URIBasedEditorInput) {
			URIBasedEditorInput input = (URIBasedEditorInput)obj;
			if (input.getURI().equals(uri))
				return true;
		}
		return false;
	}
	
	public URI getURI() {
		return uri;
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFactoryId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub
		
	}
}
