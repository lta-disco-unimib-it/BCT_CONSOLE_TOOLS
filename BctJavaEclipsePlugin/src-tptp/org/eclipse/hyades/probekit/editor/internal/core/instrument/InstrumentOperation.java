/********************************************************************** 
 * Copyright (c) 2005, 2006 IBM Corporation and others. 
 * All rights reserved.   This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html         
 * $Id: InstrumentOperation.java,v 1.1 2011-12-01 21:34:10 pastore Exp $ 
 * 
 * Contributors: 
 * IBM - Initial API and implementation 
 **********************************************************************/ 


package org.eclipse.hyades.probekit.editor.internal.core.instrument;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.hyades.probekit.ProbeInstrumenterDriver;
import org.eclipse.hyades.probekit.editor.internal.core.instrument.GenerateOperation.GenerateOperationUtil;
import org.eclipse.hyades.probekit.editor.internal.core.instrument.JavacOperation.JavacOperationUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ConversionUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.osgi.util.NLS;


/**
 * Runs the "Instrument" action of Probekit, and if the .probe file needs
 * its _probe.java file generated or compiled, this operation silently runs
 * those operations before proceeding with the instrument.
 */
public class InstrumentOperation implements IWorkspaceRunnable {
	private final IFile _probeFile;
	private final List _elementsToInstrument;
	private List _uninstrumentedFiles;

	public static final int GENERATE_ERROR = 0;
	public static final int COMPILE_ERROR = 1;
	public static final int INSTRUMENT_ERROR = 2;
	public static final int SETUP_ERROR = 3;
	
	/**
	 * List can contain IResource, IJavaElement, or both. Each element will be an IResource
	 * when a directory has been specified, an IPackageFragmentRoot if an archive has
	 * been specified, and an IClassFile if a .class file has been specified.
	 */
	public InstrumentOperation(IFile probeFile, List elements) {
		_probeFile = probeFile;
		_elementsToInstrument = elements;
	}
	
	public IFile getProbeFile() {
		return _probeFile;
	}
	
	public List getElements() {
		return _elementsToInstrument;
	}
	
	private IFile[] generate(IProgressMonitor monitor) throws CoreException {
		// Check that the "Compile" output (the _probe.java and probe script files) is up-to-date.
		IFile[] generatedFiles = GenerateOperationUtil.getProbekitGeneratedFiles(getProbeFile());
		if(GenerateOperationUtil.needToGenerateProbe(getProbeFile())) {
			try {
				GenerateOperation generateOp = new GenerateOperation(getProbeFile());
				generateOp.run(monitor);
				
				if(GenerateOperationUtil.needToGenerateProbe(getProbeFile())) {
					// Generate failed for some reason? 
					throw createGenerateFailedException(generatedFiles);
				}
			}
			catch(CoreException exc) {
				// Log the old before creating the new
	    		ProbekitUIPlugin.getPlugin().log(exc);
				throw createGenerateFailedException(generatedFiles);
			}
		}

		return generatedFiles;
	}
	
	private CoreException createGenerateFailedException(IFile[] generatedFiles) {
		StringBuffer buffer = new StringBuffer(ConversionUtil.LINE_SEPARATOR);
		for(int i=0; i<generatedFiles.length; i++) {
			buffer.append(generatedFiles[i].getName());
			buffer.append(ConversionUtil.LINE_SEPARATOR);
		}
		buffer.append(NLS.bind(ProbekitMessages._98, new Object[]{getProbeFile().getName()}));
		
		IStatus status = ResourceUtil.createInitialStatus(
			IStatus.ERROR,
			buffer.toString(),
			GENERATE_ERROR,
			null
		);
		return new CoreException(status);
	}
	
