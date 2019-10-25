/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
