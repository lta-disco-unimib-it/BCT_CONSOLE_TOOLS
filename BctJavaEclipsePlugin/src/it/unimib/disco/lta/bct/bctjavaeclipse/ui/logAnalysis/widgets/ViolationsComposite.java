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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AdvancedViolationsUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ExtensionsLocator;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.application.ActionBarAdvisor;

public class ViolationsComposite extends Composite {

	
	
	
	private TreeViewer treeViewer;
	private TableViewer tableViewer;
	private Tree violationsTree;
	private Table violationsTable;
	private MonitoringConfiguration mc;
	private SashForm sash;
	private ViolationTreeLabelAndContentProvider labelProvider;
	private SelectionProviderIntermediate selectionProvider;
	private boolean treeView;
	private ArrayList<ViolationElement> violationElements;
	private boolean mPressed;
	private boolean sPressed;
	private MenuManager popupMenu;
	private boolean demangle;
	private ViolationsTableLabelAndContentProvider tableLabelProvider;

	public boolean isDemangle() {
		return demangle;
	}



	public void setDemangle(boolean demangle) {
		this.demangle = demangle;
	}

	public ViolationsComposite(Composite parent, int style) {
		this(parent, style, false);
	}


	public ViolationsComposite(Composite parent, int style, boolean demangle) {
		super(parent, style);
		
		this.demangle = demangle;

		tableLabelProvider = new ViolationsTableLabelAndContentProvider(demangle);
		selectionProvider = new SelectionProviderIntermediate();
		labelProvider = new ViolationTreeLabelAndContentProvider();
		
		createPopupMenuManager();
		
		sash = new SashForm(this, SWT.VERTICAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		createTreeViewer(sash);
		createTableViewer(sash);
		
		sash.setMaximizedControl(violationsTable);
		treeView = false;
		selectionProvider.setSelectionProviderDelegate(tableViewer);
			
		createButtonsBar(this);
		setLayout();
	}

	

	private void createPopupMenuManager() {
		popupMenu = new MenuManager();
	    

	    
//	    popupMenu.add(new OpenTraceViolationsCompositeAction());
	    
	    
//	    popupMenu.add(new Action(){
//
//			@Override
//			public String getText() {
//				return "Find faulty change";
//			}
//
//			@Override
//			public String getToolTipText() {
//				return "Find faulty chage";
//			}
//
//			@Override
//			public String getDescription() {
//				return "Find faulty chage";
//			}
//
//			@Override
//			public void run() {
//				handleActionMenu(MenuActions.FindFaultyChange);
//			}
//	    	
//	    });
	    
	    for ( ViolationsCompositeAction violationsCompositeActions : ExtensionsLocator.getViolationCompositeActions() ){
	    	popupMenu.add(new ViolationsCompositeWrappingAction(this,violationsCompositeActions));
	    }
	}

	private void setLayout() {
		GridLayout layout = new GridLayout();
		layout.numColumns =  1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		this.setLayout(layout);
	}

	private void createButtonsBar(Composite parent) {
		final Button switchViewButton = new Button(parent, SWT.CHECK);
		switchViewButton.setText("Group by method");
		switchViewButton.setSelection(treeView);
		
		switchViewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (treeView) { // we have to switch to table view
					sash.setMaximizedControl(violationsTable);
					selectionProvider.setSelectionProviderDelegate(tableViewer);
				} else {
					sash.setMaximizedControl(violationsTree);
					selectionProvider.setSelectionProviderDelegate(treeViewer);
				}
				treeView = !treeView;
			}
		});
	}

	private void createTableViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.FULL_SELECTION);
		
		tableViewer.setLabelProvider(tableLabelProvider);
		tableViewer.setContentProvider(tableLabelProvider);
		tableViewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				System.out.println("SORTER "+e1+" "+e2);
				if (e1 instanceof ViolationElement && e2 instanceof ViolationElement) {
					ViolationElement ve1 = (ViolationElement) e1;
					ViolationElement ve2 = (ViolationElement) e2;
					
					if ( ve1.getViolation().getCreationTime() == ve2.getViolation().getCreationTime() ){
						return ve1.getViolation().getId().compareTo(ve2.getViolation().getId());	
					}
					if ( ve1.getViolation().getCreationTime() > ve2.getViolation().getCreationTime() ){
						return 1;
					}
					return -1;
//					int id1 = Integer.parseInt(ve1.getViolation().getId());
//					int id2 = Integer.parseInt(ve2.getViolation().getId());
//					
//					if (id1 < id2) {
//						return -1;
//					} else if (id1 > id2) {
//						return +1;
//					} else if (id1 == id2) {
//						return 0;
//					}
				}
				return super.compare(viewer, e1, e2);
			}
		});
		
		
		
		
		
		violationsTable = tableViewer.getTable();
		

	    
	    Menu menu = popupMenu.createContextMenu(violationsTable);
	    violationsTable.setMenu(menu);
		
		
