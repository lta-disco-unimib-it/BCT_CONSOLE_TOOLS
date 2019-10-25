package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfigurationPage;
import org.eclipse.tptp.trace.ui.provisional.launcher.IStatusListener;

public class FileDataRecorderSettingConfigurationPage implements IConfigurationPage{

	String name = "FileDataRecorderSetting";
	String title = "FileDataRecorderSetting";
	String description = "FileDataRecorderSetting";
	Composite result;
	
	public void reset(ILaunchConfiguration launchConfiguration)
	{
	}
	
	public void createControl(Composite parent)
	{


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
