/**********************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.hyades.probekit.internal.JarReader;
import org.eclipse.hyades.probekit.internal.JarWriter;

import util.FileUtil;
import util.ProcessRunner;
import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRulesUtil;

/* 
 * Note related to localization and NLS: this class can run as part of 
 * a stand-alone Java application, so it can't use the usual resource 
 * bundle system.
 */ 

/**
 * This class exports an API that you can use to instrument class files and
 * jar files on disk.
 * Use cmdLineMain to pass command-line options and have errors reported
 * to standard error using System.err.println. 
 * <P>
 * Otherwise use instrumentItems() to pass 
 * a File object which refers to the saved BCI engine
 * script from the probe compiler, and one or more File objects referring to files
 * that you want to process. If a File object refers to a directory, all 
 * *.class and *.jar files (and *.war and *.ear) in that directory and its subdirectories, 
 * recursively, will be processed. "Processing" a file means rewriting the 
 * class or jar file in place with an instrumented verison of itself.
 * <P>
 * When using instrumentItems, error reports from the native executable
 * are collected into an array of Strings and you can get them using getErrorStrings().
 * If any run of the native executable exits with a nonzero exit code, then
 * the return value from instrumentItems will be nonzero. Instrumenter problems
 * in one file don't prevent the continued processing of other files. 
 * If there are other problems (unrecoverable problems like an inability to run the
 * instrumenter at all), they are reported by throwing an exception.
 * <P>
 * You can optionally call setExePath before calling instrumentItems, to give 
 * the full path to the native
 * probeinstrumenter program. If you don't call setExePath and you are
 * calling this code from an Eclipse plugin,
 * the Probekit plugin's location and the current OS and Arch values are 
 * used to find the directory containing the native executable. 
 * <P>
 * The actual byte-code instrumentation is done with a native executable.
 * If you use this as a "main" program from the command line, the native
 * executable "probeinstrumenter" should appear on your PATH, such that Runtime.exec
 * can find it. If you use this as a plug-in, the logic in SetExePathFromPlugin
 * will find the native executable relative to the plugin directory,
 * in this subdirectory of the plugin's directory: os/$os$/$arch$/probeinstrumenter 
 * (with a .exe suffix on Windows).
 */

public class ProbeInstrumenterDriver {
	/**
	 * accepts command-line arguments and processes them. 
	 * Errors are written to System.err.
	 * <P>
	 * First argument must be a probe script file name.
	 * Subsequent arguments are class file, jar file, or directory names to process.
	 * Errors are written using System.err.println().
	 * Return value is zero if no errors, nonzero for any errors.
	 * 
	 * TODO: we don't check the already-instrumented state and leave instrumented classes alone.
	 * TODO: consider detecting instrumented files and reinstrumenting from *.bak files.
	 * 
	 * @param args the arguments: an engine script, and a list of class file,
	 * jar file, and directory names
	 * @return zero for no error, nonzero for any error. Errors are also reported to System.err.
	 */
	static public int cmdLineMain(String[] args) {
		String scriptFileName = "";
		ProbeInstrumenterDriver m = new ProbeInstrumenterDriver();
		int arg_counter = 0;
		try {
			// Usage errors throw a string so we display usage.
			for ( ; arg_counter < args.length; arg_counter++) {
				if (args[arg_counter].equals("-v")) {
					m.setVerbose(true);
				}
				else if (args[arg_counter].equals("-h")) {
					System.err.println("User requested this usage message.");
					return 1;
				}
				else if (args[arg_counter].startsWith("-")) {
					System.err.println("Unrecognized option " + args[arg_counter]);
					return 1;
				}
				else break;
			}
			if (args.length - arg_counter < 2) {
				System.err.println("Not enough arguments.");
				return 1;
			}
			
			scriptFileName = args[arg_counter];
			if (!new File(scriptFileName).exists()) {
				System.err.println("Probescript file \"" + scriptFileName + "\" does not exist.");
				return 1;
			}
			arg_counter++;
		}
		catch (Exception s) {
			System.err.println("Usage error: " + s);
			System.err.println("Usage: ProbeInstrumenter engine_script item [item ...]");
			System.err.println("Where \"items\" may be class files, jar/war/ear files, or directories.");
			System.err.println("Directories are processed recursively, instrumenting all subdirectories and");
			System.err.println("jar, war, ear, and class files found within.");
			return 1;
		}

		String[] items = new String[args.length - arg_counter];
	
		for (int i = 0; i < (args.length - arg_counter); i++) {
			items[i] = args[arg_counter + i];
		}

		// Set the exe path assuming we'll find it in the user's PATH
		m.setExePath(INSERTION_EXECUTABLE_BASENAME);
		
		try {
			m.instrumentItems(new File(scriptFileName), items, true);
		}
		catch (StaticProbeInstrumenterException e) {
			// Serious, unrecoverable error, not just an instrumentation error on a jar file or something.
			System.err.println("Instrumenter error: " + e.getMessage());
			return 2;
		}
		
		// Dump out the error strings (if any) to System.err.
		String[] errorStrings = m.getErrorStrings();
		for (int i = 0; i < errorStrings.length; i++) {
			System.err.println(errorStrings[i]);
		}

		return m.getErrorCount();
	}

