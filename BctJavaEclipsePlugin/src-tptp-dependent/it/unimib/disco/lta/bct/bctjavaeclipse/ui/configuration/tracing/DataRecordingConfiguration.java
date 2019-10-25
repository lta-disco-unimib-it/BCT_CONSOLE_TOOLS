package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.tracing;


public class DataRecordingConfiguration extends AbstractConfiguration {

	public String getTitleConf()
	{
		return "Data Recording Configuration";
	}
	
	public String getDescriptionConf()
	{
		return "Data Recording";
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
			configurationPage = new DataRecordingConfigurationPage();
			System.out.println(configurationPage.getPageName());
		}
	}

}
