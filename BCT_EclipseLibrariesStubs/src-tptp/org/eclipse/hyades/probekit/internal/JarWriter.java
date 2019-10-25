/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: JarWriter.java,v 1.1 2013-07-25 22:04:42 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit.internal;
import java.util.jar.*;
import java.io.*;

/**
 * This class implements a "jar cf" command - you can create a jar file
 * from files and directories on your system using this class.
 */
public class JarWriter {
	/**
	 * This exception class is thrown if the target jar file provided
	 * to JarWriter already exists.
	 */
	public class TargetAlreadyExists extends IOException {
		/**
		 * This serialVersionUID was generated May 19, 2005.
		 */
		private static final long serialVersionUID = 4050205249639822134L;

		public TargetAlreadyExists(String s) {
			super(s);
		}
	}
	
	/**
	 * Creates an instance you can use to write files and directories to a jar file.
	 * 
	 * @param targetJar a File indicating where to write the jar file to. Should not exist.
	 * @param manifest a Manifest for the jar file, or null  to construct one automatically(?).
	 * @param comment a comment for the jar file, or null for none.
	 * @throws IOException if there is any problem, including JarWriter$TargetAlreadyExists.
	 */
	public JarWriter(File targetJar, Manifest manifest, String comment) throws IOException {
		if (targetJar.exists()) {
			throw new TargetAlreadyExists(targetJar.toString());
		}
		if (manifest != null) {
			// Create a Jar output stream with an explicit manifest
			fJarOutputStream= new JarOutputStream(new FileOutputStream(targetJar), manifest);
		}
		else {
			// Create a Jar output stream with a default manifest(?)
			fJarOutputStream= new JarOutputStream(new FileOutputStream(targetJar));
		}
		if (comment != null) {
			fJarOutputStream.setComment(comment);
		}
	}

	/**
	 * Closes the archive and does all required cleanup.
	 *
	 * @throws	IOException		to signal any unusal termination.
	 */
	public void close() throws IOException {
		if (fJarOutputStream != null) {
			fJarOutputStream.close();
		}
	}

	/**
	 * Writes the passed File to the current archive. If the File is a directory, writes
	 * all its contents to the archive, recursively. Never recurses into
	 * a directory called META-INF - that has to be written specially.
	 *
	 * @param file				the file or directory to be written
	 * @param destinationPath	the path for the file inside the archive. Should be a relative path
	 * 							string and may use system-dependent directory separator or the 
	 * 							jar-standard forward slash.
	 * @throws	IOException		to signal any unusal termination.
	 */
	public void write(File file, String destinationDir, boolean initialDirectory) throws IOException {
		if (file.isDirectory()) {
			File[] contents = file.listFiles();

			// If this directory is called META-INF then strip any
			// member file called MANIFEST.MF ... the manifest is managed separately.
			// "Strip" the member by nulling out its name; later we check for null names.
			if (file.getName().equals("META-INF")) {
				for (int j = 0; j < contents.length; j++) {
					if (contents[j].equals("MANIFEST.MF")) {
						contents[j] = null;
					}
				}
			}

			// The target subdirectory name in the jar file depends on 
			// the initialDirectory flag. If true (as when we are first called),
			// then we use the destinationDir as the target dir.
			// There is a special case in the file loop for the empty string.
			//
			// If initialDirectory is false (as when we have done recursion),
			// we append file.getName() to the destinationDir to get the new
			// destination dir, with a slash separator if the old destination dir
			// wasn't the empty string.

			String subdirName;
			if (initialDirectory) {
				subdirName = destinationDir;
			}
			else {
				if (destinationDir.equals("")) {
					subdirName = file.getName();
				}
				else {
					subdirName = destinationDir + "/" + file.getName();
				}
			}
			for (int i = 0; i < contents.length; i++) {
				// contents[i] might be null if it was MANIFEST.MF - see above
				if (contents[i] != null) {
					write(contents[i], subdirName, false);
				}
			}
		}
		else {
			// Read the contents of this file into a buffer, then
			// use the byte[] form of write() to write to the jar stream.
			ByteArrayOutputStream output = null;
			BufferedInputStream contentStream = null;

			try {
				output = new ByteArrayOutputStream();
				contentStream = new BufferedInputStream(new FileInputStream(file));
				// The byte array will probably be allocated big enough to hold the whole file,
				// assuming available() on a file input stream returns the size of the file.
				// Just in case it returns something stupid like '1', we have a 4K minimum.
				int chunkSize = Math.max(contentStream.available(), 4096);
				byte[] readBuffer = new byte[chunkSize];
				int count;
				while ((count = contentStream.read(readBuffer, 0, chunkSize)) != -1) {
					output.write(readBuffer, 0, count);
				}
			} finally {
				if (output != null)
					output.close();
				if (contentStream != null)
					contentStream.close();
			}

			long lastModified = file.lastModified();
			String destinationFile;
			if (destinationDir == "") {
				destinationFile = file.getName();
			}
			else {
				destinationFile = destinationDir + "/" + file.getName();
			}
			write(destinationFile, output.toByteArray(), lastModified);
		}
	}

	/**
	 * Creates a new JarEntry with the passed path and contents, and writes it
	 * to the current archive.
	 *
	 * @param	destinationPath	the path inside the archive to the file, with slashes.
	 * @param	contents		the bytes to write
	 * @param	lastModified	a long which represents the last modification date
	 * @throws	IOException		if an I/O error has occurred
	 */
	protected void write(String destinationPath, byte[] contents, long lastModified) throws IOException {
		JarEntry newEntry= new JarEntry(destinationPath.replace(File.separatorChar, '/'));
		newEntry.setMethod(JarEntry.DEFLATED);
		// Set modification time
		newEntry.setTime(lastModified);

		fJarOutputStream.putNextEntry(newEntry);
		fJarOutputStream.write(contents);
	}

	private JarOutputStream fJarOutputStream;
}

