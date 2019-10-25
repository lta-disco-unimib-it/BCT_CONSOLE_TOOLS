/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: InstrumentClassesAction.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui.instrument;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.hyades.probekit.editor.internal.core.instrument.InstrumentOperation.InstrumentOperationUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.JavaUtil;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitUtil;
import org.eclipse.hyades.probekit.editor.internal.ui.DialogUtil;
import org.eclipse.hyades.probekit.editor.internal.ui.SelectOneIncludedValidator;
import org.eclipse.hyades.probekit.ui.internal.ContextIds;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.osgi.util.NLS;

/**
 * When the user selects several classes/wars/ears/jars in the workspace,
 * this action pops up the dialog to choose the .probe to instrument them
 * with, and then instruments them.
 */
public class InstrumentClassesAction implements IActionDelegate {
    public void run(IAction action) {
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = display.getActiveShell();
		IFile probeFile = getProbeFile(shell);
		if(probeFile == null) {
			// Nothing to do
			return;
		}
		
		List resourcesToInstrument = getResourcesToInstrument();
		InstrumentRunnableWithProgress runInstrument = 
			new InstrumentRunnableWithProgress(
					shell,
					display,
					probeFile,
					resourcesToInstrument
			);
		runInstrument.open();
    }
    
    private IFile getProbeFile(Shell shell) {
    	IFile probeFile = null;
    	try {
    		List allProbeFiles = ProbekitUtil.getProbeFiles();
	    	CheckedTreeSelectionDialog dialog = 
	    		DialogUtil.createResourceDialog(
		    		shell,
					JavaUtil.getProjects(allProbeFiles),
					allProbeFiles
				);
			dialog.setValidator(new SelectOneIncludedValidator(allProbeFiles,
					ProbekitMessages._92, ProbekitMessages._93));
			dialog.setInitialSelections(new Object[0]);
			dialog.setTitle(ProbekitMessages._96);
			dialog.setMessage(ProbekitMessages._97);
			
			dialog.create(); // create controls and the OK button
			dialog.getOkButton().setText(ProbekitMessages._146);
			
			Shell dialogShell = dialog.getShell();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(dialogShell, ContextIds.PROBEKIT_INSTRUMENT);
			
			dialog.open();
			
			Object[] result = dialog.getResult();
			if(result != null) { 
				// iterate - skip projects and find the first file;
				// the dialog allows selection of a single file, so no checks here
				for(int i = 0; i < result.length; i++) {
					if(result[i] instanceof IFile) { 
						probeFile = (IFile)result[i];
						break;
					} else if(result[i] instanceof IAdaptable) {
						IAdaptable a = (IAdaptable)result[i];
						IResource res = (IResource)a.getAdapter(IResource.class);
						if(res instanceof IFile) {
							probeFile = (IFile)res;
							break;
						}
					}
				}
			}
    	}
		catch(CoreException exc) {
    		ProbekitUIPlugin.getPlugin().log(exc);
    		MessageDialog.openError(
    				shell, 
					NLS.bind(ProbekitMessages._90, new Object[]{probeFile.getName()}),
					ProbekitMessages._91
				);
		}
		return probeFile;
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
        _selection = selection;
        
        // Handle the enabled/disabled that the plugin.xml file can't.
        boolean enabled = true;
        if(!ProbekitUtil.isSupportedPlatform()) {
        	enabled = false;
        }
        else if(selection instanceof IStructuredSelection) {
        	IStructuredSelection strSel = (IStructuredSelection)selection;
        	Object[] selected = strSel.toArray();
        	for(int i=0; i<selected.length; i++) {
        		// We know from the plugin.xml that this is either an IFile, 
        		// IPackageFragmentRoot, or IClassFile.
        		Object theselected = selected[i];
				if(theselected instanceof IClassFile) {
        			IClassFile cFile = (IClassFile)theselected;
        			if(isInArchive(cFile)) {
        				enabled = false;
        				break;
        			}
        		}
        	}
        }
        action.setEnabled(enabled);
    }
    
    private boolean isInArchive(IClassFile cFile) {
    	IPackageFragmentRoot container = (IPackageFragmentRoot)cFile.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
    	if(container == null) {
    		return true;
    	}
    	
    	return (container.isArchive());
    }
    
    // Can return a list of IJavaElement (if from the Package Explorer view),
    // or a list of IResource (if from the Navigator view).
    private List getClasses() {
		if ((_selection == null) || _selection.isEmpty() || !(_selection instanceof IStructuredSelection)) {
			return null;
		}
		
		IStructuredSelection strSel = (IStructuredSelection)_selection;
		List list = new ArrayList(strSel.toList());
		return list;		
    }
    
    private List getResourcesToInstrument() {
    	List selected = getClasses();
    	Iterator iterator = selected.iterator();
    	Set soFar = new HashSet(selected.size());
    	while(iterator.hasNext()) {
	    	IAdaptable adaptable = (IAdaptable)iterator.next();
	    	IJavaElement res = (IJavaElement)adaptable.getAdapter(IJavaElement.class);
	    	if(res != null) {
    			if(InstrumentOperationUtil.canFileBeProbed(res)) {
		    		soFar.add(res);
	    		}
	    	}
    	}
    	return selected;
    }
    
    private ISelection _selection;

}
