/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
