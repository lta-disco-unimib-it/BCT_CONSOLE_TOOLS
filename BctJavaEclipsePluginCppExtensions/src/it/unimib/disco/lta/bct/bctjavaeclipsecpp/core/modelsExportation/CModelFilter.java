package it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.modelsExportation;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.FunctionsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.cdt.internal.core.model.FunctionDeclaration;

import tools.fshellExporter.ModelFilter;
import tools.fshellExporter.ModelInfo;
import tools.fshellExporter.ModelsExporter;
import tools.violationsAnalyzer.ViolationsUtil;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.GlobalVarsDetector;
import cpp.gdb.GlobalVarsDetector.GlobalVariable;
import cpp.gdb.VariablesDetector;

public class CModelFilter implements ModelFilter {
	private GlobalVarsDetector detector;
	private MonitoringConfiguration mc;
	
	public CModelFilter(MonitoringConfiguration mc) {
		this.mc = mc;
		File executable = null;
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			CConfiguration conf = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
			executable = new File ( conf.getOriginalSwExecutable() );
		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			CConfiguration conf = (CConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			executable = new File ( conf.getOriginalSwExecutable() );
		} else {
			throw new IllegalStateException("Only C_Config and C_regression config supported");
		}
		detector = new GlobalVarsDetector(executable);
	}

	@Override
	public String processDataProperty(String dataProperty, ModelInfo modelInfo) {
		FunctionMonitoringData functionData = modelInfo.getFunctionOriginal();
		if ( functionData == null ){
			return null;
		}

		FunctionDeclaration function = FunctionsUtil.getFunctionDeclaration(functionData);
		
		
		
		
		if ( function == null ){
			return null;
		}
		
		if ( dataProperty.equals("returnValue == null") ||dataProperty.equals("returnValue != null") || dataProperty.equals("returnValue.eax != null") || dataProperty.equals("returnValue.eax == null") ){
			return processReturnNotNull( dataProperty, modelInfo, function, functionData );
		}
		
		dataProperty = rewriteNullImplication(dataProperty, modelInfo, function, functionData);
		
		if ( filterUselessNullCheck( dataProperty, modelInfo, function, functionData ) ){
			return null;
		}
		
		if ( assertWithReturnInVoidFunction(dataProperty, modelInfo, function, functionData) ){
			return null;
		}
		
		if ( ! allVariablesAreVisible(dataProperty, modelInfo, function, functionData) ){
			return null;
		}
		
		return dataProperty;
	}
	
	private boolean assertWithReturnInVoidFunction(String dataProperty,
			ModelInfo modelInfo, FunctionDeclaration function,
			FunctionMonitoringData functionData) {
		Set<String> vars = ViolationsUtil.extractParentVariables(dataProperty);
		if ( ! vars.contains("returnValue") ){
			return false;
		}
		
		if ( FunctionsUtil.isVoid( function ) ){
			return true;
		}
		
		return false;
	}

	private boolean allVariablesAreVisible(String dataProperty,
			ModelInfo modelInfo, FunctionDeclaration function,
			FunctionMonitoringData functionData) {
		Set<String> varsToIdentify = ViolationsUtil.extractParentVariablesNoStar(dataProperty);
		Set<String> varsInFile = ModelsExporter.identifyVariablesUsedInMethodDefinition(functionData, varsToIdentify);
		
		varsToIdentify.removeAll(varsInFile);
		
		if ( varsToIdentify.isEmpty() ){
			return true;
		}
		
		List<GlobalVariable> globalVars = detector.getGlobalVariables();
		
		for ( GlobalVariable globalVar : globalVars){
			varsToIdentify.remove(globalVar.getName());
			if ( varsToIdentify.isEmpty() ){
				break;
			}

		}
		
		
		
		
		return varsToIdentify.isEmpty();
	}

	private String processReturnNotNull(String dataProperty,
			ModelInfo modelInfo, FunctionDeclaration function,
			FunctionMonitoringData functionData) {
		String returnType = FunctionsUtil.extractMethodReturnType(functionData);
		if ( returnType.endsWith("*") ){
			return dataProperty;
		}
		
		return null;
	}