	private void compile(IFile[] genFiles, IProgressMonitor monitor) throws CoreException {
		// Check that the .class files of the generated files are up-to-date.
		try {
			JavacOperation javacOp = new JavacOperation(getProbeFile().getProject());
			javacOp.run(monitor);
		}
		catch(CoreException exc) {
			// Log the old before creating the new
    		ProbekitUIPlugin.getPlugin().log(exc);
    		throw createJavacException();
		}
		
		if(JavacOperationUtil.hasCompileErrors(genFiles)) {
			throw createJavacException();
		}
	}
	
	private CoreException createJavacException() {
		IStatus status = ResourceUtil.createInitialStatus(IStatus.ERROR, 
				NLS.bind(ProbekitMessages._99, new Object[] { getProbeFile()
						.getName() }), COMPILE_ERROR, null);
		return new CoreException(status);
	}
	
	private void instrument(IFile scriptFile, IProgressMonitor monitor) throws CoreException {
		List elements = getElements();
		String[] fileList = InstrumentOperationUtil.toOSStringFromCompound(elements);
		try {
			ProbeInstrumenterDriver driver = new ProbeInstrumenterDriver();
			File localFile = InstrumentOperationUtil.getLocalFileFromResource(scriptFile);
			int errorCount = driver.instrumentItems(localFile, fileList, true); // Always save backups to be able to tell if the project was instrumented or not.
			if(errorCount != 0) {
				// argh
				String[] errors = driver.getErrorStrings();
				for(int i=0; i<errors.length; i++) {
					String error = errors[i];
					IStatus status = ResourceUtil.createInitialStatus(IStatus.ERROR, error);
					ProbekitUIPlugin.getPlugin().log(status);
				}
				String uninstrumentedFiles = ConversionUtil.toLineDelimitedString(fileList);
				throw createInstrumentException(uninstrumentedFiles);
			}
		}
		catch(ProbeInstrumenterDriver.StaticProbeInstrumenterException exc) {
			ProbekitUIPlugin.getPlugin().log(exc);
			String uninstrumentedFiles = ConversionUtil.toLineDelimitedString(fileList);
			throw createInstrumentException(uninstrumentedFiles);
		}
	}
	
	private CoreException createInstrumentException(String uninstrumentedFiles) {
		IStatus status = ResourceUtil.createInitialStatus(
				IStatus.ERROR,
				NLS.bind(ProbekitMessages._100, new Object[]{uninstrumentedFiles}),
				INSTRUMENT_ERROR,
				null
		);
		return new CoreException(status);
	}
	
	private List getUninstrumentedFileNames() {
		if(_uninstrumentedFiles == null) {
			_uninstrumentedFiles = new ArrayList();
		}
		return _uninstrumentedFiles;
	}
	
	private void validate(IProgressMonitor monitor) throws CoreException {
		// Check that for every instrumented File, there is an <instrumentedFile>.bak.
		List elements = getElements();
		Iterator iterator = elements.iterator();
		while(iterator.hasNext()) {
			Object element = iterator.next();
			if(element instanceof IResource) {
				IResource res = (IResource)element;
				if(res.getType() == IResource.FILE) {
					String name = validateFile((IFile)res, monitor);
					if(name != null) {
						getUninstrumentedFileNames().add(name);
					}
				}
				else {
					// refresh the container
					getUninstrumentedFileNames().addAll(validateContainer((IContainer)res, monitor));
				}
			}
			else if(element instanceof IJavaElement) {
				IJavaElement jElement = (IJavaElement)element;
				String name = validateElement(jElement, monitor);
				if(name != null) {
					getUninstrumentedFileNames().add(name);
				}
			}
		}
		
		if(getUninstrumentedFileNames().size() > 0) {
			throw createInstrumentException(ConversionUtil.toLineDelimitedString(getUninstrumentedFileNames()));
		}
	}
	
