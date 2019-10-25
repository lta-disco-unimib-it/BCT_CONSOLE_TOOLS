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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.URIUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.IFindActionEditorSupport;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.TableItemsFindJob;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.BuildCallHierarchyJob;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.FindShell;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.MethodCallItem;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.util.BctSelectionProvider;

import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;

public class InteractionRawTracesEditor extends EditorPart implements IFindActionEditorSupport, ISelectionProvider {

	private URI uri;
	private InteractionRawTrace rawTrace;
	private Table table;
	private Tree tree;
	private List<MethodCallPoint> methodCallPoints;
	private SashForm sash;
	private RootsContainer roots = new RootsContainer();
	private Button switchViewButton, buildTreeViewButton;
	private Job buildMethodCallHierarchy, findTableItems;
	boolean tableView;
	
	private BctSelectionProvider selectionProvider = new BctSelectionProvider(this) {
		
		@Override
		public ISelection getSelection() {
			if ( tree == null ){
				return new StructuredSelection();
			}
			return new StructuredSelection(tree.getSelection());
		}
		
	};
	private int callIdToSelect = -1;
	private Types callPointTypeToSelect;
	private MonitoringConfiguration mc;
	private TableColumn tableColumn2;

	@Override
	public void dispose() {
		// Cancel jobs if they're running.
		cancelJob(buildMethodCallHierarchy);
		cancelJob(findTableItems);
		super.dispose();
	}
	
	private void cancelJob(Job job) {
		if (job != null && job.getState() == Job.RUNNING) {
			job.cancel();
		}
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// We do not provide a save functionality
	}

	@Override
	public void doSaveAs() {
		// We do not provide a save functionality
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.setSite(site);
		super.setInput(input);
		
		if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput fsInput = (FileStoreEditorInput) input;
			uri = fsInput.getURI();
			super.setPartName("Interaction Raw Traces " + fsInput.getName());
			loadInteractionRawTrace();
		} else {
			throw new PartInitException(getClass().getSimpleName() + " input must be a FileStoreEditorInput instance!");
		}
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		getSite().setSelectionProvider(this);  
		sash = new SashForm(parent, SWT.VERTICAL);
		createTable(sash);
		sash.setMaximizedControl(table);
		tableView = true;
		
		GridData gridData = new GridData(SWT.FILL , SWT.FILL, true, true, 2, 1);
		sash.setLayoutData(gridData);
		
