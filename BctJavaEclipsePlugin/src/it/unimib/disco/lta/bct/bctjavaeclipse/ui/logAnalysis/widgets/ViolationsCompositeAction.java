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
