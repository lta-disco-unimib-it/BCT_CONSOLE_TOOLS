package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class AnomalyGraphVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
			.getInstance().getBundle().getSymbolicName()
			+ "/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
					.equals(view.getType())) {
				return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(
					Platform.getDebugOption(DEBUG_KEY))) {
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
						.getInstance().logError(
								"Unable to parse view type as a visualID number: "
										+ type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return String.valueOf(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
				.getGraph().isSuperTypeOf(domainElement.eClass())
				&& isDiagram((it.unimib.disco.lta.eclipse.anomalyGraph.Graph) domainElement)) {
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getModelID(containerView);
		if (!it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
				.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
				.equals(containerModelID)) {
			containerVisualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID:
			if (it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
					.getBctIOModelViolation().isSuperTypeOf(
							domainElement.eClass())) {
				return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID;
			}
			if (it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
					.getBctFSAModelViolation().isSuperTypeOf(
							domainElement.eClass())) {
				return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getModelID(containerView);
		if (!it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
				.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
				.equals(containerModelID)) {
			containerVisualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationIdEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID:
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
				.getRelationship().isSuperTypeOf(domainElement.eClass())) {
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(
			it.unimib.disco.lta.eclipse.anomalyGraph.Graph element) {
		return true;
	}

}