	static final String fileSeparator = System.getProperty("file.separator");

	/**
	 * Error status tracker. Goes nonzero when there is any error.
	 * This value is eventually returned from cmdLineMain.
	 */
	int errorCount = 0;

	/**
	 * Returns the current error status value for the instrumenter driver. 
	 * @return the error status
	 */
	public int getErrorCount() { return this.errorCount; }
	
	/**
	 * This is an array of strings used as jar file suffixes.
	 */
	static String[] JAR_FILE_SUFFIX_LIST = { ".jar", ".war", ".ear" };

	boolean verbose = false;
	/**
	 * The "verbose" flag, when true, results in output on System.out
	 * at major points in processing. Default is false.
	 * @param f
	 */
	public void setVerbose(boolean f) {
		this.verbose = f;
	}
	
	String exePath = null;
	/**
	 * Set the path and file name to use for the native instrumentation executable.
	 * The format of the argument should be such that System.exec will accept it and
	 * run the native executable. For example, on Windows the ".exe" suffix is required.
	 * If the argument is a plain file name with no path information, it means the
	 * executable must be found on the user's PATH.
	 * 
	 * @param s the fully-qualified path and file name of the "probeinstrumenter" executable.
	 */
	public void setExePath(String s) {
		this.exePath = s;	
	}

	List errorStrings = new LinkedList();

	/**
	 * Return an array of strings describing errors that occurred during instrumentItems.
	 * @return an error strings 
	 */
	public String[] getErrorStrings() {
		return (String[])this.errorStrings.toArray(new String[0]);
	}
	
	static final String COMPILER_PLUGIN_NAME = "org.eclipse.hyades.probekit";
	static final String INSERTION_EXECUTABLE_BASENAME = "probeinstrumenter";

	boolean useJava6Verifier;
	
	public boolean isUseJava6Verifier() {
		return useJava6Verifier;
	}

	public void setUseJava6Verifier(boolean useJava6Verifier) {
		this.useJava6Verifier = useJava6Verifier;
	}

	/**
	 * This function calls SetExePath using an absolute path name
	 * derived from the org.eclipse.hyades.probekit plugin location
	 * and a platform-specific subdirectory, plus a platform-specific
	 * suffix on the default executable name "probeinstrumenter."
	 * <P>
	 * See the comment above ProbeInstrumenterInner for an explanation of why
	 * it's done with an inner class.
	 * 
	 * @throws StaticProbeInstrumenterException if the target executable is not found.
	 */
	void SetExePathFromPlugin() throws StaticProbeInstrumenterException {
		setExePath(ProbeInstrumenterInner.getExeName());
	}
	
	/**
	 * Inner class that computes the executable name relative to the plugin.
	 * This is an inner class because that way we can successfully 
	 * use this Java program outside Eclipse - Classes like Plugin and other
	 * references won't need to be resolvable.
	 */
	static class ProbeInstrumenterInner {
		static String getExeName() throws StaticProbeInstrumenterException {
			return "";
//			try {
//				String engine_executable_name = "$os$/" + INSERTION_EXECUTABLE_BASENAME;
//		
//				// Platform-specific suffix processing goes here.
//				if (Platform.getOS().equals("win32")) {
//					engine_executable_name += ".exe";
//				}
//				Plugin compilerPlugin = ProbekitPlugin.getDefault();
//				IPath relativePath = new Path(engine_executable_name);
//				// Plugin.find() does substitution on strings like $os$
//				URL nativeInstrumenterRelativeURL = compilerPlugin.find(relativePath, null);
//				URL compilerURL = compilerPlugin.getBundle().getEntry("/");
//				URL nativeInstrumenterURL;
//	
//				String relativeFile = nativeInstrumenterRelativeURL.getFile();
//				if (relativeFile.startsWith("/")) {
//					relativeFile = relativeFile.substring(1);
//				}
//				URL nativeInstrumenterPlatformURL = new URL(compilerURL, relativeFile);
//				nativeInstrumenterURL = Platform.asLocalURL(nativeInstrumenterPlatformURL);
//				if (nativeInstrumenterURL == null) {
//					throw new StaticProbeInstrumenterException(
//						"Native executable for instrumentation not found (tried " + engine_executable_name + " relative to " + compilerPlugin.getBundle().getSymbolicName());
//				}
//				String exe_path = nativeInstrumenterURL.getFile();
//				File exe_file = new File(exe_path);
//				if (!exe_file.exists()) {
//					throw new StaticProbeInstrumenterException(
//						"Native executable for instrumentation not found (tried " + exe_path + ")");
//				}
//				exe_path = exe_file.getCanonicalPath();
//				return exe_path;
//			}
//			catch (IOException e) {
//				return null;
//			}
		}
	}
	
