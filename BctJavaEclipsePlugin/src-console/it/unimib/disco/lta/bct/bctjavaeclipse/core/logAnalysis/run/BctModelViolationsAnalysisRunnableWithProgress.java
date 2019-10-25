package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.AnomalyGraphExporter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;

import tools.violationsAnalyzer.BctViolationsAnalysisResult;
import tools.violationsAnalyzer.BctViolationsAnalyzer;
import tools.violationsAnalyzer.anomalyGraph.AnomalyGraph;

/**
 * This runnable runs a BCT Violations Analysis task
 * 
 * @author Fabrizio Pastore
 *
 */
public class BctModelViolationsAnalysisRunnableWithProgress implements
		IRunnableWithProgress {

	private IContainer destFolderResource;
	private BctViolationsAnalyzer violationsAnalyzer;
	private String failureToAnalyze;
	private ViolationsAnalysisResult violationAnalysisResult;
	
	/**
	 * Constructor 
	 * 
	 * @param destinationFolderResource	folder in which anomalyGraphs must be persisted
	 * @param analyzer	Violations analyzer to run (properly configured)
	 * @param failureToAnalyze	Id of the failure to analyze (it is passed to the analyzer
	 * @param violationAnalysisResult result object to fill with the information about the generated graphs
	 */
	public BctModelViolationsAnalysisRunnableWithProgress(IContainer destinationFolderResource, BctViolationsAnalyzer analyzer, String failureToAnalyze, ViolationsAnalysisResult violationAnalysisResult) {
		this.destFolderResource = destinationFolderResource;
		this.violationsAnalyzer = analyzer;
		this.failureToAnalyze = failureToAnalyze;
		this.violationAnalysisResult = violationAnalysisResult;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		File destFolder = destFolderResource.getLocation().toFile();
		
		
		BctViolationsAnalysisResult result = violationsAnalyzer.analyze(failureToAnalyze);
		AnomalyGraph ag = result.getBestAnomalyGraph();
		
		
		
		List<AnomalyGraph> ags = result.getAnomalyGraphs();
		int i = 0;
		for ( AnomalyGraph agr : ags  ){

			File destFile = new File(destFolder,i+".anomalyGraph_diagram");
			try {	
				AnomalyGraphExporter.exportAnomalyGraph(agr,destFile);

			} catch (IOException e) {
				Logger.getInstance().log(e);
			}
			int connectedComponents = agr.getConnectedComponents().size();
			//IFile idestFile = destFolderResource.getParent().getFile(new Path(destFolder.getName()+"/"+destFile.getName()));
			//anomalyTable.addElement(""+i, destFile.getName(), ""+connectedComponents, idestFile);
			boolean isBest = ( ag == agr );


			File serializedFile = new File(destFolder,i+".ser");
			try{
				tools.violationsAnalyzer.anomalyGraph.AnomalyGraphExporter.serialize(agr, serializedFile);
			} catch (IOException e) {
				Logger.getInstance().log(e);
			}
			
			
			IFile localDestFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(destFile.getAbsolutePath()));
			IFile localSerializedFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(serializedFile.getAbsolutePath()));
			
			//violationAnalysisResult.newElement(""+i, destFile.getAbsolutePath(), serializedFile.getAbsolutePath(), connectedComponents, agr.getConnectedComponents(), isBest);
			violationAnalysisResult.newElement(""+i, localDestFile.getLocation().toString(), localSerializedFile.getLocation().toString(), connectedComponents, agr.getConnectedComponents(), isBest);
			
			++i;

		}
		

		
	}

}
