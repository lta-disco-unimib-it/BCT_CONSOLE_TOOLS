package it.unimib.disco.lta.eclipse.fsa.custom.editor;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import util.FileIndex;
import util.FileIndex.FileIndexException;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.FileSystemDataManager;

import fsa.diagram.edit.parts.TransitionDescriptionEditPart;

public class OpenTransitionDescriptionAction implements IObjectActionDelegate {
	List<TransitionDescriptionEditPart> selected = new ArrayList<TransitionDescriptionEditPart>();
	
	public OpenTransitionDescriptionAction() {
		
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		System.out.println("ACTION");
		for ( TransitionDescriptionEditPart sel : selected ){
			System.out.println("Selection "+sel.getModel()+ " "+sel.getTargetConnections());
			for ( Object c : sel.getTargetConnections() ){
				System.out.println(c + " "+ sel.getEditText()+" ");
			}
			
			ResourceSet rs = sel.getEditingDomain().getResourceSet();
			EList<Resource> resources = rs.getResources();
			if ( resources.size() > 0 ){
				System.out.println("Resources > 0 ");
				//diagram resource
				Resource diagram = rs.getResources().get(0);
				diagram.getURI().path();
				
				
				
				//System.out.println("Diagram "+file.getName()+" "+diagram.getURI().lastSegment()+" "+diagram.getURI().devicePath());
				
				//Retrieve the resource using its URI, we remove the foirst part "/resource", it is a bad hack
				//TODO: find a correct soloution to retrieve the file from a rsource
				String[] segs = diagram.getURI().segments();
				StringBuffer sb = new StringBuffer();
				for ( int i = 1; i < segs.length; ++i ){
					sb.append("/");
					sb.append(segs[i]);
				}
				
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(sb.toString()));
				
				if ( file != null ){
					IContainer parent = file.getParent();
					
					System.out.println("Container "+parent+" "+file.getFullPath());
					
						
					File indexFile = new File ( parent.getLocation().toFile(), "interactionModels.idx" );
					FileIndex fi = new FileIndex(indexFile);
					
					System.out.println(" "+sel.getEditText());
					
					try {
						final File modelToOpen = new File ( parent.getLocation().toFile(), fi.getId(sel.getEditText()) );

						if ( ! modelToOpen.exists() ){
							MessageDialog.openError(null, "Problem while retrieving model.", "The model for "+sel.getEditText()+" does not exists in index file "+indexFile.getAbsolutePath());
						} else {
						
						System.out.println("Model to open "+modelToOpen.getAbsolutePath());
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								IWorkbenchPage page =
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
								try {
									IDE.openEditor(page, ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(modelToOpen.getAbsolutePath())), true);
								} catch (PartInitException e) {
									e.printStackTrace();
								}
							}
						});
						}
					} catch (FileIndexException e1) {
						MessageDialog.openError(null, "Problem while retrieving the model.", "An exception occurred "+e1.getMessage());
					}
					
				}
			}
			Iterator<Resource> it = rs.getResources().iterator();
			while( it.hasNext() ){
				System.out.println(it.next());
				
			}
		}
//		
//		Point p = selectedElement.getFigure().getBounds().getTopRight().getCopy();
//		selectedElement.getFigure().translateToAbsolute(p);
//		int edgeCount = selectedElement.getNotationView().getSourceEdges().size();
//		// A quick hack to get subtopics to layout to the right, from top to bottom
//		int offset = (edgeCount * 50) - 100;
//		topicRequest.setLocation(p.translate(100, offset));
//
//		MapEditPart mapEditPart = (MapEditPart) selectedElement.getParent();
//
//		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if ( ! ( selection instanceof StructuredSelection ) ){
			return;
		}
		
		StructuredSelection sselection = (StructuredSelection) selection;
		
		Iterator it = sselection.iterator();
		
		selected.clear();
		while ( it.hasNext() ){
			Object obj = it.next();
			
			System.out.println("Selection: "+obj.getClass().getCanonicalName());
			
			if ( obj instanceof TransitionDescriptionEditPart ){
				selected.add((TransitionDescriptionEditPart) obj);
			}
		}
		
		 
	}

}
