package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @generated
 */
public class AnomalyGraphNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 3003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) {
			it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem item = (it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) element;
			return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphVisualIDRegistry
					.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
