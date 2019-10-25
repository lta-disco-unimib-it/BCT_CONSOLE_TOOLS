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
