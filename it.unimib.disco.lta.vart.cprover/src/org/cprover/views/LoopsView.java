package org.cprover.views;

import org.cprover.communication.Claim;
import org.cprover.communication.Loop;
import org.cprover.core.CProverPlugin;
import org.cprover.runners.LoopsRunner;
import org.cprover.ui.Constants;
import org.cprover.ui.Utils;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * Shows the loops found in a file and allows to specify loop bounds.
 * 
 * @author Gérard Basler
 */
public class LoopsView extends ViewPart {
    public static final String ID_LOOPS_VIEW = "org.cprover.views.LoopsView";

	private TableViewer viewer;
	private Combo cbBound;
	private Button btRecheck;
	
	private LoopsRunner loopsRun;

	private LoopsComparator sorter1;
	private LoopsComparator sorter2;
	private LoopsComparator sorter3;
	private LoopsComparator sorter4;
	
	private Object[] elements;
	
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (elements != null)
				return elements;
			if (loopsRun == null)
				elements = new Loop[]{};
			else
				elements = loopsRun.getLoops().toArray();
			return elements;
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			Loop loop = (Loop)obj;
			if (index == 0) 
				return "";
			if (index == 1)
				return loop.id;
			if (index == 2) {
				if (loop.bound == -1)
					return "";
				return String.valueOf(loop.bound);
			}
			if (index == 3) {
				if (loop.location.file.startsWith(loopsRun.root))
					return loop.location.file.substring(loopsRun.root.length()+ 1);
				else
					return loop.location.file;
			}
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			Loop loop = (Loop)obj;
			if (index == 0) {
				if (loop.boundTooSmall)
					return CProverPlugin.getImageCache().get(Constants.ICON_FAILURE);
			}
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return null;
		}
	}

	public LoopsView() {
		this.sorter1 = new LoopsComparator(1, 0);
		this.sorter2 = new LoopsComparator(2, 0);
		this.sorter3 = new LoopsComparator(3, 0);
		this.sorter4 = new LoopsComparator(4, 0);
		this.elements = null;
	}

	class LoopsComparator extends MyViewerComparator {
		private int column;
		private int direction;

		public LoopsComparator(int column, int direction) {
			super(column, direction);
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			Loop l1 = (Loop)e1;
			Loop l2 = (Loop)e2;
			switch (this.column) {
			case 1:
				return new Boolean(l1.boundTooSmall).compareTo(new Boolean(l2.boundTooSmall))* this.direction;
			case 2:
				return l1.id.compareTo(l2.id)* this.direction;
			case 3:
				return new Integer(l1.bound).compareTo(new Integer(l2.bound))* this.direction;
			case 4:
				return l1.location.file.compareTo(l2.location.file)* this.direction;
			default:
				return 0;
			}
		}				
	}
	
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
	    GridData gdata = new GridData(GridData.FILL_HORIZONTAL);
	    parent.setLayoutData(gdata);
		parent.setLayout(layout);
		
	    Label label = new Label(parent, SWT.LEFT);
	    label.setText("Bound:");
	    
	    cbBound = new Combo(parent, SWT.BORDER);
	    cbBound.setItems(new String[]{"", "0", "1", "5", "10", "20", "50", "100"});
	    cbBound.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				Loop loop = getSelectedLoop();
				if (loop == null)
					return;
				String value = cbBound.getText();
				if (value.equals("") || value.equals("0") || value.equals("1") || value.equals("5") || value.equals("10") || value.equals("20") || value.equals("50") || value.equals("100")) {
					if (value.equals(""))
						loop.bound = -1;
					else
						loop.bound = Integer.parseInt(value);
					refresh();
					setFocus();
				}
				else 
					cbBound.setText("");
			}	    	
	    });
	    cbBound.setEnabled(false);
	    
	    btRecheck = new Button(parent, SWT.PUSH);
	    btRecheck.setText("Recheck");
	    btRecheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
