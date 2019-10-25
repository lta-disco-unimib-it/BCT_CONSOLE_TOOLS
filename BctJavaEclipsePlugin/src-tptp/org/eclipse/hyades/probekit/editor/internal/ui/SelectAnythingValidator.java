/**********************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: SelectAnythingValidator.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;



public class SelectAnythingValidator implements ISelectionStatusValidator {
	private List _included;
	
	protected boolean cont = false;
	protected ArrayList vmLibPath = null;
	
	public SelectAnythingValidator(List includedResources) {
		_included = includedResources;
	}
	
	public IStatus validate(Object[] obj) {
		Object[] res = ResourceUtil.filter(obj, _included);
		if(res.length >= 1) {
			return getVMInstrumentation(obj);
		}
		else {
			return getChangeNumber();
		}
	}
	protected IStatus getVMInstrumentation(Object[] objects)
	{
		if(vmLibPath == null)
		{
			vmLibPath = new ArrayList();
			
			IVMInstallType[] types = JavaRuntime.getVMInstallTypes();
			for (int i = 0; i < types.length; i++) {
				
				IVMInstall[] installs = types[i].getVMInstalls();
				for (int j = 0; j < installs.length; j++) {
					IVMInstall install = installs[j];					
					
					vmLibPath.add(install.getInstallLocation().toString());
				}
			}
		}
		
		String msg = null;
		for(int idx=0; idx<objects.length; idx++)
		{
			if(msg != null)
				break;
			
			Object obj = objects[idx];
			if(obj instanceof IPackageFragmentRoot)
			{
				String path = ((IPackageFragmentRoot)obj).getPath().toOSString();
				for(int i=0;i<vmLibPath.size(); i++)
				{
					if(path.startsWith(vmLibPath.get(i).toString()))
					{
					    msg = NLS.bind(ProbekitMessages._147, path);
						break;						
					}
				}
			}
		}
		
		if(msg!=null)
		{
		
			IStatus status = new Status(
					IStatus.WARNING, ProbekitUIPlugin.getPluginId(), IStatus.WARNING, 
					msg, 
					null);
			return status;
		}
		
		return getOK();
	}
	
	
	IStatus getOK() {
		IStatus status = ResourceUtil.createInitialStatus(IStatus.OK,
				ProbekitMessages._87, null);
		return status;
	}
	
	IStatus getChangeNumber() {
		IStatus status = ResourceUtil.createInitialStatus(IStatus.ERROR,
				ProbekitMessages._88, null);
		return status;
	}
}