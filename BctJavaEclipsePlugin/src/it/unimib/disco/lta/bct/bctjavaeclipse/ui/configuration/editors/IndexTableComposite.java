package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class IndexTableComposite extends Composite {

	private static final String ELEMENTS = "Elements";
	private static final String RESOURCE = "Resource";
	private static final String OPEN = "Open";
	private static final String SIZE = "Size";
	private Table table;
	private TableColumn elementsColumn;
	private TableColumn resourceColumn;
	private TableColumn sizeColumn;
	private TableViewer tableViewer;
	//private TableViewer tw;
	
	private String[] columnNames = new String[] { 
			ELEMENTS, 
			RESOURCE,
			SIZE,
			OPEN
			};
	private TableColumn openColumn;
	
	
	public IndexTableComposite(Composite parent, int style) {
		super(parent, style);
		
		//tw = new TableViewer(this,SWT.NONE);
		
		createTable();
		
//		createTableViewer();
	    
	}

//	private void createTableViewer() {
//		tableViewer = new TableViewer(table);
//		tableViewer.setUseHashlookup(true);
//		
//		tableViewer.setColumnProperties(columnNames);
//
//		// Create the cell editors
//		CellEditor[] editors = new CellEditor[columnNames.length];
//
//		// Column 1 : Completed (Checkbox)
//		TextCellEditor textEditor = new TextCellEditor(table);
//		((Text) textEditor.getControl()).setTextLimit(60);
//		editors[0] = new CheckboxCellEditor(table);
//
//		// Column 2 : Description (Free text)
//		textEditor = new TextCellEditor(table);
//		((Text) textEditor.getControl()).setTextLimit(60);
//		editors[1] = textEditor;
//
//		
//		textEditor = new TextCellEditor(table);
//		((Text) textEditor.getControl()).setTextLimit(60);
//		editors[2] = textEditor;
//		
//		textEditor = new TextCellEditor(table);
//		((Text) textEditor.getControl()).setTextLimit(60);
//		editors[3] = textEditor;
//		
//		// Assign the cell editors to the viewer 
//		tableViewer.setCellEditors(editors);
//		// Set the cell modifier for the viewer
//		tableViewer.setCellModifier(new ExampleCellModifier(this));
//		// Set the default sorter for the viewer 
//		tableViewer.setSorter(new ExampleTaskSorter(ExampleTaskSorter.DESCRIPTION));
//	}

	private void createTable() {
		
		table = new Table(this, SWT.BORDER | SWT.MULTI);
//		table.setLayout(new FillLayout());
		
		GridLayout g = new GridLayout();
		g.numColumns = 1;
		table.setLayout(g);
		
	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    
	    //table.setSize(500, 150);
	    table.setBounds(15,15,540,300);
		
	    elementsColumn = new TableColumn(table, SWT.NONE, 0);
	    elementsColumn.setText(ELEMENTS);
	    elementsColumn.setWidth(300);
	    
	    resourceColumn = new TableColumn(table, SWT.NONE, 1);
	    resourceColumn.setText("Resource");
	    resourceColumn.setWidth(100);
	    
	    sizeColumn = new TableColumn(table, SWT.NONE, 2);
	    sizeColumn.setText("Size");
	    sizeColumn.setWidth(50);
	    
	    openColumn = new TableColumn(table, SWT.NONE, 3);
	    openColumn.setText("Open");
	    openColumn.setWidth(25);
		
	}

	public void setElementsColumnTitle(String title){
		elementsColumn.setText(title);
	}
	
	public void setResourceColumnTitle(String title){
		resourceColumn.setText(title);
	}
	
	public void setSizeColumnTitle(String title){
		sizeColumn.setText(title);
	}
	
	public void addElement( String element, String resource, String size, final IFile file ){
		addElement(element, resource, size, file, false);
	}
	
	public void addElement( String element, String resource, String size, final IFile file, boolean highlight ){
		TableItem items = new TableItem(table, SWT.NONE);
	    
	    
		TableEditor editor=new TableEditor(table);
		Text text = new Text(table,SWT.NONE);
		text.setText(element);
		text.setEditable(false);
		
		if ( highlight ){
			text.setBackground(new Color(getDisplay(),255,85,0));
		}
		
		editor.grabHorizontal = true;
		editor.setEditor(text,items,0);
		
		
		editor = new TableEditor(table);
		text = new Text(table,SWT.NONE);
		text.setText(resource);
		text.setEditable(false);
		
		if ( highlight ){
			text.setBackground(new Color(getDisplay(),255,85,0));
		}
		
		editor.grabHorizontal = true;
		editor.setEditor(text,items,1);
		
		editor = new TableEditor(table);
		text = new Text(table,SWT.NONE);
		text.setText(size);
		text.setEditable(false);
		
		if ( highlight ){
			text.setBackground(new Color(getDisplay(),255,85,0));
		}
		
		editor.grabHorizontal = true;
		editor.setEditor(text,items,2);
		
		editor = new TableEditor(table);
		Button openButton = new Button(table, SWT.PUSH);
		openButton.setSize(20, 20);
		openButton.setText("open");
		openButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				//IProgressMonitor monitor = new NullProgressMonitor();
				
				
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						IWorkbenchPage page =
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try {
							IDE.openEditor(page, file, true);
						} catch (PartInitException e) {
						}
					}
				});
				
			}
			
		});
		editor.grabHorizontal = true;
		editor.setEditor(openButton,items,3);
		
		
		
		
//		editor.grabHorizontal = true;
//		editor.setEditor(text,items,0);
	}

	public void clearAll() {
		table.clearAll();
	}
	
	public void setTitles(String element, String resource, String size, String open ){
		elementsColumn.setText(element);
		resourceColumn.setText(resource);
		sizeColumn.setText(size);
		openColumn.setText(open);
	}

	public void highLightElement(int toHighLight) {
		if ( toHighLight >= 0 ){
			table.getItem(toHighLight).setGrayed(true);
			
			table.getItem(toHighLight).setBackground(0, new Color(getDisplay(),255,85,0) );
		}
		
	}
	
}
