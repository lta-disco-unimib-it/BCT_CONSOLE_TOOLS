/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: DiscardDataReadOnlyDialog.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui.newFile;

import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.ui.WrappedCheckedTreeSelectionDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;



class DiscardDataReadOnlyDialog extends	WrappedCheckedTreeSelectionDialog {
	public DiscardDataReadOnlyDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
	}
	
	protected Composite createSelectionButtons(Composite composite) {
		// Don't create the "Select All" and "Deselect All" buttons.
		return new Composite(composite, SWT.NONE);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		String discardData = ProbekitMessages._33;
		String cancelConversion = ProbekitMessages._34;
		createButton(parent, IDialogConstants.OK_ID, discardData, true);
		createButton(parent, IDialogConstants.CANCEL_ID, cancelConversion, false);
	}

	protected CheckboxTreeViewer createTreeViewer(Composite parent) {
		CheckboxTreeViewer viewer = super.createTreeViewer(parent);
		Object input = viewer.getInput();
		ITreeContentProvider provider = (ITreeContentProvider)viewer.getContentProvider();
		final Object[] everything = provider.getElements(input);
		viewer.setGrayedElements(everything);
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				// Do not allow the user to uncheck anything.
				getTreeViewer().setCheckedElements(everything);		
			}
		});
		return viewer;
	}
}
