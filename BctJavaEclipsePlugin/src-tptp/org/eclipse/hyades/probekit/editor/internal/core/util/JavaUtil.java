/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JavaUtil.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;



public class JavaUtil {
	public static final String DOT_CLASS_EXTENSION = ".class"; //$NON-NLS-1$
	public static final String DOT_JAVA_EXTENSION = ".java"; //$NON-NLS-1$
	public static final String JAVA_EXTENSION = "java"; //$NON-NLS-1$
	public static final String CLASS_EXTENSION = "class"; //$NON-NLS-1$
	public static final String PROBEKIT_CLASS_FOLDER_NAME = "_probekit_probes_"; //$NON-NLS-1$
	
   	public static IJavaElement[] getDirectChildren(IJavaElement parent, IJavaElement[] children) {
   		Set temp = new HashSet(children.length); // Set instead of List because more than one child may be in the same package.
   		for(int i=0; i<children.length; i++) {
   			IJavaElement element = children[i];
   			IJavaElement directChild = getDirectChild(parent, element);
   			if(directChild != null) {
   				temp.add(directChild);
   			}
   		}
   		return (IJavaElement[])temp.toArray(new IJavaElement[0]);
   	}
   	
   	/**
   	 * Given that the IResource is a member of the IContainer, return the part
   	 * of the resource path that is contained directly in the container. For example,
   	 *    IContainer: /MyProject/src
   	 *    IResource: /MyProject/src/com/yourco/Foo.java
   	 * The direct child for these two resources would be /MyProject/src/com.
   	 * If the IContainer were /MyProject/src/com/yourco, then the child would be
   	 * the Foo.java resource itself.
   	 */
   	private static IJavaElement getDirectChild(IJavaElement parent, IJavaElement element) {
   		IJavaElement directChild = element;
   		for(IJavaElement elementParent = element.getParent(); elementParent != null; elementParent = elementParent.getParent()) {
   			if(parent.equals(elementParent)) {
   				return directChild;
   			}
   			
   			directChild = elementParent;
   		}
   		return null;
   	}
   	
   	public static IPackageFragment[] getAllPackageFragments(IJavaProject jp) throws JavaModelException {
   		IPackageFragmentRoot[] roots = jp.getAllPackageFragmentRoots();
   		List fragments = new ArrayList(roots.length);
   		for(int i=0; i<roots.length; i++) {
   			IPackageFragmentRoot root = roots[i];
   			IJavaElement[] rootFragments = root.getChildren();
   			for(int j=0; j<rootFragments.length; j++) {
   				fragments.add(rootFragments[j]);
   			}
   		}
   		return (IPackageFragment[])fragments.toArray(new IPackageFragment[fragments.size()]);
   	}
   	
	public static IClassFile getClassFile(IFile file) {
		IJavaElement element = JavaCore.create(file);
		IClassFile result = null;
		if((element != null) && (element.getElementType() == IJavaElement.CLASS_FILE)) {
			result = (IClassFile)element;
		}
		return result;
	}
	
	public static IClassFile[] getClassFiles(IFile javaFile) throws JavaModelException {
		ICompilationUnit cu = (ICompilationUnit)JavaCore.create(javaFile);
		if((cu == null) || (!cu.exists())) {
			return new IClassFile[0];
		}
		
		IType[] types = cu.getAllTypes();
		Set files = new HashSet(types.length);
		for(int i=0; i<types.length; i++) {
			IType type = types[i];
			IClassFile cfile = getClassFile(getOutputContainer(javaFile), type);
			if(cfile != null) {
				files.add(cfile);
			}
		}
		
		return (IClassFile[])files.toArray(new IClassFile[0]);
	}
	
	public static IFile[] getCompiledFiles(IClassFile[] cFiles) {
		Set files = new HashSet(cFiles.length);
		for(int i=0; i<cFiles.length; i++) {
			IClassFile cFile = cFiles[i];
			IResource res = cFile.getResource();
			if(res != null) {
				// Not in an external archive
				IFile file = (IFile)res;
				if(CLASS_EXTENSION.equals(file.getFileExtension())) {
					files.add(file);
				}
			}
		}
		return (IFile[])files.toArray(new IFile[files.size()]);
	}
	
	public static IFile[] getCompiledFiles(IFile javaFile) throws JavaModelException {
		IClassFile[] cFiles = getClassFiles(javaFile);
		return getCompiledFiles(cFiles);
	}

	private static IClassFile getClassFile(IContainer outputContainer, IType type) {
		IClassFile cfile = type.getClassFile();
		if(cfile != null) {
			return cfile;
		}
		
		// Else, this type comes from a source type. 
		IPath newFile = new Path(type.getTypeQualifiedName() + DOT_CLASS_EXTENSION);
		IPackageFragment packageFragment = type.getPackageFragment();
		IPath newFilePackage = new Path(packageFragment.getElementName().replace('.', IPath.SEPARATOR));
		IFile file = outputContainer.getFile(newFilePackage.append(newFile));
		return JavaUtil.getClassFile(file);
	}