	/**
	 * 
	 * @param dataProperty
	 * @return
	 */
	public String rewriteNullImplication(String dataProperty,
			ModelInfo modelInfo, FunctionDeclaration function, FunctionMonitoringData functionData) {
		//This is a rwa implementation, we would need a better one with parsing. 
//		StringTokenizer st = new StringTokenizer(dataProperty, "()" );
//		if ( ! st.hasMoreTokens() ){
//			return dataProperty;
//		}
//		String nullExpression = st.nextToken();
//		
//		if ( ! st.hasMoreTokens() ){
//			return dataProperty;
//		}
//		String implication = st.nextToken().trim();
//		if ( ! implication.equals("==>") ){
//			return dataProperty;
//		}
		
		int implicationPos = dataProperty.indexOf("==>");
		if ( implicationPos < 0 ){
			return dataProperty;
		}
		
		String leftPart = dataProperty.substring(0,implicationPos);
		leftPart = removeWrappingParenthesis( leftPart );
		
		if ( ! filterOutUselessNullCheckInExpression(dataProperty, modelInfo, function, functionData) ){
			return dataProperty;
		}
		
		dataProperty = dataProperty.substring(implicationPos+3).trim();
		
		dataProperty = removeWrappingParenthesis( dataProperty );
		
		return dataProperty;
	}

	private String removeWrappingParenthesis(String expression) {
		return expression.replace('(', ' ').replace(')', ' ').trim();
	}

	private boolean filterUselessNullCheck(String dataProperty,
			ModelInfo modelInfo, FunctionDeclaration function, FunctionMonitoringData functionData) {
		
		StringTokenizer st = new StringTokenizer(dataProperty, "()" );
//		List<String> vars = new ArrayList<String>();
		while ( st.hasMoreTokens() ){
			String expression = st.nextToken();
		
			if ( filterOutUselessNullCheckInExpression(expression, modelInfo, function, functionData)){
				return true;
			}
		}
		
		//TODO: check if all variables are in scope
		return false;
	}

	private boolean filterOutUselessNullCheckInExpression(String expression,
			ModelInfo modelInfo, FunctionDeclaration function, FunctionMonitoringData functionData) {
		if ( ! containsNullCheck ( expression) ){
			return false;
		}
		Set<String> variables = ViolationsUtil.extractParentVariables(expression);
		
		if ( variables.size() != 1 ){
			//we cannot handle  cases like var.field != null
			return true;
		}
		
		String variableName = variables.iterator().next();
		
		if ( ! isPointerParameter( variableName, function, functionData ) ){
			return true;
		}
		
		return false;
		
	}

	private boolean isPointerParameter(String variableName,
			FunctionDeclaration function, FunctionMonitoringData functionData) {
		Map<String,GlobalVariable>vars = getAllVariables( function, functionData );
		
		GlobalVariable var = vars.get(variableName);
		if ( var == null ){
			return false;
		}
		
		return var.isPointer();
	}

	private Map<String, Map<String, GlobalVariable>> variablesInScope = new HashMap<String,Map<String,GlobalVariable>>();
	
	private Map<String, GlobalVariable> getAllVariables(FunctionDeclaration function, FunctionMonitoringData functionData) {
		Map<String, GlobalVariable> varsMap = variablesInScope.get( functionData.getMangledName() );

		if ( varsMap == null ){
			ArrayList<GlobalVariable> vars = new ArrayList<GlobalVariable>();

			List<GlobalVariable> parameters = FunctionsUtil.retrieveParameters(function);
			vars.addAll(parameters);
			
			File sourceFile = functionData.getAbsoluteFile();
			
			List<GlobalVariable> globalVars = detector.getGlobalVariables();
			
			
			
			Set<GlobalVariable> usedGlobals = VariablesDetector.identifyVariablesInFile(globalVars, sourceFile, functionData.getFirstSourceLine() , functionData.getLastSourceLine());
			vars.addAll(usedGlobals);
			
			varsMap = new HashMap<String, GlobalVariable>();
			for ( GlobalVariable gv : vars ){
				varsMap.put(gv.getName(), gv);
			}
			variablesInScope.put(functionData.getMangledName(), varsMap);
		}
		
		return varsMap;
	}

	private boolean containsNullCheck(String expression) {
		StringTokenizer st = new StringTokenizer(expression, "\t ,;+-=/!~|{}():<>?" );
//		List<String> vars = new ArrayList<String>();
		while ( st.hasMoreTokens() ){
			String variable = st.nextToken();

//			variable = variable.replaceAll("\\[[0-9]*\\]","[]");
			if ( variable.equals("null") ){
				return true;
			}
		}
		return false;
	}

}
