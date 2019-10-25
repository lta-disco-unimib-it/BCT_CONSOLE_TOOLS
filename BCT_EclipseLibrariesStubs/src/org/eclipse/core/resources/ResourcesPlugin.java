package org.eclipse.core.resources;

import java.io.File;

public class ResourcesPlugin {
	
	private static File workspace;

	public static void setWorkspace(File workspace){
		ResourcesPlugin.workspace = workspace;
	}

	public static IWorkspace getWorkspace(){
		return new Workspace( workspace );
	}
}
