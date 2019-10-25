package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Resource;

import java.util.Properties;

import conf.DBConnectionSettings;

/**
 * This is a util that translates the plugin resources objects into original BCT configuration objects
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public class BctResourcesUtil {

	/**
	 * Returns an InvariantGeneratorSettings object given a Resource
	 * @param resource
	 * @return
	 */
	public static Properties getInvariantGeneratorSettings(Resource resource) {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Returns a DBConnectionSettings object given a resource
	 * 
	 * @param resource
	 * @return
	 */
	public static DBConnectionSettings getDBConnectionSettings(Resource resource) {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Returns a ModelsFectherSettings object taht permits to refer to passed resource data
	 * 
	 * @param resource
	 * @return
	 */
	public static Properties getModelsFetcherSettings(Resource resource) {
		throw new RuntimeException("Not Implemented");
	}

	/**
	 * Returns the name of the method as it is recorded in the trace
	 * 
	 * @param resource
	 * @param method
	 * @return
	 */
	public static String getRawMethodName ( Resource resource, Method method ){
		//FIXME
		return method.getSignature();
	}
	
	
}
