package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This class contains the default BCt configuration options
 * 
 * We expect to have just one instance of this class created by the DefaultOptionsManager.
 * 
 * @author Fabrizio Pastore
 *
 */
public class BctDefaultOptions implements BCTObservable {

	private FlattenerOptions flattenerOptions;
	private Properties invariantGeneratorOptions;
	private Properties kbehaviorInferenceEngineOptions;
	private BCTObservaleIncapsulated observationHandler;
	private Properties reissEngineOptions;
	private Properties ktailEngineOptions;
	private Properties kinclusionEngineOptions;
	private HashMap<String,Properties> daikonConfigs = new HashMap<String, Properties>();
	private HashSet<String> immutableConfigs = new HashSet<String>();
	private Properties violationsRecorderOptions;
	
	public BctDefaultOptions(FlattenerOptions flattenerOptions,
			Properties invariantGeneratorOptions,
			Properties kbehaviorInferenceEngineOptions,
			Properties ktailEnigineOptions,
			Properties reissEnigineOptions,
			Properties kinclusionEnigineOptions,
			Properties violationsRecorderOptions,
			Map<? extends String, ? extends Properties> defaultDaikonConfigs) {
		//System.out.println("BctDefault Options constructor start");
		this.flattenerOptions = flattenerOptions;
		this.invariantGeneratorOptions = invariantGeneratorOptions;
		this.kbehaviorInferenceEngineOptions = kbehaviorInferenceEngineOptions;
		this.reissEngineOptions = reissEnigineOptions;
		this.ktailEngineOptions = ktailEnigineOptions;
		this.kinclusionEngineOptions = kinclusionEnigineOptions;
		this.violationsRecorderOptions = violationsRecorderOptions;
		observationHandler =  new BCTObservaleIncapsulated( this );
		
		
		//System.out.println("DC size "+defaultDaikonConfigs.size());
		daikonConfigs.putAll(defaultDaikonConfigs);
		
		//System.out.println("BctDefault Options constructor end");
	}


	public FlattenerOptions getFlattenerOptions() {
		return new FlattenerOptions ( flattenerOptions );
	}

	public void setFlattenerOptions(FlattenerOptions flattenerOptions) {
		this.flattenerOptions = flattenerOptions;
		//observationHandler.notifyBCTObservers("update");
	}

	public Properties getInvariantGeneratorOptions() {
		Properties p = new Properties ( );
		p.putAll(invariantGeneratorOptions);
		return p;
	}

	public void setInvariantGeneratorOptions(Properties invariantGeneratorOptions) {
		this.invariantGeneratorOptions = invariantGeneratorOptions;
	}


	public void addBCTObserver(BCTObserver bctObserver) {
		observationHandler.addBCTObserver(bctObserver);
	}

	public Properties getKbehaviorInferenceEngineOptions() {
		Properties p = new Properties ( );
		p.putAll(kbehaviorInferenceEngineOptions);
		return p;
	}

	public void setKbehaviorInferenceEngineOptions(
			Properties kbehaviorInferenceEngineOptions) {
		this.kbehaviorInferenceEngineOptions = kbehaviorInferenceEngineOptions;
	}

	public Properties getReissEnigineOptions() {
		Properties p = new Properties ( );
		p.putAll(reissEngineOptions);
		return p;
	}

	public void setReissEnigineOptions(Properties reissEnigineOptions) {
		this.reissEngineOptions = reissEnigineOptions;
		observationHandler.notifyBCTObservers("update");
	}

	public Properties getKtailEnigineOptions() {
		Properties p = new Properties ( );
		p.putAll(ktailEngineOptions);
		return p;
	}

	public void setKtailEnigineOptions(Properties ktailEnigineOptions) {
		this.ktailEngineOptions = ktailEnigineOptions;
		observationHandler.notifyBCTObservers("update");
	}

	public Properties getKinclusionEnigineOptions() {
		Properties p = new Properties ( );
		p.putAll(kinclusionEngineOptions);
		return p;
	}

	public void setKinclusionEnigineOptions(Properties kinclusionEnigineOptions) {
		this.kinclusionEngineOptions = kinclusionEnigineOptions;
		observationHandler.notifyBCTObservers("update");
	}

	/**
	 * Add a daikon configuration property to the existing configuration properties.
	 * If a property with the same name already exists throw an exception.
	 * 
	 * @param name
	 * @param p
	 * @throws BctDefaultOptionsException 
	 */
	public void addDaikonConfigProperties( String name, Properties p) throws BctDefaultOptionsException{
		if ( daikonConfigs.containsKey(name) ){
			throw new BctDefaultOptionsException("Cannot create the Daikon config with name "+name+" another ocnfiguration with the same name alreay exists");
		}
		if ( immutableConfigs.contains(name) ){
			throw new BctDefaultOptionsException("Cannot overwrite the Daikon config with name "+name);
		}
		daikonConfigs.put(name, p);
		observationHandler.notifyBCTObservers("updateDaikonProperties");
	}
	
