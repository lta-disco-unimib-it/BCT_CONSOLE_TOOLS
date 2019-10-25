package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This tree shows a list of Java Fragments 
 * 
 * @author Fabrizio Pastore
 *
 */
public class JavaResourcesTreeComposite extends Composite implements BCTObservable {

	private Tree tree;
	private TreeEditor treeEditor;
	
	
	private HashMap<TreeItem,IPackageFragmentRoot> roots = new HashMap<TreeItem, IPackageFragmentRoot>();
	private JavaResourcesMonitoringPage page;
	
	private BCTObservaleIncapsulated observable = new BCTObservaleIncapsulated(this);

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}
	
	public JavaResourcesTreeComposite(JavaResourcesMonitoringPage parent, int style) {
		super(parent, style);
		this.page = parent;
		//this.editor = editor;
		//setLayout(new FillLayout());
		
		createWidgets();
	}

	private void createWidgets() {
		createTree();
	}

	private void createTree() {
		tree = new Tree(this, SWT.MULTI | SWT.CHECK);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText("Resource");
		tree.setSize(200, 300);
		
		treeEditor = new TreeEditor(tree);
		treeEditor.horizontalAlignment = SWT.LEFT;
		treeEditor.grabHorizontal = true;
		treeEditor.minimumWidth = 50;

		tree.addSelectionListener(createSelectionListener());
		
	}



	private SelectionListener createSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if ( ! ( e.detail == SWT.CHECK ) ){
					return;
				}
				
				TreeItem item = (TreeItem) e.item;
				if (item == null){
					return;
				}
				
				checkAction(item);
			
				setChanged();
				
			}
			
		};
	}
	
	private void checkAction(TreeItem item) {
		boolean selectionValue = item.getChecked();
		selectChildren(item, selectionValue);
	}

	private void selectChildren(TreeItem item, boolean selectionValue) {
		item.setChecked(selectionValue);
		for ( TreeItem child : item.getItems() ){
			selectChildren(child,selectionValue);
		}
	}

	private void setChanged() {
		bctObservableUpdate(this, null);
	}


	public void populateTree(List<IJavaProject> projects, IJavaProject expandedProjects ) {
		
		for (IJavaProject project : projects) {
			TreeItem prjItem = new TreeItem(tree, SWT.NONE);
			prjItem.setText(project.getElementName());
			
			try {
				
				
				for (IPackageFragmentRoot root : project.getPackageFragmentRoots() ){
					
					TreeItem item = new TreeItem(prjItem, SWT.NONE);
					item.setText(root.getElementName());
					
					roots.put(item, root);
				}
			} catch (JavaModelException e) {
				Logger.getInstance().log(e);
			}
			
			if ( project.equals(expandedProjects) ){
				prjItem.setExpanded(true);
			}
			
		}
		for (int i = 0; i < tree.getColumnCount(); i++) {
			tree.getColumn(i).pack();
		}
	}

	public Tree getTree() {
		return tree;
	}

	public void selectElements(
			List<IPackageFragmentRoot> fragmentsToSelect) {
		for ( IPackageFragmentRoot fragment : fragmentsToSelect ){
			for ( Entry<TreeItem,IPackageFragmentRoot> entry : roots.entrySet() ){
				if ( entry.getValue().equals(fragment) ){
					entry.getKey().setChecked(true);
				}
			}
		}
	}
	
	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observable.notifyBCTObservers(message);
	}

	public List<IPackageFragmentRoot> getSelectedPackageFragments() {
		List<IPackageFragmentRoot> result = new ArrayList<IPackageFragmentRoot>();
		for ( Entry<TreeItem,IPackageFragmentRoot> entry : roots.entrySet() ){
			if ( entry.getKey().getChecked() ){
				result.add(entry.getValue());
			}
		}
		return result;
	}
}

