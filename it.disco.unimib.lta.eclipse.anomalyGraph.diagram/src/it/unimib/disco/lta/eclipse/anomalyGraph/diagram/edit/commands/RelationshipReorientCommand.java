package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

/**
 * @generated
 */
public class RelationshipReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public RelationshipReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (!(getElementToEdit() instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Relationship)) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation && newEnd instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation)) {
			return false;
		}
		it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation target = getLink()
				.getTo();
		if (!(getLink().eContainer() instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Graph)) {
			return false;
		}
		it.unimib.disco.lta.eclipse.anomalyGraph.Graph container = (it.unimib.disco.lta.eclipse.anomalyGraph.Graph) getLink()
				.eContainer();
		return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.AnomalyGraphBaseItemSemanticEditPolicy.LinkConstraints
				.canExistRelationship_3001(container, getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation && newEnd instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation)) {
			return false;
		}
		it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation source = getLink()
				.getFrom();
		if (!(getLink().eContainer() instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Graph)) {
			return false;
		}
		it.unimib.disco.lta.eclipse.anomalyGraph.Graph container = (it.unimib.disco.lta.eclipse.anomalyGraph.Graph) getLink()
				.eContainer();
		return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.AnomalyGraphBaseItemSemanticEditPolicy.LinkConstraints
				.canExistRelationship_3001(container, source, getNewTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setFrom(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTo(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.Relationship getLink() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation getOldSource() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) oldEnd;
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation getNewSource() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) newEnd;
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation getOldTarget() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) oldEnd;
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation getNewTarget() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) newEnd;
	}
}