	/**
	 * Add daikon configuration properties in string form.
	 * 
	 * @param name
	 * @param p
	 * @throws BctDefaultOptionsException 
	 */
	public void addDaikonConfigProperties( String name, String stringProperties) throws BctDefaultOptionsException{
		
		Properties p = new Properties();
		
		ByteArrayInputStream is = new ByteArrayInputStream(stringProperties.getBytes());
		try {
			p.load(is);
			addDaikonConfigProperties(name, p);
		} catch (IOException e) {
			throw new BctDefaultOptionsException("Wrong daikon properties format");
		}
	}

	/**
	 * Modify a daikon property overwriting its contents.
	 * If the name corresponds to an immutable one throws an exception.
	 * If the config does not exists throw an exception.
	 * 
	 * @param name
	 * @param p
	 * @throws BctDefaultOptionsException
	 */
	public void modifyDaikonConfigProperties( String name, Properties p) throws BctDefaultOptionsException{
		if ( ! daikonConfigs.containsKey(name) ){
			throw new BctDefaultOptionsException("The configuration with name "+name+" does not exists");
		}
		if ( immutableConfigs.contains(name) ){
			throw new BctDefaultOptionsException("Cannot overwrite the Daikon config with name "+name);
		}
		daikonConfigs.put(name, p);
		observationHandler.notifyBCTObservers("updateDaikonProperties");
	}
	
	/**
	 * Modify a daikon configuration properties in string form.
	 * 
	 * @param name
	 * @param p
	 * @throws BctDefaultOptionsException 
	 */
	public void modifyDaikonConfigProperties( String name, String stringProperties) throws BctDefaultOptionsException{
		
		Properties p = new Properties();
		
		ByteArrayInputStream is = new ByteArrayInputStream(stringProperties.getBytes());
		try {
			p.load(is);
			modifyDaikonConfigProperties(name, p);
		} catch (IOException e) {
			throw new BctDefaultOptionsException("Wrong daikon properties format");
		}
	}
	
	/**
	 * Removes the daikon config with the given name.
	 * If the name is one of the immutable config names throws an exception.
	 * 
	 * @param name
	 * @throws BctDefaultOptionsException
	 */
	public void removeDaikonConfigProperties( String name ) throws BctDefaultOptionsException{
		if ( immutableConfigs.contains(name) ){
			throw new BctDefaultOptionsException("Cannot remove the Daikon config with name "+name);
		}
		daikonConfigs.remove(name);
		observationHandler.notifyBCTObservers("updateDaikonProperties");
	}
	
	/**
	 * Returns all the available configuration properties names
	 * @return 
	 */
	public Set<String> getDaikonConfigPropertiesNames() {
		return daikonConfigs.keySet();
	}
	
	/**
	 * Return an hash map with the daikon configs, key is the config name, value is the config associated property object 
	 * @return
	 */
	public HashMap<String,Properties> getDaikonConfigs(){
		return new HashMap<String, Properties>(daikonConfigs);
	}
	
	/**
	 * Return the daikon config properties for a given daikon configuratin name
	 * 
	 * 
	 * @param daikonConfig
	 * @return 
	 * @throws BctDefaultOptionsException 
	 */
	public Properties getDaikonConfigProperties(String daikonConfig) throws BctDefaultOptionsException {
		Properties properties = daikonConfigs.get(daikonConfig);
		if ( properties == null ){
			throw new BctDefaultOptionsException( "No properties are associated with the given configuration name ");
		}
		Properties p = new Properties ( );
		p.putAll(properties);
		return p;
	}

	/**
	 * Add the passed names as immutable daikon config names
	 * 
	 * @param immutableConfigs
	 */
	public void addImmutableDaikonConfigs(List<String> immutableConfigs) {
		this.immutableConfigs.addAll(immutableConfigs);
	}
	
	/**
	 * Returns the list of daikon ummutable configs
	 * @return
	 */
	public Set<String> getImmutableDaikonConfigs() {
		return new HashSet<String>( immutableConfigs );
	}

	public void addImmutableDaikonConfigs(String[] defaultConfigsNames) {
		ArrayList<String> names = new ArrayList<String>(defaultConfigsNames.length);
		for ( String defaultConfigName : defaultConfigsNames ){
			names.add(defaultConfigName);
		}
		immutableConfigs.addAll(names);
	}


	public Properties getViolationsRecorderOptions() {
		Properties p = new Properties ( );
		p.putAll(violationsRecorderOptions);
		return p;
	}


	public void setViolationsRecorderOptions(Properties violationsRecorderOptions) {
		this.violationsRecorderOptions = violationsRecorderOptions;
	}


	public Properties getDefaultFSAInferenceEngineOptions() {
		return getKbehaviorInferenceEngineOptions();
	}

}
