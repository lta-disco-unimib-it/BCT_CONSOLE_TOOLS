package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.core.resources.IFile;

import cpp.gdb.SourceLinesMapper;

public class CRegressionConfiguration extends CConfiguration {

	private String modifiedSwSourcesFolder;
	private String modifiedSwExecutable;
	
	
	private boolean useUpdatedReferencesForModels = true;
	private boolean monitorAddedAndDeletedFunctions;
	List<ProgramPoint> modifiedProgramPoints = new ArrayList<ProgramPoint>();
	private boolean hideAddedAndDeletedFunctions = true;
	private boolean monitorCallersOfModifiedFunctions = true;
	
	public boolean isUseUpdatedReferencesForModels() {
		return useUpdatedReferencesForModels;
	}


	public void setUseUpdatedReferencesForModels(
			boolean useUpdatedReferencesForModels) {
		this.useUpdatedReferencesForModels = useUpdatedReferencesForModels;
	}


	private boolean monitorOnlyNotModifiedLines;
	
	
	public boolean isMonitorOnlyNotModifiedLines() {
		return monitorOnlyNotModifiedLines;
	}


	public void setMonitorOnlyNotModifiedLines(boolean monitorOnlyNotModifiedLines) {
		this.monitorOnlyNotModifiedLines = monitorOnlyNotModifiedLines;
	}


	
	
	
	public CRegressionConfiguration( CConfiguration parent ){
		super( parent );
	}


	public boolean isMonitorProjectFunctionsOnly() {
		return monitorProjectFunctionsOnly;
	}

	public void setMonitorProjectFunctionsOnly(boolean monitorProjectFunctionsOnly) {
		this.monitorProjectFunctionsOnly = monitorProjectFunctionsOnly;
	}
	public boolean isUseDemangledNames() {
		return useDemangledNames;
	}

	public void setUseDemangledNames(boolean useDemangledNames) {
		this.useDemangledNames = useDemangledNames;
	}

	public boolean isMonitorCallersOfModifiedFunctions() {
		return monitorCallersOfModifiedFunctions;
	}

	public void setMonitorCallersOfModifiedFunctions(
			boolean monitorCallersOfModifiedFunctions) {
		this.monitorCallersOfModifiedFunctions = monitorCallersOfModifiedFunctions;
	}

	public CRegressionConfiguration(){
		
	}
	
	public String getOriginalSwSourcesFolder() {
		return originalSwSourcesFolder;
	}
	public void setOriginalSwSourcesFolder(String originalSwSourcesFolder) {
		this.originalSwSourcesFolder = originalSwSourcesFolder;
	}
	public String getOriginalSwExecutable() {
		return originalSwExecutable;
	}
	public File getOriginalSwExecutableFile() {
		return new File( originalSwExecutable );
	}
	public void setOriginalSwExecutable(String originalSwExecutable) {
		this.originalSwExecutable = originalSwExecutable;
	}
	
	public File getModifiedSwSourcesFolderFile() {
		return new File( modifiedSwSourcesFolder );
	}
	
	public String getModifiedSwSourcesFolder() {
		return modifiedSwSourcesFolder;
	}
	public void setModifiedSwSourcesFolder(String modifiedSwSourcesFolder) {
		this.modifiedSwSourcesFolder = modifiedSwSourcesFolder;
	}
	public String getModifiedSwExecutable() {
		return modifiedSwExecutable;
	}
	public File getModifiedSwExecutableFile() {
		return new File(modifiedSwExecutable);
	}
	public void setModifiedSwExecutable(String modifiedSwExecutable) {
		this.modifiedSwExecutable = modifiedSwExecutable;
	}
	
	public void addSourceProgramPoint(IFile openFile, int startLine) throws IOException {
		
		String fileToMonitorPath = openFile.getLocation().toFile().getCanonicalPath();
		addSourceProgramPoint(fileToMonitorPath, startLine);
	}


	public void addSourceProgramPoint(String fileToMonitorPath, int startLine) {
		List<ProgramPoint> programPointsList;
		
		if ( fileToMonitorPath.startsWith(originalSwSourcesFolder) ){
			programPointsList = sourceProgramPoints;
		} else if ( fileToMonitorPath.startsWith(modifiedSwSourcesFolder) ){
			programPointsList = modifiedProgramPoints;
		} else {
			return; //FIXME: throw an exception
		}
		
		//WAS String fileToMonitor = openFile.getFullPath().toString();
		
		programPointsList.add( new ProgramPoint( fileToMonitorPath, startLine) );
	}


	public List<ProgramPoint> getSourceProgramPointsForModifiedSoftware() {
		return modifiedProgramPoints;
	}


	public boolean isMonitorAddedAndDeletedFunctions() {
		return monitorAddedAndDeletedFunctions;
	}


	public void setMonitorAddedAndDeletedFunctions(
			boolean monitorAddedAndDeletedFunctions) {
		this.monitorAddedAndDeletedFunctions = monitorAddedAndDeletedFunctions;
	}


	public boolean isHideAddedAndDeletedFunctions() {
		return hideAddedAndDeletedFunctions;
	}


	public void setHideAddedAndDeletedFunctions(boolean hideAddedAndDeletedFunctions) {
		this.hideAddedAndDeletedFunctions = hideAddedAndDeletedFunctions;
	}


}
