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
package console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;


import tools.InvariantGenerator;
import util.JavaRunner;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.FileSystemDataManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

public class InferJavaModels {



	/**
	 * @param args
	 * @throws ConfigurationFilesManagerException 
	 * @throws IOException 
	 * @throws conf.management.ConfigurationFilesManagerException 
	 * @throws InterruptedException 
	 * @throws DefaultOptionsManagerException 
	 * @throws InvocationTargetException 
	 * @throws CoreException 
	 */
	public static void main(String[] args) throws ConfigurationFilesManagerException, IOException, conf.management.ConfigurationFilesManagerException, DefaultOptionsManagerException, InterruptedException, CoreException, InvocationTargetException {
		String projectDir = args[0];
		ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);

		File monitoringConfigurationFile = projectVars.getMonitoringConfigurationFile();
		
		MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(monitoringConfigurationFile);

		File bctHome = ConfigurationFilesManager.getBctHomeDir(mrc);
		List<String> bctArgs = Arrays.asList(new String[]{"-default"});
		List<String> _vmArgs = Arrays.asList(new String[]{"-Dbct.home="+bctHome.getAbsolutePath()});
		
		List<String> vmArgs = new ArrayList<>();
		vmArgs.addAll( _vmArgs );
		vmArgs.addAll(JavaRunner.retrieveBctVMArgs());
		
		EnvironmentalSetter.setFlattenerType(flattener.flatteners.BreadthObjectFlattener.class);
		EnvironmentalSetter.setBctHome(bctHome.getAbsolutePath());
		
		conf.management.ConfigurationFilesManager.updateConfigurationFiles();
		
		conf.management.ConfigurationFilesManager.setDaikon_config("essentials");
		
//		EnvironmentalSetter.getInvariantGeneratorSettings().setSessionsToIgnore();
//		InvariantGenerator.main(new String[]{"-default"} );
		
		ModelInference.setupInferenceFilters(mrc, true, false, false);
		
		AnomaliesIdentifier.setupDefaultOptions(monitoringConfigurationFile, mrc);
		
		Properties opts = mrc.getInvariantGeneratorOptions();
		opts.setProperty("deleteTemporaryDir", "false");
		opts.setProperty(InvariantGeneratorSettings.Options.daikonConfig, "essentials");
		
		String metaHandlerString = System.getProperty("bct.inference.metaDataHandlerSettingsType");
		if ( metaHandlerString != null ){
			if ( metaHandlerString.isEmpty() ) {
				opts.remove(InvariantGeneratorSettings.Options.metaDataHandlerSettingsType);
			} else {
				opts.put(InvariantGeneratorSettings.Options.metaDataHandlerSettingsType, metaHandlerString );
			}
		}
		
		MonitoringConfigurationSerializer.serialize(projectVars.getMonitoringConfigurationFile(), mrc);

		ConfigurationFilesManager.updateConfigurationFiles(mrc);

		
//		AnomaliesIdentifier.inferModels(monitoringConfigurationFile, mrc);
		
//		ModelInference.inferModels(mrc, new NullProgressMonitor(), System.out, System.err);
//		Appendable sb = new StringBuffer();
		
		BufferedWriter obw = new BufferedWriter(new OutputStreamWriter(System.out), 24);
		BufferedWriter ebw = new BufferedWriter(new OutputStreamWriter(System.err), 24);
		
		FileSystemDataManager.deleteNormalizedFiles(mrc);
		FileSystemDataManager.deleteModels(mrc);

		JavaRunner.runMainInClass(InvariantGenerator.class, vmArgs , bctArgs , 0, new ArrayList<String>(), true, obw, ebw);
		obw.flush();
		obw.close();
		ebw.flush();
		ebw.close();
		
//		System.out.println(sb.toString());
		

	}

}
