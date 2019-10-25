package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.AdditionalInferenceOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.AnomalyDetection;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.ProcessesRunner;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ConsoleDisplayMgr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IViewDescriptor;

import tools.gdbTraceParser.GdbTraceParser;
import util.FileUtil;
import util.ProcessRunner;

import conf.EnvironmentalSetter;

public class IdentifyAnomaliesRunnableWithProgress implements IRunnableWithProgress {

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
	private boolean inverseFiltering;

	public IdentifyAnomaliesRunnableWithProgress(Shell shell, Display display, MonitoringConfiguration mc ){

		this.shell = shell;
		this.display = display;
		this.mc = mc;

	}




	public void runInProcessesRunner(){


		try {



			AnomalyDetection.identifyAnomalies( mc );

			Display.getDefault().asyncExec(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					NewAnomaliesAnalysisAction anomaliesAnalysis = new NewAnomaliesAnalysisAction();
					try {
						anomaliesAnalysis.runAnomaliesAnalysis(mc);
					} catch (ConfigurationFilesManagerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				}

			});




		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DefaultOptionsManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public void runInThreads(final IProgressMonitor monitor){

		try {
			File configHome = ConfigurationFilesManager.getBctHomeDir(mc);
			ConfigurationFilesManager.updateConfigurationFiles(mc);
			System.setProperty("bct.home", configHome.getAbsolutePath());

			//setProperty does not work use this
			EnvironmentalSetter.setBctHome(configHome.getAbsolutePath());


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
			//		ConsolePlugin.getDefault().getConsoleManager().showConsoleView(mc);
			//		final IOConsoleOutputStream ios = mc.newOutputStream();
			//		final IOConsoleOutputStream eos = mc.newOutputStream();


			//String inferenceCommand = "java -Dbct.home=\""+configHome.getAbsolutePath()+"\" -cp \""+DefaultOptionsManager.getBctJarFile().getAbsolutePath()+"\" "+tools.InvariantGenerator.class.getName()+" -default";
			//inferenceCommand="java -version";
			//System.out.println(inferenceCommand);
			//Process process = Runtime.getRuntime().exec(inferenceCommand);
			String cmdarray[] = {
					"java",
					"-Dbct.home="+configHome.getAbsolutePath(),
					"-cp",
					DefaultOptionsManager.getBctJarFile().getAbsolutePath(),
					check.AnomaliesDetector.class.getName(),
					"-default"
			};

			final Process process = Runtime.getRuntime().exec(cmdarray);
			ImageDescriptor id = ImageDescriptor.getMissingImageDescriptor();
			//		MessageConsole mc = new MessageConsole("Bct Inference Engine", id);
			//		mc.activate();


			IViewDescriptor view = PlatformUI.getWorkbench().getViewRegistry().find("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
			System.out.println(view);

			final ConsoleDisplayMgr c = ConsoleDisplayMgr.getDefault();

			//		BctConsoleView bctConsoleView = (BctConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
			//		final OutputStream ios = bctConsoleView.getOutputStream();
			//		final OutputStream eos = bctConsoleView.getOutputStream();
			//		System.out.println(ios);
			//		System.out.println(eos);


			final InputStream es =  process.getErrorStream();
			final InputStream is = process.getInputStream();
			final int bufSize = 256;
			final OutputStream os = c.getOutputStream();
			final OutputStream eos = c.getErrorStream();

			Thread ir = new Thread(){
				public void run(){
					byte buf[] = new byte[bufSize];
					int readed;
					try {
						while ( ( readed = is.read(buf, 0, bufSize) ) > 0 ){

							//c.println(new String(buf,"utf-8"), 1);
							try {
								os.write(buf, 0, readed);
								System.out.write(buf,0,readed);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};

			Thread er = new Thread(){
				public void run(){
					byte buf[] = new byte[bufSize];
					int readed;
					try {
						while ( ( readed = es.read(buf, 0, bufSize) ) > 0 ){
							//c.println(new String(buf,"utf-8"), 1);
							try {
								eos.write(buf, 0, readed);
								System.out.write(buf,0,readed);
							} catch (IOException e) {

								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			ir.start();
			er.start();



			//Monitor for cancel operations
			Thread monitorChecker = new Thread(){
				public void run(){
					while ( true ){
						if ( monitor.isCanceled() ){
							System.out.println("Process canceled");
							process.destroy();
							try {
								c.getErrorStream().write("PROCESS TERMINATED".getBytes());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
						try {
							sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			};

			monitorChecker.start();


			process.waitFor();



			System.out.println("Terminated");

		} catch ( Exception e ){
			e.printStackTrace();
			MessageDialog.openError(shell, "Anomaly detection error", "Cannot identify anomalies: "+e.getMessage());
		}
	}

	public void run(final IProgressMonitor monitor) throws InvocationTargetException,
	InterruptedException {

		runInProcessesRunner();		
		//runInThreads(monitor);


	}

}
