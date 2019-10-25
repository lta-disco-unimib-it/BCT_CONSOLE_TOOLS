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
