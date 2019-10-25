package org.cprover.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Class: ComboFieldEditor
 *
 * Purpose: Implement a Combo type FieldEditor
 */

public class ComboFieldEditor extends FieldEditor {

	private Combo combo;	
	private String value;
	
	private String[][] values;

	public ComboFieldEditor(String name, String labelText, String[][] values, Composite parent) {
		init(name, labelText);
		this.values = values;
		createControl(parent);		
	}

	protected void adjustForNumColumns(int numColumns) {
		Control control = getLabelControl();
		if (control != null) {
			((GridData)control.getLayoutData()).horizontalSpan= numColumns;
		}
		((GridData)combo.getLayoutData()).horizontalSpan= numColumns;
	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);
		control = getComboBox(parent);
		gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);
	}

	protected void doLoad() {
		updateComboForValue(getPreferenceStore().getString(getPreferenceName()));
	}

	protected void doLoadDefault() {
		updateComboForValue(getPreferenceStore().getDefaultString(getPreferenceName()));
	}

	protected void doStore() {
		if (value == null) {
			getPreferenceStore().setToDefault(getPreferenceName());
			return;
		}
	
		getPreferenceStore().setValue(getPreferenceName(), value);
	}

	public int getNumberOfControls() {
		return 2;
	}

	public Combo getComboBox(Composite parent) {
		if (combo == null) {
			combo = new Combo(parent, SWT.READ_ONLY);
			for (int i = 0; i < values.length; i++) {
				combo.add(values[i][0], i);
			}
			combo.setFont(parent.getFont());
			combo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					String oldValue = value;
					String name = combo.getText();
					value = getValueForName(name);
					setPresentsDefaultValue(false);
					fireValueChanged(VALUE, oldValue, value);					
				}
			});
		}
		return combo;
	}
	
	protected String getValueForName(String name) {
		for (int i = 0; i < values.length; i++) {
			String[] entry = values[i];
			if (name.equals(entry[0])) {
				return entry[1];
			}
		}
		return values[0][0];
	}
	
	protected void updateComboForValue(String val) {
		value = val;
		for (int i = 0; i < values.length; i++) {
			if (val.equals(values[i][1])) {
				combo.setText(values[i][0]);
				return;
			}
		}
		if (values.length > 0) {
			value = values[0][1];
			combo.setText(values[0][0]);
		}
	}
}
