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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.eclipse.core.BctCoreActivator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.hyades.probekit.ProbeInstrumenterDriver;
import org.eclipse.hyades.probekit.ProbeInstrumenterDriver.StaticProbeInstrumenterException;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;

public class ProbeInstrumenter {

	private String instrumenterExecutablePath;
	private List<Component> matchingRules;

	public String getInstrumenterExecutablePath() {
		return instrumenterExecutablePath;
	}

	public ProbeInstrumenter( String instrumenterExecutablePath ){
		this.instrumenterExecutablePath = instrumenterExecutablePath;
	}
	
	public void instrument(File probeScript, String resourcesToInstrument[]) throws ConfigurationFilesManagerException,
	InvocationTargetException {
		//ComponentsConfiguration cc;
		File dest;


		//cc = mc.getComponentsConfiguration();
		System.out.println("MC deserialized");

		System.out.println("Probescript generated "+probeScript);

		ProbeInstrumenterDriver dr = new ProbeInstrumenterDriver();
		if ( matchingRules != null ){
			dr.setMatchingRules( matchingRules );
		}
		dr.setExePath(getInstrumenterExecutablePath());

		//FIXME: change configurations

		try {
			System.out.println("Instrumenting");
			dr.instrumentItems(probeScript, resourcesToInstrument , true );
		} catch (Throwable e) {
			e.printStackTrace();
			throw new InvocationTargetException(e);
		}
	}

	public void setMatchingRules(List components) {
		this.matchingRules = components;
	}

	

}
