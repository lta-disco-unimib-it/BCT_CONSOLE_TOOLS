/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: WrappedCheckedTreeSelectionDialog.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;


public class WrappedCheckedTreeSelectionDialog extends CheckedTreeSelectionDialog {
	private Label _label;
	
	public WrappedCheckedTreeSelectionDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
	}
	
	protected Label createMessageArea(Composite parent) {
		// Do not set the text on the Label initially because that makes
		// the width of the dialog equal the length of the Label's text.
		// See the "create()" method for details.
		_label = new Label(parent, SWT.WRAP);
		GridData labelData = new GridData(GridData.FILL_BOTH);
		_label.setLayoutData(labelData);
		_label.setFont(parent.getFont());
		
		return _label;
	}

	// Override the Window "create()" method so that the text description 
	// can appear wrapped initially, rather than in one long line that 
	// can make the dialog appear abnormally wide.
	public void create() {
		// First create the dialog without the text so that the dialog's 
		// width can be determined.
		super.create();
		
		// Calling "computeSize(currentWidth, SWT.DEFAULT)" does not increase
		// the height of the contents or the shell. Rather than rely on the 
		// default calculation, figure out how much vertical space the label 
		// needs, and add it to the contents and shell height.
		Point currentShellSize = getShell().getSize();
		Point currentContentsSize = getContents().getSize();
		int currentWidth = currentContentsSize.x;
		if(getMessage() != null) {
			_label.setText(getMessage());
		}
		_label.pack();
		Point labelSize = _label.computeSize(currentWidth, SWT.DEFAULT, true);
		Point newContentsSize = new Point(currentWidth, currentContentsSize.y + labelSize.y);
		getContents().setSize(newContentsSize);
		Point newShellSize = getShell().computeSize(currentWidth, currentShellSize.y + labelSize.y, true);
		getShell().setSize(newShellSize);
	}
	
	protected void computeResult() {
		// Return only the selected, non-grey elements.
		CheckboxTreeViewer viewer = getTreeViewer();
		Object[] checked = viewer.getCheckedElements();
		List checkedNotGray = new ArrayList(checked.length);
		for(int i=0; i<checked.length; i++) {
			Object check = checked[i];
			if(!viewer.getGrayed(check)) {
				checkedNotGray.add(check);
			}
		}
		setResult(checkedNotGray);
	}
	
	// Make this method public for the automated tests.
	public Button getButton(int buttonId) {
		return super.getButton(buttonId);
	}
}