	public static IJavaProject[] getAllJavaProjects() {
   		IProject[] projects = ResourceUtil.ROOT.getProjects();
   		IJavaProject[] javaProjects = new IJavaProject[projects.length];
   		int count = 0;
   		for(int i=0; i<projects.length; i++) {
   			IJavaProject jp = JavaCore.create(projects[i]);
   			if(jp.exists()) {
   				javaProjects[count++] = jp;
   			}
   		}
   		
   		IJavaProject[] results = new IJavaProject[count];
   		System.arraycopy(javaProjects, 0, results, 0, count);
   		return results;
	}
	
	public static IJavaProject[] getSourceJavaProjects() throws JavaModelException {
		IJavaProject[] jprojects = getAllJavaProjects();
		List temp = new ArrayList(25);
		for(int i=0; i<jprojects.length; i++) {
			IJavaProject jp = jprojects[i];
			List srcContainers = getSourceContainers(jp);
			if(srcContainers.size() > 0) {
				temp.add(jp);
			}
		}
		return (IJavaProject[])temp.toArray(new IJavaProject[0]);
	}
	
	/**
	 * Return the IProjects that contain the given resources.
	 */
	public static IProject[] getProjects(List resources) {
		Set projects = new HashSet(resources.size());
		Iterator iterator = resources.iterator();
		while(iterator.hasNext()) {
			IResource res = (IResource)iterator.next();
			projects.add(res.getProject());
		}
		
		return (IProject[])projects.toArray(new IProject[0]);
	}
	
	/**
	 * The List contains IResource and IJavaElement.
	 */
	public static IJavaProject[] getJavaProjectsFromCompound(List resources) {
		Set projects = new HashSet(resources.size());
		Iterator iterator = resources.iterator();
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			IJavaProject jp = null;
			if(obj instanceof IResource) {
				IResource res = (IResource)obj;
				IProject project = res.getProject();
				jp = JavaCore.create(project);
			}
			else if(obj instanceof IJavaElement) {
				IJavaElement element = (IJavaElement)obj;
				jp = element.getJavaProject();
			}
			
			if((jp != null) && (jp.exists())) {
				projects.add(jp);
			}
		}
		