	private String validateFile(IFile file, IProgressMonitor monitor) throws CoreException {
		if(!InstrumentOperationUtil.canFileBeProbed(file)) {
			return null;
		}
		
		// .bak files are generated only for binary input
		File localFile = InstrumentOperationUtil.getLocalFileFromResource(file, InstrumentOperationUtil.DOT_PROBEKIT_BACKUP_EXT);
		boolean successful = validateLocalFile(localFile);
		if(successful) {
			InstrumentOperationUtil.refreshLocalFile(localFile, monitor);
			return null;
		}
		else {
			return file.getName();
		}
	}
	
	private List validateContainer(IContainer container, IProgressMonitor monitor) throws CoreException {
		List filesToProbe = InstrumentOperationUtil.getFilesToProbe(container);
		Iterator iterator = filesToProbe.iterator();
		List fileNames = new ArrayList();
		while(iterator.hasNext()) {
			IFile file = (IFile)iterator.next();
			String name = validateFile(file, monitor);
			if(name != null) {
				fileNames.add(name);
			}
		}
		return fileNames;
	}
	
	private String validateElement(IJavaElement element, IProgressMonitor monitor) throws CoreException {
		if(!InstrumentOperationUtil.canFileBeProbed(element)) {
			return null;
		}
		
		// .bak files are generated only for binary input
		File localFile = InstrumentOperationUtil.getLocalFileFromElement(element, InstrumentOperationUtil.PROBEKIT_BACKUP_EXT);
		boolean successful = validateLocalFile(localFile);
		if(successful) {
			if(!InstrumentOperationUtil.isExternal(element)) {
				InstrumentOperationUtil.refreshLocalFile(localFile, monitor);
			}
			return null;
		}
		else {
			return element.getElementName();
		}
	}
	
	private boolean validateLocalFile(File localFile) {
		if(!localFile.exists()) {
			return false;
		}
		
		if(!localFile.isFile()) {
			return false;
		}
		
		return true;
	}
	
	private void setupForExecution(IProgressMonitor monitor) throws CoreException {
		// Check classpath and if the project with the _probe.class files 
		// is not on the classpath then copy the _probe.class files into
		// the classpath of the probed project. If the probed project is a 
		// binary Java project, then a new "Class Folder" entry has to be 
		// added to the classpath, and the probe files copied into that new
		// folder.
		IJavaProject thisProject = JavaCore.create(getProbeFile().getProject());
		IJavaProject jp = null;
		try {
			IJavaProject[] probedProjects = JavaUtil.getJavaProjectsFromCompound(getElements());
			IClassFile[] probeClassFiles = GenerateOperationUtil.getProbekitGeneratedClassFiles(getProbeFile());
			for(int i=0; i<probedProjects.length; i++) {
				jp = probedProjects[i];
				if(jp.equals(thisProject)) {
					continue;
				}
				
				IPath outputPath = getOutputLocation(jp);
				for(int j=0; j<probeClassFiles.length; j++) {
					IClassFile cFile = probeClassFiles[j];
					if(!jp.isOnClasspath(cFile)) {
						IResource res = cFile.getResource();
						if((res != null) && (res.isAccessible())) {
							IPath newResource = outputPath.append(res.getName());
							IResource existingResource = ResourceUtil.ROOT.findMember(newResource);
							if((existingResource != null) && (existingResource.isAccessible())) {
								existingResource.delete(true, monitor);
							}
							res.copy(newResource, true, monitor);
						}
					}
				}
			}
		}
		catch(JavaModelException exc) {
			// Log the old before creating the new
    		ProbekitUIPlugin.getPlugin().log(exc);
    		throw createSetupException(jp);
		}
		catch(CoreException exc) {
			// Log the old before creating the new
    		ProbekitUIPlugin.getPlugin().log(exc);
    		throw createSetupException(jp);
		}
	}
	
	private IPath getOutputLocation(IJavaProject jp) throws JavaModelException, CoreException {
		IPath result = null;
		if(JavaUtil.isBinaryProject(jp)) {
			result = JavaUtil.createClassFolder(jp);
		}
		else {
			result = jp.getOutputLocation();
		}
		return result;
	}
	
