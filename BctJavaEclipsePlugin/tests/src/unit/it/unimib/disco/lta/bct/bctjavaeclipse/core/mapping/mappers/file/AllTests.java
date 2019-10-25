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
