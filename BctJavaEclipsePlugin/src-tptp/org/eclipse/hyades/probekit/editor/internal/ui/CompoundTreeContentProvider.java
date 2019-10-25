/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: CompoundTreeContentProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;



public class CompoundTreeContentProvider implements ITreeContentProvider {
	private ResourceContentProvider _resourceTreeContentProvider;
	private JavaElementContentProvider _javaTreeContentProvider;
	
	public CompoundTreeContentProvider(ResourceContentProvider resourceProvider, JavaElementContentProvider javaProvider) {
		_resourceTreeContentProvider = resourceProvider;
		_javaTreeContentProvider = javaProvider;
	}
	
	private ITreeContentProvider getTreeContentProvider(Object element) {
		if(element instanceof IResource) {
			return _resourceTreeContentProvider;
		}
		else if(element instanceof IJavaElement) {
			return _javaTreeContentProvider;
		}
		return null;
	}
	
	/**
	 * Return the elements in the Object[] that are not contained in another 
	 * element in the array. For example, if an IFile is in the array, and that 
	 * file's IContainer is also in the array, then return only the IContainer.
	 * 
	 * The Object[] is made up of IResource, IPackageFragmentRoot, and IClassFile.
	 */
	public List getBaseJavaElements(Object[] selected) {
		// The input to the viewer was IJavaProject[], and external JAR files
		// were represented as IPackageFragmentRoot, and workspace resources
		// were represented as IResource.
		List selectedList = Arrays.asList(selected);
		for(int i=0; i<selected.length; i++) {
			Object selection = selected[i];
			IContainer container = null;
			if(selection instanceof IJavaProject) {
				IJavaProject jp = (IJavaProject)selection;
				container = jp.getProject();
			}
			else if (selection instanceof IContainer) {
				container = (IContainer)selection;
			}
			
			if(container != null) {
				selectedList = CompoundTreeContentProviderUtil.removeMembers(container, selectedList);
			}
		}
		return selectedList;
	}
	
	/* Filter selected objects and leave only files (only classes will get here) and jars.
	 * The model is mixed, i.e. some object are resources, others come from Java model 
	 */
	public List getClassAndJarElements(Object[] selected) {
		List selectedList = new ArrayList();
		for(int i=0; i<selected.length; i++) {
			Object selection = selected[i];
			if(selection instanceof IFile ) {
				selectedList.add(selection);
			} else if(selection instanceof IPackageFragmentRoot) {
				IPackageFragmentRoot pr = (IPackageFragmentRoot)selection;
				if(pr.isArchive()) {
					selectedList.add(selection);
				}
			}
		}	
		return selectedList;
	}
   	
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof IJavaProject) {
			IJavaProject jp = (IJavaProject)parentElement;
			Object[] resourceChildren = _resourceTreeContentProvider.getChildren(jp.getProject());
			Object[] javaChildren = _javaTreeContentProvider.getChildren(jp);
			Object[] result = new Object[javaChildren.length + resourceChildren.length];
			System.arraycopy(resourceChildren, 0, result, 0, resourceChildren.length);
			System.arraycopy(javaChildren, 0, result, resourceChildren.length, javaChildren.length);
			return result;
		}
		else {
			ITreeContentProvider provider = getTreeContentProvider(parentElement);
			if(provider == null) {
				return new Object[0];
			}
			else {
				return provider.getChildren(parentElement);
			}
		}
	}
	
	public Object getParent(Object element) {
		ITreeContentProvider provider = getTreeContentProvider(element);
		if(provider == null) {
			return null;
		}
		else {
			Object parent = provider.getParent(element);
			if(parent instanceof IProject) {
				return JavaCore.create((IProject)parent);
			}
			else {
				return parent;
			}
		}
	}
	
	public boolean hasChildren(Object element) {
		ITreeContentProvider provider = getTreeContentProvider(element);
		if(provider == null) {
			return false;
		}
		else {
			return provider.hasChildren(element);
		}
	}
	
	public Object[] getElements(Object inputElement) {
		return (Object[])inputElement;
	}
	
	public void dispose() {
		_resourceTreeContentProvider.dispose();
		_javaTreeContentProvider.dispose();
		_resourceTreeContentProvider = null;
		_javaTreeContentProvider = null;
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
	private static class CompoundTreeContentProviderUtil {
		public static List removeMembers(IContainer container, List compound) {
			Iterator iterator = compound.iterator();
			List trimmedList = new ArrayList(compound.size());
			while(iterator.hasNext()) {
				Object obj = iterator.next();
				boolean remove = false;
				if(obj instanceof IResource) {
					if(ResourceUtil.isMember(container, (IResource)obj)) {
						remove = true;
					}
				}
				
				if(!remove) {
					trimmedList.add(obj);
				}
			}
			return trimmedList;
		}
	}
}
