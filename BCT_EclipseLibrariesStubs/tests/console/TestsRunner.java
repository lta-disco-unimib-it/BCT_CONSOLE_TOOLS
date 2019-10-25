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
package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import util.ProcessRunner;
import cpp.gdb.EnvUtil;

public class TestsRunner {

	private static final String JAVA_UTIL_LOGGING_FILE_PATH = "java.util.logging.file.path";
	public static final String GDB;
	private static final String originalScript;
	private static final String modifiedScript;
	public static final String defaultProjectDir;
	private static final String toVerifyScript;
	
	static {
		String loggingConf = System.getProperty(JAVA_UTIL_LOGGING_FILE_PATH);
		if ( loggingConf == null ){
			System.setProperty(JAVA_UTIL_LOGGING_FILE_PATH, "logging.properties");
		}
		
		if ( EnvUtil.isWindows() ){
			originalScript = EnvUtil.getBctDefaultDrive()+"tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt".replace("/", "\\");
			toVerifyScript = EnvUtil.getBctDefaultDrive()+"tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.toVerify.txt".replace("/", "\\");
			modifiedScript = EnvUtil.getBctDefaultDrive()+"tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/modifiedSoftware.gdb.config.txt".replace("/", "\\");
			defaultProjectDir = EnvUtil.getBctDefaultDrive()+"tmp\\BCT";
		} else {
			originalScript = "/tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt";
			toVerifyScript = "/tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.toVerify.txt";
			modifiedScript = "/tmp/BCT/BCT/BCT_DATA/BCT/conf/files/scripts/modifiedSoftware.gdb.config.txt";
			defaultProjectDir = "/tmp/BCT/";
		}
		GDB = EnvUtil.getGdbExecutablePath();
	}
	
	public static void runMonitoringOriginal( String programToExecute, String args[]) throws IOException {
		runMonitoring(originalScript, programToExecute, args);
	}
	
	public static void runMonitoringModified( String programToExecute, String args[]) throws IOException {
		runMonitoring(modifiedScript, programToExecute, args);
	}
	

	public static void runMonitoringOriginalToVerify(String programToExecute,
			String[] args) throws IOException {
		runMonitoring(toVerifyScript, programToExecute, args);
	}
	
	public static void compile(String srcFolder) throws IOException{
		String[] commands = new String[]{
				"make"
		};

		executeCommand(srcFolder, commands);
	}

	public static void executeCommand(String srcFolder,
			String[] commandAndArguments) throws IOException {
		executeCommand(srcFolder, commandAndArguments, null);
	}
	
	public static void executeCommand(String srcFolder, String[] commandAndArguments,
				HashMap<String, String> env) throws IOException {
			// TODO Auto-generated method stub
			
		
		srcFolder = TestsRunner.testPath( srcFolder );
		Appendable errorBuffer = new StringBuffer();;
		Appendable outputBuffer = new StringBuffer();

		List<String> commandList = new ArrayList<String>();

		for ( String arg : commandAndArguments ){
			commandList.add(arg);
		}
		
		
		ProcessRunner.run(commandList, outputBuffer, errorBuffer, -1, new File(srcFolder), env );
		
		System.out.println(outputBuffer.toString());
		System.out.println(errorBuffer.toString());
	}
	
	private static void runMonitoring(String configScript, String programToExecute, String args[]) throws IOException {
		Appendable errorBuffer = new StringBuffer();;
		Appendable outputBuffer = new StringBuffer();

		String[] commands = new String[]{
				GDB,
				"-batch",
				"-silent",
				"-n",
				"-x",
				configScript,
				"--args",
				programToExecute
		};

		List<String> commandList = new ArrayList<String>();

		for ( String arg : commands ){
			commandList.add(arg);
		}

		if ( args != null ) {
			for ( String arg : args ){
				commandList.add(arg);
			}
		}
		
		
		ProcessRunner.run(commandList, outputBuffer, errorBuffer, -1);
	}

	public static String testPath(String path ) {
		if ( ! EnvUtil.isWindows() ){
			return path;
		}
		if ( path.startsWith("/home/BCT") ){
			path = EnvUtil.getBctDefaultDrive()+"workspaceBCT\\BCT_TestCasesProject\\"+path.substring(5);
			path = path.replace("/", "\\");
			File f = new File( path );
			if ( ! f.isDirectory() ){
				path = path+".exe";
			}
		} else if ( path.startsWith("/tmp/BCT") ){
			path = path.replace("/", "\\");
			path = EnvUtil.getBctDefaultDrive()+path;
		}
		return path;
	}


	public static List<String> getResultLines(List<String> lines) {
		List<String> res = getResultLinesUnsorted(lines);
		Collections.sort(res);
		return res;
	}
	
	public static List<String> getResultLinesUnsorted(List<String> lines) {
		List<String> result = new ArrayList<String>();
		
		for ( int i = lines.size()-1; i >= 0 ; i-- ){
			String line = lines.get(i);
			if ( line.startsWith("WARNING") ){
				break;
			}
			result.add(line);
		}
		
		Collections.sort(result);
		
		System.out.println("Result lines:");
		for ( String line : result ){
			System.out.println(line);
		}
		
		
		return result;
	}

	public static MonitoringConfiguration getDefaultMonitoringConfiguration(String projectDir) {
		ProjectSetup prj = ProjectSetup.setupProject(projectDir);
		try {
			return MonitoringConfigurationDeserializer.deserialize(prj.getMonitoringConfigurationFile());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
