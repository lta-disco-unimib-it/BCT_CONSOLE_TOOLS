package fsa.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import fsa.diagram.part.FsaVisualIDRegistry;

/**
 * @generated
 */
public class FsaNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 3003;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof FsaNavigatorItem) {
			FsaNavigatorItem item = (FsaNavigatorItem) element;
			return FsaVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
