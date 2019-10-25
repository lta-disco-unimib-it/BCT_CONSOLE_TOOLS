package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.util;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphFactory;
import it.unimib.disco.lta.eclipse.anomalyGraph.Graph;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.WorkspaceEditingDomainFactory;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;

public class DiagramCreator {

	public static void createDiagram(URI uri) {
		try {
//		System.out.println("CREATE "+diagramResource);
		Graph model = AnomalyGraphFactory.eINSTANCE.createGraph();
		
//		diagramResource.getContents().add(model);
		TransactionalEditingDomain domain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();
		
		ResourceSet rs = domain.getResourceSet();
		
		Resource diagramResource = rs.createResource(uri);
		Diagram diagram = ViewService.createDiagram(
				model,
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID,
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		
		if (diagram != null) {
			diagramResource.getContents().add(diagram);
			diagram.setName("A");
			diagram.setElement(model);
		}
		System.out.println("HERE");
		

			diagramResource
			.save(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorUtil
					.getSaveOptions());
		} catch (Exception e) {
			System.out.println("E");
			e.printStackTrace();
//			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
//			.getInstance()
//			.logError(
//					"Unable to store model and diagram resources", e); //$NON-NLS-1$
		}
		System.out.println("DONE");
	}

}
