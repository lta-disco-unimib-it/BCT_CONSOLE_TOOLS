package fsa.diagram.providers;

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

import fsa.FsaPackage;
import fsa.diagram.edit.parts.StateNameEditPart;
import fsa.diagram.edit.parts.TransitionDescriptionEditPart;
import fsa.diagram.parsers.MessageFormatParser;
import fsa.diagram.part.FsaVisualIDRegistry;

/**
 * @generated
 */
public class FsaParserProvider extends AbstractProvider implements
		IParserProvider {

	/**
	 * @generated
	 */
	private IParser stateName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getStateName_4001Parser() {
		if (stateName_4001Parser == null) {
			stateName_4001Parser = createStateName_4001Parser();
		}
		return stateName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createStateName_4001Parser() {
		EAttribute[] features = new EAttribute[] { FsaPackage.eINSTANCE
				.getState_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser transitionDescription_4002Parser;

	/**
	 * @generated
	 */
	private IParser getTransitionDescription_4002Parser() {
		if (transitionDescription_4002Parser == null) {
			transitionDescription_4002Parser = createTransitionDescription_4002Parser();
		}
		return transitionDescription_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createTransitionDescription_4002Parser() {
		EAttribute[] features = new EAttribute[] { FsaPackage.eINSTANCE
				.getTransition_Description(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case StateNameEditPart.VISUAL_ID:
			return getStateName_4001Parser();
		case TransitionDescriptionEditPart.VISUAL_ID:
			return getTransitionDescription_4002Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(FsaVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(FsaVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (FsaElementTypes.getElement(hint) == null) {
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
