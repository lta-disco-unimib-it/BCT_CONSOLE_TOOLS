/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: CreateInitialModelOperation.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.core.newFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;




public class CreateInitialModelOperation implements IWorkspaceRunnable {
	private ProbeFileModel _model;
	
	public CreateInitialModelOperation(ProbeFileModel model) {
		_model = model;
	}

	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			// Create a resource set
			//
			ResourceSet resourceSet = new ResourceSetImpl();
	
			// Get the URI of the model file.
			//
			URI fileURI = URI.createPlatformResourceURI(_model.getModelFile().getFullPath().toString());
	
			// Create a resource for this file.
			//
			Resource resource = resourceSet.createResource(fileURI);
	
			// Add the initial model object to the contents.
			//
			EObject rootObject = _model.createInitialModel();
			if (rootObject != null) {
				resource.getContents().add(rootObject);
			}
	
			// Save the contents of the resource to the file system.
			//
			Map options = new HashMap();
			options.put(XMLResource.OPTION_ENCODING, _model.getEncoding());
			resource.save(options);
		}
		catch(IOException exc) {
			IStatus status = ResourceUtil.createInitialStatus(IStatus.ERROR, exc);
			throw new CoreException(status);
		}
		finally {
			monitor.done();
		}
	}
}
