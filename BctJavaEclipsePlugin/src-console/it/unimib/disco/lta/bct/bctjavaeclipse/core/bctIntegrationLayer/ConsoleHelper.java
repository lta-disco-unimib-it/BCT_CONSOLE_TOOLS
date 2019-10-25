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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.File;

import cpp.gdb.EnvUtil;

public class ConsoleHelper {

	public static String createGdbMonitoringcommandHelp(File traceToVerify) {
		String command = "GDB_CONFIG="+EnvUtil.getShellPathToCopy(traceToVerify)+"\n"+
				"GDB=\"gdb -batch -silent -n -x $GDB_CONFIG --args\"\n" +
				"$GDB <modifiedProgram or testCases> <args>\n";
		return command;
	}
	
	public static String createGdbCommandToExecuteAsSingleString(File traceToVerify) {
		String[] cmd = createGdbCommandToExecute(traceToVerify);
		return arrayCmdToString(cmd);
	}
	
	public static String createPinCommandToExecuteAsSingleString(MonitoringConfiguration mc, CConfiguration regressionConfig, File validTrace) {
		String[] cmd = createPinMonitoringCommand(mc, regressionConfig, validTrace); 
		return arrayCmdToString(cmd);
	}

	private static String arrayCmdToString(String[] cmd) {
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < cmd.length; i++ ){
			if ( i != 0 ){
				sb.append(" ");
			}
			sb.append(cmd[i]);
			
		}
		return sb.toString();
	}
	
	
	public static String[] createGdbCommandToExecute(File traceToVerify) {
		return new String[]{EnvUtil.getGdbExecutablePath(),"-batch","-silent","-n","-x",EnvUtil.getShellPathToCopy(traceToVerify)};
	}

	public static String createPinMonitoringcommandHelp(
			MonitoringConfiguration mc, CConfiguration regressionConfig, File validTrace) {
		return arrayCmdToString( createPinMonitoringCommand(mc, regressionConfig, validTrace) );
	}

	public static String[] createPinMonitoringCommand(
			MonitoringConfiguration mc, CConfiguration regressionConfig,
			File validTrace) {
		try {
			String pinHome = regressionConfig.getPinHome(); ///home/pastore/PIN/
			File pinProbe = ConfigurationFilesManager.getOriginalSoftwarePinMonitoringProbe(mc);
			String[] command = 
					new String[]{ pinHome+"/intel64/bin/pinbin",
					"-p32",
					pinHome+"/ia32/bin/pinbin",
					"-t",
					pinProbe.getAbsolutePath(),
					"-o",
					validTrace.getAbsolutePath(),
					"--"};
			return command;
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
