package console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import console.ProcessAnomalousAssertions.FolderResult;


import util.FileUtil;

public class ProcessAnomalousAssertions {
	
	public static class FolderResult {

		private int[] truePositives;
		private int[] falsePositives;
		private int[] falseNegatives;

		public FolderResult(int[] truePositives, int[] falsePositives,
				int[] falseNegatives) {
			this.truePositives = truePositives;
			this.falsePositives = falsePositives;
			this.falseNegatives = falseNegatives;
		}

	}
	
	
	static String fileNames[] = { 
		
		"anomalousTestAssertions_singleton_allAnomalies_checkedByTestsOnly.txt",
		"anomalousTestAssertions_allAnomalies_checkedByTestsOnly.txt",
		"anomalousTestAssertions_singleton_returnAnomaliesOnly_checkedByTestsOnly.txt",
		"anomalousTestAssertions_singleton_returnAnomaliesOnly_coveragePerc.txt",
		"anomalousTestAssertions_returnAnomaliesOnly_checkedByTestsOnly.txt",
		"anomalousTestAssertions_allAnomalies.txt",
		"anomalousTestAssertions_allAnomalies_executedByTestsOnly.txt",
		"anomalousTestAssertions_returnAnomaliesOnly_executedByTestsOnly.txt",
		"anomalousTestAssertions_singleton_allAnomalies_coveragePerc.txt",
		"anomalousTestAssertions_returnAnomaliesOnly_coveragePerc.txt",
		"anomalousTestAssertions_allAnomalies_coveragePerc.txt",
		"anomalousTestAssertions_returnAnomaliesOnly_coverageLinesPerc.txt",
		"anomalousTestAssertions_singleton_returnAnomaliesOnly_coverageLinesPerc.txt",
		"anomalousTestAssertions_allAnomalies_coverageLinesPerc.txt",
		"anomalousTestAssertions_singleton_returnAnomaliesOnly_executedByTestsOnly.txt",
		"anomalousTestAssertions_singleton_allAnomalies_executedByTestsOnly.txt",
		"anomalousTestAssertions_singleton_returnAnomaliesOnly.txt",
		"anomalousTestAssertions_singleton_allAnomalies.txt",
		"anomalousTestAssertions_singleton_allAnomalies_coverageLinesPerc.txt",
		"anomalousTestAssertions_returnAnomaliesOnly.txt"
		
		};

	static Map<String,Integer> namesMap = new HashMap<>();
	
	
	static {
		for ( String fileName : fileNames ){
			namesMap.put(fileName,namesMap.size());
		}
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		List<File> filesToProcess = new ArrayList<>();
		
		for ( String arg : args ){
			filesToProcess.add(new File(arg));
		}
		
		ProcessAnomalousAssertions p = new ProcessAnomalousAssertions();
		p.process(filesToProcess);
	}

	private void process(List<File> filesToProcess) throws FileNotFoundException {
		
		for ( String fileName : fileNames ){
			System.out.print( fileName );
			System.out.print( "," );
		}
		System.out.println();
		
		
		for( File folder : filesToProcess ){
			FolderResult folderRes = processFolder ( folder ); 
			
			int[] truePositives = folderRes.truePositives;
			int[] falsePositives = folderRes.falsePositives;
			
			System.out.print(folder.getName() +",");
			for ( int i = 0; i < truePositives.length; i++ ){
				System.out.print( truePositives[i] +  "/" + falsePositives[i]+"/"+folderRes.falseNegatives[i]+",");
			}
			
			
			System.out.println("");
		}
	}

	private FolderResult processFolder(File folder) throws FileNotFoundException {
		File toFind = new File(folder,"toFind.txt");
		
		HashSet<String> lines = new HashSet<>();
		lines.addAll( FileUtil.getLines(toFind) );
		
		int[] truePositives = new int[fileNames.length];
		int[] falsePositives = new int[fileNames.length];
		int[] falseNegatives = new int[fileNames.length];
		
		for ( String fileName : fileNames ){
			addResultsForFile(folder, lines, truePositives, falsePositives, falseNegatives, fileName);
		}
		return new FolderResult(truePositives,falsePositives,falseNegatives);
	}

	public void addResultsForFile(File folder, HashSet<String> faultyAssertions,
			int[] truePositives, int[] falsePositives, int[] falseNegatives, String fileName)
			throws FileNotFoundException {
		int TP = 0;
		int FP = 0;
		
		List<String> resultLines = FileUtil.getLines(new File( folder, fileName  ) );
		for ( String resultLine : resultLines ){
			int pos = resultLine.indexOf('\t');
			String testAssertion = resultLine.substring(0,pos);
			
			if ( faultyAssertions.contains(testAssertion) ){
				TP++;
			} else {
				FP++;
			}
		}
		
		
		Integer indexPos = namesMap.get(fileName);
		truePositives[indexPos] = TP;
		falsePositives[indexPos] = FP;
		falseNegatives[indexPos] = faultyAssertions.size()-TP;
	}

}
