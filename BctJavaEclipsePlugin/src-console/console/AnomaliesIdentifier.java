package console;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.BctDefaultOptionsException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.FileSystemDataManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.AnomalyDetection;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.ModelInference;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.operations.RegressionAnalysis;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.CleaningUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import modelsFetchers.ModelsFetcherException;
import modelsFetchers.ModelsFetcherFactoy;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;

import conf.InvariantGeneratorSettings;

import tools.InvariantGenerator;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;
import tools.violationsAnalyzer.ViolationsUtil;
import tools.violationsAnalyzer.ViolationsUtil.VariableData;
import tools.violationsAnalyzer.ViolationsUtil.ViolationData;
import util.FileUtil;
import util.PropertyUtil;


public class AnomaliesIdentifier {

	///PROPERTIES-DESCRIPTION: Options that control the formatting of the BCT/RADAR output



	///path to file wwith custom properties to use for Daikon
	private static final String BCT_INFERENCE_DAIKON_CONFIG_FILE = "bct.inference.daikon.config.file";

	///specifies the folder where to save BCT reports (csv,html,txt)
	private static final String BCT_OUTPUT_FOLDER = "bct.output.folder";

	///sets the number/id for the updated software
	public static final String BCT_RADAR_UPGRADE_VERSION = "bct.radar.updatedVersion";

	///sets the number/id for the base software
	public static final String BCT_RADAR_BASE_VERSION = "bct.radar.baseVersion";

	///sets the column separator for entries of file bct.anomalies.csv
	private static final String BCT_ANOMALIES_SEPARATOR = "bct.anomalies.entries.separator";
	
	private static final String STACK_ROW_END_TAG = "<!-- stack--row-end -->";
	private static final String STACK_ROW_TAG = "<!-- stack--row -->";



	private static final String BCT_UPDATED_SOFTWARE_VERSION = "BCT_UPDATED_SOFTWARE_VERSION";
	private static final String BCT_UPDATED_SOFTWARE_PATH = "BCT_UPDATED_SOFTWARE_PATH";
	private static final String BCT_BASE_SOFTWARE_PATH = "BCT_BASE_SOFTWARE_PATH";
	private static final String BCT_BASE_SOFTWARE_VERSION = "BCT_BASE_SOFTWARE_VERSION";

	
	
	private static String SEPARATOR = ";";

	private static boolean addTests = false;

	public static void setAddTests( boolean a ){
		addTests = a;
	}

	public static void setSeparator( String s ){
		SEPARATOR = s;
	}

	public static void main(String args[]) throws IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{
		AnomaliesIdentifier identifier = new AnomaliesIdentifier();
		run(args, identifier);
	}

	static {
		String separatorString = System.getProperty(BCT_ANOMALIES_SEPARATOR);
		if ( separatorString != null ){
			setSeparator(separatorString);
		}
	}
	
	{
		setSkipInference( checkSkipInference() );
		setSkipCheck( checkSkipIntendedCHangesIdentification() );
	}

	public static boolean checkSkipIntendedCHangesIdentification() {
		return Boolean.parseBoolean( System.getProperty(AnomaliesIdentifier.BCT_SKIP_CHECK) );
	}

	public static boolean checkSkipInference() {
		return Boolean.parseBoolean( System.getProperty(AnomaliesIdentifier.BCT_SKIP_INFERENCE) );
	}

	public static void run(String[] args, AnomaliesIdentifier identifier)
			throws IOException, ConfigurationFilesManagerException,
			CoreException, DefaultOptionsManagerException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InterruptedException,
			CBEBctViolationsLogLoaderException {

		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_UNDO_DAIKON_OPTIMIZATIONS, "false");
		PropertyUtil.setPropertyIfUndefined( InvariantGenerator.BCT_INFERENCE_EXPAND_EQUIVALENCES, "false" );

