/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;

import conf.InteractionInferenceEngineSettings;
import conf.SettingsException;

import recorders.FileCBEViolationsRecorder;
import util.componentsDeclaration.Component;
import util.componentsDeclaration.CppMangledSignatureParser;
import util.componentsDeclaration.SignatureParser;


/**
 * A monitoring configuration object describe how an application should be monitored by BCT and where information should be stored.
 * It contains information about flattener options, components definition and storage definition.
 * 
 * @author Fabrizio Pastore
 * @param <T>
 *
 */
public class MonitoringConfiguration<T> {

	///Type of the TestCasesRegistry to use
	public static final String BCT_TEST_CASES_REGISTRY = "bct.testCasesRegistry";
	
	private FlattenerOptions flattenerOptions;
	private ComponentsConfiguration componentsConfiguration;
	private String configurationName;
	private String referredProjectName;
	private StorageConfiguration storageConfiguration;
	private Properties invariantGeneratorOptions;
	private Properties fsaEngineOptions;
	private Properties violationsRecorderOptions;
	private ActionsMonitoringOptions actionsMonitoringOptions;
	private TestCasesMonitoringOptions testCasesMonitoringOptions;
	private ResourcesMonitoringOptions resourcesMonitoringOptions;
	private Properties interactionCheckerOptions;
	private AdditionalInferenceOptions additionalInferenceOptions;
	private HashMap<Class,Object> additionalConfigurations = new HashMap<Class,Object>();
	
	public static class ConfigurationTypes {
		public static final int JavaMonitoring = 0;
		public static final int C_Regression_Config = 1;
		public static final int C_Config = 2;
		public static final int VART_Config = 3;
	}
	
	
	private int configurationType = 0;
	
	
	public HashMap<Class, Object> getAdditionalConfigurations() {
		return additionalConfigurations;
	}

	public void setAdditionalConfigurations(
			HashMap<Class, Object> additionalConfigurations) {
		this.additionalConfigurations = additionalConfigurations;
	}

	public <X> X getAdditionalConfiguration( Class<X> configClass ){
		return (X) additionalConfigurations.get(configClass);
	}
	
	public <X> X  putAdditionalConfiguration( Class<X> configClass, X configurationObject ){
		return (X) additionalConfigurations.put(configClass,configurationObject);
	}
	
	public int getConfigurationType() {
		return configurationType;
	}

	public void setConfigurationType(int configurationType) {
		this.configurationType = configurationType;
	}