		createButtonsBar(parent);
		setLayout(parent);
	}

	@Override
	public void setFocus() {
		if (tableView) {
			table.setFocus();
		} else {
			tree.setFocus();
		}
	}

	private void createButtonsBar(Composite parent) {
		buildTreeViewButton = new Button(parent, SWT.NONE);
		buildTreeViewButton.setText("Build tree view");
		buildTreeViewButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
		buildTreeViewButton.addSelectionListener(new BuildTreeViewButtonListener(buildTreeViewButton, switchViewButton));
		
		switchViewButton = new Button(parent, SWT.NONE);
		switchViewButton.setText("Switch to tree view");
		switchViewButton.addSelectionListener(new SwitchViewButtonListener(switchViewButton));
		switchViewButton.setEnabled(false);
		switchViewButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
	}

	private void createTable(Composite parent) {
		table = new Table(parent, SWT.VIRTUAL | SWT.MULTI | SWT.FULL_SELECTION  );
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn tableColumn1 = new TableColumn(table, SWT.NONE);
		tableColumn1.setText("Enter/Exit");
		tableColumn1.setWidth(70);
		tableColumn2 = new TableColumn(table, SWT.NONE);
		tableColumn2.setText("Method signature");
		tableColumn2.setWidth(400);
		
		table.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				
				
				
				//TODO: fill here code to show call data
				System.out.println(arg0);
				if ( arg0.item == null ){
					return;
				}
				Object data = arg0.item.getData();
				
				
				if ( data instanceof MethodCallPoint ) {
					
					MethodCallPoint methodCall = (MethodCallPoint) data;
					
					
					selectionProvider.setSelection(new StructuredSelection(methodCall));
					
				}
			}
		});
		
		
		table.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				//MethodCallPoint methodCallPoint = methodCallPoints.listIterator(event.index).next();
				
				//TODO: this try is not so good but helps in the case we are trying to fetch more elements than necessary
				try{
					MethodCallPoint methodCallPoint = methodCallPoints.get(event.index);
//					MethodCallPoint methodCallPoint = methodCallPoints.listIterator(event.index).next();
					
					String signature = methodCallPoint.getMethod().getSignature();
					try {
						signature = DisplayNamesUtil.getSignatureToPrint(mc, signature );
					} catch ( Throwable t ){
						t.printStackTrace();
					}

					item.setText(1, signature);
					if (methodCallPoint.isEnter()) {
						item.setText(0, "enter");
					} else if (methodCallPoint.isExit()) {
						item.setText(0, "exit");
					} else if (methodCallPoint.isGeneric()) {
						item.setText(0, "point");	
					}
					item.setData(methodCallPoint);
				}catch (Exception e){
					//Logger.getInstance().log(e);
				}
			}
		});
		
		//The new lazy implementation permits to calculate size without loading everything		
		table.setItemCount(methodCallPoints.size());
		table.setData(methodCallPoints);
		
		selectCallAccordingToURI();
	}
	
	private void createTree(Composite parent) {
		tree = new Tree(parent, SWT.VIRTUAL | SWT.SINGLE | SWT.FULL_SELECTION );
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		
		final TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Method signature");
		treeColumn.setWidth(1400);

		tree.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				
				
				
				//TODO: fill here code to show call data
				System.out.println(arg0);
				if ( arg0.item == null ){
					return;
				}
				Object data = arg0.item.getData();
				if ( data instanceof MethodCallItem ) {
					MethodCallItem methodCallItem = (MethodCallItem) data;
					MethodCallPoint methodCall = methodCallItem.getMethodCall();
					
					
					selectionProvider.setSelection(new StructuredSelection(methodCallItem));
					
				}
			}
		});
		
		
		
		tree.addListener(SWT.SetData, new Listener() {

			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;
				TreeItem parentItem = item.getParentItem();
				int index = event.index;
				
				if (parentItem == null) {
					// root-level item
					MethodCallItem methodCallItem = roots.getElements().get(index);
					
					String signature = DisplayNamesUtil.getSignatureToPrint(mc, methodCallItem.getMethodCall().getMethod().getSignature() );
					
					
					item.setText(signature);
					
					item.setData(methodCallItem);
					
					
					List<MethodCallItem> children = methodCallItem.getChildren();
					//GoFFI
//					item.setData(children.toArray(new MethodCallItem[0]));
					item.setItemCount(children.size());
					
				} else {
					//GOFFI
//					MethodCallItem[] children = (MethodCallItem[]) parentItem.getData();
//					MethodCallItem methodCallItem = children[index];
					
					MethodCallItem methodCallItemP = (MethodCallItem) parentItem.getData();
					List<MethodCallItem> children = methodCallItemP.getChildren();
					
					MethodCallItem methodCallItem = children.get(index);
					
					String signature = methodCallItem.getMethodCall().getMethod().getSignature();
					try {
						signature = DisplayNamesUtil.getSignatureToPrint(mc, signature );
					} catch ( Throwable t ){
						t.printStackTrace();
					}
					item.setText(signature);
					
					// set item children
					List<MethodCallItem> children2 = methodCallItem.getChildren();
					item.setData(methodCallItem);
					
					if(children2.size() > 0) {
						
						//GOFFI
//						item.setData(children2.toArray(new MethodCallItem[0]));
						item.setItemCount(children2.size());
					}
				}
			}
		});
	}

	private void setLayout(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns =  2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		parent.setLayout(layout);
	}

	private class BuildTreeViewButtonListener extends SelectionAdapter {
		Button buildButton, switchButton;
		
		public BuildTreeViewButtonListener(Button buildTreeViewButton, Button switchViewButton) {
			this.buildButton = buildTreeViewButton;
			this.switchButton = switchViewButton;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			roots.getElements();
		}
	}
	
	private class SwitchViewButtonListener extends SelectionAdapter {
		private Button button;

		public SwitchViewButtonListener(Button button) {
			this.button = button;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			
			boolean currentViewIsTable = tableView;
			tableView = !tableView;	
			
			if (currentViewIsTable) { // we have to switch to tree view mode
				button.setText("Switch to table view");
				sash.setMaximizedControl(tree);
//				
				TableItem[] selectedElements = table.getSelection();
				if ( selectedElements == null || selectedElements.length == 0 ){
					return;
				}
				
				MethodCallPoint selectedCallPoint = (MethodCallPoint) selectedElements[0].getData();
				
				try {
				for ( TreeItem item : tree.getItems() ) {
					item.setExpanded(true);
					if ( selectInTree( selectedCallPoint, item ) ){
						item.setExpanded(true);
						break;
					}
				}
				} catch ( Throwable t ){
					System.out.println("Throwable caught");
					t.printStackTrace();
				}
				
				
			} else {
				button.setText("Switch to tree view");
				sash.setMaximizedControl(table);
			}
			
			setFocus();
		}

		private boolean selectInTree(MethodCallPoint selectedCallPoint, TreeItem root) {
			
			
			for ( TreeItem item : root.getItems() ){
				
				
				
				MethodCallItem methodCallItem = (MethodCallItem) item.getData();
				
				if ( methodCallItem == null ){
					return false;
				}
				
				if ( methodCallItem.getMethodCall() == selectedCallPoint ||
						methodCallItem.getMethodCallExit() == selectedCallPoint ) {
					tree.setSelection(item);
					return true;
				}
				
				if ( selectInTree(selectedCallPoint, item ) ){ //if found break the loop
					item.setExpanded(true);
					return true;
				}
			}
			return false;
		}
	}

	private void loadInteractionRawTrace() {
		mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		try {
			rawTrace = URIMapperFactory.getMapper(mc).getInteractionRawTrace(uri);
			methodCallPoints = rawTrace.getMethodCallPoints();
			
			Hashtable<String, String> elements = URIUtil.parseQueryString(uri.getQuery());
			
			String callIdToSelectString = elements.get(PluginConstants.CALLID);
			
			if ( callIdToSelectString != null ){
				callIdToSelect = Integer.valueOf(callIdToSelectString);
				callPointTypeToSelect = MethodCallPoint.Types.valueOf( elements.get(PluginConstants.CALL_POINT_TYPE) );
			}
			
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		} catch (LoaderException e) {
			Logger.getInstance().log(e);
		}
		
		
	}

	private void selectCallAccordingToURI() {
		
		if ( callIdToSelect == -1 ){
			return;
		}
		

		
		System.out.println("LOOKING FOR "+callIdToSelect+" "+methodCallPoints.size());

		if( callPointTypeToSelect != Types.EXIT ){
			int currentCallId = -1;
			MethodCallPoint callPointToSelect = null;
			for( int i = 0; i < methodCallPoints.size(); i++ ){
				MethodCallPoint cp = methodCallPoints.get(i);
				if( cp.isExit() ){
					continue;
				}

				currentCallId++;

				if ( currentCallId == callIdToSelect ){
					callPointToSelect=cp;
					if ( callPointTypeToSelect == Types.ENTER || callPointTypeToSelect == Types.GENERIC ) {
						selectMethodCallPoint( i, callPointToSelect );
						return;
					}
				}
			}

		} else {
			
			MethodCallPoint callPointToSelect = null;
			Stack<Integer> stack = new Stack<Integer>();
			int callsCounter = -1;
			
			System.out.println("LOOKING FOR "+callIdToSelect+" "+methodCallPoints.size());
			for( int i = 0; i < methodCallPoints.size(); i++ ){
				MethodCallPoint cp = methodCallPoints.get(i);
				
//				System.out.println(" "+cp.getMethodCallId());
				
				
				if( cp.isEnter() ){
					callsCounter++;
					System.out.println("PUSH : "+callsCounter);
					stack.push(callsCounter);
					continue;
				} else if( cp.isGeneric() ){
					callsCounter++;
					System.out.println("GENERIC : "+callsCounter);
					continue;
				}

				int currentCallId = stack.pop();
				System.out.println("POP : "+currentCallId);
				
//				if ( stack.size() == 0 ){
//					selectMethodCallPoint( i, cp );
//					return;
//				}
				if ( currentCallId == callIdToSelect ){
					callPointToSelect=cp;

					selectMethodCallPoint( i, cp );
					return;

				}
			}
		}
		
		
	}

	

	private void selectMethodCallPoint(int positionToSelect, MethodCallPoint callPointToSelect) {
//		TableItem[] items = table.getItems();
//		for ( int i = 0; i < items.length; i++  ){
//			TableItem item  = items[i];
//			MethodCallPoint cp = (MethodCallPoint) item.getData();
//			if ( cp == callPointToSelect ){
//				table.select(i);
//				return;
//			}
//		}
//		
//		System.out.println("NOTHING SELECTED");
		
		TableItem item = table.getItem(positionToSelect);
		MethodCallPoint cp = (MethodCallPoint) item.getData();
//		if ( cp != callPointToSelect ){
//			System.err.println("WRONG SELECTION!!!");
//		}
//		table.select(positionToSelect);
		table.setSelection(positionToSelect);
	}

	private class RootsContainer {
		private List<MethodCallItem> methodCallRootItems = null;
		
		public List<MethodCallItem> getElements(){
			if ( methodCallRootItems == null ){
				methodCallRootItems = new ArrayList<MethodCallItem>();
				buildMethodCallHierarchy(methodCallRootItems);
			}
			return methodCallRootItems;
		}
		
		public void resetItems() {
			methodCallRootItems = null;
		}
	}
	
	private void buildMethodCallHierarchy(List<MethodCallItem> rootItems) {
		buildMethodCallHierarchy = new BuildCallHierarchyJob("Building tree view", rootItems, methodCallPoints);
		
		buildMethodCallHierarchy.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				super.done(event);
				
				IStatus result = event.getResult();
				if (result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							createTree(sash);
							List<MethodCallItem> elements = roots.getElements();
							tree.setItemCount(elements.size());
							tree.setData(elements);
							switchViewButton.setEnabled(true);
							buildTreeViewButton.setEnabled(false);
							 
						}
					});
				} else {
					roots.resetItems();
				}
			}
		});
		buildMethodCallHierarchy.setUser(true);
		buildMethodCallHierarchy.setPriority(Job.LONG);
		buildMethodCallHierarchy.schedule();	
	}

	public void executeFindAction() {
//		InputDialog inputDialog = new InputDialog(table.getParent().getShell(), "Find table items",
//				"Search items by regular expression on method signature:", "Regular Expression", null);
//		if (inputDialog.open() == Dialog.OK) {
//			String regex = inputDialog.getValue();
//			
//			findTableItems = new TableItemsFindJob("Find matching items", regex, table);
//			findTableItems.setUser(true);
//			findTableItems.schedule();
//		}
		FindShell findShell = new FindShell(table.getParent().getShell(), table, "Find table items");
		findShell.open();
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener arg0) {
		selectionProvider.addSelectionChangedListener(arg0);
	}

	@Override
	public ISelection getSelection() {
		return selectionProvider.getSelection();
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener arg0) {
		selectionProvider.removeSelectionChangedListener(arg0);
	}

	@Override
	public void setSelection(ISelection arg0) {
		selectionProvider.setSelection(arg0);
	}

	public MonitoringConfiguration getMonitoringCOnfiguration() {
		// TODO Auto-generated method stub
		return mc;
	}
}
