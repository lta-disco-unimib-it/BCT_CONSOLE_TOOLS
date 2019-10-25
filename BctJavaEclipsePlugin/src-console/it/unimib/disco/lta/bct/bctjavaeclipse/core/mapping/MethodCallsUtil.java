package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping;

import traceReaders.raw.FileTracesReader;

public class MethodCallsUtil {

	public static String getMethodCallId(String sessionId, String threadId,
			int tokenId) {
		return FileTracesReader.getMethodCallId(sessionId, threadId, tokenId);
	}

	public static String getSessionId(String methodCallId) {
		return FileTracesReader.getSessionId(methodCallId);
	}

	public static String getThreadId(String methodCallId) {
		return FileTracesReader.getThreadId(methodCallId);
	}

	public static Long getMethodCallNumber(String methodCallId) {
		return FileTracesReader.getMethodCallNumber(methodCallId);
	}
}
