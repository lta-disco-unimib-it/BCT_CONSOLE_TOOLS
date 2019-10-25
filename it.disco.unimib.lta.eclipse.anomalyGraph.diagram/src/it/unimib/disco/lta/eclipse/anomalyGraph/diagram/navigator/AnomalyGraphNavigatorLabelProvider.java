package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * @generated
 */
public class AnomalyGraphNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put(
						"Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem
				&& !isOwnView(((it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) element)
						.getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorGroup) {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorGroup group = (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorGroup) element;
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
					.getInstance().getBundledImage(group.getIcon());
		}

		if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem navigatorItem = (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?it.unimib.disco.lta.eclipse.anomalyGraph?Graph", it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Graph_79); //$NON-NLS-1$
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?it.unimib.disco.lta.eclipse.anomalyGraph?BctIOModelViolation", it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctIOModelViolation_1001); //$NON-NLS-1$
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?it.unimib.disco.lta.eclipse.anomalyGraph?BctFSAModelViolation", it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctFSAModelViolation_1002); //$NON-NLS-1$
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?it.unimib.disco.lta.eclipse.anomalyGraph?Relationship", it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
				.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null
				&& elementType != null
				&& it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
						.isKnownElementType(elementType)) {
			image = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes
					.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorGroup) {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorGroup group = (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem navigatorItem = (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
				.getVisualID(view)) {
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.VISUAL_ID:
			return getGraph_79Text(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationEditPart.VISUAL_ID:
			return getBctIOModelViolation_1001Text(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationEditPart.VISUAL_ID:
			return getBctFSAModelViolation_1002Text(view);
		case it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID:
			return getRelationship_3001Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getGraph_79Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getBctIOModelViolation_1001Text(View view) {
		IAdaptable hintAdapter = new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphParserProvider.HintAdapter(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctIOModelViolation_1001,
				(view.getElement() != null ? view.getElement() : view),
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.getType(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctIOModelViolationIdEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 4001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getBctFSAModelViolation_1002Text(View view) {
		IAdaptable hintAdapter = new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphParserProvider.HintAdapter(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.BctFSAModelViolation_1002,
				(view.getElement() != null ? view.getElement() : view),
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.getType(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.BctFSAModelViolationIdEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 4002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getRelationship_3001Text(View view) {
		IAdaptable hintAdapter = new it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphParserProvider.HintAdapter(
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.providers.AnomalyGraphElementTypes.Relationship_3001,
				(view.getElement() != null ? view.getElement() : view),
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.getType(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart.VISUAL_ID));
		IParser parser = ParserService.getInstance().getParser(hintAdapter);

		if (parser != null) {
			return parser.getPrintString(hintAdapter, ParserOptions.NONE
					.intValue());
		} else {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 4003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.GraphEditPart.MODEL_ID
				.equals(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
						.getModelID(view));
	}

}
