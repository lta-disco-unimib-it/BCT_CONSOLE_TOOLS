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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;

import java.util.Collection;

import org.eclipse.jdt.core.IPackageFragmentRoot;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.SystemElement;

public class ComponentTreeData extends BctMonitoringConfigurationTreeData {
	
	public ComponentTreeData(String txtToDisplay, Object parent) {
		super(txtToDisplay, parent);
	}

	public SystemElement getComponent() {
		ComponentsConfiguration componentsConfiguration = super.getMonitoringConfiguration().getComponentsConfiguration();
		Collection<Component> components = componentsConfiguration.getComponents();
		String folderName = super.getTxtToDisplay();
		
		//Check environment component
		if (folderName.equals(PluginConstants.ENVIRONMENT_COMPONENT)) {
			return componentsConfiguration.getEnvironment();
		} else {
			//Check other components
			for(Component component : components) {
				if(component.getName().equals(super.getTxtToDisplay()))
					return component;
			}
		}
		return null;
	}
	
	public Collection<IPackageFragmentRoot> getMonitoredPackages() {
		return getMonitoringConfiguration().getResourcesMonitoringOptions().getAllMonitoredFragmentRoots();
	}
}
