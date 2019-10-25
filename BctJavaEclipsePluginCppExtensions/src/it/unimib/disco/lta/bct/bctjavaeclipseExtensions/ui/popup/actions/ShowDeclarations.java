package it.unimib.disco.lta.bct.bctjavaeclipseExtensions.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.ProgramPointsUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ShowDeclarations implements IObjectActionDelegate {

	private ISelection selection;

	public ShowDeclarations() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IAction action) {
		
		if ( selection == null ){
			return;
		}
		// TODO Auto-generated method stub
		if ( selection instanceof IStructuredSelection ){
			IStructuredSelection ss = (IStructuredSelection) selection;
			final IFile selectedElement = (IFile) ss.getFirstElement();
			try {
				ProgramPointsUtil.getVariables(selectedElement, 0);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		
	}

}
