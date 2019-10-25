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
import it.unimib.disco.lta.alfa.logging.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConsoleHelper;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.FileUtil;
import util.ProcessRunner;
import console.ProjectSetup;
import cpp.gdb.EnvUtil;
import cpp.gdb.FunctionMonitoringData;
import cpp.gdb.FunctionMonitoringDataSerializer;

/**
 * This program monitors the base version of a software by running it multiple times,
 * one for each breakpoint of type 'BCT-POINT' declared in originalSoftware.gdb.config.txt.
 * 
 * It was built to allow monitoring on systems where GDB hangs if more than one breakpoint is monitored.
 * 
 *
 */
public class MonitorOneLineAtime {
	
	private static boolean original = true;

	public static void setOriginal(boolean original) {
		MonitorOneLineAtime.original = original;
	}

	/**
	 * @param args
	 * @throws CBEBctViolationsLogLoaderException CBEBctViolationsLogLoaderException
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, IOException, ClassNotFoundException {
		
		
		
		String projectDir = args[0];
		
		
		int maxExecutionTime = 0;
		
		
		
		String[] programToMonitor = Arrays.copyOfRange(args, 1, args.length);
		
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
		

		Map<String, FunctionMonitoringData> functionData = getFunctionMonitoringData(original, mc);
		
		File validTrace = getGdbScript(original, mc);
		
		List<LineBreakpoint> lineBreakPoints = extractLineBreakpoints ( functionData, validTrace );
		
		for ( LineBreakpoint lineBreakPoint : lineBreakPoints ){
			File tmpFolder = new File ( EnvUtil.getTmpFolderPath() );
			File tmpTrace = new File ( tmpFolder, "bct.gdb.script.tmp");
			
			System.out.println("TMP "+tmpTrace.getAbsolutePath());
			fillTmpTrace( tmpTrace, lineBreakPoint );
			
			String[] commandString = ConsoleHelper.createGdbCommandToExecute(tmpTrace);
			List<String> command = new ArrayList<String>(commandString.length + programToMonitor.length );
			
			for ( String cmd : commandString ){
				command.add(cmd);
			}
			
			for ( String cmd : programToMonitor ){
				command.add(cmd);
			}
			
			ProcessRunner.run(command, null, null, maxExecutionTime);
		}
		
	}

	private static void fillTmpTrace(File tmpTrace,
			LineBreakpoint lineBreakPoint) throws IOException {
		FileUtil.writeToTextFile(lineBreakPoint.getLines(), tmpTrace);
	}

	private static List<LineBreakpoint> extractLineBreakpoints(
			Map<String, FunctionMonitoringData> functionData, File validTrace) {
		
		List<LineBreakpoint> lineBreakpoints = new ArrayList<MonitorOneLineAtime.LineBreakpoint>();
		
		try {
			List<String> lines = FileUtil.getLines(validTrace);
			
			ArrayList<String> header = new ArrayList<String>();
			
			ArrayList<String> currentBreakpoint = null;
			
			boolean beginPart = true;
			boolean withinBreakpoint = false;
			
			
			List<String> footer = createDefaultFooter();
			String breakpointLocation = null;
			
			int curLine = 0;
			for ( String line : lines ){
				curLine++;
				if ( beginPart ){
					if ( line.startsWith("#breakpoint") ){
						Logger.info("Header ends at line "+curLine);
						beginPart = false;
						continue;
					}
					header.add(line);
				} else if ( withinBreakpoint ) {
					if ( line.startsWith("end") ){ //breakpoint end instruction
						Logger.info("Breakpoint ends at line "+curLine);
						currentBreakpoint.add(line);
						withinBreakpoint = false;
						LineBreakpoint lineBreakpoint = new LineBreakpoint(header, currentBreakpoint, footer);
						lineBreakpoints.add(lineBreakpoint);
					}  else if ( line.startsWith("b ") ){ //breakpoint address/line to set
						if ( line.contains("*") ){//breakpoint points to an address e.g. 'b *0x374526'
							line = "b "+breakpointLocation;
						}
						currentBreakpoint.add(line);
					} else { //any other breakpoint instruction
						currentBreakpoint.add(line);
					}
				} else {
					
					if ( line.startsWith("# code for ") ){ //breakpoint start
						if ( ! line.contains(" POINT ") ){
							continue;
						} else { //# code for POINT <func>:<lineNo>
							Logger.info("Breakpoint starts at line "+curLine);
							withinBreakpoint = true;
							
							line = line.trim();
							int functionAndLineStart = line.lastIndexOf(' ');
							int colons = line.lastIndexOf(':');
							
							String function = line.substring(functionAndLineStart, colons);
							Integer lineNo = Integer.valueOf( line.substring(colons+1) );
							
							FunctionMonitoringData thisFunc = functionData.get(function);
							if ( thisFunc != null ){
								breakpointLocation = thisFunc.getAbsoluteFile().getName()+":"+lineNo;
							} else {
								breakpointLocation = line;
							}
							currentBreakpoint = new ArrayList<String>();
							currentBreakpoint.add(line);
						}
					}
					
				}
				
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lineBreakpoints;
	}

	private static List<String> createDefaultFooter() {
		ArrayList<String> footer = new ArrayList<String>();
		footer.add( "echo !!!BCT-NEW-EXECUTION\\n" );
		footer.add( "run" );
		footer.add( "quit" );

		return footer;
	}

	public static class LineBreakpoint {
		List<String> header;
		List<String> breakPointInsructions;
		List<String> footer;
		
		public LineBreakpoint(List<String> header,
				List<String> breakPointInsructions, List<String> footer) {
			super();
			this.header = header;
			this.breakPointInsructions = breakPointInsructions;
			this.footer = footer;
		}

		public List<String> getLines() {
			int size = breakPointInsructions.size()+footer.size()+header.size();
			
			ArrayList<String> lines = new ArrayList<String>(size);
			lines.addAll(header);
			lines.addAll(breakPointInsructions);
			lines.addAll(footer);
			
			return lines;
		}

		
	}
	
	public static File getGdbScript(boolean original, MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException {
		File validTrace;
		if ( original ){
			validTrace = ConfigurationFilesManager.getOriginalSoftwareGdbMonitoringConfig(mc);
		} else {
			validTrace = ConfigurationFilesManager.getModifiedSoftwareGdbMonitoringConfig(mc);
		}
		return validTrace;
	}

	public static Map<String, FunctionMonitoringData> getFunctionMonitoringData(boolean original,
			MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException, FileNotFoundException,
			IOException, ClassNotFoundException {
		File functionMonitoringDataFile;
		if( original ){
			functionMonitoringDataFile = ConfigurationFilesManager.getMonitoredFunctionsDataFile(mc);
		} else {
			functionMonitoringDataFile = ConfigurationFilesManager.getMonitoredFunctionsDataFileModifiedVersion(mc);
		}
		Map<String, FunctionMonitoringData> functionData = FunctionMonitoringDataSerializer.load(functionMonitoringDataFile);
		return functionData;
	}

}
