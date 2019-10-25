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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AssertionAnomaliesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.JavaResourcesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.util.ActionUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import console.AnomaliesIdentifier;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.FileUtil;

public class IdentifyAnomalousAssertionsAction  implements IObjectActionDelegate  {

	private HashSet<Set<String>> coveredInstructions;



	public IdentifyAnomalousAssertionsAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {

	}

	public void run(IAction action) {
		run(action, null);
	}

	public void run(IAction action, IJobChangeListener jobListener) {
		
		//RESET STATE
		coveredInstructions = null;
		
		try {
			File selectedFile = ActionUtil.getSelectedMonitoringConfigurationFile( action );
			
			File logFile;
			MonitoringConfiguration mc;
			
//			if ( selectedFile.getName().equals("bctCBELog") ){
//				logFile = selectedFile;
//				String bctHome = logFile.getParentFile().getName();
//				
//				int pos = bctHome.indexOf('_');
//				if ( pos > 0 ){
//					bctHome = bctHome.substring(0,pos);
//				}
//				MonitoringConfigurationRegistry.getInstance().getAssociatedMonitoringConfiguration(new File(bctHome));
//				mc = null;
//			} else {
//				mc = ActionUtil.getSelectedMonitoringConfiguration( action );
//				logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);
//			}
			
			mc = ActionUtil.getSelectedMonitoringConfiguration( action );
			logFile = ConfigurationFilesManager.getBCTCbeLogFile(mc);

			File bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
			
			List<BctModelViolation> testAnomalies;
			
			if ( logFile.length() > 100000000 ){
				
				int anomaliesSize = splitLog( logFile,  bctHome, 10000 );
				
				testAnomalies = new ArrayList<>(anomaliesSize);
				
				int idx = 1;
				File anomFile = new File ( bctHome, "anomalies.ser."+idx );
				
				while ( anomFile.exists() ){
					testAnomalies.addAll( loadAnomalies(anomFile) );
					idx++;
					anomFile = new File ( bctHome, "anomalies.ser."+idx );
				}
				
				System.gc();
				
				Set<String> testClasses = extractTestClasses(testAnomalies);
				

				identifyAnoamlousAssertions(mc, testAnomalies, testClasses, true, null );
				
				return;
			} 
			
			
			
			testAnomalies = AssertionAnomaliesUtil.retrieveTestAnomalies(logFile);
			
			
			Set<String> testClasses = extractTestClasses(testAnomalies);


			
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, true, FILTERING.ALL );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, true, FILTERING.InvokedByTests );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, true, FILTERING.VerifiedByTests );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, true, FILTERING.CoverageLinesPercentage );
			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, true, FILTERING.CoveragePercentage );
			
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, false, FILTERING.ALL );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, false, FILTERING.InvokedByTests );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, false, FILTERING.VerifiedByTests );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, false, FILTERING.CoverageLinesPercentage );
//			identifyAnoamlousAssertions(mc, testAnomalies, testClasses, false, FILTERING.CoveragePercentage );

		} catch (CBEBctViolationsLogLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public Set<String> extractTestClasses(List<BctModelViolation> testAnomalies) {
		Set<String> testCases = extractTestCasesWithViolations(testAnomalies);

		Set<String> testClasses = new HashSet<String>();
		for( String testCase : testCases ){
			int classEnd = testCase.lastIndexOf('.');
			String testClassName = testCase.substring(0, classEnd);
			testClasses.add(testClassName);
		}
		return testClasses;
	}

	private int splitLog(File logFile, File bctHome, int bceBlockLimit ) {
		
		int anomaliesSize = 0;
		try {
			
			
			BufferedReader r = new BufferedReader( new FileReader(logFile) );
			try {
				int fileIdx = 1;
				int cbeBlocks = 0;
				
				File splittedLog = new File( logFile.getAbsolutePath()+".split" );
				BufferedWriter bw = new BufferedWriter(new FileWriter( splittedLog ) );
				
				String line;
				
				try {
					while ( ( line = r.readLine() ) != null ){
						bw.write(line);
						bw.newLine();
						if ( line.startsWith("</CommonBaseEvent>") ){
							cbeBlocks++;
							
							if ( cbeBlocks > bceBlockLimit ){
								cbeBlocks = 0;
								
								bw.close();
								
								
								
								try {
									
									
									anomaliesSize += processSplittedFileAndSaveAnomalies( splittedLog, fileIdx, bctHome );
									
									
									fileIdx++;
									
									
									Runtime.getRuntime().gc();
									
								} catch (CBEBctViolationsLogLoaderException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
								
								
								
								Runtime rt = Runtime.getRuntime();
								System.out.println("FREE max:"+rt.maxMemory()+" free:"+rt.freeMemory()+" total:"+rt.totalMemory());
								
								bw = new BufferedWriter(new FileWriter( splittedLog ) );
							}
						}
						
					}
					
					bw.close();
					anomaliesSize += processSplittedFileAndSaveAnomalies( splittedLog, fileIdx, bctHome );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CBEBctViolationsLogLoaderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						r.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return anomaliesSize;
	}



	private int processSplittedFileAndSaveAnomalies(File splittedLog, int fileIdx, File bctHome) throws CBEBctViolationsLogLoaderException, FileNotFoundException {
		

		List<BctModelViolation> currentAnomalies = AssertionAnomaliesUtil.retrieveTestAnomalies(splittedLog);
		
		System.out.println("CURRENT-ANOMALIES: "+currentAnomalies.size());
		List<BctModelViolation> filteredAnomalies = filterAnomaliesByCoverage(currentAnomalies, bctHome);
		System.out.println("FILTERED-ANOMALIES: "+currentAnomalies.size());
		
		List<BctModelViolation> anomalies = new ArrayList<BctModelViolation>();
		for ( BctModelViolation  viol : filteredAnomalies ){
			if ( viol instanceof BctIOModelViolation ){
				((BctIOModelViolation) viol).deleteParameters();
				
				anomalies.add(viol);
			}
		}
		
		System.out.println("ANOMALIES "+anomalies.size());
		
		int anomaliesSize = anomalies.size();
	
		System.out.println("WRITING FILE "+fileIdx);
		serializeAnomalies( anomalies, bctHome, "anomalies.ser."+fileIdx );
		
		return anomaliesSize;
	}

	private void serializeAnomalies(List<BctModelViolation> anomalies,
			File bctHome, String destFileName) {
		
		File serializedFile = new File( bctHome, destFileName );

		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( serializedFile ) );
			oos.writeObject( anomalies );
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<BctModelViolation> loadAnomalies( File serializedFile ) {
		System.out.println("LOADING "+serializedFile.getAbsolutePath());
		List<BctModelViolation> anomalies = null;
		
		

		try {
			ObjectInputStream oos = new ObjectInputStream( new FileInputStream( serializedFile ) );
			anomalies = (List<BctModelViolation>) oos.readObject();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return anomalies;
	}

	private enum FILTERING { ALL, VerifiedByTests, InvokedByTests, Coverage, CoverageLines, CoverageLinesPercentage, CoveragePercentage }
	
	public void identifyAnoamlousAssertions(final MonitoringConfiguration mc,
			List<BctModelViolation> testAnomalies, Set<String> testClasses,
			boolean acceptAllAnomalies, FILTERING filteirng )
			throws ConfigurationFilesManagerException, IOException {
		
		String suffix;
		if ( acceptAllAnomalies ){
			suffix = "allAnomalies";
		} else {
			suffix = "returnAnomaliesOnly";
		}
		
		File bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
		
		if ( filteirng == FILTERING.VerifiedByTests ){
			suffix += "_checkedByTestsOnly";
			
			File coveredMethodsFile = new File( bctHome, "verifiedMethods.txt" );
			List<String> coveredMethods = FileUtil.getLines(coveredMethodsFile);
			
			Set<String> covered = new HashSet<String>();
			for ( String meth : coveredMethods ){
				int parStart = meth.indexOf('(');
				
				String methName = meth.substring(0, parStart);
				String methSig = meth.substring(parStart);
				covered.add(methName+"("+methSig+")");
			}
			
			List<BctModelViolation> filteredAnomalies = new ArrayList<>();
			for( BctModelViolation v : testAnomalies ){
				if ( v instanceof BctIOModelViolation ){
					BctIOModelViolation ioV = (BctIOModelViolation) v;
					if ( covered.contains( ioV.getViolatedMethod() ) ){
						filteredAnomalies.add(v);
					}	
				}
				
			}
			
			testAnomalies = filteredAnomalies;
		} else if ( filteirng == FILTERING.InvokedByTests ){
			suffix += "_executedByTestsOnly";
			
			File coveredMethodsFile = new File( bctHome, "TestedMethods.txt" );
			
			BufferedReader br = new BufferedReader(new FileReader(coveredMethodsFile));
			String line;
			Set<String> covered = new HashSet<String>();
			while ( ( line = br.readLine() ) != null ){
				int indexOf = line.indexOf('\t');
				String method;
				if ( indexOf > 0 ){
					method = line.substring(0, indexOf);
				} else  {
					method = line;
				}
				covered.add(method);
			}
			
			
			
			List<BctModelViolation> filteredAnomalies = new ArrayList<>();
			for( BctModelViolation v : testAnomalies ){
				if ( v instanceof BctIOModelViolation ){
					BctIOModelViolation ioV = (BctIOModelViolation) v;
					String vm = ioV.getViolatedMethod();
					if ( covered.contains( vm ) ){
						filteredAnomalies.add(v);
					}	
				}
				
			}
			
			testAnomalies = filteredAnomalies;
		} else if ( filteirng == FILTERING.Coverage ){
			suffix += "_coverage";
			
			File coveredInstructionsFile = new File( bctHome, "bct.coverage.txt" );
			List<String> lines = FileUtil.getLines(coveredInstructionsFile);
			
			HashSet<String> coveredInstructions = new HashSet<String>();
			
			for ( String fileCovLine : lines ){
				StringBuffer sb = new StringBuffer();
				for ( String instrKey : fileCovLine.split(",") ){
					if ( !instrKey.contains(".-1.") ){
						sb.append(instrKey);
					}
				}
				coveredInstructions.add(sb.toString());
			}
			
			
			List<BctModelViolation> filteredAnomalies = new ArrayList<>();
			for( BctModelViolation v : testAnomalies ){
				if ( v instanceof BctIOModelViolation ){
					BctIOModelViolation ioV = (BctIOModelViolation) v;
					if ( allInstructionsAlreadyCovered( ioV, coveredInstructions, false ) ){
						filteredAnomalies.add(v);	
					}
				}
				
			}
			
			testAnomalies = filteredAnomalies;
			
		} else if ( filteirng == FILTERING.CoverageLines ){
			suffix += "_coverageLines";
			
			File coveredInstructionsFile = new File( bctHome, "bct.coverage.txt" );
			List<String> lines = FileUtil.getLines(coveredInstructionsFile);
			
			HashSet<String> coveredInstructions = new HashSet<String>();
			
			
			for ( String fileCovLine : lines ){
				StringBuffer sb = new StringBuffer();
				for ( String instrKey : fileCovLine.split(",") ){
					if ( !instrKey.contains(".-1.") ){
						int dotPos = instrKey.lastIndexOf('.');
						sb.append(instrKey.substring(0,dotPos));
					}
				}
				coveredInstructions.add(sb.toString());
			}
			
			
			List<BctModelViolation> filteredAnomalies = new ArrayList<>();
			for( BctModelViolation v : testAnomalies ){
				if ( v instanceof BctIOModelViolation ){
					BctIOModelViolation ioV = (BctIOModelViolation) v;
					if ( allInstructionsAlreadyCovered( ioV, coveredInstructions, true ) ){
						filteredAnomalies.add(v);	
					}
				}
				
			}
			
			testAnomalies = filteredAnomalies;	
			
		} else if ( filteirng == FILTERING.CoverageLinesPercentage ){
			suffix += "_coverageLinesPerc";
			
			File coveredInstructionsFile = new File( bctHome, "bct.coverage.txt" );
			List<String> lines = FileUtil.getLines(coveredInstructionsFile);
			
			HashSet<Set<String>> coveredInstructions = new HashSet<Set<String>>();
			
			
			for ( String fileCovLine : lines ){
				if ( fileCovLine.isEmpty() ){
					continue;
				}
				
				HashSet<String> coveredByAnInvocation = new HashSet<String>();
				for ( String instrKey : fileCovLine.split(",") ){
					if ( !instrKey.contains(".-1.") ){
						int dotPos = instrKey.lastIndexOf('.');
						if ( dotPos < 0 ){
							System.out.println("DOT NOT FOUND "+dotPos);
						}
						coveredByAnInvocation.add(instrKey.substring(0,dotPos));
					}
				}
				coveredInstructions.add(coveredByAnInvocation);
			}
			
			
			List<BctModelViolation> filteredAnomalies = new ArrayList<>();
			for( BctModelViolation v : testAnomalies ){
				if ( v instanceof BctIOModelViolation ){
					BctIOModelViolation ioV = (BctIOModelViolation) v;
					if ( instructionsCovered( ioV, coveredInstructions, true, 1.0 ) ){
						filteredAnomalies.add(v);	
					}
				}
				
			}
			
			testAnomalies = filteredAnomalies;	
		} else if ( filteirng == FILTERING.CoveragePercentage ){
			suffix += "_coveragePerc";
			
			List<BctModelViolation> filteredAnomalies = filterAnomaliesByCoverage(
					testAnomalies, bctHome);
			
			testAnomalies = filteredAnomalies;	
		} else {
			suffix += "_default";
		}
		
		Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies = new HashMap<String, Map<String,List<BctIOModelViolation>>>();
		for( String testClass : testClasses ){
			testClassesAnomalies.put( testClass, processTestClass(testClass, testAnomalies, mc, acceptAllAnomalies ) );
		}

		

		

		final Map<String,Integer> anomaliesFrequency = calculateAnomaliesFrequency( testClassesAnomalies );

		final Map<String,Double> weightedAnomalies = assignWeightsToAnomalies(anomaliesFrequency);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(bctHome,"testAnomalies_"+suffix+".txt")));
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			try {
				printTestAssertionAnomalies(writer, testClassAnomalies, weightedAnomalies);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		writer.close();
		
		{
			Map<String,Double> weightedAssertions = weightByMaxCoverage(testClassesAnomalies, 0.0);
			File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+"_maxCoverage.txt");
			writeSortedAssertions(weightedAssertions, destFile);
		}
		
		{
			Map<String,Double> weightedAssertions = weightByCoverageFrequencyWeightedAverage( testClassesAnomalies, weightedAnomalies, 0.0, 0.5, 0.5 );
			File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+"_CovergageFrequencyWeightedAverage.txt");
			writeSortedAssertions(weightedAssertions, destFile);
		}
		
		{
			Map<String,Double> weightedAssertions = weightByMaxCoverageFrequencyWeightedAverage( testClassesAnomalies, weightedAnomalies, 0.0, 0.5, 0.5 );
			File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+"_MaxCovergageFrequencyWeightedAverage.txt");
			writeSortedAssertions(weightedAssertions, destFile);
		}
		
		{
			Map<String,Double> weightedAssertions = weightByExpCoverageFrequencyWeightedAverage( testClassesAnomalies, weightedAnomalies, 0.0, 0.5, 0.5 );
			File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+"_ExpCovergageFrequencyWeightedAverage.txt");
			writeSortedAssertions(weightedAssertions, destFile);
		}
		
		{
			Map<String,Double> weightedAssertions = weightByMaxExpCoverageFrequencyWeightedAverage( testClassesAnomalies, weightedAnomalies, 0.0, 0.5, 0.5 );
			File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+"_MaxExpCovergageFrequencyWeightedAverage.txt");
			writeSortedAssertions(weightedAssertions, destFile);
		}
		
		{
			Map<String,Double> weightedAssertions = weightByCoverageAndFrequency( testClassesAnomalies, weightedAnomalies, 0.0 );
			File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+"_covAndFrequency.txt");
			writeSortedAssertions(weightedAssertions, destFile);
		}
		
		Map<String,Double> weightedAssertions = weightByAnomaliesFrequency( testClassesAnomalies, weightedAnomalies, 0.0 );
		File destFile = new File(bctHome,"anomalousTestAssertions_"+suffix+".txt");
		writeSortedAssertions(weightedAssertions, destFile);
		
		Map<String,Double> singletonAnomalies = new HashMap<>();
		for ( Entry<String,Double> e : weightedAnomalies.entrySet() ){
			if ( e.getValue() == 1.0 ){
				singletonAnomalies.put(e.getKey(), e.getValue() );
			}
		}
		
		weightedAssertions = weightByAnomaliesFrequency( testClassesAnomalies, singletonAnomalies, 1.0 );
		destFile = new File(bctHome,"anomalousTestAssertions_singleton_"+suffix+".txt");
		writeSortedAssertions(weightedAssertions, destFile);
	}
	
	
	private Map<String, Double> weightByMaxExpCoverageFrequencyWeightedAverage(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			Map<String, Double> weightedAnomalies, double threshold, double wCoverage, double wFrequency) {

		
		
		HashMap<String, Double> anomaliesMap = new HashMap<String,Double>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				
				double weight = 0;
				
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					Double anomalyWeight = weightedAnomalies.get(anomalyKey);
					
					Double coverageWeight = getCoveragePercentage(anomaly);
					
					if ( anomalyWeight != null ){
						double wNew = calculateExponentialWeight(wCoverage, wFrequency, anomalyWeight, coverageWeight);
						
						if ( wNew > weight ){
							weight = wNew;
						}
					}
				}
				
				if ( weight >= threshold ){
					anomaliesMap.put(assertion, weight);
				}
			}
		}
	
		return anomaliesMap;
	}

	public double calculateExponentialWeight(double wCoverage,
			double wFrequency, Double anomalyWeight, Double coverageWeight) {
//		double wNew =  wFrequency * Math.exp(anomalyWeight) + wCoverage * Math.exp(coverageWeight);
		
		double wNew =  wFrequency * Math.pow(anomalyWeight,2) + wCoverage * Math.pow(coverageWeight,2);
		
		return wNew;
	}
	
	
	private Map<String, Double> weightByMaxCoverageFrequencyWeightedAverage(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			Map<String, Double> weightedAnomalies, double threshold, double wCoverage, double wFrequency) {

		Map<String,Double> weightedByCoverage= weightByMaxCoverage(testClassesAnomalies, 0.0);
		
		HashMap<String, Double> anomaliesMap = new HashMap<String,Double>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				
				double weight = 0;
				
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					Double anomalyWeight = weightedAnomalies.get(anomalyKey);
					
					Double coverageWeight = getCoveragePercentage(anomaly);
					
					if ( anomalyWeight != null ){
						double wNew = wFrequency * anomalyWeight + wCoverage * coverageWeight;
						
						if ( wNew > weight ){
							weight = wNew;
						}
					}
				}
				
				if ( weight >= threshold ){
					anomaliesMap.put(assertion, weight);
				}
			}
		}
		
		return anomaliesMap;

	}
	
	
	private Map<String, Double> weightByExpCoverageFrequencyWeightedAverage(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			Map<String, Double> weightedAnomalies, double threshold, double wCoverage, double wFrequency) {

		
		HashMap<String, Double> anomaliesMap = new HashMap<String,Double>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				
				double weight = 0;
				
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					Double anomalyWeight = weightedAnomalies.get(anomalyKey);
					
					Double coverageWeight = getCoveragePercentage(anomaly);
					
					if ( anomalyWeight != null ){
						weight += calculateExponentialWeight(wCoverage, wFrequency, anomalyWeight, coverageWeight);
						
//						if ( anomalyWeight > weight ){
//							weight = anomalyWeight;
//						}
					}
				}
				
				if ( weight >= threshold ){
					anomaliesMap.put(assertion, weight);
				}
			}
		}
		
		return anomaliesMap;

	}
	
	
	private Map<String, Double> weightByCoverageFrequencyWeightedAverage(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			Map<String, Double> weightedAnomalies, double threshold, double wCoverage, double wFrequency) {

		Map<String,Double> weightedByCoverage= weightByMaxCoverage(testClassesAnomalies, 0.0);
		
		HashMap<String, Double> anomaliesMap = new HashMap<String,Double>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				
				double weight = 0;
				
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					Double anomalyWeight = weightedAnomalies.get(anomalyKey);
					
					Double coverageWeight = getCoveragePercentage(anomaly);
					
					if ( anomalyWeight != null ){
						weight += wFrequency * anomalyWeight + wCoverage * coverageWeight;
						
//						if ( anomalyWeight > weight ){
//							weight = anomalyWeight;
//						}
					}
				}
				
				if ( weight >= threshold ){
					anomaliesMap.put(assertion, weight);
				}
			}
		}
		
		return anomaliesMap;

	}

	private Map<String, Double> weightByCoverageAndFrequency(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			Map<String, Double> weightedAnomalies, double d) {
		
		Map<String,Double> weightedByFrequency = weightByAnomaliesFrequency( testClassesAnomalies, weightedAnomalies, 0.0 );
		Map<String,Double> weightedByCoverage= weightByMaxCoverage(testClassesAnomalies, 0.0);
		
		Map<String,Double> resMap = new HashMap<>();
		for( Entry<String, Double> assertionFreqEntry : weightedByFrequency.entrySet() ){
			Double freq = assertionFreqEntry.getValue();
			Double cov = weightedByCoverage.get(assertionFreqEntry.getKey());
			
			double res = cov * freq;
			
			resMap.put(assertionFreqEntry.getKey(), res);
		}
		
		return resMap;
	}

	public double getCoveragePercentage( BctIOModelViolation ioV ){
		if ( coveredInstructions == null ){
			return 0;
		}
		
		HashSet<String> coveredByViol = new HashSet<String>();
		for ( String key : ioV.getParametersMap().keySet() ){
			if ( key.startsWith("coverage_") ){
				String instr = key.substring(9);
				if ( ! instr.contains(".-1.") ){
					
					coveredByViol.add(instr);
				}
			}
		}
		
		double max = 0.0;
		for ( Set<String> instructionsCoveredByAnInvocation : coveredInstructions ) {
			double coveredFraction = extractCoveragePercentage( coveredByViol, instructionsCoveredByAnInvocation);
			if ( coveredFraction > max ){
				//System.out.println("MAX: "+coveredFraction);
				max = coveredFraction;
				
				
			}
		}
		
		for ( Set<String> instructionsCoveredByAnInvocation : coveredInstructions ) {
			double coveredFraction = extractCoveragePercentage( coveredByViol, instructionsCoveredByAnInvocation);
			if ( coveredFraction == max ){
				//System.out.println("BEST COVERAGE ");
				
				
				Set<String> common = new HashSet<>();
				common.addAll(coveredByViol);
				common.retainAll(instructionsCoveredByAnInvocation);
				
				Set<String> newBehavior = new HashSet<String>();
				newBehavior.addAll(coveredByViol);
				newBehavior.removeAll(instructionsCoveredByAnInvocation);
				
				Set<String> missing = new HashSet<String>();
				missing.addAll(instructionsCoveredByAnInvocation);
				missing.removeAll(coveredByViol);
				
				//System.out.println("MISSING : "+missing);
				//System.out.println("NEW-BEHAV : "+newBehavior);
				//System.out.println("COMMON : "+common);
			}
		}
		
		
		return max;
		
	}

	public List<BctModelViolation> filterAnomaliesByCoverage(
			List<BctModelViolation> testAnomalies, File bctHome)
			throws FileNotFoundException {
		
		if ( coveredInstructions == null ){
			File coveredInstructionsFile = new File( bctHome, "bct.coverage.txt" );
			List<String> lines = FileUtil.getLines(coveredInstructionsFile);



			coveredInstructions = new HashSet<Set<String>>();


			for ( String fileCovLine : lines ){
				HashSet<String> coveredByAnInvocation = new HashSet<String>();
				for ( String instrKey : fileCovLine.split(",") ){
					if ( !instrKey.contains(".-1.") ){
						coveredByAnInvocation.add(instrKey);
					}
				}
				coveredInstructions.add(coveredByAnInvocation);
			}
		}
		
		
		List<BctModelViolation> filteredAnomalies = new ArrayList<>();
		for( BctModelViolation v : testAnomalies ){
			if ( v instanceof BctIOModelViolation ){
				BctIOModelViolation ioV = (BctIOModelViolation) v;
				if ( instructionsCovered( ioV, coveredInstructions, false, 0.75 ) ){
					filteredAnomalies.add(v);	
				}
			}
			
		}
		return filteredAnomalies;
	}

	private boolean instructionsCovered(BctIOModelViolation ioV,
			HashSet<Set<String>> coveredInstructions, boolean workWithLines, double threshold) {
		HashSet<String> coveredByViol = new HashSet<String>();
		for ( String key : ioV.getParametersMap().keySet() ){
			if ( key.startsWith("coverage_") ){
				String instr = key.substring(9);
				if ( ! instr.contains(".-1.") ){
					if ( workWithLines ) {
						int dotPos = instr.lastIndexOf('.');
						instr = instr.substring(0, dotPos);
					}
					coveredByViol.add(instr);
				}
			}
		}
		
		double max = 0.0;
		for ( Set<String> instructionsCoveredByAnInvocation : coveredInstructions ) {
			double coveredFraction = extractCoveragePercentage( coveredByViol, instructionsCoveredByAnInvocation);
			if ( coveredFraction > max ){
				//System.out.println("MAX: "+coveredFraction);
				max = coveredFraction;
				
				if ( max >= threshold ){
					return true;
				}
			}
		}
		
		
		
		
		return max >= threshold;
	}

	public double extractCoveragePercentage(
			HashSet<String> coveredByViol,
			Set<String> coveredByPassingManual) {
		
		HashSet<String> coveredByEvoTestsOnly = new HashSet<>();
		coveredByEvoTestsOnly.addAll(coveredByViol);
		coveredByEvoTestsOnly.removeAll(coveredByPassingManual);
		
		
		
		HashSet<String> coveredByManualTestsOnly = new HashSet<>();
		coveredByManualTestsOnly.addAll(coveredByPassingManual);
		coveredByManualTestsOnly.removeAll(coveredByViol);
		
		
		//System.out.println("MANUAL "+coveredByManualTestsOnly);
		//System.out.println("EVOS "+coveredByEvoTestsOnly);
		
		int commonInstructions = coveredByViol.size()-coveredByEvoTestsOnly.size();
		
		int totalInstructions = commonInstructions + coveredByEvoTestsOnly.size() + coveredByManualTestsOnly.size();
		
		double coveredFraction = (double)commonInstructions/(double)totalInstructions;
		return coveredFraction;
	}

	private boolean allInstructionsAlreadyCovered(BctIOModelViolation ioV,
			HashSet<String> coveredInstructions, boolean workWithLines) {
		
		HashSet<String> coveredByViol = new HashSet<String>();
		for ( String key : ioV.getParametersMap().keySet() ){
			if ( key.startsWith("coverage_") ){
				String instr = key.substring(9);
				if ( ! instr.contains(".-1.") ){
					if ( workWithLines ) {
						int dotPos = instr.lastIndexOf('.');
						instr = instr.substring(0, dotPos);
					}
					coveredByViol.add(instr);
				}
			}
		}
		
		ArrayList<String> cov = new ArrayList<String>();
		cov.addAll(coveredByViol);
		
		Collections.sort(cov);
		
		StringBuffer sb = new StringBuffer();
		for( String instr : cov ){
			sb.append( instr );
			sb.append(',');
		}
		
		System.out.println("TO_FIND : "+sb.toString());
		
		return coveredInstructions.contains(sb.toString());
		
	}

	public void writeSortedAssertions(
			final Map<String, Double> weightedAssertions,
			File destFile) throws IOException {
		
		ArrayList<String> ass = new ArrayList<String>();
		ass.addAll(weightedAssertions.keySet());
		Collections.sort(ass, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return -1 * Double.compare(weightedAssertions.get(o1),weightedAssertions.get(o2));
			}
		});
		
		BufferedWriter writer;
		String SEP = "\t";
		writer = new BufferedWriter(new FileWriter(destFile));
		for( String assertion : ass ){
			writer.append(assertion+SEP+weightedAssertions.get(assertion));
			writer.append("\n");
		}
		writer.close();
	}


	private Map<String, Double> weightByMaxCoverage(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			double threshold) {
		HashMap<String, Double> anomaliesMap = new HashMap<String,Double>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				
				double weight = 0;
				
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					Double anomalyWeight = getCoveragePercentage(anomaly);
					
					if ( anomalyWeight >= threshold && anomalyWeight > weight ){
						weight = anomalyWeight;
						
//						if ( anomalyWeight > weight ){
//							weight = anomalyWeight;
//						}
					}
				}
				
				if ( weight > 0 ){
					anomaliesMap.put(assertion, weight);
				}
			}
		}
		
		return anomaliesMap;
	}
	
	
	private Map<String, Double> weightByAnomaliesFrequency(
			Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies,
			Map<String, Double> weightedAnomalies, double threshold) {
		HashMap<String, Double> anomaliesMap = new HashMap<String,Double>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				
				double weight = 0;
				
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					Double anomalyWeight = weightedAnomalies.get(anomalyKey);
					
					if ( anomalyWeight != null && anomalyWeight >= threshold ){
						weight += anomalyWeight;
						
//						if ( anomalyWeight > weight ){
//							weight = anomalyWeight;
//						}
					}
				}
				
				if ( weight > 0 ){
					anomaliesMap.put(assertion, weight);
				}
			}
		}
		
		return anomaliesMap;
	}

	private Map<String, Double> assignWeightsToAnomalies(Map<String, Integer> anomaliesOccurencies) {
		Map<String, Double> anomaliesFrequency = new HashMap<String, Double>();
		
		for ( Entry<String, Integer> e : anomaliesOccurencies.entrySet() ){
			anomaliesFrequency.put(e.getKey(), 1.0/e.getValue());	
		}
		
		return anomaliesFrequency;
	}

	private Map<String, Integer> calculateAnomaliesFrequency(Map<String, Map<String, List<BctIOModelViolation>>> testClassesAnomalies) {
		
		HashMap<String, Integer> anomaliesMap = new HashMap<String,Integer>();
		
		for (  Map<String, List<BctIOModelViolation>> testClassAnomalies : testClassesAnomalies.values() ){
			for ( Entry<String, List<BctIOModelViolation>> assertionEntry : testClassAnomalies.entrySet() ){
				String assertion = assertionEntry.getKey();
				for ( BctIOModelViolation anomaly : assertionEntry.getValue() ){
					String anomalyKey = anomaly.getDescriptiveKey();
					
					addAnomalyKey(anomaliesMap, anomalyKey);
				}
			}
		}
		
		return anomaliesMap;
	}

	private void addAnomalyKey(HashMap<String, Integer> assertionsMap, String anomalyKey) {
		Integer value = assertionsMap.get(anomalyKey);
		if ( value == null ){
			value = 1;
		} else  {
			value = value+1;
		}
		
		assertionsMap.put(anomalyKey, value);
	}

	private Map<String, List<BctIOModelViolation>> processTestClass(String testClassName,
			List<BctModelViolation> testAnomalies, MonitoringConfiguration mc, boolean acceptAllViolations) {


		try {
			ICompilationUnit testCompilationUnit = JavaResourcesUtil.getCompilationUnit(mc, testClassName);

			ASTParser parser = ASTParser.newParser(AST.JLS4); 
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(testCompilationUnit); // set source
			parser.setResolveBindings(true); // we need bindings later on

			CompilationUnit ast = (CompilationUnit) parser.createAST(null /* IProgressMonitor */); // parse

			TestAnomaliesASTVisitor testAstVisitor = new TestAnomaliesASTVisitor(testClassName, testAnomalies, ast, acceptAllViolations );
			ast.accept(testAstVisitor);


			Map<String, List<BctIOModelViolation>> anomalies = testAstVisitor.getAssertionAnomalies();

			return anomalies;


		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void printTestAssertionAnomalies( BufferedWriter w, Map<String, List<BctIOModelViolation>> anomalies, Map<String, Double> weightedAnomalies) throws IOException {
		String SEP = "\t";

		Set<String> processed = new HashSet<String>();

		for (Entry<String, List<BctIOModelViolation>> e : anomalies.entrySet() ){
			String testAssertion = e.getKey();
			for( BctIOModelViolation v : e.getValue() ){
				String key = testAssertion+SEP+v.getViolation();

				if ( processed.contains(key) ){
					continue;
				}
				processed.add(key);

				w.append(testAssertion+SEP+v.getViolation()+SEP+v.getViolatedModel()+SEP+v.getStackTrace()[1]+SEP+getCoveragePercentage(v)+SEP+weightedAnomalies.get(v.getDescriptiveKey()));	
				w.newLine();
			}

		}
	}

	public Set<String> extractTestCasesWithViolations(
			List<BctModelViolation> testAnomalies) {
		Set<String> testCases = new HashSet<String>();
		for( BctModelViolation testAnomaly : testAnomalies){
			testCases.addAll( Arrays.asList( testAnomaly.getCurrentTests() ) );
		}
		return testCases;
	}



	private void setTracesToExclude(MonitoringConfiguration mc, InferModelWizardResult result) throws ConfigurationFilesManagerException {


		boolean skipFailingTests = result.getSkipFailingTests();
		boolean skipFailingActions = result.getSkipFailingActions();
		boolean skipFailingProcesses = result.getSkipFailingProcesses();

		ModelInference.setupInferenceFilters(mc, skipFailingTests, skipFailingActions, skipFailingProcesses);
		//
	}



	public void selectionChanged(IAction action, ISelection selection) {
	}


}
