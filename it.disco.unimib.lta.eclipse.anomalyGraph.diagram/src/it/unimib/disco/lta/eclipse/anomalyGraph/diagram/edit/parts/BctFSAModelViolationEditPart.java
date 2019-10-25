package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts;

import it.unimib.disco.lta.eclipse.anomalyGraph.BctFSAModelViolation;
import it.unimib.disco.lta.eclipse.anomalyGraph.ViolationTypes;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class BctFSAModelViolationEditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1002;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public BctFSAModelViolationEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.policies.BctFSAModelViolationItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		FlowLayoutEditPolicy lep = new FlowLayoutEditPolicy() {

			protected Command createAddCommand(EditPart child, EditPart after) {
				return null;
			}

			protected Command createMoveChildCommand(EditPart child,
					EditPart after) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		BctFSAModelViolationFigure figure = new BctFSAModelViolationFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public BctFSAModelViolationFigure getPrimaryShape() {
		return (BctFSAModelViolationFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart) {
			((it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureBctFSAModelViolationIdFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {

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
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode()
				.DPtoLP(40), getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getType(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public class BctFSAModelViolationFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrapLabel fFigureBctFSAModelViolationIdFigure;

		/**
		 * @generated
		 */
		public BctFSAModelViolationFigure() {

			FlowLayout layoutThis = new FlowLayout();
			layoutThis.setStretchMinorAxis(false);
			layoutThis.setMinorAlignment(FlowLayout.ALIGN_LEFTTOP);

			layoutThis.setMajorAlignment(FlowLayout.ALIGN_LEFTTOP);
			layoutThis.setMajorSpacing(5);
			layoutThis.setMinorSpacing(5);
			layoutThis.setHorizontal(true);

			this.setLayoutManager(layoutThis);

			createContents();
			
			setToolTipLabel();
		}

		/**
		 * This method was not generated but added
		 */
		private void setToolTipLabel() {
			Label label = new Label();
			Node node = (Node)BctFSAModelViolationEditPart.this.getModel();
			BctFSAModelViolation viol = (BctFSAModelViolation) node.getElement();
			String violType = "";
			
			if ( viol.getViolationType().equals(ViolationTypes.PREMATURE_END) ){
				violType="Execution prematurely ended";
			} else if ( viol.getViolationType().equals(ViolationTypes.UNEXPECTED_EVENT) ){
				violType="An unexpected call was observed: "+viol.getViolation();
			}
			
			StringBuffer states = new StringBuffer();
			
			EList<String> curStates = viol.getCurrentStates();
			
			states.append("Current state");
			if ( curStates.size() > 1 ){
				states.append("s");
			}
			states.append(": ");
			
			int count = 0;
			for ( String state : curStates ){
				if ( count > 0 ){
					states.append(", ");
				}
				states.append(state);
			}
			
			EList<String> stack = viol.getStackTrace();
			String invokingMethod;
			if ( stack.size() > 1 ){
				invokingMethod = stack.get(1);
			} else {
				invokingMethod = "";
			}
			
			
			String text = "FSA Model Violation \n"+
					"Violated model: "+viol.getViolatedModel()+"\n" +
					violType+"\n"+
					states+"\n"+
					"Invoking method: "+invokingMethod;
			
			label.setText(text);
			setToolTip(label);
		}
		
		/**
		 * @generated
		 */
		private void createContents() {

			fFigureBctFSAModelViolationIdFigure = new WrapLabel();
			fFigureBctFSAModelViolationIdFigure.setText("<...>");

			this.add(fFigureBctFSAModelViolationIdFigure);

		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public WrapLabel getFigureBctFSAModelViolationIdFigure() {
			return fFigureBctFSAModelViolationIdFigure;
		}

	}

}
