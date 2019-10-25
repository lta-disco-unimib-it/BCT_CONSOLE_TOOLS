package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions;

/**
 * This interface provides support for finding elements in a editor.
 * All editors want provide find capability must implements this interface.
 */
public interface IFindActionEditorSupport {
	
	/**
	 * Execute find action when the action is triggered.
	 */
	public void executeFindAction();
}
