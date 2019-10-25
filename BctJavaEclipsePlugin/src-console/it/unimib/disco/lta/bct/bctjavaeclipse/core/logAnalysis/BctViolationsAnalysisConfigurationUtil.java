package it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.BctViolationsAnalysisSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.tptp.logging.events.cbe.FormattingException;

import util.cbe.CBELogLoader;
import failureDetection.Failure;

public class BctViolationsAnalysisConfigurationUtil {
	
	public static BctViolationsAnalysisConfiguration createAndStoreBctViolationsAnalysisConfiguration(IResource resultingViolationsLogAnalysisFile, boolean copyLogs, List<IFile> files, IFile anomalyGraphsFolder){
		
		//Create folder
		anomalyGraphsFolder.getLocation().toFile().mkdirs();
		try {
			
			IProgressMonitor pm = new NullProgressMonitor();
			anomalyGraphsFolder.getParent().refreshLocal(IResource.DEPTH_INFINITE, pm);
//			pm.waitFor();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BctViolationsAnalysisConfiguration config = createBctViolationsAnalysisConfiguration(copyLogs, files, anomalyGraphsFolder);
		
		try {
			BctViolationsAnalysisSerializer.serialize(resultingViolationsLogAnalysisFile.getLocation().toFile(), config);
		} catch (FileNotFoundException e1) {
			//FIXME throw
		}
		
		return config;
	}
	
	public static BctViolationsAnalysisConfiguration createBctViolationsAnalysisConfiguration(boolean copyLogs, List<IFile> files, IFile anomalyGraphsFolder){
		
		final BctViolationsAnalysisConfiguration config = new BctViolationsAnalysisConfiguration();
		config.setAnomaliesGraphsPath(anomalyGraphsFolder.getFullPath().toString());
		
		handleLogFiles(config,anomalyGraphsFolder,copyLogs,files);
		setFailingElements(config);
		
		
		return config;
	}
	
	
	private static void handleLogFiles(BctViolationsAnalysisConfiguration config,
			IFile anomalyGraphsFolder, boolean copyLogs, List<IFile> files) {
		
		List<IFile> logFiles;
		
		if ( copyLogs ){
			List<IFile> toCopy = new ArrayList<IFile>();
			for ( IFile file : files ){
				if ( file.exists() ){
					toCopy.add(file);
				}
			}
			IFile[] resources = toCopy.toArray(new IFile[toCopy.size()]);
			try {
				IStatus s = ResourcesPlugin.getWorkspace().copy(resources, anomalyGraphsFolder.getFullPath(), false, new NullProgressMonitor());
				
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logFiles=new ArrayList<IFile>();
			for ( IFile orig : files ){
				String name = orig.getName();
				IPath newFilePath = anomalyGraphsFolder.getFullPath().append(name);
				System.out.println(newFilePath);
				logFiles.add(ResourcesPlugin.getWorkspace().getRoot().getFile(newFilePath));
			}
			
		} else {
			logFiles=files;
		}
		
		
		ArrayList<String> filesToOpen = new ArrayList<String>();
		for ( IFile file : logFiles ){
			filesToOpen.add(file.getFullPath().toString());
		}
		
		config.setLogFilesNames(filesToOpen);
		
		
		
	}
	
	
	/**
	 * Set as failing the actions pids tests in correspondence to the information present in the log file
	 * 
	 * @param config
	 */
	private static void setFailingElements(BctViolationsAnalysisConfiguration config) {
		
		CBELogLoader loader = new CBELogLoader();
		ArrayList<Failure> failures = new ArrayList<Failure>();
		ArrayList<BctModelViolation> modelsViolations = new ArrayList<BctModelViolation>();
		
		for ( String path : config.getLogFilesNames() ){
			IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
			File file = ifile.getLocation().toFile();
			
			Object[] entities;
			try {
				entities = loader.loadEntitiesFromCBEFile(file);
				for ( Object entity : entities ){
					if ( entity instanceof BctModelViolation ){
						modelsViolations.add(
								(BctModelViolation) entity);
					} else if ( entity instanceof Failure) {
						failures.add((Failure) entity);
					}
				}
			} catch (FormattingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		
		for ( Failure f : failures ){
			String id;
			
			id= f.getFailingActionId();
			if ( id != null ){
				config.addFailingAction(id);
			}
			
			
			id=f.getFailingPID();
			if ( id != null ){
			config.addFailingProcess(f.getFailingPID());
			}
			
			id= f.getFailingTestId();
			if ( id != null ){
			config.addFailingTest(f.getFailingTestId());
			}
		}

		
		
	}
	
}
