package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.tptp.trace.ui.provisional.launcher.DataCollectionEngineAttribute;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfiguration;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfigurationPage;

public abstract class AbstractConfigurationSetting  implements  IConfiguration
{
  
	Shell shell;	  
	Shell box;	  
	Composite result;
	protected IConfigurationPage configurationPageSetting;
	public AbstractConfigurationSetting()
	{
		super();
	}
	  

	/**
	 * Return the configuration pages of this configuration.
	 */
	public IConfigurationPage[] getConfigurationPages()
	{
		return new IConfigurationPage[]{configurationPageSetting};
	}

	/**
	 * Normally client would use this to write the selected options of the configuration wizard to
	 * the launch configuration. 
	 */
	public boolean finishConfiguration(ILaunchConfigurationWorkingCopy workingCopy)
	{
		return true;
	}

	/**
	 * Normally this is used to return the attributes that is understandable by the back end engine
	 * that starts the profile/monitor session.
	 */
	public DataCollectionEngineAttribute[] getAttributes()
	{
		return new DataCollectionEngineAttribute[]{};
	}

	/**
	 * Return the label text that will be displayed in the single page 
	 * configuration wizard.
	 */
//	public abstract String getLabelText();
	public abstract String getTitleConf();
	public abstract String getDescriptionConf();
	public abstract String getMethodData();
	
}