	/**
	 * This is the main API method for this subsystem: use it to instrument
	 * class files and jar files, including recursive operations on directories.
	 * 
	 * Errors in one item don't stop processing
	 * of other items. After using this method, use getErrorStatus() and getErrorStrings()
	 * to find out how it went. 
	 * 
	 * @param scriptFile a File that refers to the engine script file to use
	 * @param items each string should be a class file name, a jar file name, 
	 * or a directory name.
	 * @param saveBackups true if you want this code to rename original class and jar files
	 * to *.bak - otherwise they'll be lost completely.
	 * @throws StaticProbeInstrumenterException for serious errors.
	 */
	public int instrumentItems(File i_scriptFile, String[] items, boolean saveBackups) throws StaticProbeInstrumenterException {
		return instrumentItems(i_scriptFile, items, saveBackups, false);
	}

	/**
	 * This is the main API method for this subsystem: use it to instrument
	 * class files and jar files, including recursive operations on directories.
	 * 
	 * Errors in one item don't stop processing
	 * of other items. After using this method, use getErrorStatus() and getErrorStrings()
	 * to find out how it went. 
	 * 
	 * @param scriptFile a File that refers to the engine script file to use
	 * @param items each string should be a class file name, a jar file name, 
	 * or a directory name.
	 * @param saveBackups true if you want this code to rename original class and jar files
	 * @param useJava6Verifier set to true to use the new java6 verifier. 
	 * to *.bak - otherwise they'll be lost completely.
	 * @throws StaticProbeInstrumenterException for serious errors.
	 * @provisional
	 */
	public int instrumentItems(File i_scriptFile, String[] items,
			boolean saveBackups, boolean useJava6Verifier) throws StaticProbeInstrumenterException {
		this.scriptFile = i_scriptFile;
		this.useJava6Verifier = useJava6Verifier;
		
		try {
			this.scriptFileNameString = this.scriptFile.getCanonicalPath();
		}
		catch (IOException e) {
			throw new StaticProbeInstrumenterException("Error resolving \"" + i_scriptFile + "\": " + e.getMessage());
		}
		
		for (int i = 0; i < items.length; i++) {
			File f = new File(items[i]);
			if (!f.exists()) {
				this.errorStrings.add("No such file or directory: " + f);
				this.errorCount++;
			}
			else if (f.isDirectory()) {
				processDirectory(f, saveBackups, f.toString());
			}
			else if (endsWithIgnoreCase(items[i], ".class")) {
				List fList = new LinkedList();
				fList.add(f);
				processClassFiles(fList, saveBackups, f.toString());
			}
			else if (endsWithIgnoreCase(items[i], JAR_FILE_SUFFIX_LIST)) {
				processJarFile(f, saveBackups, f.toString());
			}
			else {
				// Neither a class file, nor a jar file, nor a directory.
				// Ignore it.
			}
		}
		return this.errorCount;
	}
	
	static private boolean endsWithIgnoreCase(String s, String suffix) {
		int len = s.length();
		int suffixLen = suffix.length();
		if (len < suffixLen) 
			return false;
		
		if(s.substring(len - suffixLen).equalsIgnoreCase(suffix))
			return true;
		else 
			return false;
	}
	
	static private boolean endsWithIgnoreCase(String s, String[] suffixList) {
		for(int i = 0; i < suffixList.length; i++) {
			if (endsWithIgnoreCase(s, suffixList[i])) 
				return true;
		}
		return false;
	}
	/**
	 * The stored "engine script" File object. Its canonical name is passed to the
	 * native instrumentation engine.
	 */
	private File scriptFile;
	private String scriptFileNameString;