//		violationsTable.setToolTipText("Double click to open the violated model");
		violationsTable.setToolTipText("Double click to open the source with the violation");
		violationsTable.addMouseListener(new MouseListener());
		violationsTable.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("REL "+e.keyCode);
				switch ( e.keyCode ){
				case 262144 : mPressed=false;
				break;
				case 131072 : sPressed=false;
				break;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("PRESS "+e.keyCode);
				switch ( e.keyCode ){
				case 262144 : mPressed=true;
				break;
				case 131072 : sPressed=true;
				break;
				}
				
			}
		});
	}
	
	
	private enum MenuActions { OpenModel, OpenTrace, FindFaultyChange, OpenSourceFile };
	
	public void handleActionMenu(MenuActions menuAction){
		ViolationElement ve = getViolationElement();
		BctModelViolation violation = ve.getViolation(); 

		
		if(violation == null) {
			return;
		}

		String modelName = violation.getViolatedModel();

		
		String methodName = AdvancedViolationsUtil.getMethodName(violation);

		Types callPointType = MethodCallPoint.Types.ENTER;
		Method method = new Method(methodName);
		URI uri = null;

		if ( menuAction == MenuActions.OpenSourceFile ){
			OpenSourceFileViolationsCompositeAction action = new OpenSourceFileViolationsCompositeAction();
			action.run(violation, mc);
		} else if ( menuAction == MenuActions.OpenModel ){
			if(violation instanceof BctFSAModelViolation) {
				FSAModel model = new FSAModel(null, method, null);
				uri = URIBuilderFactory.getBuilder(mc).buildURI(model);
			} else if(violation instanceof BctIOModelViolation) {
				IoModel model = new IoModel(method, null, null);
				uri = URIBuilderFactory.getBuilder(mc).buildURI(model);	
				if ( ! ( modelName.endsWith("ENTER") || modelName.endsWith("POINT") ) ){
					callPointType = Types.EXIT;
				}
			}
		} else if ( menuAction == MenuActions.OpenTrace ) {


			if(violation instanceof BctIOModelViolation) {
				if ( ! ( modelName.endsWith("ENTER") || modelName.endsWith("POINT") ) ){
					callPointType = Types.EXIT;
				}
			}

			uri = URIBuilderFactory.getBuilder(mc).buildRawTraceURI(violation.getPid(), violation.getThreadId(), violation.getCallId(), callPointType );
		
		} else if ( menuAction == MenuActions.FindFaultyChange ) {
			
		} else {
			return;
		}

		EditorOpener.openEditor(violationsTree.getShell().getDisplay(), uri);

	}
	
	public ViolationElement getViolationElement() {
		if (treeView) {
			ITreeSelection selection = (ITreeSelection) treeViewer.getSelection();
			return (ViolationElement) selection.getFirstElement();
		} else {
			IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
			return (ViolationElement) selection.getFirstElement();
		}
	}
	
	private class MouseListener extends MouseAdapter {
		
	

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			handleActionMenu(MenuActions.OpenSourceFile);
		}

		
	}
	
	private void createTreeViewer(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.SINGLE);
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setContentProvider(new ViolationTreeLabelAndContentProvider());
		
		
		violationsTree = treeViewer.getTree();
		violationsTree.setToolTipText("Double click to open the violated model");
		violationsTree.addMouseListener(new MouseListener());
		
		Menu menu = popupMenu.createContextMenu(violationsTree);
		violationsTree.setMenu(menu);
		
