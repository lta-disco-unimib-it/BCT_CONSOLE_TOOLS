/*
 * Author: atotic
 * Created on Apr 23, 2004
 * License: Common Public License v1.0
 */
package org.cprover.model;

import java.net.URI;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.ISourcePresentation;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Locates source files from stack elements
 * 
 */
public class VerifySourceLocator implements ISourceLocator, ISourcePresentation {

    public Object getSourceElement(IStackFrame stackFrame) {
        return stackFrame;
    }

	public String getEditorId( IEditorInput input, Object element ) {
		String id = null;
		if ( input != null ) {
			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor descriptor = registry.getDefaultEditor( input.getName() );
			id = (descriptor != null) ? descriptor.getId() : CUIPlugin.EDITOR_ID;
		}
		return id;
	}

	public IEditorInput getEditorInput(Object element) {
		if (element instanceof StepStackFrame) {
			StepStackFrame frame = (StepStackFrame) element;
			IFile file = null;
			assert( frame.location != null );
			IPath path = new Path( frame.location.file );
			
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation( path );
			if ( files.length > 0 ) {
				// default to the first file found in the workspace
				file = files[0];
				
				// now try to match any finding to the project in the breakpoint
				IProject project = frame.thread.config.project;
				for (IFile f : files) {
					if (f.getProject().equals(project)) {
						file = f;
						break;
					}
				}
			}
			assert( file != null );
			
			if( file == null ) {
				return null;
				// quick hack so eclipse won't crash...
			}
			return new FileEditorInput( file );
		}
		return null;
	}
}