		return (IJavaProject[])projects.toArray(new IJavaProject[projects.size()]);
	}
	
	public static IProject[] getProjects(IJavaProject[] javaProjects) {
		IProject[] projects = new IProject[javaProjects.length];
		for(int i=0; i<javaProjects.length; i++) {
			projects[i] = javaProjects[i].getProject();
		}
		return projects;
	}
	
	public static boolean isBinaryProject(IJavaProject jp) throws JavaModelException {
		if(jp == null) {
			return false;
		}
		
		return (getSourceContainerEntries(jp).isEmpty());
	}
	
	/**
	 * Create a new class folder on the given Java project and add that folder
	 * to the project's classpath. If the folder exists already, then the folder
	 * is added to the classpath if necessary and the existing folder is returned.
	 */
	public static IPath createClassFolder(IJavaProject jp) throws JavaModelException, CoreException {
		IPath jpPath = jp.getProject().getFullPath();
		IPath newClassFolderPath = jpPath.append(PROBEKIT_CLASS_FOLDER_NAME);
		IFolder newFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(newClassFolderPath);
		
		// First create the IFolder
		if(!newFolder.exists()) {
			newFolder.create(true, true, new NullProgressMonitor());
		}
		
		// Then add the IFolder to the classpath
		if(!jp.isOnClasspath(newFolder)) {
			IClasspathEntry newClassFolderEntry = JavaCore.newLibraryEntry(newClassFolderPath, null, null); // FINISH Pass in probe project source folder as source folder?
			IClasspathEntry[] oldClasspath = jp.getRawClasspath();
			IClasspathEntry[] newClasspath = new IClasspathEntry[oldClasspath.length + 1];
			System.arraycopy(oldClasspath, 0, newClasspath, 0, oldClasspath.length);
			newClasspath[oldClasspath.length] = newClassFolderEntry;
			jp.setRawClasspath(newClasspath, new NullProgressMonitor());
		}
		
		return newClassFolderPath;
	}
	
	public static IContainer[] getClassContainers(IJavaProject jp) throws JavaModelException {
		Set temp = new HashSet();
		IClasspathEntry[] classpath = jp.getResolvedClasspath(true);
		for(int i=0; i<classpath.length; i++) {
			IClasspathEntry entry = classpath[i];
			if(entry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
				IResource resource = ResourceUtil.ROOT.findMember(entry.getPath());
				if((resource != null) && (resource.getType() != IResource.FILE)) {
					temp.add(resource);
				}
			}
		}
		return (IContainer[])temp.toArray(new IContainer[temp.size()]);
	}
	
	/**
	 *  Return the output container where this file will either be copied or compiled into.
	 */
	public static IContainer getOutputContainer(IFile res) throws JavaModelException {
    	IContainer container = res.getParent();
    	IClasspathEntry sourceContainer = getSourceContainerEntry(container);
    	if(sourceContainer == null) {
    		return null;
    	}
    	
		IPath outputPath = sourceContainer.getOutputLocation();
		if(outputPath == null) {
			IJavaProject jp = JavaCore.create(res.getProject());
			outputPath = jp.getOutputLocation();
		}
		return (IContainer)ResourceUtil.ROOT.findMember(outputPath);
	}
	
	/**
	 *  Convenience method equivalent to getSourceContainers(getSourceJavaProjects());
	 */
	public static List getSourceContainers() throws JavaModelException {
		return getSourceContainers(getSourceJavaProjects());
	}
	
	/**
	 * Returns a List of IContainer.
	 */
	public static List getSourceContainers(IJavaProject[] javaProjects) throws JavaModelException {
		List includedResources = new ArrayList(javaProjects.length);
		for(int i=0; i<javaProjects.length; i++) {
			IJavaProject jp = javaProjects[i];
			includedResources.addAll(JavaUtil.getSourceContainers(jp));
		}
		return includedResources;
	}
	
	/**
	 * Returns a List of IContainer.
	 */
   	public static List getSourceContainers(IJavaProject jp) throws JavaModelException {
   		List entries = getSourceContainerEntries(jp);
   		List containers = new ArrayList(entries.size());
   		Iterator iterator = entries.iterator();
   		while(iterator.hasNext()) {
   			IClasspathEntry entry = (IClasspathEntry)iterator.next();
			if(entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IResource resource = getResource(entry);
				if((resource != null) && (resource.isAccessible())) {
					containers.add(resource);
				}
			}
   		}
		return containers;
   	}
   	
   	/**
   	 * Returns a List of IClasspathEntry.
   	 */
   	public static List getSourceContainerEntries(IJavaProject jp) throws JavaModelException {
   		List containers = new ArrayList(10);
   		IProject project = jp.getProject();
   		if(project.isAccessible() && jp.exists()) {
			IClasspathEntry entries[] = jp.getResolvedClasspath(true);
			for(int i=0; i<entries.length; i++) {
				IClasspathEntry entry = entries[i];
				if(entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					containers.add(entry);
				}
			}
   		}
		
		return containers;
   	}
   	
   	private static IClasspathEntry getSourceContainerEntry(IContainer container) throws JavaModelException {
    	IJavaProject jp = JavaCore.create(container.getProject());
   		List entries = getSourceContainerEntries(jp);
   		Iterator iterator = entries.iterator();
   		while(iterator.hasNext()) {
   			IClasspathEntry entry = (IClasspathEntry)iterator.next();
			if(entry.getPath().isPrefixOf(container.getFullPath())){
				return entry;
			}
		}
    	return null;
    }
   	
   	public static IContainer getSourceContainer(String containerPath) throws JavaModelException {
		IResource res = ResourceUtil.ROOT.findMember(containerPath);
		if(res == null) {
			return null;
		}
		
		if(!res.isAccessible()) {
			return null;
		}

		if(res.getType() == IResource.FILE) {
			return null;
		}
		
		IContainer container = (IContainer)res;
		if(JavaUtil.isSourceContainer(container)) {
			return container;
		}
		return null;
   	}
   	
   	private static IResource getResource(IClasspathEntry entry) {
		return ResourceUtil.ROOT.findMember(entry.getPath());
   	}
   	
   	public static boolean isSourceContainer(IContainer container) throws JavaModelException {
		IProject project = container.getProject();
		IJavaProject jp = JavaCore.create(project);
		if(jp == null) {
			return false;
		}
		
		List sourceContainers = getSourceContainers(jp);
		if(sourceContainers.contains(container)) {
			return true;
		}
		
		return false;
   	}
   	
	public static boolean isClassFile(IJavaElement element) {
		int elementType = element.getElementType();
		if(elementType == IJavaElement.CLASS_FILE) {
			IResource res = element.getResource();
			if(res != null) {
				// If the resource doesn't exist, then this is a class file
				// inside of an archive, and it can't be instrumented separately.
				
				// Do not check if the resource is accessible or not because
				// if the resource is not null, then this file is a .class file,
				// and if it doesn't exist then operations such as InstrumentOperation
				// need to be able to fail becuase it doesn't exist.
				return true;
			}
		}
		return false;
	}
	
   	public static boolean isArchive(IJavaElement element) {
		int elementType = element.getElementType();
		if(elementType == IJavaElement.PACKAGE_FRAGMENT_ROOT) {
			IPackageFragmentRoot root = (IPackageFragmentRoot)element;
			if(root.isArchive()) {
				return true;
			}
		}
		
		return false;
   	}

   	private JavaUtil() {
	}
}
