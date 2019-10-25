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
package org.eclipse.core.runtime;

public class Status implements IStatus {

	private Throwable exception;
	private String message;
	private String pluginId;
	private int status;

	public Status(int error, String pluginId, String string) {
		this(error,pluginId,string,null);
	}
	
	public Status(int error, String pluginId, String string, Throwable e) {
		this.status = error;
		this.pluginId = pluginId;
		this.message = string;
		this.exception = e;
	}

	public Throwable getException() {
		return exception;
	}

	public String getMessage() {
		return message;
	}

	public String getPluginId() {
		return pluginId;
	}

	public int getStatus() {
		return status;
	}
	
	public String toString(){
		return "Status:"+status+" PlugIn:"+pluginId+" message:"+message;
	}

}
