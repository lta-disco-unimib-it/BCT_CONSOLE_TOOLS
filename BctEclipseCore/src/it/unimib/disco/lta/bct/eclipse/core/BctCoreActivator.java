package it.unimib.disco.lta.bct.eclipse.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import conf.EnvironmentalSetter;

/**
 * The activator class controls the plug-in life cycle
 */
public class BctCoreActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "BctEclipseCore";

	// The shared instance
	private static BctCoreActivator plugin;
	
	/**
	 * The constructor
	 */
	public BctCoreActivator() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		EnvironmentalSetter.setBctHome("");
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BctCoreActivator getDefault() {
		return plugin;
	}

}
