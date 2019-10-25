package org.cprover.launch;

import org.cprover.core.CProverPlugin;
import org.cprover.launch.VerifyConfig.Verifier;
import org.cprover.perspectives.VerifyPerspective;
import org.cprover.runners.LoopsRunner;
import org.cprover.runners.ShowClaimsRunner;
import org.cprover.ui.Utils;
import org.cprover.views.ClaimsView;
import org.cprover.views.LoopsView;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

/**
 * @author Gérard Basler
 * @version $Revision: 1.2 $
 */
public class LaunchConfigurationDelegate implements ILaunchConfigurationDelegate {
	
	private static VerifyConfig runConfig;

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        }

        try {
        	if ( runConfig == null ){
        		runConfig = new VerifyConfig(configuration);
        	}
        	
            if (runConfig.verifier == Verifier.CBMC) {
                monitor.beginTask("Preparing configuration", 5 + 3);
            } else {
                monitor.beginTask("Preparing configuration", 5);
            }
    		
            Utils.switchPerspectiveTo(VerifyPerspective.PERSPECTIVE_ID);

			monitor.worked(1);
			
            try {
                ShowClaimsRunner claimsRun = new ShowClaimsRunner(runConfig, launch);
                claimsRun.run(monitor);
                ClaimsView.refreshClaimsRun(claimsRun);
                monitor.worked(1);
                
                if( runConfig.verifier == Verifier.CBMC ) {
                    LoopsRunner loopsRun = new LoopsRunner(runConfig, launch, claimsRun);
                    loopsRun.run(monitor);
                    LoopsView.refreshLoopsRunner(loopsRun);
                    monitor.worked(1);
                }
            }
            catch(CoreException e) {
                Status status = CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null);
    			Utils.showErrorDialog("Error running verifier", e.toString(), status);
                CProverPlugin.log(e);
                finishLaunchWithError(launch);
//				throw new CoreException(status);
            }
			
		} catch (final InvalidRunException e) {
			Utils.showErrorDialog("Invalid launch configuration", 
                    "Unable to make launch because launch configuration is not valid", 
                    CProverPlugin.makeStatus(IStatus.ERROR, e.getMessage(), e));
            finishLaunchWithError(launch);
		}
		
		monitor.done();
	}

	private void finishLaunchWithError(ILaunch launch) {
		try {
			launch.terminate();

			ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			launchManager.removeLaunch(launch);
		} catch (Throwable x) {
			CProverPlugin.log(x);
		}
	}

	public static void setVerifyConfig(VerifyConfig _runConfig) {
		runConfig = _runConfig;
	}
}
