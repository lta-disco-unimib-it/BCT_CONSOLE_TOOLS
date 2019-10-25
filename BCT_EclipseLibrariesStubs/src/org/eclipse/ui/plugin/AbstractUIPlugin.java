package org.eclipse.ui.plugin;

import java.util.logging.Logger;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jface.preference.FakePreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;

public class AbstractUIPlugin extends Plugin {
	private static Logger LOGGER = Logger.getLogger("BCT-CONSOLE");
	
	public class FakeLog implements ILog {

		@Override
		public void log(IStatus status) {
			LOGGER.info(status.toString());
		}
		
	}
	

	
	public ILog getLog(){
		return new FakeLog();
	}
	

	public IPreferenceStore getPreferenceStore() {
		return new FakePreferenceStore();
	}
	

}