	private CoreException createSetupException(IJavaProject jp) {
		String outputPathString = InstrumentOperationUtil.toOSStringFromResource(jp.getProject());
		IStatus status = ResourceUtil.createInitialStatus(
				IStatus.ERROR,
				NLS.bind(ProbekitMessages._101, new Object[]{outputPathString}),
				SETUP_ERROR,
				null
		);
		return new CoreException(status);
	}
	
	public boolean canRun() {
		String message = getErrorMessage();
		return (message == null);
	}
	
	public String getErrorMessage() {
		if(!ProbekitUtil.isSupportedPlatform()) {
			return ProbekitMessages._103;
		}
		
		if(!getProbeFile().isAccessible()) {
			return NLS.bind(ProbekitMessages._104, new Object[]{getProbeFile().getName()});
		}
		
		boolean valid = true;
		if(getElements() == null) {
			valid = false;
		}
		else if(getElements().isEmpty()) {
			valid = false;
		}
		else {
			Iterator iterator = getElements().iterator();
			while(iterator.hasNext()) {
				Object obj = iterator.next();
				if(!InstrumentOperationUtil.canObjectBeProbed(obj)) {
					valid = false;
					break;
				}
			}
		}
		
		if(!valid) {
			return ProbekitMessages._105;
		}
		
		return null;
	}
	
	public void run(IProgressMonitor monitor) throws CoreException {
		String runErrorMessage = getErrorMessage();
		if(runErrorMessage != null) {
			IStatus status = ResourceUtil.createInitialStatus(
					IStatus.ERROR, 
					NLS.bind(ProbekitMessages._102, new Object[]{runErrorMessage}),
					InstrumentOperation.INSTRUMENT_ERROR);
			throw new CoreException(status);
		}
		
		IFile[] genFiles = generate(monitor);
		IFile probeScriptFile = genFiles[0];
		compile(genFiles, monitor);
		instrument(probeScriptFile, monitor);
		validate(monitor);
		InstrumentOperationUtil.refresh(getElements(), monitor);
		setupForExecution(monitor);
	}
	
	public static class InstrumentOperationUtil {
		private static final String DOT_PROBEKIT_BACKUP_EXT = ".bak"; //$NON-NLS-1$
		private static final String PROBEKIT_BACKUP_EXT = "bak"; //$NON-NLS-1$
		private static final String DOT = "."; //$NON-NLS-1$
		
		static IResource[] getWorkspaceResourcesForLocalFile(File localFile) {
			IPath location = new Path(localFile.getAbsolutePath());
			IResource[] resources = null;
			if(localFile.isDirectory()) {
				resources = ResourceUtil.ROOT.findContainersForLocation(location); // Find linked resources as well
			}
			else {
				resources = ResourceUtil.ROOT.findFilesForLocation(location);
			}
			return resources;
		}
	
		static String toOSStringFromResource(IResource res) {
			return getLocalFileFromResource(res).getAbsolutePath();
		}
	
		static String toOSStringFromJavaElement(IJavaElement element) {
			return getLocalFileFromElement(element).getAbsolutePath();
		}
		
		// This method is public for the test case.
		public static IResource[] getInstrumentedResourcesFromElement(IJavaElement element) {
			File localFile = getLocalFileFromElement(element, PROBEKIT_BACKUP_EXT);
			IResource[] resources = getWorkspaceResourcesForLocalFile(localFile);
			return resources;
		}
		
		static File getLocalFileFromResource(IResource resource) {
			return new File(resource.getLocation().toOSString());
		}
		
		static File getLocalFileFromElement(IJavaElement element) {
			IResource res = element.getResource();
			if(res == null) {
				// External archive
				IPath path = element.getPath();
				return path.toFile();
			}
			else {
				return getLocalFileFromResource(res);
			}
		}
		
		static File getLocalFileFromElement(IJavaElement element, String fileExtension) {
			IResource res = element.getResource();
			if(res == null) {
				// External archive
				IPath path = element.getPath();
				path = path.addFileExtension(fileExtension);
				return path.toFile();
			}
			else {
				return getLocalFileFromResource(res, DOT + fileExtension);
			}
		}
		
