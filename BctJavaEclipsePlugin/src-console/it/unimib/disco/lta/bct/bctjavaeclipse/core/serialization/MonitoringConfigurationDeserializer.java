package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;



import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.AdditionalInferenceOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.DBStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourcesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesFileRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMemoryRegistryOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration.ModelChecker;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationDeserializer.NullOutputStream;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MonitoringConfigurationDeserializer {


//	static MonitoringConfiguration result;

	public static MonitoringConfiguration deserialize(File file)throws FileNotFoundException
	{
		//
		//HACK HACK HACK
		//

		ComponentsConfiguration cc = HackUtil.getFakeComponnetsConfiguration();

		FileStorageConfiguration fsc = HackUtil.getFakeFileStorageCOnfiguration();

		FlattenerOptions fo = new FlattenerOptions();

		ActionsMonitoringOptions mo = new ActionsMonitoringOptions();
		TestCasesMonitoringOptions tcMo = new TestCasesMonitoringOptions();
		TestCasesFileRegistryOptions fileROpt = new TestCasesFileRegistryOptions();
		tcMo.setTestCasesRegistryOptions(fileROpt);
		TestCasesMemoryRegistryOptions memROpt = new TestCasesMemoryRegistryOptions();
		AdditionalInferenceOptions aopts = new AdditionalInferenceOptions();
		
		MonitoringConfiguration mcfile = new MonitoringConfiguration("",fsc,fo,cc,mo,tcMo);
		mcfile.putAdditionalConfiguration(CRegressionConfiguration.class, new CRegressionConfiguration());
		mcfile.setAdditionalInferenceOptions(aopts);
		CConfiguration cconf = new CConfiguration();
		cconf.addSourceProgramPoint(new ProgramPoint("mypath", 23) );
		mcfile.putAdditionalConfiguration(CConfiguration.class,cconf);
		mcfile.setAdditionalInferenceOptions(aopts);
		
		VARTRegressionConfiguration vart = new VARTRegressionConfiguration();
		vart.setModelChecker(ModelChecker.CBMC);
		mcfile.putAdditionalConfiguration(VARTRegressionConfiguration.class, vart);
		
		XMLEncoder encoder = new XMLEncoder(new NullOutputStream());
		encoder.writeObject(mcfile);
		encoder.close();

		DBStorageConfiguration dbsc = HackUtil.getFakeDBStorageCOnfiguration();
		MonitoringConfiguration mcdb = new MonitoringConfiguration("",dbsc,fo,cc,mo,tcMo);

		ResourcesMonitoringOptions opt = new ResourcesMonitoringOptions();
		mcfile.setResourcesMonitoringOptions(opt);
		
		encoder = new XMLEncoder(new NullOutputStream());
		encoder.writeObject(mcdb);
		encoder.close();


		//HACK END


		XMLDecoder decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

		MonitoringConfiguration result = (MonitoringConfiguration) decoder.readObject();

		decoder.close();

		return result;

	}
}
