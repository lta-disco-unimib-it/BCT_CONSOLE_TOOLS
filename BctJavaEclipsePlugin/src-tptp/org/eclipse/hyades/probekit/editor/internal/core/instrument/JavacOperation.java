/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JavacOperation.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.instrument;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jdt.core.JavaCore;



public class JavacOperation implements IWorkspaceRunnable {
	private IProject _project;

	public JavacOperation(IProject project) {
		_project = project;
	}
	
	public void run(IProgressMonitor monitor) throws CoreException {
		IProjectDescription description= _project.getDescription();
		ICommand javaBuilder = JavacOperationUtil.getJavaCommand(description);
		if(javaBuilder != null) {
			_project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, JavaCore.BUILDER_ID, javaBuilder.getArguments(), monitor);
		}
	}

	public static class JavacOperationUtil {
		public static IMarker[] getCompileErrors(IFile[] files) throws CoreException {
			Set markers = new HashSet();
			for(int i=0; i<files.length; i++) {
				IFile file = files[i];
				Set compileErrors = getCompileErrors(file);
				markers.addAll(compileErrors);
			}
			
			return (IMarker[])markers.toArray(new IMarker[markers.size()]);
		}
		
		public static boolean hasCompileErrors(IFile[] files) throws CoreException {
			for(int i=0; i<files.length; i++) {
				IFile file = files[i];
				if(hasCompileErrors(file)) {
					return true;
				}
			}
			
			return false;
		}
		
		public static boolean hasCompileErrors(IResource resource) throws CoreException {
			return (getCompileErrors(resource).size() > 0);
		}
		
		private static Set getCompileErrors(IResource resource) throws CoreException {
			IMarker[] markers = resource.findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true,  IResource.DEPTH_INFINITE);
			return ResourceUtil.getErrors(markers);
		}

		public static ICommand getJavaCommand(IProjectDescription description) {
			if (description == null) {
				return null;
			}
		
			ICommand[] commands = description.getBuildSpec();
			for (int i = 0; i < commands.length; ++i) {
				if (JavaCore.BUILDER_ID.equals(commands[i].getBuilderName())) {
					return commands[i];
				}
			}
			return null;
		}
	}
}
