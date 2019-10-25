package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.DomainEntity;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FinderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperFactoryFile;

import java.io.File;

/**
 * This class contains all the information to access BCT data stored in a particular folder in the file system. 
 *
 */
public class ResourceFile implements Resource {

	private MapperFactoryFile mapperFactoryFile;
	private File ioRawTracesDir;
	private File interactionRawTracesDir;
	private File interactionNormalizedTracesDir;
	private File ioModelsDir;
	private File interactionModelsDir;
	
	private String resourceName;
	private String dataDir;
	private String configurationName;
	private File ioNormalizedDeclsDir;
	private File ioNormalizedDtracesDir;
	private File dataRecordingDir;
	
	private String ioRawTracesDirName;
	private String interactionRawTracesDirName;
	private File dataPreprocessingDir;


	private String ioNormalizedDtracesDirName;
	private String interactionNormalizedTracesDirName;
	private String ioNormalizedDeclsDirName;


	public ResourceFile(String resourceName, String dataDirPath, String configurationName) {
		this.resourceName=resourceName;
		this.dataDir=dataDirPath;
		this.configurationName=configurationName;
		
		File bctDataDir = new File(dataDirPath);
		
		this.dataRecordingDir = new File(bctDataDir,ConfigurationUtil.getDataRecordingDirName());
		this.ioRawTracesDirName = ConfigurationUtil.getFileIoTraceRawDirName();
		this.interactionRawTracesDirName = ConfigurationUtil.getFileInteractionTraceRawDirName();
		
		this.dataPreprocessingDir = new File(bctDataDir,ConfigurationUtil.getDataNormalizedDirName());
		this.ioNormalizedDtracesDirName = ConfigurationUtil.getFileIoTraceNormalizedDtraceDirName();
		this.ioNormalizedDeclsDirName = ConfigurationUtil.getFileIoTraceNormalizedDeclsDirName();
		this.interactionNormalizedTracesDirName = ConfigurationUtil.getFileInteractionNormalizedDirName();
			
		this.ioRawTracesDir = new File(bctDataDir,ConfigurationUtil.getDataRecordingDirName()+File.separator+ConfigurationUtil.getFileIoTraceRawDirName());
		this.interactionRawTracesDir = new File(bctDataDir,ConfigurationUtil.getDataRecordingDirName()+File.separator+ConfigurationUtil.getFileInteractionTraceRawDirName());
		
		this.interactionNormalizedTracesDir = new File(bctDataDir,ConfigurationUtil.getDataNormalizedDirName()+File.separator+ConfigurationUtil.getFileInteractionNormalizedDirName());
		
		this.ioNormalizedDeclsDir = new File(bctDataDir,ConfigurationUtil.getDataNormalizedDirName()+File.separator+ConfigurationUtil.getFileIoTraceNormalizedDeclsDirName());
		this.ioNormalizedDtracesDir = new File(bctDataDir,ConfigurationUtil.getDataNormalizedDirName()+File.separator+ConfigurationUtil.getFileIoTraceNormalizedDtraceDirName());
		
		this.ioModelsDir =  new File(bctDataDir,ConfigurationUtil.getModelsDirName()+File.separator+ConfigurationUtil.getFileIoModelsDirName());
		this.interactionModelsDir =  new File(bctDataDir,ConfigurationUtil.getModelsDirName()+File.separator+ConfigurationUtil.getFileInteractionModelsDirName());
		
		this.mapperFactoryFile = new MapperFactoryFile(this);
	}
	
	public ResourceFile(){
		
	}

	public FinderFactory getFinderFactory() {
		return mapperFactoryFile;
	}

	public void saveEntity(DomainEntity entity) throws MapperException {
		mapperFactoryFile.update( entity );
	}

	public File getIoRawTracesDir() {
		return ioRawTracesDir;
	}

	public File getInteractionRawTracesDir() {
		return interactionRawTracesDir;
	}
	
	public String getName() {
		
		return resourceName;
	}

	public File getInteractionNormalizedTracesDir() {
		return interactionNormalizedTracesDir;
	}
	

	public File getInteractionModelsDir() {
		return interactionModelsDir;
	}

	public File getIoModelsDir() {
		return ioModelsDir;
	}
	
		public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	public String getDataDir() {
		return dataDir;
	}
	
	public File getDataDirFile() {
		return new File(dataDir);
	}

	public File getIoNormalizedDeclsDir() {
		return ioNormalizedDeclsDir;
	}

	public File getIoNormalizedDtracesDir() {
		return ioNormalizedDtracesDir;
	}

	
	
	public File getDataRecordingDir() {
		return dataRecordingDir;
	}

	public String getIoRawTracesDirName() {
		return ioRawTracesDirName;
	}

	public String getInteractionRawTracesDirName() {
		return interactionRawTracesDirName;
	}

	
	public File getDataPreprocessingDir() {
		return dataPreprocessingDir;
	}	

	public String getIoNormalizedDtracesDirName() {
		return ioNormalizedDtracesDirName;
	}

	public String getInteractionNormalizedTracesDirName() {
		return interactionNormalizedTracesDirName;
	}

	public String getIoNormalizedDeclsDirName() {
		return ioNormalizedDeclsDirName;
	}


}
