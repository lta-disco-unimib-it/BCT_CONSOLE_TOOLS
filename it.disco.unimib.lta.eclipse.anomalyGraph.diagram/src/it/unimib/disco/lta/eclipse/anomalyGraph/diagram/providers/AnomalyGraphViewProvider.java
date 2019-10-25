package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class AnomalyGraphViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter,
			String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
				.equals(diagramKind)
				&& it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.getDiagramVisualID(semanticElement) != -1) {
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.GraphViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}
		IElementType elementType = getSemanticElementType(semanticAdapter);
		EObject domainElement = getSemanticElement(semanticAdapter);
		int visualID;
		if (semanticHint == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return null;
			}
			visualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram element.
				// In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
						.isKnownElementType(elementType)
						|| (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType)
						.getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be the same as in element type
				}
				if (domainElement != null
						&& visualID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
								.getNodeVisualID(containerView, domainElement)) {
					return null; // visual id for node EClass should match visual id from element type
				}
			} else {
				// Element type is not specified. Domain element should be present (except pure design elements).
				// This method is called with EObjectAdapter as parameter from:
				//   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
				//   - generated ViewFactory.decorateView() for parent element
				if (!it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
						.equals(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
								.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
				case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
					if (domainElement == null
							|| visualID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
									.getNodeVisualID(containerView,
											domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationIdEditPart.VISUAL_ID:
					if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart.VISUAL_ID:
					if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart.VISUAL_ID:
					if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
							.getVisualID(containerView)
							|| containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				default:
					return null;
				}
			}
		}
		return getNodeViewClass(containerView, visualID);
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(View containerView, int visualID) {
		if (containerView == null
				|| !it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.canCreateNode(containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.BctIOModelViolationViewFactory.class;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationIdEditPart.VISUAL_ID:
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.BctIOModelViolationIdViewFactory.class;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.BctFSAModelViolationViewFactory.class;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart.VISUAL_ID:
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.BctFSAModelViolationIdViewFactory.class;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart.VISUAL_ID:
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.RelationshipWeightViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter,
			View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (!it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
				.isKnownElementType(elementType)
				|| (!(elementType instanceof IHintedType))) {
			return null; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null) {
			return null; // our hint is visual id and must be specified
		}
		if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
			return null; // if semantic hint is specified it should be the same as in element type
		}
		int visualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(elementTypeHint);
		EObject domainElement = getSemanticElement(semanticAdapter);
		if (domainElement != null
				&& visualID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.getLinkWithClassVisualID(domainElement)) {
			return null; // visual id for link EClass should match visual id from element type
		}
		return getEdgeViewClass(visualID);
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(int visualID) {
		switch (visualID) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories.RelationshipViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return (IElementType) semanticAdapter.getAdapter(IElementType.class);
	}
}
