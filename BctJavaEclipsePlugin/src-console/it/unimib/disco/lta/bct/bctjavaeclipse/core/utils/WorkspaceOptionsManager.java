package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.preferences.PreferenceConstants;

import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;

import conf.InvariantGeneratorSettings;

/**
 * This class manages options which are relative to the current workspace
 * 
 * @author Fabrizio Pastore
 *
 */
public class WorkspaceOptionsManager {

	private static IPreferenceStore getPreferenceStore(){
		return BctJavaEclipsePlugin.getDefault().getPreferenceStore();
	}
	
	/**
	 * Returns the BctOptions as configured by the user in the preference window.
	 * pay attention the returned object won't reflect future changes in the preferences, to have an updated version of the options call again this method
	 * 
	 * @return
	 * @throws CoreException
	 * @throws DefaultOptionsManagerException
	 */
	public static BctDefaultOptions getWorkspaceOptions() throws CoreException, DefaultOptionsManagerException{
		IPreferenceStore store = getPreferenceStore();
		
		BctDefaultOptions defaultOptions = DefaultOptionsManager.getDefaultOptions();
		
		BctDefaultOptions workspaceOptions = new BctDefaultOptions(
				defaultOptions.getFlattenerOptions(),
				defaultOptions.getInvariantGeneratorOptions(),
				defaultOptions.getKbehaviorInferenceEngineOptions(),
				defaultOptions.getKtailEnigineOptions(),
				defaultOptions.getReissEnigineOptions(),
				defaultOptions.getKinclusionEnigineOptions(),
				defaultOptions.getViolationsRecorderOptions(),
				defaultOptions.getDaikonConfigs()
				);
		

		
		updateFlattenerOptions( store, workspaceOptions );
		
		updateInvariantGeneratorOptions( store,  workspaceOptions );
		
		updateKbehaviorInferenceEngineOptions( store,  workspaceOptions );
		
		updateKtailEnigineOptions( store,  workspaceOptions );
		
		updateReissEnigineOptions( store,  workspaceOptions );
		
		updateKinclusionEnigineOptions( store,  workspaceOptions );
		
		updateViolationsRecorderOptions( store, workspaceOptions );
		

		
		return workspaceOptions;
	}

