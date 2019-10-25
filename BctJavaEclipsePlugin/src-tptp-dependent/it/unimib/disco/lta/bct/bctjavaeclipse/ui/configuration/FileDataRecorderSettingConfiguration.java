package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

public class FileDataRecorderSettingConfiguration extends AbstractConfigurationSetting{

	public String getTitleConf()
	{
		return "File Data Recorder Setting Configuration";
	}
	
	public String getDescriptionConf()
	{
		return "Descrizione File Setting";
	}
	
	public String getMethodData()
	{
		return "ChiamataRun";
	}
	public void initialize()
	{

		/* Create the configuration page if it has not yet been created */
		if (configurationPageSetting == null)
		{
			System.out.println("nullPage");
			configurationPageSetting = new FileDataRecorderSettingConfigurationPage();
			System.out.println(configurationPageSetting.getPageName());
		}
	}
}
