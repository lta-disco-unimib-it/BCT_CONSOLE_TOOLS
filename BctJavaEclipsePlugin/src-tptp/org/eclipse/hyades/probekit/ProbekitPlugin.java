 /********************************************************************** 
 * Copyright (c) 2005, 2008 IBM Corporation and others. 
 * All rights reserved.   This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html         
 * $Id: ProbekitPlugin.java,v 1.1 2011-12-01 21:34:10 pastore Exp $ 
 * 
 * Contributors: 
 * IBM - Initial API and implementation 
 **********************************************************************/ 

package org.eclipse.hyades.probekit;

//import org.eclipse.tptp.platform.probekit.registry.ProbeRegistry;
//import org.eclipse.tptp.platform.probekit.registry.ProbeRegistryException;
//import org.eclipse.tptp.platform.probekit.util.ProbeSrcListener;
//import org.eclipse.tptp.platform.probekit.util.ProbekitDebugConfig;
import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.resources.*;
import org.osgi.framework.BundleContext;

import java.io.File;
import java.util.*;

/**
 * The main plugin class to be used in the desktop.
 */
public class ProbekitPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static ProbekitPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
	
	private static String PLUGIN_ID = "org.eclipse.hyades.probekit";
	
	//  Copied over from the dynamic probekit plugin class.
	private static final String STATE_FILE_BASENAME = "state";	//$NON-NLS-1$
	
	
	/**
	 * The constructor.
	 */
	public ProbekitPlugin() {
		super();
		plugin = this;
	}

	/**
	 * Start method. Overrides base class start method. Gets the resource bundle
	 * for translations etcetera.
	 */
	public void start(BundleContext context) throws Exception {
//		super.start(context);
//		try {
//			this.resourceBundle = Platform.getResourceBundle(this.getBundle());
//		} catch (MissingResourceException x) {
//			this.resourceBundle = null;
//		}
//		
//		if ( isDebugging() ) {
//			ProbekitDebugConfig.configure(getPluginId());
//		}
//		try {
//			ProbeRegistry.startup();
//		} catch (ProbeRegistryException ex) {			
//			throw new CoreException(new Status(IStatus.ERROR, getPluginId(), 
//					0, "Unable to initialize probekit registry", ex));	// TODO localize me	
//		}
//		
//		// Restore registry contents from previous saved state
//		IWorkspace workspace = ResourcesPlugin.getWorkspace();
//		final ISavedState lastState = 
//			workspace.addSaveParticipant(this, new SaveParticipant());
//		File lastStateFile = getLastStateFile(lastState);		
//		if ( lastStateFile != null )
//		{
//			restore(lastStateFile);
//			// Process resource changes that have occurred since our last activation
//			// This does the same resource processing at activation that we 
//			// normally do while we're active. This catches us up to what's
//			// been going on while we weren't look.
//			
//			// we have to do this in a separate job otherwise it creates a deadlock
//			// since processResourceChangeEvents acquires a  Workspace scheduling lock and
//			// is blocked indefinitely because the thread this start method is called in
//			// is actually the main UI thread (this changed in eclipse 3.3, in previous version,
//			// bundle startup was done in seaparate thread), has already the workspace lock
//			Job job = new Job("Process resource changes") {
//
//				protected IStatus run(IProgressMonitor monitor) {
//					lastState.processResourceChangeEvents(new ProbeSrcListener());
//					return Status.OK_STATUS;				}
//				
//			};
//			job.setSystem(true);
//			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
//			job.schedule();
//			
//		}		
//		
//		// Jack in our resource change listener so we can keep the registry and
//		// persistent properties tidy.
//		workspace.addResourceChangeListener(new ProbeSrcListener(), 
//				ProbeSrcListener.ACTIVE_EVENT_MASK);
	}
	
	/**
	 * Returns the shared instance.
	 */
	public static ProbekitPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the workspace instance.
	 */
	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getString(String key) {
		ResourceBundle bundle= ProbekitPlugin.getDefault().getResourceBundle();
		if (bundle == null) return key;
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	// Handle registry state - create necessary state save areas, if it
	// doesn't already exist. Restore registry contents from previously 
	// saved state, if any.  Copied over from the dynamic probekit plugin class.
//	public void startup() throws CoreException
//	{
//		super.startup();
//		
//	}
	
	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.  Copied over from the dynamic probekit plugin class.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = ProbekitPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}
	
	/**
	 * Save plugin state to a file. In particular, persist the probe registry.
	 * Copied over from the dynamic probekit plugin class.
	 * @param saveFile The file to save state into.
	 */
	private static void save(File saveFile)
	{
//		// TODO remove previous save file, if one exists
//		try {
//			ProbeRegistry.getRegistry().save(saveFile);
//		} catch (ProbeRegistryException e) {
//			// TODO Duh, what to do?
//		}
	}
	
	/**
	 * Copied over from the dynamic probekit plugin class.
	 * @param stateFile
	 */
	private static void restore(File stateFile)
	{
//		try {
//			ProbeRegistry.getRegistry().restore(stateFile);
//		} catch (ProbeRegistryException e) {
//			// TODO log some kind of message?
//		}
	}
	
	/**
	 * Construct a File reference to a plugin state save file. The save number is
	 * obtained from the workbench by the caller. Typically, this represents either
	 * the current or previous save file.  Copied over from the dynamic probekit plugin class.
	 * 
	 * @param saveNum Save iteration whose file you want to reference
	 * @return Save file reference
	 */
	static File getSaveFile(int saveNum)
	{
		String saveFileName = getSaveFileName(saveNum);
		return plugin.getStateLocation().append(saveFileName).toFile();
	}
	
	public static String getPluginId() {
		  return PLUGIN_ID;
	}
	
	/**
	 * Construct the name of a plugin state save file, based on a save iteration number.
	 * The save number is obtained from the workbench by the caller. Typically, it
	 * represents the current or previous save iteration.  Copied over from the dynamic probekit plugin class.
	 * 
	 * <p>The constructed filename takes the form <base><number> and does not
	 * include a directory or other platform specific components.
	 * 
	 * @param saveNum Save iteration whose filename you want to construct
	 * @return the simple name of the requested save file
	 */
	static String getSaveFileName(int saveNum)
	{
		String saveFileName = STATE_FILE_BASENAME + Integer.toString(saveNum);
		return saveFileName;
	}

	/**
	 * Find the file associated with a given save state. If a non-null reference is
	 * returned, the file is known to exist.  Copied over from the dynamic probekit plugin class.
	 * 
	 * @param state The state whose save file to locate
	 * @return The requested saved state file, or null if the file does not exist
	 */
	static File getLastStateFile(ISavedState state)
	{
		if ( state == null )
			return null;
		
		IPath savePath = state.lookup(new Path(STATE_FILE_BASENAME));
		
		if ( savePath != null )
		{
			File saveFile = plugin.getStateLocation().append(savePath).toFile();
			return (saveFile.exists() ? saveFile : null);
		} else {
			return null;
		}
	}

	// This class just rolls up the machinery of plugin state saving participation.
	// It is all generic and need not be changed if the plugin acquires new and
	// interesting things to save.  Copied over from the dynamic probekit plugin class.
	private class SaveParticipant implements ISaveParticipant
	{
		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.ISaveParticipant#doneSaving(org.eclipse.core.resources.ISaveContext)
		 */
		public void doneSaving(ISaveContext context) {
			// Save was successful, so delete previous state file
			File prevSaveFile = getSaveFile(context.getPreviousSaveNumber());
			prevSaveFile.delete();
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.ISaveParticipant#prepareToSave(org.eclipse.core.resources.ISaveContext)
		 */
		public void prepareToSave(ISaveContext context) throws CoreException {
			// TODO Lock registry?
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.ISaveParticipant#rollback(org.eclipse.core.resources.ISaveContext)
		 */
		public void rollback(ISaveContext context) {
			// Save failed, so delete current save
			File saveFile = getSaveFile(context.getSaveNumber());
			saveFile.delete();
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.core.resources.ISaveParticipant#saving(org.eclipse.core.resources.ISaveContext)
		 */
		public void saving(ISaveContext context) throws CoreException 
		{
		    int saveKind = context.getKind();
			if ( saveKind == ISaveContext.FULL_SAVE ||
			        saveKind == ISaveContext.SNAPSHOT )
			{
				int saveNum = context.getSaveNumber();
				File saveFile = getSaveFile(saveNum);
				
				ProbekitPlugin.save(saveFile);
				context.map(new Path(STATE_FILE_BASENAME), 
							new Path(getSaveFileName(saveNum)));
				context.needSaveNumber();
				context.needDelta();	// for ProbeSrcListener
			}
		}
	} // SaveParticipant

}
