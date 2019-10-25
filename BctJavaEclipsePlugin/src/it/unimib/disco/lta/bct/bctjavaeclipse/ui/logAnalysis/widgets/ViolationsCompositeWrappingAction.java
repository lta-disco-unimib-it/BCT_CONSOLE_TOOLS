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
