package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue.ModifiedInfo;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointVariable.Type;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.IOTracesEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ProgramPointsTreeComposite extends Composite {

	private Tree tree;
	private TreeEditor treeEditor;
	private int selectedColumn;
	private IOTracesEditor editor;
	private List<? extends ProgramPoint> programPoints;

	private Set<TreeItem> deletedItems = new HashSet<TreeItem>();
	private Set<TreeItem> modifiedItems = new HashSet<TreeItem>();
	private HashMap<TreeItem, ProgramPointValue> ppvItems = new HashMap<TreeItem, ProgramPointValue>();
	private boolean readOnly;
	private MonitoringConfiguration mc;

	/**
	 * Construct a read only composite. Tree items will not be editable.
	 * @param parent this composite's parent
	 * @param style	SWT Composite style
	 */
	public ProgramPointsTreeComposite(Composite parent, int style) {
		super(parent, style);
		readOnly = true;		
		setLayout();
		createWidgets();
	}
	
	/**
	 * Construct a read/write composite. Tree items will be editable.
	 * @param parent this composite's parent
	 * @param style	SWT Composite style
	 * @param editor editor to be informed when a modify occurs.
	 */
	public ProgramPointsTreeComposite(Composite parent, int style, IOTracesEditor editor) {
		super(parent, style);
		this.editor = editor;
		mc = editor.getMonitoringConfiguration();
		readOnly = false;
		setLayout();
		createWidgets();
	}

	private void setLayout() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		this.setLayout(layout);
	}

	@Override
	public boolean setFocus() {
		return tree.setFocus();
	}

	private void createWidgets() {
		createTree();
		if (!readOnly)
			createButtonsBar();
	}

	private void createTree() {
		tree = new Tree(this, SWT.CHECK | SWT.MULTI | SWT.FULL_SELECTION);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Variable name");

		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Variable type");
		
		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Variable value");

		treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Modified info");

		treeEditor = new TreeEditor(tree);
		treeEditor.horizontalAlignment = SWT.LEFT;
		treeEditor.grabHorizontal = true;
		treeEditor.minimumWidth = 50;

		if(!readOnly) {
			tree.addMouseListener(createMouseListener());
			tree.addSelectionListener(createSelectionListener());
		}
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		tree.setLayoutData(gridData);
	}

	private void createButtonsBar() {
		Button button = new Button(this, SWT.NONE);
		button.setText("Remove selected program points variables");
		button.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		button.addSelectionListener(createRemoveButtonListener());
	}

	private SelectionListener createRemoveButtonListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// Clean up any previous editor control
				Control oldEditor = treeEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				for (TreeItem ppItem : tree.getItems()) {
					for (TreeItem variableItem : ppItem.getItems()) {
						if (variableItem.getChecked()) {
							deletedItems.add(variableItem);
							variableItem.dispose();
							editor.setDirty();
						}
					}
				}
			}
		};
	}

	private MouseListener createMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				TreeItem[] items = tree.getSelection();

				if (items.length != 0) {
					TreeItem selectedItem = items[0];

					for (int i = 0; i < tree.getColumnCount(); i++) {
						if (selectedItem.getBounds(i).contains(e.x, e.y))
							selectedColumn = i;
					}
				}
			}
		};
	}

	private SelectionListener createSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = treeEditor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TreeItem item = (TreeItem) e.item;
				if (item == null)
					return;
				else { // Prevent editing of ProgramPoints
					List<TreeItem> itemsList = Arrays.asList(tree.getItems());
					if (itemsList.contains(item))
						return;
				}

				// The control that will be the editor must be a child of the Tree
				Control control = null;
				if (selectedColumn == 1) {
					Combo combo = new Combo(tree, SWT.READ_ONLY);
					for (Type value : Type.values())
						combo.add(value.toString());
					combo.setText(item.getText(selectedColumn));
					combo.addModifyListener(createModifyListener());
					control = combo;
				} else if (selectedColumn == 3) {
					Combo combo = new Combo(tree, SWT.READ_ONLY);
					for (ModifiedInfo value : ModifiedInfo.values())
						combo.add(value.toString());
					combo.setText(item.getText(selectedColumn));
					combo.addModifyListener(createModifyListener());
					combo.setFocus();
					treeEditor.setEditor(combo, item, selectedColumn);
					control = combo;
				} else {
					Text newEditor = new Text(tree, SWT.NONE);
					newEditor.setText(item.getText(selectedColumn));
					newEditor.addModifyListener(createModifyListener());
					newEditor.selectAll();
					newEditor.setFocus();
					control = newEditor;
				}
				control.setFocus();
				treeEditor.setEditor(control, item, selectedColumn);
			}
		};
	}

	private ModifyListener createModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String text = null;
				editor.setDirty();
				Control control = treeEditor.getEditor();
				if (control instanceof Text) {
					Text textEditor = (Text) control;
					text = textEditor.getText();
				} else if (control instanceof Combo) {
					Combo comboEditor = (Combo) control;
					text = comboEditor.getText();
				}
				TreeItem modifiedItem = treeEditor.getItem();
				modifiedItem.setText(selectedColumn, text);
				modifiedItems.add(modifiedItem);
			}
		};
	}

	public void populateTree(List<? extends ProgramPoint> programPoints) {
		this.programPoints = programPoints;
		
		tree.removeAll();
		
		if ( programPoints == null){
			return;
		}
		
		for (ProgramPoint programPoint : programPoints) {
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setGrayed(true);
			
			
			String ppName = DisplayNamesUtil.getProgramPointToPrint(mc, programPoint.getProgramPointName());
			item.setText(ppName);
			Collection<ProgramPointValue> programPointVariableValues = programPoint.getProgramPointVariableValues();
			for (ProgramPointValue value : programPointVariableValues) {
				TreeItem child = new TreeItem(item, SWT.NONE);
				ppvItems.put(child, value);
				child.setText(new String[] { value.getVariable().getName(), value.getVariable().getType().toString(),
						value.getValue(), value.getModified().toString() });
			}
		}
		for (int i = 0; i < tree.getColumnCount(); i++) {
			tree.getColumn(i).pack();
		}
	}

	public List<? extends ProgramPoint> updateProgramPoints() {
		TreeItem[] programPointsItems = tree.getItems();
		ArrayList<ProgramPoint> result = new ArrayList<ProgramPoint>();

		modifiedItems.removeAll(deletedItems);

		for (int i = 0; i < programPointsItems.length; i++) {
			ProgramPoint programPoint = programPoints.get(i);

			boolean modified = false;
			TreeItem[] programPointValues = programPointsItems[i].getItems();
			for (TreeItem valueItem : programPointValues) {
				if (modifiedItems.contains(valueItem)) {
					modified = true;
					ProgramPointValue ppv = ppvItems.get(valueItem);
					ProgramPointVariable variable = ppv.getVariable();
					variable.setName(valueItem.getText(0));
					variable.setType(Type.valueOf(valueItem.getText(1)));
					ppv.setValue(valueItem.getText(2));
					ppv.setModified(ModifiedInfo.valueOf(valueItem.getText(3)));
				} else if (deletedItems.contains(valueItem)) {
					modified = true;
					ProgramPointValue ppv = ppvItems.get(valueItem);
					programPoint.removeVariableValue(ppv);
				}
			}

			if (modified) {
				result.add(programPoint);
			}
		}

		return result;
	}

	
	/**
	 * Find all items that match <code>regex</code> regular expression, and select them.
	 * @param regex regular expression
	 */
	public void find(String regex) {
		List<TreeItem> selection = new ArrayList<TreeItem>();
		for (TreeItem programPoint : tree.getItems()) {
			// Find all program points matching with input REGEX
			if (programPoint.getText().matches(regex)) {
				selection.add(programPoint);
			}
			
			// Select all program points found
			for (TreeItem variable : programPoint.getItems()) {
				for (int i = 0; i < tree.getColumnCount(); i++) {
					if (variable.getText(i).matches(regex)) {
						programPoint.setExpanded(true);
						selection.add(variable);
						break;
					}
				}
			}
		}
		tree.setSelection(selection.toArray(new TreeItem[0]));
	}

	public void expandAll() {
		int iRow = 0;   
		while (iRow < tree.getItemCount()) {  
		 tree.getItem(iRow).setExpanded(true);  
		 iRow++;  
		}
	}

	public void setMonitoringConfiguration(MonitoringConfiguration mc2) {
		this.mc = mc2;
	}
}
