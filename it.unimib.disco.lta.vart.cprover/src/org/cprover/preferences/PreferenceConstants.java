package org.cprover.preferences;

/**
 * Class: PreferenceConstants
 *  
 * Purpose: Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	// tools
	public static final String EXEC_SATABS = "satabs";
	public static final String EXEC_CBMC = "cbmc";
	public static final String EXEC_HWCBMC = "hw-cbmc";
	public static final String EXEC_EBMC = "ebmc";
	public static final String EXEC_VCEGAR = "vcegar";
	
	//common options
	public static final String verifier = "verifier";
	public static final String main_function = "main_function";
	public static final String endianess = "endianess";
	public static final String word_width = "word_width";
	public static final String max_threads = "max_threads";
	public static final String environment = "environment";
	public static final String assertions_check = "assertions_check";
	public static final String array_bounds_check = "array_bounds_check";
	public static final String div_by_zero_check = "div_by_zero_check";
	public static final String pointers_check = "pointers_check";
	public static final String overflow_check = "overflow_check";
	public static final String rounding_mode = "rounding_mode";
	public static final String nan_check = "nan_check";
	
	//CBMC
	public static final String cbmc_simplify = "cbmc_simplify";
	//public static final String cbmc_all_claims = "cbmc_all_claims";
	public static final String cbmc_unwind = "cbmc_unwind";
	public static final String cbmc_slice = "cbmc_slice";
	//public static final String cbmc_substitution = "cbmc_substitution";
	public static final String cbmc_assertions = "cbmc_assertions";
	public static final String cbmc_beautify = "cbmc_beautify";
	public static final String hwcbmc_module = "hwcbmc_module";
	public static final String hwcbmc_bound = "hwcbmc_bound";	
	//public static final String cbmc_value_sets = "cbmc_value_sets";
	
	//SATABS
	public static final String satabs_modelchecker = "satabs_modelchecker";
	public static final String satabs_abstractor = "satabs_abstractor";
	public static final String satabs_refiner = "satabs_refiner";
	public static final String satabs_iterations = "satabs_iterations";
	public static final String satabs_data_races = "satabs_data_races";
	public static final String satabs_loop_detection = "satabs_loop_detection";
}
