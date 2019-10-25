package it.unimib.disco.lta.eclipse.vart;

import it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.CDTStandaloneCFileAnalyzer;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import cpp.gdb.CSourceAnalyzerRegistry;

/**
 * The activator class controls the plug-in life cycle
 */
public class VART extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "it.unimib.disco.lta.eclipse.vart"; //$NON-NLS-1$

	// The shared instance
	private static VART plugin;
	
	/**
	 * The constructor
	 */
	public VART() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		CDTStandaloneCFileAnalyzer analyzer = new CDTStandaloneCFileAnalyzer();
		CSourceAnalyzerRegistry.setCSourceAnalyzer( analyzer );
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
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static VART getDefault() {
		return plugin;
	}

}
