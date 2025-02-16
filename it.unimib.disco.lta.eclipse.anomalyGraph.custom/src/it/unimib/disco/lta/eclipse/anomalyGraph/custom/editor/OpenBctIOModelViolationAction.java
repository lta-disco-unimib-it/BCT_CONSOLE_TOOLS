package it.unimib.disco.lta.eclipse.anomalyGraph.custom.editor;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;
import it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart;

public class OpenBctIOModelViolationAction implements IObjectActionDelegate {
	List<BctIOModelViolationEditPart> selected = new ArrayList<BctIOModelViolationEditPart>();
	private Shell shell;
	
	public OpenBctIOModelViolationAction() {
		
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		System.out.println("ACTION");
		for ( BctIOModelViolationEditPart sel : selected ){
			System.out.println("Selection "+sel.getModel()+ " "+sel.getTargetConnections());
			
			
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
					
					MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getAssociatedMonitoringConfiguration(file);
					
					it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation violation = (it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation) (((Node)sel.getModel()).getElement());
					String violatedModel = violation.getViolatedModel();
					
					int pos = violatedModel.lastIndexOf(":::");
					String methodName = violatedModel.substring(0, pos);
					
					Method method = new Method(methodName);
					IoModel model = new IoModel(method, null, null);
					
					URI uri = URIBuilderFactory.getBuilder(mc).buildURI(model);
					
					EditorOpener.openEditor( shell.getDisplay(), uri);
					

					
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
			
			if ( obj instanceof BctIOModelViolationEditPart ){
				selected.add((BctIOModelViolationEditPart ) obj);
			}
		}
		
		 
	}

}
