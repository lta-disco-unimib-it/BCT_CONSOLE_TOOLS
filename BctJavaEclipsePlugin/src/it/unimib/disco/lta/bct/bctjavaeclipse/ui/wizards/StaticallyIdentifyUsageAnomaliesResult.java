package it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards;

public class StaticallyIdentifyUsageAnomaliesResult {

	private boolean skipPassingProcesses;
	private boolean skipPassingActions;
	private boolean skipPassingTests;
	private boolean canceled;

	public boolean getSkipPassingProcesses() {
		return skipPassingProcesses;
	}

	public boolean getSkipPassingActions() {
		return skipPassingActions;
	}

	public boolean getSkipPassingTests() {
		return skipPassingTests;
	}

	public void setSkipPassingTests(boolean skipPassingTests) {
		this.skipPassingTests = skipPassingTests;
	}
	
	public void setSkipPassingActions(boolean skipPassingActions) {
		this.skipPassingActions = skipPassingActions;
	}
	
	public void setSkipPassingProcesses(boolean skipPassingProcesses) {
		this.skipPassingProcesses = skipPassingProcesses;
	}

	public boolean getCanceled() {
		// TODO Auto-generated method stub
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

}
