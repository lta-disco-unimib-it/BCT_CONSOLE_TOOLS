package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;


public class FileStorageConfiguration implements StorageConfiguration {

	private String dataDirPath;

	/**
	 * Public constructor leave for serialization
	 * 
	 */
	public FileStorageConfiguration(){};
	
	public FileStorageConfiguration(String dataDirPath) {
		this.dataDirPath = dataDirPath;
	}

	public String getDataDirPath() {
		return dataDirPath;
	}

	public void setDataDirPath(String dataDirPath) {
		this.dataDirPath = dataDirPath;
	}

	public ConfigurationType getConfigurationType() {
		return StorageConfiguration.ConfigurationType.FILE;
	}

}
