package org.cprover.views;

import java.util.HashSet;
import java.util.Set;

import org.cprover.communication.Claim;
import org.cprover.core.CProverPlugin;
import org.cprover.runners.ShowClaimsRunner;
import org.cprover.ui.Constants;
import org.cprover.ui.EditorUtil;
import org.cprover.ui.ExternalEditorInput;
import org.cprover.ui.Utils;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;


/**
 * Class: ClaimsView
 * 
 * Purpose: Implement ClaimsView.
 */

public class ClaimsView extends ViewPart {
	public static final String ID_CLAIMS_VIEW = "org.cprover.views.ClaimsView";
	
	public TableViewer viewer;
	private Action checkbyFile_action;
	private Action checkbyProp_action;
	private Action checkall_action;
	private Action checkSel_action;
	private Action stopSel_action;
	private Action stopall_action;
	private Action resetsession_action;
	private Action terminatesession_action;
	private Action stopsession_action;
	
	private Action check_actions;
	private Action stop_actions;
	private Action session_actions;
	
	private ClaimsComparator sorter1;
	private ClaimsComparator sorter2;
	private ClaimsComparator sorter3;
	private ClaimsComparator sorter4;
	private ClaimsComparator sorter5;
	
	ShowClaimsRunner claimsRun;

	private Object[] elements;
	
