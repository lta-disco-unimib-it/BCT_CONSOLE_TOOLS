package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.OpenListener;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class ActionProvider extends CommonActionProvider {

	private RefreshBCTContentsAction refreshBCTContentsAction;
	private FindAction findAction;
	private CompareInteractionTracesAction compareInteractionTracesAction;
	private CompareInteractionTracesAction compareInteractionTracesActionNoPar;
	private CompareInteractionTracesWithAlignmentAction compareInteractionTracesActionAlign;
	private CompareInteractionTracesWithAlignmentAction identifyNonDeterministicVariables;
	
	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		StructuredViewer structuredViewer = site.getStructuredViewer();
		structuredViewer.addOpenListener(new OpenListener());
		refreshBCTContentsAction = new RefreshBCTContentsAction(structuredViewer);
		compareInteractionTracesAction = new CompareInteractionTracesAction(structuredViewer, true);
		
		
		compareInteractionTracesActionNoPar = new CompareInteractionTracesAction(structuredViewer, false);
		
		identifyNonDeterministicVariables = new CompareInteractionTracesWithAlignmentAction(structuredViewer, true, true);
		
		compareInteractionTracesActionAlign = new CompareInteractionTracesWithAlignmentAction(structuredViewer, true, false);
		
		findAction = new FindAction(structuredViewer);
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), refreshBCTContentsAction);
		actionBars.updateActionBars();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, refreshBCTContentsAction);
		if (findAction.isEnabled()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_SEARCH, findAction);
		}
		if ( compareInteractionTracesAction.isEnabled() ){
			menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, compareInteractionTracesAction);
			menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, compareInteractionTracesActionNoPar);
			menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, identifyNonDeterministicVariables);
			menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, compareInteractionTracesActionAlign);
		}
	}
}
