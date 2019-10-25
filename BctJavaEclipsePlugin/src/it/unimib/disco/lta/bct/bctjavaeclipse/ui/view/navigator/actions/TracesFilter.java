package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpp.gdb.FileChangeInfo;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.SourceLinesMapper;
import cpp.gdb.SourceMapperException;
import cpp.gdb.TraceUtils;
import cpp.gdb.FileChangeInfo.Delta;

public class TracesFilter {
	
	MonitoringConfiguration mc;
	List<MethodCallPoint> originalTrace, modifiedTrace;
	Map<String, FunctionMonitoringData> originalFunctionsMap, modifiedFunctionsMap;
	List<FileChangeInfo> diffs;
	private boolean renameTraces;
	private boolean modifiedTraceBelongsToOriginalSoftware;
	private boolean filterOutNotModifiedLines;
	
	public TracesFilter(MonitoringConfiguration mc, List<MethodCallPoint> originalTrace, List<MethodCallPoint> modifiedTrace, boolean renameTraces, boolean modifiedTraceBelongsToOriginalSoftware, boolean filterOutNotModifiedLines) throws ConfigurationFilesManagerException, IOException {
		this.mc = mc;
		this.originalTrace = originalTrace;
		this.modifiedTrace = modifiedTrace;
		this.renameTraces = renameTraces;
		this.modifiedTraceBelongsToOriginalSoftware = modifiedTraceBelongsToOriginalSoftware;
		CRegressionConfiguration conf = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		diffs = CRegressionAnalysisUtil.getDiffs(mc, conf);
		originalFunctionsMap = CRegressionAnalysisUtil.getFunctionsDeclaredInOriginalSoftware(mc, conf);
		modifiedFunctionsMap = CRegressionAnalysisUtil.getFunctionsDeclaredInModifiedSoftware(mc, conf);
		this.filterOutNotModifiedLines = filterOutNotModifiedLines;
	}
	
	public List<MethodCallPoint> getFilteredOriginalTrace() throws ConfigurationFilesManagerException, IOException {
		return filterLines(true, getLinesToRemove(true));
	}
	
	public List<String> getFilteredOriginalTraceAsString() throws ConfigurationFilesManagerException, IOException {
		return alignTraces(filterLines(true, getLinesToRemove(true)));
	}
	
	public List<String> getFilteredModifiedTraceAsString() throws ConfigurationFilesManagerException, IOException {				
		List<MethodCallPoint> trace = filterLines(false, getLinesToRemove( modifiedTraceBelongsToOriginalSoftware) );
		return getCallPointsAsString(trace);
	}
	
	public List<MethodCallPoint> getFilteredModifiedTrace() throws ConfigurationFilesManagerException, IOException {
		return filterLines(false, getLinesToRemove( modifiedTraceBelongsToOriginalSoftware ) );
	}
	
	private List<String> alignTraces(List<MethodCallPoint> traceToAlign) throws IOException {
		List<String> callPointsAsString = new ArrayList<String>(traceToAlign.size());
		CRegressionConfiguration conf = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		
		SourceLinesMapper mapper = new SourceLinesMapper(conf.getOriginalSwSourcesFolderFile(), conf.getModifiedSwSourcesFolderFile(), diffs);
		
		for (MethodCallPoint cp : traceToAlign) {
			if (cp.isEnter() || cp.isExit()) {
				callPointsAsString.add(cp.getMethod().getSignature());
				continue;
			}
			
			if ( ! renameTraces ){
				callPointsAsString.add(cp.getMethod().getSignature());
				continue;
			}
			
			String signature = cp.getMethod().getSignature();
			String function = TraceUtils.getFunctionName(signature);
			String fileName = originalFunctionsMap.get(function).getSourceFileName();
			
			
			int line = TraceUtils.getLine(signature);
			
			try {
				int newLine = mapper.getCorrespondingLineInModifiedProject(fileName, line);
				if (newLine != line) {
					callPointsAsString.add(function + ":" + newLine);
				} else {
					callPointsAsString.add(cp.getMethod().getSignature());
				}
			} catch (SourceMapperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return callPointsAsString;
	}
	
	private List<MethodCallPoint> filterLines(boolean original, Map<String, List<Integer>> linesToRemove) {
		List<MethodCallPoint> trace = original ? originalTrace : modifiedTrace;
		if ( ! filterOutNotModifiedLines  ){
			return trace;
		} 
		
		
		List<MethodCallPoint> callPoints = new ArrayList<MethodCallPoint>();
		
		for (MethodCallPoint callPoint : trace) {
			if (callPoint.isEnter() || callPoint.isExit()){
				callPoints.add(callPoint);
			} else {
				includeIfNotToFilterOut( callPoints, callPoint, original, linesToRemove );
			}
		}
		
		return callPoints;	
	}
	
	private void includeIfNotToFilterOut(List<MethodCallPoint> callPoints,
			MethodCallPoint callPoint, boolean original, Map<String, List<Integer>> linesToRemove) {
		String function = TraceUtils.getFunctionName(callPoint.getMethod().getSignature());
		
		FunctionMonitoringData originalFunction = originalFunctionsMap.get(function);
		FunctionMonitoringData modifiedFunction = modifiedFunctionsMap.get(function);
		
	    String fileName;
	    
	    if ( original ){
	    	if ( originalFunction == null ){
	    		throw new RuntimeException("function not found: "+function);
	    	}
	    	fileName = originalFunction.getSourceFileName(); 
	    } else {
	    	if ( modifiedFunction == null ){
	    		throw new RuntimeException("function not found: "+function);
	    	}
	    	fileName = modifiedFunction.getSourceFileName();
	    }
	    
		int line = TraceUtils.getLine(callPoint.getMethod().getSignature());
		
		if (line < 0){
			return;
		}
		
		if (!linesToRemove.get(fileName).contains(line)){
			callPoints.add(callPoint);
		}
	}

	private Map<String, List<Integer>> getLinesToRemove(boolean originalTrace) {
		if ( ! filterOutNotModifiedLines ){
			return new HashMap<String, List<Integer>>();
		}
		
		return TraceUtils.getModifiedLines(diffs, originalTrace);
	}
	
	private List<String> getCallPointsAsString(List<MethodCallPoint> callPoints) {
		List<String> list = new ArrayList<String>(callPoints.size());
		
		for (MethodCallPoint cp : callPoints) {
			list.add(cp.getMethod().getSignature());
		}
		
		return list;
	}
}