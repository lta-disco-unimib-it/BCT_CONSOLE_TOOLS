package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import tools.fshellExporter.FShellExecutor;
import tools.fshellExporter.FShellExecutor.FShellResult;
import tools.fshellExporter.FShellModelsExporter;
import util.FileUtil;
import util.JavaRunner;

public class IdentifyInvalidatedPropertiesAction  implements IObjectActionDelegate  {

	public IdentifyInvalidatedPropertiesAction() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {

		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

		if ( selection instanceof IStructuredSelection ){

			final Display display = PlatformUI.getWorkbench().getDisplay();
			final Shell shell = display.getActiveShell();
            
           


			IStructuredSelection ss = (IStructuredSelection) selection;
			IFile selectedElement = (IFile) ss.getFirstElement();
			File selectedFile = selectedElement.getLocation().toFile();
			try {
			
				final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);


				
				//Run it as a Job, we do not want to block users operations 
				Job job = new Job("FShellValidation : "+mc.getConfigurationName()){
					@Override
					protected IStatus run(IProgressMonitor monitor) {
							
							try {
								return runTrueModelsIdentification(mc);
							} catch (ConfigurationFilesManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DefaultOptionsManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return Status.OK_STATUS;
					}

					
				};
				
				
				job.schedule();


			//TODO: Add message dialogs in case of errors
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

	}

	public static IStatus runTrueModelsIdentification(
			final MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException,
			DefaultOptionsManagerException, IOException {
		File bctHomeFile = ConfigurationFilesManager.getBctHomeDir(mc);
		
		List<String> args = new ArrayList<String>();
		args.add(bctHomeFile.getAbsolutePath());
		
		List<String> cp = new ArrayList<String>();
		cp.add(DefaultOptionsManager.getBctJarFile().getAbsolutePath());
		
		JavaRunner.runMainInClass(FShellModelsExporter.class, args, 0, cp);
		
		
		File validatedModelsDir = ConfigurationFilesManager.getValidatedModelsDir( mc ) ;
		File fshellScript = FShellModelsExporter.getAllModelsFile(validatedModelsDir);
		
		
		FShellExecutor executor = new FShellExecutor();
		
		CConfiguration config = null;
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			config = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);
		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			config = (CConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);
		} else {
			return Status.OK_STATUS;
		}
		
		ArrayList<File> filesToCheck = FileUtil.getContentsRecursively(new File(config.getOriginalSwSourcesFolder()), new FileFilter() {
			
			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				return arg0.getName().endsWith(".C") || arg0.getName().endsWith(".c") || arg0.getName().endsWith(".CPP") || arg0.getName().endsWith(".cpp");
			}
		});
		
		ArrayList<File> files = new ArrayList<File>();
		for ( File file : filesToCheck ){
			files.add( new File(file.getAbsolutePath().substring(config.getOriginalSwSourcesFolder().length()+1)) );
		}
		
		
		FShellResult fresult = executor.execute(fshellScript, new File(config.getOriginalSwSourcesFolder()), files);
		
		FShellModelsExporter fsm = new FShellModelsExporter(bctHomeFile);
		fsm.exportValidModels( fresult, validatedModelsDir );
		
		return Status.OK_STATUS;
	}

	

	public void selectionChanged(IAction action, ISelection selection) {
	}


}
