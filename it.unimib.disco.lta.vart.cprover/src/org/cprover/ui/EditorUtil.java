package org.cprover.ui;

import java.io.FileNotFoundException;

import org.cprover.communication.Claim;
import org.eclipse.cdt.core.resources.FileStorage;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


public class EditorUtil {
	@SuppressWarnings("deprecation")
	public static void openEditor(IWorkbenchPage page,
	         ISelection selection) throws FileNotFoundException, CoreException {

      if (!(selection instanceof IStructuredSelection))
         return;

      IStructuredSelection sel = (IStructuredSelection)selection;
      Claim cl = (Claim)sel.getFirstElement();
      
      IPath path = new Path(cl.location.file);
      
      IEditorRegistry registry=PlatformUI.getWorkbench().getEditorRegistry();
      IEditorDescriptor desc = registry.getDefaultEditor(cl.location.file);
      if (desc == null) {
		desc = registry.getDefaultEditor();
      }
	
	  IStorage storage = new FileStorage(path);
	  IEditorInput input = new ExternalEditorInput(storage);	
	  
	  page.openEditor(input, desc.getId());
	}
}

