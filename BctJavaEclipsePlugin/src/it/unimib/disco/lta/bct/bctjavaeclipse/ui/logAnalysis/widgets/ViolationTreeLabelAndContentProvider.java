package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import modelsViolations.BctModelViolation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class ViolationTreeLabelAndContentProvider extends LabelProvider implements ITreeContentProvider {

	private Hashtable<String, ViolationElement> parents = new Hashtable<String, ViolationElement>();

	// ***********************************************************************
	// LabelProvider method
	@Override
	public String getText(Object element) {
		ViolationElement violationElement = (ViolationElement) element;
		BctModelViolation violation = violationElement.getViolation();

		if (violation == null)
			return violationElement.getTxt();
		String viol = violation.getViolation();
		viol = viol.replace("returnValue.eax", "return" );
		return violation.getViolationType() + ": " + viol;
	}

	@Override
	public void dispose() {
		parents.clear();
	}

	// ***********************************************************************
	// TreeContentProvider method
	public Object[] getChildren(Object parentElement) {
		
		
		
		ViolationElement parentElm = (ViolationElement) parentElement;
//		for ( ViolationElement child : parentElm.getChildren() ){
//			child.getViolation().getViolation();
//		}
		
		LinkedList<ViolationElement> result = new  LinkedList<ViolationElement>();
		result.addAll(parentElm.getChildren());
		Collections.sort(result, new Comparator<ViolationElement>() {

			@Override
			public int compare(ViolationElement o1, ViolationElement o2) {
				return o1.getViolation().getViolation().compareTo(
						o2.getViolation().getViolation());
			}
		});
		
		
		LinkedList<ViolationElement> filteredResult = new  LinkedList<ViolationElement>();
		String prev = null; 
		System.out.println("Models");
		for ( ViolationElement child : result ){
			if ( ! child.getViolation().getViolation().equals(prev) ){
				filteredResult.add(child);
				System.out.println(child.getViolation().getViolation());
			}
			prev = child.getViolation().getViolation();
		}
		
		return filteredResult.toArray();
	}

	public Object getParent(Object element) {
		ViolationElement ve = (ViolationElement) element;
		return parents.get(ve.getTxt());
	}

	public boolean hasChildren(Object element) {
		ViolationElement ve = (ViolationElement) element;
		return ve.getViolation() == null ? true : false;
	}

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		Collection<ViolationElement> input = (Collection<ViolationElement>) inputElement;

		for (ViolationElement ve : input) {
			if (ve.getViolation() == null)
				parents.put(ve.getTxt(), ve);
		}

		for (ViolationElement ve : input) {
			if (ve.getViolation() != null) {
				ViolationElement parent = parents.get(ve.getTxt());
				parent.addChild(ve);
			}
		}

		return parents.values().toArray();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// We don't provide support to input changes.
		
		System.out.println("OLD INPUT "+oldInput);
		System.out.println("NEW INPUT "+newInput);
		
		if ( newInput == null ){
			//FIXES annoying errror occurring when violations analysis editor is closed: 
			//Unhandled event loop exception
			//java.lang.StackOverflowError
			return;
		}
//		
//		if ( oldInput != null ){
//			System.out.println(oldInput.getClass().getCanonicalName());
//		}
		
		if ( oldInput instanceof List ){
			if ( viewer instanceof TableViewer ){
				for ( Object element : ((List)oldInput) ){
					System.out.println("REMOVING "+element);
					((TableViewer)viewer).remove(element);
				}
			} else if ( viewer instanceof TreeViewer ) {
				for ( Object element : ((List)oldInput) ){
					System.out.println("REMOVING "+element);
					((TreeViewer)viewer).remove(element);
				}
			}
		}
		
		
		viewer.refresh();
		
	}
}