	/**
	 * This method update the violations options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateViolationsRecorderOptions(IPreferenceStore store,
			BctDefaultOptions workspaceOptions) {
		
	}

	/**
	 * This method update the kinclusion options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateKinclusionEnigineOptions(IPreferenceStore store,
			BctDefaultOptions workspaceOptions) {
		Properties opts = workspaceOptions.getKinclusionEnigineOptions();
		String value = store.getString(PreferenceConstants.KInclusionLogger);
		if ( isSet(value) )
		opts.setProperty( "logger" , value );
		
		value= store.getString(PreferenceConstants.KInclusionLevel);
		if ( isSet(value) )
		opts.setProperty( "level" , value );
		
		value = store.getString(PreferenceConstants.KInclusionEnableMinimization);
		if ( isSet(value) )
		opts.setProperty( "enableMinimization" , value );
		
		value = store.getString(PreferenceConstants.KInclusionMinTrustLen);
		if ( isSet(value) )
		opts.setProperty( "minTrustLen", value );
		
		workspaceOptions.setKinclusionEnigineOptions(opts);
	}

	/**
	 * This method update the reiss options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateReissEnigineOptions(IPreferenceStore store,
			BctDefaultOptions workspaceOptions) {
		
			Properties opts = workspaceOptions.getReissEnigineOptions(); 
			
			String value = store.getString(PreferenceConstants.ReissLogger);
			if ( isSet(value) )
				opts.setProperty( "logger" , value );

			value = store.getString(PreferenceConstants.ReissLevel);
			if ( isSet(value) )
				opts.setProperty( "level" , value );

			value =  store.getString(PreferenceConstants.ReissEnableMinimization);
			if ( isSet(value) )
				opts.setProperty( "enableMinimization" , value);

			value = store.getString(PreferenceConstants.ReissMinTrustLen);
			if ( isSet(value) )
				opts.setProperty( "minTrustLen", value );

			workspaceOptions.setReissEnigineOptions(opts);
	}

	/**
	 * This method update the ktail options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateKtailEnigineOptions(IPreferenceStore store,
			BctDefaultOptions workspaceOptions) {
		Properties opts = workspaceOptions.getKtailEnigineOptions();
		
		String value = store.getString(PreferenceConstants.KTailLogger);
		if ( isSet(value) )
		opts.setProperty( "logger" , value);
		
		value = store.getString(PreferenceConstants.KTailLevel);
		if ( isSet(value) )
		opts.setProperty( "level" , value );
		
		value = store.getString(PreferenceConstants.KTailEnableMinimization);
		if ( isSet(value) )
		opts.setProperty( "enableMinimization" , value);
		
		value = store.getString(PreferenceConstants.KTailMinTrustLen);
		if ( isSet(value) )
		opts.setProperty( "minTrustLen", value );
		
		workspaceOptions.setKtailEnigineOptions(opts);
	}


	/**
	 * This method update the kbehavior options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateKbehaviorInferenceEngineOptions(IPreferenceStore store,
			BctDefaultOptions workspaceOptions) {
		Properties opts = workspaceOptions.getKbehaviorInferenceEngineOptions(); 
		
		String value = store.getString(PreferenceConstants.KbehaviorLogger);
		if ( isSet(value) )
		opts.setProperty( "logger" , value);
		
		value =store.getString(PreferenceConstants.KbehaviorLevel);
		if ( isSet(value) )
		opts.setProperty( "level" , value );
		
		value =store.getString(PreferenceConstants.KbehaviorEnableMinimization);
		if ( isSet(value) )
		opts.setProperty( "enableMinimization" , value );
		
		value =store.getString(PreferenceConstants.KbehaviorMinTrustLen);
		if ( isSet(value) )
		opts.setProperty( "minTrustLen",value  );
		
		value =store.getString(PreferenceConstants.KbehaviorMaxTrustLen);
		if ( isSet(value) )
		opts.setProperty("maxTrustLen",value);
		
		value =store.getString(PreferenceConstants.KbehaviorCutoffSearch);
		if ( isSet(value) )
		opts.setProperty("cutOffSearch",value);
		
		value =store.getString(PreferenceConstants.KbehaviorStepSave);
		if ( isSet(value) )
		opts.setProperty("stepSave",value); 
		
		
		//set
		workspaceOptions.setKbehaviorInferenceEngineOptions(opts);
	}

	/**
	 * This method update the invariant generator options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateInvariantGeneratorOptions(
			IPreferenceStore store, BctDefaultOptions workspaceOptions) {
		

		Properties opts = workspaceOptions.getInvariantGeneratorOptions(); 
		
		String value = store.getString(PreferenceConstants.DaikonPath);
		
		if ( isSet(value) )
		opts.setProperty( InvariantGeneratorSettings.Options.daikonPath , value );
		
		value = store.getString(PreferenceConstants.DaikonConfig);
		if ( isSet(value) )
			opts.setProperty( InvariantGeneratorSettings.Options.daikonConfig , value );

		value=store.getString(PreferenceConstants.FsaEngine);
		if ( isSet(value) )
			opts.setProperty( InvariantGeneratorSettings.Options.fsaEngine , value );

		value=store.getString(PreferenceConstants.AddAdditionalInvariants);
		if ( isSet(value) )
			opts.setProperty( InvariantGeneratorSettings.Options.addAdditionalInvariants , value );

		value=store.getString(PreferenceConstants.ExpandeReferences);
		if ( isSet(value) )
			opts.setProperty( InvariantGeneratorSettings.Options.expandReferences , value );
		
		
		//set
		workspaceOptions.setInvariantGeneratorOptions(opts);
	}
	
	/**
	 * Returns true if the value represent a valid value and not the preference store default
	 * @param value
	 * @return
	 */
	private static boolean isSet(String value) {
		return (value!=null && value.length()>0);
	}

