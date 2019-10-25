/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Options for the selection of the Java Resources to monitor
 * 
 * @author Fabrizio Pastore
 *
 */
public class ResourcesMonitoringOptions {
	private Set<String> resourcesToMonitor = new HashSet<String>();
	private boolean configured = false;
	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ResourcesMonitoringOptions( String project ){
		this.projectName = project;
	}

	public ResourcesMonitoringOptions() {
	}

	public IJavaProject getMonitoredProject() {
		if ( projectName == null ){
			return null;
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return JavaCore.create(project);
	}

	/**
	 * Returns all the fragment roots monitored by the configuration
	 * 
	 * @return
	 */
	public List<IPackageFragmentRoot> getAllMonitoredFragmentRoots(){
		List<IPackageFragmentRoot> result = new ArrayList<IPackageFragmentRoot>();

		//TODO: for C/C++ we should return something different
		//in the case of mangled function, just a list of functons
		//in the case of demangled the list of source files that show just the monitored functions (but maybe is too complex)
		try {
			if ( ! configured ){
				return getDefaultMonitoredFragmentRootsForProject(getMonitoredProject());
			}


			for ( String resource : resourcesToMonitor ){
				Path p = new Path( resource );
				IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(p.segment(0));

				IJavaProject jp = JavaCore.create(prj);

				for ( IPackageFragmentRoot fragment : jp.getAllPackageFragmentRoots() ){
					if ( resourcesToMonitor.contains(getFragmentRootKey(fragment)) ){
						result.add(fragment);
					}
				}	
			}

		} catch (JavaModelException e) {
		}

		return result;
	}

	/**
	 * Returns the fragment roots that a monitoring configuration monitors for a certain project
	 * 
	 * @param project
	 * @return
	 * @throws JavaModelException
	 */
	public List<IPackageFragmentRoot> getMonitoredFragmentRootsForProject( IJavaProject project ) throws JavaModelException{
		if ( project == null ){
			return new ArrayList<IPackageFragmentRoot>(0);
		}

		if ( ! configured ){
			return getDefaultMonitoredFragmentRootsForProject(project);
		}

		List<IPackageFragmentRoot> result = new ArrayList<IPackageFragmentRoot>();
		for ( IPackageFragmentRoot fragment : project.getAllPackageFragmentRoots() ){
			if ( resourcesToMonitor.contains(getFragmentRootKey(fragment)) ){
				result.add(fragment);
			}
		}

		return result;

	}

	private String getFragmentRootKey(IPackageFragmentRoot fragment) throws JavaModelException {
		return fragment.getPath().toString();
	}


	private List<IPackageFragmentRoot> getDefaultMonitoredFragmentRootsForProject( IJavaProject project ) throws JavaModelException{
		List<IPackageFragmentRoot> result = new ArrayList<IPackageFragmentRoot>();

		if ( project == null ){
			return result;
		}

		for ( IPackageFragmentRoot fragment : project.getAllPackageFragmentRoots() ){

			if ( fragment.getKind() == IPackageFragmentRoot.K_SOURCE ){
				result.add(fragment);
			}
		}
		return result;
	}

	public void addFragmentToMonitor(IPackageFragmentRoot fragment){
		configured = true;
		try {
			resourcesToMonitor.add(getFragmentRootKey(fragment));
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<String> getResourcesToMonitor() {
		return resourcesToMonitor;
	}

	public void setResourcesToMonitor(Set<String> resourcesToMonitor) {
		this.resourcesToMonitor = resourcesToMonitor;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	/**
	 * Returns true if the file is part of a package fragment to monitor
	 * @param path
	 */
	public boolean isResourceMonitored(IPath fullpath) {

		for ( IPackageFragmentRoot fragment : getAllMonitoredFragmentRoots() ){
			if ( fragment.getPath().isPrefixOf(fullpath) ){
				return true;
			}

		}

		return false;
	}

	public boolean isResourceMonitored(IPackageFragmentRoot packageFragmentRoot) {
		IPath packageFragmentRootPath = packageFragmentRoot.getPath();


		for ( IPackageFragmentRoot monitoredFragment : getAllMonitoredFragmentRoots() ){
			//We check on path because the equals on the fragmentRoot object does not work correctly for us
			if ( packageFragmentRoot.equals(monitoredFragment) ){
				return true;
			}

		}
		return false;
	}

}
