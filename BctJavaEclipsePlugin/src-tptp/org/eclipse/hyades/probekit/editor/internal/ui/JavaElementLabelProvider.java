/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JavaElementLabelProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.LabelProvider;


public class JavaElementLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if(element instanceof IJavaElement) {
			IJavaElement jElement = (IJavaElement)element;
			String name = null;
			if(jElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT) {
				IPackageFragmentRoot root = (IPackageFragmentRoot)jElement;
				name = root.getPath().toString();
			}

			if(name == null) {
				name = jElement.getElementName();
			}
			
			return name;
		}
		return super.getText(element);
	}		
}