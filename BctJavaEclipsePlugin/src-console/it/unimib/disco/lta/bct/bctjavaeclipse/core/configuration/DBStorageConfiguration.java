package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;


public class DBStorageConfiguration implements StorageConfiguration {

	private String password;
	private String user;
	private String uri;

	/**
	 * Default Constructor leave for serialization 
	 */
	public DBStorageConfiguration(){
		
	}
	
	public DBStorageConfiguration(String uri, String user, String password) {
		this.uri = uri;
		this.user = user;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ConfigurationType getConfigurationType() {
		return StorageConfiguration.ConfigurationType.DB;
	}

}
