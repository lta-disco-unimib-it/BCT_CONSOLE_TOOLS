package it.unimib.disco.lta.eclipse.anomalyGraph.custom.editor;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.dialogs.DialogsMessages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.ITextEditor;

import sun.util.logging.resources.logging;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.JavaResourcesUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorUtil;
import it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart;

public class OpenJavaClassAction implements IObjectActionDelegate {
	List<ShapeNodeEditPart> selected = new ArrayList<ShapeNodeEditPart>();
	private Shell shell;
	
	public OpenJavaClassAction() {
		
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		System.out.println("ACTION");
		for ( ShapeNodeEditPart sel : selected ){
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
					
					String methodInStack;
					String methodName;
					
					EObject bctViolation = (((Node)sel.getModel()).getElement());
					if ( bctViolation instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation ) {
						it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation violation = (it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation) bctViolation;
						String violatedModel = violation.getViolatedModel();
						int pos = violatedModel.lastIndexOf(":::");
						methodName = violatedModel.substring(0, pos);

					
						methodInStack = violation.getStackTrace().get(0);
						
						
						
						
					} else if ( bctViolation instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation ) {
						it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation violation = (it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation) (((Node)sel.getModel()).getElement());
						methodName = violation.getViolatedModel();	
						
						
						methodInStack = violation.getStackTrace().get(1);
						
					} else {

						Logger.getInstance().logError("Unexpected violation type "+bctViolation.getClass().getCanonicalName());
						return;

					}
					
					
					
					Method method = new Method(methodName);


					int colons = methodInStack.indexOf(':');
					Integer lineNo = Integer.valueOf( methodInStack.substring(colons+1) );
					
					
					String methodWithViolation = methodInStack.substring(0, colons);
					int pointIndex = methodWithViolation.lastIndexOf('.');
					String className = methodWithViolation.substring(0,pointIndex);
					
//					String className = method.getOwnerClassName();
					
					ICompilationUnit classFile;
					try {
						classFile = JavaResourcesUtil.getCompilationUnit(mc, className);
						
						
						if ( classFile == null ){
							Logger.getInstance().logError("Cannot open editor for class "+className+" class file is null");
							return;
						}
						
						IJavaElement element = classFile.getElementAt(lineNo);
						
						System.out.println(element.getClass().getCanonicalName());
						
						
						
						ITextEditor editor = (ITextEditor) JavaUI.openInEditor(classFile);
						
						EditorUtil.gotoLine(editor, lineNo);
						
//						classFile.getOpenable().open(new NullProgressMonitor());
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Logger.getInstance().logError("Cannot open editor for class "+className, e);
					} catch (PartInitException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Logger.getInstance().logError("Cannot open editor for class "+className, e);
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
			
			if ( obj instanceof ShapeNodeEditPart ){
				selected.add((ShapeNodeEditPart ) obj);
			}
		}
		
		 
	}

}
