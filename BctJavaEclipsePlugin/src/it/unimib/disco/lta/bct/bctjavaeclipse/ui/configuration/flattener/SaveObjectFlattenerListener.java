package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.MonitoringConfigurationManager;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class SaveObjectFlattenerListener implements SelectionListener {

	ObjectFlattenerPage of;
	MonitoringConfigurationManager mcm;
	
	public SaveObjectFlattenerListener(ObjectFlattenerPage of, MonitoringConfigurationManager mcm)
	{

		this.of=of;
		this.mcm=mcm;
	}
	
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Stub di metodo generato automaticamente

	}

	public void widgetSelected(SelectionEvent e) {
		System.out.println("SaveFlattenerButtonPressed");
		System.out.println("gettingFlattenerOptions");
		FlattenerOptions flattenerOptions = of.getDefinedFlattenerOptions();
		System.out.println("settingFlattenerOptions");
		//mcm.setFlattenerOptions( flattenerOptions );

		of.shell.close();
	}
	

}
