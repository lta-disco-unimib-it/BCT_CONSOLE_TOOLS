package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

/**
 * @generated
 */
public class RelationshipCreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	private it.unimib.disco.lta.eclipse.anomalyGraph.Graph container;

	/**
	 * @generated
	 */
	public RelationshipCreateCommand(CreateRelationshipRequest request,
			EObject source, EObject target) {
		super(request);
		this.source = source;
		this.target = target;
		if (request.getContainmentFeature() == null) {
			setContainmentFeature(it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
					.getGraph_Relationships());
		}

		// Find container element for the new link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null; element = element
				.eContainer()) {
			if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.Graph) {
				container = (it.unimib.disco.lta.eclipse.anomalyGraph.Graph) element;
				super.setElementToEdit(container);
				break;
			}
		}
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null
				&& !(source instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation)) {
			return false;
		}
		if (target != null
				&& !(target instanceof it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation)) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		// target may be null here but it's possible to check constraint
		if (getContainer() == null) {
			return false;
		}
		return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.AnomalyGraphBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateRelationship_3001(getContainer(), getSource(),
						getTarget());
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		// it.unimib.disco.lta.eclipse.anomalyGraph.Relationship newElement = (it.unimib.disco.lta.eclipse.anomalyGraph.Relationship) super.doDefaultElementCreation();
		it.unimib.disco.lta.eclipse.anomalyGraph.Relationship newElement = it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphFactory.eINSTANCE
				.createRelationship();
		getContainer().getRelationships().add(newElement);
		newElement.setFrom(getSource());
		newElement.setTo(getTarget());
		return newElement;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
				.getGraph();
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}
		return super.doExecuteWithResult(monitor, info);
	}

	/**
	 * @generated
	 */
	protected ConfigureRequest createConfigureRequest() {
		ConfigureRequest request = super.createConfigureRequest();
		request.setParameter(CreateRelationshipRequest.SOURCE, getSource());
		request.setParameter(CreateRelationshipRequest.TARGET, getTarget());
		return request;
	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation getSource() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) source;
	}

	/**
	 * @generated
	 */
	protected it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation getTarget() {
		return (it.unimib.disco.lta.eclipse.anomalyGraph.BctModelViolation) target;
	}

	/**
	 * @generated
	 */
	public it.unimib.disco.lta.eclipse.anomalyGraph.Graph getContainer() {
		return container;
	}
}
