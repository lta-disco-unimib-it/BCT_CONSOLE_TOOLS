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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.vart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;
import util.FileUtil;
import util.StringUtils;

public class CorrespondingLinesFinder {

	private HashMap<String, int[]> filePositions;

	public CorrespondingLinesFinder(File monitoredFunctionsFile, File srcFolder, File injectedFolder, File injectedAssertionsFile, File injectedAssertionsFileWithLines) {
		try {
			List<String> assertions = FileUtil.getLines(injectedAssertionsFile);
			List<String> assertionLines = FileUtil.getLines(injectedAssertionsFileWithLines);

			Map<String, FunctionMonitoringData> monitoredFunctions = FunctionMonitoringDataSerializer.load(monitoredFunctionsFile);

			filePositions = new HashMap<String,int[]>();

			for ( int i = 0; i < assertions.size(); i++ ){
				String assertion = assertions.get(i);
				String assertLine = assertionLines.get(i);



				String[] assertionSplit = assertion.split("\t");
				String[] assertionLinesSplit = assertLine.split("\t");

				int origLine;
				if ( StringUtils.isNumeric(assertionSplit[1]) ){
					origLine = Integer.valueOf(assertionSplit[1]);
				} else {
					FunctionMonitoringData function = monitoredFunctions.get(assertionSplit[1]);
					origLine = function.getLastSourceLine();
				}

				String file = assertionSplit[0];

				int[] pos = filePositions.get(file);

				if ( pos == null ){
					File f = new File( injectedFolder, file );
					int size = FileUtil.getLines(f).size();

					pos = new int[size];

					filePositions.put(file, pos);
				}

				int newLine = -1;
				if ( StringUtils.isNumeric(assertionLinesSplit[1]) ){
					newLine = Integer.valueOf(assertionLinesSplit[1]);
				} else {
					continue;
				}

				int delta = origLine-newLine;




				System.out.println("DELTA: "+newLine+" "+delta);
				pos[newLine]=delta;
			}


			for ( Entry<String, int[]>  e : filePositions.entrySet() ){
				String file = e.getKey();

				int[] pos = e.getValue();


				int lastDelta = 0;
				int prevLine = 0;

				for ( int i = 0; i < pos.length; i++ ){
					int val = pos[i];
					if ( val < 0 ){
						lastDelta=val;
						pos[i]=(i)+lastDelta;
					} else {
						int correspondingLine = (i)+lastDelta-1;

						pos[i]=correspondingLine;
						prevLine=pos[i];

					}
				}

				save( srcFolder, injectedFolder, file, pos );
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void save(File srcFolder, File injectedFolder, String file, int[] pos) {
		try {
			BufferedWriter bw = new BufferedWriter( new FileWriter ( new File(injectedFolder,file+".mapper") ) );

			bw.write(new File( srcFolder, file).getAbsolutePath() );
			bw.newLine();

			for ( int i = 1; i < pos.length; i++ ){
				bw.append(String.valueOf(pos[i]));
				bw.newLine();
			}

			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCorrespondingLine( String file, int injectedLine ){
		int[] pos = filePositions.get(file);
		return pos[injectedLine];
	}

	public static void main(String args[]){
		{
			String ws = "/mnt/data/VART/WS/";
			String base = ws+"_VART/BCT_DATA/Store/";
			File functions = new File ( base + "conf/files/scripts/monitoredFunctions.modified.ser");
			File srcFolder = new File( ws+"StoreV2" );
			File injectedFolder = new File( base + "VART.SRC.MODIFIED" );
			File injectedAssertionsFile = new File(base+"VART/assertions-V1.txt.trueProperties.filtered.injected");
			File injectedAssertionsFileWithLines = new File(base+"VART/assertions-V1.txt.trueProperties.filtered.injected.lines");

			CorrespondingLinesFinder f = new CorrespondingLinesFinder(functions, srcFolder, injectedFolder, injectedAssertionsFile, injectedAssertionsFileWithLines);


			test(f,28,23);
			test(f,29,23);
			test(f,31,23);
			test(f,39,28);
			test(f,34,26);
		}

		{
			String ws = "/mnt/data/VART/WSneon/";
			String base = ws+"VART/BCT_DATA/Analysis/";
			File functions = new File ( base + "conf/files/scripts/monitoredFunctions.modified.ser");
			File srcFolder = new File( ws+"StoreV2" );
			File injectedFolder = new File( base + "VART.SRC.MODIFIED" );
			File injectedAssertionsFile = new File(base+"VART/assertions-V1.txt.trueProperties.filtered.injected");
			File injectedAssertionsFileWithLines = new File(base+"VART/assertions-V1.txt.trueProperties.filtered.injected.lines");
			CorrespondingLinesFinder f = new CorrespondingLinesFinder(functions, srcFolder, injectedFolder, injectedAssertionsFile, injectedAssertionsFileWithLines);


			test(f,28,23);
			test(f,29,23);
			test(f,31,23);
			test(f,39,29);
			test(f,34,26);

			test(f,42,31);
			test(f,43,31);
			test(f,44,31);

			//test(f,45,32);
			test(f,46,32);
			test(f,47,32);
			test(f,48,32);
			test(f,49,32);
		}

	}

	private static void test(CorrespondingLinesFinder f, int injectedLine, int originalLine ) {
		int res = f.getCorrespondingLine("src/store.c", injectedLine); 
		boolean comp = originalLine == res;
		System.out.println( comp ); 
		if (! comp ) {
			System.out.println("Injected line: "+injectedLine+" Expected original: "+originalLine+" found:"+res); 
		}
	}

}
