package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part;

import it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage;
import it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart;
import it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart;
import it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart;
import it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class AnomalyGraphDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID:
			return getGraph_79SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getGraph_79SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		it.unimib.disco.lta.eclipse.anomalyGraph.Graph modelElement = (it.unimib.disco.lta.eclipse.anomalyGraph.Graph) view
				.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getViolations().iterator(); it
				.hasNext();) {
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation childElement = (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) it
					.next();
			int visualID = AnomalyGraphVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == BctIOModelViolationEditPart.VISUAL_ID) {
				result
						.add(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphNodeDescriptor(
								childElement, visualID));
				continue;
			}
			if (visualID == BctFSAModelViolationEditPart.VISUAL_ID) {
				result
						.add(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphNodeDescriptor(
								childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID:
			return getGraph_79ContainedLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			return getBctIOModelViolation_1001ContainedLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			return getBctFSAModelViolation_1002ContainedLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return getRelationship_3001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			return getBctIOModelViolation_1001IncomingLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			return getBctFSAModelViolation_1002IncomingLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return getRelationship_3001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			return getBctIOModelViolation_1001OutgoingLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			return getBctFSAModelViolation_1002OutgoingLinks(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return getRelationship_3001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getGraph_79ContainedLinks(View view) {
		it.unimib.disco.lta.eclipse.anomalyGraph.Graph modelElement = (it.unimib.disco.lta.eclipse.anomalyGraph.Graph) view
				.getElement();
		List result = new LinkedList();
		result
				.addAll(getContainedTypeModelFacetLinks_Relationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getBctIOModelViolation_1001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBctFSAModelViolation_1002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRelationship_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBctIOModelViolation_1001IncomingLinks(View view) {
		it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation modelElement = (it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation) view
				.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Relationship_3001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getBctFSAModelViolation_1002IncomingLinks(View view) {
		it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation modelElement = (it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation) view
				.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Relationship_3001(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRelationship_3001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBctIOModelViolation_1001OutgoingLinks(View view) {
		it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation modelElement = (it.unimib.disco.lta.eclipse.anomalyGraph.BctIOModelViolation) view
				.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingTypeModelFacetLinks_Relationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getBctFSAModelViolation_1002OutgoingLinks(View view) {
		it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation modelElement = (it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation) view
				.getElement();
		List result = new LinkedList();
		result
				.addAll(getOutgoingTypeModelFacetLinks_Relationship_3001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRelationship_3001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_Relationship_3001(
			it.unimib.disco.lta.eclipse.anomalyGraph.Graph container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getRelationships().iterator(); links
				.hasNext();) {
			Object linkObject = links.next();
			if (false == linkObject instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) {
				continue;
			}
			it.unimib.disco.lta.eclipse.anomalyGraph.Relationship link = (it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) linkObject;
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation dst = link
					.getTo();
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation src = link
					.getFrom();
			result
					.add(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphLinkDescriptor(
							src,
							dst,
							link,
							it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001,
							it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_Relationship_3001(
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation target,
			Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != AnomalyGraphPackage.eINSTANCE
					.getRelationship_To()
					|| false == setting.getEObject() instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) {
				continue;
			}
			it.unimib.disco.lta.eclipse.anomalyGraph.Relationship link = (it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) setting
					.getEObject();
			if (RelationshipEditPart.VISUAL_ID != AnomalyGraphVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation src = link
					.getFrom();
			result
					.add(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphLinkDescriptor(
							src,
							target,
							link,
							AnomalyGraphElementTypes.Relationship_3001,
							RelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_Relationship_3001(
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation source) {
		it.unimib.disco.lta.eclipse.anomalyGraph.Graph container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Graph) {
				container = (it.unimib.disco.lta.eclipse.anomalyGraph.Graph) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getRelationships().iterator(); links
				.hasNext();) {
			Object linkObject = links.next();
			if (false == linkObject instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) {
				continue;
			}
			it.unimib.disco.lta.eclipse.anomalyGraph.Relationship link = (it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) linkObject;
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID != it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation dst = link
					.getTo();
			it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation src = link
					.getFrom();
			if (src != source) {
				continue;
			}
			result
					.add(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphLinkDescriptor(
							src,
							dst,
							link,
							it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001,
							it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID));
		}
		return result;
	}

}
