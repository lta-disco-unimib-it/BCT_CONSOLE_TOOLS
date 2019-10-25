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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourceFile;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.FSAModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.test.TestUtil;

import java.util.List;

import junit.framework.TestCase;
import automata.fsa.FiniteStateAutomaton;

public class ModelsMapperFileTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetEFSAModels() {
		fail("Not yet implemented");
	}

	public void testGetFSAModelsOneMethod() throws Exception {
		String methodName = "testPackage.Test0.m(int)";
		String testName = "T0";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(testName+"/BCT_DATA").getAbsolutePath(),"testConfig");
		List<FSAModel> models = resource.getFinderFactory().getFSAModelsHandler().getFSAModels();
		
		assertEquals(1,models.size());
		
		FSAModel model = models.get(0);
		
		checkFSAModel(testName,methodName,model);
	}

	public void testGetFSAModelsTwoMethods() throws Exception {
		
		String testName = "T1";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(testName+"/BCT_DATA").getAbsolutePath(),"testConfig");
		List<FSAModel> models = resource.getFinderFactory().getFSAModelsHandler().getFSAModels();
		
		assertEquals(2,models.size());
		
		String methodName = "testPackage.Test0.changeAge(int,User)";
		FSAModel model = models.get(0);
		checkFSAModel(testName,methodName,model);
		
		methodName = "testPackage.Test1.m(int)";
		model = models.get(1);
		checkFSAModel(testName,methodName,model);
		
	}
	
	private void checkFSAModel(String testName, String methodName, FSAModel model) throws Exception {
		FiniteStateAutomaton expectedFSA = TestUtil.getExpectedFSA(testName,methodName);
		
		assertEquals( methodName, model.getMethod().getSignature() );
		
		assertTrue( modelsEquals (expectedFSA, model.getFSA() ) );
		
	}

	private boolean modelsEquals(FiniteStateAutomaton expectedFSA, FiniteStateAutomaton fsa) {
		if ( expectedFSA.getFinalStates().length != fsa.getFinalStates().length )
			return false;
		
		if ( expectedFSA.getTransitions().length != fsa.getTransitions().length )
			return false;
		
		if ( expectedFSA.getStates().length != fsa.getStates().length )
			return false;
		
		return true;
	}

	public void testGetIoModelsVoidModel() throws Exception {
		String testName = "T0";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(testName+"/BCT_DATA").getAbsolutePath(),"testConfig");
		List<IoModel> models = resource.getFinderFactory().getIoModelsHandler().getIoModels();
		
		assertEquals(1, models.size());
		
		String methodName = "testPackage.Test0.m(int)";
		IoModel model = models.get(0);
		checkIoModel(testName,methodName,model);
	}

	public void testGetIoModelsTwoModels() throws Exception {
		String testName = "T1";
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(testName+"/BCT_DATA").getAbsolutePath(),"testConfig");
		List<IoModel> models = resource.getFinderFactory().getIoModelsHandler().getIoModels();
		
		assertEquals(2, models.size());
		
		String methodName = "testPackage.Test0.changeAge(int,User)";
		IoModel model = models.get(0);
		checkIoModel(testName,methodName,model);
		
		
		methodName = "testPackage.Test1.m(int)";
		model = models.get(1);
		checkIoModel(testName,methodName,model);
		
	}
	
	private void checkIoModel(String testName, String methodName, IoModel model) throws Exception {
		
		assertNotNull(model);
		
		assertEquals(methodName, model.getMethod().getSignature());
		
		
		List<IoExpression> enterExpressions = model.getExpressionsEnter();
		List<String> expected = TestUtil.getExpectedIoInvariuantsEnter(testName, methodName);
		
		assertNotNull(expected);
		assertEquals(expected.size(), enterExpressions.size());
		
		for ( IoExpression enterExpression : enterExpressions ){
			assertTrue( expected.contains(enterExpression.toString()) );
		}
		
		
		List<IoExpression> exitExpressions = model.getExpressionsExit();
		expected = TestUtil.getExpectedIoInvariuantsExit(testName, methodName);
		for ( IoExpression exitExpression : exitExpressions ){
			assertTrue( expected.contains(exitExpression.toString()) );
		}
	}

	public void testGetEFSAModel() {
		fail("Not yet implemented");
	}

	public void testGetFSAModel() throws Exception {
		String testName = "T1";
		
		ResourceFile resource = new ResourceFile("test",TestUtil.getUnitTestArtifact(testName+"/BCT_DATA").getAbsolutePath(),"testConfig");
		
		
		FSAModel model;
		String methodName;
		
		FSAModelsFinder fsaFinder = resource.getFinderFactory().getFSAModelsHandler();
		
		methodName = "testPackage.Test1.m(int)";
		Method method = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		
		model = fsaFinder.getFSAModel(method);
		checkFSAModel(testName,methodName,model);
		
		
		methodName = "testPackage.Test0.changeAge(int,User)";
		method = resource.getFinderFactory().getMethodHandler().getMethod(methodName);
		
		model = fsaFinder.getFSAModel(method);
		checkFSAModel(testName,methodName,model);
	}

	public void testGetIoModel() {
		
	}


	public void testGetRawEFSA() {
		fail("Not yet implemented");
	}

	

}