		static File getLocalFileFromResource(IResource resource, String fileExtension) {
			String osPath = toOSStringFromResource(resource) + fileExtension;
			return new File(osPath);
		}
		
		static boolean isExternal(IJavaElement element) {
			if(element.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT) {
				IPackageFragmentRoot root = (IPackageFragmentRoot)element;
				if(root.isExternal()) {
					// Nothing to refresh
					return true;
				}
			}
			return false;
		}

		private static void refreshResources(IResource[] resources, IProgressMonitor monitor) throws CoreException {
			for(int i=0; i<resources.length; i++) {
				IResource res = resources[i];
				refreshResource(res, monitor);
			}
		}
	
		static void refreshResource(IResource resource, IProgressMonitor monitor) throws CoreException {
			resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		}
		
		static void refreshLocalFile(File localFile, IProgressMonitor monitor) throws CoreException {
			IResource[] resources = getWorkspaceResourcesForLocalFile(localFile);
			refreshResources(resources, monitor);
		}

		static void refresh(List elements, IProgressMonitor monitor) throws CoreException {
			IResource[] resources = ConversionUtil.toResourceFromCompounds(elements);
			InstrumentOperationUtil.refreshResources(resources, monitor);
		}

		public static boolean canFileBeProbed(IJavaElement element) {
			if(JavaUtil.isClassFile(element)) {
				return true;
			}
			
			if(JavaUtil.isArchive(element)) {
				return true;
			}
			
			return false;
		}
		
		static boolean canFileBeProbed(IFile file) {
			IJavaElement element = JavaCore.create(file); // Could be either an archive or a .class file.
			boolean valid = true;
		
			/* Navid Mehregani - bugzilla 166096: If 'element' is null, pass the validation only if
			 * the selected file is not a JAR, WAR, or EAR file */
			if (element == null)
			{
				String fileName = file.getName();
				if (fileName.lastIndexOf('.') != -1)
				{
					String fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
					if ((!fileExtension.equals(".jar")) && (!fileExtension.equals(".war")) && (!fileExtension.equals(".ear")))
						valid = false;
				}
				else
				{
					valid = false;
				}
			}
			else
			{
				valid = canFileBeProbed(element);
			}
			return valid;
		}
		
		static boolean canResourceBeProbed(IResource resource) {
			if(resource.getType() == IResource.FILE) {
				return canFileBeProbed((IFile)resource);
			}
			return true; // By default, assume that the container has instrumentable content. Even if it doesn't, Probekit silently finishes.
		}
		
		static boolean canObjectBeProbed(Object obj) {
			if(obj instanceof IResource) {
				return canResourceBeProbed((IResource)obj);
			}
			else if(obj instanceof IJavaElement) {
				return canFileBeProbed((IJavaElement)obj);
			}
			return false;
		}
		
		static List getFilesToProbe(IContainer container) throws CoreException {
			List files = new ArrayList();
			IResource[] children = container.members();
			for(int i=0; i<children.length; i++) {
				IResource child = children[i];
				if(child.getType() == IResource.FILE) {
					if(canFileBeProbed((IFile)child)) {
						files.add(child);
					}
				}
				else {
					files.addAll(getFilesToProbe((IContainer)child));
				}
			}
			return files;
		}
		
		static String[] toOSStringFromCompound(List elements) {
			String[] result = new String[elements.size()];
			Iterator iterator = elements.iterator();
			int count = 0;
			while(iterator.hasNext()) {
				Object element = iterator.next();
				if(element instanceof IResource) {
					result[count++] = toOSStringFromResource((IResource)element);
				}
				else if(element instanceof IJavaElement) {
					result[count++] = toOSStringFromJavaElement((IJavaElement)element);
				}
			}
			return result;
		}
	}
}
