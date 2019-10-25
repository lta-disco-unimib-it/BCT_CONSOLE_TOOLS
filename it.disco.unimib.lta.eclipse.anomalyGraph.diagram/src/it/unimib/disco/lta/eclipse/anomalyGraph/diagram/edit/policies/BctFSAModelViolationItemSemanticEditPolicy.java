package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class BctFSAModelViolationItemSemanticEditPolicy
		extends
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.AnomalyGraphBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = getDestroyEdgesCommand();
		addDestroyShortcutsCommand(cc);
		View view = (View) getHost().getModel();
		if (view.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
			req.setElementToDestroy(view);
		}
		cc.add(getGEFWrapper(new DestroyElementCommand(req)));
		return cc.unwrap();
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super
				.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(
			CreateRelationshipRequest req) {
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001 == req
				.getElementType()) {
			return getGEFWrapper(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands.RelationshipCreateCommand(
					req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(
			CreateRelationshipRequest req) {
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001 == req
				.getElementType()) {
			return getGEFWrapper(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands.RelationshipCreateCommand(
					req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(
			ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return getGEFWrapper(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands.RelationshipReorientCommand(
					req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
