package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.tracing;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfigurationPage;
import org.eclipse.tptp.trace.ui.provisional.launcher.IStatusListener;

public class DataRecordingConfigurationPage implements IConfigurationPage{
	
	
	String name = "DataRecordingPage";
	String title = "DataRecordingPage";
	String description = "DataRecordingPage";
	Composite result;

	public void reset(ILaunchConfiguration launchConfiguration)
	{
	}
	
	public void createControl(Composite parent)
	{
	    
		System.out.println("DRC.createControl()");
		DataRecordingComposite t=new DataRecordingComposite(parent);
	}
	
	
	////////////////////////////////////////////////
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
