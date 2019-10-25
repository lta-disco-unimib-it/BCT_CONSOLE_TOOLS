package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions.CallPointDifferences.Difference;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions.TracesComparator;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class InteractionTracesDifferencesEditor extends EditorPart {

	private List<List<Difference>> differences;
	private List<String> firstSequenceAligned, secondSequenceAligned;
	
	public InteractionTracesDifferencesEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {

		if (input instanceof InteractionTracesDifferencesInput) {
			TracesComparator comparator = ((InteractionTracesDifferencesInput) input).getComparator();
			differences = comparator.getDifferencesWithNullFill();
			firstSequenceAligned = comparator.getFirstSequenceAligned();
			secondSequenceAligned = comparator.getSecondSequenceAligned();
		}
		
		super.setSite(site);
		super.setInput(input);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		final Tree tree = new Tree(parent, SWT.VIRTUAL);
		final TreeColumn column1 = new TreeColumn(tree, SWT.NONE);
		final TreeColumn column2 = new TreeColumn(tree, SWT.NONE);
		
		column1.setText("Trace 1");
		column1.setWidth(400);
		column2.setText("Trace 2");
		column2.setWidth(400);
		
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		tree.setItemCount(firstSequenceAligned.size());
		tree.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;
				int index = tree.indexOf(item);
				
				item.setFont(new Font(tree.getDisplay(), "arial", 11, SWT.BOLD));
				item.setText(0, firstSequenceAligned.get(index));
				item.setText(1, secondSequenceAligned.get(index));
				
				List<Difference> diffList =  differences.get(index);
				if (diffList != null) {
					for (Difference difference : diffList) {
						TreeItem subItem = new TreeItem(item, SWT.NONE);
						subItem.setText(0, difference.getVariable().getName() + ": " + difference.getValue1());
						subItem.setText(1, "\t" + difference.getVariable().getName() + ": " + difference.getValue2());
					}
				}
			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
