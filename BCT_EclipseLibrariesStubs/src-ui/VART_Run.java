import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CBCMRegressionsDetector;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.eclipse.vart.core.VARTRunnableWithProgress;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;

import org.eclipse.core.runtime.CoreException;

import console.AnomaliesIdentifier;
import console.ProjectSetup;
import console.RegressionsDetector;

import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;

//Previously named as RunUnitChecking

public class VART_Run {

	public static class ExportModelsAndInjectAssertionsOriginal {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_CBMC_EXECUTION,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"false");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"true");
			
			
			
			VART_Run.main(args);
		}
	}
	
	public static class InjectAssertionsOriginal {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_CBMC_EXECUTION,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_CREATE_ONLY_ORIGINAL_WITH_ASSERTIONS,"true");
			
			
			
			VART_Run.main(args);
		}
	}
	
	public static class InjectAssertionsModified {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_CBMC_EXECUTION,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"true");
			//System.setProperty(CBCMRegressionsDetector.BCT_CREATE_ONLY_ORIGINAL_WITH_ASSERTIONS,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_PROCESSING_OF_ORIGINAL,"true");
			
			
			
			VART_Run.main(args);
		}
	}
	
	public static class InferOnly {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_CBMC,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			VART_Run.main(args);
		}
	}
	
	public static class AnomalyDetectionOnly {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_CBMC,"true");
			VART_Run.main(args);
		}
	}
	
	public static class ExportModels {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {

			String projectDir = args[0];
			ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
			
			CBCMRegressionsDetector detector = new CBCMRegressionsDetector(new VARTRegressionConfiguration(), ModelsFetcherFactoy.modelsFetcherInstance);
			MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
			detector.initialize(mrc);
			detector.exportModels();
		}
	}
	
	public static class IdentifyNonRegressionProperties {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {

			String projectDir = args[0];
			ProjectSetup projectVars = ProjectSetup.setupProject(projectDir);
			
			CBCMRegressionsDetector detector = new CBCMRegressionsDetector(new VARTRegressionConfiguration(), ModelsFetcherFactoy.modelsFetcherInstance);
			MonitoringConfiguration mrc = MonitoringConfigurationDeserializer.deserialize(projectVars.getMonitoringConfigurationFile());
			detector.initialize(mrc);
			detector.filterTruePropertiesAndIntendedAnomalies(detector.identifyIntendedAnomalies());
//			VART_Run.main(args);
		}
	}
	
	public static class InferAndCheck {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_CBMC_EXECUTION,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_CREATE_ONLY_ORIGINAL_WITH_ASSERTIONS,"true");
			VART_Run.main(args);
		}
	}

	public static class RunCBMCOriginal {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"false");
//			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_ASSERTIONS_INJECTION,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_PROCESSING_OF_MODIFIED,"true");
			
			VART_Run.main(args);
		}
	}
	
	public static class ImportCBMCOriginal {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"false");
			System.setProperty(CBCMRegressionsDetector.BCT_CBMC_OUTPUT_ORIGINAL,args[0]);
			
			
			VART_Run.main(Arrays.copyOfRange(args, 1, args.length));
		}
	}
	
	public static class RunCBMCModified {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_PROCESSING_OF_ORIGINAL,"true");
//			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_ASSERTIONS_INJECTION,"true");
			
	
			VART_Run.main(args);
		}
	}
	
	public static class ImportCBMCModified {
		public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_EXPORT_MODELS,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE,"true");
			System.setProperty(AnomaliesIdentifier.BCT_SKIP_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_VALID_ANOMALIES_CHECK,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_SKIP_PROCESSING_OF_ORIGINAL,"true");
			System.setProperty(CBCMRegressionsDetector.BCT_CBMC_OUTPUT_MODIFIED,args[0]);
			System.setProperty(VARTRunnableWithProgress.BCT_SKIP_MONITORING,"true");
	
			VART_Run.main(Arrays.copyOfRange(args, 1, args.length));
		}
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws ModelsFetcherException 
	 * @throws CBEBctViolationsLogLoaderException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws DefaultOptionsManagerException 
	 * @throws CoreException 
	 * @throws ConfigurationFilesManagerException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException, ModelsFetcherException, ClassNotFoundException {
		console.RunUnitChecking.main(args);
	}

}
