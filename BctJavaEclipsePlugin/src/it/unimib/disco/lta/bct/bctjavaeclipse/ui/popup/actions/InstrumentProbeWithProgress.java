package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.eclipse.core.BctCoreActivator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.hyades.probekit.ProbeInstrumenterDriver;
import org.eclipse.hyades.probekit.ProbeInstrumenterDriver.StaticProbeInstrumenterException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IRuntimeClasspathProvider;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class InstrumentProbeWithProgress implements InstrumentWithProgress {
	
	private Shell shell;
	private Display display;
	private IFile file;
	private String[] resourcesToInstrument;
	private MonitoringConfiguration mc;
	private boolean updateLaunchConfiguration;
	private Set<IProject> projectsToInstrument;

	public InstrumentProbeWithProgress(Shell shell, Display display,
			IFile selectedElement, String resourcesToInstrument[], Set<IProject> projectsToInstrument, MonitoringConfiguration mc, boolean updateLaunchConfiguration){
		this.shell = shell;
		this.display = display;
		this.file = selectedElement;
		this.mc = mc;
		this.resourcesToInstrument = resourcesToInstrument;
		this.updateLaunchConfiguration = updateLaunchConfiguration;
		this.projectsToInstrument = projectsToInstrument;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		try {
			
			
			
			
			

			
			System.out.println("To instrument:");
			for ( String res : resourcesToInstrument ){
				System.out.println(res);
			}
			
			if ( updateLaunchConfiguration ){
				updateLaunchConfigurations(projectsToInstrument,mc);
			}

			ConfigurationFilesManager.updateConfigurationFiles(mc);
			
			
			File probeScript = getProbeScript(mc);
			
			ProbeInstrumenter instrumenter = new ProbeInstrumenter(getInstrumenterExecutablePath());
			instrumenter.instrument(probeScript, resourcesToInstrument );
			
			checkInstrumentationDone();
			
//			Probe probe = ProbekitFactory.eINSTANCE.createProbe();
//			InvocationObject obj = ProbekitFactory.eINSTANCE.createInvocationObject();
//			Fragment fragment = ProbekitFactory.eINSTANCE.createFragment();
//			fragment.setType(FragmentType.ENTRY_LITERAL);
//			
//			fragment.getData().addAll();
//			probe.getFragment().add(fragment);

		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	/**
	 * This method checks if the instrumentation has beed done corectly.
	 * If problems are discovered it pops up an error dialog.
	 * 
	 */
	private void checkInstrumentationDone() {
		ArrayList<String> missingResources = new ArrayList<String>();
		
		for ( String resource : resourcesToInstrument ){
			File f = new File(resource+".bak");
			if ( ! f.exists() ){
				missingResources.add(resource);
			}
		}
		
		if ( missingResources.size() > 0 ){
			System.out.println("ERR");
			StringBuffer messageB = new StringBuffer();
			messageB.append("The following problems could not be instrumented:\n");
			for ( String resource : missingResources ){
				messageB.append(resource+"\n");
			}
			MessageDialog.openError(shell, "Instrumentation problem", messageB.toString());

		}
	}

	private List<IProject> getConfigurationProjects(ILaunchConfiguration lc) throws CoreException {
		System.out.println("LCN "+lc.getName());
		List<IProject> p = new ArrayList<IProject>();
		IResource[] resources = lc.getMappedResources();
		if ( resources != null ){
			for ( IResource r : resources ){
				p.add(r.getProject());
			}
		}
		return p;
	}
	
	
	private String getInstrumenterExecutablePath() {
		String exePath = "$os$/probeinstrumenter";
		if ( Platform.getOS().equals("win32") ){
			exePath = exePath + ".exe";
		}

		//org.eclipse.core.runtime.IPath relativePath = new Path(exePath);
		//System.out.println(relativePath);
		//URL res = BctCoreActivator.getDefault().find(relativePath, null);
		//URL res = BctCoreActivator.getDefault().getBundle().getEntry(exePath);

		String exe_path;
		String engine_executable_name = "$os$/probeinstrumenter";
		if(Platform.getOS().equals("win32"))
			engine_executable_name = engine_executable_name + ".exe";
		Plugin compilerPlugin = BctCoreActivator.getDefault() ;
		org.eclipse.core.runtime.IPath relativePath = new Path(engine_executable_name);
		URL nativeInstrumenterRelativeURL = compilerPlugin.find(relativePath, null);
		URL compilerURL = compilerPlugin.getBundle().getEntry("/");
		String relativeFile = nativeInstrumenterRelativeURL.getFile();
		if(relativeFile.startsWith("/"))
			relativeFile = relativeFile.substring(1);
		URL nativeInstrumenterPlatformURL;
		try {
			nativeInstrumenterPlatformURL = new URL(compilerURL, relativeFile);

			URL nativeInstrumenterURL = Platform.asLocalURL(nativeInstrumenterPlatformURL);


			if ( nativeInstrumenterURL == null )
				return "null";

			exe_path = nativeInstrumenterURL.getFile();
			File exe_file = new File(exe_path);
			if(!exe_file.exists()){
				Logger.getInstance().logError("Cannot find instrumenter executable "+exe_file.toString(), null);
				return "NotExists";
			}
			try {
				exe_path = exe_file.getCanonicalPath();
			} catch (IOException e) {
				Logger.getInstance().logError("Cannot find instrumenter executable "+exe_file.toString(), null);
				return "err";
			}
			return exe_path.toString();	
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Logger.getInstance().logError("Cannot find instrumenter executable "+compilerURL.toString(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getInstance().logError("Cannot find instrumenter executable "+compilerURL.toString(), null);
		}

		return "err";


	}

	
	
	
	
	private void updateLaunchConfigurations(Set<IProject> instrumentedProjects, MonitoringConfiguration mc) {
		ILaunchConfiguration[] lcs;
		System.out.println("ULC "+instrumentedProjects);
		try {
			lcs = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations();
			for ( ILaunchConfiguration lc : lcs  ){
				List<IProject> resourceProjects = getConfigurationProjects(lc);
				
				for ( IProject resProject : resourceProjects ){
					System.out.println("prj "+resProject.getName());
					if ( instrumentedProjects.contains(resProject) ){
						IJavaProject jproject = JavaCore.create(resProject);
						System.out.println("FOUND ");
						
						Map attributes = lc.getAttributes();
						ILaunchConfigurationWorkingCopy wc = lc.getWorkingCopy();
						
						Object envVars = attributes.get("org.eclipse.debug.core.environmentVariables");
						HashMap map;
						if ( envVars != null ){
							map = (HashMap) envVars;
							
							System.out.println(envVars.getClass().getName());
							System.out.println(envVars.toString());
							for ( Object  el : map.values() ){
								System.out.println(el.getClass().getName());
								System.out.println(el);
							}
						} else {
							map = new HashMap();
							attributes.put("org.eclipse.debug.core.environmentVariables", map);
						}
						map.put("BCT_HOME", ConfigurationFilesManager.getBctHomeDir(mc).getAbsolutePath());
						
						Object appendEnvVars = attributes.get("org.eclipse.debug.core.appendEnvironmentVariables");
						if ( appendEnvVars == null ){
							attributes.put("org.eclipse.debug.core.appendEnvironmentVariables",Boolean.TRUE);
						}
						
						//Object defClassPath = attributes.get("org.eclipse.jdt.launching.DEFAULT_CLASSPATH");
						
						attributes.put("org.eclipse.jdt.launching.DEFAULT_CLASSPATH",Boolean.FALSE);
						
						
						
						Object classPath = attributes.get("org.eclipse.jdt.launching.CLASSPATH");
						ArrayList list; 
						if ( classPath == null ){
							//System.out.println("null classPath");
							list = new ArrayList();
							IRuntimeClasspathEntry[] entries= JavaRuntime.computeUnresolvedRuntimeClasspath(jproject);
							attributes.put("org.eclipse.jdt.launching.CLASSPATH", list);
							String[] rtcp = JavaRuntime.computeDefaultRuntimeClassPath(jproject);
							String[] defcp = JavaRuntime.computeDefaultRuntimeClassPath(jproject);
							
							//list.add(JavaRuntime.newDefaultProjectClasspathEntry(jproject).getMemento());

							for ( IRuntimeClasspathEntry e : entries){
								list.add(e.getMemento());
							}
						} else {
							System.out.println("CPE "+classPath.getClass().getName());
							list = (ArrayList) classPath;
							for ( Object  o : list){
								System.out.println("CPE EL "+o.getClass().getName()+" "+o);
							}
						}
						
						//FIXME: update library path
						
						IRuntimeClasspathProvider cpp = JavaRuntime.getClasspathProvider(lc);
						//IRuntimeClasspathEntry e = JavaRuntime.newStringVariableClasspathEntry(DefaultOptionsManager.getBctJarFile().getAbsolutePath().toString());
						
						File[] requiredLibraries = ConfigurationFilesManager.getRequiredLibraries(mc);
						
						for ( File libraryFile : requiredLibraries ){
							IRuntimeClasspathEntry e = JavaRuntime.newArchiveRuntimeClasspathEntry( new Path( libraryFile.getAbsolutePath() ) );
							e.setClasspathProperty(IRuntimeClasspathEntry.BOOTSTRAP_CLASSES);
							list.add( e.getMemento() );
						}
						//RuntimeClasspathEntry e = new RuntimeClasspathEntry(JavaCore.newLibraryEntry(new Path(DefaultOptionsManager.getBctJarFile().getAbsolutePath()), null, null) );
						
						
						attributes.put("org.eclipse.jdt.launching.CLASSPATH", list);
						//list.add(DefaultOptionsManager.getBctJarFile().getAbsolutePath() );
						
						wc.setAttributes(attributes);
						wc.doSave();
						
						for ( Object key : lc.getAttributes().keySet() ){
							System.out.println(key.getClass().getName());
							System.out.println(key.toString());
						}
						break;
					}
				}
				
			}
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		

		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	

	protected abstract File getProbeScript(MonitoringConfiguration mc) throws ConfigurationFilesManagerException;
}
