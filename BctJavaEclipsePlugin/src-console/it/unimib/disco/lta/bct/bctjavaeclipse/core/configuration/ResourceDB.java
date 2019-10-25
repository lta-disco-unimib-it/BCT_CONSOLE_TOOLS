package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.DomainEntity;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FinderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.db.MapperFactoryDB;

public class ResourceDB implements Resource {

	private MapperFactoryDB mapperFactoryDB;
	private String resourceName;
	private String uriName;
	private String userName;
	private String password;
	private String configurationName;

	public ResourceDB(String resourceName, String uriName, String userName, String password, String configurationName) {
		this.resourceName=resourceName;
		this.uriName=uriName;
		this.userName=userName;
		this.password=password;
		this.configurationName=configurationName;
	}

	public ResourceDB(){}
	public FinderFactory getFinderFactory() {
		throw new RuntimeException("Not Implemented");
	}

	public void saveEntity(DomainEntity entity) {
		throw new RuntimeException("Not Implemented");
	}

	public String getName() {
		
		return resourceName;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
