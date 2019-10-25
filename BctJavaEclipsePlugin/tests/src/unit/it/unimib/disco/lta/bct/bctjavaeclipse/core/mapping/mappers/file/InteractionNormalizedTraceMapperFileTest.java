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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.MethodCallSequence;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.InteractionNormalizedTraceFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.test.TestUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

public class InteractionNormalizedTraceMapperFileTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFindAllTracesOneTrace() throws MapperException, LoaderException {
		String curTest = "T0";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA").getAbsolutePath(),"testConfig");
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
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA").getAbsolutePath(),"testConfig");
		InteractionNormalizedTraceFinder finder = resource.getFinderFactory().getInteractionNormalizedTraceHandler();
		
		Collection<InteractionNormalizedTrace> returnedTraces = finder.findAllTraces();
		
		ArrayList<InteractionNormalizedTrace> traces = new ArrayList<InteractionNormalizedTrace>();
		traces.addAll(returnedTraces);
		
		Collections.sort(traces, new Comparator<InteractionNormalizedTrace>(){

			public int compare(InteractionNormalizedTrace o1,
					InteractionNormalizedTrace o2) {
				return o1.getMethod().getSignature().compareTo(o2.getMethod().getSignature());
			}

			
		});

		
		
		Iterator<InteractionNormalizedTrace> tIt = traces.iterator();
		
				
		InteractionNormalizedTrace  trace;
		
		
		//
		// trace for method testPackage.Test0.changeAge(int,User)
		// 
		
		trace = tIt.next();
		
		checkTrace(curTest , "testPackage.Test0.changeAge(int,User)", trace);
		
		//
		// trace for method testPackage.Test1.m(int)
		//
		
		trace = tIt.next();
		
		checkTrace(curTest , "testPackage.Test1.m(int)", trace);
		
		
		
		
		
		
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
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA").getAbsolutePath(),"testConfig");
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
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(curTest+"/BCT_DATA").getAbsolutePath(),"testConfig");
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
