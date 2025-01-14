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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file");
		//$JUnit-BEGIN$
		suite.addTestSuite(InteractionRawTraceMapperZipFileTest.class);
		suite.addTestSuite(ModelsMapperFileTest.class);
		suite.addTestSuite(InteractionNormalizedTraceMapperZipFileTest.class);
		suite.addTestSuite(InteractionNormalizedTraceMapperFileTest.class);
		suite.addTestSuite(IoNormalizedTraceMapperFileTest.class);
		suite.addTestSuite(InteractionRawTraceMapperFileTest.class);
		suite.addTestSuite(IoRawTraceMapperFileTest.class);
		suite.addTestSuite(IoNormalizedTraceMapperZipFileTest.class);
		suite.addTestSuite(IoRawTraceMapperZipFileTest.class);
		//$JUnit-END$
		return suite;
	}

}
