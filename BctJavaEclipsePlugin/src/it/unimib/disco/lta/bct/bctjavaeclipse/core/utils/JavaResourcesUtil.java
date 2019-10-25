package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

/**********************************************************************
 * Copyright (c) 2005, 2019 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JavaResourcesUtil.java,v 1.3 2014-02-13 22:03:00 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/





import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.hyades.probekit.editor.internal.core.util.ConversionUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IParent;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;



    
public class JavaResourcesUtil {
	
	public static IType getType( MonitoringConfiguration conf, String className ) throws JavaModelException{

		for ( IPackageFragmentRoot packageF : conf.getResourcesMonitoringOptions().getAllMonitoredFragmentRoots() ){
			IJavaProject jProject = packageF.getJavaProject();
			IType type = jProject.findType(className);
			
			if ( type != null ){
				return type;
			}
		}
		return null;
//		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(conf.getReferredProjectName());
//		
//		IJavaProject jProject = JavaCore.create(project);
//		
//		
//		return jProject.findType(className);
	}
	
	public static IClassFile getClassFile( MonitoringConfiguration conf, String className ) throws JavaModelException{
		
		//IType type = getType(conf, className);
		
		for ( IPackageFragmentRoot packageF : conf.getResourcesMonitoringOptions().getAllMonitoredFragmentRoots() ){
			IJavaProject jProject = packageF.getJavaProject();
			IType type = jProject.findType(className);
			
			if ( type != null ){
				if ( type.getClassFile() != null ) {
					return type.getClassFile();
				}
			}
		}
		
		return null;

	}
	
	public static ICompilationUnit getCompilationUnit( MonitoringConfiguration conf, String className ) throws JavaModelException{
		
		//IType type = getType(conf, className);
		
		for ( IPackageFragmentRoot packageF : conf.getResourcesMonitoringOptions().getAllMonitoredFragmentRoots() ){
			IJavaProject jProject = packageF.getJavaProject();
			IType type = jProject.findType(className);
			
			if ( type != null ){
				if ( type.getCompilationUnit() != null ) {
					return type.getCompilationUnit();
				}
			}
		}
		
		return null;

	}
	
	public static IPackageFragmentRoot[] getAllArchivesAsArray() throws JavaModelException, CoreException {
		IPackageFragmentRoot[] allArchives = getAllArchives().toArray(new IPackageFragmentRoot[0]);
		return allArchives;
	}

	public static IPackageFragmentRoot[] getExternalArchives(IPackageFragmentRoot[] allArchives) {
		List<IPackageFragmentRoot> result = new ArrayList<IPackageFragmentRoot>(allArchives.length); // Do not use a Set because different projects use the same external JARs, e.g. the JRE.
		for(int i=0; i<allArchives.length; i++) {
			IPackageFragmentRoot archive = allArchives[i];
			if(archive.isExternal()) {
				result.add(archive);
			}
		}
		return result.toArray(new IPackageFragmentRoot[0]);
	}

	private static List<IJavaElement> getAllArchives() throws JavaModelException, CoreException {
		IJavaProject[] javaProjects = JavaUtil.getAllJavaProjects();
		List<IJavaElement> list = new ArrayList<IJavaElement>(100);
		for(int i=0; i<javaProjects.length; i++) {
			IJavaProject jp = javaProjects[i];
			list.addAll(getArchives(jp));
		}

		return list;
	}

	private static List<IJavaElement> getArchives(IJavaElement element) {
		List<IJavaElement> allchildren = new ArrayList<IJavaElement>();

		if(JavaUtil.isArchive(element)) {
			allchildren.add(element);
			return allchildren;
		}

		if(element instanceof IParent) {
			IParent parent = (IParent)element;
			IJavaElement[] children;
			try {
				children = parent.getChildren();

				for(int i=0; i<children.length; i++) {
					IJavaElement je = children[i];
					allchildren.addAll(getArchives(je));
				}
			} catch (JavaModelException e) {
				//if we trow it we should not be able to instrument applications if "strange" projects are presnt in the workspace 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allchildren;
	}

	public static IFile[] getBinaryFilesExcluding(IPackageFragmentRoot[] allArchives, IClassFile[] excludedFiles) throws JavaModelException, CoreException {
		IFile[] classFiles = getAllClassFilesExcluding(excludedFiles);
		IFile[] archives = getWorkspaceArchives(allArchives);
		IFile[] result = new IFile[classFiles.length + archives.length];
		System.arraycopy(classFiles, 0, result, 0, classFiles.length);
		System.arraycopy(archives, 0, result, classFiles.length, archives.length);
		return result;
	}

	private static IFile[] getAllClassFilesExcluding(IClassFile[] excludedFiles) throws JavaModelException, CoreException {
		List<IClassFile> allClassFiles = getAllClassFiles();
		for(int i=0; i<excludedFiles.length; i++) {
			allClassFiles.remove(excludedFiles[i]);
		}

		IFile[] files = ConversionUtil.toFileArrayFromIClassFiles(allClassFiles);
		return files;
	}

	/**
	 * Return a List of IClassFile.
	 */
	private static List<IClassFile> getAllClassFiles() throws JavaModelException, CoreException {
		IJavaProject[] javaProjects = JavaUtil.getAllJavaProjects();
		List<IClassFile> classFiles = new ArrayList<IClassFile>();
		for(int i=0; i<javaProjects.length; i++) {
			IJavaProject jp = javaProjects[i];
			IContainer[] outputContainers = getOutputContainers(jp);
			for(int j=0; j<outputContainers.length; j++) {
				IContainer container = outputContainers[j];
				IClassFile[] cFiles = getClassFiles(container);
				for(int k=0; k<cFiles.length; k++) {
					classFiles.add(cFiles[k]);
				}
			}

			IContainer[] classContainers = JavaUtil.getClassContainers(jp);
			for(int j=0; j<classContainers.length; j++) {
				IContainer container = classContainers[j];
				IClassFile[] cFiles = getClassFiles(container);
				for(int k=0; k<cFiles.length; k++) {
					classFiles.add(cFiles[k]);
				}
			}
		}
		return classFiles;
	}

	private static IFile[] getWorkspaceArchives(IPackageFragmentRoot[] archives) {
		List<IResource> result = new ArrayList<IResource>(archives.length); // Do not use a Set because different projects use the same external JARs, e.g. the JRE.
		for(int i=0; i<archives.length; i++) {
			IPackageFragmentRoot archive = archives[i];
			if(!archive.isExternal()) {
				IResource res = archive.getResource();
				if((res != null) && (res.isAccessible())) {
					result.add(res);
				}
			}
		}
		return result.toArray(new IFile[0]);
	}

	private static IContainer[] getOutputContainers(IJavaProject jp) throws JavaModelException {
		List entries = JavaUtil.getSourceContainerEntries(jp);
		Set<IContainer> containers = new HashSet<IContainer>(entries.size());
		Iterator iterator = entries.iterator();
		while(iterator.hasNext()) {
			IClasspathEntry entry = (IClasspathEntry)iterator.next();
			IPath outputLocation = entry.getOutputLocation();
			if(outputLocation != null) {
				IContainer container = (IContainer)ResourceUtil.ROOT.findMember(outputLocation);
				if((container != null) && (container.isAccessible())) {
					containers.add(container);
				}
			}
		}

		IPath outputLocation = jp.getOutputLocation();
		IContainer container = (IContainer)ResourceUtil.ROOT.findMember(outputLocation);
		if((container != null) && (container.isAccessible())) {
			containers.add(container);
		}
		return containers.toArray(new IContainer[0]);
	}

	private static IClassFile[] getClassFiles(IContainer container) throws CoreException {
		List<IResource> files = getFiles(container);
		Set<IClassFile> classFiles = new HashSet<IClassFile>(files.size());
		Iterator<IResource> iterator = files.iterator();
		while(iterator.hasNext()) {
			IFile file = (IFile)iterator.next();
			IClassFile cfile = JavaUtil.getClassFile(file);
			if(cfile != null) {
				classFiles.add(cfile);
			}
		}
		return classFiles.toArray(new IClassFile[0]);
	}

	private static List<IResource> getFiles(IContainer container) throws CoreException {
		IResource[] children = container.members();
		List<IResource> result = new ArrayList<IResource>(children.length);
		for(int i=0; i<children.length; i++) {
			IResource res = children[i];
			if(res.getType() == IResource.FILE) {
				result.add(res);
			}
			else {
				result.addAll(getFiles((IContainer)res));
			}
		}
		return result;
	}
	
	/**
	 * Return the list of source containers of a java project
	 * 
	 * @param project
	 * @return
	 * @throws JavaModelException
	 */
	public static List<IResource> getSourceContainers(IJavaProject project) throws JavaModelException{
		return JavaUtil.getSourceContainers(project);
	}

	public static List<ICompilationUnit> getCompilationUnitsFromSelection( ISelection selection) throws JavaModelException {
		List<ICompilationUnit> compilationUnits = new ArrayList<ICompilationUnit>();
		IStructuredSelection selectedFiles = (IStructuredSelection) selection;
		Iterator filesIteraror = selectedFiles.iterator();
		
		while ( filesIteraror.hasNext() ){
			Object selectedObject = filesIteraror.next();
			extractCompilationUnitsToCollection( compilationUnits, selectedObject );
		}
		
		return compilationUnits;
	}

	private static void extractCompilationUnitsToCollection(
			List<ICompilationUnit> compilationUnits, Object selectedObject) throws JavaModelException {

		if ( selectedObject instanceof ICompilationUnit ) {
			compilationUnits.add( (ICompilationUnit) selectedObject );
		} else if ( selectedObject instanceof IPackageFragment ) {
			IPackageFragment frag = (IPackageFragment) selectedObject;
			compilationUnits.addAll( Arrays.asList( frag.getCompilationUnits() ) );
		} else if ( selectedObject instanceof IPackageFragmentRoot ) {
			IPackageFragmentRoot froot = (IPackageFragmentRoot) selectedObject;
			for ( IJavaElement frag : froot.getChildren() ){
				extractCompilationUnitsToCollection(compilationUnits, frag);
			}
		} else if ( selectedObject instanceof IJavaProject ) {
			IJavaProject prj = (IJavaProject) selectedObject;
			for ( IPackageFragmentRoot pfr : prj.getAllPackageFragmentRoots() ){
				extractCompilationUnitsToCollection(compilationUnits, pfr);	
			}
		} else  {
			
		}
	}

	public static IMethod retrieveIMethod(IJavaProject jprj, String methodFullSignature) throws JavaModelException {
		
		
		
		int parStart = methodFullSignature.indexOf('(');
		
		if ( parStart < 0 ){
			return null;
		}
		
		String typeName = methodFullSignature.substring(0, parStart);
		
		
		//necessary for gnu.trove.list.linked.TPrimitiveLinkedListTest$2.<init>((Lgnu.trove.list.linked.TPrimitiveLinkedListTest;)V)
		int dotpos = typeName.lastIndexOf('.');
		typeName = typeName.substring(0, dotpos);
		
		System.out.println( methodFullSignature + " "+ dotpos+" "+parStart);
		if ( parStart < 0 || dotpos < 0 ){
			System.out.println(methodFullSignature);
		}
		
		String methodName = methodFullSignature.substring(dotpos+1,parStart);
		
		IType type = jprj.findType(typeName);
		if ( type == null ){
			return null;
		}
		
		String signature = methodFullSignature.substring(parStart);
		signature = signature.substring(1,signature.length()-1);
		for ( IMethod imethod : type.getMethods() ){
//			System.out.println(imethod.getSignature());
			if ( "<init>".equals(methodName) && imethod.isConstructor() && imethod.getSignature().equals(signature)
					|| imethod.getElementName().equals(methodName) &&  imethod.getSignature().equals(signature) ){
				
				return imethod;
			}
		}
		
		return null;
	}
}

