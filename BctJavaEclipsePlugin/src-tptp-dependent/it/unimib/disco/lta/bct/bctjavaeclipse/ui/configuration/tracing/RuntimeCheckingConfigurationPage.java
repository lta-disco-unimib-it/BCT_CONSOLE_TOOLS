package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.tracing;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfigurationPage;
import org.eclipse.tptp.trace.ui.provisional.launcher.IStatusListener;

public class RuntimeCheckingConfigurationPage implements IConfigurationPage{

	String name = "RuntimeCheckingPage";
	String title = "RuntimeCheckingPage";
	String description = "RuntimeCheckingPage";
	Composite result;
	
	public void reset(ILaunchConfiguration launchConfiguration)
	{
	}
	
	public void createControl(Composite parent)
	{
	    
	    RuntimeCheckingComposite t=new RuntimeCheckingComposite(parent);
	    
		System.out.println("RC.createControl()");
//		Button loadModels = new Button(result, SWT.PUSH);
//		loadModels.setText("Load Model");

	    
		

	}
	
	public String getPageName()
	{
		return name;
	}
	
	public String getTitle()
	{
		return title;
	}

	public ImageDescriptor getWizardBanner()
	{
		return null;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void addErrorListener(IStatusListener statusListener)
	{
		
	}
	
}
