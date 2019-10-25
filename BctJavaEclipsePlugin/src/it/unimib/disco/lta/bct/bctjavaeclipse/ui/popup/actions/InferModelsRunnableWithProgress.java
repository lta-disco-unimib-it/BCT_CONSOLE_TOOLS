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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ConsoleDisplayMgr;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

public class InferModelsRunnableWithProgress implements IRunnableWithProgress {

	//	/**
	//	 * This class updates the progress dialog.
	//	 * This must be re-implemented after BctResource is completed
	//	 *  
	//	 * 
	//	 * @author Fabrizio Pastore 
	//	 *
	//	 */
	//	public static class ProgressManager {
	//		
	//		private MonitoringConfiguration mc;
	//		private IProgressMonitor monitor;
	//		
	//
	//		ProgressManager ( MonitoringConfiguration mc, IProgressMonitor monitor ){
	//			this.mc = mc;
	//			this.monitor = monitor;
	//			
	//			try {
	//				
	//				int methodsMonitored = getMethodsMonitored();
	//				int totaltasks = methodsMonitored * 2 //preprocessing IO and interaction
	//								* 2 ;// inference
	//				monitor.				
	//			} catch (ConfigurationFilesManagerException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			
	//		}
	//		
	//		private int getMethodsMonitored() throws ConfigurationFilesManagerException {
	//			File dr = getDataRecordingDir(mc);
	//			return dr.list(new FilenameFilter(){
	//
	//				public boolean accept(File dir, String name) {
	//					return name.endsWith(".dtrace");
	//				}
	//				
	//			}).length;
	//		}
	//
	//		private File getDataRecordingDir(MonitoringConfiguration mc2) throws ConfigurationFilesManagerException {
	//			return new File(ConfigurationFilesManager.getBctHomeDir(mc),"DataRecording");
	//		}
	//
	//		File updateMonitorState(){
	//			
	//		}
	//		
	//		
	//	}


	private Shell shell;
	private Display display;
	private IFile file;
	private String[] resourcesToInstrument;
	private MonitoringConfiguration mc;
	private boolean updateLaunchConfiguration;

	public InferModelsRunnableWithProgress(Shell shell, Display display, MonitoringConfiguration mc ){

		this.shell = shell;
		this.display = display;
		this.mc = mc;
	}

	public void run(final IProgressMonitor monitor) throws InvocationTargetException,
	InterruptedException {


		try {
			



			//This code cannot be used: BCT uses a lot of static variables that won't be reset from one execution to another
			//String args[]={"-default"};
			//tools.InvariantGenerator.main(args);
			//EnvironmentalSetter.reset();
			//End of not usable code


			//FIXME: launching the main is not correct, unfortunately bct uses a lot of static variables, so launching a detached process should be better
			//To launch a process and attach a console to it, i think that we need to do some of this actions (plus creating the console)
			//ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			//LaunchManager.getLaunchCOnfiguration(String memento)
			//LaunchCOnfiguration.launch
			//DebugPLugin.newProcess

			//Example about creating a console
			//			ConsolePlugin.getDefault().getConsoleManager().showConsoleView(mc);
			//			final IOConsoleOutputStream ios = mc.newOutputStream();
			//			final IOConsoleOutputStream eos = mc.newOutputStream();


			//String inferenceCommand = "java -Dbct.home=\""+configHome.getAbsolutePath()+"\" -cp \""+DefaultOptionsManager.getBctJarFile().getAbsolutePath()+"\" "+tools.InvariantGenerator.class.getName()+" -default";
			//inferenceCommand="java -version";
			//System.out.println(inferenceCommand);
			//Process process = Runtime.getRuntime().exec(inferenceCommand);
			



			//			BctConsoleView bctConsoleView = (BctConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
			//			final OutputStream ios = bctConsoleView.getOutputStream();
			//			final OutputStream eos = bctConsoleView.getOutputStream();
			//			System.out.println(ios);
			//			System.out.println(eos);
			
			ConsoleDisplayMgr.getDefault().printInfo("BCT - Starting model inference \n");
			
			final ConsoleDisplayMgr c = ConsoleDisplayMgr.getDefault();
			final OutputStream os = c.getOutputStream();
			final OutputStream eos = c.getErrorStream();
			
			ImageDescriptor id = ImageDescriptor.getMissingImageDescriptor();
			IViewDescriptor view = PlatformUI.getWorkbench().getViewRegistry().find("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
			
			
			ModelInference.inferModels(mc, monitor, os, eos);



			ConsoleDisplayMgr.getDefault().printInfo("BCT - Model Inference Successfully Terminated" );


		} catch (ConfigurationFilesManagerException e) {
			MessageDialog.openError(shell, "Model inference error", "Cannot infer models : "+e.getMessage());
			//		} catch (IOException e) {
			//			// TODO Auto-generated catch block
			//			e.printStackTrace();
			//		} catch (DefaultOptionsManagerException e) {
			//			// TODO Auto-generated catch block
			//			e.printStackTrace();
			//		} catch (PartInitException e) {
			//			// TODO Auto-generated catch block
			//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	

}
