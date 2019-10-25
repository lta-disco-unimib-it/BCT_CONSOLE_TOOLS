package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

public class ConfigurationUtil {

	private static final String fileIoTraceRawDirName = "ioInvariantLogs";
	private static final String fileInteractionTraceRawDirName = "interactionInvariantLogs";
	private static final String dataRecordingDirName = "DataRecording";
	private static final String dataNormalizedDirName = "Preprocessing";
	private static final String modelsDirName = "Models";
	private static final String fileIoTraceNormalizedDtraceDirName = "dtrace";
	private static final String fileIoTraceNormalizedDeclsDirName = "decls";
	private static final String fileIoModelsDirName = "ioInvariants";
	private static final String fileInteractionModelsDirName = "interactionInvariants";
	private static final String fileInteractionNormalizedDirName = "interaction";
	
	public static String getFileIoTraceRawDirName() {
		return fileIoTraceRawDirName;
	}

	public static String getFileInteractionTraceRawDirName() {
		return fileInteractionTraceRawDirName;
	}

	public static String getDataRecordingDirName() {
		return dataRecordingDirName;
	}

	public static String getDataNormalizedDirName() {
		return dataNormalizedDirName ;
	}

	public static String getModelsDirName() {
		return modelsDirName ;
	}

	public static String getFileIoTraceNormalizedDtraceDirName() {
		return fileIoTraceNormalizedDtraceDirName;
	}

	public static String getFileIoTraceNormalizedDeclsDirName() {
		return fileIoTraceNormalizedDeclsDirName;
	}

	public static String getFileIoModelsDirName() {
		return fileIoModelsDirName;
	}

	public static String getFileInteractionModelsDirName() {
		return fileInteractionModelsDirName;
	}

	public static String getFileInteractionNormalizedDirName() {
		return fileInteractionNormalizedDirName ;
	}
}
