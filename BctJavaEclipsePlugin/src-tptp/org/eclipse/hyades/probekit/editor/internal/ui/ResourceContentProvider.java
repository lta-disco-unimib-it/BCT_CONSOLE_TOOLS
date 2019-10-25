/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ResourceContentProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;



public class ResourceContentProvider implements ITreeContentProvider {
	private IResource[] _included;
	
	public ResourceContentProvider(IResource[] included) {
		_included = included;
	}
	
	public Object[] getChildren(Object parentElement) {
		IResource res = (IResource)parentElement;
		if(res.getType() == IResource.FILE) {
			return new Object[0];
		}
		
		return ResourceUtil.getDirectChildren((IContainer)parentElement, _included);
	}
	
	public Object getParent(Object element) {
		IResource res = (IResource)element;
		return res.getParent();
	}
	
	public boolean hasChildren(Object element) {
		if(((IResource)element).getType() == IResource.FILE) {
			return false;
		}
		
		IContainer parent = (IContainer)element;
		for(int i=0; i<_included.length; i++) {
			IResource res = _included[i];
			if(ResourceUtil.isMember(parent, res)) {
				return true;
			}
		}
		return false;
	}
	
	public Object[] getElements(Object inputElement) {
		return (Object[])inputElement;
	}
	
	public void dispose() {
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}