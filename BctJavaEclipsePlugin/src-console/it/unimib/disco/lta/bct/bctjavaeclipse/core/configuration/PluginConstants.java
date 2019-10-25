package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public class PluginConstants {
	public static final String BCT_MONITORING_CONFIGURATION_FILE_EXTENSION = "bctmc";
	public static final String URI_PATH_SEPARATOR = "/";
	
	// Constants used in BCT Project Explorer
	public static final String COMPONENTS = "Components";
	public static final String DATA_RECORDED = "Recorded Data";
	public static final String DATA_RECORDED_RAW = "Raw " + DATA_RECORDED;
	public static final String DATA_RECORDED_NORMALIZED = "Normalized " + DATA_RECORDED ;
	public static final String MODELS = "Models";
	public static final String ANOMALIES = "Anomalies";
	public static final String ANOMALIES_ANALISYS = ANOMALIES + " Analysis";
	
	public static final String IO_ = "Data properties";
	public static final String IO_TRACE = IO_ + " Traces";
	public static final String INTERACTION_TRACE = "Interaction Traces";
	
	public static final String FSA = "FSA";
	public static final String IO = "Data properties";
	public static final String IO_INTERACTION_TRACE = "EFSA";
	
	public static final String ENVIRONMENT_COMPONENT = "Environment";
	
	// Editors IDs
	public static final String IO_MODELS_EDITOR_ID = "bctjavaeclipse.editors.IOModelsEditor";
	public static final String FSA_MODELS_EDITOR_ID = "fsa.diagram.part.FsaDiagramEditorID";
	public static final String IO_TRACES_EDITOR_ID = "bctjavaeclipse.editors.IOTracesEditor";
	public static final String INTERACTION_NORMALIZED_TRACES_EDITOR_ID = "bctjavaeclipse.editors.InteractionNormalizedTracesEditor";
	public static final String INTERACTION_RAW_TRACES_EDITOR_ID = "bctjavaeclipse.editors.InteractionRawTracesEditor";
	
	public static final String IO_MODELS_EDITOR_INDEX_ID = "bctjavaeclipse.editors.IOModelsIndexEditor";
	public static final String FSA_MODELS_EDITOR_INDEX_ID = "bctjavaeclipse.editors.InteractionModelsIndexEditor";
	public static final String IO_TRACES_EDITOR_INDEX_ID = "bctjavaeclipse.editors.IOTracesIndexEditor";
	public static final String INTERACTION_NORMALIZED_TRACES_EDITOR_INDEX_ID = "bctjavaeclipse.editors.InteractionNormalizedTracesIndexEditor";
	public static final String INTERACTION_RAW_TRACES_EDITOR_INDEX_ID = "bctjavaeclipse.editors.InteractionRawTracesIndexEditor";
	
	// URI constants
	public static final String RESOURCE_IDENTIFIER = "id";
	public static final String IO_MODELS_PATH = URI_PATH_SEPARATOR + ConfigurationUtil.getModelsDirName() + URI_PATH_SEPARATOR + ConfigurationUtil.getFileIoModelsDirName();
	public static final String INTERACTION_MODELS_PATH = URI_PATH_SEPARATOR + ConfigurationUtil.getModelsDirName() +URI_PATH_SEPARATOR + ConfigurationUtil.getFileInteractionModelsDirName();
	public static final String IO_RAW_TRACES_PATH = URI_PATH_SEPARATOR + ConfigurationUtil.getDataRecordingDirName() + URI_PATH_SEPARATOR + ConfigurationUtil.getFileIoTraceRawDirName();
	public static final String IO_NORMALIZED_TRACES_PATH = URI_PATH_SEPARATOR + ConfigurationUtil.getDataNormalizedDirName() + URI_PATH_SEPARATOR + ConfigurationUtil.getFileIoTraceNormalizedDtraceDirName();
	public static final String INTERACTION_RAW_TRACES_PATH = URI_PATH_SEPARATOR + ConfigurationUtil.getDataRecordingDirName() + URI_PATH_SEPARATOR + ConfigurationUtil.getFileInteractionTraceRawDirName();
	public static final String INTERACTION_NORMALIZED_TRACES_PATH = URI_PATH_SEPARATOR + ConfigurationUtil.getDataNormalizedDirName() + URI_PATH_SEPARATOR + ConfigurationUtil.getFileInteractionNormalizedDirName();
	public static final String PID = "pid";
	public static final String THREADID = "threadId";
	public static final String CALLID = "callId";
	public static final String CALL_POINT_TYPE = "callPointType";
}
