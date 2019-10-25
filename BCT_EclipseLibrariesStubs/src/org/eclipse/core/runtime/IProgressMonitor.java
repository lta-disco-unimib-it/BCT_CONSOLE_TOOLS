package org.eclipse.core.runtime;

public interface IProgressMonitor {

	public void beginTask( String taskName, int delay );
	
	public void subTask( String taskName );
	
	public void worked( int taskNum );

	public boolean isCanceled();

	void done();

	void internalWorked(double work);

	void setCanceled(boolean value);

	void setTaskName(String name);
	
}
