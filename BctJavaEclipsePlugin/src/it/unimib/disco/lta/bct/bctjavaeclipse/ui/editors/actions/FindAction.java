package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions;

import org.eclipse.jface.action.Action;

public class FindAction extends Action {
	private IFindActionEditorSupport editor;

	@Override
	public void run() {
		editor.executeFindAction();
	}

	public void setEditor(IFindActionEditorSupport editor) {
		this.editor = editor;
	}
}