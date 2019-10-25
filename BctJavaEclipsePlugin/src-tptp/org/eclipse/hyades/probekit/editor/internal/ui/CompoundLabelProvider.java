/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: CompoundLabelProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.LabelProvider;


public class CompoundLabelProvider extends LabelProvider {
	private final ResourceLabelProvider _resourceLabelProvider;
	private final JavaElementLabelProvider _javaLabelProvider;
	
	public CompoundLabelProvider(ResourceLabelProvider resourceLabels, JavaElementLabelProvider javaLabels) {
		_resourceLabelProvider = resourceLabels;
		_javaLabelProvider = javaLabels;
	}
	
	private LabelProvider getLabelProvider(Object element) {
		if(element instanceof IResource) {
			return _resourceLabelProvider;
		}
		else if(element instanceof IJavaElement) {
			return _javaLabelProvider;
		}
		return null;
	}
	
	public String getText(Object element) {
		LabelProvider provider = getLabelProvider(element);
		if(provider == null) {
			return super.getText(element);
		}
		else {
			return provider.getText(element);
		}
	}	
}
