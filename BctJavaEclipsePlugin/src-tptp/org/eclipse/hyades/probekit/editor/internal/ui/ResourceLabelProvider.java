/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ResourceLabelProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;


public class ResourceLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if(element instanceof IResource) {
			return ((IResource)element).getName();
		}
		return super.getText(element);
	}		
}