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
