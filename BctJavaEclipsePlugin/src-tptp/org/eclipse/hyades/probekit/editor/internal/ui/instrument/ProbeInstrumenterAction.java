/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbeInstrumenterAction.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui.instrument;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.hyades.probekit.editor.internal.core.util.ConversionUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.hyades.probekit.editor.internal.ui.CompoundLabelProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.CompoundTreeContentProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.DialogUtil;
import org.eclipse.hyades.probekit.editor.internal.ui.JavaElementContentProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.JavaElementLabelProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.ResourceContentProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.ResourceLabelProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.SelectAnythingValidator;
import org.eclipse.hyades.probekit.ui.internal.ContextIds;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IParent;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;


/**
 * When the user selects a .probe file in the workspace, this action 
 * pops up the dialog to choose the classes/wars/ears/jars to instrument 
 * them with, and then instruments the selected resources.
 */
public class ProbeInstrumenterAction implements IActionDelegate {
    private ISelection _selection;

    public void run(IAction action) {
		final IFile probeFile = getProbeFile();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = display.getActiveShell();
		
		List elementsToInstrument = getChosenResources(shell, probeFile);
		if(elementsToInstrument.size() == 0) {
			return;
		}
		
		InstrumentRunnableWithProgress runInstrument = 
			new InstrumentRunnableWithProgress(
					shell,
					display,
					probeFile,
					elementsToInstrument
			);
		runInstrument.open();
    }
    
    private List getChosenResources(Shell shell, IFile probeFile) {
		try {
			IContainer outputContainer = JavaUtil.getOutputContainer(probeFile);
			if(outputContainer == null) {
				// Selected the .probe file in the bin folder by mistake?
				MessageDialog.openError(shell, NLS.bind(ProbekitMessages._90,
						new Object[] { getProbeFile().getName() }),
						ProbekitMessages._89);
				return Collections.EMPTY_LIST;
			}
			
			IClassFile[] generatedClassFiles = JavaUtil.getClassFiles(probeFile);
			IPackageFragmentRoot[] allArchives = ProbeInstrumenterActionUtil.getAllArchivesAsArray();
			IPackageFragmentRoot[] archives = ProbeInstrumenterActionUtil.getExternalArchives(allArchives);
			IFile[] classesAndArchives = ProbeInstrumenterActionUtil.getBinaryFilesExcluding(allArchives, generatedClassFiles);
			List binary = new ArrayList(archives.length + classesAndArchives.length);
			Set temp = new HashSet(archives.length + classesAndArchives.length);
			for(int i=0; i<archives.length; i++) {
				IPackageFragmentRoot archive = archives[i];
				
				binary.add(archive);
				
				IJavaProject jp = archive.getJavaProject();
				if((jp != null) && (jp.exists())) {
					temp.add(jp);
				}
			}
			for(int i=0; i<classesAndArchives.length; i++) {
				IFile file = classesAndArchives[i];
				
				binary.add(file);
				
				IJavaProject jp = JavaCore.create(file.getProject());
				if((jp != null) && (jp.exists())) {
					temp.add(jp);
				}
			}			

			IJavaProject[] javaProjects = (IJavaProject[])temp.toArray(new IJavaProject[0]);
			CompoundLabelProvider labelProvider = 
				new CompoundLabelProvider(
						new ResourceLabelProvider(),
						new JavaElementLabelProvider());

			CompoundTreeContentProvider treeContentProvider = 
				new CompoundTreeContentProvider(
						new ResourceContentProvider(classesAndArchives),
						new JavaElementContentProvider(archives));
			
	    	CheckedTreeSelectionDialog dialog = 
	    		DialogUtil.createCompoundDialog(
		    		shell,
					javaProjects,
					treeContentProvider,
					labelProvider
				);
			dialog.setValidator(new SelectAnythingValidator(binary));
			dialog.setTitle(NLS.bind(ProbekitMessages._94, new Object[]{getProbeFile().getName()}));
			dialog.setMessage(ProbekitMessages._95);
			
			dialog.create(); // create dialog to get its shell
			Shell dialogShell = dialog.getShell();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(dialogShell, ContextIds.PROBEKIT_INSTRUMENT);
			
			dialog.open();
			
			Object[] selected = dialog.getResult();
			if(selected != null) {
				List chosen = treeContentProvider.getClassAndJarElements(selected);
				return chosen;
			}
		}
		catch(CoreException exc) {
    		ProbekitUIPlugin.getPlugin().log(exc);
    		MessageDialog.openError(
    				shell, 
    				NLS.bind(ProbekitMessages._90, new Object[]{getProbeFile().getName()}),
					ProbekitMessages._91
					);
		}

		return Collections.EMPTY_LIST;
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
        _selection = selection;
        
        boolean enabled = true;
        IFile probeFile = getProbeFile();
        if((probeFile == null) || (!probeFile.isAccessible()) || (!ProbekitUtil.isSupportedPlatform())) {
        	enabled = false;
        }
        else {
	        IProject project = probeFile.getProject();
	        IJavaProject jp = JavaCore.create(project);
			enabled = jp.isOnClasspath(probeFile);
        }
        action.setEnabled(enabled);
    }
    
