/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JarReader.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit.internal;
import java.util.jar.*;
import java.io.*;

/**
 * This class implements a "jar xf" command - you can extract the contents of
 * a jar file to a directory on your system using this class.
 */
public class JarReader {
	/**
	 * Exception thrown when the target directory argument to extractAll doesn't exist.
	 */
	static public class MissingTargetDirectoryException extends IOException {
		/**
		 * This serialVersionUID was generated May 19, 2005.
		 */
		private static final long serialVersionUID = 3832902156687914550L;

		public MissingTargetDirectoryException(String s) {
			super(s);
		}
	}
	
	/**
	 * Exception thrown when an entry starts with ".." (which is invalid).
	 */
	static public class EntryStartsWithDotDotException extends IOException {
		/**
		 * This serialVersionUID was generated May 19, 2005.
		 */
		private static final long serialVersionUID = 3257285842199195699L;

		public EntryStartsWithDotDotException(String s) {
			super(s);
		}
	}
	/**
	 * Exception thrown when a file or directory create operation fails.
	 */
	static public class FailedToCreateDirectoryOrFile extends IOException { 
		/**
		 * This serialVersionUID was generated May 19, 2005.
		 */
		private static final long serialVersionUID = 3256719576531089457L;

		public FailedToCreateDirectoryOrFile(String s) {
			super(s);
		}
	}
	
	private JarInputStream fJarInputStream;
	
	public JarReader(File jarFile) throws IOException {
		fJarInputStream = new JarInputStream(new FileInputStream(jarFile));
	}

	public Manifest getManifest() throws IOException {
		return fJarInputStream.getManifest();
	}

	/**
	 * Extract all files to the indicated direcotry, then closes the Jar file. 
	 * The directory should already exist.
	 * 
	 * @param targetDirectory the destination directory for the jar contents. Should exist.
	 * @throws IOException (including the JarReader subclasses of it) to indicate errors
	 */	
	public void extractAll(File targetDirectory) throws IOException {
		if (!targetDirectory.exists()) {
			throw new MissingTargetDirectoryException(targetDirectory.toString());
		}
		JarEntry entry;
		while ((entry = fJarInputStream.getNextJarEntry()) != null) {
			if (entry.getName().startsWith("..")) {
				// this is a malformed jar which I don't want to touch with a ten-foot pole.
				// Unfortunately, the Rational ClearCase plugin suffers from this.
				throw new EntryStartsWithDotDotException(entry.getName());
			}
			if (entry.isDirectory()) {
				// Doesn't happen
				String subdirName = entry.getName();
				File newSubDir = new File(targetDirectory, subdirName);
				if (!newSubDir.exists() && !newSubDir.mkdirs()) {
					throw new FailedToCreateDirectoryOrFile(newSubDir.toString());
				}
			}
			else {
				String entryName = entry.getName();
				// Pick off the directory name part and mkdir it if necessary
				int lastSlash = entryName.lastIndexOf('/');
				if (lastSlash != -1) {
					String dirPart = entryName.substring(0, lastSlash);
					File dirFile = new File(targetDirectory.getCanonicalPath() + File.separator + dirPart);
					if (!dirFile.exists()) {
						if (!dirFile.mkdirs()) {
							throw new FailedToCreateDirectoryOrFile(dirFile.toString());
						}
					}
					else if (!dirFile.isDirectory()) {
						throw new FailedToCreateDirectoryOrFile(dirFile.toString());
					}
					// else it exists and is a directory
				}
				File newFile = new File(targetDirectory, entryName);
				if (!newFile.createNewFile()) {
					throw new FailedToCreateDirectoryOrFile(newFile.toString());
				}
				
				int uncompressedSize = (int)entry.getSize();
				FileOutputStream fos = new FileOutputStream(newFile);
				int bufferSize = Math.max(uncompressedSize, 4096);
				byte[] buffer = new byte[bufferSize];
				int actualCount;
				while ((actualCount = fJarInputStream.read(buffer, 0, bufferSize)) != -1) {
					fos.write(buffer, 0, actualCount);
				}
				fos.close();
			}
		}
	}
	
	public void close() throws IOException {
		if (fJarInputStream != null) {
			fJarInputStream.close();
		} 
	}
}
