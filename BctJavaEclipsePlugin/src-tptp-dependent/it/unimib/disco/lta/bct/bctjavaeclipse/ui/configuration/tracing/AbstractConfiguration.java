package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.tracing;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.tptp.trace.ui.provisional.launcher.DataCollectionEngineAttribute;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfiguration;
import org.eclipse.tptp.trace.ui.provisional.launcher.IConfigurationPage;


/**
 * An abstract configuration that will only display a single page with a label.
 */
public abstract class AbstractConfiguration  implements  IConfiguration
{
  
	Shell shell;	  
	Shell box;	  
	Composite result;
	protected IConfigurationPage configurationPage;
	public AbstractConfiguration()
	{
		super();
	}
	  

	/**
	 * Return the configuration pages of this configuration.
	 */
	public IConfigurationPage[] getConfigurationPages()
	{
		return new IConfigurationPage[]{configurationPage};
	}

	/**
	 * Normally client would use this to write the selected options of the configuration wizard to
	 * the launch configuration. 
	 */
	public boolean finishConfiguration(ILaunchConfigurationWorkingCopy workingCopy)
	{
		//tasto finish della configurazione dell'analysis type.
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
							
 

