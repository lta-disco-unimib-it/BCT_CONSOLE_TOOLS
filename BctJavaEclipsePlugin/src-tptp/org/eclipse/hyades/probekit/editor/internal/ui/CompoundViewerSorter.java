/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: CompoundViewerSorter.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


public class CompoundViewerSorter extends ViewerSorter {
	public int compare(Viewer viewer, Object e1, Object e2) {
		// Sort workspace resources before external archives,
		// and then sort by name.
		if(e1 instanceof IResource) {
			if(e2 instanceof IResource) {
				return super.compare(viewer, e1, e2);
			}
			else if(e2 instanceof IJavaElement) {
				return -1;
			}
		}
		else if(e1 instanceof IJavaElement) {
			if(e2 instanceof IResource) {
				return 1;
			}
			else if(e2 instanceof IJavaElement) {
				return super.compare(viewer, e1, e2);
			}
		}
		return super.compare(viewer, e1, e2);
	}
}
