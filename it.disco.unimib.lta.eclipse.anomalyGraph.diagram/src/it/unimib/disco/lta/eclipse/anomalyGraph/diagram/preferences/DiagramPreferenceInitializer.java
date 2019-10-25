package it.unimib.disco.lta.eclipse.anomalyGraph.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @generated
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * @generated
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.preferences.DiagramPrintingPreferencePage
				.initDefaults(store);
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.preferences.DiagramGeneralPreferencePage
				.initDefaults(store);
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.preferences.DiagramAppearancePreferencePage
				.initDefaults(store);
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.preferences.DiagramConnectionsPreferencePage
				.initDefaults(store);
		it.unimib.disco.lta.eclipse.anomalyGraph.diagram.preferences.DiagramRulersAndGridPreferencePage
				.initDefaults(store);
	}

	/**
	 * @generated
	 */
	protected IPreferenceStore getPreferenceStore() {
		return it.unimib.disco.lta.eclipse.anomalyGraph.diagram.part.AnomalyGraphDiagramEditorPlugin
				.getInstance().getPreferenceStore();
	}
}
