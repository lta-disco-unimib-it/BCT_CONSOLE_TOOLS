
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionRawTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.test.TestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class Bug147 {

	private static String[] expectedCallPoints;


	@BeforeClass
	public static void setUpClass(){
		ArrayList<String> l = new ArrayList<String>();
		
		l.add("main.ProgramCorrect.main(([Ljava.lang.String;)V)B#");
		l.add("people.Persona.<init>((Ljava.lang.String;)V)B#");
		l.add("people.Persona.<init>((Ljava.lang.String;)V)E#");
		l.add("people.Persona.<init>((Ljava.lang.String;)V)B#");
		l.add("people.Persona.<init>((Ljava.lang.String;)V)E#");
		l.add("people.Persona.saluta((Lpeople.Persona;)V)B#");
		l.add("people.Persona.saluta((Lpeople.Persona;)V)E#");
		l.add("people.Persona.saluta((Lpeople.Persona;)V)B#");
		l.add("people.Persona.saluta((Lpeople.Persona;)V)E#");
		l.add("main.ProgramCorrect.main(([Ljava.lang.String;)V)E#");
		
		expectedCallPoints = l.toArray(new String[l.size()]);
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetSingleElements() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getBugsTestArtifact("147/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		List<InteractionRawTrace> traces = finder.findAllTraces();
		
		assertEquals(1, traces.size());
		
		InteractionRawTrace trace = traces.get(0);
		
		//FIXME: we do not want a string as an id
		assertEquals("0.dtrace",trace.getId());
		
		//FIXME: we want the java id here
		assertEquals("0.dtrace",trace.getThread().getId());
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(expectedCallPoints.length, callPoints.size());
		
		assertEquals( expectedCallPoints[0], getRawCallPoint( callPoints.get(0) ) );
		
		assertEquals( expectedCallPoints[1], getRawCallPoint( callPoints.get(1) ) );
		
		assertEquals( expectedCallPoints[2], getRawCallPoint( callPoints.get(2) ) );
		
		assertEquals( expectedCallPoints[3], getRawCallPoint( callPoints.get(3) ) );
		
		assertEquals( expectedCallPoints[4], getRawCallPoint( callPoints.get(4) ) );
		
		assertEquals( expectedCallPoints[5], getRawCallPoint( callPoints.get(5) ) );
		
		assertEquals( expectedCallPoints[6], getRawCallPoint( callPoints.get(6) ) );
		
		assertEquals( expectedCallPoints[7], getRawCallPoint( callPoints.get(7) ) );
		
		assertEquals( expectedCallPoints[8], getRawCallPoint( callPoints.get(8) ) );
		
		assertEquals( expectedCallPoints[9], getRawCallPoint( callPoints.get(9) ) );
		
		
	}
	
	
	@Test
	public void testIterator() throws MapperException, LoaderException {
		ResourceFile resource = new ResourceFile("test",TestUtil.getBugsTestArtifact("147/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionRawTraceFinder finder = resource.getFinderFactory().getInteractionRawTraceHandler();
		
		List<InteractionRawTrace> traces = finder.findAllTraces();
		
		assertEquals(1, traces.size());
		
		InteractionRawTrace trace = traces.get(0);
		
		//FIXME: we do not want a string as an id
		assertEquals("0.dtrace",trace.getId());
		
		//FIXME: we want the java id here
		assertEquals("0.dtrace",trace.getThread().getId());
		
		List<MethodCallPoint> callPoints = trace.getMethodCallPoints();
		
		assertEquals(expectedCallPoints.length, callPoints.size());
		
		Iterator<MethodCallPoint> it = callPoints.iterator();
		int i = -1;
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertTrue( it.hasNext() );
		assertEquals( expectedCallPoints[i], getRawCallPoint( it.next() ) );
		
		i++;
		assertFalse( it.hasNext() );
		
	}
	
	
	
	/*
	 * Copied from InteractionRawTraceMapperTest
	 */
	private String getRawCallPoint(MethodCallPoint mcp) {
		String suffix;
		if ( mcp.isEnter() ){
			suffix = "B#";
		} else {
			suffix = "E#";
		}
		return mcp.getMethod().getSignature()+suffix;
	}
}
