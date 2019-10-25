/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: DialogUtil.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;




public final class DialogUtil {
	public static final String PROBLEMS_VIEW_ID = "org.eclipse.ui.views.ProblemView"; //$NON-NLS-1$

	public static CheckedTreeSelectionDialog createResourceDialog(Shell shell, IResource[] input, List includedResources) {
		IResource[] res = (IResource[])includedResources.toArray(new IResource[0]);

		WrappedCheckedTreeSelectionDialog dialog = 
			new WrappedCheckedTreeSelectionDialog(
					shell,
					new ResourceLabelProvider(),
					new ResourceContentProvider(res)
			);
		dialog.setInput(input);
		dialog.setSorter(new ViewerSorter());
		dialog.setBlockOnOpen(true);
		dialog.setContainerMode(true);
		return dialog;
	}
	
	public static CheckedTreeSelectionDialog createCompoundDialog(Shell shell, 
																IJavaProject[] input, 
																CompoundTreeContentProvider compoundTreeContentProvider,
																CompoundLabelProvider compoundLabelProvider) {
		WrappedCheckedTreeSelectionDialog dialog = 
			new WrappedCheckedTreeSelectionDialog(
					shell,
					compoundLabelProvider,
					compoundTreeContentProvider
			);
		dialog.setInput(input);
		dialog.setSorter(new CompoundViewerSorter());
		dialog.setBlockOnOpen(true);
		dialog.setContainerMode(true);
		return dialog;
	}

	private DialogUtil() {
	}

	public static void openProblemsView() {
		try {
			String viewId = DialogUtil.PROBLEMS_VIEW_ID;
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = workbenchWindow.getActivePage();
			if(page != null) {
				page.showView(viewId);
			}
		}
		catch(PartInitException exc) {
			ProbekitUIPlugin.getPlugin().log(exc);
		}
	}
	
	public static Image getImage(URL url) {
		return ImageDescriptor.createFromURL(url).createImage();
	}
}
