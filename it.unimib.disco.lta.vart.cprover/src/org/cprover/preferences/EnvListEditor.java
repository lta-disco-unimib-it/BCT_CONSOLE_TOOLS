package org.cprover.preferences;

import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Class: EnvListEditor
 *
 * Purpose: Implement a ListEditor for environment setting.
 */

public class EnvListEditor extends ListEditor {
	private InputDialog inDlg;
	
	public EnvListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		this.inDlg = new InputDialog(parent.getShell(), "New environment setting", "Key=Value", "", null);
	}

	protected String createList(String[] items) {
		StringBuffer sbuf = new StringBuffer();

		boolean first  = true;
		for (String it: items) {
			if (it.equals(""))
				continue;
			if (first) {
				sbuf.append(it);
				first = false;
			}
			else
				sbuf.append(","+ it);			
		}
		
		return sbuf.toString();
	}

	protected String getNewInputObject() {
		this.inDlg.open();		
		String s = this.inDlg.getValue();
		if (s == null)
			return "";
		return s;
	}

	protected String[] parseString(String stringList) {
		StringTokenizer stok = new StringTokenizer(stringList, ",");
		String[] tokens = new String[stok.countTokens()];
		int k = 0;
		while (stok.hasMoreTokens()) 
			tokens[k++] = stok.nextToken();
		return tokens;
	}

}
