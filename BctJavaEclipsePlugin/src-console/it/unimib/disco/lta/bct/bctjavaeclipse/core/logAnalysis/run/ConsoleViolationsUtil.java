package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.File;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctIOModelViolation.Position;
import modelsViolations.BctModelViolation;
import cpp.gdb.LineData;

public class ConsoleViolationsUtil {



	public static LineData getIOViolationLocation(
			MonitoringConfiguration monitoringConfiguration,
			BctIOModelViolation violation) {
		String modelName = violation.getViolatedModel();
		int typeStart = modelName.indexOf(":::");
		String modelNameNoType = modelName.substring(0, typeStart);
		int index = modelNameNoType.indexOf(':');

		if ( index > 0 ){ //POINT violation			

			String suffixWithLineNumber = modelName.substring(index+1);

			int linenumberEnd = suffixWithLineNumber.indexOf(':');

			String lineNumberString = suffixWithLineNumber.substring(0, linenumberEnd);

			int lineNumber = Integer.valueOf(lineNumberString.trim());


			LineData lineData = getLineFromStackInfo( violation, 0 );

			return new LineData( lineData.getFileLocation(), lineNumber);
		}
		
		BctIOModelViolation ioViolation = (BctIOModelViolation) violation;
		if ( ioViolation.getPosition() == Position.ENTER ){
			return getLineFromStackInfo( violation, 1 ); //caller side		
		} else {
			return getLineFromStackInfo( violation, 0 );
		}
	}



	public static String getMethodName( BctModelViolation violation ){
		String modelName = violation.getViolatedModel();

		int index = modelName.indexOf(":::");
		if ( index < 0 ){
			index = modelName.length();
		}

		return modelName.substring(0,index);
	}

	public static LineData getLineFromStackInfo(BctModelViolation violation, int stackPosition) {
		String topStack = violation.getStackTrace()[stackPosition];
		int stackColons = topStack.indexOf(':');

		String fileName = topStack.substring(0, stackColons);
		String lineNoString = topStack.substring(stackColons+1).trim();

		if ( fileName.endsWith(".") ){
			fileName = fileName.substring(0,fileName.length()-1);
		}
		return new LineData(fileName, Integer.valueOf(lineNoString));
	}

	public static File getSourceFolderForViolations(
			MonitoringConfiguration mc) {

		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			CConfiguration conf = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
			return new File( conf.getOriginalSwSourcesFolder() );

		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			CRegressionConfiguration conf = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			return new File( conf.getModifiedSwSourcesFolder() );
		} else {
			return null;
		}
	}

	public static File getExecutableFolderForViolations(
			MonitoringConfiguration mc) {

		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			CConfiguration conf = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
			return new File( conf.getOriginalSwExecutable() ).getParentFile();

		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			CRegressionConfiguration conf = (CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
			return new File( conf.getModifiedSwExecutable() ).getParentFile();
		} else {
			return null;
		}
	}

	

}
