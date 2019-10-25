/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: ProbeFileModel.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import java.util.Arrays;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.models.internal.probekit.DocumentRoot;
import org.eclipse.hyades.models.internal.probekit.Fragment;
import org.eclipse.hyades.models.internal.probekit.FragmentType;
import org.eclipse.hyades.models.internal.probekit.Probe;
import org.eclipse.hyades.models.internal.probekit.Probekit;
import org.eclipse.hyades.models.internal.probekit.ProbekitFactory;
import org.eclipse.hyades.models.internal.probekit.ProbekitPackage;
import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.JavaModelException;


/**
 * This type represents the data needed to construct a default probe file.
 * The fields on this type represent the information per file (e.g., version, id)
 * while the IProbeMetaData represents the data per probe in the file.
 */
public final class ProbeFileModel {
	private IProbeMetaData _probeMetaData = null;
	private String _sourceContainerPath = null;
	private String _fileName = FILE_NAME_DEFAULT;
	private String _encoding = null;	
	private static final String FILE_EXTENSION = ".probe"; //$NON-NLS-1$
	private static final String FILE_NAME_DEFAULT = "myprobe" + FILE_EXTENSION; //$NON-NLS-1$
	private static final String NO_CONTAINER = ""; //$NON-NLS-1$
	private static final String XML_ENCODING_CHOICES = "ASCII UTF-8 UTF-16 UTF-16BE UTF-16LE ISO-8859-1"; //$NON-NLS-1$	
	private static final String XML_ENCODING_DEFAULT = "UTF-8"; //$NON-NLS-1$
	private static final ProbekitPackage PROBEKIT_PACKAGE = ProbekitPackage.eINSTANCE;
	private static final ProbekitFactory PROBEKIT_FACTORY = PROBEKIT_PACKAGE.getProbekitFactory();
	
	public ProbeFileModel(IProbeMetaData probe) {
		setProbeMetaData(probe);
	}
	
	public final DocumentRoot createInitialModel() {
		DocumentRoot documentRoot = PROBEKIT_FACTORY.createDocumentRoot();
		Probekit probekit = PROBEKIT_FACTORY.createProbekit();
		documentRoot.setProbekit(probekit);
		if(getProbeMetaData().getProbeType() != IProbeMetaData.NO_PROBE) {
			probekit.getProbe().add(createProbe());
		}
		return documentRoot;
	}
	
	public Probe createProbe() {
		Probe probe = PROBEKIT_FACTORY.createProbe();
		FragmentType type = getProbeMetaData().getFragmentType();
		Fragment fragment = PROBEKIT_FACTORY.createFragment();
		fragment.setType(type);
		fragment.setCode(getProbeMetaData().getCode());
		fragment.getData().addAll(Arrays.asList(getProbeMetaData().getDataItems()));
		probe.getFragment().add(fragment);
		return probe;
	}
	
	public void forceValid() {
		getProbeMetaData().forceValid();
	}
	
	public IFile getModelFile() throws JavaModelException {
		IContainer sourceContainer = JavaUtil.getSourceContainer(_sourceContainerPath);
		if(sourceContainer == null) {
			return null;
		}
		
		IPath filePath = sourceContainer.getFullPath().append(_fileName);
		return ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
	}
	
	public String[] getXMLEncodingChoices() {
		StringTokenizer stringTokenizer = new StringTokenizer(XML_ENCODING_CHOICES);
		String[] choices = new String[stringTokenizer.countTokens()];
		int count = 0;
		while ( stringTokenizer.hasMoreTokens() ) {
			choices[count++] = (stringTokenizer.nextToken());
		}
		return choices;
	}
	
	public String getDefaultEncoding() {
		return XML_ENCODING_DEFAULT;
	}
	
	public String getEncoding() {
		if (_encoding != null)
			return _encoding;
		else
			return getDefaultEncoding();
	}	

	public void setEncoding(String encoding) {
		_encoding = encoding;
	}		
	
	public void release() {
		getProbeMetaData().release();
	}
	
	public DataItem createDataItem() {
		DataType[] availableTypes = getProbeMetaData().getAvailableDataTypes();
		DataItem item = ProbekitTypesSingleton.singleton().createDataItem(availableTypes, getProbeMetaData().countDataItems());
		getProbeMetaData().addDataItem(item);
		
		return item;
	}
	
	
	public String getDataExplanation() {
		return ProbekitMessages._66;
	}
	public boolean isValidCombination(IProbeMetaData newProbe, FragmentType type) {
		DataItem[] invalidDataItems = getInvalidDataItems(newProbe, type);		
		if(invalidDataItems.length == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isValidFileName() {
		if(!_fileName.endsWith(FILE_EXTENSION)) {
			return false;
		}
		
		if(!(_fileName.length()>FILE_EXTENSION.length())) {
			return false;
		}
		
		return true;
	}
	
	public boolean isValidFile() {		
		try {
			IFile file = getModelFile();
			if((file == null) || file.exists()) {
				return false;
			}
		}
		catch(JavaModelException exc) {
    		ProbekitUIPlugin.getPlugin().log(exc);
			return false;
		}
		
		return true;
	}

	public boolean isValidSourceContainer() {
		try {
			IContainer container = JavaUtil.getSourceContainer(getSourceContainerPath());
			return (container != null);
		}
		catch(JavaModelException exc) {
			return false;
		}
	}
	
	public boolean isValidDataItems() {
		DataItem[] items = getProbeMetaData().getDataItems();
		return ProbekitTypesSingleton.singleton().isValidDataItemNames(items);
	}
	
	public DataItem[] getInvalidDataItems(IProbeMetaData probe, FragmentType type) {
		return probe.getInvalidDataItems(type);
	}
	
	public IProbeMetaData getProbeMetaData() {
		return _probeMetaData;
	}
	
	public void setProbeMetaData(IProbeMetaData probe) {
		_probeMetaData = probe;
	}
	
	public String getSourceContainerPath() {
		return _sourceContainerPath;
	}
	
	public void setSourceContainerPath(String containerPath) {
		_sourceContainerPath = containerPath;
	}
	
	public void setSourceContainer(IContainer container) {
		String path;
		if(container == null) {
			path = NO_CONTAINER;
		}
		else {
			path = container.getFullPath().toString();
		}
		setSourceContainerPath(path);
	}
	
	public String getFileName() {
		return _fileName;
	}
	
	public void setFileName(String fileName) {
		_fileName = fileName;
	}
}
