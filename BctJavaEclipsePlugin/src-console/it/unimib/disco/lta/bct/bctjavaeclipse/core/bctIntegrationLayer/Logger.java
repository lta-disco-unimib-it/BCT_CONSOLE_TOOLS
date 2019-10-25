package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class Logger {
	private static Logger logger = null;
	private ILog log;
	
	private Logger() {
		log = BctJavaEclipsePlugin.getDefault().getLog();
//		log = new FakeLogger();
	}
	
	public static Logger getInstance() {
		if (logger == null)
			logger = new Logger();
		return logger;
	}
	
	public void log(Throwable e) {
		IStatus status = new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, "Unexpected exception occured", e);
		log.log(status);
	}
	
	public void logError(String message) {
		IStatus status = new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, message);
		log.log(status);
	}
	
	public void logError(String message,Throwable e) {
		IStatus status = new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, message, e);
		log.log(status);
	}
	
	public void logWarning(String message) {
		IStatus status = new Status(IStatus.WARNING, BctJavaEclipsePlugin.PLUGIN_ID, message );
		log.log(status);
	}
	
	public void logWarning(String message,Throwable e) {
		IStatus status = new Status(IStatus.WARNING, BctJavaEclipsePlugin.PLUGIN_ID, message, e);
		log.log(status);
	}
	
	public void logInfo(String message,Throwable e) {
		IStatus status = new Status(IStatus.INFO, BctJavaEclipsePlugin.PLUGIN_ID, message, e);
		log.log(status);
	}
	
	public void logOk(String message,Throwable e) {
		IStatus status = new Status(IStatus.OK, BctJavaEclipsePlugin.PLUGIN_ID, message, e);
		log.log(status);
	}
	
	public void logCancel(String message,Throwable e) {
		IStatus status = new Status(IStatus.CANCEL, BctJavaEclipsePlugin.PLUGIN_ID, message, e);
		log.log(status);
	}
}
