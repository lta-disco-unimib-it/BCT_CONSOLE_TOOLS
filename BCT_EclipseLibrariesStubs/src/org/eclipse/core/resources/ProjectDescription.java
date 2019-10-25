package org.eclipse.core.resources;

public class ProjectDescription implements IProjectDescription {

	private String projectName;
	private Workspace workspaceRoot;

	public ProjectDescription(String project, Workspace workspace) {
		this.projectName = project;
		this.workspaceRoot = workspace;
	}

	public IWorkspace getWorkspace(){
		return workspaceRoot;
	}

	
}
