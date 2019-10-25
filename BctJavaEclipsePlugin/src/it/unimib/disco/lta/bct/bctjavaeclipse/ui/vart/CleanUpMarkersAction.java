package it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.actions.ActionDelegate;

public class CleanUpMarkersAction extends ActionDelegate {

	public CleanUpMarkersAction(){
		System.out.println("BUILD");
	}
	
	@Override
	public void run(IAction action) {
		super.run(action);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		System.out.println(selection);
	}

}
