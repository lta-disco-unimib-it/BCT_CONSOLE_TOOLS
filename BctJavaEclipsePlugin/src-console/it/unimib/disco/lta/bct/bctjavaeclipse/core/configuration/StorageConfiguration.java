package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public interface StorageConfiguration {

	public enum ConfigurationType  { FILE, DB };
	
	public ConfigurationType getConfigurationType();
}
