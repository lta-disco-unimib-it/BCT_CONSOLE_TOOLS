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
