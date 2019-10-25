package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.AnomalyGraphExporter;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ExtensionsLocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;

import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import cpp.gdb.GlobalVariablesMapSerializer;
import cpp.gdb.GlobalVarsDetector;
import cpp.gdb.GlobalVarsDetector.GlobalVariable;
import cpp.gdb.LineData;
import cpp.gdb.TraceUtils;
import cpp.gdb.VariablesDetector;

import tools.violationsAnalyzer.BctViolationsAnalysisResult;
import tools.violationsAnalyzer.BctViolationsAnalyzer;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import tools.violationsAnalyzer.anomalyGraph.AnomalyGraph;

/**
 * This runnable runs a BCT Violations Analysis task
 * 
 * @author Fabrizio Pastore
 *
 */
public class BctRegressionAnalysisRunnableWithProgress implements
		IRunnableWithProgress {
	private Logger LOGGER = Logger.getLogger(BctRegressionAnalysisRunnableWithProgress.class.getCanonicalName());
	private static final String BUG_205_PATTERN = ".*\\\\([0-9])\\1([0-9])$";
	private IContainer destFolderResource;
	private BctViolationsAnalyzer violationsAnalyzer;
	private Set<String> failuresToAnalyze;
	private RegressionAnalysisResult violationAnalysisResult;
	private MonitoringConfiguration monitoringConfiguration;
	private Map<String, FunctionMonitoringData> monitoredFunctions;
	private HashMap<String, BctIOModelViolation> violFlowMap = new HashMap<String, BctIOModelViolation>();;
	
	/**
	 * Constructor 
	 * 
	 * @param destinationFolderResource	folder in which anomalyGraphs must be persisted
	 * @param analyzer	Violations analyzer to run (properly configured)
	 * @param failureToAnalyze	Id of the failure to analyze (it is passed to the analyzer
	 * @param violationAnalysisResult result object to fill with the information about the generated graphs
	 */
	public BctRegressionAnalysisRunnableWithProgress(IContainer destinationFolderResource, MonitoringConfiguration mc, BctViolationsAnalyzer analyzer, String failureToAnalyze, RegressionAnalysisResult violationAnalysisResult) {
		this(destinationFolderResource,mc,analyzer,createSetOfFailuresToAnalyze(failureToAnalyze),violationAnalysisResult);
	}
	
	private static Set<String> createSetOfFailuresToAnalyze(String failureToAnalyze) {
		Set<String> failuresToAnalyze = new HashSet<String>();
		failuresToAnalyze.add(failureToAnalyze);
		return failuresToAnalyze;
	}

	public BctRegressionAnalysisRunnableWithProgress(IContainer destinationFolderResource, MonitoringConfiguration mc, BctViolationsAnalyzer analyzer, Set<String> failuresToAnalyze, RegressionAnalysisResult violationAnalysisResult) {
		this.destFolderResource = destinationFolderResource;
		this.violationsAnalyzer = analyzer;
		this.failuresToAnalyze = failuresToAnalyze;
		this.violationAnalysisResult = violationAnalysisResult;
		this.monitoringConfiguration = mc;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		List<BctModelViolation> result = new ArrayList<BctModelViolation>();
		
		for( String failureToAnalyze : failuresToAnalyze ){
			List<BctModelViolation> singleResult = violationsAnalyzer.getFilteredBctViolations(failureToAnalyze);
			result.addAll(singleResult);
		}
		
		if ( monitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ||
				monitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config	){
			result=filterFalsePositives( result );
		}
		
		violationAnalysisResult.setFilteredViolations( result );
		
	}

	private List<BctModelViolation> filterFalsePositives(
			List<BctModelViolation> viols) {
		List<BctModelViolation> result = new ArrayList<BctModelViolation>();
		
		
		
		for ( BctModelViolation viol : viols){
			
			viol = applyFilters( viol );
			if ( viol == null ){
				continue;
			}
			
			if ( viol instanceof BctIOModelViolation ){
				try {	
					viol = filterBug205( (BctIOModelViolation) viol );
				} catch ( Throwable t ){
					t.printStackTrace();
				}
				if ( viol == null ){
					continue;
				}

				try {
					viol = filterReturnForConstructor( (BctIOModelViolation) viol );
				} catch ( Throwable t ){
					t.printStackTrace();
				}
				if ( viol == null ){
					continue;
				}

				
				try {
					viol = filterUninitializedVariables( (BctIOModelViolation) viol );
				} catch ( Throwable t ){
					t.printStackTrace();
				}
				if ( viol == null ){
					continue;
				}
				
				

				result.add(viol);

			} else {
				result.add(viol);
			}
		}
		
		return result;
	}



	private BctModelViolation filterUninitializedVariables(
			BctIOModelViolation viol) throws ConfigurationFilesManagerException {
		
		Map<String, FunctionMonitoringData> monitoredFunctions = getMonitoredFunctions();
		
		String violatedFunction = viol.getViolatedMethod();
		violatedFunction = TraceUtils.extractFunctionSignatureFromGenericProgramPointName(violatedFunction);
		
		
		FunctionMonitoringData violatedFunctonData = monitoredFunctions.get(violatedFunction);
		
		if ( violatedFunctonData == null ){
			return viol;
		}
		
		ViolationData violationData = ViolationsUtil.getViolationData(viol);
		
		for ( VariableData violatedVar : violationData.getViolatedVariables() ){
			String varName = violatedVar.getVariableName();
			
			if ( varName.startsWith("&") ){
				varName = varName.substring(1);
			}
			
			if ( isLocalVariable( varName, violatedFunctonData ) ){
				if ( ! variableHasBeenDeclared( varName, violatedFunctonData, viol ) ){
					return null;
				}
			}
		}
		
		return viol;
	}

	private boolean variableHasBeenDeclared(
			String varName, FunctionMonitoringData violatedFunctonData, BctIOModelViolation viol) {
		
		List<String> varToIdentify = new ArrayList<String>();
		varToIdentify.add(varName);
		
		LineData location = ConsoleViolationsUtil.getIOViolationLocation(monitoringConfiguration,viol);
		
		if ( location.getLineNumber() == -1 ){
			return true;
		}
		
		Set<String> varsInFile = VariablesDetector.identifyVariableNamesInFile(varToIdentify, 
				violatedFunctonData.getAbsoluteFile(), 
				violatedFunctonData.getFirstSourceLine(), 
				location.getLineNumber() //violation is observed at the beginning of line, so we stop looking at the line where violation is found (the passed line is excluded)
				);
		
		if ( varsInFile.size() == 0 ){
			return false;
		}
		
		return true;

	}

	private boolean isLocalVariable(String varName,
			FunctionMonitoringData violatedFunctonData) {
		
		if ( varName.startsWith("*this") || varName.startsWith("*this") ){
			return false;
		}
		
		if ( varName.contains(".") ){
			return false;
		}
		
		Set<GlobalVariable> globalVar = getGlobalVariables( violatedFunctonData );
		if ( globalVar == null ){
			return true;
		}
		
		
		return ! globalVarContains(globalVar,varName);
	}

	private boolean globalVarContains(Set<GlobalVariable> globalVar,
			String varName) {
		for ( GlobalVariable gv : globalVar ){
			if ( gv.getName() == null && varName == null ){
				return true;
			}
			if ( gv.getName().equals(varName) ){
				return true;
			}
		}
		
		return false;
	}

	private HashMap<File, Set<GlobalVariable>> globalVariablesMap;
	
	private Set<GlobalVariable> getGlobalVariables(
			FunctionMonitoringData violatedFunctonData) {
		
		try {
			
			if ( globalVariablesMap == null ){
				if ( monitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
					globalVariablesMap = GlobalVariablesMapSerializer.load( ConfigurationFilesManager.getGlobalVariablesMapModifiedFile(monitoringConfiguration) );
				} else {
					globalVariablesMap = GlobalVariablesMapSerializer.load( ConfigurationFilesManager.getGlobalVariablesMapFile(monitoringConfiguration) );
				}
			}
			
			Set<GlobalVariable> result = globalVariablesMap.get(violatedFunctonData.getAbsoluteFile());
			if ( result == null ){
				System.out.println("WORKAROUND: "+BctRegressionAnalysisRunnableWithProgress.class.getCanonicalName());
				result = globalVariablesMap.get(violatedFunctonData.getAbsoluteFile().getName()); ///FIXME: this is a workaround, we shoul be sure that the path of the file is absolute
			}
			
			return result;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private BctModelViolation applyFilters(BctModelViolation viol) {
		
		List<ViolationsFilterAction> actions = ExtensionsLocator.getViolationFilterActions();
		for ( ViolationsFilterAction action : actions ){
			try {
				viol = action.run( monitoringConfiguration, getMonitoredFunctions(), viol );
			} catch ( Throwable t ){
				t.printStackTrace();
			}
			if ( viol == null ){
				return null;
			}
		}
		
		return viol;
	}

	private BctModelViolation filterReturnForConstructor(
			BctIOModelViolation viol) {
		LOGGER.fine("filtering return for constructor "+viol.getViolatedModel());
		if ( ! isReturnValueViolation( viol ) ){
			LOGGER.fine("Not return");
			return viol;
		}
		
		
		
		String method = viol.getViolatedMethod().trim();
		
		if ( TraceUtils.isLineProgramPoint(method) ){
			LOGGER.fine("Not EXTER/EXIT");
			return viol;
		}
		
		method = TraceUtils.extractFunctionSignatureFromGenericProgramPointName(method);
		
		
		Map<String, FunctionMonitoringData> mf = getMonitoredFunctions();
		
		if ( ! mf.containsKey(method) ){
			LOGGER.fine("Method not in cache");
			return viol;
		}
		
		
		
		FunctionMonitoringData functionData = mf.get(method);
		
		if (functionData.isConsructor() || functionData.isDestructor() ){
			LOGGER.fine("COnstructor, return null");
			return null;
		}
		LOGGER.fine("Not constructor");
		
		String returnType = functionData.getReturnType();
		if ( returnType != null && returnType.endsWith("void") ){
			LOGGER.fine("void function");
			return null;
		}
		
		return viol;
				
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

	private Map<String, FunctionMonitoringData> getMonitoredFunctions() {
		
		if ( monitoredFunctions == null ){
			try {
				File monitoredFunctionsF;
				
				if ( monitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
					monitoredFunctionsF = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(monitoringConfiguration);
				} else {
					monitoredFunctionsF = ConfigurationFilesManager.getMonitoredFunctionsDataFile(monitoringConfiguration);
				}
				
				monitoredFunctions = FunctionMonitoringDataSerializer.load(monitoredFunctionsF);
			} catch (Throwable e) {
				e.printStackTrace();
				monitoredFunctions = new HashMap<String, FunctionMonitoringData>();
			}
		}
		
		return monitoredFunctions;
	}

	private BctModelViolation filterBug205(BctIOModelViolation viol) {
		
		ViolationData violationData = ViolationsUtil.getViolationData(viol);
		for ( VariableData vd : violationData.getViolatedVariables() ){
			Object actualValue = vd.getActualValue();
			
			if ( actualValue instanceof String ){
				String value = (String) actualValue;
				if ( value.matches(BUG_205_PATTERN) ){
					System.out.println("BUG 205 - Actual value: "+value);
//					is a string like XXXXX\221
					String suffix = value.substring(value.length()-4);
					if ( viol.getViolation().contains(suffix) ){
						
						return null;
					}
				}
			}
		}
		
		return viol;
	}

	
	public static void main(String args[]){
		
		String pattern = BUG_205_PATTERN;
		
		System.out.println("true : "+"\\1234\\2211".matches(pattern));
		System.out.println("true : "+"AB\\2211".matches(pattern));
		System.out.println("true : "+"AB\\2211AB\\2211".matches(pattern));
		System.out.println("true : "+"AB\\221AB\\2211".matches(pattern));
		System.out.println("true : "+"\\2211".matches(pattern));
		
		System.out.println("false : "+"AB\\221AB\\221".matches(pattern));
		System.out.println("false : "+"\\2212".matches(pattern));
		System.out.println("false : "+"\\2112".matches(pattern));
		System.out.println("false : "+"\\2345".matches(pattern));
		System.out.println("false : "+"\\1221".matches(pattern));
		System.out.println("false : "+"\\1212".matches(pattern));
		
	}
}
