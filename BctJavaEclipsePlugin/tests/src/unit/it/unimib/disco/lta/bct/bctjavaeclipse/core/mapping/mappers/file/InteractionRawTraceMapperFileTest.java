package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionRawTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.test.TestUtil;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InteractionRawTraceMapperFileTest extends TestCase {

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAllTracesOneTrace() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T0/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		List<InteractionRawTrace> traces = finder.findAllTraces();
		
		assertEquals(1, traces.size());
		
		InteractionRawTrace trace = traces.get(0);
		
		//FIXME: we do not want a string as an id
		assertEquals("0.dtrace",trace.getId());
		
		//FIXME: we want the java id here
		assertEquals("0.dtrace",trace.getThread().getId());
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(2, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T0", "1");
		
		assertEquals( expectedCallPoints[0], getRawCallPoint( callPoints.get(0) ) );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint( callPoints.get(1) ) );
		
	}
	
	@Test
	public void testFindAllTracesOneTraceIterator() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T0/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		List<InteractionRawTrace> traces = finder.findAllTraces();
		
		assertEquals(1, traces.size());
		
		InteractionRawTrace trace = traces.get(0);
		
		//FIXME: we do not want a string as an id
		assertEquals("0.dtrace",trace.getId());
		
		//FIXME: we want the java id here
		assertEquals("0.dtrace",trace.getThread().getId());
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(2, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T0", "1");
		
		
		Iterator<MethodCallPoint> it = callPoints.iterator();
		
		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[0], getRawCallPoint( it.next() ) );
		
		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[1], getRawCallPoint( it.next()) );
		
		assertFalse(it.hasNext());
	}
	
	public void testFindAllTracesManyTraces() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		List<InteractionRawTrace> traces = finder.findAllTraces();
		
		assertEquals(2, traces.size());
		
		//Trace for thread 1
		
		InteractionRawTrace trace = traces.get(0);
		
		//FIXME
		assertEquals("1.dtrace",trace.getId());
		
		assertEquals("1",trace.getThread().getThreadId());
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(6, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T1", "1" );
		
		assertEquals( expectedCallPoints[0], getRawCallPoint( callPoints.get(0) ) );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint( callPoints.get(1) ) );
		
		assertEquals( expectedCallPoints[2], getRawCallPoint( callPoints.get(2) ) );
		
		assertEquals( expectedCallPoints[3], getRawCallPoint( callPoints.get(3) ) );
		
		assertEquals( expectedCallPoints[4], getRawCallPoint( callPoints.get(4) ) );
		
		assertEquals( expectedCallPoints[5], getRawCallPoint( callPoints.get(5) ) );
		
		
		//
		//Trace for thread 10
		//
		
		trace = traces.get(1);
		
		assertEquals("0.dtrace",trace.getId());
		
		assertEquals("10",trace.getThread().getThreadId());
		
		callPoints = trace.getMethodCallPoints();
		
		assertEquals(2, callPoints.size());
		
		expectedCallPoints = TestUtil.getThreadCallPoints("T1", "10" );
		
		assertEquals( expectedCallPoints[0], getRawCallPoint(callPoints.get(0)) );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint(callPoints.get(1)) );
		
	}
	
	


	private String getRawCallPoint(MethodCallPoint mcp) {
		String suffix;
		if ( mcp.isEnter() ){
			suffix = "B#";
		} else {
			suffix = "E#";
		}
		return mcp.getMethod().getSignature()+suffix;
	}

	@Test
	public void testFindMethodCallPointsForTrace() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		
		//
		//Thread 10
		//
		
		InteractionRawTrace trace = finder.findTrace("0.dtrace");
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(2, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T1", "10" );
		
		assertEquals( expectedCallPoints[0], getRawCallPoint( callPoints.get(0) ) );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint( callPoints.get(1) ) );
		
		
		
		//
		//Thread 1
		//
		
		trace = finder.findTrace("1.dtrace");
		
		callPoints = trace.getMethodCallPoints();
		
		assertEquals(6, callPoints.size());
		
		expectedCallPoints = TestUtil.getThreadCallPoints("T1", "1" );
		
		assertEquals( expectedCallPoints[0], getRawCallPoint( callPoints.get(0) ) );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint( callPoints.get(1) ) );
		
		assertEquals( expectedCallPoints[2], getRawCallPoint( callPoints.get(2) ) );
		
		assertEquals( expectedCallPoints[3], getRawCallPoint( callPoints.get(3) ) );
		
		assertEquals( expectedCallPoints[4], getRawCallPoint( callPoints.get(4) ) );
		
		assertEquals( expectedCallPoints[5], getRawCallPoint( callPoints.get(5) ) );
	}
	
	
	@Test
	public void testFindMethodCallPointsForTraceIterator() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		
		//
		//Thread 10
		//
		
		InteractionRawTrace trace = finder.findTrace("0.dtrace");
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(2, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T1", "10" );
		
		Iterator<MethodCallPoint> it;
		
		it = callPoints.iterator();
		
		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[0], getRawCallPoint( it.next()) );
		
		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[1], getRawCallPoint( it.next() ) );
		
		assertFalse(it.hasNext());
		
		//
		//Thread 1
		//
		
		trace = finder.findTrace("1.dtrace");
		
		callPoints = trace.getMethodCallPoints();
		
		assertEquals(6, callPoints.size());
		it = callPoints.iterator();
		
		expectedCallPoints = TestUtil.getThreadCallPoints("T1", "1" );

		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[0], getRawCallPoint( it.next() ) );

		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[1], getRawCallPoint( it.next() ) );

		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[2], getRawCallPoint( it.next() ) );

		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[3], getRawCallPoint( it.next() ) );

		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[4], getRawCallPoint( it.next() ) );

		assertTrue(it.hasNext());
		assertEquals( expectedCallPoints[5], getRawCallPoint( it.next() ) );

		assertFalse(it.hasNext());
		
	}
	
	
	/**
	 * Direct access to last call point
	 * 
	 * @throws MapperException
	 * @throws LoaderException
	 */
	@Test
	public void testFindSpecificMethodCallPointForThread_last() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		
		//
		//Thread 10
		//
		
		InteractionRawTrace trace = finder.findTrace("0.dtrace");
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(2, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T1", "10" );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint( callPoints.get(1) ) );
		
		
		
		//
		//Thread 1
		//
		
		trace = finder.findTrace("1.dtrace");
		
		callPoints = trace.getMethodCallPoints();
		
		assertEquals(6, callPoints.size());
		
		expectedCallPoints = TestUtil.getThreadCallPoints("T1", "1" );
		
		assertEquals( expectedCallPoints[5], getRawCallPoint( callPoints.get(5) ) );
	}
	
	
	/**
	 * Direct access to last call point
	 * 
	 * @throws MapperException
	 * @throws LoaderException
	 */
	@Test
	public void testFindSpecificMethodCallPointForThread_middle() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		
		
		//
		//Thread 1
		//
		
		InteractionRawTrace trace = finder.findTrace("1.dtrace");
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(6, callPoints.size());
		
		String[] expectedCallPoints = TestUtil.getThreadCallPoints("T1", "1" );
		
		assertEquals( expectedCallPoints[3], getRawCallPoint( callPoints.get(3) ) );
	}

}