//Menu violationsMenu = new Menu(getShell());
//		
//		MenuItem b1 = new MenuItem(violationsMenu,SWT.PUSH);
//		b1.setText("Open violated model");
//		
//		MenuItem b2 = new MenuItem(violationsMenu,SWT.PUSH);
//		b2.setText("Open trace");
//		
//		violationsTree.setMenu(violationsMenu);
		
		
	}
	
	public void setItems(Collection<BctModelViolation> violations) {
		
		//PLANE violationElements = new ArrayList<ViolationElement>(violations.size());
		if ( violationElements == null ){
			violationElements = new ArrayList<ViolationElement>();
			treeViewer.setInput(violationElements);
			tableViewer.setInput(violationElements);
		}
		
		violationElements.clear();
		Hashtable<String, ViolationElement> parents = new Hashtable<String, ViolationElement>();

		for (BctModelViolation violation : violations) {
			System.out.println(violation);
			ViolationElement ve = new ViolationElement(violation, null, mc);
			violationElements.add(ve);
			String txt = ve.getTxt();
			System.out.println(txt);
			if (parents.get(txt) == null) {
				String pName = DisplayNamesUtil.getProgramPointToPrint(mc, txt);
//				pName = DisplayNamesUtil.removeNamespacesFromSignature(pName);
				ViolationElement newParent = new ViolationElement(null, pName, mc );
				parents.put(txt, newParent);
				violationElements.add(newParent);
			}
		}
		
		
//PLANE		treeViewer.setInput(violationElements);
//PLANE		tableViewer.setInput(violationElements);
		treeViewer.refresh();
		tableViewer.refresh();
	}

	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		violationsTree.setBounds(0, 0, width - 5, height - 5);
	}

	public void setSelectionProvider(IEditorSite site) {
		site.setSelectionProvider(selectionProvider);
	}

	public void setMonitoringConfiguration(MonitoringConfiguration associatedMonitoringConfiguration) {
		mc = associatedMonitoringConfiguration;
		tableLabelProvider.setMonitoringConfiguration( mc );
	}

	public void applyFilter(final FilterCommand filterCommand) {
		ArrayList<ViolationElement> elements = new ArrayList<ViolationElement>();
		
		for ( ViolationElement v : violationElements ){
			if ( v.getViolation() != null && filterCommand.match(v.getViolation())){
				elements.add(v);
			}
		}
		
		treeViewer.setInput(elements);
		tableViewer.setInput(elements);
		
//		ViewerFilter filter = new ViewerFilter() {
//			
//			@Override
//			public boolean select(Viewer viewer, Object parentElement, Object element) {
//				
//				if ( ! ( element instanceof ViolationElement) ){
//					return true;
//				}
//				ViolationElement v = (ViolationElement) element;
//				
//				if ( v.getViolation() == null ){
//					return false;
//				}
//				
//				if ( filterCommand.match ( v.getViolation() ) ){
//					return true;
//				}
//				return false;
//			}
//		};
//		
//		treeViewer.setFilters(new ViewerFilter[]{filter});
	}
	
	public void removeFilter(){
		//treeViewer.setFilters(new ViewerFilter[]{});
		treeViewer.setInput(violationElements);
		tableViewer.setInput(violationElements);
	}
	
	public int getNumberOfShownElements(){
		return tableViewer.getTable().getItemCount();
	}

	public MonitoringConfiguration getMonitoringConfiguration() {
		// TODO Auto-generated method stub
		return mc;
	}
}
