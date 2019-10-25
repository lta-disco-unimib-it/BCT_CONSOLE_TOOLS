package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.view;

import java.util.ArrayList;
import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.ViolationElement;

import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class AnomalousSequenceView extends ViewPart implements ISelectionListener {

	class AnomalousSequenceContentProvider
    implements IStructuredContentProvider{
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {

		}

		public void dispose() {
			//taskList.removeChangeListener(this);
			
		}

		// Return the tasks as an array of Objects
		public Object[] getElements(Object parent) {
			if ( parent != null ){
				ViolationElement ve = (ViolationElement) parent;
				BctModelViolation violation = ve.getViolation();
				
				System.out.println(violation);
				if ( violation instanceof BctFSAModelViolation ){
					BctFSAModelViolation fsaV = (BctFSAModelViolation) violation;
					BctAnomalousCallSequence cs = fsaV.getAnomalousCallSequence();
					if ( cs != null ){
						return 	cs.getAnomalousCallSequence();
					}
				}
			}
			return new Object[0];
		}
		//return taskList.getTasks().toArray();
	}

	
	public class AnomalousSequenceLabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if ( element == null ){
				return "";
			}
			
			if ( columnIndex == 0 ){
				return element.toString();
			}
			return "";
		}

		public void addListener(ILabelProviderListener listener) {
			
		}

		public void dispose() {
			
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		
		}


		
	}
	
	private Table table;
	private TableViewer tableViewer;
	private String[] columnNames = new String[]{"Events","Description"};

	public AnomalousSequenceView() {
		
	}

	@Override
	public void createPartControl(Composite parent) {
		createTable(parent);
		createTableViewer();
		tableViewer.setContentProvider(new AnomalousSequenceContentProvider());
		tableViewer.setLabelProvider(new AnomalousSequenceLabelProvider());
		
		getViewSite().getPage().addSelectionListener(this);

		// The input for the table viewer is the instance of ExampleTaskList
		//tableViewer.setInput(taskList);
	}

	private void createTable(Composite parent) {
		table = new Table(parent,SWT.NONE);
		table.setHeaderVisible(true);
		
		TableColumn column;

		column = new TableColumn(table, SWT.LEFT, 0);
		column.setText("Events");
		column.setWidth(450);
		
		// Add listener to column so tasks are sorted by description when clicked 
		column.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				//				tableViewer.setSorter(
				//						new ExampleTaskSorter(ExampleTaskSorter.DESCRIPTION));
			}
		});
		
		
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("Description");
		column.setWidth(50);
		// Add listener to column so tasks are sorted by description when clicked 
		column.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				//				tableViewer.setSorter(
				//						new ExampleTaskSorter(ExampleTaskSorter.DESCRIPTION));
			}
		});
	}
	
	
	private void createTableViewer() {
		tableViewer = new TableViewer(table);    
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(columnNames );
		
		// Create the cell editors
		CellEditor[] editors = new CellEditor[columnNames.length];

		editors[0] = new TextCellEditor(table);
		editors[1] = new TextCellEditor(table); 

		// Assign the cell editors to the viewer 
		tableViewer.setCellEditors(editors);
		
		
		// Set the cell modifier for the viewer
		//	          tableViewer.setCellModifier(new ExampleCellModifier(this));
		// Set the default sorter for the viewer 
		//tableViewer.setSorter(
		//     new ExampleTaskSorter(ExampleTaskSorter.DESCRIPTION));
	}


	@Override
	public void setFocus() {
		
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if ( selection instanceof IStructuredSelection ){
			Object selected = ((IStructuredSelection)selection).getFirstElement();
			System.out.println(selected);
			if ( selected instanceof ViolationElement ){
				Display display = getSite().getShell().getDisplay(); 
				Color red = display.getSystemColor(SWT.COLOR_RED);
				Color white = display.getSystemColor(SWT.COLOR_WHITE);
				
				for ( TableItem item : tableViewer.getTable().getItems() ){
					item.setBackground(white);
				}
				
				
				ViolationElement ve = (ViolationElement) selected;
				tableViewer.setInput(ve);
				BctModelViolation viol = ve.getViolation();
				if ( viol instanceof BctFSAModelViolation ){
					BctFSAModelViolation fsaViol = (BctFSAModelViolation) viol;
					int position = fsaViol.getAnomalousEventPosition();
					
					TableItem item = tableViewer.getTable().getItem(position);
					
					
					System.out.println("POS "+position+" "+item);
					if ( item != null ){
						tableViewer.setSelection(new StructuredSelection(item.getData()));
					}
					
					
					
					
					int end = position + fsaViol.getAnomalousCallSequence().getAnomalousCallSequence().length;
					for ( int i = position; i <  end; i++ ){
						TableItem citem = tableViewer.getTable().getItem(i);

						if ( citem != null ){
							citem.setBackground(red);
						}
					}
//					tableViewer.setSelection(new StructuredSelection(items));
				}
			}
		}
		
	}

	

}
