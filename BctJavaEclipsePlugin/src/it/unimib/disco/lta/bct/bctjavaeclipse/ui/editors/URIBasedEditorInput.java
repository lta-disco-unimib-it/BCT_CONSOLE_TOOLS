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
