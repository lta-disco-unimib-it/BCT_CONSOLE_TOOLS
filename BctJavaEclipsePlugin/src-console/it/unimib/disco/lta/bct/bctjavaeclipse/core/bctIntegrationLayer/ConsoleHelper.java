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