	/**
	 * Set the passed invariantGeneratorOptions as the current workspace option.
	 * 
	 * @param invariantGeneratorOptions
	 */
	public static void setInvariantGeneratorOptions(Properties invariantGeneratorOptions){
		IPreferenceStore preferenceStore = getPreferenceStore();
		
		preferenceStore.setValue(PreferenceConstants.DaikonPath, invariantGeneratorOptions.getProperty(InvariantGeneratorSettings.Options.daikonPath) );
		preferenceStore.setValue(PreferenceConstants.DaikonConfig, invariantGeneratorOptions.getProperty(InvariantGeneratorSettings.Options.daikonConfig));
		preferenceStore.setValue(PreferenceConstants.FsaEngine, invariantGeneratorOptions.getProperty(InvariantGeneratorSettings.Options.fsaEngine));
		preferenceStore.setValue(PreferenceConstants.AddAdditionalInvariants,invariantGeneratorOptions.getProperty(InvariantGeneratorSettings.Options.addAdditionalInvariants));
		preferenceStore.setValue(PreferenceConstants.ExpandeReferences ,invariantGeneratorOptions.getProperty(InvariantGeneratorSettings.Options.expandReferences));
	}

	public static void setKbehaviorInferenceEngineOptions(Properties kBehaviorOptions){
		IPreferenceStore preferenceStore = getPreferenceStore();
		
		preferenceStore.setValue(PreferenceConstants.KbehaviorLogger, kBehaviorOptions.getProperty("logger"));
		preferenceStore.setValue(PreferenceConstants.KbehaviorLevel , kBehaviorOptions.getProperty("level"));
		preferenceStore.setValue(PreferenceConstants.KbehaviorEnableMinimization, kBehaviorOptions.getProperty("enableMinimization"));
		preferenceStore.setValue(PreferenceConstants.KbehaviorMinTrustLen,kBehaviorOptions.getProperty("minTrustLen"));
		preferenceStore.setValue(PreferenceConstants.KbehaviorMaxTrustLen,kBehaviorOptions.getProperty("maxTrustLen"));
		preferenceStore.setValue(PreferenceConstants.KbehaviorCutoffSearch,kBehaviorOptions.getProperty("cutOffSearch"));
		preferenceStore.setValue(PreferenceConstants.KbehaviorStepSave,kBehaviorOptions.getProperty("stepSave"));
	}
	
	public static void setKTailInferenceEngineOptions(Properties kBehaviorOptions){
		IPreferenceStore preferenceStore = getPreferenceStore();
		
		preferenceStore.setValue(PreferenceConstants.KTailLogger, kBehaviorOptions.getProperty("logger"));
		preferenceStore.setValue(PreferenceConstants.KTailLevel , kBehaviorOptions.getProperty("level"));
		preferenceStore.setValue(PreferenceConstants.KTailEnableMinimization, kBehaviorOptions.getProperty("enableMinimization"));
		preferenceStore.setValue(PreferenceConstants.KTailMinTrustLen,kBehaviorOptions.getProperty("minTrustLen"));
	}
	
	public static void setKInclusionInferenceEngineOptions(Properties kBehaviorOptions){
		IPreferenceStore preferenceStore = getPreferenceStore();
		
		preferenceStore.setValue(PreferenceConstants.KInclusionLogger, kBehaviorOptions.getProperty("logger"));
		preferenceStore.setValue(PreferenceConstants.KInclusionLevel , kBehaviorOptions.getProperty("level"));
		preferenceStore.setValue(PreferenceConstants.KInclusionEnableMinimization, kBehaviorOptions.getProperty("enableMinimization"));
		preferenceStore.setValue(PreferenceConstants.KInclusionMinTrustLen,kBehaviorOptions.getProperty("minTrustLen"));
	}
	
	public static void setReissInferenceEngineOptions(Properties kBehaviorOptions){
		IPreferenceStore preferenceStore = getPreferenceStore();
		
		preferenceStore.setValue(PreferenceConstants.ReissLogger, kBehaviorOptions.getProperty("logger"));
		preferenceStore.setValue(PreferenceConstants.ReissLevel , kBehaviorOptions.getProperty("level"));
		preferenceStore.setValue(PreferenceConstants.ReissEnableMinimization, kBehaviorOptions.getProperty("enableMinimization"));
		preferenceStore.setValue(PreferenceConstants.ReissMinTrustLen,kBehaviorOptions.getProperty("minTrustLen"));
	}
	
	

	/**
	 * This method update the flatter options of the given workspaceOption object according to the settings in the store
	 * @param store 
	 * @param workspaceOptions
	 */
	private static void updateFlattenerOptions(IPreferenceStore store, BctDefaultOptions workspaceOptions) {
		//since the storage do not contains any flattener configuration we do not need to update them
	}
}
