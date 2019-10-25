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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.RawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AdvancedViolationsUtil;



import modelsViolations.BctModelViolation;

import org.eclipse.jface.action.Action;

import cpp.gdb.LineData;

public abstract class ViolationsCompositeAction {
	
	private MonitoringConfiguration monitoringConfiguration;

	public abstract String getText();

	public abstract String getToolTipText();

	public abstract String getDescription();
	
	public final void headRun(BctModelViolation bctModelViolation, MonitoringConfiguration monitoringConfiguration ){
		this.monitoringConfiguration = monitoringConfiguration;
		run(bctModelViolation, monitoringConfiguration);
	}
	
	public abstract void run (BctModelViolation bctModelViolation, MonitoringConfiguration monitoringConfiguration );
	

	public String getMethodName( BctModelViolation violation ){
		return AdvancedViolationsUtil.getMethodName(violation);
	}
	

	public LineData getViolationLocation( BctModelViolation violation ){
		return AdvancedViolationsUtil.getViolationLocation(monitoringConfiguration, violation);
	}

	public LineData getLineFromStackInfo(BctModelViolation violation, int stackPosition) {
		return AdvancedViolationsUtil.getLineFromStackInfo(violation, stackPosition);
	}
	

}
