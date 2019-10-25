package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

/**
 * @generated
 */
public class GraphItemSemanticEditPolicy
		extends
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.AnomalyGraphBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctIOModelViolation_1001 == req
				.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req
						.setContainmentFeature(it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
								.getGraph_Violations());
			}
			return getGEFWrapper(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands.BctIOModelViolationCreateCommand(
					req));
		}
		if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctFSAModelViolation_1002 == req
				.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req
						.setContainmentFeature(it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
								.getGraph_Violations());
			}
			return getGEFWrapper(new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.commands.BctFSAModelViolationCreateCommand(
					req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
				.getEditingDomain();
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends
			DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(
				TransactionalEditingDomain editingDomain,
				DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req
					.getElementsToBeDuplicated(), req
					.getAllDuplicatedElementsMap());
		}

	}

}
