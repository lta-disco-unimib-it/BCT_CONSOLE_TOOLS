package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

public class ActionBarContributor extends EditorActionBarContributor {

	private FindAction findAction;

	public ActionBarContributor() {
		findAction = new FindAction();
	}
	
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		bars.setGlobalActionHandler(ActionFactory.FIND.getId(), findAction);
		bars.updateActionBars();
	}
	
	@Override
	public void setActiveEditor(IEditorPart targetEditor) {
		super.setActiveEditor(targetEditor);
		
		if(targetEditor instanceof IFindActionEditorSupport) {
			findAction.setEditor((IFindActionEditorSupport) targetEditor);
		}
	}
}
