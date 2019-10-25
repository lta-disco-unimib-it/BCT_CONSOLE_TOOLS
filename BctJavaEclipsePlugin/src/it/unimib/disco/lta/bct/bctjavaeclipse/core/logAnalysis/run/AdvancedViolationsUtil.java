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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPointExit;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MethodCallPointGeneric;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctIOModelViolation.Position;
import modelsViolations.BctModelViolation;
import modelsViolations.BctModelViolation.ViolationType;
import cpp.gdb.LineData;

public class AdvancedViolationsUtil {

	public static IFile getFileForLocation(LineData location, MonitoringConfiguration mc) {
		File execFolder = getExecutableFolderForViolations(mc);
		return getFileForLocation(execFolder, location);
	}
	
	public static IFile getFileForLocation(File sources, LineData location) {
		String fileToOpen = new File( sources, location.getFileLocation() ).getAbsolutePath();

		return getFileForLocation(fileToOpen);
	}

	public static IFile getFileForLocation(String fileToOpen) {
		if ( fileToOpen == null ){
			return null;
		}

		for( IProject p : ResourcesPlugin.getPlugin().getWorkspace().getRoot().getProjects() ){
			String projectPath = p.getLocation().toString();
			System.err.println("prjPath "+projectPath);
			System.err.println("toOpen "+fileToOpen);

			if ( ! projectPath.endsWith("/") ){
				projectPath = projectPath +"/";
			}
			if ( fileToOpen.startsWith(projectPath) ){
				String toOpen = fileToOpen.substring(projectPath.length());
				System.err.println("Opening "+toOpen);
				return p.getFile(toOpen);
			}
		}

		return null;
	}
	
	public static LineData getViolationLocation( MonitoringConfiguration monitoringConfiguration, BctModelViolation violation ){
		
		if ( violation instanceof BctIOModelViolation ){
			return ConsoleViolationsUtil.getIOViolationLocation( monitoringConfiguration, (BctIOModelViolation) violation );
		} else if ( violation instanceof BctFSAModelViolation ){
			BctFSAModelViolation fsaViol = (BctFSAModelViolation)violation;
			String suffixWithLineNumber = fsaViol.getViolation();
			int index = suffixWithLineNumber.indexOf(':');
			if ( index > 0 ){
				//suffixWithLineNumber is like ExtendProfileTime :50
				String lineNumberString = suffixWithLineNumber.substring(index+1);
				int lineNumber = Integer.valueOf(lineNumberString.trim());


				LineData lineData = getLineFromStackInfo( violation, 0 );

				return new LineData( lineData.getFileLocation(), lineNumber);	
			}
			
			LineData line;

			if ( BctModelViolation.ViolationType.UNEXPECTED_TERMINATION.equals(fsaViol.getViolationType()) || 
					BctModelViolation.ViolationType.UNEXPECTED_TERMINATION_SEQUENCE.equals(fsaViol.getViolationType()) ){
				line = getLineFromStackInfo( violation, 0 );	


				try {
					InteractionRawTrace trace = MonitoringConfigurationRegistry.getInstance().getResource(monitoringConfiguration).getFinderFactory().getInteractionRawTraceHandler().findTrace(violation.getPid(), violation.getThreadId());
					int counter = 0;
					for ( MethodCallPoint callPoint : trace.getMethodCallPoints() ){
						if ( counter == violation.getCallId() ){
							if ( callPoint instanceof MethodCallPointGeneric ){
								int lineNo = ((MethodCallPointGeneric) callPoint).getLineNumber();
								if ( lineNo >= 0 ){
									return new LineData(line.getFileLocation(), lineNo);
								}
							}
						}
						if ( callPoint instanceof MethodCallPointExit ){
							continue;
						}
						counter++;
					}


				} catch (MapperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch ( Throwable t ){
					t.printStackTrace();
				}

			} else {
				line = getLineFromStackInfo( violation, 1 );	
			}

			return line;
		} 


		return getLineFromStackInfo( violation, 0 );

	}
	
	
	
	

	public static String getMethodName( BctModelViolation violation ){
		return ConsoleViolationsUtil.getMethodName(violation);
	}

	public static LineData getLineFromStackInfo(BctModelViolation violation, int stackPosition) {
		return ConsoleViolationsUtil.getLineFromStackInfo(violation, stackPosition);
	}

	public static File getSourceFolderForViolations(
			MonitoringConfiguration mc) {
		return ConsoleViolationsUtil.getSourceFolderForViolations(mc);
	}

	public static File getExecutableFolderForViolations(
			MonitoringConfiguration mc) {
		return ConsoleViolationsUtil.getExecutableFolderForViolations(mc);
	}


	

}
