package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class RelationshipViewFactory extends ConnectionViewFactory {

	/**
	 * @generated
	 */
	protected List createStyles(View view) {
		List styles = new ArrayList();
		styles.add(NotationFactory.eINSTANCE.createConnectorStyle());
		styles.add(NotationFactory.eINSTANCE.createFontStyle());
		return styles;
	}

	/**
	 * @generated
	 */
	protected void decorateView(View containerView, View view,
			IAdaptable semanticAdapter, String semanticHint, int index,
			boolean persisted) {
		if (semanticHint == null) {
			semanticHint = it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getType(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipEditPart.VISUAL_ID);
			view.setType(semanticHint);
		}
		super.decorateView(containerView, view, semanticAdapter, semanticHint,
				index, persisted);
		IAdaptable eObjectAdapter = null;
		EObject eObject = (EObject) semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			eObjectAdapter = new EObjectAdapter(eObject);
		}
		getViewService()
				.createNode(
						eObjectAdapter,
						view,
						it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
								.getType(it.unimib.disco.lta.eclipse.anomalyGraph.diagram.edit.parts.RelationshipWeightEditPart.VISUAL_ID),
						ViewUtil.APPEND, true, getPreferencesHint());
	}
}
