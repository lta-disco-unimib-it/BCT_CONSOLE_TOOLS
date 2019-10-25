/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbekitUtil.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;


public class ProbekitUtil {
	private static final String PROBE_EXTENSION = "probe"; //$NON-NLS-1$
	public static final String URI_SCHEME_PLATFORM = "platform"; //$NON-NLS-1$
	public static final String URI_RESOURCE = "/resource"; //$NON-NLS-1$
	
	public static final String PROBEKIT_ICON_PATH = "full/wizban/NewProbekit"; //$NON-NLS-1$
	
	
	public static String getDataTypeName(DataItem item) {
		if(item == null) {
			return ResourceUtil.NO_TEXT;
		}
		
		String typeName = item.getType().getName();
		String result = (typeName == null) ? ResourceUtil.NO_TEXT : typeName;
		return result;
	}
	
	public static String getDataItemName(DataItem item) {
		if(item == null) {
			return ResourceUtil.NO_TEXT;
		}
		
		String itemName = item.getName();
		String result = (itemName == null) ? ResourceUtil.NO_TEXT : itemName;
		return result;
	}

	public static List getProbeFiles() throws JavaModelException, CoreException {
		List containers = JavaUtil.getSourceContainers();
		Iterator iterator = containers.iterator();
		List includedResources = new ArrayList(100);
		while(iterator.hasNext()) {
			IContainer container = (IContainer)iterator.next();
			IResource[] members = container.members();
			for(int j=0; j<members.length; j++) {
				IResource resource = members[j];
				if((resource.getType() == IResource.FILE) && (PROBE_EXTENSION.equals(resource.getFileExtension()))) {
					includedResources.add(resource);
				}
			}
		}
		
		return includedResources;
	}

	public static boolean isSupportedPlatform() {
		String os = Platform.getOS();
		if(Platform.OS_LINUX.equals(os)) {
			return true;
		}
		else if(Platform.OS_SOLARIS.equals(os)) {
			return true;
		}
		else if(Platform.OS_WIN32.equals(os)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Prerequisite: the EObject must be an instanceof one of the Probekit EObjects.
	 */
	public static IFile getProbeFile(EObject eObject) {
		Resource resource = eObject.eResource();
		URI uri = resource.getURI(); 
		// TODO: Is there a better way to resolve the resource other than hard-coding
		// the conversion?
		if(uri.scheme().equals(URI_SCHEME_PLATFORM)) {
			String segments = uri.path();
			int index = URI_RESOURCE.length();
			String filePathString = segments.substring(index);
			IFile file = ResourceUtil.ROOT.getFile(new Path(filePathString));
			return file;
		}
		return null;
	}
	
	/**
	 * Prerequisite: the EObject must be an instanceof one of the Probekit EObjects.
	 */
	public static IJavaProject getJavaProject(EObject eObject) {
		IFile probeFile = getProbeFile(eObject);
		if((probeFile != null) && probeFile.isAccessible()) {
			IProject project = probeFile.getProject();
			return JavaCore.create(project);
		}
		return null;
	}
	
	public static Collection unwrap(Collection collection) {
		if(collection == null) {
			return null;
		}
		
		if(collection.isEmpty()) {
			return collection;
		}
		
		List result = new ArrayList();
		Iterator iterator = collection.iterator();
		while(iterator.hasNext()) {
			Object o = iterator.next();
			if (o instanceof EObjectContainmentEList) {
				result.addAll((EObjectContainmentEList) o);
			}
			else {
				result.add(o);
			}
		}
		return result;
	}

	private ProbekitUtil() {
	}
}
