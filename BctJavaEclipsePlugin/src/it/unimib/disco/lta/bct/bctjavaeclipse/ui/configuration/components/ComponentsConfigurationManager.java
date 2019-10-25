package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import util.componentsDeclaration.Component;

public interface ComponentsConfigurationManager {

	public void addComponent(Component component) throws ComponentManagerException;
	
	public void removeComponent ( int idx );
	
	public Component getSelectedComponent() throws ComponentManagerException;

	public boolean contains(Component component);

	public Component getComponent(String from);

}
