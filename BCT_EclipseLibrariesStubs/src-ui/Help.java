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


import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import console.AnomaliesIdentifier;


public class Help {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("To DEBUG a regression fault:" +
//						"\n\t1) execute java "+GenerateDebuggingScripts.class.getCanonicalName()+" " +
//						"\n\t2) run the original program" +
//						"\n\t3) run the modified program" +
//						"\n\t4) execute java "+Debug.class.getCanonicalName()+" " +
//						
//						
//						"\n\n\n" +
//						"\nTo IDENTIFY runtime mibehaviors of a modified software" +
//						"\n\t1) execute java "+GenerateScripts.class.getCanonicalName()+" " +
//						"\n\t2) run the original program" +
//						"\n\t3) run the modified program" +
//						"\n\t4) execute java "+IdentifyAnomalies.class.getCanonicalName()+" " +		
//				
//"\n\n\n" +
//"\nTo IDENTIFY potential regressions in a modified software" +
//"\n\t1) execute java "+GenerateDebuggingScripts.class.getCanonicalName()+" " +
//"\n\t2) run the original program" +
//"\n\t3) run the test cases that have been modified for the upgraded program" +
//"\n\t4) execute java "+IdentifyPropertyViolations.class.getCanonicalName()+" " +	
//				
//				"\n" +
//				"\n" +
//				"\n" +
//				"System properties that tune BCT execution:\n" +
//				"\t"+CBCMRegressionsDetector.BCT_GOTO_PROGRAM+"	name of the executable generated by goto-cc (e.g. program.exe)\n" +
//				"\t"+CBCMRegressionsDetector.BCT_COMPILE_COMMAND+" name of the command to execute to compile the program with goto-cc\n "+
////				"\t"+RegressionsDetector.BCT_SKIP_ANOMALIES_IDENTIFIER+" skip model inference (useful if already done)\n"+		
//				"\t"+CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK+" true/false used to skip checking of anomalies violated by valid executions of the modified program\n" +
//						"");
		
		showAdditionalOptions();
	}

	private static void showAdditionalOptions() {
		InputStream s = AnomaliesIdentifier.class.getClassLoader().getResourceAsStream("bct.system.properties.desc.txt");

		BufferedReader reader = new BufferedReader(new InputStreamReader(s));
		String line;
		
		Map<String,List<String>> options = new HashMap<String,List<String>>();
		
		
		
		try {
			
			List<String> opts = null;
			while( ( line = reader.readLine() ) != null ){
				line = line.trim();
				if ( line.startsWith("///PROPERTIES-DESCRIPTION:") ){
					line = line.substring(27);
					
					opts = options.get(line);
					if ( opts == null ){
						opts = new ArrayList<>();
						options.put(line, opts);
					}
	
				} else if ( line.startsWith("///") ){
					String next = reader.readLine().trim();
					String data = "\t\t"+next+"\t"+line+"\n";
					
					if ( opts == null ){
						System.out.println(line);
					}
					
					opts.add(data);
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> keys = new ArrayList<>();
		keys.addAll(options.keySet());
		Collections.sort(keys);
		
		System.out.println("System options used to configure BCT/RADAR/VART." +
				"\nOptions should be specified as java properties, e.g." +
				"\njava -Dbct.comparePointers=true VART_Run <prj>");
		
		for ( String key : keys ){
			System.out.println("\n\n\t"+key+"\n");

			for ( String opt : options.get(key) ){
				System.out.println(opt);
			}
		}
		

	}

}
