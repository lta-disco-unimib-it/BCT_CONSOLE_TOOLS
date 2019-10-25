package it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.logAnalysis;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.internal.core.model.Function;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;
import org.eclipse.core.resources.IFile;

import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;
import cpp.gdb.DemangledNamesUtils;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.LineData;
import cpp.gdb.TraceUtils;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AdvancedViolationsUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.ViolationsFilterAction;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.CFileUtil;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.FunctionsUtil;

public class VoidReturnViolationsFilterAction implements ViolationsFilterAction {

	public VoidReturnViolationsFilterAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BctModelViolation run(
			MonitoringConfiguration monitoringConfiguration,
			Map<String, FunctionMonitoringData> mf,
			BctModelViolation _viol) {
		
		if ( ! ( _viol instanceof BctIOModelViolation ) ){
			return _viol;
		}
		
		BctIOModelViolation viol = (BctIOModelViolation) _viol;
		
		if ( ! isReturnValueViolation( viol ) ){
			return viol;
		}
		
		
		
		String method = viol.getViolatedMethod().trim();
		
		if ( TraceUtils.isLineProgramPoint(method) ){
			return viol;
		}
		
		method = TraceUtils.extractFunctionSignatureFromGenericProgramPointName(method);
		
		
		
		if ( ! mf.containsKey(method) ){
			return viol;
		}
		LineData location = AdvancedViolationsUtil.getViolationLocation(monitoringConfiguration, viol);
		
		FunctionMonitoringData functionData = mf.get(method);
		String returnType = null;
		
		
		
//		functionData.getAbsoluteFile()
//		IFile sourceFile = AdvancedViolationsUtil.getFileForLocation(location, monitoringConfiguration);
		
		returnType = FunctionsUtil.extractMethodReturnType(functionData);
		
		if ( returnType == null ){
			return viol;
		}
		boolean isVoid = returnType.equals("void");
		if ( isVoid ){
			return null;
		} else {
			return viol;//stop here, violation holds
		}
		
//		System.out.println("Function not found: "+demangled);
		
//		return viol;
	}

	

	private boolean isReturnValueViolation(BctIOModelViolation viol) {
		ViolationData violationData = ViolationsUtil.getViolationData(viol);
		for ( VariableData vd : violationData.getViolatedVariables() ){
			if ( vd.getVariableName().startsWith("returnValue") ){
				return true;
			}
		}
		return false;
	}

}