	public void resetFilter(ViewerFilter filter) {
		viewer.removeFilter(filter);
		viewer.addFilter(filter);		
	}
	
//	public void showClaim(Claim cl) {
//		if (cl == null || !CProverPlugin.claimsFilter.select(this.viewer, null, cl)) {
//			CProverPlugin.hideView("CTraceView");
//			CProverPlugin.hideView("VerilogTraceView");
//			CProverPlugin.hideView("ProblemsView");
//			CProverPlugin.getLogView().setRun(null);
//			CProverPlugin.getConsoleView().setClRun(null);			
//			return;
//		}
//		CProverPlugin.getCTraceView().setClRun(cl.clRun);
//		CProverPlugin.getVerilogTraceView().setClRun(cl.clRun);
//		CProverPlugin.getProblemsView().setRun(cl.clRun);
//		CProverPlugin.getLogView().setRun(cl.clRun);
//		CProverPlugin.getConsoleView().setClRun(cl.clRun);
//		
//		if (cl.clRun.result == Result.ERROR)
//			CProverPlugin.showView("ProblemsView");
//		else if (cl.clRun.result == Result.UNKNOWN || cl.clRun.result == Result.SUCCESS)
//			CProverPlugin.showView("LogView");
//		else {
//			if (!CProverPlugin.getCTraceView().isEmpty())
//				CProverPlugin.showView("CTraceView");
//			if (!CProverPlugin.getVerilogTraceView().isEmpty())
//				CProverPlugin.showView("VerilogTraceView");
//		}
//		CProverPlugin.showView("ClaimsView");
//		
//		if (CProverPlugin.getCTraceView().isEmpty())
//			CProverPlugin.hideView("CTraceView");
//		if (CProverPlugin.getVerilogTraceView().isEmpty())
//			CProverPlugin.hideView("VerilogTraceView");
//		if (CProverPlugin.getProblemsView().isEmpty())
//			CProverPlugin.hideView("ProblemsView");
//	}

	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (elements != null)
				return elements;
			if (claimsRun == null)
				elements = new Claim[]{};
			else
				elements = claimsRun.claims.toArray(); 
			return elements;
		}
	}
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			Claim claim = (Claim)obj;
			if (index == 1) {
				try {
				if (claim.location.file.startsWith(claimsRun.root)) {
				    return claim.location.file.substring(claimsRun.root.length()+ 1);
				}
				return claim.location.file;
				} catch ( Throwable t  ){
					t.printStackTrace();
				}
			}
			if (index == 2)
				return claim.property.toString();
			if (index == 3)
				return claim.description;
			if (index == 4)
				return claim.expression;
			return null;
		}
		
		public Image getColumnImage(Object obj, int index) {
			if (index > 0)
				return null;
			return getImage(obj);
		}
		
		public Image getImage(Object obj) {
			Claim cl = (Claim)obj;
			if (cl.clRun.isRunning())
				return CProverPlugin.getImageCache().get(Constants.ICON_RUNNING);
			if (cl.clRun.waiting)
				return CProverPlugin.getImageCache().get(Constants.ICON_WAITING);
			return cl.clRun.result.getImage();
		}
	}

	public ClaimsView() {
		this.sorter1 = new ClaimsComparator(1, 0);
		this.sorter2 = new ClaimsComparator(2, 0);
		this.sorter3 = new ClaimsComparator(3, 0);
		this.sorter4 = new ClaimsComparator(4, 0);
		this.sorter5 = new ClaimsComparator(5, 0);
		this.elements = null;
	}
	
	class ClaimsComparator extends MyViewerComparator {

		public ClaimsComparator(int column, int direction) {
			super(column, direction);
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			Claim cl1 = (Claim)e1;
			Claim cl2 = (Claim)e2;
			switch (this.column) {
			case 1:
				return cl1.clRun.result.compareTo(cl2.clRun.result)* this.direction;
			case 2:
				return cl1.location.file.compareTo(cl2.location.file)* this.direction;
			case 3:
				return cl1.property.compareTo(cl2.property)* this.direction;
			case 4:
				return cl1.description.compareTo(cl2.description)* this.direction;
			case 5:
				return cl1.expression.compareTo(cl2.expression)* this.direction;
			default:
				return 0;
			}
		}			
	}
	
	public void createPartControl(Composite parent) {
		//viewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer = new TableViewer(parent, SWT.MULTI | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());
		
		final Table table = viewer.getTable();
		
		final TableColumn col1 = new TableColumn(table,SWT.CENTER);
		col1.setText("");
		col1.setWidth(20);
		col1.setResizable(false);
		col1.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter1, table, col1);
			}
		});		
		final TableColumn col2 = new TableColumn(table,SWT.LEFT);
		col2.setText("File");
		col2.setWidth(100);
		col2.setResizable(true);
		col2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter2, table, col2);
			}
		});
		final TableColumn col3 = new TableColumn(table,SWT.LEFT);
		col3.setText("Property");
		col3.setWidth(100);
		col3.setResizable(true);
		col3.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter3, table, col3);
			}
		});
		final TableColumn col4 = new TableColumn(table,SWT.LEFT);
		col4.setText("Description");
		col4.setWidth(150);
		col4.setResizable(true);
		col4.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter4, table, col4);
			}
		});
		final TableColumn col5 = new TableColumn(table,SWT.LEFT);
		col5.setText("Expression");
		col5.setWidth(100);
		col5.setResizable(true);
		col5.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				handleSorting(sorter5, table, col5);
			}
		});
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
//		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(SelectionChangedEvent event) {
//				Claim cl = getSelectedClaim();
//				if (cl == null) {
//					showClaim(null);
//					return;
//				}
//				showClaim(cl);
//				CProverPlugin.showLocation(cl.location);
//				setFocus();				
//			}			
//		});
//		
		this.viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {				
				try {
					getSelectedClaim().clRun.exec();
					
					EditorUtil.openEditor(Utils.getActivePage(), viewer.getSelection());
				} catch (Exception e) {
				    e.printStackTrace();
//					Utils.showErrorDialog(title, message, status);
//					CProverPlugin.showError(e);
				}
			}			
		});
		
		makeActions();
		contributeToActionBars();		
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(check_actions);
		bars.getToolBarManager().add(new Separator());
		bars.getToolBarManager().add(stop_actions);
		bars.getToolBarManager().add(new Separator());
		bars.getToolBarManager().add(session_actions);
	}

	private void makeActions() {
		checkSel_action = new Action() {
			public void run() {
				if (!checkSelection())
					CProverPlugin.reset();
			}
		};
		checkSel_action.setText("Selection");
		checkSel_action.setToolTipText("Check the selected claims");
		
		checkbyFile_action = new Action() {
			public void run() {
				if (!checkByFile())
					CProverPlugin.reset();
			}
		};
		checkbyFile_action.setText("by File");
		checkbyFile_action.setToolTipText("Check all the claims from the selected file");
		
		checkbyProp_action = new Action() {
			public void run() {
				if (!checkByProperty())
					CProverPlugin.reset();
			}
		};
		checkbyProp_action.setText("by Property");
		checkbyProp_action.setToolTipText("Check all the claims with the selected property");
		
		checkall_action = new Action() {
			public void run() {
				if (!checkAll())
					CProverPlugin.reset();
			}
		};
		checkall_action.setText("All");
		checkall_action.setToolTipText("Check all the claims");
		
		stopSel_action = new Action() {
			public void run() {
				abortSelection();
			}
		};
		stopSel_action.setText("Selection");
		stopSel_action.setToolTipText("Stop checking the selected claims");
		
		stopall_action = new Action() {
			public void run() {
				abortAll();
			}
		};
		stopall_action.setText("All");
		stopall_action.setToolTipText("Stop all the running claim checking threads");
		
		stopsession_action = new Action() {
			public void run() {
//				CProverPlugin.stop();
			}
		};
		stopsession_action.setText("Stop");
		stopsession_action.setToolTipText("Stop the current session");
		
		terminatesession_action = new Action() {
			public void run() {
				terminate();
			}
		};
		terminatesession_action.setText("Terminate");
		terminatesession_action.setToolTipText("Terminate the current session");
		
		resetsession_action = new Action() {
			public void run() {
//				CProverPlugin.reset();				
			}
		};
		resetsession_action.setText("Reset");
		resetsession_action.setToolTipText("Reset the current session");
		
		checkbyFile_action.setEnabled(false);
		checkbyProp_action.setEnabled(false);
		checkall_action.setEnabled(false);
		checkSel_action.setEnabled(false);
		stopSel_action.setEnabled(false);
		stopall_action.setEnabled(false);
		stopsession_action.setEnabled(false);
		terminatesession_action.setEnabled(false);
		resetsession_action.setEnabled(false);
		
		this.check_actions = new ActionsGroup("Check", new Action[]{checkall_action, checkSel_action, checkbyFile_action, checkbyProp_action});
		this.stop_actions = new ActionsGroup("Stop", new Action[]{stopall_action, stopSel_action});
		this.session_actions = new ActionsGroup("Session", new Action[]{stopsession_action, terminatesession_action, resetsession_action}); 
	}
	
	public boolean checkSelection() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		if (sel.isEmpty())
			return true;
		for (Object obj:sel.toList()) {
			Claim cl = (Claim)obj;
			try {
				cl.clRun.exec();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				//CProverPlugin.showError(e);
			}					
		}
		return true;
		/*
		this.refresh();
		this.viewer.setSelection(new StructuredSelection(sel.getFirstElement()));
		return true;*/
	}
	
	public boolean checkAll() {
		for (Claim cl: claimsRun.claims) {
			try {
				cl.clRun.exec();
				//Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				//CProverPlugin.showError(e);
			}					
		}
		return true;
		/*
		this.refresh();
		this.viewer.setSelection(new StructuredSelection(sel.getFirstElement()));
		return true;*/
	}
	
	public boolean checkByFile() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		if (sel.isEmpty())
			return true;
		Set<String> selFiles = new HashSet<String>();
		for (Object o: sel.toArray())
			selFiles.add(((Claim)o).location.file);
		
		for (Claim cl: claimsRun.claims) {
			if (!selFiles.contains(cl.location.file))
				continue;
			
			try {
				cl.clRun.exec();
				//Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				//CProverPlugin.showError(e);
			}					
		}
		return true;
		/*
		this.refresh();
		this.viewer.setSelection(new StructuredSelection(sel.getFirstElement()));
		return true;*/
	}
	
	public boolean checkByProperty() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		if (sel.isEmpty())
			return true;
		Set<String> selProps = new HashSet<String>();
		for (Object o: sel.toArray())
			selProps.add(((Claim)o).property);
		for (Claim cl: claimsRun.claims) {
			if (!selProps.contains(cl.property))
				continue;
			try {
				cl.clRun.exec();
				//Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
				//CProverPlugin.showError(e);
			}					
		}
		return true;
		/*
		this.refresh();
		this.viewer.setSelection(new StructuredSelection(sel.getFirstElement()));
		return true;
		*/
	}
	
	public boolean recheckWithNewBounds() {
//		Claim first = null;
//		for (Claim cl: claimsRun.claims) {
//			if (cl.clRun.trace.failure == null || cl.clRun.trace.failure.loopId.equals(""))
//				continue;
//			if (first == null)
//				first = cl;
//			try {
//				if (!cl.clRun.exec())
//					return false;
//			} catch (Exception e) {
//				CProverPlugin.log(e);
//			}					
//		}
//		if (first != null) {
//			this.refresh();
//			this.viewer.setSelection(new StructuredSelection(first));
//		}
		return true;		
	}
	
	public void abortSelection() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		if (sel.isEmpty())
			return;
		for (Object obj:sel.toList()) {
			Claim cl = (Claim)obj;
			if (cl.clRun != null) 
				cl.clRun.resume();
		}
		for (Object obj:sel.toList()) {
			Claim cl = (Claim)obj;
			if (cl.clRun != null) 
				cl.clRun.stop();
		}
		this.refresh();
		this.viewer.setSelection(new StructuredSelection(sel.getFirstElement()));
	}
	
	public void abortAll() {
		if (claimsRun == null)
			return;
		for (Claim cl: claimsRun.claims) {
			if (cl.clRun != null)
				cl.clRun.resume();
		}
		for (Claim cl: claimsRun.claims) {
			if (cl.clRun != null)
				cl.clRun.stop();
		}
		if (PlatformUI.getWorkbench().isClosing())
			return;
		this.refresh();
		this.viewer.setSelection(null);
	}
	
	public void terminate() {
//		CProverPlugin.stop();
//		CProverPlugin.crtTaskFile = null;
//		CProverPlugin.crtTask = null;
		terminatesession_action.setEnabled(false);
		resetsession_action.setEnabled(false);
	}
	
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public static void refreshClaimsRun(final ShowClaimsRunner claimsRun) {
		Display.getDefault().syncExec(new Runnable() {
	
			public void run() {
				try {
					ClaimsView view = (ClaimsView) Utils.findOrOpenView(ID_CLAIMS_VIEW);
					if( view != null ) {
						view.refresh(claimsRun);
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void removeClaimsRun(final ShowClaimsRunner claimsRun) {
	    Display.getDefault().syncExec(new Runnable() {
	        
	        public void run() {
	            ClaimsView view = (ClaimsView) Utils.findView(ID_CLAIMS_VIEW);
	            if( view != null && view.claimsRun == claimsRun ) {
	                view.refresh((ShowClaimsRunner)null);
	            }
	        }
	    });
	}

	protected void refresh(ShowClaimsRunner claimsRun) {
		if( this.claimsRun != null ) {
			for (Claim claim : this.claimsRun.claims) {
				viewer.remove(claim);
			}
		}
		
		this.claimsRun = claimsRun;
		elements = null;
		
		if (claimsRun != null) 
			this.setPartName("Claims - "+ claimsRun.getConfig().verifier.getName().toUpperCase());
		else
			this.setPartName("Claims");
//		terminatesession_action.setEnabled(CProverPlugin.crtTaskFile != null);
		stopsession_action.setEnabled(this.isActive());
//		resetsession_action.setEnabled(CProverPlugin.crtTaskFile != null);
		checkbyFile_action.setEnabled(claimsRun != null && !claimsRun.claims.isEmpty());
		checkbyProp_action.setEnabled(claimsRun != null && !claimsRun.claims.isEmpty());
		checkall_action.setEnabled(claimsRun != null && !claimsRun.claims.isEmpty());
		checkSel_action.setEnabled(claimsRun != null && !claimsRun.claims.isEmpty());
		stopSel_action.setEnabled(claimsRun != null && !claimsRun.claims.isEmpty());
		stopall_action.setEnabled(claimsRun != null && !claimsRun.claims.isEmpty());
		
//		resetFilter(CProverPlugin.claimsFilter);
//		resetFilter(CProverPlugin.claimsFileFilter);
//		this.refresh();
		
		if (claimsRun != null) {
		    for (Claim claim : claimsRun.claims) {
		        viewer.add(claim);
//			viewer.update(claim, null);
//			if (viewer.getComparator() == this.sorter1)
//				this.reSort();
		    }
		}
	}
	
	   public static void refreshClaim(final Claim claim) {
        Display.getDefault().syncExec(new Runnable() {

            public void run() {
				try {
					ClaimsView view = (ClaimsView) Utils.findOrOpenView(ID_CLAIMS_VIEW);
					if( view != null ) {
						view.refresh(claim);
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
            }
        });
    }
	    
    protected void refresh(Claim claim) {
	      if (PlatformUI.getWorkbench().isClosing()) 
	          return;
	      
	      viewer.update(claim, null);
    }

//        public void refreshClaim(Claim cl) {
//	      if (PlatformUI.getWorkbench().isClosing()) 
//	          return;
//	      viewer.update(cl, null);
//	      if (viewer.getComparator() == this.sorter1)
//	          this.reSort();
//	      
//	      CProverPlugin.getClaimsFilterView().refresh();
//	      if (this.claimsRun != null && this.claimsRun.loopsRun != null) { 
//	          CProverPlugin.getLoopsView().setLoopsRun(this.claimsRun.loopsRun);
//	          if (CProverPlugin.getLoopsView().isEmpty()) 
//	              CProverPlugin.hideView("LoopsView");
//	      }
//	      this.markClaims(cl.location.file);
//	    }
	
//	public void markClaims() {
//		if (this.claimsRun == null)
//			return;
//		if (this.claimsRun.result == Result.ERROR) {
//			for (Message msg: this.claimsRun.messages)
//				if (msg.type.equals("ERROR") && msg.location != null) {
//					CodeEditor ed = CProverPlugin.getEditor(msg.location.file);
//					if (ed != null)
//						ed.markLocation(msg.location, Result.ERROR);
//				}					
//		}
//		else {
//			HashSet<CodeEditor> editors = new HashSet<CodeEditor>();
//			for (Claim cl: this.claimsRun.claims) {
//				CodeEditor ed = CProverPlugin.getEditor(cl.location.file);
//				if (ed != null) {
//					ed.locClaims.clear();
//					editors.add(ed);
//				}
//			}			
//			Vector<Location> locFails = new Vector<Location>(); 
//			for (Claim cl: this.claimsRun.claims) {
//				CodeEditor ed = CProverPlugin.getEditor(cl.location.file);
//				if (ed != null) 
//					ed.addClaim(cl);			
//				if (cl.clRun.trace.failure != null)
//					locFails.add(cl.clRun.trace.failure.location);
//			}		
//			for (CodeEditor ed: editors)
//				ed.markClaims();
//			editors.clear();
//			
//			for (Location loc: locFails) {
//				CodeEditor ed = CProverPlugin.getEditor(loc.file);
//				if (ed != null)
//					ed.markLocation(loc, Result.FAILURE);
//			}
//			locFails.clear();
//		}
//	}
//	
//	public void markClaims(String file) {
//		if (this.claimsRun == null)
//			return;
//		if (this.claimsRun.result == Result.ERROR) {
//			for (Message msg: this.claimsRun.messages)
//				if (msg.type.equals("ERROR") && msg.location != null && msg.location.file.equals(file)) {
//					CodeEditor ed = CProverPlugin.getEditor(msg.location.file);
//					ed.markLocation(msg.location, Result.ERROR);
//				}					
//		}
//		else {
//			CodeEditor editor = CProverPlugin.getEditor(file);
//			Vector<Location> locFails = new Vector<Location>(); 
//			editor.locClaims.clear();
//			for (Claim cl: this.claimsRun.claims) {
//				if (cl.location.file.equals(file))
//					editor.addClaim(cl);
//				if (cl.clRun.trace.failure != null)
//					locFails.add(cl.clRun.trace.failure.location);				
//			}		
//			editor.markClaims();
//			
//			for (Location loc: locFails) {
//				if (loc.file.equals(file))
//					editor.markLocation(loc, Result.FAILURE);
//			}
//			locFails.clear();
//		}
//	}
	
	public void refresh() {
//		if (PlatformUI.getWorkbench().isClosing()) 
//			return;
//		viewer.refresh(true);
//		this.reSort();
//		
//		CProverPlugin.getClaimsFilterView().refresh();
//		if (this.claimsRun != null && this.claimsRun.loopsRun != null ) { 
//			CProverPlugin.getLoopsView().setLoopsRun(this.claimsRun.loopsRun);
//			if (CProverPlugin.getLoopsView().isEmpty()) 
//				CProverPlugin.hideView("LoopsView");
//		}
//		this.markClaims();
	}
	
	public Claim getSelectedClaim() {
		IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
		return (Claim)sel.getFirstElement();
	}
		
	public boolean isActive() {
		return this.claimsRun != null;
	}	
	
	public boolean isEmpty() {
		return this.claimsRun == null || this.claimsRun.claims.isEmpty();
	}
	
	public void handleSorting(ClaimsComparator sorter, Table table, TableColumn col) {
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

