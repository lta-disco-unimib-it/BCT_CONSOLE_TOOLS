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
package it.unimib.disco.lta.bct.bctjavaeclipse.core.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import automata.fsa.FiniteStateAutomaton;

/**
 * This class contains utilities to run test cases.
 * 
 * @author Fabrizio Pastore
 *
 */
public class TestUtil {
	private static final String artifactsDir = "tests/artifacts/"; 
	private static final String modelsDir = "Models";
	private static final String interactionModelsDir = "interactionInvariants";
	private static final String ioModelsDir = "interactionInvariants";
	
	private static HashMap<String, String[]> threadCallPoints;
	private static HashMap<String, String[]> normalizedTraces;
	private static HashMap<String, File> expectedFSA;
	private static HashMap<String, ArrayList<String> > ioInvariantsEnter;
	private static HashMap<String, ArrayList<String> > ioInvariantsExit;
	
	static {
		threadCallPoints = new HashMap<String,String[]>();
		
		
		
		String[] callPoints = new String[]{
				"testPackage.Test1.m(int)B#",
				"testPackage.Test0.changeAge(int,User)B#",
				"testPackage.Test0.changeAge(int,User)E#",
				"testPackage.Test1.m(int)E#",
				"testPackage.Test0.changeAge(int,User)B#",
				"testPackage.Test0.changeAge(int,User)E#"
		};
		
		threadCallPoints.put(getThreadCallPointId("T1", "1"), callPoints);
		
		
		
		
		callPoints = new String[]{
				"testPackage.Test0.changeAge(int,User)B#",
				"testPackage.Test0.changeAge(int,User)E#"
		};
		
		threadCallPoints.put(getThreadCallPointId("T1", "10"), callPoints);
		
		
		callPoints = new String[]{
				"testPackage.Test0.m(int)B#",
				"testPackage.Test0.m(int)E#"
		};
		
		
		threadCallPoints.put(getThreadCallPointId("T0", "1"), callPoints);
		
		
		
		//
		//Normalized traces contents
		//
		
		normalizedTraces = new HashMap<String, String[]>();
		
		String[] sequences = new String[]{
			""	
		};
		
		normalizedTraces.put(getNormalizedTraceId("T0", "testPackage.Test0.m(int)"), sequences);
		
		sequences = new String[]{
				"",
				"",
				""
		};
		
		normalizedTraces.put(getNormalizedTraceId("T1", "testPackage.Test0.changeAge(int,User)"), sequences);
		
		sequences = new String[]{
				"testPackage.Test0.changeAge(int,User)"
		};
		
		normalizedTraces.put(getNormalizedTraceId("T1", "testPackage.Test1.m(int)"), sequences);
		
		//
		//Expected FSA
		//
		
		expectedFSA = new HashMap<String, File>();
		String test;
		
		
		//T0
		test = "T0";
		File file = new File(getTestFile("unit", test+"/BCT_DATA"),modelsDir+"/"+interactionModelsDir+"/0.ser");
		
		expectedFSA.put(getFSAModelId(test,"testPackage.Test0.m(int)"), file);
		
		//T1
		test = "T1";
		file = new File(getTestFile("unit", test+"/BCT_DATA"),modelsDir+"/"+interactionModelsDir+"/0.ser");
		expectedFSA.put(getFSAModelId(test,"testPackage.Test1.m(int)"), file);
		
		test = "T1";
		file = new File(getTestFile("unit", test+"/BCT_DATA"),modelsDir+"/"+interactionModelsDir+"/1.ser");
		expectedFSA.put(getFSAModelId(test,"testPackage.Test0.changeAge(int,User)"), file);
		
		
		ioInvariantsEnter = new HashMap<String, ArrayList<String>>();
		ioInvariantsExit = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> ioInvariants;
		
		
		//
		//T0
		//
		
		//No invariants for this method
		ioInvariantsEnter.put(getIoModelEnterId("T0","testPackage.Test0.m(int)"), new ArrayList<String>(0));
		
		
		//
		//T1
		//
		
		//method 0
		
		String method = "testPackage.Test0.changeAge(int,User)";
		
		ioInvariants = new ArrayList<String>();
		
		ioInvariants.add("parameter[0] == 1");
		ioInvariants.add("parameter[1].age one of { 9, 90 }");
		ioInvariants.add("store(parameter[0])");
		ioInvariants.add("store(parameter[1].age) >> returnValue == 0)");
		
		ioInvariantsEnter.put(getIoModelEnterId("T1",method), ioInvariants);
		
		
		
		ioInvariants = new ArrayList<String>();
		
		ioInvariants.add("parameter[0] == orig(parameter[0])");
		ioInvariants.add("parameter[1].age == returnValue");
		ioInvariants.add("parameter[0] == 1");
		ioInvariants.add("returnValue one of { 9, 10, 13 }");
		ioInvariants.add("(orig(parameter[1].age) >> returnValue == 0)");
		
		ioInvariantsExit.put(getIoModelExitId("T1",method), ioInvariants);
		
		//method 1
		
		method = "testPackage.Test1.m(int)";
		
		ioInvariants = new ArrayList<String>();
		
		ioInvariants.add("parameter[0] == 1");
		ioInvariants.add("store(parameter[0])");
		
		ioInvariantsEnter.put(getIoModelEnterId("T1",method), ioInvariants);
		
		
		
		ioInvariants = new ArrayList<String>();
		
		ioInvariants.add("parameter[0] == orig(parameter[0])");
		ioInvariants.add("parameter[0] == 1");
		ioInvariants.add("returnValue == 10");
		
		ioInvariantsExit.put(getIoModelExitId("T1",method), ioInvariants);
		
		
		
	}
	
	
	
