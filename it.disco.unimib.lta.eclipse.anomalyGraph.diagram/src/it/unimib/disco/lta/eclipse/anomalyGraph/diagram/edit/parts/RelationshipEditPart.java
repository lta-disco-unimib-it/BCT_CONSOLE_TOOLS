package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class RelationshipEditPart extends ConnectionNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	public RelationshipEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.RelationshipItemSemanticEditPolicy());
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart) {
			((it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureRelationshipWeightFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */

	protected Connection createConnectionFigure() {
		return new RelationshipFigure();
	}

	/**
	 * @generated
	 */
	public RelationshipFigure getPrimaryShape() {
		return (RelationshipFigure) getFigure();
	}

	/**
	 * @generated
	 */
	public class RelationshipFigure extends PolylineConnectionEx {

		/**
		 * @generated
		 */
		private WrapLabel fFigureRelationshipWeightFigure;

		/**
		 * @generated
		 */
		public RelationshipFigure() {

			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureRelationshipWeightFigure = new WrapLabel();
			fFigureRelationshipWeightFigure.setText("<...>");

			this.add(fFigureRelationshipWeightFigure);

		}

		/**
		 * @generated
		 */
		public WrapLabel getFigureRelationshipWeightFigure() {
			return fFigureRelationshipWeightFigure;
		}

	}

}