//				CProverPlugin.getClaimsView().recheckWithNewBounds();
			}	    	
	    });
	    btRecheck.setEnabled(false);
		
		viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
	    gdata = new GridData(GridData.FILL_BOTH);
	    gdata.horizontalSpan = 3;
	    viewer.getControl().setLayoutData(gdata);
		
		final Table table = viewer.getTable();
		
		final TableColumn col1 = new TableColumn(table, SWT.CENTER);
		col1.setText("");
		col1.setWidth(20);
		col1.setResizable(false);
		col1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter1, table, col1);
			}
		});
		final TableColumn col2 = new TableColumn(table, SWT.CENTER);
		col2.setText("Id");
		col2.setWidth(40);
		col2.setResizable(false);
		col2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter2, table, col2);
			}
		});
		final TableColumn col3 = new TableColumn(table, SWT.CENTER);
		col3.setText("Bound");
		col3.setWidth(60);
		col3.setResizable(false);
		col3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter3, table, col3);
			}
		});
		final TableColumn col4 = new TableColumn(table, SWT.CENTER);
		col4.setText("File");
		col4.setWidth(100);
		col4.setResizable(true);
		col4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter4, table, col4);
			}
		});
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Loop loop = getSelectedLoop();
				if (loop == null) 
					return;
				if (loop.bound == -1)
					cbBound.setText("");
				else
					cbBound.setText(String.valueOf(loop.bound));
				
				
//				new SourceLookupService();
//				DebugUITools.displaySource(result, page);
				
				
//				CProverPlugin.showLocation(loop.location);
				setFocus();				
			}			
		});		
	}
	
	public Loop getSelectedLoop() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		return (Loop)sel.getFirstElement();
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	public static void refreshLoopsRunner(final LoopsRunner loopsRun) {
		Display.getDefault().syncExec(new Runnable() {
	
			public void run() {
				try {
					LoopsView view = (LoopsView) Utils.findOrOpenView(ID_LOOPS_VIEW);
					if( view != null ) {
						view.refresh(loopsRun);
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void refresh(final LoopsRunner loopsRun) {
		this.loopsRun = loopsRun;
		this.elements = null;
		refresh();
	}
	
	public void refresh() {
		if (PlatformUI.getWorkbench().isClosing()) 
			return;
		
		if (loopsRun != null) {
			for (Loop l: loopsRun.getLoops())
				l.boundTooSmall = false;
			
			// this is from Horatius code, no idea why we need this...
			for (Claim cl: loopsRun.getShowClaimsRunner().claims) {
				if (cl.clRun.getTrace() != null ) {
				    
				    if(cl.clRun.getTrace().failure == null || cl.clRun.getTrace().failure.loopId.equals("")) continue;
				    loopsRun.getLoop(cl.clRun.getTrace().failure.loopId).boundTooSmall = true;
				}
			}
		}
		
		this.cbBound.setEnabled(!this.isEmpty());
		this.btRecheck.setEnabled(!this.isEmpty());
		viewer.refresh(true);		
		this.reSort();
	}
	
	public void setLoopsRun(LoopsRunner lRun) {
		this.loopsRun = lRun;
		this.elements = null;
		this.refresh();
	}
	
	public boolean isEmpty() {
		return this.loopsRun == null || this.loopsRun.getLoops().isEmpty();
	}
	
	public void handleSorting(LoopsComparator sorter, Table table, TableColumn col) {
		sorter.nextDirection();
		if (sorter.direction == 0) {
			viewer.setSorter(null);
			table.setSortColumn(null);
			table.setSortDirection(SWT.NONE);
		}
		else {
			viewer.setComparator(null);
			viewer.setComparator(sorter);
			table.setSortColumn(col);
			table.setSortDirection(sorter.getSWTDirection());
		}		
	}
	
	private void reSort() {		
		ViewerComparator sorter = viewer.getComparator();
		if (sorter == null)
			return;
		viewer.setComparator(null);
		viewer.setComparator(sorter);
	}
}
