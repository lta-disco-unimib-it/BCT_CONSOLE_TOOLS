package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;


public class AddButtonListener  implements SelectionListener{

	
	ComponentDefinitionShell box;
	private ComponentsConfigurationManager componentManager;


	public AddButtonListener(ComponentsConfigurationManager componentsConfigurationComposite) {
		this.componentManager = componentsConfigurationComposite;
	}


	public void widgetDefaultSelected(SelectionEvent e)
	{
		
	}
	
	public void widgetSelected(SelectionEvent e)
	{

		box = new ComponentDefinitionShell(componentManager);


	}

}
