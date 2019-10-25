package it.unimib.disco.lta.eclipse.vart.ui.popup.actions;


import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTResultsLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.util.ActionUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart.VARTMarker;
import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.CDTStandaloneCFileAnalyzer;

import it.unimib.disco.lta.eclipse.vart.core.VARTRunnableWithProgress;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import cpp.gdb.CSourceAnalyzerRegistry;


public class VerifyUpgrade implements IObjectActionDelegate {

	private ISelection selection;

	public VerifyUpgrade() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(final IAction action) {
		System.out.println("HERE");
		
		try {
			final MonitoringConfiguration mc = ActionUtil.getSelectedMonitoringConfiguration( action );
			
			CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();
			CSourceAnalyzerRegistry.setCSourceAnalyzer(analyzer);
			
			final IRunnableWithProgress r = new VARTRunnableWithProgress( mc );
			Job j = new Job("Executing VART"){

				@Override
				protected IStatus run(IProgressMonitor arg0) {
					try {
						
						VARTMarker.INSTANCE.cleanupMarkers();
						
						r.run(arg0);
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return Status.OK_STATUS;
				}
				
			};
			
			j.addJobChangeListener(new IJobChangeListener() {
				
				@Override
				public void sleeping(IJobChangeEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void scheduled(IJobChangeEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void running(IJobChangeEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void done(IJobChangeEvent arg0) {
					Display.getDefault().asyncExec( new Runnable() {
						
						@Override
						public void run() {
							VARTResultsLoader loader = new VARTResultsLoader(mc);
							
							try {
								Set<VARTDataProperty> regressedProperties = loader.loadRegressedProperties();
								VARTMarker.INSTANCE.markAll(regressedProperties);
							} catch (ConfigurationFilesManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
				}
				
				@Override
				public void awake(IJobChangeEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void aboutToRun(IJobChangeEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			j.schedule();
		} catch (CoreException e) {
			Logger.getInstance().log(e);
			return;
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
