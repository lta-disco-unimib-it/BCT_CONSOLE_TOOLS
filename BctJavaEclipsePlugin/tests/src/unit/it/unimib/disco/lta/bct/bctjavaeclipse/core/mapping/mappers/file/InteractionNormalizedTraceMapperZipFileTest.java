package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.MethodCallSequence;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.test.TestUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class InteractionNormalizedTraceMapperZipFileTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFindAllTracesOneTrace() throws MapperException, LoaderException {
		String curTest = "T0";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA_ZIPPED").getAbsolutePath(),"testConfig");
		InteractionNormalizedTraceFinder finder = resource.getFinderFactory().getInteractionNormalizedTraceHandler();
		
		Collection<InteractionNormalizedTrace> traces = finder.findAllTraces();
		
		assertEquals(1, traces.size());
		
		Iterator<InteractionNormalizedTrace> tIt = traces.iterator();
		
		InteractionNormalizedTrace trace = tIt.next();
		
		checkTrace(curTest , "testPackage.Test0.m(int)", trace);
	}

	private String getStringSequence(MethodCallSequence sequence) {
		Iterator<MethodCall> sIt = sequence.iterator();
		StringBuffer result = new StringBuffer();
		
		while ( sIt.hasNext() ){
			MethodCall methodCall = sIt.next();
			String methodName = methodCall.getMethod().getSignature();
			result.append(methodName);
		}
		
		return result.toString();
	}

	public void testFindAllTracesTwoTraces() throws MapperException, LoaderException {
		String curTest = "T1";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA_ZIPPED").getAbsolutePath(),"testConfig");
		InteractionNormalizedTraceFinder finder = resource.getFinderFactory().getInteractionNormalizedTraceHandler();
		
		Collection<InteractionNormalizedTrace> traces = finder.findAllTraces();
		
		Iterator<InteractionNormalizedTrace> tIt = traces.iterator();
		
		InteractionNormalizedTrace  trace;
		//
		// trace for method testPackage.Test1.m(int)
		//
		
		trace = tIt.next();
		
		checkTrace(curTest , "testPackage.Test1.m(int)", trace);
		
		//
		// trace for method testPackage.Test0.changeAge(int,User)
		// 
		
		trace = tIt.next();
		
		checkTrace(curTest , "testPackage.Test0.changeAge(int,User)", trace);
		
		
		
		
	}
	
	private void checkTrace( String testName, String methodName, InteractionNormalizedTrace trace ) throws LoaderException{
		List<MethodCallSequence> callSequences = trace.getCallSequences();
		String[] expectedSequences = TestUtil.getNormalizedCallSequences(testName, methodName );
		
		assertNotNull(callSequences);
		assertNotNull(expectedSequences);
		
		assertEquals(trace.getMethod().getSignature(), methodName);
		
		assertEquals(expectedSequences.length, callSequences.size());
		
		for ( int i = 0; i < expectedSequences.length; ++i ){
			System.err.println("Iter "+i);
			assertEquals(expectedSequences[i], getStringSequence(callSequences.get(i)) );
		}
		
		
		
	}
	
	


	public void testFindTraceMethodSingleMethodTrace() throws Exception {
		String curTest = "T0";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA_ZIPPED").getAbsolutePath(),"testConfig");
		InteractionNormalizedTraceFinder finder = resource.getFinderFactory().getInteractionNormalizedTraceHandler();

		String methodName;
		Method method;
		
		
		methodName = "testPackage.Test0.m(int)";
		method = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		
		InteractionNormalizedTrace trace = finder.findTrace(method);
		checkTrace(curTest, methodName, trace);
		
	}
	
	
	public void testFindTraceMethodMultipleMethodTraces() throws Exception {
		String curTest = "T1";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA_ZIPPED").getAbsolutePath(),"testConfig");
		InteractionNormalizedTraceFinder finder = resource.getFinderFactory().getInteractionNormalizedTraceHandler();

		String methodName = "testPackage.Test0.changeAge(int,User)";
		Method method = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		
		InteractionNormalizedTrace trace = finder.findTrace(method);
		checkTrace(curTest, methodName, trace);
		
		
		methodName = "testPackage.Test1.m(int)";
		method = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		
		trace = finder.findTrace(method);
		checkTrace(curTest, methodName, trace);
		
	}

}
