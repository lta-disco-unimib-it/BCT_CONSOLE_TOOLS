package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class AnomalyGraphNavigatorItem
		extends
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphAbstractNavigatorItem {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { View.class, EObject.class };
		Platform.getAdapterManager().registerAdapters(
				new IAdapterFactory() {

					public Object getAdapter(Object adaptableObject,
							Class adapterType) {
						if (adaptableObject instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem
								&& (adapterType == View.class || adapterType == EObject.class)) {
							return ((it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) adaptableObject)
									.getView();
						}
						return null;
					}

					public Class[] getAdapterList() {
						return supportedTypes;
					}
				},
				it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private View myView;

	/**
	 * @generated
	 */
	private boolean myLeaf = false;

	/**
	 * @generated
	 */
	public AnomalyGraphNavigatorItem(View view, Object parent, boolean isLeaf) {
		super(parent);
		myView = view;
		myLeaf = isLeaf;
	}

	/**
	 * @generated
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @generated
	 */
	public boolean isLeaf() {
		return myLeaf;
	}

	/**
	 * @generated
	 */
	public boolean equals(Object obj) {
		if (obj instanceof it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) {
			return EcoreUtil
					.getURI(getView())
					.equals(
							EcoreUtil
									.getURI(((it.unimib.disco.lta.eclipse.anomalyGraph.diagram.navigator.AnomalyGraphNavigatorItem) obj)
											.getView()));
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	public int hashCode() {
		return EcoreUtil.getURI(getView()).hashCode();
	}

}
