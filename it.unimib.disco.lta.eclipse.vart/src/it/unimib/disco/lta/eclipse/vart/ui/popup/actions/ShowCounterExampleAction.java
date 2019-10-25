package it.unimib.disco.lta.eclipse.vart.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;

import java.io.File;
import java.util.LinkedList;

import org.cprover.communication.Claim;
import org.cprover.launch.InvalidRunException;
import org.cprover.launch.LaunchConfigurationDelegate;
import org.cprover.launch.VerifyConfig;
import org.cprover.launch.VerifyConfig.Verifier;
import org.cprover.perspectives.VerifyPerspective;
import org.cprover.runners.ClaimCheckRunner;
import org.cprover.runners.ShowClaimsRunner;
import org.cprover.ui.Utils;
import org.cprover.views.ClaimsView;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionDelegate;

public class ShowCounterExampleAction extends ActionDelegate {

	private VARTDataProperty selectedDataProperty;

	public ShowCounterExampleAction(){
		System.out.println("BUILD");
	}

	@Override
	public void run(IAction action) {
		ILaunchConfiguration launch = null;
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type =
				manager.getLaunchConfigurationType("org.cprover.plugin.launchConfigurationType");
		ILaunchConfiguration[] configurations;
		try {
			configurations = manager.getLaunchConfigurations(type);
			for (int i = 0; i < configurations.length; i++) {
				ILaunchConfiguration configuration = configurations[i];
				if (configuration.getName().equals("VART")) {
					launch = configuration;
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ( launch == null ){
			System.out.println("Plug-in "+VerifyPerspective.PERSPECTIVE_ID+" not found.");
			return;
		}

		VerifyConfig runConfig;
		try {
			
			if ( selectedDataProperty == null ){
				return;
			}
			
			MonitoringConfiguration mc = selectedDataProperty.getMonitoringConfiguration();
			VARTRegressionConfiguration vartConfiguration = (VARTRegressionConfiguration) mc.getAdditionalConfiguration(VARTRegressionConfiguration.class);
			
			
			runConfig = new VerifyConfig(launch);
			runConfig.verifier = Verifier.CBMC;
			runConfig.workingDirectory = ((CRegressionConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class)).getModifiedSwSourcesFolderFile();
			runConfig.files = new LinkedList<>();
			runConfig.files.add(runConfig.workingDirectory+"/"+selectedDataProperty.getFile());
			LaunchConfigurationDelegate.setVerifyConfig( runConfig );
			
			Utils.switchPerspectiveTo(VerifyPerspective.PERSPECTIVE_ID);

			ILaunch ilaunch = launch.launch("debug", new NullProgressMonitor());

			
			String cbmcSrcFolder;
			String gotoCCprogram;
			try {
				cbmcSrcFolder = ConfigurationFilesManager.getGotoCCSrcFolderModified(mc).getAbsolutePath();
				gotoCCprogram = ConfigurationFilesManager.getGotoCCProgramModified(mc).getAbsolutePath();
			} catch (ConfigurationFilesManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			//= "/home/fabrizio/Workspaces/workspaceVART_VTT/Analysis/BCT_DATA/VA/CBMC.SRC.MODIFIED/";
			
			if ( ! cbmcSrcFolder.endsWith(File.separator) ){
				cbmcSrcFolder = cbmcSrcFolder + File.separator;
			}
			
					
			
			ShowClaimsRunner claimsRun = new ShowClaimsRunner(runConfig, ilaunch );
			claimsRun.setGotoCCprogram(gotoCCprogram);
			claimsRun.setSourceFolder(cbmcSrcFolder);
			claimsRun.run(new NullProgressMonitor());
			ClaimsView.refreshClaimsRun(claimsRun);
			
			System.out.println("LINE: "+selectedDataProperty.getInjectedLine());

			Claim claim = null;
			for ( Claim c : claimsRun.claims ){
				System.out.println(c);
				if ( selectedDataProperty.getInjectedLine() == c.location.line ){
					if ( c.location.file.startsWith(cbmcSrcFolder) ){
						String file = c.location.file.substring(cbmcSrcFolder.length());
						if ( file.equals( selectedDataProperty.getFile() ) ){
							claim = c;
							break;		
						}
					}
					
				}
				
			}
			
			if ( claim == null ){
				return;
			}
			
			ClaimCheckRunner r = new ClaimCheckRunner(runConfig, ilaunch, claim, claimsRun);
			r.setUnwind(vartConfiguration.getUnwind());
			r.setGotoCCprogram(gotoCCprogram);
			r.setSourceFolder(cbmcSrcFolder);
			r.exec();
		} catch (CoreException | InvalidRunException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





		//				ILaunchConfigurationWorkingCopy workingCopy =
		//						type.newInstance(null, nameToShow);


	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		System.out.println(selection);
		if ( selection instanceof IStructuredSelection ){
			Object selected = ((IStructuredSelection) selection).getFirstElement();
			
			if ( selected instanceof VARTDataProperty ){
				selectedDataProperty = (VARTDataProperty)selected;
			}
		}
	}

}
