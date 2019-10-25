package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class ResourceButtonListener implements SelectionListener {

	ResourcesPage composite;
	Resource resource;
	
	public ResourceButtonListener(ResourcesPage composite) {
	
		this.composite=composite;
		
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Stub di metodo generato automaticamente

	}

	public void widgetSelected(SelectionEvent event) {
		
////		System.out.println("DIR "+ composite.getConfigurationName().getText());
//		if (composite.isSelectedFile()==true)
//		{
////			System.out.println("SALVA FILE");
//			resource = new ResourceFile(composite.getResourceName().getText(),composite.getDataDirFileText().getText(),monitorConfiguration.getConfigurationName().getText());
//		}
//		else
//		{
//			if(composite.isSelectedDB()==true)
//			{
////				System.out.println("SALVA DB");
//				resource = new ResourceDB(composite.getResourceName().getText(),composite.getUriText().getText(),composite.getUserText().getText(),composite.getPasswordText().getText(),monitorConfiguration.getConfigurationName().getText());
//			}
//		}
		
//		try{
//
//			System.out.println("Creazione RISORSA XML ");
//			File filename= new File("c:/risorsa.xml");	
//
//			ResourceSerializer.serialize(filename, composite.createResources());		
//			System.out.println("Chiusura file");
//			
//			
//
//			}catch(FileNotFoundException e){
//			      System.out.println("not found");}
//
	}

}
