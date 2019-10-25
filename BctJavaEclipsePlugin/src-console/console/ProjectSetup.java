package console;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.core.runtime.Path;

import conf.EnvironmentalSetter;
import cpp.gdb.CSourceAnalyzer;
import cpp.gdb.CSourceAnalyzerRegistry;
import cpp.gdb.EnvUtil;


public class ProjectSetup {
	private static final String BCT_CONSOLE_TOOLS_JAR = "bct-console-tools.jar";
	private String dataDirPath;
	private File projectFolder;
	private File dataDir;
	private String projectName;
	private Path monitoringConfigurationPath;
	private File monitoringConfigurationFile;

	public Path getMonitoringConfigurationPath() {
		return monitoringConfigurationPath;
	}
	
	public File getMonitoringConfigurationFile() {
		return monitoringConfigurationFile;
	}

	public ProjectSetup(String projectName, File projectFolder, String dataDirPath) {
		this.projectFolder = projectFolder;
		this.dataDirPath = dataDirPath;
		this.projectName = projectName;
		String mcFileName = projectName+".bctmc";
		this.dataDir = new File( dataDirPath );
		this.monitoringConfigurationFile = new File ( projectFolder, mcFileName );
		this.monitoringConfigurationPath = new Path("/"+projectName+"/"+mcFileName);
	}

	public File getProjectFolder() {
		return projectFolder;
	}

//	public File getDataDir() {
//		return dataDir;
//	}

	public String getProjectName() {
		return projectName;
	}

	
	public static ProjectSetup setupProject(String projectDir){
		
		BctJavaEclipsePlugin plugin = new BctJavaEclipsePlugin();//required for logging
		
		setupLogger();
		
		projectDir += File.separator + "BCT";
		
		File projectFolder = new File( projectDir);
		projectFolder.mkdirs();
		
		if ( ! projectFolder.exists() ){
			throw new IllegalStateException("Cannot create folder :"+projectFolder.getAbsolutePath());
		}
		
		File defaultData = new File( projectFolder.getParentFile(), "BCT_DATA");
		defaultData.mkdir();
		
		String projectName = "BCT";
		String dataDirPath = "/" + projectName+"/"+"BCT_DATA"+"/"+projectName;
		String dataDirAbsolutePath = EnvUtil.getOSAbsolutePath( projectFolder.getParentFile().getAbsolutePath() + dataDirPath );
		File dataDir  = new File( dataDirAbsolutePath );
		
		WorkspaceUtil.setWorkspaceRoot(projectFolder.getParentFile());
		EnvironmentalSetter.setBctHome(dataDir.getAbsolutePath());
		
		try {
			Class<?> clazz = Class.forName("it.unimib.disco.lta.bct.bctjavaeclipsecpp.core.util.CDTStandaloneCFileAnalyzer");
			CSourceAnalyzer analyzer = (CSourceAnalyzer) clazz.newInstance();
			CSourceAnalyzerRegistry.setCSourceAnalyzer( analyzer );

		} catch ( ClassNotFoundException e ){
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setupBctJar();
		
		return new ProjectSetup( projectName, projectFolder, dataDirPath );
	}
	
	private static void setupBctJar(){
		File jarFile = null;
		try {
			jarFile = new File( ProjectSetup.class.getProtectionDomain()
					.getCodeSource().getLocation().toURI());

		} catch (URISyntaxException e) { }

		if ( jarFile != null && jarFile.getName().endsWith(".jar") ){
			DefaultOptionsManager.setDaikonJarFile(jarFile);
			DefaultOptionsManager.setBctJarFile(jarFile);
		} else {
			DefaultOptionsManager.setDaikonJarFile(new File( "../BctEclipseCore/lib/daikon.jar" ));
			DefaultOptionsManager.setBctJarFile(new File( "dist/"+BCT_CONSOLE_TOOLS_JAR ));
		}
	}

	private static void setupLogger() {
		Logger logger = Logger.getLogger("");
	    logger.setLevel(Level.ALL);
	    FileHandler fileTxt;
		try {
			fileTxt = new FileHandler(System.getProperty("java.io.tmpdir")+"/bct.log");
		    SimpleFormatter formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ;
	}

	public String getDataDirPath() {
		return dataDirPath;
	}
}