	/**
	 * The exception type this subsystem can throw.
	 */
	public static class StaticProbeInstrumenterException extends Exception {
		/**
		 * This serialVersionUID was generated May 19, 2005.
		 */
		private static final long serialVersionUID = 3690197663471710512L;

		public StaticProbeInstrumenterException(String m) {
			super(m);
		}
	}

	/**
	 * accumulates strings representing errors and warnings as items are processed.
	 * Will be the empty string if there's nothing to say.
	 */
	String errorString = "";
	
	/**
	 * The number of class files to batch up from a directory before running the instrumenter process.
	 */
	int classFilesBatchSize = 10;


	/**
	 * Process a batch of class files - this invokes the instrumentation engine just once,
	 * passing the whole batch as arguments.
	 * 
	 * @param files a List of files to instrument.
	 * @param shouldSaveBakFile true to rename the original to *.bak, false otherwise
	 */
	void processClassFiles(List files, boolean shouldSaveBakFile, String location) throws StaticProbeInstrumenterException {
		if (this.verbose) System.out.println("(ProcessClassFiles)");
		
		
		files = MatchingRulesUtil.updateFilesAccordingToMatchingRules(files,matchingRules);
		
		
		
		int totalArgs = useJava6Verifier ? 3 + files.size() : 2 + files.size();
		String[] args = new String[totalArgs];

		// If nobody's set exePath yet, set it from the plugin location.
		// Note: when using this as a command-line program you'd better call setExePath
		// before now, or you'll get unresolved classes in the plugin framework.
		if (exePath == null) SetExePathFromPlugin();
		
		int currentArgIndex = 0;
		
		args[currentArgIndex++] = exePath;		// will be filled in later
		if(useJava6Verifier) {
			args[currentArgIndex++] = "-usejava6verifier";
		}
		args[currentArgIndex++] = this.scriptFileNameString;

		Iterator iter = files.iterator();
		for (int i = currentArgIndex; iter.hasNext(); i++) {
			Object obj = iter.next();
			args[i] = ((File)obj).getAbsolutePath();
		}

		// The instrumenter creates a file with the input name plus .bci
		String[] outputStrings;
		try {
			outputStrings = executeCommandAndWait(args);
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new StaticProbeInstrumenterException("Unrecoverable error attempting to execute \"" + args[0] + "\"");
		}

		// Rename the original class file to *.bak
		// unless we've been told not to bother.
		// (We don't rename if we are in a jar file.)
		
		for (iter = files.iterator(); iter.hasNext(); ) {
			File f = (File)(iter.next());
			String thisFileLocation = location + f.getName();
			File bciFile = new File(f.getAbsolutePath() + ".bci");
			if (!bciFile.exists()) {
				this.errorStrings.add("Error instrumenting " + thisFileLocation + ": no BCI file created");
				this.errorStrings.add("Instrumenter error output follows:");
				this.errorStrings.add(outputStrings[1]);
				this.errorCount++;
			}
			else {
				if (shouldSaveBakFile) {
					File bakFile = new File(f.getAbsolutePath() + ".bak");
					bakFile.delete();	// don't care if this fails
					if (!f.renameTo(bakFile)) {
						this.errorStrings.add("Error renaming " + f.getAbsolutePath() + " to *.bak");
						this.errorCount++;
					}
				}
				else {
					// Don't save a backup file, delete the original instead.
					f.delete();
				}
				if (!bciFile.renameTo(f)) {
					this.errorStrings.add("Error renaming " + bciFile.getAbsolutePath() + " to *.class");
					this.errorCount++;
				}
			}
		}
	}


