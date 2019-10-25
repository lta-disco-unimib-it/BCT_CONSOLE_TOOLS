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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import org.eclipse.jface.action.Action;

public class ViolationsCompositeWrappingAction extends Action {

	private ViolationsCompositeAction wrappedAction;
	private ViolationsComposite violationsComposite;

	public ViolationsCompositeWrappingAction(
			ViolationsComposite violationsComposite, ViolationsCompositeAction wrappedAction) {
		this.wrappedAction = wrappedAction;
		this.violationsComposite = violationsComposite;
	}

	public String getText() {
		return wrappedAction.getText();
	}

	public String getToolTipText() {
		return wrappedAction.getToolTipText();
	}

	public String getDescription() {
		return wrappedAction.getDescription();
	}

	public void run() {
		
		wrappedAction.headRun(violationsComposite.getViolationElement().getViolation(), violationsComposite.getMonitoringConfiguration());
	}
	
}
