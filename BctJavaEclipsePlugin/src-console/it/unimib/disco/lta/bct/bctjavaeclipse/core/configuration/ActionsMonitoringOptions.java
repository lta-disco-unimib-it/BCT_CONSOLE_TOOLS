package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.List;

import util.componentsDeclaration.Component;

public class ActionsMonitoringOptions {
	private boolean monitorActions = false;
	private List<Component> actionsGroupsToMonitor;
	

	public boolean isMonitorActions() {
		return monitorActions;
	}

	public void setMonitorActions(boolean monitorActions) {
		this.monitorActions = monitorActions;
	}

	public List<Component> getActionsGroupsToMonitor() {
		return actionsGroupsToMonitor;
	}

	public void setActionsGroupsToMonitor(List<Component> actionsGroupsToMonitor) {
		this.actionsGroupsToMonitor = actionsGroupsToMonitor;
	}


}