	/**
	 * Instrument the contents of a jar file.
	 * 
	 * Jar files are processed by unpacking them into a temporary directory,
	 * processing the *.class files found inside, and packing them up again.
	 * The manifest file (if any) will be rewritten to remove any lines that
	 * contain "-Digest:" because we've (potentially)
	 * destroyed the checksum of every class file.
	 * 
	 * TODO: only remove Digest entries for class files that actually change.
	 *
	 * @param f the file to process
	 */
	void processJarFile(File f, boolean shouldSaveBakFile, String location) throws StaticProbeInstrumenterException {
		if (this.verbose) System.out.println("Jar file " + f.getName());
		File tmpdir = null;

		try {
			// Create a temporary file, then remove it and use its name 
			// for a temporary directory.
			tmpdir = File.createTempFile("probetemp-", "");
			if (!tmpdir.delete() || !tmpdir.mkdir()) {
				throw new StaticProbeInstrumenterException("Internal error managing temporary files");
			}

			// Unpack the jar file to the temporary directory.
			// Get its manifest, too.
			JarReader jr = new JarReader(f);
			java.util.jar.Manifest m = jr.getManifest();
			jr.extractAll(tmpdir);
			jr.close();
			
			// Process the temporary directory to instrument the class files.
			// The "location" for reporting purposes is the JAR file's name.
			processDirectory(tmpdir, false, location);

			// Strip the manifest of Digest entries
			if (m != null) {
				stripManifest(m);
			} 

			if (this.verbose) System.out.println("Repack " + location);

			// Rename the original jar file to a backup name if we were asked to.
			// Otherwise delete the original so we can create a new one.
			if (shouldSaveBakFile) {
				File bakFile = new File(f.getCanonicalPath() + ".bak");
				bakFile.delete();	// don't care if this fails
				if (!f.renameTo(bakFile)) {
					this.errorStrings.add("Unable to rename jar file to *.bak: " + f.getCanonicalPath());
					this.errorCount++;
					return;
				}
			}
			else {
				f.delete();
				if (f.exists()) {
					this.errorStrings.add("Unable to delete jar file: " + f.getCanonicalPath());
					this.errorCount++;
					return;
				}
			}
			// Now jar everything up again.
			JarWriter jw = new JarWriter(f, m, null);
			jw.write(tmpdir, "", true);
			jw.close();

			return;
		}
		catch (IOException e) {
			this.errorStrings.add("I/O Exception processing jar file " + f.getAbsolutePath() + ": " + e.getClass() + e.getMessage());
			this.errorCount++;
			return;
		}
		finally {
			// Delete the temporary directory
			recursiveDelete(tmpdir);
		}
	}

	/**
	 * Process a whole directory, recursively.
	 * 
	 * For each class file in the directory, call processClassFile.
	 * For each jar file, call processJarFile.
	 * For each directory, call ProcessDirectory.
	 * <P>
	 * When called from elsewhere in this program, the treeRoot parameter
	 * should be null. When called recursively, this parameter is the
	 * root of the recursive call. This lets error messages report with
	 * trimmed path names based on the start of the recursion.
	 *  
	 * @param f the File representing the directory to process.
	 * @param shouldSaveBakFile true if a "bak" file should be saved for class and jar files
	 * @param treeRoot the File representing the root of a recursive call to this method
	 * @throws ProbeException
	 */
	void processDirectory(File f, boolean shouldSaveBakFile, String location) throws StaticProbeInstrumenterException {
		if (this.verbose) System.out.println("Directory " + f.getName());
		
		File[] contents = f.listFiles();
		List classFilesList = new Vector();
		for (int i = 0; i < contents.length; i++) {
			String newLocation = location + fileSeparator + contents[i].toString();
			if (contents[i].isDirectory()) {
				processDirectory(contents[i], shouldSaveBakFile, newLocation);
			}
			else if (contents[i].getName().endsWith(".class")) {
				classFilesList.add(contents[i]);
			}
			else if (endsWithIgnoreCase(contents[i].getName(), JAR_FILE_SUFFIX_LIST)) {
				processJarFile(contents[i], shouldSaveBakFile, newLocation);
			}
			
			// If we just handled the last file or the class file list
			// is big enough, process it.
			if ((i == (contents.length - 1)) || 
				(classFilesList.size() == classFilesBatchSize)) 
			{
				processClassFiles(classFilesList, shouldSaveBakFile, location);
				classFilesList.clear();
			}
		}
	}

	/**
	 * Function to recursively delete a file. This means if it's
	 * a directory we'll delete children first, then the directory.
	 * Even in the face of errors, deletes everything that can be deleted.
	 * 
	 * @param f represents the file to delete
	 * @return true on success, false for failure
	 */
	boolean recursiveDelete(File f) {
		if (f.isDirectory()) {
			// We don't need to track success of inner deletes,
			// because if any fail then the top-level delete will fail
			// and cause us to return false.
			File[] contents = f.listFiles();
			for (int i = 0; i < contents.length; i++) {
				recursiveDelete(contents[i]);
			}
		}
		return f.delete();
	}

	/**
	 * Strips a manifest of any "-Digest" attributes. We do this because
	 * we've changed the signatures of classes that we did instrumentation on.
	 * 
	 * @param m the manifest to strip
	 */

