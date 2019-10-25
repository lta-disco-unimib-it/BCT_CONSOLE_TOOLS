package org.cprover.launch;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.cprover.core.CProverPlugin;
import org.cprover.ui.Constants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Holds configuration for a verification run. (Horatiu's old Task class)
 * 
 * It knows how to extract proper launching arguments from disparate sources.
 * Has many launch utility functions (getCommandLine & friends).
 * 
 * @author Gérard Basler
 * @version $Revision: 1.2 $
 */
public class VerifyConfig {

	public final IProject project;
	
    public File workingDirectory;
    
	public String[] envp = null;

	public enum Verifier {
		SATABS("satabs"), CBMC("cbmc"), HW_CBMC("hw-cbmc"), EBMC("ebmc"), VCEGAR("vcegar");

		private static final Map<String, Verifier> lookup = new HashMap<String, Verifier>();

		static {
			for (Verifier s : EnumSet.allOf(Verifier.class))
				lookup.put(s.getName(), s);
		}

		private final String name;

		private Verifier(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static Verifier get(String name) {
			return lookup.get(name);
		}
	}
	
	// task
	public Verifier verifier;
//	public boolean useXmlBin;
//	public String xmlBinary;
//	public String makeTarget;
//	public String root;
//	public String filter;
	public LinkedList<String> files;
//	public LinkedList<String> includes;
//	public LinkedList<String> defines;
	
	// launch options
	public String mainFun;
	public String endianess;//unspecified, little, big
	public String word_width;//16, 32, 64
	public boolean chk_assertions;
	public boolean chk_array_bounds;
	public boolean chk_div_by_zero;
	public boolean chk_pointers;
	public boolean chk_overflow;
	public boolean nan_check;
	public String round_mode;
	
	// cbmc
//	public boolean cbmc_value_sets;
	public boolean cbmc_simplify;
	public boolean cbmc_all_claims;
	public int cbmc_unwind;
	public boolean cbmc_slice;
//	public boolean cbmc_substitution;
	public boolean cbmc_assertions;
	public String cbmc_beautify;//none, greedy, pbs
	public String hwcbmc_module;//for hw
	public int hwcbmc_bound;//for hw
	
	// satabs
	public String satabs_modelchecker;
	public String satabs_abstractor;
	public String satabs_refiner;
	public int satabs_iterations;
	public boolean satabs_data_races;
	public boolean satabs_loop_detection;

	/**
	 * Sets defaults.
	 * 
	 * @throws InvalidRunException
	 */
	public VerifyConfig(ILaunchConfiguration conf) throws CoreException, InvalidRunException {
		project = null;
		workingDirectory = null;
		// 1st thing, see if this is a valid run.
		//project = getProjectFromConfiguration(conf);

//		if (project == null) { // Ok, we could not find it out
//			throw CProverPlugin.logWithException("Could not get project for configuration: " + conf);
//		}
//
//
//      IPath workingPath = getWorkingDirectory(conf);
//      workingDirectory = workingPath == null ? null : workingPath.toFile();
//	}
//	
//	public VerifyConfig(IProject project, File workingDirectory) throws CoreException, InvalidRunException {
//			this.project = project;
//			this.workingDirectory = workingDirectory;
//
//        //-NH
//        files = new LinkedList<String>();        
//        String file_list = conf.getAttribute(Constants.ATTR_FILES, (String) null);
//        String[] file_item = file_list.split(";", 100);
//
//        for(int i = 0; i < file_item.length; i++) {
//        	if(file_item[i].isEmpty() == false){
//        		files.add(file_item[i]);
//        	}
//        }        
//        if (files.isEmpty()) {
//        	throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get files for run", null));
//        }
        //-NH
//        
//		// make the environment
//		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
//		envp = launchManager.getEnvironment(conf);
//		if(envp == null) {
//			envp = new String[] {};
//		}
//		
//		IPreferenceStore prefs = CProverPlugin.getDefault().getPreferenceStore();
//		
//		// common options
//		verifier = Verifier.get(conf.getAttribute(Constants.ATTR_VERIFIER, (String) null));
//        if (verifier == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get verifier for run", null));
//        }
//        
//        mainFun = conf.getAttribute(Constants.ATTR_MAINFUN, (String) null);
//        if (mainFun == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get name of main function for run", null));
//        }
//        
//        endianess = conf.getAttribute(Constants.ATTR_ENDIANESS, (String) null);
//        if (endianess == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get endianess for run", null));
//        }
//        
//        word_width = conf.getAttribute(Constants.ATTR_WORD_WIDTH, (String) null);
//        if (word_width == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get word width for run", null));
//        }
//		
//        chk_assertions = conf.getAttribute(Constants.ATTR_ASSERTIONS_CHECK, false);
//        chk_array_bounds = conf.getAttribute(Constants.ATTR_ARRAY_BOUNDS_CHECK, false);
//        chk_div_by_zero = conf.getAttribute(Constants.ATTR_DIV_BY_ZERO_CHECK, false);
//        chk_pointers = conf.getAttribute(Constants.ATTR_POINTERS_CHECK, false);
//        chk_overflow = conf.getAttribute(Constants.ATTR_OVERFLOW_CHECK, false);
//
//        // cbmc
//        cbmc_simplify = conf.getAttribute(Constants.ATTR_CBMC_SIMPLIFY, false);
//        cbmc_all_claims = conf.getAttribute(Constants.ATTR_CBMC_ALL_CLAIMS, false);
//        
//        cbmc_unwind = conf.getAttribute(Constants.ATTR_CBMC_UNWIND, -1);
//        if (cbmc_unwind == -1) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get CBMC unwind number for run", null));
//        }
//        
//        cbmc_slice = conf.getAttribute(Constants.ATTR_CBMC_SLICE, false);
//        //cbmc_substitution = conf.getAttribute(Constants.ATTR_CBMC_SUBSTITUTION, false);
//        cbmc_assertions = conf.getAttribute(Constants.ATTR_CBMC_ASSERTIONS, false);
//        
//        cbmc_beautify = conf.getAttribute(Constants.ATTR_CBMC_BEAUTIFY, (String) null);
//        if (cbmc_beautify == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get CBMC beautify for run", null));
//        }
//
//        round_mode = conf.getAttribute(Constants.ATTR_ROUND, (String) null);
//        /*if (round_mode == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get CBMC round mode for run", null));
//        }*/
//        
//        hwcbmc_module = conf.getAttribute(Constants.ATTR_HWCBMC_MODULE, (String) null);
//        if (hwcbmc_module == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get module for run", null));
//        }
//        
//        hwcbmc_bound = conf.getAttribute(Constants.ATTR_HWCBMC_BOUND, -1);
//        if (hwcbmc_bound == -1) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get bound for CBMC/HWCBMC", null));
//        }
//        
//        // satabs
//        satabs_modelchecker = conf.getAttribute(Constants.ATTR_SATABS_MODELCHECKER, (String) null);
//        if (satabs_modelchecker == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get model checker for SATABS run", null));
//        }
//        
//        satabs_abstractor = conf.getAttribute(Constants.ATTR_SATABS_ABSTRACTOR, (String) null);
//        if (satabs_abstractor == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get abstractor for SATABS run", null));
//        }
//        
//        satabs_refiner = conf.getAttribute(Constants.ATTR_SATABS_REFINER, (String) null);
//        if (satabs_refiner == null) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get refiner for SATABS run", null));
//        }
//        
//        satabs_iterations = conf.getAttribute(Constants.ATTR_SATABS_ITERATIONS, -1);
//        if (satabs_iterations == -1) {
//            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get iteration bound for SATABS", null));
//        }
//        
//        satabs_data_races = conf.getAttribute(Constants.ATTR_SATABS_DATA_RACES, false);
//        satabs_loop_detection = conf.getAttribute(Constants.ATTR_SATABS_LOOP_DETECTION, false);
//        
//        
	}

	/**
	 * Gets the project that should be used for a launch configuration
	 * 
	 * @param conf
	 *            the launch configuration from where the project should be
	 *            gotten
	 * @return the related IProject
	 * @throws CoreException
	 */
	private static IProject getProjectFromConfiguration(ILaunchConfiguration conf) throws CoreException {
		String projName = conf.getAttribute(Constants.ATTR_PROJECT_NAME, "");
		if (projName == null || projName.length() == 0) {
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get project for the run", null));
		}
	
		IWorkspace w = ResourcesPlugin.getWorkspace();
		IProject p = w.getRoot().getProject(projName);
		if (p == null) { // Ok, we could not find it out
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Could not get project: " + projName, null));
		}
		return p;
	}

    private static IStringVariableManager getStringVariableManager() {
        return VariablesPlugin.getDefault().getStringVariableManager();
    }
	
    /**
     * Expands and returns the working directory attribute of the given launch
     * configuration. Returns <code>null</code> if a working directory is not
     * specified. If specified, the working is verified to point to an existing
     * directory in the local file system.
     * 
     * @param configuration launch configuration
     * @return an absolute path to a directory in the local file system, or
     * <code>null</code> if unspecified
     * @throws CoreException if unable to retrieve the associated launch
     * configuration attribute, if unable to resolve any variables, or if the
     * resolved location does not point to an existing directory in the local
     * file system
     */
    public static IPath getWorkingDirectory(ILaunchConfiguration configuration) throws CoreException {
    	return getProjectFromConfiguration(configuration).getLocation();
//        String location = configuration.getAttribute(Constants.ATTR_WORKING_DIRECTORY, (String) null);
//        if (location != null) {
//            String expandedLocation = getStringVariableManager().performStringSubstitution(location);
//            if (expandedLocation.length() > 0) {
//                File path = new File(expandedLocation);
//                if (path.isDirectory()) {
//                    return new Path(expandedLocation);
//                } 
//                throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, "Unable to get working location for the run \n(the location: '"+expandedLocation+"' is not a valid directory).",null));
//            }
//        }
//        return null;
    }
}