	public void setInteractionCheckerOptions(Properties interactionCheckerOptions) {
		this.interactionCheckerOptions = interactionCheckerOptions;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	/**
	 * Default constructor, needed for serialization.
	 * 
	 */
	public MonitoringConfiguration(){
		
	}

	/**
	 * Construct a monitoring configuration
	 * 
	 * @param name
	 * @param monitoringOptions 
	 */
	public MonitoringConfiguration( String name, StorageConfiguration storageConfiguration, FlattenerOptions flattenerOptions, ComponentsConfiguration componentsConfiguration, ActionsMonitoringOptions monitoringOptions,
			TestCasesMonitoringOptions testCasesMonitoringOptions){
		this.configurationName = name;
		this.flattenerOptions=flattenerOptions;
		this.componentsConfiguration = componentsConfiguration;
		this.storageConfiguration = storageConfiguration;
		this.actionsMonitoringOptions = monitoringOptions;
		this.testCasesMonitoringOptions = testCasesMonitoringOptions;
	}

	public void setNameConf(String nome)
	{
		configurationName=nome;
	}
	
	public String getNameConf ()
	{
		return configurationName;
	}
	
	public void setFlattenerOptions(FlattenerOptions flattenerOp)
	{
		flattenerOptions=flattenerOp;
	}
	
	public FlattenerOptions getFlattenerOptions ()
	{
		return flattenerOptions;
	}

	public ComponentsConfiguration getComponentsConfiguration() {
		return componentsConfiguration;
	}

	public void setComponentsConfiguration(
			ComponentsConfiguration componentsConfiguration) {
		this.componentsConfiguration = componentsConfiguration;
	}

	public StorageConfiguration getStorageConfiguration() {
		return storageConfiguration;
	}

	public void setStorageConfiguration(StorageConfiguration storageConfiguration) {
		this.storageConfiguration = storageConfiguration;
	}

	/**
	 * Returns the invariant generator options
	 * @return
	 */
	public Properties getInvariantGeneratorOptions() {
		return  invariantGeneratorOptions ;
	}

	public void setInvariantGeneratorOptions(Properties invariantGeneratorOptions) {
		this.invariantGeneratorOptions = invariantGeneratorOptions;
	}
	
	/**
	 * Returns the inference engine options
	 * @return
	 * @throws OptionalFieldNotSetException
	 */
	public Properties getFsaEngineOptions() {
		return fsaEngineOptions;
	}

	public void setFsaEngineOptions(Properties fsaEngineOptions) {
		this.fsaEngineOptions = fsaEngineOptions;
	}

	/**
	 * Returns the violations recorder options
	 * @return
	 * 
	 */
	public Properties getViolationsRecorderOptions(){
		
		if ( violationsRecorderOptions == null ){
			violationsRecorderOptions = new Properties();
		}
		
		//FIXME: we are always returning the CBE recorded, we need options from the GUI
		violationsRecorderOptions.setProperty("type", FileCBEViolationsRecorder.class.getCanonicalName() );
		
		
//		if( getTestCasesMonitoringOptions().isMonitorTestCases() ){
//			violationsRecorderOptions.setProperty("type", FileActionsTestsViolationsRecorder.class.getCanonicalName());
//		} else if( getActionsMonitoringOptions().isMonitorActions() ){
//			violationsRecorderOptions.setProperty("type", FileActionsViolationsRecorder.class.getCanonicalName());
//		}
		
		//violationsRecorderOptions.setProperty("type", File);
		
		return violationsRecorderOptions ;
	}
	
	public void setViolationsRecorderOptions(Properties violationsRecorderOptions) {
		this.violationsRecorderOptions = violationsRecorderOptions;
	}

	/**
	 * Returns the workspace path of the associated project. If no project is associated with this Monitoring COnfiguration returns null
	 * @return
	 */
	public String getReferredProjectName() {
		return referredProjectName;
	}

	public void setReferredProjectName(String referredProjectName) {
		this.referredProjectName = referredProjectName;
	}

	public ActionsMonitoringOptions getActionsMonitoringOptions() {
		return actionsMonitoringOptions;
	}

	public void setActionsMonitoringOptions(
			ActionsMonitoringOptions actionsMonitoringOptions) {
		System.out.println("setA "+actionsMonitoringOptions);
		this.actionsMonitoringOptions = actionsMonitoringOptions;
	}

	public TestCasesMonitoringOptions getTestCasesMonitoringOptions() {
		return testCasesMonitoringOptions;
	}

	public void setTestCasesMonitoringOptions(
			TestCasesMonitoringOptions testCasesMonitoringOptions) {
		this.testCasesMonitoringOptions = testCasesMonitoringOptions;
	}

	public ResourcesMonitoringOptions getResourcesMonitoringOptions() {
		if ( resourcesMonitoringOptions == null ){
			resourcesMonitoringOptions =  new ResourcesMonitoringOptions(referredProjectName);
		}
		return resourcesMonitoringOptions;
	}

	public void setResourcesMonitoringOptions(
			ResourcesMonitoringOptions resourcesMonitoringOptions) {
		this.resourcesMonitoringOptions = resourcesMonitoringOptions;
	}

	public InteractionInferenceEngineSettings getFsaEngineSettings() {
		try {
			return new InteractionInferenceEngineSettings(getFsaEngineOptions());
		} catch (SettingsException e) {
			return null;
		}
	}

	public Properties getInteractionCheckerOptions() {
		return interactionCheckerOptions;
	}

	public void setAdditionalInferenceOptions(AdditionalInferenceOptions opts) {
		additionalInferenceOptions = opts;
	}

	public AdditionalInferenceOptions getAdditionalInferenceOptions() {
		return additionalInferenceOptions;
	}

	public static MonitoringConfiguration createMonitoringConfiguration(
			String resourceName, StorageConfiguration storageConfiguration,
			FlattenerOptions opt, String referredProjectName, int configurationType ) {
		ActionsMonitoringOptions mo = new ActionsMonitoringOptions();
		TestCasesMonitoringOptions tcMo = new TestCasesMonitoringOptions();
		
		TestCasesRegistryOptions tcr;
		
		String testCasesRegistry = System.getProperty(BCT_TEST_CASES_REGISTRY, "MEMORY");
		switch( testCasesRegistry ){
		case "FILE" :
			tcr = new TestCasesFileRegistryOptions();
			break;
		case "TXTFILE" :
			tcr = new TestCasesTextFileRegistryOptions();
			break;
		default:
			tcr = new TestCasesMemoryRegistryOptions();
		}
		
		
		tcMo.setTestCasesRegistryOptions(tcr);
		
		
		
		MonitoringConfiguration mrc = new MonitoringConfiguration(resourceName,storageConfiguration,opt,null,mo,tcMo);
		mrc.setReferredProjectName(referredProjectName);
		mrc.setConfigurationType(configurationType);
		
		ComponentsConfiguration componentsConfiguration = new ComponentsConfiguration(mrc.getConfigurationName(), new ArrayList<CallFilter>(), new ArrayList<Component>(), true );
		
		mrc.setComponentsConfiguration(componentsConfiguration);
		
		
		Properties invGenOptions; 
		try {
			invGenOptions = WorkspaceOptionsManager.getWorkspaceOptions().getInvariantGeneratorOptions();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invGenOptions = new Properties();
		}
		mrc.setInvariantGeneratorOptions(invGenOptions);
		
		Properties fsaEngineOptions;
		try {
			fsaEngineOptions = WorkspaceOptionsManager.getWorkspaceOptions().getDefaultFSAInferenceEngineOptions();
		} catch (CoreException | DefaultOptionsManagerException e) {
			fsaEngineOptions = new Properties();
		}
		
		if ( mrc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config || mrc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config){
			//HACK to improve C/C++ monitoringwith default
			fsaEngineOptions.setProperty("maxTrustLen","7" );
			fsaEngineOptions.setProperty("minTrustLen","7" );
			fsaEngineOptions.setProperty("cutOffSearch", "true");
		}
		
		
		mrc.setFsaEngineOptions(fsaEngineOptions);
		
		return mrc;
	}
	
	public static MonitoringConfiguration createMonitoringConfiguration(
			String resourceName, StorageConfiguration storageConfiguration,
			CRegressionConfiguration regressionConfiguration,
			FlattenerOptions opt, String referredProjectName, boolean identifyComponentsToMonitor) {
		
		
		MonitoringConfiguration mrc = createMonitoringConfiguration(resourceName, storageConfiguration, opt, referredProjectName, MonitoringConfiguration.ConfigurationTypes.C_Regression_Config );
		
		mrc.putAdditionalConfiguration(CRegressionConfiguration.class, regressionConfiguration );
		
		
		
		
		
		
			//Now set the components to monitor after extracting them from teh regression info

			try {
				ArrayList<Component> components;
				if ( identifyComponentsToMonitor ){
					components = CRegressionAnalysisUtil.getComponentsToMonitor( mrc, regressionConfiguration );
				} else {
					components = new ArrayList<>();
				}

				ComponentsConfiguration componentsConfiguration = new ComponentsConfiguration(mrc.getConfigurationName(), new ArrayList<CallFilter>(), components, true );

				mrc.setComponentsConfiguration(componentsConfiguration);

				SignatureParser signatureParser;
				if ( regressionConfiguration.isUseDemangledNames() ){
					//FIXME: Add demangled parser
					signatureParser=null;
				} else {
					signatureParser = new CppMangledSignatureParser();
				}

				for ( Component component : mrc.getComponentsConfiguration().getComponents() ){
					component.setSignatureParser(signatureParser);
				}

			} catch (ConfigurationFilesManagerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		return mrc;
	}


}
