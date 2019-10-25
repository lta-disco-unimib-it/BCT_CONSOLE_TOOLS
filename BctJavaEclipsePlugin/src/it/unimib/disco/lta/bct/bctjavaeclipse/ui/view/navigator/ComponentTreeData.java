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
