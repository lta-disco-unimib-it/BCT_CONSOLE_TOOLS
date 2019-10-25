package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.dialogs.ISelectionValidator;

public abstract class ProjectValidator implements ISelectionValidator {
	private static final String error = "Wrong selection";
	
	
	
	protected abstract String accept( IProject project );
	
	public String isValid(Object selection) {
		
		if ( selection == null ){
			return error;
		}
		
		System.out.println(selection.getClass().getName());
		if ( ! ( selection instanceof IPath ) ){
			return error;
		}
		
		
		
		IPath selectionPath = (IPath)selection;
		
		
		
//		System.out.println(selectionPath);
//		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(selectionPath);
//		System.out.println(selectionPath);
//		if ( file == null ){
//			return error;
//		}
//		
//		IProject project = file.getProject();
		
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(selectionPath.toString());
		
		
		
		System.out.println(project.getFullPath());
		if ( project.getFullPath().equals(selectionPath)){
			return accept( project );
			
			
			
		}	
		
		return error;
	}

}