	static void stripManifest(java.util.jar.Manifest m) {
		Map entries = m.getEntries();
		if (entries != null) {
			Iterator iter = entries.entrySet().iterator();
			while (iter.hasNext()) {
				Object o = iter.next();
				java.util.Map.Entry e = (java.util.Map.Entry)o;
				java.util.jar.Attributes attr = (java.util.jar.Attributes)e.getValue();
				if (attr != null) {
					Set keys = attr.keySet();
					Iterator keyIter = keys.iterator();
					Set keysToRemove = new HashSet();
					while (keyIter.hasNext()) {
						Object keyObj = keyIter.next();
						String keyName = keyObj.toString();
						if (keyName.endsWith("-Digest")) {
							// Add this to the list of objects to remove from the map
							// (we can't do it now because we'd destroy the key set iterator)
							keysToRemove.add(keyName);
						}
					}

					// Now remove the digest strings we found from the attribute map
					Iterator keysToRemoveIterator = keysToRemove.iterator();
					while (keysToRemoveIterator.hasNext()) {
						String keyName = (String)keysToRemoveIterator.next();
						java.util.jar.Attributes.Name attrName = new java.util.jar.Attributes.Name(keyName); 
						attr.remove(attrName);
					}
				}
			}
		}
	}

	/**
	 * Class to capture the contents of a stream into a String.
	 * Run it as a thread to soak up stdout and stderr of a child process.
	 */	
	static class StreamCapture extends Thread {
		InputStream stream;
		StringBuffer capture = new StringBuffer();
		static final String newline = System.getProperty("line.separator");
			
		public StreamCapture(InputStream s) {
			stream = s;
		}
			
		public void run() {
			BufferedReader breader = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			boolean anyExceptions = false;
			do {
				try {
					line = breader.readLine();
					if (line != null) {
						capture.append(line);
						capture.append(newline);
					}
				}
				catch (IOException e) {
					capture.append("IO Exception in stream reader:");
					capture.append(newline);
					capture.append(e.getMessage());
					capture.append(newline);
					
					// break this loop on the second exception we get
					if (anyExceptions) break;
					anyExceptions = true;
				}
			} while (line != null);
		}
			
		public StringBuffer getCapture() {
			return capture;
		}
	}

	/**
	 * Function that executes a command with Runtime.exec 
	 * and doesn't return until it exits.
	 * Consumes and discards the standard output, standard error, and exit code.
	 * On Windows at least, this can start a batch file as well as native executables.
	 * 
	 * @param args the argument array to execute. First element is the executable name.
	 * @throws IOException when something goes wrong.
	 */
	private String[] executeCommandAndWait(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		for( String s : args ){
			sb.append(" ");
			sb.append(s);
		}
		System.out.println("Executing: "+sb.toString() );
		
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(args);

		List<String> command = Arrays.asList(args);
		
		Appendable outputBuffer = new StringBuffer();
		Appendable errorBuffer  = new StringBuffer();
		
		int exitCode = ProcessRunner.run(command, outputBuffer, errorBuffer, 0);
		
//		// Set up threads to read the stdout and stderr of the process
//		StreamCapture stdoutCapture = new StreamCapture(proc.getInputStream());
//		stdoutCapture.start();
//		StreamCapture stderrCapture = new StreamCapture(proc.getErrorStream());            
//		stderrCapture.start();
// 
//		// Wait for the process to exit.
//		// Loop if the waitFor gets interrupted by something.
//		boolean interrupted = false;
//		int exitCode = 0;
//		do {
//			try {
//				exitCode = proc.waitFor();
//				/* Next clause will only be executed if a previous waitFor() attempt was
//				 * interrupted and a subsequent attempt has succeeded. */
//			    if ( interrupted ) break;
//			}
//			catch (InterruptedException e) {
//				interrupted = true;
//			}
//		} while (interrupted);	
//		
//		String out = stdoutCapture.getCapture().toString();
//		String err = stderrCapture.getCapture().toString();
		
		System.out.println("Exit code: "+exitCode);
		System.out.println(outputBuffer.toString());
		System.out.println(errorBuffer.toString());
		
		if (exitCode != 0) this.errorCount = 4;
		return new String[] { outputBuffer.toString(), errorBuffer.toString() };
	}


	
	//MATCHING RULES
	

	private List<Component> matchingRules;


	public void setMatchingRules(List<Component> matchingRules) {
		this.matchingRules = matchingRules;
	}
	

}
