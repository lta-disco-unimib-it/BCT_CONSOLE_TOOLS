package org.cprover.core;

import java.io.IOException;
import java.net.URL;

import org.cprover.bundle.BundleInfo;
import org.cprover.bundle.IBundleInfo;
import org.cprover.bundle.ImageCache;
import org.cprover.ui.Constants;
import org.cprover.views.ClaimsFileFilter;
import org.cprover.views.ClaimsFilter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CProverPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.cprover.plugin";

	// The shared instance
	private static CProverPlugin plugin;
	
	public static ClaimsFilter claimsFilter = new ClaimsFilter();
	public static ClaimsFileFilter claimsFileFilter = new ClaimsFileFilter();	
	
    public static IBundleInfo info;
    public static IBundleInfo getBundleInfo(){
        if(info == null){
            info = new BundleInfo(getDefault().getBundle());
        }
        return info;
    }
    public static void setBundleInfo(IBundleInfo b){
        info = b;
    }
	
	/**
	 * The constructor
	 */
	public CProverPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// oct-nan
		/*if(!isWindowsOS()) {
			//String workingDir = CProverPlugin.getInstallationDir();
			String wrnMsg = "CProver plugin failed to execute the commands:\n" +
				"chmod +x cbmc\n" +
				"chmod +x hw-cbmc\n" +
				"chmod +x satabs\n" +
				"chmod +x ebmc\n" +
				"Please make sure the files \'cbmc\', \'hw-cbmc\', \'satabs\',\'ebmc\' have been downloaded, installed in the Path and have execution permissions.\n" +
				"Download CBMC/HW-CBMC, please visit http://www.cprover.org/cbmc/ \n" +
				"Download SATABS, please visit http://www.cprover.org/satabs/ \n" + 
				"Download EBMC, please visit http://www.cprover.org/ebmc/ \n";
			try{
				if (CProverPlugin.runCommand("chmod +x cbmc") != 0 ||
						CProverPlugin.runCommand("chmod +x hw-cbmc") != 0 ||
						CProverPlugin.runCommand("chmod +x satabs") != 0 ||
						CProverPlugin.runCommand("chmod +x ebmc") != 0)
					; //showWarning(wrnMsg);
			}
			catch (Exception e) {
				showWarning(wrnMsg);
			}
		}*/
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Get the currently active workbench page from the active workbench window.
	 * 
	 * @return currently active workbench page
	 */
	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow w = getActiveWorkbenchWindow();
		if (w != null) {
			return w.getActivePage();
		}
		return null;
	}

	/**
	 * Gets the currently active workbench window
	 * 
	 * @return the active workbench window
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}
	
	/**
	 * Returns the active workbench shell
	 * 
	 * @return the active workbench shell or <code>null</code>.
	 */
	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow workBenchWindow = getActiveWorkbenchWindow();
		if (workBenchWindow == null) {
			return null;
		}
		return workBenchWindow.getShell();
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static CProverPlugin getDefault() {
		return plugin;
	}

    public static String getPluginID() {
        return getDefault().getBundle().getSymbolicName();
    }
    
	/**
	 * Get the display instance.
	 * 
	 * @return the display instance
	 */
	public static Display getDisplay() {
		Display display= Display.getCurrent();
		if (display == null) {
			display= Display.getDefault();
		}
		return display;		
	}
	
//	public static ClaimsView getClaimsView() {
//		ClaimsView view = (ClaimsView)getActivePage().findView(ClaimsView.ID_CLAIMS_VIEW);
//		if (view == null) {
//			showView(ClaimsView.ID_CLAIMS_VIEW);
//			view = (ClaimsView)getActivePage().findView(ClaimsView.ID_CLAIMS_VIEW);
////			view.refresh();
//		}
//		return view;
//	}
//	
//	public static void showView(String viewName) {
//		try {
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewName);
//		} catch (PartInitException e) {
//			log(e);
//		} 
//	}
	
	/**
	 * Get a unique identifier for this plugin (used for logging)
	 * 
	 * @return unique identifier
	 */
	public static String getUniqueIdentifier() {
		if (getDefault() == null)
			return PLUGIN_ID;

		return getDefault().getBundle().getSymbolicName();
	}
	
	/**
	 * Generate a log message
	 * 
	 * @param msg message to log
	 */
	public static void log(String msg) {
		log(makeStatus(IStatus.ERROR, msg, null));
	}

    public static CoreException logWithException(String msg) {
	    IStatus s = CProverPlugin.makeStatus(IStatus.ERROR, msg, new RuntimeException(msg));
	    CoreException e = new CoreException(s);
	    CProverPlugin.log(e);
	    return e;
	}

	/**
	 * Generate a log message given an IStatus object
	 * 
	 * @param status IStatus object
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static Status makeStatus(int errorLevel, String message, Throwable e) {
	    return new Status(errorLevel, getPluginID(), errorLevel, message, e);
	}

	/**
	 * Generate a log message for an exception
	 * 
	 * @param e exception used to generate message
	 */
	public static void log(Throwable e) {
		log(new Status(IStatus.ERROR, getUniqueIdentifier(), Constants.INTERNAL_ERROR, "Internal Error", e)); //$NON-NLS-1$
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
    /**
     * @return the cache that should be used to access images within the pydev plugin.
     */
    public static ImageCache getImageCache(){
        return getBundleInfo().getImageCache();
    }
    
	public static void reset() {
		// TODO dummy for horatius crap 
	}
	
	public static String getOSName() {
		return System.getProperty("os.name");
	}
	
	public static boolean isWindowsOS() {
		return getOSName().toLowerCase().indexOf("windows") > -1 || 
			getOSName().toLowerCase().indexOf("nt") > -1;
	}
	
	public static String getInstallationDir() {
		URL url = FileLocator.find(Platform.getBundle("org.plugin.CProver"), new Path("/"), null);
		String dir = "";
		if( url != null) {
			dir = FileLocator.find(url).getFile();
			dir = new Path(dir).toOSString();
		}
		return dir;
	}
	
	public static void showError(Throwable ex) {
		ex.printStackTrace();
	}
	
    public static void showWarning(String msg) {
    	MessageDialog.openWarning(Display.getDefault().getActiveShell(), "CProver - Warning", msg);
    }
    
	public static int runCommand(String cmd) throws Exception {
		Process proc;
		//if (isWindowsOS()) {
		proc = DebugPlugin.exec(new String[]{cmd}, null, null);
		/*}
		else {
			proc = DebugPlugin.exec(new String[]{"cmd.exe", "/C", cmd}, null, null);
		}*/
		proc.waitFor();
		int exit_val = proc.exitValue();
		proc.destroy();
		return exit_val;
	}
}
