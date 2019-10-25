/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: GenerateOperation.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.instrument;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.hyades.probekit.CompilerFactory;
import org.eclipse.hyades.probekit.IProbeCompiler;
import org.eclipse.hyades.probekit.ProbekitException;
import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.JavaModelException;



public class GenerateOperation implements IWorkspaceRunnable {
	private IFile _probeFile;

	public GenerateOperation(IFile probeFile) {
		_probeFile = probeFile;
	}
	
	private IFile getProbeFile() {
		return _probeFile;
	}

	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			IFile file = getProbeFile();
			IContainer fileContainer = file.getParent();
			file.deleteMarkers(IProbeCompiler.PROBEKIT_PROBLEM_MARKER, true, IResource.DEPTH_ZERO);
			IProbeCompiler c = CompilerFactory.INSTANCE.createCompiler();
			String basename = ResourceUtil.getBaseName(file);
			basename = c.makeValidJavaIdentifier(basename);
			c.setClassPrefix(basename);
			c.addIFile(file);
			String generatedSource = c.getGeneratedSource();
			
			// Create the _probe.java file.
			String suffix = c.getClassSuffix();
			IFile javaSourceIFile = fileContainer.getFile(new Path(basename + suffix + JavaUtil.DOT_JAVA_EXTENSION)); //$NON-NLS-1$
			ByteArrayInputStream newJavaContents = new ByteArrayInputStream(generatedSource.getBytes());
			if (javaSourceIFile.exists()) {
				javaSourceIFile.setContents(newJavaContents, true, false, monitor);
			}
			else {
				javaSourceIFile.create(newJavaContents, true, monitor);
			}
	
			// Create the .probescript file.
			String engineScript = c.getEngineScript();
			IFile engineScriptIFile = fileContainer.getFile(new Path(basename + GenerateOperationUtil.DOT_PROBESCRIPT_EXTENSION)); //$NON-NLS-1$
			ByteArrayInputStream newScriptContents = new ByteArrayInputStream(engineScript.getBytes());
			if (engineScriptIFile.exists()) {
				engineScriptIFile.setContents(newScriptContents, true, false, monitor);
			}
			else {
				engineScriptIFile.create(newScriptContents, true, monitor);
			}
		}
		catch(ProbekitException exc) {
			IStatus status = ResourceUtil.createInitialStatus(IStatus.ERROR, exc);
			throw new CoreException(status);
		}
	}
	
	public final static class GenerateOperationUtil {
		private static final String SCRIPT_EXTENSION = "script"; //$NON-NLS-1$
		private static final String GEN_JAVA_EXTENSION = "_probe.java"; //$NON-NLS-1$
		public static final String DOT_PROBESCRIPT_EXTENSION = ".probescript"; //$NON-NLS-1$

		public static IFile[] getProbekitGeneratedFiles(IFile probeFile) {
			return new IFile[]{
					getProbeScriptFile(probeFile),
					getProbeJavaFile(probeFile)
			};
		}
	
		/**
		 * Convenience method equivalent to JavaUtil.getClassFiles(getProbeJavaFile(probeFile))
		 */
		public static IClassFile[] getProbekitGeneratedClassFiles(IFile probeFile) throws JavaModelException {
			return JavaUtil.getClassFiles(getProbeJavaFile(probeFile));
		}
		
		public static boolean needToGenerateProbe(IFile probeFile) {
			IFile[] generatedFiles = GenerateOperationUtil.getProbekitGeneratedFiles(probeFile);
			return GenerateOperationUtil.isOlderThan(probeFile, generatedFiles);
		}

		private static boolean isOlderThan(IFile probeFile, IFile[] generatedFiles) {
			for(int i=0; i<generatedFiles.length; i++) {
				IFile file = generatedFiles[i];
				if(!file.isAccessible()) {
					return true;
				}
				
				if(isOlderThan(file, probeFile)) {
					return true;
				}
			}
			
			return false;
		}
		
		private static boolean isOlderThan(IFile isNewer, IFile isOlder) {
			long newerTime = isNewer.getLocalTimeStamp();
			long olderTime = isOlder.getLocalTimeStamp();
			return (olderTime > newerTime);
		}

		private static IFile getProbeScriptFile(IFile probeFile) {
			String probeScriptFileName = probeFile.getName() + SCRIPT_EXTENSION;
			Path scriptPath = new Path(probeScriptFileName);
			IContainer probeDir = probeFile.getParent();
			return probeDir.getFile(scriptPath);
		}
		
		public static IFile getProbeJavaFile(IFile probeFile) {
			String fileName = ResourceUtil.getBaseName(probeFile) + GEN_JAVA_EXTENSION;
			Path filePath = new Path(fileName);
			IContainer probeDir = probeFile.getParent();
			return probeDir.getFile(filePath);
		}

		public static IMarker[] getGenerateErrors(IFile[] files) throws CoreException {
			Set markers = new HashSet();
			for(int i=0; i<files.length; i++) {
				IFile file = files[i];
				Set compileErrors = getGenerateErrors(file);
				markers.addAll(compileErrors);
			}
			
			return (IMarker[])markers.toArray(new IMarker[markers.size()]);
		}
		
		private static Set getGenerateErrors(IFile file) throws CoreException {
			IMarker[] markers = file.findMarkers(IProbeCompiler.PROBEKIT_PROBLEM_MARKER, true,  IResource.DEPTH_INFINITE);
			return ResourceUtil.getErrors(markers);
		}
		

	}
}
