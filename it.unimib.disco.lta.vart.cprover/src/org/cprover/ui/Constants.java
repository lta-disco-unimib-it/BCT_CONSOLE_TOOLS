package org.cprover.ui;

import org.cprover.core.CProverPlugin;

/**
 *
 */
public interface Constants {
	public static final String PLUGIN_ID = CProverPlugin.getUniqueIdentifier();
	public static final String PREFIX = PLUGIN_ID + ".";
	
	public static final int INTERNAL_ERROR = 150;
	public static final int STATUS_CODE_QUESTION = 10000;
	public static final int STATUS_CODE_INFO = 10001;
	public static final int STATUS_CODE_ERROR = 10002;
	
	// LaunchConfiguration properties
    static final String ATTR_LOCATION = "org.eclipse.ui.externaltools" + ".ATTR_LOCATION";
    
    /** Attribute holding alternate location that is used to actually run the resource. */
    static final String ATTR_ALTERNATE_LOCATION = "org.python.cprover.core" + ".ATTR_ALTERNATE_LOCATION";
    
    /** Attribute to control if a wrapper for test runner should be used. */
    static final String ATTR_NO_UNITTEST_WRAPPER = "org.python.cprover.core" + ".ATTR_NO_UNITTEST_WRAPPER";
    
    static final String ATTR_WORKING_DIRECTORY = "org.eclipse.ui.externaltools" + ".ATTR_WORKING_DIRECTORY";
    static final String ATTR_OTHER_WORKING_DIRECTORY = "org.eclipse.ui.externaltools" + ".ATTR_OTHER_WORKING_DIRECTORY";
    static final String ATTR_PROGRAM_ARGUMENTS = "org.eclipse.ui.externaltools" + ".ATTR_TOOL_ARGUMENTS";

    static final String ATTR_RESOURCE_TYPE = PLUGIN_ID + ".ATTR_RESOURCE_TYPE";
    
    // main options
    static final String ATTR_PROJECT_NAME = PLUGIN_ID + ".ATTR_PROJECT";
    static final String ATTR_FILES = PLUGIN_ID + ".ATTR_FILES";
    
	// common options
    static final String ATTR_VERIFIER = PLUGIN_ID + ".ATTR_VERIFIER";
    static final String ATTR_MAINFUN = PLUGIN_ID + ".ATTR_MAINFUN";
    static final String ATTR_ENDIANESS = PLUGIN_ID + ".ATTR_ENDIANESS";
    static final String ATTR_WORD_WIDTH = PLUGIN_ID + ".ATTR_WORD_WIDTH";
	static final String ATTR_ASSERTIONS_CHECK = PLUGIN_ID + ".ATTR_ASSERTIONS_CHECK";
	static final String ATTR_ARRAY_BOUNDS_CHECK = PLUGIN_ID + ".ATTR_ARRAY_BOUNDS_CHECK";
	static final String ATTR_DIV_BY_ZERO_CHECK = PLUGIN_ID + ".ATTR_DIV_BY_ZERO_CHECK";
	static final String ATTR_POINTERS_CHECK = PLUGIN_ID + ".ATTR_POINTERS_CHECK";
	static final String ATTR_OVERFLOW_CHECK = PLUGIN_ID + ".ATTR_OVERFLOW_CHECK";
	static final String ATTR_ROUND = PLUGIN_ID + ".ATTR_ROUND";
	static final String ATTR_NANCHECK = PLUGIN_ID + ".ATTR_NANCHECK";
	
	// cbmc
	static final String ATTR_CBMC_VALUE_SETS = PLUGIN_ID + ".ATTR_CBMC_VALUE_SETS";
	static final String ATTR_CBMC_SIMPLIFY = PLUGIN_ID + ".ATTR_CBMC_SIMPLIFY";
	static final String ATTR_CBMC_ALL_CLAIMS = PLUGIN_ID + ".ATTR_CBMC_ALL_CLAIMS";
	static final String ATTR_CBMC_UNWIND = PLUGIN_ID + ".ATTR_CBMC_UNWIND";
	static final String ATTR_CBMC_SLICE = PLUGIN_ID + ".ATTR_CBMC_SLICE ";
	static final String ATTR_CBMC_SUBSTITUTION = PLUGIN_ID + ".ATTR_CBMC_SUBSTITUTION";
	static final String ATTR_CBMC_ASSERTIONS = PLUGIN_ID + ".ATTR_CBMC_ASSERTIONS";
	static final String ATTR_CBMC_BEAUTIFY = PLUGIN_ID + ".ATTR_CBMC_BEAUTIFY";
	static final String ATTR_HWCBMC_MODULE = PLUGIN_ID + ".ATTR_HWCBMC_MODULE";
	static final String ATTR_HWCBMC_BOUND = PLUGIN_ID + ".ATTR_HWCBMC_BOUND";
	
	// satabs
	static final String ATTR_SATABS_MODELCHECKER = PLUGIN_ID + ".ATTR_SATABS_MODELCHECKER";
	static final String ATTR_SATABS_ABSTRACTOR = PLUGIN_ID + ".ATTR_SATABS_ABSTRACTOR";
	static final String ATTR_SATABS_REFINER = PLUGIN_ID + ".ATTR_SATABS_REFINER";
	static final String ATTR_SATABS_ITERATIONS = PLUGIN_ID + ".ATTR_SATABS_ITERATIONS";
	static final String ATTR_SATABS_DATA_RACES = PLUGIN_ID + ".ATTR_SATABS_DATA_RACES";
	static final String ATTR_SATABS_LOOP_DETECTION = PLUGIN_ID + ".ATTR_SATABS_LOOP_DETECTION";
	
	static final String ICON_RUNNING = "icons/running.gif";
	static final String ICON_WAITING = "icons/waiting.gif";
	static final String ICON_UNKNOWN = "icons/unknown.gif";
	static final String ICON_SUCCESS = "icons/success.gif";
	static final String ICON_FAILURE = "icons/failure.gif";
	static final String ICON_ERROR = "icons/error.gif";
}