		try { 
			List<BctModelViolation> filteredAnomalies = identifier.identifyAnomalies(args[0]);

			ProjectSetup projectSetup = ProjectSetup.setupProject(args[0]);
			File mcFile = projectSetup.getMonitoringConfigurationFile();
			MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(mcFile);

			int exitValue = generateOutputFiles(mc,filteredAnomalies);

			System.exit( exitValue );

		} catch ( Throwable t ){
			System.out.println("Error caught identifying anomalies");
			t.printStackTrace();
		}




	}

	public static int generateOutputFiles(
			MonitoringConfiguration mc, List<BctModelViolation> filteredAnomalies) {

		String bctOutputFolderString = System.getProperty(BCT_OUTPUT_FOLDER);

		File bctOutputFolder;
		if ( bctOutputFolderString != null ){
			bctOutputFolder = new File ( bctOutputFolderString );
			bctOutputFolder.mkdirs();
		} else {
			bctOutputFolder = new File( "." );
		}


		File outFile = new File( bctOutputFolder, "bct.anomalies.txt");
		int exitValue = processResults(filteredAnomalies, outFile);

		File fCsv = new File( bctOutputFolder, "bct.anomalies.csv");
		AnomaliesIdentifier.processResultsCsv(filteredAnomalies,fCsv);

		File fhtml = new File( bctOutputFolder, "bct.anomalies.html");
		AnomaliesIdentifier.processResultsHtml(mc,filteredAnomalies,fhtml);

		return exitValue;
	}

	private static void processResultsHtml(
			MonitoringConfiguration mc, List<BctModelViolation> filteredAnomalies, File fhtml) {

		String baseVersion = System.getProperty(BCT_RADAR_BASE_VERSION);
		if ( baseVersion == null ){
			baseVersion = "";
		}

		String upgradeVersion = System.getProperty(BCT_RADAR_UPGRADE_VERSION);
		if ( upgradeVersion == null ){
			upgradeVersion = "";
		}

		String baseSources = "";
		String updatedSources = "";

		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			CConfiguration conf = (CConfiguration) mc.getAdditionalConfigurations().get(CConfiguration.class);

			baseSources = conf.getOriginalSwSourcesFolder();
			updatedSources = baseSources;

		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			CRegressionConfiguration conf = (CRegressionConfiguration) mc.getAdditionalConfigurations().get(CRegressionConfiguration.class);

			baseSources = conf.getOriginalSwSourcesFolder();
			updatedSources = conf.getModifiedSwSourcesFolder();
		}

		InputStream s = AnomaliesIdentifier.class.getClassLoader().getResourceAsStream("TEMPLATE.bct.anomalies.html");

		BufferedReader reader = new BufferedReader(new InputStreamReader(s));
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fhtml));

			boolean anomalyRow = false;
			boolean stackStart = false;

			String line;

			StringBuffer anomalyRowBuffer = new StringBuffer();
			try {
				while ( ( line = reader.readLine() ) != null ){

					if ( line.contains(BCT_BASE_SOFTWARE_VERSION) ){
						line = line.replace(BCT_BASE_SOFTWARE_VERSION, baseVersion );
					}

					if ( line.contains(BCT_BASE_SOFTWARE_PATH) ){
						line = line.replace(BCT_BASE_SOFTWARE_PATH, baseSources );
					}

					if ( line.contains(BCT_UPDATED_SOFTWARE_VERSION) ){
						line = line.replace(BCT_UPDATED_SOFTWARE_VERSION, upgradeVersion );
					}

					if ( line.contains(BCT_UPDATED_SOFTWARE_PATH) ){
						line = line.replace(BCT_UPDATED_SOFTWARE_PATH, updatedSources );
					}

					if ( line.equals("//STACK CONTENTS ARRAY START") ){
						stackStart = true;
						continue;
					}

					if ( line.equals("//STACK CONTENTS ARRAY END") ){
						stackStart = false;
						continue;
					}

					if ( stackStart ){
						String stackLine = line;
						addStackForAnomalies( stackLine, filteredAnomalies, writer );
						continue;
					}

					if ( line.equals("<!--anomalies-row-start-->") ){
						anomalyRow = true;
					}
					if ( line.equals("<!--anomalies-row-end-->") ){
						anomalyRow = false;

						String anomalyRowString = anomalyRowBuffer.toString();

						addAnomaliesToHtml( anomalyRowString, filteredAnomalies, writer );
					}

					if ( anomalyRow ){
						anomalyRowBuffer.append(line);
					} else {
						writer.write(line);
						writer.newLine();
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				writer.close();
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void addStackForAnomalies(String stackLine,
			List<BctModelViolation> filteredAnomalies, BufferedWriter writer) throws IOException {

		int stackPrefixEnd = stackLine.indexOf(STACK_ROW_TAG);
		int stackRowStart = stackPrefixEnd + STACK_ROW_TAG.length();

		int stackRowEnd = stackLine.indexOf(STACK_ROW_END_TAG);
		int stackSuffixStart = stackRowEnd+STACK_ROW_END_TAG.length();

		String stackPrefix = stackLine.substring(0, stackPrefixEnd);
		String stackEntry = stackLine.substring(stackRowStart, stackRowEnd);
		String stackSuffix = stackLine.substring(stackSuffixStart);

		int i = 0;
		for ( BctModelViolation anomaly : filteredAnomalies ){
			String anomalyId = String.valueOf(++i); 

			String stack = stackPrefix.replace("ANOMALY_ID", anomalyId);

			for ( String t : anomaly.getStackTrace() ){
				int pos = t.lastIndexOf(':');
				String file = t.substring(0, pos);
				String line = t.substring( pos+1 );

				stack += stackEntry.replace("STACK_LINE", line).replace("STACK_FILE", file );
			}

			stack += stackSuffix;

			writer.write(stack);
			writer.newLine();
		}

	}

	private static void addAnomaliesToHtml(String anomalyRowString,
			List<BctModelViolation> filteredAnomalies, BufferedWriter writer) throws IOException {

		int i = 0;
		for ( BctModelViolation anomaly : filteredAnomalies ){
			String anomalyId = String.valueOf(++i); 


			String anomalyPP = anomaly.getViolatedModel();

			String anomalyExpected = "";
			String anomalyObserved = "";

			if ( anomaly instanceof BctIOModelViolation ){
				BctIOModelViolation ioAnomaly = (BctIOModelViolation) anomaly;
				anomalyExpected = anomaly.getViolation();	
				for ( VariableData  vd :  ViolationsUtil.extractViolatedVariables(anomalyExpected, ioAnomaly.getParametersMap() ) ){
					anomalyObserved += vd.getVariableName()+" : "+vd.getActualValue()+"<br/>";
				}
			} else if ( anomaly instanceof BctFSAModelViolation ){
				BctFSAModelViolation fsaAnomaly = (BctFSAModelViolation) anomaly;
				anomalyObserved = fsaAnomaly.getViolation();
				anomalyExpected = retrieveExpectedTransitionNames(anomalyPP, anomalyExpected, fsaAnomaly);
			}

			String firstStack = anomaly.getStackTrace()[0];
			int colons = firstStack.indexOf(":");

			CharSequence anomalyFile = firstStack.substring(0,colons);
			CharSequence anomalyLine = firstStack.substring(colons+1);

			String row = anomalyRowString.replace("ANOMALY_ID", anomalyId)
					.replace("ANOMALY_PP", anomalyPP)
					.replace("ANOMALY_FILE", anomalyFile)
					.replace("ANOMALY_LINE", anomalyLine)
					.replace("ANOMALY_EXPECTED", anomalyExpected)
					.replace("ANOMALY_OBSERVED", anomalyObserved);

			writer.write(row);
		}
	}

	public static String retrieveExpectedTransitionNames(String anomalyPP,
			String anomalyExpected, BctFSAModelViolation fsaAnomaly) {
		for ( String currentState : fsaAnomaly.getViolationStatesNames() ){
			try {
				FiniteStateAutomaton fsa = ModelsFetcherFactoy.modelsFetcherInstance.getInteractionModel(anomalyPP);
				for ( State s : fsa.getStates() ){
					if ( currentState.equals(s.getLabel()) ){
						for ( Transition t : fsa.getTransitionsFromState(s) ){
							anomalyExpected+= t.getDescription()+"<br/>";	
						}
					}
				}
			} catch (ModelsFetcherException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return anomalyExpected;
	}

	public static int processResults(List<BctModelViolation> filteredAnomalies) {
		return processResults(filteredAnomalies, null);
	}

	public static int processResults(List<BctModelViolation> filteredAnomalies, File outFile) {
		BufferedWriter w = null;
		try {
			if ( outFile != null ){
				w = new BufferedWriter(new FileWriter( outFile ) );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> out = new ArrayList<String>();
		int res = __processResults(filteredAnomalies, out);
		for ( String s : out ){
			System.out.println(s);
			if ( w != null ){
				try {
					w.write(s);
					w.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		if ( w != null ){
			try {
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return res;
	}



	public static int processResultsCsv(List<BctModelViolation> filteredAnomalies, File outFile) {
		BufferedWriter w = null;
		try {
			if ( outFile != null ){
				w = new BufferedWriter(new FileWriter( outFile ) );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> out = new ArrayList<String>();
		int res = __processResultsCsv(filteredAnomalies, out);
		for ( String s : out ){
			//			System.out.println(s);
			if ( w != null ){
				try {
					w.write(s);
					w.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		if ( w != null ){
			try {
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return res;
	}


	private static int __processResultsCsv(
			List<BctModelViolation> filteredAnomalies, ArrayList<String> outputs) {
		if ( filteredAnomalies.size() > 0 ){

			HashMap<String,Integer> variablePositions = new HashMap<String, Integer>();

			for ( BctModelViolation violation : filteredAnomalies){


				if ( violation instanceof BctIOModelViolation ){
					ViolationData vd = ViolationsUtil.getViolationData((BctIOModelViolation) violation);
					for ( VariableData varData : vd.getViolatedVariables() ){
						String varName = varData.getVariableName();
						Integer pos = variablePositions.get(varName);
						if ( pos == null ){
							variablePositions.put(varName, variablePositions.size());
						}
					}
				} 
			}


			String header = "FunctionName"+SEPARATOR+"Anomaly"+SEPARATOR+"ViolationType"+SEPARATOR+"ViolatedModel"+SEPARATOR+"Enter/Exit"+SEPARATOR+"StackTraceLength"+SEPARATOR+"CurrentState"+getVariablesHeader( variablePositions );

			if ( addTests ){
				header = "Test"+SEPARATOR+"TestLine"+SEPARATOR+header;
			}

			outputs.add(header);

			for ( BctModelViolation violation : filteredAnomalies){
				violation.getCurrentTests();
				String functionName = DisplayNamesUtil.getDemangledProgramPoint(violation.getViolatedModel());

				String anomaly = violation.getViolation();
				if ( violation.getViolationType().equals(BctModelViolation.ViolationType.UNEXPECTED_EVENT) ){
					anomaly = DisplayNamesUtil.getDemangledProgramPoint(anomaly.trim());
				}

				int idx = functionName.indexOf(":::");
				if ( idx > 0 ){
					functionName = functionName.substring(0,idx);
				}

				String line = null;



				if ( violation instanceof BctIOModelViolation ){

					ViolationData vd = ViolationsUtil.getViolationData((BctIOModelViolation) violation);
					
					String locationType;
					if ( violation.getViolatedModel().endsWith("ENTER") ){
						locationType = "ENTER";
					} else {
						locationType = "EXIT";
					}
					
					line = functionName +SEPARATOR+anomaly+SEPARATOR+violation.getViolationType()+SEPARATOR+violation.getViolatedModel()+SEPARATOR+locationType+SEPARATOR+violation.getStackTrace().length+SEPARATOR+getVariablesCsv( variablePositions, vd );



				} else if ( violation instanceof BctFSAModelViolation ){

					ViolationData vd = ViolationsUtil.getViolationData((BctFSAModelViolation) violation);
					
					String locationType = "ENTER";
					
					line = functionName +SEPARATOR+anomaly+SEPARATOR+violation.getViolationType()+SEPARATOR+violation.getViolatedModel()+SEPARATOR+locationType+SEPARATOR+violation.getStackTrace().length+SEPARATOR+vd.getViolationStatesNames()[0];

				}

				if ( addTests ){
					String[] tests = violation.getCurrentTests();
					Set<String>testsSet = new HashSet<String>();
					testsSet.addAll(Arrays.asList(tests));
					for ( String test :  testsSet ){
						outputs.add( test+SEPARATOR+violation.getStackTrace()[1]+SEPARATOR+line );
					}
				} else {
					outputs.add(line);
				}
			}

			return 1;
		}
		return 0;
	}


	private static String getVariablesHeader( HashMap<String, Integer> variablePositions) {
		int max = variablePositions.size();

		HashMap<Integer, String> support = new HashMap<Integer, String>();
		for ( Entry<String, Integer> e : variablePositions.entrySet() ){
			support.put(e.getValue(),e.getKey());
		}

		StringBuffer res = new StringBuffer();
		for ( int i = 0; i < max ; i++ ){
			res.append(SEPARATOR);
			res.append(support.get(i));
		}

		return res.toString();
	}


	private static String getVariablesCsv( HashMap<String, Integer> variablePositions, ViolationData vd) {

		String pos[]= new String[variablePositions.size()];

		for ( VariableData varData : vd.getViolatedVariables() ){
			Integer n = variablePositions.get(varData.getVariableName());
			if ( n == null ){
				continue;
			}
			pos[n] = String.valueOf(varData.getActualValue());
		}

		StringBuffer sb = new StringBuffer();
		for ( String value : pos ){
			sb.append(SEPARATOR);
			if ( value != null ){
				sb.append(value);
			}
		}

		return sb.toString();
	}


	public static int __processResults(List<BctModelViolation> filteredAnomalies,ArrayList<String> outputs) {
		if ( filteredAnomalies.size() > 0 ){
			outputs.add("\n\n\n\n!!!!!!!! Violations Found");

			for ( BctModelViolation violation : filteredAnomalies){

				String functionName = DisplayNamesUtil.getDemangledProgramPoint(violation.getViolatedModel());

				outputs.add("Monitored function: "+functionName +"\t["+violation.getViolatedModel()+"]");


				String anomaly = violation.getViolation();
				if ( violation.getViolationType().equals(BctModelViolation.ViolationType.UNEXPECTED_EVENT) ){
					anomaly = DisplayNamesUtil.getDemangledProgramPoint(anomaly.trim());
				}
				outputs.add("Anomaly: "+ anomaly + "\t\t("+violation.getViolationType()+")");

				if ( violation instanceof BctIOModelViolation ){
					ViolationData vd = ViolationsUtil.getViolationData((BctIOModelViolation) violation);

					outputs.add("\tActual values: ");
					for ( VariableData varData : vd.getViolatedVariables() ){
						outputs.add("\t\t"+varData.getVariableName()+" "+varData.getActualValue());
					}


				} else if ( violation instanceof BctFSAModelViolation ){
					ViolationData vd = ViolationsUtil.getViolationData((BctFSAModelViolation) violation);

					outputs.add("\tCurrent state(s): ");
					for( String state : vd.getViolationStatesNames() ){
						outputs.add("\t"+state);
					}
					outputs.add("");
				}

				outputs.add("Stack trace:");
				for( String stack : violation.getStackTrace() ){
					if ( stack.startsWith(".") ){
						stack = stack.substring(1);
					}
					outputs.add("\t"+DisplayNamesUtil.getDemangledProgramPoint(stack));
				}
			}

			return 1;
		}
		return 0;
	}

	protected boolean processFailingTracesOnly;
	private boolean skipInference;
	private boolean skipCheck;

	///if true skips the inference of models (default false)
	public static final String BCT_SKIP_INFERENCE = "bct.skipInference";

	///if true skips the identification of anomalous data values (default false), i.e. intended changes
	public static final String BCT_SKIP_CHECK = "bct.skipCheck";
	
	public boolean isSkipInference() {
		return skipInference;
	}


	public void setSkipInference(boolean skipInference) {
		this.skipInference = skipInference;
	}


	public boolean isSkipCheck() {
		return skipCheck;
	}


	public void setSkipCheck(boolean skipCheck) {
		this.skipCheck = skipCheck;
	}


	public List<BctModelViolation> identifyAnomalies(String projectDir) throws IOException, ConfigurationFilesManagerException, CoreException, DefaultOptionsManagerException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InterruptedException, CBEBctViolationsLogLoaderException{

		ProjectSetup projectSetup = ProjectSetup.setupProject(projectDir);







		File mcFile = projectSetup.getMonitoringConfigurationFile();

		MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(mcFile);
		setupDefaultOptions(mcFile, mc);

		return identifyAnomalies(mc);
	}

	public List<BctModelViolation> identifyAnomalies(MonitoringConfiguration mc)
			throws ConfigurationFilesManagerException, CoreException,
			DefaultOptionsManagerException, InvocationTargetException,
			InterruptedException, IOException,
			CBEBctViolationsLogLoaderException {

		if ( ! skipInference ){
			CleaningUtil.deleteInferenceAndAnalysisData(ConfigurationFilesManager.getBctHomeDir(mc));
			ModelInference.inferModels(mc, new NullProgressMonitor(), System.out, System.err);
		}

		if ( skipCheck ){
			return new ArrayList<BctModelViolation>();
		}


		System.out.println("Identifying anomalies");
		AnomalyDetection.identifyAnomalies(mc);

		List<BctModelViolation> filteredAnomalies = RegressionAnalysis.identifyRegressions(mc, processFailingTracesOnly).getViolationAnalysisResult().getFilteredViolations();

		return filteredAnomalies;
	}

	//	public static void inferModels(File mcFile, MonitoringConfiguration mc)
	//			throws CoreException, DefaultOptionsManagerException,
	//			InvocationTargetException, InterruptedException,
	//			ConfigurationFilesManagerException, IOException {
	//
	//		
	//
	//		ModelInference.inferModels(mc, new NullProgressMonitor(), System.out, System.err);
	//	}


	public static void setupDefaultOptions(File mcFile,
			MonitoringConfiguration mc) throws CoreException,
			DefaultOptionsManagerException, ConfigurationFilesManagerException,
			FileNotFoundException {
		BctDefaultOptions defaultOptions = DefaultOptionsManager.getDefaultOptions();



		Properties opts = defaultOptions.getInvariantGeneratorOptions();


		String customConfigFile = System.getProperty(BCT_INFERENCE_DAIKON_CONFIG_FILE);
		if ( customConfigFile != null ){
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(new File(customConfigFile)));
				defaultOptions.addDaikonConfigProperties("custom", p );
				//				System.setProperty(DefaultOptionsManager.BCT_INFERENCE_DAIKON_CONFIG_DEFAULT, "custom");
				opts.setProperty(InvariantGeneratorSettings.Options.daikonConfig, "custom");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BctDefaultOptionsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		mc.setInvariantGeneratorOptions(opts);
		mc.setFsaEngineOptions(defaultOptions.getKbehaviorInferenceEngineOptions());



		MonitoringConfigurationSerializer.serialize(mcFile, mc);
	}
}
