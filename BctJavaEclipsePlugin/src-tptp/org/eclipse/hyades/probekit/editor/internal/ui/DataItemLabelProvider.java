/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: DataItemLabelProvider.java,v 1.1 2011-12-01 21:34:09 pastore Exp $
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/


package org.eclipse.hyades.probekit.editor.internal.ui;

import org.eclipse.hyades.models.internal.probekit.DataItem;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitUtil;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;



public class DataItemLabelProvider extends LabelProvider {
	public String getText(Object element) {
		if(element instanceof DataItem) {
			DataItem item = (DataItem)element;
			return NLS.bind(ProbekitMessages._61, new Object[]{ProbekitUtil.getDataTypeName(item), ProbekitUtil.getDataTypeName(item)});
		}
		return super.getText(element);
	}
}