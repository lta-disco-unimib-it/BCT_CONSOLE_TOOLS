/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
