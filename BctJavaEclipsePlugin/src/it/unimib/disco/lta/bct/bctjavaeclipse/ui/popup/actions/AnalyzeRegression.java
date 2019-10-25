package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.FileSystemDataManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.AnomalyDetection;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis.RegressionAnalysisOutput;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.CleaningUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.util.ActionUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.StaticallyIdentifyUsageAnomaliesResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.ui.internal.Workbench;

import console.AnomaliesIdentifier;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import util.FileUtil;

public class AnalyzeRegression implements IObjectActionDelegate {

	private ISelection selection;

	public AnalyzeRegression() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(final IAction action) {
		try {
			final MonitoringConfiguration mc = ActionUtil.getSelectedMonitoringConfiguration( action );
			CleaningUtil.deleteInferenceAndAnalysisData(ConfigurationFilesManager.getBctHomeDir(mc));
			
		} catch (CoreException e) {
			Logger.getInstance().log(e);
			return;
		} catch (ConfigurationFilesManagerException e) {
			Logger.getInstance().log(e);
			return;
		}
		
		InferModelsAction infer = new InferModelsAction();
		infer.run(action, new IJobChangeListener() {
			
			@Override
			public void sleeping(IJobChangeEvent event) {
			}
			
			@Override
			public void scheduled(IJobChangeEvent event) {
			}
			
			@Override
			public void running(IJobChangeEvent event) {
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				runIndentifyAnomalies(action);
			}
			
			

			@Override
			public void awake(IJobChangeEvent event) {
			}
			
			@Override
			public void aboutToRun(IJobChangeEvent event) {
			}
		});
	}
	
	private void runIndentifyAnomalies(IAction action) {
		
		IStructuredSelection ss = (IStructuredSelection) selection;
		IFile selectedElement = (IFile) ss.getFirstElement();
		final File selectedFile = selectedElement.getLocation().toFile();
		try {
			//Open the bct console for debug info
			//BctConsoleView bctConsoleView = (BctConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctConsoleView");
			
			//Read the monitoring configuration
			
			
			Job job = new Job("RADAR: Identify Anomalies"){
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);

						AnomalyDetection.identifyAnomalies(mc);
						
						RegressionAnalysisOutput regressionAnalysisResult = RegressionAnalysis.identifyRegressions(mc,true);
					
						final IFile resultFile = regressionAnalysisResult.getAnalysisFile();
						
						Display.getDefault().asyncExec( new Runnable() {
							
							@Override
							public void run() {
								try {
									IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() , resultFile);
								} catch (PartInitException e) {
									Logger.getInstance().log(e);
								}		
							}
						} );
						
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						return Status.CANCEL_STATUS;
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ConfigurationFilesManagerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DefaultOptionsManagerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CBEBctViolationsLogLoaderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
			};
			
			
			job.schedule();
			
//			StaticallyIdentifyUsageAnomaliesResult result = new StaticallyIdentifyUsageAnomaliesResult();
//			
			StaticallyIdentifyAnomaliesAction identifyAnomalies = new StaticallyIdentifyAnomaliesAction();
//			identifyAnomalies.selectionChanged(action, selection);
//			identifyAnomalies.identifyAnomalies(Display.getDefault(), null, mc, result);
			
		} catch ( Exception e ){
			
		}
		

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

}
