/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: SelectOneIncludedValidator.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.hyades.probekit.editor.internal.core.util.ResourceUtil;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;



public class SelectOneIncludedValidator implements ISelectionStatusValidator {
	private final List _included;
	private final String _okMessage;
	private final String _errorMessage;
	
	public SelectOneIncludedValidator(List includedResources, String okMessage, String errorMessage) {
		_included = includedResources;
		_okMessage = okMessage;
		_errorMessage = errorMessage;
	}
	
	public IStatus validate(Object[] obj) {
		Object[] res = ResourceUtil.filter(obj, _included);
		if(res.length == 1) {
			return getOK();
		}
		else {
			return getChangeNumber();
		}
	}
	
	IStatus getOK() {
		IStatus status = ResourceUtil.createInitialStatus(
				IStatus.OK, 
				_okMessage, 
				null);
		return status;
	}
	
	IStatus getChangeNumber() {
		IStatus status = ResourceUtil.createInitialStatus(
				IStatus.ERROR, 
				_errorMessage, 
				null);
		return status;
	}
}