    IFile getProbeFile() {
		if ((_selection == null) || _selection.isEmpty() || !(_selection instanceof IStructuredSelection)) {
			return null;
		}
		
		IStructuredSelection strSel = (IStructuredSelection)_selection;
		Object element = strSel.getFirstElement();
		if(!(element instanceof IFile)) {
		    return null;
		}
		
		return (IFile)element;
    }
    
    private static class ProbeInstrumenterActionUtil {
		public static IPackageFragmentRoot[] getAllArchivesAsArray() throws JavaModelException, CoreException {
			IPackageFragmentRoot[] allArchives = (IPackageFragmentRoot[])getAllArchives().toArray(new IPackageFragmentRoot[0]);
			return allArchives;
		}
	
		public static IPackageFragmentRoot[] getExternalArchives(IPackageFragmentRoot[] allArchives) {
			List result = new ArrayList(allArchives.length); // Do not use a Set because different projects use the same external JARs, e.g. the JRE.
			for(int i=0; i<allArchives.length; i++) {
				IPackageFragmentRoot archive = allArchives[i];
				if(archive.isExternal()) {
					result.add(archive);
				}
			}
			return (IPackageFragmentRoot[])result.toArray(new IPackageFragmentRoot[0]);
		}

		private static List getAllArchives() throws JavaModelException, CoreException {
			IJavaProject[] javaProjects = JavaUtil.getAllJavaProjects();
			List list = new ArrayList(100);
			for(int i=0; i<javaProjects.length; i++) {
				IJavaProject jp = javaProjects[i];
				list.addAll(getArchives(jp));
			}
			
			return list;
		}
		
		private static List getArchives(IJavaElement element) throws JavaModelException, CoreException {
			List allchildren = new ArrayList();
			
			if(JavaUtil.isArchive(element)) {
				allchildren.add(element);
				return allchildren;
			}
		
			if(element instanceof IParent) {
				IParent parent = (IParent)element;
				IJavaElement[] children = parent.getChildren();
				for(int i=0; i<children.length; i++) {
					IJavaElement je = children[i];
					allchildren.addAll(getArchives(je));
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
			List allClassFiles = getAllClassFiles();
			for(int i=0; i<excludedFiles.length; i++) {
				allClassFiles.remove(excludedFiles[i]);
			}
			
			IFile[] files = ConversionUtil.toFileArrayFromIClassFiles(allClassFiles);
			return files;
		}
	
		/**
		 * Return a List of IClassFile.
		 */
		private static List getAllClassFiles() throws JavaModelException, CoreException {
			IJavaProject[] javaProjects = JavaUtil.getAllJavaProjects();
			List classFiles = new ArrayList();
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
			List result = new ArrayList(archives.length); // Do not use a Set because different projects use the same external JARs, e.g. the JRE.
			for(int i=0; i<archives.length; i++) {
				IPackageFragmentRoot archive = archives[i];
				if(!archive.isExternal()) {
					IResource res = archive.getResource();
					if((res != null) && (res.isAccessible())) {
						result.add(res);
					}
				}
			}
			return (IFile[])result.toArray(new IFile[0]);
		}
		
		private static IContainer[] getOutputContainers(IJavaProject jp) throws JavaModelException {
	   		List entries = JavaUtil.getSourceContainerEntries(jp);
			Set containers = new HashSet(entries.size());
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
			return (IContainer[])containers.toArray(new IContainer[0]);
		}
		
	   	private static IClassFile[] getClassFiles(IContainer container) throws CoreException {
			List files = getFiles(container);
			Set classFiles = new HashSet(files.size());
			Iterator iterator = files.iterator();
			while(iterator.hasNext()) {
				IFile file = (IFile)iterator.next();
				IClassFile cfile = JavaUtil.getClassFile(file);
				if(cfile != null) {
					classFiles.add(cfile);
				}
			}
			return (IClassFile[])classFiles.toArray(new IClassFile[0]);
		}
		
		private static List getFiles(IContainer container) throws CoreException {
			IResource[] children = container.members();
			List result = new ArrayList(children.length);
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
    }
}
