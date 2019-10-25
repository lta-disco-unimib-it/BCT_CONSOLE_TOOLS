package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationDeserializer;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class BctComponentConfigurationLoadButton implements SelectionListener{

	private ComponentsConfigurationComposite monitoringConfigurationManager;
	
	public BctComponentConfigurationLoadButton(ComponentsConfigurationComposite componentsConfigurationComposite)
	{
		this.monitoringConfigurationManager=componentsConfigurationComposite;
	}
	
	public void widgetDefaultSelected(SelectionEvent e)
	{
		
	}
	
	public void widgetSelected(SelectionEvent e) {
		Shell shell=new Shell();
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Open");

		String userHome = System.getProperty("user.home");
		fd.setFilterPath(userHome);

		String[] filterExt = { "*.bctcc", "*.*" };
		fd.setFilterExtensions(filterExt);  	  
		String selected = fd.open();

		if ( selected == null )
			return;

		try {
			ComponentsConfiguration monitoringConfiguration = ComponentsConfigurationDeserializer.deserialize(new File(selected));
			
			monitoringConfigurationManager.load(monitoringConfiguration);
		} catch (FileNotFoundException e1) {
			MessageDialog.openError(new Shell(), "Error", "Cannot load the configuration "+e1.getMessage());
			e1.printStackTrace();
		} catch (ComponentManagerException e1) {
			MessageDialog.openError(new Shell(), "Error", "Cannot load the configuration "+e1.getMessage());
			e1.printStackTrace();
		}

	}
}
