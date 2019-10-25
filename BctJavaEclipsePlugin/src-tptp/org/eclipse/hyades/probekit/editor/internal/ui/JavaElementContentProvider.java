/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JavaElementContentProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;



public class JavaElementContentProvider implements ITreeContentProvider {
	private IJavaElement[] _included;
	
	public JavaElementContentProvider(IJavaElement[] included) {
		_included = included;
	}
	
	public Object[] getChildren(Object parentElement) {
		IJavaElement jElement = (IJavaElement)parentElement;
		return JavaUtil.getDirectChildren(jElement, _included);
	}
	
	public Object getParent(Object element) {
		IJavaElement jElement = (IJavaElement)element;
		return jElement.getParent();
	}
	
	public boolean hasChildren(Object element) {
		Object[] children = getChildren(element);
		return (children.length > 0);
	}
	
	public Object[] getElements(Object inputElement) {
		return (Object[])inputElement;
	}
	
	public void dispose() {
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}