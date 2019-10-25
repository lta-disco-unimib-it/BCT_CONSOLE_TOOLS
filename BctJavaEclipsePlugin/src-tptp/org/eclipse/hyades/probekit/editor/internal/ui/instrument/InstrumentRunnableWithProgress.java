/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: InstrumentRunnableWithProgress.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui.instrument;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.hyades.probekit.editor.internal.core.instrument.InstrumentOperation;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.ui.DialogUtil;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.osgi.util.NLS;


class InstrumentRunnableWithProgress implements IRunnableWithProgress {
	private final IFile _probeFile;
	private final Shell _shell;
	private final Display _display;
	private final List _elementsToInstrument;
	
	/**
	 * The List can contain IResource, IJavaElement, or both.
	 */
	public InstrumentRunnableWithProgress(Shell shell, Display display, IFile probeFile, List elements) {
		_shell = shell;
		_display = display;
		_probeFile = probeFile;
		_elementsToInstrument = elements;
	}
	
	Shell getShell() {
		return _shell;
	}
	
	private Display getDisplay() {
		return _display;
	}
	
	IFile getProbeFile() {
		return _probeFile;
	}
	
	private List getElements() {
		return _elementsToInstrument;
	}
	
	public void run(IProgressMonitor monitor) {
    	try {
			InstrumentOperation op = new InstrumentOperation(getProbeFile(), getElements());
	    	op.run(monitor);
    	}
    	catch(CoreException exc) {
    		ProbekitUIPlugin.getPlugin().log(exc);
    		int code = exc.getStatus().getCode();
    		final String excMessage = exc.getMessage();
    		switch(code) {
    			case(InstrumentOperation.COMPILE_ERROR): 
    			case(InstrumentOperation.GENERATE_ERROR):
    			case(InstrumentOperation.INSTRUMENT_ERROR):
    			case(InstrumentOperation.SETUP_ERROR): {
					getDisplay().syncExec( new Runnable() {
						public void run() {
				    		MessageDialog.openError(
				    				getShell(), 
									NLS.bind(ProbekitMessages._90, new Object[]{getProbeFile().getName()}),
									excMessage
								);
				    		DialogUtil.openProblemsView();
						}
					});
					break;
    			}
    			
    			default: {
					getDisplay().syncExec( new Runnable() {
						public void run() {
				    		MessageDialog.openError(
				    				getShell(),
				    				NLS.bind(ProbekitMessages._90,new Object[]{getProbeFile().getName()}),
									ProbekitMessages._91
								);
						}
					});
					break;
    			}
			}
    	}
	}
   	
	public void open() {
		if(getElements().size() == 0) {
			// Nothing to do
			return;
		}

   		try {
    		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
    		dialog.run(true, true, this);
    	}
    	catch(InterruptedException exc) {
    		// Nothing to do if the user cancels the action.
    	}
		catch(InvocationTargetException exc) {
    		ProbekitUIPlugin.getPlugin().log(exc);
    		MessageDialog.openError(
    				getShell(), 
    				NLS.bind(ProbekitMessages._90, new Object[]{getProbeFile().getName()}),
					ProbekitMessages._91
				);
		}
	}
}
