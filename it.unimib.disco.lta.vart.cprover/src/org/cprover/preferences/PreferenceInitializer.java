package org.cprover.preferences;

import org.cprover.core.CProverPlugin;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class: PreferenceInitializer
 * 
 * Purpose: Initialize default values for runtime preferences.
 */

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		
		IPreferenceStore store = CProverPlugin.getDefault().getPreferenceStore();
		
		// external tools
		store.setDefault(PreferenceConstants.EXEC_SATABS, "");
		store.setDefault(PreferenceConstants.EXEC_CBMC, "");
		store.setDefault(PreferenceConstants.EXEC_HWCBMC, "");
		store.setDefault(PreferenceConstants.EXEC_EBMC, "");
		store.setDefault(PreferenceConstants.EXEC_VCEGAR, "");
		
		//common options defaults
		store.setDefault(PreferenceConstants.verifier, "satabs");
		store.setDefault(PreferenceConstants.max_threads, "3");
		store.setDefault(PreferenceConstants.environment, "");
		store.setDefault(PreferenceConstants.word_width, "machine_default");
		store.setDefault(PreferenceConstants.main_function, "main");
		store.setDefault(PreferenceConstants.endianess, "machine_default");
		store.setDefault(PreferenceConstants.assertions_check, true);
		store.setDefault(PreferenceConstants.array_bounds_check, true);
		store.setDefault(PreferenceConstants.div_by_zero_check, true);		
		store.setDefault(PreferenceConstants.pointers_check, true);
		store.setDefault(PreferenceConstants.overflow_check, false);
		store.setDefault(PreferenceConstants.rounding_mode, "machine_default");
		store.setDefault(PreferenceConstants.nan_check, false);
		//CBMC defaults
		store.setDefault(PreferenceConstants.cbmc_simplify, true);
		//store.setDefault(PreferenceConstants.cbmc_all_claims, false);
		store.setDefault(PreferenceConstants.cbmc_unwind, "5");
		store.setDefault(PreferenceConstants.cbmc_slice, false);
		//store.setDefault(PreferenceConstants.cbmc_substitution, true);
		store.setDefault(PreferenceConstants.cbmc_assertions, true);
		store.setDefault(PreferenceConstants.cbmc_beautify, "none");
		store.setDefault(PreferenceConstants.hwcbmc_module, "main");
		store.setDefault(PreferenceConstants.hwcbmc_bound, "10");		
		//store.setDefault(PreferenceConstants.cbmc_value_sets, false);
		//Satabs defaults
		store.setDefault(PreferenceConstants.satabs_modelchecker, "default");
		store.setDefault(PreferenceConstants.satabs_abstractor, "default");
		store.setDefault(PreferenceConstants.satabs_refiner, "default");
		store.setDefault(PreferenceConstants.satabs_iterations, "50");
		store.setDefault(PreferenceConstants.satabs_data_races, false);
		store.setDefault(PreferenceConstants.satabs_loop_detection, false);		
	}

}
