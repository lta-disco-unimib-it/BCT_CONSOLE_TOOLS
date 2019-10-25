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
