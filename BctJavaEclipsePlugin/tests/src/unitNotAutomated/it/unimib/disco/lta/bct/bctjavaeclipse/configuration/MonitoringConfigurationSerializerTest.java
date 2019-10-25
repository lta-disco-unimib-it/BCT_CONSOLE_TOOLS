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
package it.unimib.disco.lta.bct.bctjavaeclipse.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;

public class MonitoringConfigurationSerializerTest {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		// TODO Stub di metodo generato automaticamente
		testSerializeSimpleConfiguration();
	}

	private static void testSerializeSimpleConfiguration() {
		MonitoringConfiguration mc = getSimpleMOnitoringConfiguration();
		File dest = new File("simpleMonitoringConfiguration.xml");
		try {
			MonitoringConfigurationSerializer.serialize(dest, mc);
		} catch (FileNotFoundException e) {
			System.err.println("FAILURE!");
			e.printStackTrace();
		}
	}

	private static MonitoringConfiguration getSimpleMOnitoringConfiguration() {
		
		ArrayList<MatchingRule> rule= new ArrayList<MatchingRule>();
		ArrayList<MatchingRule> rule2= new ArrayList<MatchingRule>();
		ArrayList<String>classToIgnore=new ArrayList<String>();
//		
		classToIgnore.add("uno.class");
		classToIgnore.add("due.class");;
		MatchingRule mr = new MatchingRuleExclude("COpackage","COclass","COmethod");
		MatchingRule mr2 = new MatchingRuleExclude("COpackage2","COclass2","COmethod2");
		
		rule.add(mr);
		rule2.add(mr2);
		
		Component c1= new Component("Componente",rule);
		Component c2= new Component("Componente2",rule2);
		
		Collection <Component> components= new ArrayList<Component>();
		components.add(c1);
		components.add(c2);
		
		FlattenerOptions op=new FlattenerOptions();
		
		op.setSmashAggregation(true);
		op.setMaxDepth(11);
		op.setFieldRetriever("all");
		op.setClassToIgnore(classToIgnore);
		
		
		LinkedList<CallFilter> cfilters = new LinkedList<CallFilter>();
		
		MatchingRule cfr=new MatchingRuleExclude("CLpackage","CLclass","CLmethod");
		
		CallFilter callFilter = new CallFilterGlobal();
		callFilter.addRule(cfr);
		
		cfilters.add(callFilter);
		
		ComponentsConfiguration cc = new ComponentsConfiguration("Conf Name",cfilters,components,false);

		StorageConfiguration sc = new FileStorageConfiguration("/tmp/");
		
		ActionsMonitoringOptions mo = new ActionsMonitoringOptions();
		TestCasesMonitoringOptions tcmo = new TestCasesMonitoringOptions();
		
		MonitoringConfiguration mc = new MonitoringConfiguration("Conf Name",sc,op,cc,mo,tcmo);
		
		
		CRegressionConfiguration conf = new CRegressionConfiguration();
		conf.setOriginalSwExecutable("/home/fabrizio/Programs/coreutils-7.5/src/timeout");
		conf.setOriginalSwSourcesFolder("/home/fabrizio/Programs/coreutils-7.5/");
		conf.setModifiedSwExecutable("/home/fabrizio/Programs/coreutils-7.6-mod/src/timeout");
		conf.setModifiedSwSourcesFolder("/home/fabrizio/Programs/coreutils-7.6-mod/");
		conf.addSourceProgramPoint(new ProgramPoint("myPath",23));
		mc.putAdditionalConfiguration(CRegressionConfiguration.class, conf);
		
		return mc;
	}
	


}
