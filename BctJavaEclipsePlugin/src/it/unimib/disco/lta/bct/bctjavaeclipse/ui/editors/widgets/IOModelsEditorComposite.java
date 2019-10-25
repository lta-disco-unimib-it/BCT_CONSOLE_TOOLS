/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.IOModelsEditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class IOModelsEditorComposite extends Composite {

	private Table table;
	private TableEditor tableEditor;
	private IOModelsEditor editor;
	private final int EDITABLE_COLUMN_INDEX = 0;

	public IOModelsEditorComposite(IOModelsEditor editor, Composite parent, int style, String header) {
		super(parent, style);

		this.editor = editor;
		setLayout();
		createWidgets(header);
	}

	private void setLayout() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		this.setLayout(layout);
	}

	private void createWidgets(String header) {
		createTable(header);
		createButtons();
	}

	private void createTable(String header) {
		table = new Table(this, SWT.FULL_SELECTION | SWT.MULTI | SWT.CHECK);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		table.setLayoutData(gridData);

		table.addSelectionListener(createTableSelectionListener());

		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText(header);
		column.setWidth(600);

		tableEditor = new TableEditor(table);
		tableEditor.horizontalAlignment = SWT.LEFT;
		tableEditor.grabHorizontal = true;
		tableEditor.grabVertical = true;
		tableEditor.minimumWidth = 50;
	}

	private void createButtons() {
		Button button = new Button(this, SWT.NONE);
		button.setText("Add new invariant");
		button.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE));
		button.addSelectionListener(createAddInvariantButtonSelectionListener());

		button = new Button(this, SWT.NONE);
		button.setText("Remove selected invariants");
		button.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		button.addSelectionListener(createRemoveInvariantsButtonSelectionListener());
	}

	public void populateTable(List<IoExpression> expressions) {
		TableItem item;
		for (IoExpression expression : expressions) {
			item = new TableItem(table, SWT.None);
			item.setText(expression.toString());
		}
	}

	public List<IoExpression> getIoExpressions() {
		TableItem[] items = table.getItems();
		ArrayList<IoExpression> expressions = new ArrayList<IoExpression>(items.length);

		for (TableItem item : items) {
			IoExpression expression = new IoExpression(item.getText());
			expressions.add(expression);
		}
		return expressions;
	}

	private SelectionListener createTableSelectionListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				final TableItem item = (TableItem) e.item;
				if (item == null)
					return;

				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.SINGLE);
				newEditor.setText(item.getText(EDITABLE_COLUMN_INDEX));
				newEditor.addModifyListener(createModifyListener());
				newEditor.setFocus();
				tableEditor.setEditor(newEditor, item, EDITABLE_COLUMN_INDEX);
			}
		};
	}

	private SelectionListener createAddInvariantButtonSelectionListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				editor.setDirty();

				// Clean up any previous editor control
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				TableItem item = new TableItem(table, SWT.NONE);
				Text newEditor = new Text(table, SWT.SINGLE);
				newEditor.setText("");
				newEditor.addModifyListener(createModifyListener());
				newEditor.setFocus();
				tableEditor.setEditor(newEditor, item, EDITABLE_COLUMN_INDEX);
			}
		};
	}

	private SelectionListener createRemoveInvariantsButtonSelectionListener() {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				editor.setDirty();

				// Clean up any previous editor control
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				ArrayList<Integer> checkedItemsIndices = new ArrayList<Integer>();
				TableItem[] items = table.getItems();
				for (int i = 0; i < items.length; i++) {
					if (items[i].getChecked())
						checkedItemsIndices.add(i);
				}
				int[] indicesToRemove = new int[checkedItemsIndices.size()];
				for (int i = 0; i < indicesToRemove.length; i++) {
					indicesToRemove[i] = checkedItemsIndices.get(i);
				}
				table.remove(indicesToRemove);
			}
		};
	}

	private ModifyListener createModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text text = (Text) tableEditor.getEditor();
				String textStr = text.getText();
				tableEditor.getItem().setText(EDITABLE_COLUMN_INDEX, textStr);
				editor.setDirty();
			}
		};
	}

	public void find(String regex) {
		List<TableItem> selection = new ArrayList<TableItem>();

		for (TableItem item : table.getItems()) {
			if (item.getText().matches(regex)) {
				selection.add(item);
			}
		}
		table.setSelection(selection.toArray(new TableItem[0]));
	}
	
	public void deselectAll() {
		table.deselectAll();
	}
}
