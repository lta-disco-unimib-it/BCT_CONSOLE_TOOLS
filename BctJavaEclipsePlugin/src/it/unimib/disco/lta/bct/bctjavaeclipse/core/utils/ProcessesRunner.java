package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * This class can be used to run java programs as "Run Configurations". 
 * As if manually started.
 * 
 * Guidelines followed to write the class:
 *    http://www.eclipse.org/articles/Article-Java-launch/launching-java.html
 *    
 * Other resources:
 * 	http://www.eclipse.org/articles/Article-Launch-Framework/launch.html   
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@gmail.com
 *
 */
public class ProcessesRunner {

	public static void runProcess(MonitoringConfiguration mc, String nameToShow, String classToRun ) throws CoreException, DefaultOptionsManagerException, ConfigurationFilesManagerException{
		
		
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type =
			manager.getLaunchConfigurationType("org.eclipse.jdt.junit.launchconfig");
		ILaunchConfiguration[] configurations =
			manager.getLaunchConfigurations(type);
		for (int i = 0; i < configurations.length; i++) {
			ILaunchConfiguration configuration = configurations[i];
			if (configuration.getName().equals(nameToShow)) {
				configuration.delete();
				break;
			}
		}
		
		ILaunchConfigurationWorkingCopy workingCopy =
			type.newInstance(null, nameToShow);

		
		IVMInstall jre = JavaRuntime.getDefaultVMInstall();			
		   File jdkHome = jre.getInstallLocation();
//		   IPath toolsPath = new Path(jdkHome.getAbsolutePath())
//		          .append("lib")
//		          .append("tools.jar");
//		   IRuntimeClasspathEntry toolsEntry =
//		      JavaRuntime.newArchiveRuntimeClasspathEntry(toolsPath);
//		   toolsEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		   
		   IPath bctPath = new Path(DefaultOptionsManager.getBctJarFile().getAbsolutePath());
		   IRuntimeClasspathEntry bctEntry = 
			   JavaRuntime.newArchiveRuntimeClasspathEntry(bctPath);
//			      JavaRuntime.newVariableRuntimeClasspathEntry(bctPath);
		   
		   
//		   IPath bootstrapPath = new Path("TOMCAT_HOME")
//		          .append("bin")
//		          .append("bootstrap.jar");
//		   
//		   IRuntimeClasspathEntry bootstrapEntry = 
//		      JavaRuntime.newVariableRuntimeClasspathEntry(bootstrapPath);
//		   bootstrapEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		   
		IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
		   IRuntimeClasspathEntry systemLibsEntry =
		      JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath,
		         IRuntimeClasspathEntry.STANDARD_CLASSES);

//		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_INSTALL_NAME, jre.getName());
//		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_INSTALL_TYPE, jre.getVMInstallType().getId());
		   
		   
		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
		   classToRun);
		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, "start");
		   
		   
		   
		   List classpath = new ArrayList();
//		   classpath.add(toolsEntry.getMemento());
//		   classpath.add(bootstrapEntry.getMemento());
		   classpath.add(systemLibsEntry.getMemento());
		   classpath.add(bctEntry.getMemento());
		   
		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
		
		   File bctHome = ConfigurationFilesManager.getBctHomeDir(mc);
		
		   workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,
				      "-Dbct.home="+bctHome.getAbsolutePath() );
				
		   
//		   
//		   Set WORKING DIR
//		   
//		   File workingDir = JavaCore.getClasspathVariable("TOMCAT_HOME")
//		      .append("bin").toFile();
//		   workingCopy.setAttribute(ATTR_WORKING_DIRECTORY,
//		      workingDir.getAbsolutePath());
//		   
		   
		   
		   
		   
		   ILaunchConfiguration configuration = workingCopy.doSave();
		   
		   configuration.launch(ILaunchManager.RUN_MODE,new NullProgressMonitor());
		   
//		   DebugUITools.launch(configuration, ILaunchManager.RUN_MODE);
		   
		   
		   
	}
}
