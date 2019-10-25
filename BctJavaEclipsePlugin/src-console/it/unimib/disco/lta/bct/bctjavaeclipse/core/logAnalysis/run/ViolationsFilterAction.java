package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import java.util.Map;

import cpp.gdb.FunctionMonitoringData;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import modelsViolations.BctModelViolation;

public interface ViolationsFilterAction {

	public BctModelViolation run(MonitoringConfiguration monitoringConfiguration,
			Map<String, FunctionMonitoringData> monitoredFunctions, BctModelViolation viol);

}
