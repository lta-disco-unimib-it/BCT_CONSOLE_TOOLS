package it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterComponent;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.DBStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;

public class HackUtil {

	public static class NullOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
			
		}
		
	}
	
	/**
	 * This method creates a fake component configuration, it is needed for the deserialization hack. It consists in creating a ComponentsConfiguration 
	 * object and serialize it to a NullOutputStream in order to have all classes loaded.
	 *   
	 * @return a fake ComponentsConfiguration
	 */
	static ComponentsConfiguration getFakeComponnetsConfiguration(){
		MatchingRule r = new MatchingRuleInclude("","","");
		MatchingRule re = new MatchingRuleExclude("","","");
		List<MatchingRule> l = new ArrayList<MatchingRule>();
		l.add(r);
		l.add(re);
		Component c = new Component("",l);
		CallFilter ca = new CallFilterGlobal();
		MatchingRule cr = new MatchingRuleInclude("","","");
		MatchingRule cre = new MatchingRuleExclude("","","");
		ca.addRule(cr);
		ca.addRule(cre);
		
		CallFilter cac = new CallFilterComponent();
		cr = new MatchingRuleInclude("","","");
		cre = new MatchingRuleExclude("","","");
		cac.addRule(cr);
		cac.addRule(cre);
		
		List<CallFilter> lr = new ArrayList<CallFilter>();
		lr.add(ca);
		lr.add(cac);
		
		
		ArrayList<Component> lc = new ArrayList<Component>();
		lc.add(c);
		ComponentsConfiguration cc = new ComponentsConfiguration("",lr,lc,true);
		return cc;
	}

	/**
	 * Return a fake FileStorageConfiguration it is used for the deserialization hack
	 * 
	 * @return
	 */
	public static FileStorageConfiguration getFakeFileStorageCOnfiguration() {
		FileStorageConfiguration fsc = new FileStorageConfiguration("");
		return fsc;
	}

	/**
	 * Return a fake DBStorageConfiguration it is used for the deserialization hack
	 * 
	 * @return
	 */
	public static DBStorageConfiguration getFakeDBStorageCOnfiguration() {
		DBStorageConfiguration dbsc = new DBStorageConfiguration("","","");
		return dbsc;
	}

}
