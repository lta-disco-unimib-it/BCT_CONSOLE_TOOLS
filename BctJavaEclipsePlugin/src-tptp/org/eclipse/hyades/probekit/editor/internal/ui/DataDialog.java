/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: DataDialog.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.models.internal.probekit.DataType;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;



public class DataDialog extends Dialog {
	private DataItem _item = null;
	private String _title = null;
	private Text _name = null;
	private Combo _combo = null;
	private String[] _dataTypeNames = null;
	
	public DataDialog(Shell shell, String[] dataTypeNames, DataItem item) {
		super(shell);
		int shellStyle = getShellStyle();
		setShellStyle(shellStyle | SWT.RESIZE);
		_dataTypeNames = dataTypeNames;
		_item = item;
	}
	
	public DataItem getResult() {
		return _item;
	}
	
	void update() {
		String typeName = _combo.getText();
		String name = _name.getText();
		DataType newType = DataType.get(typeName);
		getResult().setType(newType);
		getResult().setName(name);
	}
	
	private String[] getDataTypeNames() {
		return _dataTypeNames;
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite)super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		GridData data = new GridData(GridData.FILL_BOTH);
		composite.setLayout(layout);
		composite.setLayoutData(data);
		
		return createDialogContents(composite);
	}
	
	protected Composite createDialogContents(Composite parent) {
		// I'm not sure why, but to navigate through this Dialog's Text
		// widgets, you need to type Ctrl-Tab instead of Tab alone. See
		// org.eclipse.swt.widgets.Control::translateTraversal(MSG),
		// block "case OS.VK_TAB:".
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 300;
		composite.setLayout(layout);
		composite.setLayoutData(data);
		
		Label comboLabel = new Label(composite, SWT.NULL);
		comboLabel.setText(ProbekitMessages._40);
				
		DataModifyListener listener = new DataModifyListener();
		_combo = new Combo(composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		GridData comboData = new GridData(GridData.FILL_HORIZONTAL);
		_combo.setItems(getDataTypeNames());
//		_combo.setEditable(false);
//		_combo.setText(ProbekitUtil.getDataTypeName(getResult()));
		_combo.setLayoutData(comboData);
		_combo.addModifyListener(listener);
		
		Label textLabel = new Label(composite, SWT.NULL);
		textLabel.setText(ProbekitMessages._41);
		
		_name = new Text(composite, SWT.BORDER | SWT.SINGLE);
//		_name.setText(ProbekitUtil.getDataItemName(getResult()));
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		_name.setLayoutData(textData);
		_name.addModifyListener(listener);
		
		return composite;
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (getTitle() != null) {
			shell.setText(getTitle());
		}
	}
	
	private String getTitle() {
		return _title;
	}
	
	public void setTitle(String title) {
		_title = title;
	}
	
	private class DataModifyListener implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			update();
		}
	}
}
