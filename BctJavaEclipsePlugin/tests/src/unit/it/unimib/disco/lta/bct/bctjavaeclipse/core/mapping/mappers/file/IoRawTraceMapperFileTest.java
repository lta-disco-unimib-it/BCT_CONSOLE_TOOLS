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


import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointValue;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.test.TestUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class IoRawTraceMapperFileTest extends TestCase {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test if the system works in case it is recorded only a method call (an enter and an exit program point) wit no values inside.
	 *  
	 * @throws LoaderException
	 */
	public void testOneCall0() throws LoaderException{
		String methodName = "testPackage.Test0.m(int)";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T0/BCT_DATA").getAbsolutePath(),"testConfig");
		IoRawTraceMapperFile mapper = new IoRawTraceMapperFile(resource);
		IoRawTrace trace = mapper.findTrace(methodName);
		
		List<ProgramPointRaw> programPoints = trace.getProgramPoints();
		
		//Two program points
		assertEquals( 2, programPoints.size() );
		
		//Method names
		ProgramPoint pp0 = programPoints.get(0);
		assertEquals ( pp0.getMethod().getSignature(), methodName );
		assertTrue( pp0.isEnter() );
		assertFalse( pp0.isExit() );
		
		assertEquals(0,pp0.getProgramPointVariableValues().size());
		
		//Method names
		ProgramPoint pp1 = programPoints.get(1);
		assertEquals ( pp1.getMethod().getSignature(), methodName );
		assertFalse( pp1.isEnter() );
		assertTrue( pp1.isExit() );
		
		assertEquals(0,pp1.getProgramPointVariableValues().size());
		
	}
	
	
	/**
	 * Test if the system works in case it is recorded only a call of a method (an enter and an exit program point) with values inside in presnce of multiple calls recorded.
	 *  
	 * @throws LoaderException
	 */
	public void testOneCall1() throws LoaderException{
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		IoRawTraceMapperFile mapper = new IoRawTraceMapperFile(resource);
		IoRawTrace trace = mapper.findTrace("testPackage.Test1.m(int)");
		
		List<ProgramPointRaw> programPoints = trace.getProgramPoints();
		
		//Two program points
		assertEquals( 2, programPoints.size() );
		
		//Method names
		ProgramPoint pp0 = programPoints.get(0);
		assertEquals ( pp0.getMethod().getSignature(), "testPackage.Test1.m(int)" );
		assertTrue( pp0.isEnter() );
		assertFalse( pp0.isExit() );
		
		ProgramPoint pp1 = programPoints.get(1);
		assertEquals ( pp1.getMethod().getSignature(), "testPackage.Test1.m(int)" );
		assertFalse( pp1.isEnter() );
		assertTrue( pp1.isExit() );
		
		//Do not create different objects for the same method
		assertTrue ( pp0.getMethod() == pp1.getMethod() );
		
		
		
		//Value of the first
		Collection<ProgramPointValue> values = pp0.getProgramPointVariableValues();
		ProgramPointValue value;
		assertEquals(1 , values.size() );
		Iterator<ProgramPointValue> it = values.iterator();
		
		value = it.next();
		
		assertEquals("parameter[0]",value.getVariable().getName() );
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		assertFalse(it.hasNext());
		
		//Values of the second program point
		values = pp1.getProgramPointVariableValues();
		assertEquals(2 , values.size() );
		it = values.iterator();
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		
		value = it.next();
		assertEquals("returnValue", value.getVariable().getName());
		assertEquals("10",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
	}
	
	/**
	 * Test if the system works in case it is recorded only a call of a method (an enter and an exit program point) with values inside in presnce of multiple calls recorded.
	 *  
	 * @throws LoaderException
	 */
	public void testMultipleCalls() throws LoaderException{
		String methodName = "testPackage.Test0.changeAge(int,User)";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact("T1/BCT_DATA").getAbsolutePath(),"testConfig");
		IoRawTraceMapperFile mapper = new IoRawTraceMapperFile(resource);
		IoRawTrace trace = mapper.findTrace(methodName);
		
		List<ProgramPointRaw> programPoints = trace.getProgramPoints();
		
		//6 program points,3 calls
		assertEquals( 6, programPoints.size() );
		
		//Method names
		
		for ( ProgramPoint pp : programPoints ){
			assertEquals ( pp.getMethod().getSignature(), methodName );
		}
		
		ProgramPoint pp0 = programPoints.get(0);
		ProgramPoint pp1 = programPoints.get(1);
		ProgramPoint pp2 = programPoints.get(2);
		ProgramPoint pp3 = programPoints.get(3);
		ProgramPoint pp4 = programPoints.get(4);
		ProgramPoint pp5 = programPoints.get(5);
		
		Collection<ProgramPointValue> values;
		Iterator<ProgramPointValue> it;
		ProgramPointValue value;
		
		//
		//Values of the first program point
		values = pp0.getProgramPointVariableValues();
		assertEquals(3 , values.size() );
		it = values.iterator();
		
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].name",value.getVariable().getName());
		assertEquals("\"Pippo\"",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].age",value.getVariable().getName());
		assertEquals("9",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
		
		//
		//Values of the 2nd program point
		values = pp1.getProgramPointVariableValues();
		assertEquals(4 , values.size() );
		it = values.iterator();
		
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].name",value.getVariable().getName());
		assertEquals("\"Pippo\"",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].age",value.getVariable().getName());
		assertEquals("10",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("returnValue",value.getVariable().getName());
		assertEquals("10",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
		
		//
		//Values of the 3rd program point
		values = pp2.getProgramPointVariableValues();
		assertEquals(3 , values.size() );
		it = values.iterator();
		
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].name",value.getVariable().getName());
		assertEquals("\"Pluto\"",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].age",value.getVariable().getName());
		assertEquals("9",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
		
		//
		//Values of the 4th program point
		values = pp3.getProgramPointVariableValues();
		assertEquals(4 , values.size() );
		it = values.iterator();
		
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].name",value.getVariable().getName());
		assertEquals("\"Pluto\"",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].age",value.getVariable().getName());
		assertEquals("9",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("returnValue",value.getVariable().getName());
		assertEquals("9",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
		
		
		
		//
		//Values of the 5th program point
		values = pp4.getProgramPointVariableValues();
		assertEquals(3 , values.size() );
		it = values.iterator();
		
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].name",value.getVariable().getName());
		assertEquals("\"Pippo\"",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].age",value.getVariable().getName());
		assertEquals("90",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
		
		//
		//Values of the 6th program point
		values = pp5.getProgramPointVariableValues();
		assertEquals(4 , values.size() );
		it = values.iterator();
		
		value = it.next();
		assertEquals("parameter[0]",value.getVariable().getName());
		assertEquals("1",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].name",value.getVariable().getName());
		assertEquals("\"Pippo\"",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("parameter[1].age",value.getVariable().getName());
		assertEquals("13",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		value = it.next();
		assertEquals("returnValue",value.getVariable().getName());
		assertEquals("13",value.getValue());
		assertEquals(ProgramPointValue.ModifiedInfo.Assigned, value.getModified());
		
		assertFalse(it.hasNext());
		
		
	}
}