	/**
	 * This method returns a unit test artifact. You need to pass the relative path of the file with respect to the test artifact folder.
	 * For example, unit test artifacts  
	 * @param fileName
	 * @return
	 */
	public static File getUnitTestArtifact(String fileName) {
		return getTestFile("unit", fileName);
	}
	
	private static String getIoModelEnterId(String test, String method) {
		return getIoModelId(test,method)+"#ENTER";
	}

	public static String getIoModelExitId(String test, String method) {
		return getIoModelId(test,method)+"#EXIT";
	}
	
	public static String getIoModelId(String test, String method) {
		return test+"#"+method;
	}

	private static String getFSAModelId(String testName, String methodName) {
		return testName+"#"+methodName;
	}

	public static File getTestFile(String type, String fileName) {
		return new File(artifactsDir+type+"/"+fileName);
	}

	public static String[] getThreadCallPoints(String test, String threadId){
		return threadCallPoints.get(getThreadCallPointId(test,threadId));
	}
	
	public static String[] getNormalizedCallSequences(String test, String method){
		return normalizedTraces.get(getNormalizedTraceId(test, method));
	}

	private static String getThreadCallPointId(String test, String threadId) {
		return test+"#"+threadId;
	}
	
	private static String getNormalizedTraceId(String test, String method) {
		return test+"#"+method;
	}

	public static FiniteStateAutomaton getExpectedFSA(String testName, String methodName) throws FileNotFoundException, ClassNotFoundException, IOException {
		File file = expectedFSA.get(getFSAModelId(testName, methodName));
		return FiniteStateAutomaton.readSerializedFSA(file.getAbsolutePath());
	}
	
	public static List<String> getExpectedIoInvariuantsEnter(String test,String method){
		return ioInvariantsEnter.get(getIoModelEnterId(test, method));
	}
	
	public static List<String> getExpectedIoInvariuantsExit(String test,String method){
		return ioInvariantsExit.get(getIoModelExitId(test, method));
	}

	public static File getBugsTestArtifact(String fileName) {
		return getTestFile("bugs", fileName);
	}
}
