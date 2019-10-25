package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class AnomalyGraphParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser bctIOModelViolationId_4001Parser;

	/**
	 * @generated
	 */
	private IParser getBctIOModelViolationId_4001Parser() {
		if (bctIOModelViolationId_4001Parser == null) {
			bctIOModelViolationId_4001Parser = createBctIOModelViolationId_4001Parser();
		}
		return bctIOModelViolationId_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createBctIOModelViolationId_4001Parser() {
		EAttribute[] features = new EAttribute[] { it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
				.getBctModelViolation_Id(), };
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.parsers.MessageFormatParser parser = new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.parsers.MessageFormatParser(
				features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser bctFSAModelViolationId_4002Parser;

	/**
	 * @generated
	 */
	private IParser getBctFSAModelViolationId_4002Parser() {
		if (bctFSAModelViolationId_4002Parser == null) {
			bctFSAModelViolationId_4002Parser = createBctFSAModelViolationId_4002Parser();
		}
		return bctFSAModelViolationId_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createBctFSAModelViolationId_4002Parser() {
		EAttribute[] features = new EAttribute[] { it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
				.getBctModelViolation_Id(), };
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.parsers.MessageFormatParser parser = new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.parsers.MessageFormatParser(
				features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser relationshipWeight_4003Parser;

	/**
	 * @generated
	 */
	private IParser getRelationshipWeight_4003Parser() {
		if (relationshipWeight_4003Parser == null) {
			relationshipWeight_4003Parser = createRelationshipWeight_4003Parser();
		}
		return relationshipWeight_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createRelationshipWeight_4003Parser() {
		EAttribute[] features = new EAttribute[] { it.unimib.disco.lta.eclipse.anomalyGraph.AnomalyGraphPackage.eINSTANCE
				.getRelationship_Weight(), };
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.parsers.MessageFormatParser parser = new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.parsers.MessageFormatParser(
				features);
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationIdEditPart.VISUAL_ID:
			return getBctIOModelViolationId_4001Parser();
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart.VISUAL_ID:
			return getBctFSAModelViolationId_4002Parser();
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart.VISUAL_ID:
			return getRelationshipWeight_4003Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
					.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
