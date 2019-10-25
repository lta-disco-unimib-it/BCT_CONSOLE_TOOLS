package org.eclipse.core.runtime;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public class Plugin {

	public Bundle getBundle(){
		return new EclipseBundle();
	}
	
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
