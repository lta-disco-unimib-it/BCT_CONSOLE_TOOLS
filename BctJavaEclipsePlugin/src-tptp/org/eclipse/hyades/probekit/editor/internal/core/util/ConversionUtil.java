/********************************************************************** 
 * Copyright (c) 2005, 2006 IBM Corporation and others. 
 * All rights reserved.   This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html         
 * $Id: ConversionUtil.java,v 1.1 2011-12-01 21:34:09 pastore Exp $ 
 * 
 * Contributors: 
 * IBM - Initial API and implementation 
 **********************************************************************/ 


package org.eclipse.hyades.probekit.editor.internal.core.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;


public final class ConversionUtil {
	public static final char FILE_SEPARATOR = System.getProperty("file.separator").charAt(0); //$NON-NLS-1$
	public static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	public static String toOSStringFromURL(URL url) throws IOException {
		URL localURL = FileLocator.toFileURL(url);
		String result = localURL.getPath();
		if(Platform.OS_WIN32.equals(Platform.getOS())) {
			if(result.charAt(0) == '/') {
				result = result.substring(1);
			}
			
			result = result.replace(IPath.SEPARATOR, FILE_SEPARATOR);
		}
		return result;
	}
	
	/**
	 * The List contains IResource, IJavaElement, or both.
	 */
	
	public static IResource[] toResourceFromJavaElements(IJavaElement[] elements) {
		Set resources = new HashSet(elements.length);
		for(int i=0; i<elements.length; i++) {
			IJavaElement element = elements[i];
			IResource resource = element.getResource();
			if((resource!=null) && (resource.isAccessible())) {
				resources.add(resource);
			}
		}
		return (IResource[])resources.toArray(new IResource[0]);
	}
	
	/**
	 * The List contains IResource, IJavaElement, or both.
	 */
	public static IResource[] toResourceFromCompounds(List elements) {
		Set resources = new HashSet(elements.size());
		Iterator iterator = elements.iterator();
		while(iterator.hasNext()) {
			Object element = iterator.next();
			if(element instanceof IResource) {
				resources.add(element);
			}
			else if(element instanceof IJavaElement) {
				IResource res = ((IJavaElement)element).getResource();
				if((res != null) && (res.isAccessible())) {
					resources.add(res);
				}
			}
		}
		return (IResource[])resources.toArray(new IResource[0]);
	}
	
	/**
	 * List must contain only IClassFile.
	 */
	public static IFile[] toFileArrayFromIClassFiles(List classFiles) {
		Set files = new HashSet(classFiles.size());
		Iterator iterator = classFiles.iterator();
		while(iterator.hasNext()) {
			IClassFile cFile = (IClassFile)iterator.next();
			IResource res = cFile.getResource();
			if(res != null) {
				files.add(res);
			}
		}
		return (IFile[])files.toArray(new IFile[0]);
	}
	
	public static IJavaElement[] toJavaElementFromResources(IResource[] resources) {
		Set elements = new HashSet(resources.length);
		for(int i=0; i<resources.length; i++) {
			IResource res = resources[i];
			IJavaElement element = JavaCore.create(res);
			if((element != null) && (element.exists())) {
				elements.add(element);
			}
		}
		return (IJavaElement[])elements.toArray(new IJavaElement[0]);
	}
	
	public static IJavaElement[] castToJavaElementFromArray(Object[] obj) {
		IJavaElement[] result = new IJavaElement[obj.length];
		for(int i=0; i<obj.length; i++) {
			result[i] = (IJavaElement)obj[i];
		}
		return result;
	}
	
	public static IJavaElement[] castToJavaElementFromCollection(Collection collection) {
		return (IJavaElement[])collection.toArray(new IJavaElement[0]);
	}
	
	public static String toLineDelimitedString(List list) {
		StringBuffer buffer = new StringBuffer(ConversionUtil.LINE_SEPARATOR);
		Iterator iterator = list.iterator();
		while(iterator.hasNext()) {
			buffer.append(iterator.next());
			buffer.append(ConversionUtil.LINE_SEPARATOR);
		}
		return buffer.toString();
	}
	
	public static String toLineDelimitedString(String[] string) {
		StringBuffer buffer = new StringBuffer(ConversionUtil.LINE_SEPARATOR);
		for(int i=0; i<string.length; i++) {
			buffer.append(string[i]);
			buffer.append(ConversionUtil.LINE_SEPARATOR);
		}
		return buffer.toString();
	}
	
	public static String toLineDelimitedString(IMarker[] markers) throws CoreException {
		StringBuffer buffer = new StringBuffer(ConversionUtil.LINE_SEPARATOR);
		for(int i=0; i<markers.length; i++) {
			IMarker marker = markers[i];
			Object message = marker.getAttribute(IMarker.MESSAGE);
			buffer.append(message);
			buffer.append(ConversionUtil.LINE_SEPARATOR);
		}
		return buffer.toString();
	}
	
	private ConversionUtil() {
	}
}
