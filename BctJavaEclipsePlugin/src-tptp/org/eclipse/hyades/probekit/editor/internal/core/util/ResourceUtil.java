/**********************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ResourceUtil.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

//import com.ibm.icu.util.ULocale;


public final class ResourceUtil {
	public static final String PROBEKIT_EDITOR_ID = "org.eclipse.hyades.probekit.ui"; //$NON-NLS-1$
	public static final String NO_TEXT = ""; //$NON-NLS-1$
	public static final IWorkspaceRoot ROOT = ResourcesPlugin.getWorkspace().getRoot();
	private static String[] languageStrings = null;

	public static IStatus createStatusOk() {
		return createInitialStatus(IStatus.INFO, NO_TEXT, IStatus.OK, null);
	}
	
	public static IStatus createInitialStatus(int severity, String message) {
		return createInitialStatus(severity, message, IStatus.OK, null);
	}
	
	public static IStatus createInitialStatus(int severity, Throwable exc) {
		return createInitialStatus(severity, NO_TEXT, IStatus.OK, exc);
	}
	
	public static IStatus createInitialStatus(int severity, String message, Throwable exc) {
		return createInitialStatus(severity, message, IStatus.OK, exc);
	}
	
	public static IStatus createInitialStatus(int severity, String message, int pluginSpecificCode) {
		return createInitialStatus(severity, message, pluginSpecificCode, null);
	}
	
	public static IStatus createInitialStatus(int severity, String message, int pluginSpecificCode, Throwable exc) {
		IStatus status = new Status(
				severity, 
				PROBEKIT_EDITOR_ID,
				pluginSpecificCode,
				message,
				exc);
		return status;
	}
	
	public static String getString(String string) {
		if(string == null) {
			return NO_TEXT;
		}
		return string;
	}
	
	// Return the file name without the extension
	public static String getBaseName(IFile file) {
		String name = file.getName();
		int dotIndex = name.lastIndexOf('.');
		if(dotIndex == -1) {
			return name;
		}
		return name.substring(0, dotIndex);
	}
		
   	public static Object[] filter(Object[] objects, Object[] toList) {
   		List list = Arrays.asList(toList);
   		return filter(objects, list);
   	}

	/**
	 * Return all of the Object[] that are members of the List
	 */
	public static Object[] filter(Object[] objects, List resources) {
		List temp = new ArrayList(objects.length);
		for(int i=0; i<objects.length; i++) {
			Object obj = objects[i];
			if(resources.contains(obj)) {
				temp.add(obj);
			}
		}
		return temp.toArray();
	}

	/**
	 * If any of the IResource are members of the IContainer, then return the direct 
	 * child of the container that contains the resource. If the direct child of the 
	 * container is the resource itself, return the IResource.
	 */
	public static IResource[] getDirectChildren(IContainer container, IResource[] resources) {
		Set temp = new HashSet(resources.length); // Use a Set because more than one of the resources may be in the same parent.
		for(int i=0; i<resources.length; i++) {
			IResource res = resources[i];
			if(ResourceUtil.isMember(container, res)) {
				temp.add(getDirectChild(container, res));
			}
		}
		return (IResource[])temp.toArray(new IResource[0]);
	}

	/**
	 * Given that the IResource is a member of the IContainer, return the part
	 * of the resource path that is contained directly in the container. For example,
	 *    IContainer: /MyProject/src
	 *    IResource: /MyProject/src/com/yourco/Foo.java
	 * The direct child for these two resources would be /MyProject/src/com.
	 * 
	 * If the IContainer were /MyProject/src/com/yourco, then the child would be
	 * the Foo.java resource itself.
	 */
	private static IResource getDirectChild(IContainer container, IResource res) {
		IPath containerFullPath = container.getFullPath();
		IPath resourceFullPath = res.getFullPath();
		int containerPathCount = containerFullPath.segmentCount();
		int pathLength = resourceFullPath.segmentCount();
		int neededTrim = pathLength - containerPathCount - 1;
		IPath directChildPath = resourceFullPath.removeLastSegments(neededTrim);
		return ResourceUtil.ROOT.findMember(directChildPath);
	}

	/**
	 * Given that the IPath is a member of the IContainer, return the part
	 * of the resource path that is relative to the container. For example,
	 *    IContainer: /MyProject/src
	 *    IPath: /MyProject/src/com/yourco/Foo.java
	 * The result returned would be com/yourco/Foo.java.
	 * 
	 * If the IContainer were /MyProject/src/com/yourco, then the path would be
	 * the Foo.java resource itself. If the IPath is the IContainer, then the
	 * empty string is returned.
	 */
	public static IPath getRelativePath(IContainer container, IPath path) {
		IPath containerFullPath = container.getFullPath();
		int matchingFirstSegments = containerFullPath.matchingFirstSegments(path);
		IPath relPath = path.removeFirstSegments(matchingFirstSegments);
		return relPath;
	}

	/**
	 * Is the IResource contained in the IContainer (i.e., a child)? If
	 * the IResource == IContainer, return false; if the IResource is not
	 * a child of the IContainer return false; otherwise return true.
	 */
	public static boolean isMember(IContainer container, IResource res) {
		if(container.equals(res)) {
			return false;
		}
		
		IPath containerFullPath = container.getFullPath();
		IPath resourceFullPath = res.getFullPath();
		return containerFullPath.isPrefixOf(resourceFullPath);
	}

	public static Set getErrors(IMarker[] markers) throws CoreException {
		Set markerSet = new HashSet(markers.length);
		for(int i=0; i<markers.length; i++) {
			IMarker marker = markers[i];
			Integer severity = (Integer)marker.getAttribute(IMarker.SEVERITY);
			if(severity.intValue() == IMarker.SEVERITY_ERROR) {
				markerSet.add(marker);
			}				
		}
		
		return markerSet;
	}
	
	public static void waitForBuildToFinish(IProgressMonitor monitor) {
		try {
			Platform.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, monitor); // Wait for the auto-build to finish before proceeding.
		}
		catch(InterruptedException exc) {
			// Doesn't matter if the job is interrupted or not.
		}
		catch(OperationCanceledException exc) {
			// Doesn't matter if the job is cancelled or not.
		}
	}
	
//	private static String getLanguageISO3066(ULocale locale) {
//		// xml:lang uses ISO 3066, i.e. something similar to lang-country:
//		// 3-letter lang (ISO 639) and 2-letter country (ISO 3166);
//		// Java uses same standards for language and country.
//		String result = NO_TEXT;
//		String language = locale.getLanguage();
//		String country = locale.getCountry();
//		if (language.length() > 0) {
//			result = country.length() == 0 
//				? language 
//				: language + "-" + country;
//		}
//		return result;
//	}
//	
//	public static String getLanguageDefault() {
//		return getLanguageISO3066(ULocale.getDefault());
//	}
//	
//	public static String[] getLanguages() {
//		if(languageStrings == null) {
//			ULocale[] locales = ULocale.getAvailableLocales();
//			Set languages = new HashSet(locales.length);
//			for (int i = 0; i < locales.length; i++) {
//				ULocale locale = locales[i];
//				String language = getLanguageISO3066(locale);
//				if (language.length() > 0) {
//					languages.add(language);
//				}
//			}
//			languages.add(NO_TEXT); // The default language
//			languageStrings = (String[])languages.toArray(new String[languages.size()]);
//			Arrays.sort(languageStrings);
//		}
//		return languageStrings;
//	}

	private ResourceUtil() {
	}
}
