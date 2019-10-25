package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import util.componentsDeclaration.Component;


public class AddComponentButtonListener implements SelectionListener {

	ComponentDefinitionShell shell;
	Text nameComponentFilter;
	ComponentsConfigurationManager componentManager;
	Text textPackage ;
	
	public AddComponentButtonListener(ComponentDefinitionShell shell,Text nameComponentFilter,ComponentsConfigurationManager componentManager)
	{
		
		this.shell=shell;
		this.nameComponentFilter=nameComponentFilter;

		this.componentManager = componentManager;
	}
	

	public void widgetDefaultSelected(SelectionEvent e) {
		

	}

	public void widgetSelected(SelectionEvent event) {
		System.out.println("Salvataggio Componente");


		System.out.println("aggiunto componente");
		try{
		Component component = shell.getDefinedComponent();
		if ( ! componentManager.contains(component) ){
			try {
				componentManager.addComponent( component );
			} catch (ComponentManagerException e) {
				//FIXME
				// TODO Blocco catch generato automaticamente
				e.printStackTrace();
			}
		}
		} catch ( Throwable t ){
			t.printStackTrace();
		}
		
		

	}



}

