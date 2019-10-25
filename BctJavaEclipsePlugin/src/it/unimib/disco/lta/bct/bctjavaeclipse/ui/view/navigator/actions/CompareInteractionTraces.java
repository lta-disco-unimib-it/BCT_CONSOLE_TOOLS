package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.navigator.CommonNavigator;

public class CompareInteractionTraces implements IViewActionDelegate {

	IViewPart view;

	public void init(IViewPart view) {
		this.view = view;
	}

	public void run(IAction action) {
		System.out.println(view.getClass().getCanonicalName());
		if (view instanceof CommonNavigator) {
			CommonNavigator navigator = (CommonNavigator) view;
			new CompareInteractionTracesAction(navigator.getCommonViewer(),true).run();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		
	}

}
