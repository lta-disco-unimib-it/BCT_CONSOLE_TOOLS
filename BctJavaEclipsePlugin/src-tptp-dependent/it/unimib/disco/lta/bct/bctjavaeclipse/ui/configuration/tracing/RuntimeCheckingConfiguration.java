package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.tracing;




/**
 * The analysis type configuration
 */
public class RuntimeCheckingConfiguration extends AbstractConfiguration
{
	

	public String getTitleConf()
	{
		return "Runtime Checking Configuration";
	}
	
	public String getDescriptionConf()
	{
		return "Descrizione Runtime Checking";
	}
	
	public String getMethodData()
	{
		return "ChiamataRun";
	}
	public void initialize()
	{
		System.out.println("AC.initialize()");
		/* Create the configuration page if it has not yet been created */
		if (configurationPage == null)
		{
			System.out.println("nullPage");
			configurationPage = new RuntimeCheckingConfigurationPage();
			System.out.println(configurationPage.getPageName());
		}
	}
}

